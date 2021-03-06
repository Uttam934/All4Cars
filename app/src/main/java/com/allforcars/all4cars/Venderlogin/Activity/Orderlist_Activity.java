package com.allforcars.all4cars.Venderlogin.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.History_Activity;
import com.allforcars.all4cars.Adapter.Orderhistory_Adapter;
import com.allforcars.all4cars.Model.Histroyorder_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Venderlogin.Adapter.Venderorderlist_Adapter;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.Utility;
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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Orderlist_Activity extends AppCompatActivity implements RefreshInterface {

    RelativeLayout backorder_list;
    String user_id,usertype,payment_status,str_filterchoose,Filter_status;
    Venderorderlist_Adapter orderhistory_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Histroyorder_Model> Arraylist_historyodermodel;
    RecyclerView Recyclerview_orderlistvender;
    private RefreshInterface refreshInterface;
    ArrayAdapter adapter;
    Spinner chooseuse;
    int count;
    LinearLayout et_Record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        backorder_list=findViewById(R.id.backorder_list);
        chooseuse=findViewById(R.id.chooseuse);
        et_Record=findViewById(R.id.et_Record);
        Recyclerview_orderlistvender=findViewById(R.id.Recyclerview_orderlistvender);
        refreshInterface= this;


        Recyclerview_orderlistvender.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Orderlist_Activity.this);
        Recyclerview_orderlistvender.setLayoutManager(layoutManager);

        adapter = new ArrayAdapter(Orderlist_Activity.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.option_types));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseuse.setAdapter(adapter);



        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");


        backorder_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        chooseuse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {


                str_filterchoose = chooseuse.getSelectedItem().toString().trim();
                if (str_filterchoose.equals("All"))
                {
                    Filter_status = "0";
                    Get_FILETERDATA();
                }
                else if (str_filterchoose.equals("Process"))
                {
                    Filter_status = "2";
                    Get_FILETERDATA();
                }
                else if (str_filterchoose.equals("Cancel"))
                {
                    Filter_status = "3";
                    Get_FILETERDATA();
                }



            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        Get_OrderDetail();
    }



    private void Get_OrderDetail() {
        final ProgressDialog pd = new ProgressDialog(Orderlist_Activity.this);
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
                            et_Record.setVisibility(View.VISIBLE);

                        }

                        for (int i = 0; i < jsonArray.length(); i++)

                        {
                            Histroyorder_Model addtcart = new Histroyorder_Model();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            payment_status = jsonObject1.getString("status");

                            if (payment_status.equals("1") || (payment_status.equals("2") || (payment_status.equals("3")))) {
                                addtcart.setpaymettype(jsonObject1.getString("payment_type"));
                                addtcart.setorder_number(jsonObject1.getString("order_number"));
                                addtcart.setsubtotoal(jsonObject1.getString("total_amount"));
                                addtcart.sethide_btn("0");
                                addtcart.settotoalpayamt(jsonObject1.getString("payment_amount"));
                                addtcart.settotdiscount(jsonObject1.getString("discount_amount"));
                                addtcart.setorderdate(jsonObject1.getString("created_at"));
                                addtcart.setvender_id(jsonObject1.getString("vender_id"));
                                payment_status = jsonObject1.getString("status");
                                if (payment_status.equals("2")) {
                                    addtcart.setstatus("Order in Process");
                                } else if (payment_status.equals("3")) {
                                    addtcart.setstatus("Order in Cancel");
                                } else if (payment_status.equals("4")) {
                                    addtcart.setstatus("Order in Completed ");
                                }

                                Arraylist_historyodermodel.add(addtcart);
                                orderhistory_adapter = new Venderorderlist_Adapter(Orderlist_Activity.this, Arraylist_historyodermodel, refreshInterface);
                                Recyclerview_orderlistvender.setLayoutManager(new LinearLayoutManager(Orderlist_Activity.this, LinearLayoutManager.VERTICAL, false));
                                Recyclerview_orderlistvender.setAdapter(orderhistory_adapter);

                            }

                        }




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
                    Toast.makeText(Orderlist_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Orderlist_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Orderlist_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Orderlist_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Orderlist_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Orderlist_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Orderlist_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                map.put("status_type", "0");


                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }


    private void Get_FILETERDATA() {
        final ProgressDialog pd = new ProgressDialog(Orderlist_Activity.this);
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

                        String array_size = jsonArray.toString();
                        if( array_size.equals("[]"))
                        {
                            et_Record.setVisibility(View.VISIBLE);
                            Recyclerview_orderlistvender.setVisibility(View.GONE);


                        }
                        else {
                            et_Record.setVisibility(View.GONE);
                            Recyclerview_orderlistvender.setVisibility(View.VISIBLE);

                        }

                        for (int i = 0; i < jsonArray.length(); i++)

                        {
                            Histroyorder_Model addtcart = new Histroyorder_Model();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            payment_status = jsonObject1.getString("status");

                            if (payment_status.equals("1") || (payment_status.equals("2") || (payment_status.equals("3")))) {
                                addtcart.setvender_id(jsonObject1.getString("vender_id"));
                                addtcart.sethide_btn("0");
                                addtcart.setsubtotoal(jsonObject1.getString("total_amount"));
                                addtcart.settotoalpayamt(jsonObject1.getString("payment_amount"));
                                addtcart.settotdiscount(jsonObject1.getString("discount_amount"));
                                addtcart.setorderdate(jsonObject1.getString("created_at"));
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
                                Arraylist_historyodermodel.add(addtcart);
                                orderhistory_adapter = new Venderorderlist_Adapter(Orderlist_Activity.this, Arraylist_historyodermodel, refreshInterface);
                                Recyclerview_orderlistvender.setLayoutManager(new LinearLayoutManager(Orderlist_Activity.this, LinearLayoutManager.VERTICAL, false));
                                Recyclerview_orderlistvender.setAdapter(orderhistory_adapter);


                            }
                        }
                       /*     else {

                                et_Record.setVisibility(View.VISIBLE);
                                Recyclerview_orderlistvender.setVisibility(View.GONE);
                            }*/
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
                    Toast.makeText(Orderlist_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Orderlist_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Orderlist_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Orderlist_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Orderlist_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Orderlist_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Orderlist_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                map.put("status_type", Filter_status);


                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }

    @Override
    public void Refresh() {
        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");

        Get_OrderDetail();
    }

}
