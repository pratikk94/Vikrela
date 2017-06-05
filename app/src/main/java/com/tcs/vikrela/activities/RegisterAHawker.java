package com.tcs.vikrela.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static android.R.attr.bitmap;

public class RegisterAHawker extends AppCompatActivity implements View.OnClickListener{

    private ImageView profilePicture;
    private TextInputLayout firstname;
    private TextInputLayout permenantAddress;
    private TextInputLayout mobileNumber;
    private TextInputLayout period;
    private TextInputLayout timeFrom;
    private TextInputLayout timeTo;
    private Button btn;
    private String string_firstName;
    private String string_permenantAddress;
    private String string_mobileNumber;
    private String string_period;
    private String string_timeFrom;
    private String string_timeTo;
    private String Gender;
    private String handicap;
    private String style;
    private String resedentialtype;
    private String idType;
    private String castetype;
    private String buisnessTypeType;
    private Bitmap bitmap;
    private ProgressDialog loading;
    private List<String> listOfExistingUsername;
    private int PICK_IMAGE_REQUEST=1;
    private RadioButton btn_male,btn_female,btn_widow,btn_widower,handicap_yes,handicap_no,style_fixed,style_not_fixed;
    private Spinner resedential_address_proof,id_proof,caste,buisnessType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ahawker);
        profilePicture = (ImageView) findViewById(R.id.registration_profile_picture_while_sign_up);

        firstname = (TextInputLayout) findViewById(R.id.input_layout_first_name);
        permenantAddress = (TextInputLayout) findViewById(R.id.input_layout_complete_address);
        mobileNumber = (TextInputLayout) findViewById(R.id.input_layout_mobile_number);
        period =  (TextInputLayout) findViewById(R.id.input_layout_period);
        timeFrom = (TextInputLayout) findViewById(R.id.input_layout_time_from_);
        timeTo = (TextInputLayout) findViewById(R.id.input_layout_time_to);

        resedential_address_proof = (Spinner) findViewById(R.id.spinner_for_resedential_address);
        id_proof = (Spinner) findViewById(R.id.spinner_for_id_proof);
        caste = (Spinner )findViewById(R.id.spinner_for_caste);
        buisnessType = (Spinner) findViewById(R.id.spinner_for_buisness_type);


        btn_male = (RadioButton) findViewById(R.id.Male);
        btn_female = (RadioButton) findViewById(R.id.Female);
        btn_widow = (RadioButton) findViewById(R.id.Widow);
        btn_widower = (RadioButton) findViewById(R.id.Widower);
        handicap_yes = (RadioButton) findViewById(R.id.handicap_yes);
        handicap_no = (RadioButton) findViewById(R.id.handicap_no);
        style_fixed = (RadioButton)findViewById(R.id.style_fixed);
        style_not_fixed = (RadioButton) findViewById(R.id.style_not_fixed);
        listOfExistingUsername = new ArrayList<String>();


        firstname.getEditText().addTextChangedListener(new MyTextWatcher(firstname));
        permenantAddress.getEditText().addTextChangedListener(new MyTextWatcher(permenantAddress));
        mobileNumber.getEditText().addTextChangedListener(new MyTextWatcher(mobileNumber));
        period.getEditText().addTextChangedListener(new MyTextWatcher(period));
//        timeFrom.getEditText().addTextChangedListener(new MyTextWatcher(timeFrom));
//        timeTo.getEditText().addTextChangedListener(new MyTextWatcher(timeTo));


//        getData();

        btn = (Button) findViewById(R.id.btn_sign_up);
        profilePicture.setOnClickListener(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                string_firstName = firstname.getEditText().getText().toString();
                string_permenantAddress = permenantAddress.getEditText().getText().toString();
                string_mobileNumber = mobileNumber.getEditText().getText().toString();
                string_period = firstname.getEditText().getText().toString();
                string_timeFrom = permenantAddress.getEditText().getText().toString();
                string_timeTo = mobileNumber.getEditText().getText().toString();
                if(btn_male.isSelected()){
                    Gender = "male";
                }
                if(btn_female.isSelected()){
                    Gender = "female";
                }
                if(btn_widow.isSelected()){
                    Gender = "widow";
                }
                if(btn_widower.isSelected()){
                    Gender = "widower";
                }
                if (handicap_yes.isSelected()){
                    handicap="yes";
                }
                if(handicap_no.isSelected()){
                    handicap="no";
                }
                if(style_fixed.isSelected()){
                    style="fixed";
                }
                if(style_not_fixed.isSelected()){
                    style="not Fixed";
                }

                resedentialtype=resedential_address_proof.getSelectedItem().toString();
                idType=id_proof.getSelectedItem().toString();
                castetype=caste.getSelectedItem().toString();
                buisnessTypeType = buisnessType.getSelectedItem().toString();

                if (!validateFirstName()) {
                    return;
                }

                if (!validateAddress()) {
                    return;
                }

                if (!validateMobileNo()) {
                    return;
                }

                if (!validatePeriod()) {
                    return;
                }
                if (!validateTimeFrom()) {
                    return;
                }
                if (!validateTimeto()) {
                    return;
                }

                uploadImage();


            }



        });

    }


    private void getData() {
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = "";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                getJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private List<String> getJSON(String response){
        String username="";
        listOfExistingUsername = new ArrayList<String>();
        /*try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            Toast.makeText(getApplicationContext(),String.valueOf(result.length()),Toast.LENGTH_LONG).show();
            for(int i=0;i<result.length();i++) {
                JSONObject collegeData = result.getJSONObject(i);
                username = collegeData.getString(Config.KEY_NAME);
                username.trim();
                listOfExistingUsername.add(username);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
        return listOfExistingUsername;
    }

    private boolean validateFirstName() {
        if (firstname.getEditText().getText().toString().trim().isEmpty()) {
            firstname.setError("Enter your name");
            requestFocus(firstname.getEditText());
            return false;
        } else {
            firstname.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateAddress() {
        if (permenantAddress.getEditText().getText().toString().trim().isEmpty()) {
            permenantAddress.setError("Enter your permanent  address");
            requestFocus(permenantAddress.getEditText());
            return false;
        } else {
            permenantAddress.setErrorEnabled(false);

        }

        return true;
    }


    private boolean validatePeriod() {
        if (period.getEditText().getText().toString().trim().isEmpty()) {
            period.setError("Enter your period");
            requestFocus(period.getEditText());
            return false;
        } else {
            period.setErrorEnabled(false);

        }

        return true;
    }


    private boolean validateTimeFrom() {
        if (timeFrom.getEditText().getText().toString().trim().isEmpty()) {
            timeFrom.setError("Enter your time from");
            requestFocus(timeFrom.getEditText());
            return false;
        } else {
            timeFrom.setErrorEnabled(false);

        }

        return true;
    }


    private boolean validateTimeto() {
        if (timeTo.getEditText().getText().toString().trim().isEmpty()) {
            timeTo.setError("Enter your time to");
            requestFocus(timeTo.getEditText());
            return false;
        } else {
            timeTo.setErrorEnabled(false);

        }

        return true;
    }


    private boolean validateMobileNo() {
        if (mobileNumber.getEditText().getText().toString().trim().isEmpty()) {
            mobileNumber.setError("Enter mobile number");
            requestFocus(mobileNumber.getEditText());
            return false;
        } else {
            mobileNumber.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
//        verifyStoragePermissions(this);
//        showFileChooser();
            takeImageFromCamera(v);
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_layout_first_name:
                    validateFirstName();
                    break;
                case R.id.input_layout_complete_address:
                    validateAddress();
                    break;
                case R.id.input_layout_mobile_number:
                    validateMobileNo();
                    break;
            }
        }
    }




    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    private void uploadImage(){
        //Showing the progress dialogU
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://kkwagheducationsocietyalumni.in/AlumniApp/Registration/createAnAlumni.php",new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                //Disimissing the progress dialog
                loading.dismiss();
                //Showing toast message of the response
                Log.d("Response++",s);
                Toast.makeText(RegisterAHawker.this,"In resonsee =>"+s,Toast.LENGTH_LONG).show();
                Intent i = new Intent(RegisterAHawker.this,NMCDashboardActivity.class);
                i.putExtra("alumni_id",s);
                startActivity(i);
                finish();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        Log.d("VolleyError","Error is "+volleyError.getMessage());
                        Toast.makeText(RegisterAHawker.this,"In error =>"+volleyError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();
            //    Log.d("Image",getStringImage(bitmap));
                //Adding parameters
            //    params.put("alumni_profile_picture",getStringImage(bitmap));
                params.put("alumni_name",string_firstName);
                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterAHawker.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
/*
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                bitmap = getResizedBitmap(bitmap, 200);// 100 is for example, replace with desired size
//Setting the Bitmap to ImageView
                profilePicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

  */  public void takeImageFromCamera(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1000);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            profilePicture.setImageBitmap(mphoto);
        }
    }

}


