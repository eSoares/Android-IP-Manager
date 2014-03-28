
package pt.it.porto.esoares.android.network.ip;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

class IPManagerKitKat implements IPManager {
    private Context context;
    private WifiManager wifiManager;

    IPManagerKitKat(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public int getDesingAPI() {
        return Build.VERSION_CODES.KITKAT;
    }

    @Override
    public InetAddress getIP() {
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        // LogMe.log(((Integer) connectionInfo.getIpAddress()).toString());
        try {
            return getIpAddress(getCurrentWifiConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AndroidNetworkUtils.intToInetAddress(connectionInfo.getIpAddress());
    }

    @Override
    public void setIP(InetAddress ip, int mask, InetAddress gateway, InetAddress DNS) {
        WifiConfiguration wifiConf = getCurrentWifiConfiguration();
        try {
            setIpAssignment("STATIC", wifiConf); // or "DHCP" for dynamic setting
            setIpAddress(ip, mask, wifiConf);
            setGateway(gateway, wifiConf);
            setDNS(DNS == null ? InetAddress.getByName("4.4.4.4") : DNS, wifiConf);
            wifiManager.updateNetwork(wifiConf); // apply the setting
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private WifiConfiguration getCurrentWifiConfiguration() {
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration conf : configuredNetworks) {
            if (conf.networkId == connectionInfo.getNetworkId()) {
                return conf;
            }
        }
        return null;
    }

    @Override
    public void setDHCP() {
        WifiConfiguration wifiConf = getCurrentWifiConfiguration();
        try {
            setIpAssignment("DHCP", wifiConf);
        } catch (Exception e) {
            LogMe.error(e.toString());
        }
    }

    @Override
    public InetAddress getGateway() {
        WifiConfiguration wifiConf = getCurrentWifiConfiguration();
        try {
            return getGateway(wifiConf);
        } catch (Exception e) {
            LogMe.error(e.toString());
        }
        return null;
    }

    @Override
    public int getMask() {
        WifiConfiguration wifiConf = getCurrentWifiConfiguration();
        try {
            return getPerfixLenght(wifiConf);
        } catch (Exception e) {
            LogMe.error(e.toString());
        }
        return -2;
    }

    @Override
    public InetAddress getDNS() {
        WifiConfiguration wifiConf = getCurrentWifiConfiguration();
        try {
            return getDNS(wifiConf);
        } catch (Exception e) {
            LogMe.error(e.toString());
        }
        return null;
    }

    /*
     * ------------------------------------------
     * Utility methods for reflection
     * ------------------------------------------
     */

    private static void setIpAssignment(String assign, WifiConfiguration wifiConf)
            throws SecurityException,
            IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        Reflection.setEnumField(wifiConf, assign, "ipAssignment");
    }

    private static InetAddress getIpAddress(WifiConfiguration wifiConf) throws Exception {
        Object linkProperties = Reflection.getField(wifiConf, "linkProperties");
        if (linkProperties == null)
            return null;
        ArrayList mLinkAddresses = (ArrayList) Reflection.getDeclaredField(linkProperties,
                "mLinkAddresses");
        return (InetAddress) Reflection.getDeclaredField(mLinkAddresses.get(0), "address");
    }

    private static void setIpAddress(InetAddress addr, int prefixLength, WifiConfiguration wifiConf) throws Exception {
        Object linkProperties = Reflection.getField(wifiConf, "linkProperties");
        if (linkProperties == null)
            return;
        Class laClass = Class.forName("android.net.LinkAddress");
        Constructor laConstructor = laClass.getConstructor(new Class[] {
                InetAddress.class, int.class
        });
        Object linkAddress = laConstructor.newInstance(addr, prefixLength);

        ArrayList mLinkAddresses = (ArrayList) Reflection.getDeclaredField(linkProperties,
                "mLinkAddresses");
        mLinkAddresses.clear();
        mLinkAddresses.add(linkAddress);
    }

    private static int getPerfixLenght(WifiConfiguration wifiConf) throws Exception {
        Object linkProperties = Reflection.getField(wifiConf, "linkProperties");
        if (linkProperties == null)
            return -1;

        ArrayList mLinkAddresses = (ArrayList) Reflection.getDeclaredField(linkProperties,
                "mLinkAddresses");

        return (Integer) Reflection.getDeclaredField(mLinkAddresses.get(0), "prefixLength");
    }

    private static void setGateway(InetAddress gateway, WifiConfiguration wifiConf) throws Exception {
        Object linkProperties = Reflection.getField(wifiConf, "linkProperties");
        if (linkProperties == null)
            return;
        Class routeInfoClass = Class.forName("android.net.RouteInfo");
        Constructor routeInfoConstructor = routeInfoClass.getConstructor(new Class[] {
                InetAddress.class
        });
        Object routeInfo = routeInfoConstructor.newInstance(gateway);

        ArrayList mRoutes = (ArrayList) Reflection.getDeclaredField(linkProperties, "mRoutes");
        mRoutes.clear();
        mRoutes.add(routeInfo);
    }

    private static InetAddress getGateway(WifiConfiguration wifiConf) throws Exception {
        Object linkProperties = Reflection.getField(wifiConf, "linkProperties");
        if (linkProperties == null)
            return null;
        Class routeInfoClass = Class.forName("android.net.RouteInfo");
        ArrayList mRoutes = (ArrayList) Reflection.getDeclaredField(linkProperties, "mRoutes");
        Object destination = Reflection.getDeclaredField(routeInfoClass.cast(mRoutes.get(0)),
                "mGateway");
        return (InetAddress) destination;

    }

    private static void setDNS(InetAddress dns, WifiConfiguration wifiConf) throws Exception {
        Object linkProperties = Reflection.getField(wifiConf, "linkProperties");
        if (linkProperties == null)
            return;

        ArrayList<InetAddress> mDnses = (ArrayList<InetAddress>) Reflection.getDeclaredField(
                linkProperties, "mDnses");
        mDnses.clear(); // or add a new dns address , here I just want to
                        // replace DNS1
        mDnses.add(dns);
    }

    private static InetAddress getDNS(WifiConfiguration wifiConf) throws Exception {
        Object linkProperties = Reflection.getField(wifiConf, "linkProperties");
        if (linkProperties == null)
            return null;

        ArrayList<InetAddress> mDnses = (ArrayList<InetAddress>) Reflection.getDeclaredField(
                linkProperties, "mDnses");
        return mDnses.get(0);
    }
}
