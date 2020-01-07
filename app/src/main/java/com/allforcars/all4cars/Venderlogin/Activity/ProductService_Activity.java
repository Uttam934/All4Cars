package com.allforcars.all4cars.Venderlogin.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Venderlogin.Adapter.ProductService_Adapter;
import com.allforcars.all4cars.Venderlogin.model.ProudctService_Model;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.RecyclerTouchListener;
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

public class ProductService_Activity extends AppCompatActivity {

    RelativeLayout backbtn;
    RecyclerView Recyclerview_product;
    ProductService_Adapter productService_adapter;
    ArrayList<ProudctService_Model> productservice_array_list;
    RecyclerView.LayoutManager  anothermanger;
    String user_id;
    CardView addproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_service);

        backbtn= findViewById(R.id.backbtn);
        Recyclerview_product= findViewById(R.id.Recyclerview_product);
        addproduct= findViewById(R.id.addproduct);
        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");


        Recyclerview_product.setHasFixedSize(true);
        anothermanger = new LinearLayoutManager(this);
        Recyclerview_product.setLayoutManager(anothermanger);

        Recyclerview_product.addOnItemTouchListener(new RecyclerTouchListener(ProductService_Activity.this, Recyclerview_product, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


                Intent intent = new Intent(ProductService_Activity.this, EditProduct_Activity.class);
                intent.putExtra("prouduct_id", productservice_array_list.get(position).getProduct_id());
                intent.putExtra("category_id", productservice_array_list.get(position).getCategory_id());

                startActivity(intent);




            }
            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(getActivity(), "Long press on position :"+position, Toast.LENGTH_LONG).show();
            }
        }));


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductService_Activity.this, AddProduct_Activity.class);
                startActivity(intent);
            }
        });

        Get_product_detail();
    }

    private void Get_product_detail() {
        final ProgressDialog pd = new ProgressDialog(ProductService_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"product_list_byid";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    JSONObject jsonObject = new JSONObject(response);
                    productservice_array_list= new ArrayList<>();
                    JSONArray jsonArray=jsonObject.getJSONArray("record list");
                    for(int i=0;i<jsonArray.length();i++)

                    {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        ProudctService_Model proudctservice = new ProudctService_Model();

                        proudctservice.setProduct_id(jsonObject1.getString("id"));
                        proudctservice.setProduct_name(jsonObject1.getString("name"));
                        proudctservice.setProduct_image(jsonObject1.getString("banner_image"));
                        proudctservice.setProduct_price(jsonObject1.getString("price"));
                        proudctservice.setCategory_id(jsonObject1.getString("category"));

                        productservice_array_list.add(proudctservice);
                        productService_adapter = new ProductService_Adapter(ProductService_Activity.this, productservice_array_list);
                        Recyclerview_product.setAdapter(productService_adapter);



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
                    Toast.makeText(ProductService_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(ProductService_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(ProductService_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(ProductService_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(ProductService_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(ProductService_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProductService_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("user_id", user_id);

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");


        Get_product_detail();
    }
}
