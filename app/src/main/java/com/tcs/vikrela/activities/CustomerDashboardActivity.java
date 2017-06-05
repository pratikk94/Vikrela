package com.tcs.vikrela.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tcs.vikrela.R;

import butterknife.ButterKnife;

/**
 * Created by Harsh on 4/6/2017.
 */

public class CustomerDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_customer);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);
    }
}
