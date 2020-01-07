package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.allforcars.all4cars.Adapter.RedeemHistory_Adapter;
import com.allforcars.all4cars.Model.RedeemHistory_Model;
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

public class RedmeeHistory_Activity extends AppCompatActivity {

    RelativeLayout backbtns;
    RecyclerView recycler_redeemhistory;
    RecyclerView.LayoutManager layoutManager;
    RedeemHistory_Adapter redeemHistory_adapter;
    ArrayList<RedeemHistory_Model> Arraylist_RedeemHistory;
    String user_id,created_on;
    TextView text_bonus;
    LinearLayout record_not;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redmee_history);

        backbtns =findViewById(R.id.backbtns);
        recycler_redeemhistory =findViewById(R.id.recycler_redeemhistory);
        record_not =findViewById(R.id.record_not);

        recycler_redeemhistory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(RedmeeHistory_Activity.this);
        recycler_redeemhistory.setLayoutManager(layoutManager);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");




        backbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


        Get_Redmeehistory();

    }


    public void  Get_Redmeehistory(){

        String urljsonobj_group = Base_URL+"redeem_history?user_id="+user_id+"";
        final ProgressDialog progressDialog = new ProgressDialog(RedmeeHistory_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Arraylist_RedeemHistory = new ArrayList<>();
                    if (response.getString("success").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = response.getJSONArray("record");

                        count = jsonArray.length();
                        if(count==0)
                        {
                            record_not.setVisibility(View.VISIBLE);

                        }


                        for (int i = 0; i < jsonArray.length(); i++)

                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            RedeemHistory_Model addtcart = new RedeemHistory_Model();
                            addtcart.setbonus_point(jsonObject1.getString("bonus_point"));
                            addtcart.setloyalty_id(jsonObject1.getString("loyalty_id"));
                            addtcart.setloyalty_image(jsonObject1.getString("loyalty_image"));
                            addtcart.setloyalty_name(jsonObject1.getString("loyalty_name"));
                            addtcart.setloyalty_status(jsonObject1.getString("status"));
                            created_on= jsonObject1.getString("created_on");

                            String[] datePartss = created_on.split("\\s+");
                            String s = datePartss[0];
                            String ss = datePartss[1];


                            String[] datetravls = s.split("-");
                            String yyyys = datetravls[0];
                            String mms = datetravls[1];
                            String dds = datetravls[2];
                            addtcart.setredeem_date(dds + "/" + mms + "/" + yyyys + " "+ss);


                            Arraylist_RedeemHistory.add(addtcart);


                        }


                        redeemHistory_adapter = new RedeemHistory_Adapter(RedmeeHistory_Activity.this, Arraylist_RedeemHistory);
                        recycler_redeemhistory.setLayoutManager(new LinearLayoutManager(RedmeeHistory_Activity.this, LinearLayoutManager.VERTICAL, false));
                        recycler_redeemhistory.setAdapter(redeemHistory_adapter);


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
                    Toast.makeText(RedmeeHistory_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(RedmeeHistory_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(RedmeeHistory_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(RedmeeHistory_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(RedmeeHistory_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(RedmeeHistory_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RedmeeHistory_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }

}
