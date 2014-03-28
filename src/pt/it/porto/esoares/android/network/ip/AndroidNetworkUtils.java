
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
        int value = 0xffffffff << (32 - numericMask);
        byte[] bytes = new byte[] {
                (byte) (value >>> 24),
                (byte) (value >> 16 & 0xff), (byte) (value >> 8 & 0xff),
                (byte) (value & 0xff)
        };

        InetAddress netAddr = null;
        try {
            netAddr = InetAddress.getByAddress(bytes);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return netAddr.getHostAddress();
    }

    public static int networkMaskFromInetAddress(InetAddress address) {
        String addr = address.getHostAddress();
        String[] Ip = addr.split("\\.");
        int result = 0;
        for (String i : Ip) {
            int val = Integer.valueOf(i);
            switch (val) {
                case 0:
                    return result;
                case 128:
                    result += 1;
                    break;
                case 192:
                    result += 2;
                    break;
                case 224:
                    result += 3;
                    break;
                case 240:
                    result += 4;
                    break;
                case 248:
                    result += 5;
                    break;
                case 252:
                    result += 6;
                    break;
                case 254:
                    result += 7;
                    break;
                case 255:
                    result += 8;
            }
        }
        return result;
    }
}
