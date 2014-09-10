package cw.kop.rawcapture;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cw.kop.rawcapture.api.ApiHelper;

/**
 * Created by TheKeeperOfPie on 9/9/2014.
 */
public class TimelapseService extends Service {

    private ScheduledThreadPoolExecutor executor;
    private Runnable timelapseRunnable;
    private Handler handler;
    private String url = null;

    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiNetwork = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

            if (!wifiNetwork.isConnected() || wifiManager.getConnectionInfo().getSSID() != AppSettings.getCameraSSID()) {
                stopSelf();
            }

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        executor = new ScheduledThreadPoolExecutor(1);
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        url = AppSettings.getCameraActionUrl();
//        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
        timelapseRunnable = new Runnable() {

            private ApiHelper apiHelper = new ApiHelper();

            @Override
            public void run() {
                testToast();
                Log.i("TS", "Test shot");
//                try {
//                    apiHelper.actTakePicture();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        };
        if (AppSettings.forceTimelapseInterval()) {
            executor.scheduleAtFixedRate(timelapseRunnable, AppSettings.getTimelapseDelay(), AppSettings.getTimelapseInterval(), TimeUnit.MILLISECONDS);
            Log.i("TS", "scheduleAtFixedRate");
        }
        else {
            executor.scheduleWithFixedDelay(timelapseRunnable, AppSettings.getTimelapseDelay(), AppSettings.getTimelapseInterval(), TimeUnit.MILLISECONDS);
            Log.i("TS", "scheduleWithFixedDelay");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        executor.shutdownNow();
        Log.i("TS", "Timelapse service destroyed");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void testToast() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TimelapseService.this, "Timelapse shot" + System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}