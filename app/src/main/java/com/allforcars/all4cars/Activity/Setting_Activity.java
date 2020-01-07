package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.Detaillistdata_Adapter;
import com.allforcars.all4cars.Model.Product_detail_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.Utility;
import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Setting_Activity extends AppCompatActivity {


   Button changepss,btn_updateprofile,btn_kmseeting;
   ImageView btnsetting;
   LinearLayout changepassod,updatefilrile,kmsetting,Notification;
   String str_status,push,user_id,user_type,notification_status;
    android.support.v7.widget.SwitchCompat simpleSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        simpleSwitch = findViewById(R.id.simpleSwitch);
        updatefilrile = findViewById(R.id.updatefilrile);
        kmsetting = findViewById(R.id.kmsetting);
        btnsetting = findViewById(R.id.btnsetting);
        changepassod = findViewById(R.id.changepassod);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        user_type =sharedpreference.getString("usertype","");
        push =sharedpreference.getString("notification_status","");


        if(push.equals("0")){
            simpleSwitch.setChecked(false);
        }else{
            simpleSwitch.setChecked(true);
        }

        changepassod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ints = new Intent(Setting_Activity.this,Changepassword_Activity.class);
                startActivity(ints);

            }
        });

        kmsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ints = new Intent(Setting_Activity.this,Km_Setting_Activity.class);
                startActivity(ints);

            }
        });

        btnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        updatefilrile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ints = new Intent(Setting_Activity.this,UserProfile_Activity.class);
                startActivity(ints);

            }
        });

        simpleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(simpleSwitch.isChecked()){
                    notification_status = "1";
                  Get_cartnumber();

                }
                else{
                    notification_status = "0";
                  Get_cartnumber();

                }

               // Toast.makeText(Setting_Activity.this, " "+notification_status, Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void Get_cartnumber() {
        final ProgressDialog pd = new ProgressDialog(Setting_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"change_notification";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {

                        Toast toast = Toast.makeText(Setting_Activity.this,""+message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();



                    }

                    else{

                        Toast toast = Toast.makeText(Setting_Activity.this,""+message, Toast.LENGTH_LONG); toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        //
                    }//Toast.makeText(Setting_Activity.this, " "+message, Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {


                    //   Toast.makeText(getActivity(), ,+e Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Setting_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Setting_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Setting_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Setting_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Setting_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Setting_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Setting_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("user_id",user_id );
                map.put("user_type",user_type );
                map.put("notification_status",notification_status );


                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }


}
