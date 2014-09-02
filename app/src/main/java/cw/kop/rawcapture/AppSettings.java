package cw.kop.rawcapture;

import cw.kop.rawcapture.api.CameraDevice;

/**
 * Created by TheKeeperOfPie on 9/1/2014.
 *
 *
 */

// Test Edit

public class AppSettings {

    private static CameraDevice cameraDevice = null;

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

}
