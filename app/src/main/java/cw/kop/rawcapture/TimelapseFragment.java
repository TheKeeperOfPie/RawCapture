package cw.kop.rawcapture;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by TheKeeperOfPie on 9/9/2014.
 */
public class TimelapseFragment extends PreferenceFragment {

    private Context appContext;
    private BroadcastReceiver imageBroadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_timelapse);
        imageBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                String url = extras.getString("image_url");
                if (url != null) {
                    updatePreview(url);
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_timelapse, container, false);

        Button startButton = (Button) view.findViewById(R.id.start_timelapse_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimelapse();
            }
        });

        Button stopButton = (Button) view.findViewById(R.id.stop_timelapse_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimelapse();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        appContext = activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(appContext).registerReceiver(imageBroadcastReceiver, new IntentFilter(TimelapseService.IMAGE_RECEIVED));
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(appContext).unregisterReceiver(imageBroadcastReceiver);
        super.onPause();
    }

    private boolean startTimelapse() {
        Intent startIntent = new Intent(appContext, TimelapseService.class);
        appContext.startService(startIntent);

        if (AppSettings.useTimelapsePreview()) {
            appContext.bindService(startIntent, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {

                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            }, Context.BIND_AUTO_CREATE);
        }

        return true;
    }

    private boolean stopTimelapse() {
        appContext.stopService(new Intent(appContext, TimelapseService.class));
        return true;
    }

    public void updatePreview(String url) {
        // UPDATE TIMELAPSE IMAGE PREVIEW
        Toast.makeText(appContext, url, Toast.LENGTH_SHORT).show();
    }

}