
package pt.it.porto.esoares.android.network.ip;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.net.InetAddress;
import java.net.UnknownHostException;

class IPManagerPreHoneycomb implements IPManager {
    final Context context;

    IPManagerPreHoneycomb(Context context) {
        this.context = context;
    }

    @Override
    public int getDesingAPI() {
        return Build.VERSION_CODES.GINGERBREAD_MR1;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public InetAddress getIP() {
        return getFromSettingsSystem(android.provider.Settings.System.WIFI_STATIC_IP);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public int getMask() {
        InetAddress tmp = getFromSettingsSystem(android.provider.Settings.System.WIFI_STATIC_NETMASK);
        return AndroidNetworkUtils.networkMaskFromInetAddress(tmp);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public InetAddress getGateway() {
        return getFromSettingsSystem(android.provider.Settings.System.WIFI_STATIC_GATEWAY);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public InetAddress getDNS() {
        return getFromSettingsSystem(android.provider.Settings.System.WIFI_STATIC_DNS1);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void setIP(InetAddress ip, int mask, InetAddress gateway, InetAddress DNS) {
        setStaticIP();
        Settings.System.putString(context.getContentResolver(), android.provider.Settings.System.WIFI_STATIC_IP,
                ip.getHostAddress());
        Settings.System.putString(context.getContentResolver(), android.provider.Settings.System.WIFI_STATIC_GATEWAY,
                gateway.getHostAddress());
        Settings.System.putString(context.getContentResolver(), android.provider.Settings.System.WIFI_STATIC_NETMASK,
                AndroidNetworkUtils.networkMaskFromInt(mask));
        Settings.System.putString(context.getContentResolver(), android.provider.Settings.System.WIFI_STATIC_DNS1,
                DNS.getHostAddress());
    }

    @Override
    public void setDHCP() {
        Settings.System.putInt(context.getContentResolver(), "wifi_use_static_ip", 0); // enabling DHCP
    }

    private void setStaticIP() {
        Settings.System.putInt(context.getContentResolver(), "wifi_use_static_ip", 1); // enabling static ip
    }

    private InetAddress getFromSettingsSystem(String whatToGet) {
        String got = Settings.System.getString(context.getContentResolver(), whatToGet);
        try {
            return InetAddress.getByName(got);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
