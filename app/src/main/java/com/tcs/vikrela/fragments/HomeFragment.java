package com.tcs.vikrela.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.tcs.vikrela.R;
import com.tcs.vikrela.util.Location;
import com.tcs.vikrela.util.MyLocationListener;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements com.android.datetimepicker.date.DatePickerDialog.OnDateSetListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    TextView txtDate, txtArea;
    //private LocationManager manager = null;
    private MyLocationListener listener;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        populate();
        return view;
    }

    private void populate() {
        txtDate = (TextView) toolbar.findViewById(R.id.txtDate);
        //listener = new MyLocationListener(txtArea);
        txtArea = (TextView) toolbar.findViewById(R.id.txtArea);
        //manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setHasOptionsMenu(true);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);

        android.location.Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();
            Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String _Location = listAddresses.get(0).getAddressLine(0);
                //    txtArea.setText(_Location);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //listener = new MyLocationListener(txtArea);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            txtArea.setEnabled(false);
            return;
        }
        //manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, listener);
        final Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);
        //txtDate.setText(new StringBuilder().append(dd).append("/").append(mm + 1).append("/").append(yy));

        /*txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(HomeFragment.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                long currentTime = new Date().getTime();

                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });*/

        com.tcs.vikrela.adapters.PagerAdapter pagerAdapter = new com.tcs.vikrela.adapters.PagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
    /*    String dateString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        Date now = new Date();
        now = getDateWithOutTime(now);
        Date date = gregorianCalendar.getTime();
        if (date.compareTo(now) == 1) {
            Snackbar.show(getActivity(), getResources().getString(R.string.invalid_date));
            return;
        }
        txtDate.setText(dateString);*/
    }

    private Date getDateWithOutTime(Date targetDate) {
        Calendar newDate = Calendar.getInstance();
        newDate.setLenient(false);
        newDate.setTime(targetDate);
        newDate.set(Calendar.HOUR_OF_DAY, 0);
        newDate.set(Calendar.MINUTE, 0);
        newDate.set(Calendar.SECOND, 0);
        newDate.set(Calendar.MILLISECOND, 0);

        return newDate.getTime();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

        }
        return super.onOptionsItemSelected(item);
    }
}

