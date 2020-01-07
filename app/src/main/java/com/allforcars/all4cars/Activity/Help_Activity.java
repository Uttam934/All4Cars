package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.RedeemHistory_Adapter;
import com.allforcars.all4cars.Model.RedeemHistory_Model;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Help_Activity extends AppCompatActivity {

   private RelativeLayout Comparre_back;
    private EditText customer_subject,customer_message,customer_whatapp,customer_Email_id;
    private String user_id,usertype,str_subject,str_query,str_whatsappno,userhelp_type,str_email;
    private Button contact_us_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Comparre_back=findViewById(R.id.Comparre_back);
        customer_subject=findViewById(R.id.customer_subject);
        customer_message=findViewById(R.id.customer_message);
        contact_us_btn=findViewById(R.id.contact_us_btn);
        customer_whatapp=findViewById(R.id.customer_whatapp);
        customer_Email_id=findViewById(R.id.customer_Email_id);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");
        userhelp_type =getIntent().getStringExtra("help");





        contact_us_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_subject=customer_subject.getText().toString();
                str_whatsappno=customer_whatapp.getText().toString();
                str_query = customer_message.getText().toString().replace('\n', ' ').trim();
                str_email= customer_Email_id.getText().toString();

                if (userhelp_type.equals("Userside")) {

                    validation_user();

                }
                else {

                    validation_vender();

                }




            }
        });

        Comparre_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }


    public void validation_user()
    {


        if(str_subject.matches(""))
        {
            customer_subject.setError("Please Enter Subject");
            customer_subject.requestFocus();
        }

        else if (str_query.matches(""))
        {
            customer_message.setError("Please Enter Query");
            customer_message.requestFocus();
        }

        else if (str_whatsappno.matches(""))
        {
            customer_whatapp.setError("Please Enter What's app Number");
            customer_whatapp.requestFocus();
        }


        else
        {

            send_help_User_api();
        }
    }
    public void validation_vender()
    {


        if(str_subject.equals(""))
        {
            customer_subject.setError("Please Enter Subject");
            customer_subject.requestFocus();
        }

        else if (str_query.equals(""))
        {
            customer_message.setError("Please Enter Query");
            customer_message.requestFocus();
        }
        else if (str_whatsappno.matches(""))
        {
            customer_whatapp.setError("Please Enter What's app Number");
            customer_whatapp.requestFocus();
        }



        else
        {
            new SendVendermsg().execute();

        }
    }


    private void send_help_User_api() {
        final ProgressDialog pd = new ProgressDialog(Help_Activity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait..");
        pd.show();
        String urll= Utility.Base_URL+"send_help";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {


                        Toast toast = Toast.makeText(Help_Activity.this,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pd.dismiss();
                        customer_subject.setText("");
                        customer_message.setText("");
                        customer_whatapp.setText("");
                        customer_Email_id.setText("");







                    }
                    else{
                        Toast toast = Toast.makeText(Help_Activity.this,message, Toast.LENGTH_LONG);
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
                    Toast.makeText(Help_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Help_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Help_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Help_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Help_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Help_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Help_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError { HashMap<String, String> map = new HashMap<>();
                map.put("user_id", user_id);
                map.put("query", str_query);
                map.put("subject", str_subject);
                map.put("user_type", usertype);
                map.put("whatsapp_num", str_whatsappno);
                map.put("email", str_email);


                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    private class SendVendermsg extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Help_Activity.this);
            pDialog.setMessage("Please Wait..");
            pDialog.setCancelable(false);
            pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {

            OkHttpClient client;
            client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            //  RequestBody body = RequestBody.create(mediaType, "message&user_id=" + user_id + "&group_id=" + group_id + "&message_text=" + messageText + "&attach=" + "" + "");
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(Base_URL+"help_vendor?vendor_id="+user_id+"&query="+str_query+"&subject="+str_subject+"&whatsapp_num="+str_whatsappno+"&email="+str_email+"")
                    .get()
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "fbb72eb5-733d-6610-9145-8e81e3a0654c")
                    .build();


            okhttp3.Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                e.getMessage();
            }
            try {
                if (response != null) {
                    return response.body().string();
                } else {
                    return "";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String response) {

            pDialog.dismiss();

            if (response.equals("")) {
                Toast.makeText(Help_Activity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();

            } else {
                try {

                    pDialog.dismiss();

                    JSONObject jsonObject=new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");

                    if (success.equals("true")) {

                        Toast toast = Toast.makeText(Help_Activity.this,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pDialog.dismiss();

                        customer_subject.setText("");
                        customer_message.setText("");
                        customer_whatapp.setText("");
                        customer_Email_id.setText("");


                    } else {
                        pDialog.dismiss();
                        Toast.makeText(Help_Activity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    pDialog.dismiss();
                } catch (Exception e) {
                    pDialog.dismiss();
                    if (e.getMessage() != null) {
                        Log.d("Error", e.getMessage());
                    }
                }
            }
        }
    }




}
