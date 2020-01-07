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
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.Categoryicon_Adapter;
import com.allforcars.all4cars.Adapter.Detaillistdata_Adapter;
import com.allforcars.all4cars.Adapter.Orderhistory_Adapter;
import com.allforcars.all4cars.Model.Addtocart_Model;
import com.allforcars.all4cars.Model.Histroyorder_Model;
import com.allforcars.all4cars.Model.Product_detail_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.Utility;
import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class History_Activity extends AppCompatActivity {

    RelativeLayout forget_histroy;
    String user_id,usertype,payment_status;
    Orderhistory_Adapter orderhistory_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Histroyorder_Model> Arraylist_historyodermodel = new ArrayList<>();
    RecyclerView Recyclerview_orderlist;
    LinearLayout record_not;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        forget_histroy=findViewById(R.id.forget_histroy);
        Recyclerview_orderlist=findViewById(R.id.Recyclerview_orderlist);
        record_not=findViewById(R.id.record_not);


        Recyclerview_orderlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(History_Activity.this);
        Recyclerview_orderlist.setLayoutManager(layoutManager);
        Get_OrderDetail();

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");



        forget_histroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


    }



    private void Get_OrderDetail() {
        final ProgressDialog pd = new ProgressDialog(History_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"order_list";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                   Arraylist_historyodermodel = new ArrayList<>();
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("record");

                        count = jsonArray.length();
                        if(count==0)
                        {
                            record_not.setVisibility(View.VISIBLE);

                        }

                        for (int i = 0; i < jsonArray.length(); i++)

                        {
                            Histroyorder_Model addtcart = new Histroyorder_Model();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            addtcart.setpaymettype(jsonObject1.getString("payment_type"));
                            addtcart.setorder_number(jsonObject1.getString("order_number"));
                            payment_status = jsonObject1.getString("status");

                              if (payment_status.equals("2")) {
                                addtcart.setstatus("Order in Process");
                            } else if (payment_status.equals("3")) {
                                addtcart.setstatus("Order in Cancel");
                            } else if (payment_status.equals("4")) {
                                addtcart.setstatus("Order in Completed ");
                            }

                            addtcart.setsubtotoal(jsonObject1.getString("total_amount"));
                            addtcart.settotoalpayamt(jsonObject1.getString("payment_amount"));
                            addtcart.settotdiscount(jsonObject1.getString("discount_amount"));
                            addtcart.setorderdate(jsonObject1.getString("created_at"));
                            Arraylist_historyodermodel.add(addtcart);
                        }


//                        orderhistory_adapter = new Orderhistory_Adapter(History_Activity.this, Arraylist_historyodermodel);
//                        Recyclerview_orderlist.setLayoutManager(new LinearLayoutManager(History_Activity.this, LinearLayoutManager.VERTICAL, false));
//                        Recyclerview_orderlist.setAdapter(orderhistory_adapter);




                    }
                    else {



                    }



                    } catch (JSONException e) {


                    //   Toast.makeText(getActivity(), ,+e Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(History_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(History_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(History_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(History_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(History_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(History_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(History_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("user_type",usertype);
                map.put("user_id", user_id);
                map.put("status_type","0");


                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }

}
