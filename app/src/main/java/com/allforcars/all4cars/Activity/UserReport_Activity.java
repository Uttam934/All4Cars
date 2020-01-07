package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Response.Report_Response;
import com.allforcars.all4cars.RetroifitApi.APIService;
import com.allforcars.all4cars.RetroifitApi.ApiClient;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class UserReport_Activity extends AppCompatActivity {

    RelativeLayout backbtn;
    EditText report_phone,report_issue,reprot_name;
    Button report_submit;
    String user_name,phone_number,user_id,usertype, str_reprot_name,str_report_phone,str_report_issue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report);
        backbtn= findViewById(R.id.backbtn);

        backbtn=findViewById(R.id.backbtn);
        reprot_name=findViewById(R.id.reprot_name);
        report_phone=findViewById(R.id.report_phone);
        report_issue=findViewById(R.id.report_issue);
        report_submit=findViewById(R.id.report_submit);


        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");
        user_name =sharedpreference.getString("user_name","");
        phone_number =sharedpreference.getString("phone_number","");
        reprot_name.setText(user_name);
        report_phone.setText(phone_number);




        report_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_reprot_name  = reprot_name.getText().toString();
                str_report_phone = report_phone.getText().toString();
                str_report_issue = report_issue.getText().toString();



                validation_user();




            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }


    public void validation_user()
    {


        if(str_reprot_name.matches(""))
        {
            reprot_name.setError("Please Enter Name");
            reprot_name.requestFocus();
        }

        else if (str_report_phone.matches(""))
        {
            report_phone.setError("Please Enter Phone");
            report_phone.requestFocus();
        }



        else
        {

            hitsend_msg_Api(user_id,str_reprot_name,str_report_phone,usertype,str_report_issue);
        }
    }







    private void hitsend_msg_Api(final String user_id, final String str_reprot_name, String str_report_phone,String usertype,String str_report_issue)

    {
        final ProgressDialog progressDialog = new ProgressDialog(UserReport_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        APIService service = ApiClient.getClient().create(APIService.class);

        retrofit2.Call<Report_Response> call= service.Reportsend(user_id,str_reprot_name,str_report_phone,usertype,str_report_issue);
        //calling the api
        call.enqueue(new Callback<Report_Response>() {
            @Override
            public void onResponse(retrofit2.Call<Report_Response> call, retrofit2.Response<Report_Response> response) {

                progressDialog.dismiss();

                try
                {
                    if (response.isSuccessful())
                    {

                        Report_Response result=response.body();
                        String success=result.getSuccess();
                        String message= result.getMessage();


                        if (success.equals("true") || success.equals("True"))
                        {

                            Toast toast = Toast.makeText(UserReport_Activity.this,message, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            progressDialog.dismiss();
                            reprot_name.setText("");
                            report_phone.setText("");
                            report_issue.setText("");

                        }
                        else
                        {
                            Toast toast = Toast.makeText(UserReport_Activity.this,message, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(UserReport_Activity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
                                case 400:
                                    Toast.makeText(UserReport_Activity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(UserReport_Activity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(UserReport_Activity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(UserReport_Activity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(UserReport_Activity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(UserReport_Activity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                   Toast.makeText(UserReport_Activity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(UserReport_Activity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        catch (Exception e)
                        {
                            Toast.makeText(UserReport_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Report_Response> call, Throwable t) {
                progressDialog.dismiss();

                if (t instanceof IOException) {
                    Toast.makeText(UserReport_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("conversion issue",t.getMessage());
                    Toast.makeText(UserReport_Activity.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }






}
