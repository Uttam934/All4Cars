package com.allforcars.all4cars.Venderlogin.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.Home_Activity;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.CircleTransform;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Contactus_Activity extends AppCompatActivity {

    RelativeLayout backbtn_contactus;
    TextView txt_adminname,txt_admineamil,txt_adminphone,txt_address,txt_tollfreenumber;
    String admin_name,admin_email,admin_number,admin_tollfreenumber,admin_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        backbtn_contactus=findViewById(R.id.backbtn_contactus);
        txt_adminname=findViewById(R.id.txt_adminname);
        txt_admineamil=findViewById(R.id.txt_admineamil);
        txt_adminphone=findViewById(R.id.txt_adminphone);
        txt_address=findViewById(R.id.txt_address);
        txt_tollfreenumber=findViewById(R.id.txt_tollfreenumber);

        Get_contacactus();

        backbtn_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

    }

    public void  Get_contacactus(){

        String urljsonobj_group = Base_URL+"get_contact_us";
        final ProgressDialog progressDialog = new ProgressDialog(Contactus_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.getString("success").equalsIgnoreCase("true")) {

                         JSONObject jsonObject1=response.getJSONObject("record");
                           admin_name = (jsonObject1.getString("contact_name"));
                            admin_email = (jsonObject1.getString("contact_email"));
                            admin_number = (jsonObject1.getString("contact_number"));
                            admin_tollfreenumber = (jsonObject1.getString("contact_toolfree"));
                            admin_address = (jsonObject1.getString("contact_address"));


                        txt_adminname.setText(admin_name);
                                txt_admineamil.setText(admin_email);
                        txt_adminphone.setText(admin_number);
                                txt_address.setText(admin_address);
                        txt_tollfreenumber.setText(admin_tollfreenumber);









                    }



                    else {



                    }



                }
                catch (JSONException e) {
                    AppController.getInstance().getRequestQueue().cancelAll("survey_list");
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AppController.getInstance().getRequestQueue().cancelAll("survey_list");
                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                    Toast.makeText(Contactus_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Contactus_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Contactus_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Contactus_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Contactus_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Contactus_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Contactus_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }


}
