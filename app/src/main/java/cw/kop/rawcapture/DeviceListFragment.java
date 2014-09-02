package cw.kop.rawcapture;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cw.kop.rawcapture.api.CameraDevice;
import cw.kop.rawcapture.api.DeviceListAdapter;
import cw.kop.rawcapture.api.SsdpClient;

/**
 * Created by TheKeeperOfPie on 8/27/2014.
 */
public class DeviceListFragment extends Fragment {

    private Context context;
    private DeviceListAdapter deviceListAdapter;
    private WifiManager wifiManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deviceListAdapter = new DeviceListAdapter(context);
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.device_list, container);

        TextView emptyText = new TextView(context);
        emptyText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        emptyText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyText.setGravity(Gravity.CENTER_HORIZONTAL);
        emptyText.setText("No devices found. \n" +
                "Please try rescanning.");

        ListView deviceList = (ListView) view.findViewById(R.id.device_list);
//        container.addView(emptyText, 0);
//        ((ViewGroup) deviceList.getParent()).addView(emptyText);
        deviceList.setEmptyView(emptyText);
        deviceList.setAdapter(deviceListAdapter);
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                wifiManager.setWifiEnabled(true);
                int netId = -1;

                WifiConfiguration wifiConfiguration = getWifiConfigurationFromSSID((deviceListAdapter.getItem(position)).SSID);
                if (wifiConfiguration == null) {
                    showPasswordDialog(deviceListAdapter.getItem(position));
                }
                else {
                    connectToNetwork(wifiConfiguration.networkId);
                }

            }
        });

        Button scanButton = (Button) view.findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanForDevices();
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private BroadcastReceiver scanResultsBroadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            List<ScanResult> cameraScanResults = new ArrayList<ScanResult>();
            for(ScanResult sr : wifiManager.getScanResults()) {

                Log.i("DLF", sr.SSID);

                if(!isSonyCameraSSID(sr.SSID)) {
                    continue;
                }

                cameraScanResults.add(sr);
            }

            if (cameraScanResults.size() > 0) {
                for (ScanResult scanResult : cameraScanResults) {
                    deviceListAdapter.add(scanResult);
                }
            }
            else {
                Log.i("DLF", "No device WiFi connections found");
            }

            context.unregisterReceiver(scanResultsBroadcastReceiver);
        }


    };

    private static boolean isSonyCameraSSID(String ssid) {
        return ssid != null && ssid.matches("^DIRECT-\\w{4}:.*$");
    }

    private WifiConfiguration getWifiConfigurationFromSSID(String SSID) {

        List<WifiConfiguration> knownNetworks = wifiManager.getConfiguredNetworks();

        if(knownNetworks == null) {
            return null;
        }

        for(WifiConfiguration net : knownNetworks) {
            if(net.SSID.equals("\""+SSID+"\"")) {
                return net;
            }
        }

        return null;
    }

    private void showPasswordDialog(final ScanResult scanResult) {

        final EditText input = new EditText(getActivity());

        AlertDialog passwordDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Enter password for " + scanResult.SSID)
                .setView(input)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                String value = input.getText().toString();
                                createIfNeededThenConnectToWifi(scanResult.SSID, value);

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Do nothing.
                            }
                        }).show();

    }

    public void createIfNeededThenConnectToWifi(String ssid, String password) {
        int netId = -1;

        List<WifiConfiguration> configurations = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration configuration : configurations) {

            // In the case of network is already registered
            if(configuration.SSID != null && configuration.SSID.equals("\"" + ssid + "\"")) {

                // In case password changed since last time
                configuration.preSharedKey = "\""+ password +"\"";
                wifiManager.saveConfiguration();

                netId = configuration.networkId;
                break;
            }
        }

        // In the case of network is not registered create it and join it
        if (netId == -1) {
            netId = addNetwork(ssid, password);
        }

        connectToNetwork(netId);
    }

    private int addNetwork(String ssid, String password) {

        WifiConfiguration wc = new WifiConfiguration();
        wc.SSID = "\"" + ssid + "\"";
        wc.preSharedKey = "\""+ password +"\"";

        wc.hiddenSSID = true;
        wc.status = WifiConfiguration.Status.ENABLED;
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);


        int netId = wifiManager.addNetwork(wc);
        wifiManager.saveConfiguration();

        return netId;
    }

    public void connectToNetwork(int netId) {

        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration wc : configuredNetworks) {
            if(wc.networkId == netId) {
                wifiManager.enableNetwork(netId, true);

                SsdpClient ssdpClient = new SsdpClient();

                ssdpClient.search(new SsdpClient.SearchResultHandler() {

                    private boolean deviceFound = false;

                    @Override
                    public void onDeviceFound(CameraDevice device) {

                        deviceFound = true;
                        AppSettings.setCameraDevice(device);

                    }

                    @Override
                    public void onFinished() {

                        if (deviceFound) {
                            Toast.makeText(context, AppSettings.getCameraFriendlyName() + " connected", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(context, "Error connecting device", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onErrorFinished() {

                    }
                });
            }
        }
    }

    private boolean scanForDevices() {

        deviceListAdapter.clear();

        context.registerReceiver(scanResultsBroadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();

        Toast.makeText(context, "Scanning...", Toast.LENGTH_SHORT).show();

        return true;
    }



}
