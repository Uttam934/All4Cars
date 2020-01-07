package com.allforcars.all4cars.Venderlogin.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.Recommend_user_Activity;
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

public class Subscription_Activity extends AppCompatActivity {

    RelativeLayout backbtn_subcrpion;
    private String   contactperson,company_address,user_name,phone_number,user_email,user_company,str_vendernm,str_txt_eamil,str_txt_phone,str_txt_name,str_venderaddress,usertype,user_id;
    private  EditText txt_vendrnm,txt_veneercompnaynm,txt_vendrphone,txt_vendreamil,txt_vendAddress;
    private  Button btn_submitsubcritp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);


        backbtn_subcrpion= findViewById(R.id.backbtn_subcrpion);
        txt_vendrnm=findViewById(R.id.txt_vendrnm);
        txt_veneercompnaynm=findViewById(R.id.txt_veneercompnaynm);
        txt_vendreamil=findViewById(R.id.txt_vendreamil);
        txt_vendrphone=findViewById(R.id.txt_vendrphone);
        btn_submitsubcritp=findViewById(R.id.btn_submitsubcritp);
        txt_vendAddress=findViewById(R.id.txt_vendAddress);


        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");
        user_company =sharedpreference.getString("user_name","");
        phone_number =sharedpreference.getString("phone_number","");
        user_email =sharedpreference.getString("user_email","");
        company_address =sharedpreference.getString("address","");
        contactperson =sharedpreference.getString("contactperson","");

        txt_vendrnm.setText(contactperson);
        txt_veneercompnaynm.setText(user_company);
        txt_vendreamil.setText(user_email);
        txt_vendrphone.setText(phone_number);
        txt_vendAddress.setText(company_address);



        backbtn_subcrpion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        btn_submitsubcritp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_txt_name=txt_veneercompnaynm.getText().toString();
                str_txt_phone=txt_vendrphone.getText().toString();
                str_txt_eamil=txt_vendreamil.getText().toString();
                str_vendernm=txt_vendrnm.getText().toString();
                str_venderaddress=txt_vendAddress.getText().toString();




                send_help_User_api();




            }
        });
    }


    private void send_help_User_api() {
        final ProgressDialog pd = new ProgressDialog(Subscription_Activity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait..");
        pd.show();
        String urll= Utility.Base_URL+"add_subscription";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {


                        Toast toast = Toast.makeText(Subscription_Activity.this,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pd.dismiss();
                        txt_veneercompnaynm.setText("");
                        txt_vendrphone.setText("");
                        txt_vendreamil.setText("");
                        txt_vendrnm.setText("");
                        txt_vendAddress.setText("");







                    }
                    else{
                        Toast toast = Toast.makeText(Subscription_Activity.this,message, Toast.LENGTH_LONG);
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
                    Toast.makeText(Subscription_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Subscription_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Subscription_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Subscription_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Subscription_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Subscription_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Subscription_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError { HashMap<String, String> map = new HashMap<>();
                map.put("vendor_id", user_id);
                map.put("company_name", str_txt_name);
                map.put("user_name", str_txt_name);
                map.put("email_id", str_txt_eamil);
                map.put("phone", str_txt_phone);
                map.put("address", str_venderaddress);



                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


}
