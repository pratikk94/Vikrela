package com.tcs.vikrela.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tcs.vikrela.R;
import com.tcs.vikrela.activities.HawkerInfoActivity;
import com.tcs.vikrela.activities.ScanQRCodeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HawkerFragment extends Fragment {


    @Bind(R.id.etLicenseNumber)
    EditText etLicenseNumber;
    @Bind(R.id.btnEnter)
    Button btnEnter;
    @Bind(R.id.btnScanQR)
    Button btnScanQR;

    @OnClick(R.id.btnScanQR)
    void scanQR(){
        startActivity(new Intent(getActivity(), ScanQRCodeActivity.class));
    }

    @OnClick(R.id.btnEnter)
    void info(){
        startActivity(new Intent(getActivity(), HawkerInfoActivity.class));
    }

    public HawkerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hawker, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
