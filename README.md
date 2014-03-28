README
===

A library to manage Network IP, Mask, Default Gateway and DNS Server on Android.

From API 11 (Android 3.0 Honeycomb) the existing API to do this was deprecated and stopped working.

# To use
- Export the library as **JAR**;
- Declare in the manifest the following permissions:

		<!-- Necessary for Android after gingerbread < -->
		<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /> 
		<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
		<!-- Necessary for Android pre Honeycomb -->
		<uses-permission android:name="android.permission.WRITE_SETTINGS" />

- Use the `IPManagerFactory.getIPManager(Context context)` to get a `IPManager`;
