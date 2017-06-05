package com.tcs.vikrela.util;

import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Harsh on 4/7/2017.
 */
public class MyLocationListener implements LocationListener {
    TextView textView;

    public MyLocationListener(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        this.textView.setText(location.toString());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
