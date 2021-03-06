README
===

A library to manage Network IP, Mask, Default Gateway and DNS Server on Android.

From API 11 (Android 3.0 Honeycomb) the existing API to do this was deprecated and stopped working.

# Repo Structure

		.
		+-- Lib: the library
		|
		+-- Example App: one application that uses the Lib, works on all the Android tested versions mention bellow

Note: *For now the Exemple App needs appcompat_v7 from Support libs.*

# To use
- Export the library as **JAR**;
- Declare in the manifest the following permissions:

		<!-- Necessary for Android after gingerbread < -->
		<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /> 
		<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
		<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
		<!-- Necessary for Android pre Honeycomb -->
		<uses-permission android:name="android.permission.WRITE_SETTINGS" />

- Use the `IPManagerFactory.getIPManager(Context context)` to get a `IPManager`;

# Tested on
- Android 2.3
- Android 4.1.2
- Android 4.2.2
- Android 4.4.2

# License

This project is license under GNU General Public License v2.0 and the full license can be found at [here](LICENSE).
