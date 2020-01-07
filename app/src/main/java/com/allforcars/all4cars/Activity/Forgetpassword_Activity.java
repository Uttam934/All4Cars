package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forgetpassword_Activity extends AppCompatActivity {

    EditText et_resetpassword;
    Button btn_forgetpssword;
    private String str_usereemail;
    RelativeLayout forget_backbtn;
    Spinner chooseusertyp;
    ArrayAdapter adapter;
    String userty_type,user_type;
    RadioGroup radio_gr_lan_login;
    RadioButton rb_english,rb_indonesia;
    RadioButton check_radio_button;
    String user_id,usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        rb_english = findViewById(R.id.rb_english);
        rb_indonesia = findViewById(R.id.rb_indonesia);
        radio_gr_lan_login = findViewById(R.id.radio_gr_lan_login);
        et_resetpassword = findViewById(R.id.et_resetpassword);
        btn_forgetpssword = findViewById(R.id.btn_forgetpssword);
        forget_backbtn = findViewById(R.id.forget_backbtn);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");

        forget_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        btn_forgetpssword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_usereemail=et_resetpassword.getText().toString();


                    if(str_usereemail.matches(""))
                    {
                        et_resetpassword.setError("Please Enter Email Id");
                        et_resetpassword.requestFocus();
                    }
                    else {

                        submits();
                    }
                }




        });


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
     //   Toast.makeText(this, "" + user_type, Toast.LENGTH_SHORT).show();
        Forgetpasword_api();

    }


    private void Forgetpasword_api() {
        final ProgressDialog pd = new ProgressDialog(Forgetpassword_Activity.this);
        pd.setCancelable(false);
        pd.setMessage("please wait..");
        pd.show();
        String urll= Utility.Base_URL+"forgot_password";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {


                        Toast toast = Toast.makeText(Forgetpassword_Activity.this,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pd.dismiss();
                        Intent ints = new Intent(Forgetpassword_Activity.this,Login_Activity.class);
                        startActivity(ints);
                        finish();






                    }
                    else{
                        Toast toast = Toast.makeText(Forgetpassword_Activity.this,message, Toast.LENGTH_LONG);
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
                    Toast.makeText(Forgetpassword_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Forgetpassword_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Forgetpassword_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Forgetpassword_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Forgetpassword_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Forgetpassword_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Forgetpassword_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError { HashMap<String, String> map = new HashMap<>();
                map.put("email", str_usereemail);
                map.put("user_type", user_type);

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
