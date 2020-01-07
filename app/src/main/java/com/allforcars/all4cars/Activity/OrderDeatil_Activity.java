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
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.OrderDetails_Adapter;
import com.allforcars.all4cars.Adapter.Orderhistory_Adapter;
import com.allforcars.all4cars.Model.Catergoyicons_Model;
import com.allforcars.all4cars.Model.Histroyorder_Model;
import com.allforcars.all4cars.Model.OrderDeatils_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Venderlogin.Activity.Orderdeatil_Activity;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderDeatil_Activity extends AppCompatActivity {

    String order_number;
    ImageView order_back;
    RecyclerView Recyclerview_orderdetail;
    OrderDetails_Adapter orderDetails_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<OrderDeatils_Model> Arraylist_orderDeatils;
    String user_id,usertype,ordernumber;
    int count=0;
    TextView txt_noofproduct,txt_ordersubtotal,txt_distotaldis,txt_ordertotalpayble;
    String subamt,totaldis,totalamt,discount_amount;
    Double Subtotal;

    DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_deatil);

        order_back = findViewById(R.id.order_back);
        Recyclerview_orderdetail = findViewById(R.id.Recyclerview_orderdetail);
        txt_noofproduct = findViewById(R.id.txt_noofproduct);
        txt_ordersubtotal = findViewById(R.id.txt_ordersubtotal);
        txt_distotaldis = findViewById(R.id.txt_distotaldis);
        txt_ordertotalpayble = findViewById(R.id.txt_ordertotalpayble);

        order_number= getIntent().getStringExtra("order_number");
        subamt= getIntent().getStringExtra("subamt");
        totaldis= getIntent().getStringExtra("totaldis");
        totalamt= getIntent().getStringExtra("totalamt");

        double d=Double.parseDouble(totalamt);
        double dd=Double.parseDouble(subamt);

        txt_ordersubtotal.setText(df.format(dd)+"₦");

        txt_ordertotalpayble.setText(df.format(d)+"₦");


        Recyclerview_orderdetail.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(OrderDeatil_Activity.this);
        Recyclerview_orderdetail.setLayoutManager(layoutManager);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");



        order_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        Get_OrderDetails();
    }


    private void Get_OrderDetails() {
        final ProgressDialog pd = new ProgressDialog(OrderDeatil_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"order_details";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Double sum=0.00;
                    Arraylist_orderDeatils = new ArrayList<>();
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        count = jsonArray.length();
                        String str = String.format("%02d", count);
                        txt_noofproduct.setText(str);

                        for (int i = 0; i < jsonArray.length(); i++)

                        {
                             JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                             OrderDeatils_Model addtcart = new OrderDeatils_Model();
                             addtcart.setproduct_name(jsonObject1.getString("product_name"));
                             addtcart.setproduct_image(jsonObject1.getString("banner_image"));
                             addtcart.setproduct_Pricetot(jsonObject1.getString("price"));
                             addtcart.setproduct_quantity(jsonObject1.getString("quantity"));
                             addtcart.setproduct_dis_Amount(jsonObject1.getString("discount_amount"));
                            addtcart.setunit(jsonObject1.getString("unit"));
                             addtcart.setdiscounthide("0");
                             discount_amount = jsonObject1.getString("discount_amount");

                            sum=sum+Double.parseDouble(discount_amount);

                            txt_distotaldis.setText(df.format(sum)+"₦");
                             Arraylist_orderDeatils.add(addtcart);


                        }


                        orderDetails_adapter = new OrderDetails_Adapter(OrderDeatil_Activity.this, Arraylist_orderDeatils);
                        Recyclerview_orderdetail.setLayoutManager(new LinearLayoutManager(OrderDeatil_Activity.this, LinearLayoutManager.VERTICAL, false));
                        Recyclerview_orderdetail.setAdapter(orderDetails_adapter);


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
                    Toast.makeText(OrderDeatil_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(OrderDeatil_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(OrderDeatil_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(OrderDeatil_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(OrderDeatil_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(OrderDeatil_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OrderDeatil_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                map.put("order_number", order_number);


                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }



}



