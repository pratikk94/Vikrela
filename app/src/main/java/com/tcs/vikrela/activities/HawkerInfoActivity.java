package com.tcs.vikrela.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harsh on 4/9/2017.
 */
public class HawkerInfoActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btnShareHawkerInfo)
    Button btnShareHawkerInfo;
    @Bind(R.id.btnViewRecords)
    Button btnViewRecords;
    String id  = new String();
    TextView txtNameVal,txtMobileVal,txtTypeVal,txtPeriod,txtStreetVal,txtAddressVal,txtDOBVal,txtGenderVal,txtSelectCityVal,txtHandicapped,txtCasteVal;

    String TAG = "HawkerInfo";

    @OnClick(R.id.btnShareHawkerInfo)
    void share(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
    @OnClick(R.id.btnViewRecords)
    void records(){
        startActivity(new Intent(this, RecordsActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hawker_info);
        txtNameVal = (TextView) findViewById(R.id.txtNameVal);
        txtMobileVal = (TextView) findViewById(R.id.txtMobileVal);
        txtTypeVal = (TextView) findViewById(R.id.txtTypeVal);
        txtDOBVal = (TextView) findViewById(R.id.txtDOBVal);
        txtGenderVal = (TextView) findViewById(R.id.txtGenderVal);
        txtSelectCityVal = (TextView) findViewById(R.id.txtSelectCityVal);
        txtHandicapped = (TextView) findViewById(R.id.txtHandicappedVal);
        txtCasteVal = (TextView) findViewById(R.id.txtCasteVal);
        txtPeriod = (TextView) findViewById(R.id.txtDOBVal);
        txtStreetVal = (TextView) findViewById(R.id.txtGenderVal);
        txtAddressVal = (TextView) findViewById(R.id.txtSelectCityVal);
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("URL");
        StringTokenizer str = new StringTokenizer(url,"id=");

        while(str.hasMoreElements()){
            id = str.nextElement().toString();
        }
        Toast.makeText(this,id,Toast.LENGTH_LONG).show();
        populate(id);
    }

    private void populate(final String id) {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hawker Information");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StringRequest jsonReq = new StringRequest(Request.Method.POST,
                "http://v16he2v4.esy.es/android/retriveHawkerData.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    parseJsonFeed(jsonObject);
                } catch (JSONException e) {
                    Log.d(TAG, "Catch" + e.getMessage());
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //if(bundle!=null)
                params.put("id",id);
                //else
                //    Log.d("Bundle","Bundle is null");
                return params;
            }
        };



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonReq);


    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
                txtNameVal.setText(feedObj.getString("name"));
                txtMobileVal.setText(feedObj.getString("mobile"));
                txtTypeVal.setText(feedObj.getString("type"));
                txtHandicapped.setText(feedObj.getString("handicap"));
                txtCasteVal.setText(feedObj.getString("caste"));
                txtPeriod.setText(feedObj.getString("period"));
                txtGenderVal.setText(feedObj.getString("street_name"));
                txtAddressVal.setText(feedObj.getString("business_place"));

            }

            if(feedArray.length()==0){
                setContentView(R.layout.register_a_hawker);
                Button register = (Button) findViewById(R.id.btn_register_user);
                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),RegisterAHawker.class));
                    }
                });
            }
            // notify data changes to list adapater
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
