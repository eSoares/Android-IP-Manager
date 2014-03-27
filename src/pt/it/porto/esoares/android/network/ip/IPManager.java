
package pt.it.porto.esoares.android.network.ip;

import java.net.InetAddress;

public interface IPManager {

    /**
     * The API version that was taken in mind to work perfectly with
     * 
     * @return API version
     */
    public int getDesingAPI();

    public InetAddress getIP();

    public int getMask();

    public InetAddress getGateway();

    public InetAddress getDNS();

    public void setIP(InetAddress ip, int mask, InetAddress gateway, InetAddress DNS);

    public void setDHCP();

}
