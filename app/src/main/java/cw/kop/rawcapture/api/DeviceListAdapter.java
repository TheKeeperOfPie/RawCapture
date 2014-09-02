package cw.kop.rawcapture.api;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cw.kop.rawcapture.R;

/**
 * Created by TheKeeperOfPie on 8/31/2014.
 */
public class DeviceListAdapter extends BaseAdapter {

    private List<ScanResult> scanResultList;
    private LayoutInflater layoutInflater;

    public DeviceListAdapter(Context context) {
        scanResultList = new ArrayList<ScanResult>();
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return scanResultList.size();
    }

    @Override
    public ScanResult getItem(int position) {
        return scanResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.device_entry, parent, false);

        TextView titleText = (TextView) view.findViewById(R.id.device_title);

        Log.i("DLA", scanResultList.get(position).SSID);

        titleText.setText(scanResultList.get(position).SSID);

        return view;
    }

    public void add(ScanResult scanResult) {
        scanResultList.add(scanResult);
        notifyDataSetChanged();
    }

    public void clear() {
        scanResultList.clear();
        notifyDataSetChanged();
    }
}
