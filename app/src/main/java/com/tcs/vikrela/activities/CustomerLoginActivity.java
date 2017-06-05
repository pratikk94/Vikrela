package com.tcs.vikrela.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tcs.vikrela.R;
import com.tcs.vikrela.ui.Snackbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harsh on 4/6/2017.
 */
public class CustomerLoginActivity extends AppCompatActivity {

    @Bind(R.id.txtName)
    EditText etName;
    @Bind(R.id.txtMobile)
    EditText etMobile;
    @Bind(R.id.etPincode)
    EditText etPincode;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.txtSkipLogin)
    TextView txtSkipLogin;

    @OnClick(R.id.btnLogin)
    void login(){
        if (etName.getText().toString().equals("")){
            Snackbar.show(this, "No Fields should be empty!!");
            return;
        }
        if (etMobile.getText().toString().equals("")){
            Snackbar.show(this, "No Fields should be empty!!");
            return;
        }
        if (etPincode.getText().toString().equals("")){
            Snackbar.show(this, "No Fields should be empty!!");
            return;
        }
        if (!(etMobile.getText().length() == 10)){
            Snackbar.show(this, "Invalid Mobile!!");
            return;
        }
        if (!(etPincode.getText().length() == 6)){
            Snackbar.show(this, "Invalid PinCode!!");
            return;
        }
        else {
            startActivity(new Intent(this, CustomerDashboardActivity.class));
        }
    }

    @OnClick(R.id.txtSkipLogin)
    void skipLogin(){
        startActivity(new Intent(this, CustomerDashboardActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.customer_details));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnLogin.setBackgroundResource(R.drawable.ripple);
        }
        txtSkipLogin.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }
}
