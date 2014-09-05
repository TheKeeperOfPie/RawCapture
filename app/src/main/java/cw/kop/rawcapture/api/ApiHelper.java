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
     * Calls getAvailableApiList API to the target server. Request JSON data is
     * such like as below.
     *
     * <pre>
     * {
     *   "method": "getAvailableApiList",
     *   "params": [""],
     *   "id": 2,
     *   "version": "1.0"
     * }
     * </pre>
     *
     * @return JSON data of response
     */
    public JSONObject getAvailableApiList() throws IOException {
        String service = "camera";
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "getAvailableApiList")
                    .put("params", new JSONArray())
                    .put("id", requestId++)
                    .put("version", "1.0");
            String url = AppSettings.getCameraActionUrl() + "/" + service;

            Log.i(TAG, "Request:  " + requestJson.toString());
            String responseJson = HttpConnector.httpPost(url, requestJson.toString());
            Log.i(TAG,"Response: " + responseJson);
            return new JSONObject(responseJson);
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }

    /**
     * Calls getApplicationInfo API to the target server. Request JSON data is
     * such like as below.
     *
     * <pre>
     * {
     *   "method": "getApplicationInfo",
     *   "params": [""],
     *   "id": 2,
     *   "version": "1.0"
     * }
     * </pre>
     *
     * @return JSON data of response
     */
    public JSONObject getApplicationInfo() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "getApplicationInfo")
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
     * Calls startRecMode API to the target server. Request JSON data is such
     * like as below.
     *
     * <pre>
     * {
     *   "method": "startRecMode",
     *   "params": [],
     *   "id": 2,
     *   "version": "1.0"
     * }
     * </pre>
     *
     * @return JSON data of response
     */
    public JSONObject startRecMode() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "startRecMode")
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

    public JSONObject getSupportedIsoSpeedRate() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "getSupportedIsoSpeedRate")
                    .put("params", new JSONArray())
                    .put("id", requestId++)
                    .put("version", "1.0");
            String url = AppSettings.getCameraActionUrl() + "/" + CAMERA_SERVICE;

            Log.i(TAG,"Request:  " + requestJson.toString());
            String responseJson = HttpConnector.httpPost(url, requestJson.toString());
            Log.i(TAG, "Response: " + responseJson);
            return new JSONObject(responseJson);
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }

    /**
     *
     * @param mode still, movie, audio, intervalstill
     * @return response
     * @throws IOException
     */
    public JSONObject setShootMode(String mode) throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "setShootMode")
                    .put("params", new JSONArray().put(mode))
                    .put("id", requestId++)
                    .put("version", "1.0");
            String url = AppSettings.getCameraActionUrl() + "/" + CAMERA_SERVICE;

            Log.i(TAG,"Request:  " + requestJson.toString());
            String responseJson = HttpConnector.httpPost(url, requestJson.toString());
            Log.i(TAG, "Response: " + responseJson);
            return new JSONObject(responseJson);
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }

    public JSONObject getShootMode() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "getShootMode")
                    .put("params", new JSONArray())
                    .put("id", requestId++)
                    .put("version", "1.0");
            String url = AppSettings.getCameraActionUrl() + "/" + CAMERA_SERVICE;

            Log.i(TAG,"Request:  " + requestJson.toString());
            String responseJson = HttpConnector.httpPost(url, requestJson.toString());
            Log.i(TAG, "Response: " + responseJson);
            return new JSONObject(responseJson);
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }

    public JSONObject getSupportedShootMode() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "getSupportedShootMode")
                    .put("params", new JSONArray())
                    .put("id", requestId++)
                    .put("version", "1.0");
            String url = AppSettings.getCameraActionUrl() + "/" + CAMERA_SERVICE;

            Log.i(TAG,"Request:  " + requestJson.toString());
            String responseJson = HttpConnector.httpPost(url, requestJson.toString());
            Log.i(TAG, "Response: " + responseJson);
            return new JSONObject(responseJson);
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }

    /**
     *
     * @return string current mode, string-array available shoot modes
     * @throws IOException
     */
    public JSONObject getAvailableShootMode() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "getAvailableShootMode")
                    .put("params", new JSONArray())
                    .put("id", requestId++)
                    .put("version", "1.0");
            String url = AppSettings.getCameraActionUrl() + "/" + CAMERA_SERVICE;

            Log.i(TAG,"Request:  " + requestJson.toString());
            String responseJson = HttpConnector.httpPost(url, requestJson.toString());
            Log.i(TAG, "Response: " + responseJson);
            return new JSONObject(responseJson);
        } catch (JSONException e) {
            throw new IOException(e);
        }
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
     * @return JSON data of response, 40403 error on camera busy
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

    public JSONObject awaitTakePicture() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "awaitTakePicture")
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

    public JSONObject startMovieRec() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "startMovieRec")
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

    public JSONObject stopMovieRec() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "stopMovieRec")
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

    public JSONObject startAudioRec() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "startAudioRec")
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

    public JSONObject stopAudioRec() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "stopAudioRec")
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

    public JSONObject startIntervalStillRec() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "startIntervalStillRec")
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

    public JSONObject stopIntervalStillRec() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "stopIntervalStillRec")
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

    public JSONObject stopLiveview() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "stopLiveview")
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

    /**
     *
     * @param size L (XGA), M (VGA)
     * @return
     * @throws IOException
     */
    public JSONObject startLiveviewWithSize(String size) throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "startLiveviewWithSize")
                    .put("params", new JSONArray().put(size))
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

    public JSONObject getLiveviewSize() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "getLiveviewSize")
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

    public JSONObject getSupportedLiveviewSize() throws IOException {
        try {
            JSONObject requestJson = new JSONObject()
                    .put("method", "getSupportedLiveviewSize")
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

    

}
