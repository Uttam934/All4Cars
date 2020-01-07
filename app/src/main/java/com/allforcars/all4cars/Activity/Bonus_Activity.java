package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.Loyality_Bonus_Adapter;
import com.allforcars.all4cars.Model.Bonus_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.interfaces.RefreshInterface;
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

public class Bonus_Activity extends AppCompatActivity implements RefreshInterface {


    RelativeLayout forget_bolnus;
    RecyclerView recycler_bonus;
    RecyclerView.LayoutManager layoutManager;
    Loyality_Bonus_Adapter loyality_bonus_adapter;
    ArrayList<Bonus_Model> Arraylist_loyalistybounus;
    String user_id,bonus_point,bonus_var;
    private RefreshInterface refreshInterface;
    TextView text_bonus;
    LinearLayout img_bounusseitn,reedem_history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyality_bonus);
        refreshInterface= this;
        forget_bolnus =findViewById(R.id.forget_bolnus);
        recycler_bonus =findViewById(R.id.recycler_bonus);
        text_bonus =findViewById(R.id.text_bonus);
        img_bounusseitn =findViewById(R.id.img_bounusseitn);
        reedem_history =findViewById(R.id.reedem_history);

        recycler_bonus.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Bonus_Activity.this);
        recycler_bonus.setLayoutManager(layoutManager);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");


        img_bounusseitn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ints = new Intent(Bonus_Activity.this,Bonus_Settings_Activity.class);
                startActivity(ints);
            }
        });
        reedem_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints = new Intent(Bonus_Activity.this,RedmeeHistory_Activity.class);
                startActivity(ints);

            }
        });


        forget_bolnus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


        Get_Loyalitybnounus();

    }


    public void  Get_Loyalitybnounus(){

        String urljsonobj_group = Base_URL+"my_loyaltys?user_id="+user_id+"";
        final ProgressDialog progressDialog = new ProgressDialog(Bonus_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Arraylist_loyalistybounus = new ArrayList<>();
                    if (response.getString("success").equalsIgnoreCase("true")) {

                        bonus_point=  response.getString("bonus_point");
                        text_bonus.setText(bonus_point);

                        JSONArray jsonArray = response.getJSONArray("record");

                        for (int i = 0; i < jsonArray.length(); i++)

                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                             Bonus_Model addtcart = new Bonus_Model();

                             bonus_var = jsonObject1.getString("bonus_point");
                             addtcart.setloyalty_status("0");
                             if(Integer.parseInt(bonus_point)>=Integer.parseInt(bonus_var))
                            {
                                addtcart.setloyalty_status("1");
                            }



                             addtcart.setbonus_point(jsonObject1.getString("bonus_point"));
                             addtcart.setloyalty_id(jsonObject1.getString("item_id"));
                             addtcart.setloyalty_image(jsonObject1.getString("item_image"));
                             addtcart.setloyalty_name(jsonObject1.getString("item_name"));

                             Arraylist_loyalistybounus.add(addtcart);


                        }


                        loyality_bonus_adapter = new Loyality_Bonus_Adapter(Bonus_Activity.this, Arraylist_loyalistybounus,refreshInterface);
                        recycler_bonus.setLayoutManager(new LinearLayoutManager(Bonus_Activity.this, LinearLayoutManager.VERTICAL, false));
                        recycler_bonus.setAdapter(loyality_bonus_adapter);


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
                    Toast.makeText(Bonus_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Bonus_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Bonus_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Bonus_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Bonus_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Bonus_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Bonus_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }


    @Override
    public void Refresh() {
        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        Get_Loyalitybnounus();

    }

}
