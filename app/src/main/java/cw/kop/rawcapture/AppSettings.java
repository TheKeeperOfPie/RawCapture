package cw.kop.rawcapture;

import android.content.SharedPreferences;

import cw.kop.rawcapture.api.CameraDevice;

/**
 * Created by TheKeeperOfPie on 9/1/2014.
 *
 */

public class AppSettings {

    private static SharedPreferences prefs;
    private static CameraDevice cameraDevice = null;

    public static void setSharedPreferences(SharedPreferences preferences) {
        prefs = preferences;
    }

    public static void setCameraDevice(CameraDevice device) {
        cameraDevice = device;
    }

    public static String getCameraDdUrl() {
        return cameraDevice.getDdUIrl();
    }

    public static String getCameraFriendlyName() {
        return cameraDevice.getFriendlyName();
    }

    public static String getModelName() {
        return cameraDevice.getModelName();
    }

    public static String getUdn() {
        return cameraDevice.getUdn();
    }

    public static String getCameraActionUrl() {
        return cameraDevice.getActionUrl();
    }

    public static String getCameraSSID() {
        return cameraDevice.getCameraSsid();
    }

    public static int getTimelapseDelay() {
        return Integer.parseInt(prefs.getString("initial_delay", "10000"));
    }

    public static int getTimelapseInterval() {
        return Integer.parseInt(prefs.getString("timelapse_interval", "1000"));
    }

    public static boolean forceTimelapseInterval() {
        return prefs.getBoolean("force_interval", true);
    }

}
