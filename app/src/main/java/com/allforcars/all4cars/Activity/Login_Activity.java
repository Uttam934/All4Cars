package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.allforcars.all4cars.Model.Catagory_Model;
import com.allforcars.all4cars.R;

import com.allforcars.all4cars.Response.Test_Response;
import com.allforcars.all4cars.Response.Test_Responsesss;
import com.allforcars.all4cars.RetroifitApi.APIService;
import com.allforcars.all4cars.RetroifitApi.ApiClient;
import com.allforcars.all4cars.Test.modelCreateBorrowers.CreateBorrowerResponse;
import com.allforcars.all4cars.Venderlogin.Activity.AddProduct_Activity;
import com.allforcars.all4cars.Venderlogin.Activity.Vender_Home_Activity;
import com.allforcars.all4cars.app.Config;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.SessionManager;
import com.allforcars.all4cars.classes.Utility;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Login_Activity extends AppCompatActivity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener {

    RelativeLayout link_registration, link_forgetpassword;
    EditText et_email_login, et_password;
    Button btn_login;
    String Choose_usertype, user_RadioBtn = "", str_userpasword, user_type, str_usereemail, user_id, regId = "", usertype, login_id, user_Email_id = "", user_phone = "", str_teacher_parent;

    SharedPreferences sharedPreferences;
    RadioButton check_radio_button;
    Boolean isClick = true;
    ImageView password_show;
    int check_radio_btn;
    RadioGroup radio_gr_lan_login;
    RadioButton rb_english, rb_indonesia;
    Spinner chooseusertype;
    private CheckBox rem_userpass;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password_show = findViewById(R.id.password_show);
        link_registration = findViewById(R.id.link_registration);
        et_email_login = findViewById(R.id.et_email_login);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        link_forgetpassword = findViewById(R.id.link_forgetpassword);
        rem_userpass = (CheckBox) findViewById(R.id.checkBox);
        chooseusertype = findViewById(R.id.chooseusertype);
        rb_english = findViewById(R.id.rb_english);
        rb_indonesia = findViewById(R.id.rb_indonesia);
        radio_gr_lan_login = findViewById(R.id.radio_gr_lan_login);

        if (ContextCompat.checkSelfPermission(Login_Activity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Login_Activity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Login_Activity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        Choose_usertype = sharedpreference.getString("user_RadioBtn", "");

        if (SessionManager.GetSharedPreference(SessionManager.KEYRadioStatus, Login_Activity.this).equals("1")) {
            rb_indonesia.setChecked(true);
        } else {

            rb_english.setChecked(true);
        }


        rb_indonesia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                link_registration.setVisibility(View.GONE);
            }
        });
        user_type = "2";
        rb_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                link_registration.setVisibility(View.VISIBLE);
            }
        });


        password_show.setImageResource(R.drawable.showvisiblity);

        password_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isClick) {
                    isClick = true;
                    password_show.setImageResource(R.drawable.showvisiblity);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                } else {
                    isClick = false;
                    password_show.setImageResource(R.drawable.paswordvisibliy);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }


            }
        });


        link_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ints = new Intent(Login_Activity.this, Signup_Activity.class);
                startActivity(ints);

            }
        });
        link_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ints = new Intent(Login_Activity.this, Forgetpassword_Activity.class);
                startActivity(ints);

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_userpasword = et_password.getText().toString();
                str_usereemail = et_email_login.getText().toString();
                check_radio_btn = radio_gr_lan_login.getCheckedRadioButtonId();


                if (str_usereemail.contains("@") == true) {
                    user_Email_id = str_usereemail;
                } else {
                    user_Email_id = str_usereemail;

                }

          Login_api();
            //    Get_Redmeehistory();

              // ViewShowMessageCount();

//
//              //  validation_user();
//                submits();

                // Get_Redmeehistory();


//                Intent intent = new Intent(Login_Activity.this, CustomSetPinActivity.class);
//                intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);

            }
        });


        if (sharedPreferences.getBoolean(KEY_REMEMBER, false))
            rem_userpass.setChecked(true);
        else
            rem_userpass.setChecked(false);

        et_email_login.setText(sharedPreferences.getString(KEY_USERNAME, ""));
        et_password.setText(sharedPreferences.getString(KEY_PASS, ""));

        et_email_login.addTextChangedListener(Login_Activity.this);
        et_password.addTextChangedListener(Login_Activity.this);
        rem_userpass.setOnCheckedChangeListener(Login_Activity.this);


    }


    public void submits() {

        int id = radio_gr_lan_login.getCheckedRadioButtonId();
        check_radio_button = (RadioButton) findViewById(id);
        String str = String.valueOf(check_radio_button.getText());
        //   Toast.makeText(this, "" + str, Toast.LENGTH_SHORT).show();
        if (str.equals("Vendor")) {

            user_type = "1";


        } else if (str.equals("User")) {
            user_type = "2";

        }


        validation_user();

    }


    public void validation_user() {


        if (str_userpasword.matches("")) {
            et_password.setError("Please Enter Password");
            et_password.requestFocus();
        } else if (str_usereemail.matches("")) {
            et_email_login.setError("Please Enter Email Id");
            et_email_login.requestFocus();

        } else {
            SessionManager.SetSharedPreference(SessionManager.KEYRadioStatus, user_type, getApplicationContext());
            Login_api();
        }
    }

    private void Login_api() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);


        final ProgressDialog pd = new ProgressDialog(Login_Activity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait..");
        pd.show();
        String urll = Utility.Base_URL + "login";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {


                        JSONObject detailsrecord = jsonObject.getJSONObject("record");

                        usertype = detailsrecord.getString("user_type");
                        user_RadioBtn = detailsrecord.getString("user_type");


                        Toast toast = Toast.makeText(Login_Activity.this, message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pd.dismiss();

                        if (usertype.equals("2")) {
                            user_id = detailsrecord.getString("id");
                            sharedPreferences = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Filter", "Filter");
                            editor.apply();
                            Intent ints = new Intent(Login_Activity.this, Home_Activity.class);
                            ints.putExtra("filter_key", "filter_value");
                            ints.putExtra("name_key", "1");
                            ints.putExtra("Tabopens", "Home");
                            ints.putExtra("Filter", "F");
                            startActivity(ints);
                            finish();

                        }

                        if (usertype.equals("1")) {

                            user_id = detailsrecord.getString("fld_admin_id");
                            Intent ints = new Intent(Login_Activity.this, Vender_Home_Activity.class);
                            startActivity(ints);
                            finish();

                        }

                        sharedPreferences = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_id", user_id);
                        editor.putString("usertype", usertype);
                        editor.putString("user_RadioBtn", user_RadioBtn);
                        editor.apply();


                    } else {
                        Toast toast = Toast.makeText(Login_Activity.this, message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }

                } catch (JSONException e) {


                    //Toast.makeText(Create_New_Check_Activity.this, "Please Enter Registered Email And Password", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppController.getInstance().getRequestQueue().cancelAll("user_Sign_up");
                pd.dismiss();
                if (error instanceof NetworkError) {
                    Toast.makeText(Login_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Login_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Login_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Login_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Login_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Login_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("email", user_Email_id);
                map.put("password", str_userpasword);
                map.put("user_type", user_type);
                map.put("device_token", "DFSDFDSF4DF4DF14DFDF");

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0, -1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void Login_test() {


        //  http://{{HOST}}/m-api/borrowers/create
        final ProgressDialog pd = new ProgressDialog(Login_Activity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait..");
        pd.show();
        String urll = "http://3.6.35.180/m-api/borrowers/create";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {


                        JSONObject detailsrecord = jsonObject.getJSONObject("record");

                        usertype = detailsrecord.getString("user_type");
                        user_RadioBtn = detailsrecord.getString("user_type");


                        Toast toast = Toast.makeText(Login_Activity.this, message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pd.dismiss();

                        if (usertype.equals("2")) {
                            user_id = detailsrecord.getString("id");
                            sharedPreferences = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Filter", "Filter");
                            editor.apply();
                            Intent ints = new Intent(Login_Activity.this, Home_Activity.class);
                            ints.putExtra("filter_key", "filter_value");
                            ints.putExtra("name_key", "1");
                            ints.putExtra("Tabopens", "Home");
                            ints.putExtra("Filter", "F");
                            startActivity(ints);
                            finish();

                        }

                        if (usertype.equals("1")) {

                            user_id = detailsrecord.getString("fld_admin_id");
                            Intent ints = new Intent(Login_Activity.this, Vender_Home_Activity.class);
                            startActivity(ints);
                            finish();

                        }

                        sharedPreferences = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_id", user_id);
                        editor.putString("usertype", usertype);
                        editor.putString("user_RadioBtn", user_RadioBtn);
                        editor.apply();


                    } else {
                        Toast toast = Toast.makeText(Login_Activity.this, message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }

                } catch (JSONException e) {


                    //Toast.makeText(Create_New_Check_Activity.this, "Please Enter Registered Email And Password", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppController.getInstance().getRequestQueue().cancelAll("user_Sign_up");
                pd.dismiss();
                if (error instanceof NetworkError) {
                    Toast.makeText(Login_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Login_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Login_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Login_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Login_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Login_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", "iis");
                map.put("m_no", "55");
                map.put("address", "dddd");
                map.put("village", "DFSDFDSF4DF4DF14DFDF");
                map.put("father_name", "DFSDFDSF4DF4DF14DFDF");
                map.put("aadhar_no", "DFSDFDSF4DF4DF14DFDF");
                map.put("pan_no", "DFSDFDSF4DF4DF14DFDF");
                map.put("landholding", "DFSDFDSF4DF4DF14DFDF");
                map.put("crops_grown", "DFSDFDSF4DF4DF14DFDF");
                map.put("existing_credit_facility", "55");
                map.put("total_existing_loan_outstanding_amount", "522");
                map.put("total_current_emi_amount", "522");
                map.put("total_monthly_expenses", "141");
                map.put("additional_income_source", "514");
                map.put("additional_monthly_income", "41");

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0, -1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    private void managePrefs() {
        if (rem_userpass.isChecked()) {
            editor.putString(KEY_USERNAME, et_email_login.getText().toString().trim());
            editor.putString(KEY_PASS, et_password.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        } else {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }

    public void Get_Redmeehistory() {

        //  String urljsonobj_group = "http://3.6.35.180/m-api/"+"esign-sdk-info?account_application_id="+"6"+"";
        String urljsonobj_group = "http://3.6.35.180/m-api/borrowers";

        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("success").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = response.getJSONArray("record");

                    } else {


                    }


                } catch (JSONException e) {
                    AppController.getInstance().getRequestQueue().cancelAll("survey_list");
                    //   progressDialog.dismiss();
                }
                //   progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AppController.getInstance().getRequestQueue().cancelAll("survey_list");
                // progressDialog.dismiss();
                if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }


    private void ViewShowMessageCount() {



        APIService service = ApiClient.getClient().create(APIService.class);

        Call<CreateBorrowerResponse> call = service.Reportsends();
        //calling the api
        call.enqueue(new Callback<CreateBorrowerResponse>() {
            @Override
            public void onResponse(Call<CreateBorrowerResponse> call, retrofit2.Response<CreateBorrowerResponse> response) {
///


                try {

                    CreateBorrowerResponse result = response.body();
                    String name=response.body().getPayload().getData().getBorrowers().get(0).getName();
                    result.getPayload().getMessage();
                    Integer status = result.getCode();







                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CreateBorrowerResponse> call, Throwable t) {
                //  progressDialog.dismiss();
                // Toast.makeText(Loyalaty_Activity.this,"Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();
            }
        });
    }







}
