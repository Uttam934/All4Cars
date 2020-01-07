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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class Changepassword_Activity extends AppCompatActivity {

   String str_current_pwd,str_new_pwd,str_confirm_pwd,user_id,user_type;
    RelativeLayout img_back_change_pwd;
   EditText edt_current_pwd,edt_new_pwd,edt_confirm_pwd;
   Button txt_submit_change_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);


        img_back_change_pwd = findViewById(R.id.img_back_change_pwd);
        edt_current_pwd = findViewById(R.id.edt_current_pwd);
        edt_new_pwd = findViewById(R.id.edt_new_pwd);
        edt_confirm_pwd = findViewById(R.id.edt_confirm_pwd);
        txt_submit_change_pwd = findViewById(R.id.txt_submit_change_pwd);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        user_type =sharedpreference.getString("usertype","");



        img_back_change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });





        txt_submit_change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_current_pwd = edt_current_pwd.getText().toString().trim();
                str_new_pwd = edt_new_pwd.getText().toString().trim();
                str_confirm_pwd = edt_confirm_pwd.getText().toString().trim();


                validation();



            }
        });


    }


    public void validation(){
        if(str_current_pwd.equals("")){
            edt_current_pwd.setError("Enter Current Password");
            edt_current_pwd.requestFocus();
        }else if (str_new_pwd.matches("")) {
            edt_new_pwd.setError("Enter New Password");
            edt_new_pwd.requestFocus();
        } else if (str_confirm_pwd.matches("")) {
            edt_confirm_pwd.setError("Enter Confirm Password");
            edt_confirm_pwd.requestFocus();

        }
        else {


            if(!str_new_pwd.equals(str_confirm_pwd)){

                Toast toast = Toast.makeText(Changepassword_Activity.this,"New password and confirm password is not matching", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }

            else {
                ChangePasword_api();

            }


        }
    }








    private void ChangePasword_api() {
        final ProgressDialog pd = new ProgressDialog(Changepassword_Activity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait..");
        pd.show();
        String urll= Utility.Base_URL+"change_password";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {


                        Toast toast = Toast.makeText(Changepassword_Activity.this,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pd.dismiss();
                        Intent ints = new Intent(Changepassword_Activity.this,Home_Activity.class);
                        startActivity(ints);
                        finish();






                    }
                    else{
                        Toast toast = Toast.makeText(Changepassword_Activity.this,message, Toast.LENGTH_LONG);
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
                    Toast.makeText(Changepassword_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Changepassword_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Changepassword_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Changepassword_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Changepassword_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Changepassword_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Changepassword_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError { HashMap<String, String> map = new HashMap<>();
                map.put("user_id", user_id);
                map.put("password", str_current_pwd);
                map.put("new_password", str_new_pwd);
                map.put("user_type", user_type);


                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
