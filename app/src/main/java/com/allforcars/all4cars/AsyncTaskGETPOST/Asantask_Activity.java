package com.allforcars.all4cars.AsyncTaskGETPOST;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.allforcars.all4cars.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.Url;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Asantask_Activity extends AppCompatActivity {

    String vendor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asantask);
    }





    private class GetApiTest extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getApplicationContext());
            pDialog.setCancelable(false);
            pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... arg0) {
            OkHttpClient client = new OkHttpClient();


            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            //  RequestBody body = RequestBody.create(mediaType, "name="+"ddd"+"&m_no="+"41"+"&address="+"pp"+"&village="+"ss"+"&father_name="+"dd"+"&aadhar_no="+i+"&pan_no="+"ddd"+"&landholding="+"ssss"+"&crops_grown="+"ddddd"+"&existing_credit_facility="+"ddd"+"&total_existing_loan_outstanding_amount="+"111"+"&total_current_emi_amount="+"411"+"&total_monthly_expenses="+"114"+"&additional_income_source="+"441"+"&additional_monthly_income="+"444"+"&farmer_image="+imge);
            Request request = new Request.Builder()
                    .url(Base_URL+"my_loyaltys?user_id="+vendor_id+"")
                    .get()
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "5a4ac3dc-ed94-2682-7a2e-62bc1d2b7882")
                    .build();


            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                e.getMessage();
            }
            try {
                if (response!=null){
                    return response.body().string();
                }
                else {
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
            Log.d("check",response.toString());

            if (response.equals("")){
                Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();

            }
            else {
                try {
                    JSONObject jsonObj = new JSONObject(response);


                    Toast.makeText(getApplicationContext(), ""+jsonObj, Toast.LENGTH_SHORT).show();

                    String status = jsonObj.getString("status");
                    if (status.equals("true")) {





                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private class PostApiTest extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getApplicationContext());
            pDialog.setCancelable(false);
            pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... arg0) {
            OkHttpClient client = new OkHttpClient();


            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "name="+"ddd"+"&m_no="+"41"+"&address="+"pp"+"&village="+"ss"+"&father_name="+"dd"+"&pan_no="+"ddd"+"&landholding="+"ssss"+"&crops_grown="+"ddddd"+"&existing_credit_facility="+"ddd"+"&total_existing_loan_outstanding_amount="+"111"+"&total_current_emi_amount="+"411"+"&total_monthly_expenses="+"114"+"&additional_income_source="+"441"+"&additional_monthly_income="+"444");
            Request request = new Request.Builder()
                    .url(Base_URL+"my_loyaltys?user_id="+vendor_id+"")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "5a4ac3dc-ed94-2682-7a2e-62bc1d2b7882")
                    .build();


            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                e.getMessage();
            }
            try {
                if (response!=null){
                    return response.body().string();
                }
                else {
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
            Log.d("check",response.toString());

            if (response.equals("")){
                Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();

            }
            else {
                try {
                    JSONObject jsonObj = new JSONObject(response);


                    Toast.makeText(getApplicationContext(), ""+jsonObj, Toast.LENGTH_SHORT).show();

                    String status = jsonObj.getString("status");
                    if (status.equals("true")) {





                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
