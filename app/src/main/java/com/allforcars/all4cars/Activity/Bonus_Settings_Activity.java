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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.Bonus_Setting_Adapter;
import com.allforcars.all4cars.Adapter.Loyality_Bonus_Adapter;
import com.allforcars.all4cars.Model.bonus_Setting_Model;
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

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Bonus_Settings_Activity extends AppCompatActivity {

    RelativeLayout relative_bonus_setting;
    ImageView btnsetting;
    RecyclerView recycler_bonuspoint;
    RecyclerView.LayoutManager layoutManager;
    Bonus_Setting_Adapter bonus_Setting_Adapter;
    ArrayList<bonus_Setting_Model> Arraylist_bonus_Setting;
    String user_id,amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus__settings);

        relative_bonus_setting = findViewById(R.id.relative_bonus_setting);
        recycler_bonuspoint = findViewById(R.id.recycler_bonuspoint);
        btnsetting = findViewById(R.id.btnsetting);

        relative_bonus_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        recycler_bonuspoint.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Bonus_Settings_Activity.this);
        recycler_bonuspoint.setLayoutManager(layoutManager);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");




        btnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


        Get_Loyalitybnounustting();

    }


    public void  Get_Loyalitybnounustting(){

        String urljsonobj_group = Base_URL+"get_bonus_list";
        final ProgressDialog progressDialog = new ProgressDialog(Bonus_Settings_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Arraylist_bonus_Setting = new ArrayList<>();
                    if (response.getString("success").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = response.getJSONArray("res");

                        for (int i = 0; i < jsonArray.length(); i++)

                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            bonus_Setting_Model addtcart = new bonus_Setting_Model();
                            addtcart.setbonus_point(jsonObject1.getString("bonus_point"));
                            amount= jsonObject1.getString("amount");

                            DecimalFormat formatter = new DecimalFormat("#,###,###");
                            String yourFormattedString = formatter.format(Integer.parseInt(amount));
                            addtcart.setamount(yourFormattedString+" "+"Naira");


                            addtcart.setstatus(jsonObject1.getString("status"));
                            addtcart.setbonus_id(jsonObject1.getString("bonus_id"));
                            Arraylist_bonus_Setting.add(addtcart);


                        }


                        bonus_Setting_Adapter = new Bonus_Setting_Adapter(Bonus_Settings_Activity.this, Arraylist_bonus_Setting);
                        recycler_bonuspoint.setLayoutManager(new LinearLayoutManager(Bonus_Settings_Activity.this, LinearLayoutManager.VERTICAL, false));
                        recycler_bonuspoint.setAdapter(bonus_Setting_Adapter);


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
                    Toast.makeText(Bonus_Settings_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Bonus_Settings_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Bonus_Settings_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Bonus_Settings_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Bonus_Settings_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Bonus_Settings_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Bonus_Settings_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }

}
