package com.allforcars.all4cars.Venderlogin.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.Categoryicon_Adapter;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Venderlogin.Adapter.Categorylist_Adapter;
import com.allforcars.all4cars.Venderlogin.Adapter.ProductService_Adapter;
import com.allforcars.all4cars.Venderlogin.model.Categorylist_Model;
import com.allforcars.all4cars.Venderlogin.model.ProudctService_Model;
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

public class VenderCategory_Activity extends AppCompatActivity {

    RecyclerView Recyclerview_category;
    Categorylist_Adapter categorylist_adapter;
    ArrayList<Categorylist_Model> categorylist_array_list;
    RecyclerView.LayoutManager  anothermanger;
    String user_id,usertype;
    RelativeLayout categroy_backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender_category);

        Recyclerview_category= findViewById(R.id.Recyclerview_category);
        categroy_backbtn= findViewById(R.id.categroy_backbtn);


        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");


        Recyclerview_category.setHasFixedSize(true);
        anothermanger = new LinearLayoutManager(this);
        Recyclerview_category.setLayoutManager(anothermanger);

        categroy_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        Get_product_detail();


    }


    private void Get_product_detail() {
        final ProgressDialog pd = new ProgressDialog(VenderCategory_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"category_list_byvender";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                    JSONObject jsonObject = new JSONObject(response);
                    categorylist_array_list= new ArrayList<>();
                    JSONArray jsonArray=jsonObject.getJSONArray("record");
                    for(int i=0;i<jsonArray.length();i++)

                    {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Categorylist_Model categorylist = new Categorylist_Model();

                        categorylist.setCategory_id(jsonObject1.getString("category_id"));
                        categorylist.setCategory_name(jsonObject1.getString("category_names"));
                        categorylist.setCategory_image(jsonObject1.getString("category_image"));
                        categorylist.setall4carcommison(jsonObject1.getString("all4carscomission"));
                        categorylist.setCategory_discoutn(jsonObject1.getString("userdiscount"));

                        categorylist_array_list.add(categorylist);
                        categorylist_adapter = new Categorylist_Adapter(VenderCategory_Activity.this, categorylist_array_list);
                        Recyclerview_category.setAdapter(categorylist_adapter);



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
                    Toast.makeText(VenderCategory_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(VenderCategory_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(VenderCategory_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(VenderCategory_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(VenderCategory_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(VenderCategory_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(VenderCategory_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

}
