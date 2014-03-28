package pt.it.porto.esoares.android.network.ip;

import java.net.InetAddress;

public interface IPManager {

	/**
	 * The API version that was taken in mind to work perfectly with
	 * 
	 * @return API version
	 */
	public int getDesingAPI();

	/**
	 * Gets the IP Address from current connected network
	 * 
	 * @return the IP address
	 */
	public InetAddress getIP();

	/**
	 * Gets the Network Mask from the current connected network
	 * 
	 * @return the Network Mask
	 */
	public int getMask();

	/**
	 * Gets the default gateway from the current connected network
	 * 
	 * @return the default gateway
	 */
	public InetAddress getGateway();

	/**
	 * Gets the current DNS 1 server from the connected network
	 * 
	 * @return the first DNS server
	 */
	public InetAddress getDNS();

	/**
	 * Sets the IP address, Network Mask, Default Gateway and the first DNS server
	 * 
	 * @param ip
	 *            the <i>IP address</i> to set, in case of don't want to change it use the return value from {@link #getIP()}
	 * @param mask
	 *            the <i>Network Mask</i> to set, in case of don't want to change it use the return value from {@link #getMask()}
	 * @param gateway
	 *            the <i>Default Gateway</i> to set, in case of don't want to change it use the return value from {@link #getGateway()}
	 * @param DNS
	 *            the first <i>DNS Server</i> to set, in case of don't want to change it use the return value from {@link #getDNS()}
	 */
	public void setIP(InetAddress ip, int mask, InetAddress gateway, InetAddress DNS);

	/**
	 * Sets the current network to use DHCP instead of static IP
	 */
	public void setDHCP();

}
