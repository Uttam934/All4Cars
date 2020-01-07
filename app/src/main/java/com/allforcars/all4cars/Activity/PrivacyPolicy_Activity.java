package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.Faq_Adapter;
import com.allforcars.all4cars.Adapter.Privacypolicy_Adapter;
import com.allforcars.all4cars.Model.Faq_Model;
import com.allforcars.all4cars.Model.Privacypolicy_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class PrivacyPolicy_Activity extends AppCompatActivity {

    ImageView btnback;
    String user_id,usertype;
    RecyclerView.LayoutManager layoutManager;
    Privacypolicy_Adapter privacypolicy_adapter;
    ArrayList<Privacypolicy_Model> Arraylist_PrivacyPolicy;
    RecyclerView recycler_privacy;
    String notification_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        btnback= findViewById(R.id.btnback);
        recycler_privacy= findViewById(R.id.recycler_privacy);


        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        Get_Notification_user();
    }


    public void  Get_Notification_user()

    {

        String urljsonobj_group = Base_URL+"privacy";
        final ProgressDialog progressDialog = new ProgressDialog(PrivacyPolicy_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Arraylist_PrivacyPolicy = new ArrayList<>();
                    if (response.getString("success").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++)

                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Privacypolicy_Model addtcart = new Privacypolicy_Model();
                            addtcart.setdescription(jsonObject1.getString("description"));
                            addtcart.setprivacy_id(jsonObject1.getString("p_id"));
                            addtcart.settitle(jsonObject1.getString("title"));

                            Arraylist_PrivacyPolicy.add(addtcart);


                        }


                        privacypolicy_adapter = new Privacypolicy_Adapter(PrivacyPolicy_Activity.this, Arraylist_PrivacyPolicy);
                        recycler_privacy.setLayoutManager(new LinearLayoutManager(PrivacyPolicy_Activity.this, LinearLayoutManager.VERTICAL, false));
                        recycler_privacy.setAdapter(privacypolicy_adapter);


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
                    Toast.makeText(PrivacyPolicy_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(PrivacyPolicy_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(PrivacyPolicy_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(PrivacyPolicy_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(PrivacyPolicy_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(PrivacyPolicy_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PrivacyPolicy_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }
}
