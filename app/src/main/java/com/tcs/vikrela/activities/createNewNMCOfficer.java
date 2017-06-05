package com.tcs.vikrela.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tcs.vikrela.R;
import com.tcs.vikrela.fragments.HomeFragment;
import com.tcs.vikrela.util.GPSTracker;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Hashtable;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Bind;

public class createNewNMCOfficer extends AppCompatActivity {
    private static final String TAG = "createNewNMCOfficer";

    @Bind(R.id.input_FirstName) EditText _FirstNameText;
    @Bind(R.id.input_LastName) EditText _LastNameText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_mobile) EditText _mobileText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;
    @Bind(R.id.input_zone) EditText _zone;
    @Bind(R.id.input_ward) EditText _ward;
    @Bind(R.id.input_designation) EditText _designation;

    String[] SPINNERLIST = {"Nasik", "Mumbai", "Pune", "Aurangabad"};
    GPSTracker gps;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_nmcofficer);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.android_material_design_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);

        gps = new GPSTracker(createNewNMCOfficer.this);
        Toast.makeText(getApplicationContext(),String.valueOf(gps.showSettingsAlert(getContentResolver(),getApplicationContext())),Toast.LENGTH_LONG).show();

    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(createNewNMCOfficer.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String firstname = _FirstNameText.getText().toString();
        String lastname = _LastNameText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        addAnAccount();
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name =_FirstNameText.getText().toString();
        String address = _LastNameText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _FirstNameText.setError("at least 3 characters");
            valid = false;
        } else {
            _FirstNameText.setError(null);
        }

        if (address.isEmpty()) {
            _LastNameText.setError("Enter Valid Address");
            valid = false;
        } else {
            _LastNameText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        return valid;
    }


    public void addAnAccount(){
        //Showing the progress dialogU
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://kkwagheducationsocietyalumni.in/newNMCOfficer.php",new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                //Showing toast message of the response
                Log.d("Response++",s);
                Intent i = new Intent(getApplicationContext(),PreferencesActivity.class);
                i.putExtra("alumni_id",s);
                startActivity(i);
                finish();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        Log.d("VolleyError","Error is "+volleyError.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();
                params.put("first_name",_FirstNameText.getText().toString());
                params.put("last_name",_LastNameText.getText().toString());
                params.put("email",_emailText.getText().toString());
                params.put("mobile_number",_mobileText.getText().toString());
                params.put("password","a");
                params.put("zone",_zone.getText().toString());
                params.put("ward",_ward.getText().toString());
                params.put("designation",_designation.getText().toString());
                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(createNewNMCOfficer.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }



}