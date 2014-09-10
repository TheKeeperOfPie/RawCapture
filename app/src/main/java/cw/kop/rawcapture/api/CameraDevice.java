package cw.kop.rawcapture.api;

/**
 * Created by TheKeeperOfPie on 8/31/2014.
 */
public class CameraDevice {

    private String ssid;
    private String ddUrl;
    private String friendlyName;
    private String modelName;
    private String udn;
    private String actionUrl;

    public CameraDevice() {

    }
    
    public void setSsid(String name) {
        ssid = name;
    }
    
    public void setDdUIrl(String url) {
        ddUrl = url;
    }

    public void setFriendlyName(String name) {
        friendlyName = name;
    }

    public void setModelName(String name) {
        modelName = name;
    }

    public void setUdn(String udn) {
        this.udn = udn;
    }

    public void setActionUrl(String url) {
        actionUrl = url;
    }

    public String getCameraSsid() {
        return ssid;
    }
    
    public String getDdUIrl() {
        return ddUrl;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getUdn() {
        return udn;
    }

    public String getActionUrl() {
        return actionUrl;
    }
}
