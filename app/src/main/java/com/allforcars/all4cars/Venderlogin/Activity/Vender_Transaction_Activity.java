package com.allforcars.all4cars.Venderlogin.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.RedmeeHistory_Activity;
import com.allforcars.all4cars.Adapter.RedeemHistory_Adapter;
import com.allforcars.all4cars.Model.Histroyorder_Model;
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
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Vender_Transaction_Activity extends AppCompatActivity {

    RelativeLayout forget_bolnus;
    String totalorder,user_id,Total_rejected,Total_completed,Total_pending,Total_totaluser,all4cars_commission,pay_receive,total_amount,total_discount;
    TextView vender_registreduser,vender_Completeuser,vender_pendingorder,vender_rejectedorder,vender_all4carscommion,vender_totalpaymentrecive,vender_totalproductamt,vender_totalbalance;
    DecimalFormat df = new DecimalFormat("####0.00");
    Double balance,totalsale,allforcarcommion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender__transaction);

        forget_bolnus =findViewById(R.id.forget_bolnus);
        vender_registreduser =findViewById(R.id.vender_registreduser);
        vender_Completeuser =findViewById(R.id.vender_Completeuser);
        vender_rejectedorder =findViewById(R.id.vender_rejectedorder);
        vender_all4carscommion =findViewById(R.id.vender_all4carscommion);
        vender_totalpaymentrecive =findViewById(R.id.vender_totalpaymentrecive);
        vender_totalproductamt =findViewById(R.id.vender_totalproductamt);
        vender_totalbalance =findViewById(R.id.vender_totalbalance);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");


        forget_bolnus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        Get_Redmeehistory();

    }

    public void  Get_Redmeehistory(){

        String urljsonobj_group = Base_URL+"vender_dashboard?vender_id="+user_id+"";
        final ProgressDialog progressDialog = new ProgressDialog(Vender_Transaction_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Double all4cars_sum=0.00,pay_receive_sum=0.00,total_amtsum=0.00,total_discount_sum=0.00;
                    if (response.getString("success").equalsIgnoreCase("true")) {
                        JSONObject jsonObject = response.getJSONObject("record");
                        Total_totaluser = jsonObject.getString("processing");
                        Total_completed = jsonObject.getString("completed");
                        Total_rejected = jsonObject.getString("rejected");
                        totalorder = jsonObject.getString("totalorder");

                        vender_registreduser.setText(Total_totaluser);
                        vender_Completeuser.setText(Total_completed);
                        vender_rejectedorder.setText(Total_rejected);
                        vender_totalpaymentrecive.setText(totalorder);

                        JSONArray jsonarray = jsonObject.getJSONArray("amount");
                        for (int i = 0; i < jsonarray.length(); i++)

                        {
                            JSONObject jsonObject1 = jsonarray.getJSONObject(i);

                            all4cars_commission = jsonObject1.getString("all4cars_commission");
                            pay_receive = jsonObject1.getString("pay_receive");
                            total_amount = jsonObject1.getString("total_amount");
                            total_discount = jsonObject1.getString("total_discount");

                            all4cars_sum =all4cars_sum+(Double.parseDouble(all4cars_commission));
                            total_amtsum =total_amtsum+(Double.parseDouble(total_amount));
                            total_discount_sum= total_amtsum-all4cars_sum;

//                            allforcarcommion=String.valueOf(all4cars_sum);
//                            totalsale=String.valueOf(total_amtsum);
//                            balance=String.valueOf(total_discount_sum);



                        }

                        vender_all4carscommion.setText(df.format(all4cars_sum)+"₦");
                        vender_totalproductamt.setText(df.format(total_amtsum)+"₦");
                                vender_totalbalance.setText(df.format(total_discount_sum)+"₦");

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
                    Toast.makeText(Vender_Transaction_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Vender_Transaction_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Vender_Transaction_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Vender_Transaction_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Vender_Transaction_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Vender_Transaction_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Vender_Transaction_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }
}