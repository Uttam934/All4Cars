package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Recommend_user_Activity extends AppCompatActivity {

    RelativeLayout backbtn_contactus;
    String user_name,Recomendation,user_id,usertype,str_txt_name,str_txt_phone,str_txt_eamil,str_txt_addres,str_txt_description;
    EditText txt_name,txt_phone,txt_eamil,txt_addres,txt_description;
    Button btn_submit;
    TextView text_recomndaton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_user);

        backbtn_contactus= findViewById(R.id.backbtn_contactus);


        txt_name=findViewById(R.id.txt_name);
        txt_phone=findViewById(R.id.txt_phone);
        txt_eamil=findViewById(R.id.txt_eamil);
        txt_addres=findViewById(R.id.txt_addres);
        txt_description=findViewById(R.id.txt_description);
        btn_submit=findViewById(R.id.btn_submit);
        text_recomndaton=findViewById(R.id.text_recomndaton);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");
        user_name =sharedpreference.getString("user_name","");

        Recomendation=getIntent().getStringExtra("Recomendation");

        if (Recomendation.equals("Venderside"))
        {
            text_recomndaton.setText("Add Recommendation");

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    str_txt_name=txt_name.getText().toString();
                    str_txt_phone=txt_phone.getText().toString();
                    str_txt_eamil=txt_eamil.getText().toString();
                    str_txt_addres=txt_addres.getText().toString();
                    str_txt_description=txt_description.getText().toString();



                    validation_vender();




                }
            });
        }

        else {

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    str_txt_name=txt_name.getText().toString();
                    str_txt_phone=txt_phone.getText().toString();
                    str_txt_eamil=txt_eamil.getText().toString();
                    str_txt_addres=txt_addres.getText().toString();
                    str_txt_description=txt_description.getText().toString();



                    validation_user();




                }
            });

        }



        backbtn_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }


    public void validation_user()
    {


        if(str_txt_name.matches(""))
        {
            txt_name.setError("Please Enter Name");
            txt_name.requestFocus();
        }

        else if (str_txt_phone.matches(""))
        {
            txt_phone.setError("Please Enter Phone");
            txt_phone.requestFocus();
        }



        else
        {

            send_help_User_api();
        }
    }

    public void validation_vender()
    {


        if(str_txt_name.matches(""))
        {
            txt_name.setError("Please Enter Name");
            txt_name.requestFocus();
        }

        else if (str_txt_phone.matches(""))
        {
            txt_phone.setError("Please Enter Phone");
            txt_phone.requestFocus();
        }



        else
        {

            send_help_vender();
        }
    }




    private void send_help_User_api() {
        final ProgressDialog pd = new ProgressDialog(Recommend_user_Activity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait..");
        pd.show();
        String urll= Utility.Base_URL+"add_recommendation";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {


                        Toast toast = Toast.makeText(Recommend_user_Activity.this,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pd.dismiss();
                        txt_name.setText("");
                        txt_phone.setText("");
                        txt_eamil.setText("");
                        txt_addres.setText("");
                        txt_description.setText("");







                    }
                    else{
                        Toast toast = Toast.makeText(Recommend_user_Activity.this,message, Toast.LENGTH_LONG);
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
                    Toast.makeText(Recommend_user_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Recommend_user_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Recommend_user_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Recommend_user_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Recommend_user_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Recommend_user_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Recommend_user_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError { HashMap<String, String> map = new HashMap<>();
                map.put("user_id", user_id);
                map.put("user_name", str_txt_name);
                map.put("email", str_txt_eamil);
                map.put("phone", str_txt_phone);
                map.put("address", str_txt_addres);
                map.put("description", str_txt_description);
                map.put("user_type", usertype);
                map.put("company_name", "User");


                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void send_help_vender() {
        final ProgressDialog pd = new ProgressDialog(Recommend_user_Activity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait..");
        pd.show();
        String urll= Utility.Base_URL+"add_recommendation";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {


                        Toast toast = Toast.makeText(Recommend_user_Activity.this,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pd.dismiss();
                        txt_name.setText("");
                        txt_phone.setText("");
                        txt_eamil.setText("");
                        txt_addres.setText("");
                        txt_description.setText("");







                    }
                    else{
                        Toast toast = Toast.makeText(Recommend_user_Activity.this,message, Toast.LENGTH_LONG);
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
                    Toast.makeText(Recommend_user_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Recommend_user_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Recommend_user_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Recommend_user_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Recommend_user_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Recommend_user_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Recommend_user_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError { HashMap<String, String> map = new HashMap<>();
                map.put("user_id", user_id);
                map.put("user_name", str_txt_name);
                map.put("email", str_txt_eamil);
                map.put("phone", str_txt_phone);
                map.put("address", str_txt_addres);
                map.put("description", str_txt_description);
                map.put("user_type", usertype);
                map.put("company_name", user_name);


                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }








}
