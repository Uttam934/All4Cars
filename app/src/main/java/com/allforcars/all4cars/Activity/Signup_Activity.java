package com.allforcars.all4cars.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Signup_Activity extends AppCompatActivity {

   LinearLayout et_alreadlylogin;
   EditText et_registionusername,et_registioneamil,et_registionpassword,et_registionphone,et_Dob;
   Button btn_register;
   String str_username="",str_email="",str_phone="",str_password="",str_usertype,user_type,str_dob="";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ArrayAdapter adapter;
    Spinner sinupchooseusertype;
    private int mYear, mMonth, mDay;
    private Calendar myCalendar = Calendar.getInstance();
    ImageView Dob_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        et_alreadlylogin = findViewById(R.id.et_alreadlylogin);
        btn_register = findViewById(R.id.btn_register);
        et_registionusername = findViewById(R.id.et_registionusername);
        et_registioneamil = findViewById(R.id.et_registioneamil);
        et_registionpassword = findViewById(R.id.et_registionpassword);
        et_registionphone = findViewById(R.id.et_registionphone);
        et_Dob = findViewById(R.id.et_Dob);
        Dob_btn = findViewById(R.id.Dob_btn);



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_username=et_registionusername.getText().toString();
                str_email=et_registioneamil.getText().toString();
                str_phone=et_registionphone.getText().toString();
                str_dob=et_Dob.getText().toString();
                str_password=et_registionpassword.getText().toString();



                    if (str_email.matches(emailPattern))
                    {
                        validation_user();
                    }
                    else
                    {
                        et_registioneamil.setError("Enter valid Email Id");
                        et_registioneamil.requestFocus();
                    }





            }
        });


        et_alreadlylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            onBackPressed();
            }
        });


        Dob_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Signup_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth)

                            {
                                if(dayOfMonth<10 && monthOfYear<10)
                                {
                                    et_Dob.setText("0"+dayOfMonth + "/" +"0"+ (monthOfYear + 1) + "/" + year);

                                }
                                else if(monthOfYear<10 && dayOfMonth>10)
                                {
                                    et_Dob.setText(dayOfMonth + "/" +"0"+ (monthOfYear + 1) + "/" + year);
                                }
                                else if(dayOfMonth<10 && monthOfYear>10)
                                {
                                    et_Dob.setText("0"+dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                                else
                                {
                                    et_Dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());


            }
        });





    }




    public void validation_user()
    {


        if(str_username.matches(""))
        {
            et_registionusername.setError("Please Enter User Name");
            et_registionusername.requestFocus();
        }

        else if (str_email.matches(""))
        {
            et_registioneamil.setError("Please Enter Email");
            et_registioneamil.requestFocus();
        }


        else if (str_password.matches(""))
        {
            et_registionpassword.setError("Please Enter Password");
            et_registionpassword.requestFocus();

        }
        else if (str_phone.matches(""))
        {
            et_registionphone.setError("Please Enter Phone");
            et_registionphone.requestFocus();

        }

        else
        {

            signup_api();
        }
    }



    private void signup_api() {
        final ProgressDialog pd = new ProgressDialog(Signup_Activity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait..");
        pd.show();
        String urll= Utility.Base_URL+"user_registration";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {


                        Toast toast = Toast.makeText(Signup_Activity.this,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pd.dismiss();

                        Intent ints = new Intent(Signup_Activity.this,Login_Activity.class);
                        startActivity(ints);
                        finish();




                    }
                    else{
                        Toast toast = Toast.makeText(Signup_Activity.this,message, Toast.LENGTH_LONG);
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
                    Toast.makeText(Signup_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Signup_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Signup_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Signup_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Signup_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Signup_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Signup_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError { HashMap<String, String> map = new HashMap<>();
                map.put("name", str_username);
                map.put("email", str_email);
                map.put("password", str_password);
                map.put("phone_number", str_phone);
                map.put("birthday_day", str_dob);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
