package pt.it.porto.esoares.android.network.ip;

import android.util.Log;

public class LogMe {
	public static final String TAG = "pt.it.esoares.ipTest";

	public static void log(String log) {
		Log.d(TAG, log);
	}

	public static void error(String string) {
		Log.e(TAG, string);
	}

}
