package cw.kop.rawcapture;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by TheKeeperOfPie on 9/9/2014.
 */
public class TimelapseFragment extends PreferenceFragment {

    private Context appContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_timelapse);
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
    }

    private boolean startTimelapse() {
        appContext.startService(new Intent(appContext, TimelapseService.class));
        return true;
    }

    private boolean stopTimelapse() {
        appContext.stopService(new Intent(appContext, TimelapseService.class));
        return true;
    }

}