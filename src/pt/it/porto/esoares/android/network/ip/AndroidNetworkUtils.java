
package pt.it.porto.esoares.android.network.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AndroidNetworkUtils {

    /**
     * Convert a IPv4 address from an integer to an InetAddress.
     * 
     * @param hostAddress an int corresponding to the IPv4 address in network byte order
     */
    public static InetAddress intToInetAddress(int hostAddress) {
        byte[] addressBytes = {
                (byte) (0xff & hostAddress),
                (byte) (0xff & (hostAddress >> 8)),
                (byte) (0xff & (hostAddress >> 16)),
                (byte) (0xff & (hostAddress >> 24))
        };

        try {
            return InetAddress.getByAddress(addressBytes);
        } catch (UnknownHostException e) {
            throw new AssertionError();
        }
    }

    
    public static String networkMaskFromInt(int numericMask) {
//        StringBuffer sb = new StringBuffer(15);
//        for (int shift = 24; shift > 0; shift -= 8) {
//            // process 3 bytes, from high order byte down.
//            sb.append(Integer.toString((numericMask >>> shift) & 0xff));
//            sb.append('.');
//        }
//        sb.append(Integer.toString(numericMask & 0xff));
//        return sb.toString();
//    }
//    
//    public String getMaskAsString() {
        int value = 0xffffffff << (32 - numericMask);
        byte[] bytes = new byte[] { (byte) (value >>> 24),
                (byte) (value >> 16 & 0xff), (byte) (value >> 8 & 0xff),
                (byte) (value & 0xff) };

        InetAddress netAddr = null;
        try {
            netAddr = InetAddress.getByAddress(bytes);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return netAddr.getHostAddress();
    }
    
    public static int networkMaskFromInetAddress(InetAddress address){
        byte[] addrs=address.getAddress(); 
        int result=0;
        int i=24;
        for(byte value:addrs){
            result+= value << i;
            i -= 8;
        }
        return result;
    }
}
