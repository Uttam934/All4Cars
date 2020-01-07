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
import com.allforcars.all4cars.Adapter.Notification_Adapter;
import com.allforcars.all4cars.Model.Faq_Model;
import com.allforcars.all4cars.Model.Notification_Model;
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

public class Faq_Activity extends AppCompatActivity {

    ImageView btnsetting;
    String user_id,usertype;
    RecyclerView.LayoutManager layoutManager;
    Faq_Adapter faq_adapter;
    ArrayList<Faq_Model> Arraylist_faq;
    RecyclerView recycler_faq;
    String notification_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        btnsetting= findViewById(R.id.btnsetting);
        recycler_faq= findViewById(R.id.recycler_faq);


        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");


        btnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        Get_Notification_user();
    }


    public void  Get_Notification_user()

    {

        String urljsonobj_group = Base_URL+"faq";
        final ProgressDialog progressDialog = new ProgressDialog(Faq_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Arraylist_faq = new ArrayList<>();
                    if (response.getString("success").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++)

                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Faq_Model addtcart = new Faq_Model();
                            addtcart.setAns(jsonObject1.getString("description").trim());
                            addtcart.setQes_Id(jsonObject1.getString("faq_id"));
                            addtcart.setQuestion(jsonObject1.getString("title").trim());
                            Arraylist_faq.add(addtcart);


                        }


                        faq_adapter = new Faq_Adapter(Faq_Activity.this, Arraylist_faq);
                        recycler_faq.setLayoutManager(new LinearLayoutManager(Faq_Activity.this, LinearLayoutManager.VERTICAL, false));
                        recycler_faq.setAdapter(faq_adapter);


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
                    Toast.makeText(Faq_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Faq_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Faq_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Faq_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Faq_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Faq_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Faq_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }
}
