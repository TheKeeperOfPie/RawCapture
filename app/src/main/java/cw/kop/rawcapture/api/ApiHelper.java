package cw.kop.rawcapture.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cw.kop.rawcapture.AppSettings;

/**
 * Created by TheKeeperOfPie on 8/27/2014.
 */
public class ApiHelper {

    private static final String TAG = ApiHelper.class.getSimpleName();

    private static final String CAMERA_SERVICE = "camera";

    private int requestId;
    private JSONArray availableApis;

    public ApiHelper() {
        requestId = 1;
    }

    public boolean checkForConnection() {




        return false;
    }


    /**
     * Calls actTakePicture API to the target server. Request JSON data is such
     * like as below.
     *
     * <pre>
     * {
     *   "method": "actTakePicture",
     *   "params": [],
     *   "id": 2,
     *   "version": "1.0"
     * }
     * </pre>
     *
     * @return JSON data of response
     */
    public JSONObject actTakePicture() throws IOException {

        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "actTakePicture")
                    .put("params", new JSONArray())
                    .put("id", requestId++)
                    .put("version", "1.0");
            String url = AppSettings.getCameraActionUrl() + "/" + CAMERA_SERVICE;

            Log.i(TAG, "Request:  " + requestJson.toString());
            String responseJson = HttpConnector.httpPost(url,
                    requestJson.toString());
            Log.i(TAG, "Response: " + responseJson);
            return new JSONObject(responseJson);
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }


    /**
     * Calls startLiveview API to the target server. Request JSON data is such
     * like as below.
     *
     * <pre>
     * {
     *   "method": "startLiveview",
     *   "params": [],
     *   "id": 2,
     *   "version": "1.0"
     * }
     * </pre>
     *
     * @return JSON data of response
     */
    public JSONObject startLiveview() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "startLiveview")
                    .put("params", new JSONArray())
                    .put("id", requestId++)
                    .put("version", "1.0");
            String url = AppSettings.getCameraActionUrl() + "/" + CAMERA_SERVICE;

            Log.i(TAG,"Request:  " + requestJson.toString());
            String responseJson = HttpConnector.httpPost(url,
                    requestJson.toString());
            Log.i(TAG, "Response: " + responseJson);
            return new JSONObject(responseJson);
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }

    // THREADING NON-SAFE

}
