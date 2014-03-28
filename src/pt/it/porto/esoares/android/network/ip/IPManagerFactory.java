
package pt.it.porto.esoares.android.network.ip;

import android.content.Context;
import android.os.Build;

public final class IPManagerFactory {

    public static IPManager getIPManager(Context context) {
        if (Build.VERSION_CODES.GINGERBREAD_MR1 >= android.os.Build.VERSION.SDK_INT) {
            return new IPManagerPreHoneycomb(context);
        } else {
            return new IPManagerKitKat(context);
        }
    }
}
