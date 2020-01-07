package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.FilterBrand_Adapter;
import com.allforcars.all4cars.Adapter.Filtertype_Adapter;
import com.allforcars.all4cars.Model.Brand_Model;
import com.allforcars.all4cars.Model.Property_Model;
import com.allforcars.all4cars.Model.Type_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.GPSTracker;
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
import java.util.List;
import java.util.Map;

public class Filter_Activity extends AppCompatActivity {

    String user_id;
    TextView btn_type, apply_btn, btn_Brand;
    ImageView img_back_filter;
    RecyclerView Recyclerview_producttype, Recyclerview_product_brand;
    FilterBrand_Adapter filterBrand_Adapter;
    Filtertype_Adapter filtertype_adapter;
    GPSTracker gps;
    RecyclerView.LayoutManager layoutManager, anothermanger;
    ArrayList<Brand_Model> brand_models;
    ArrayList<Type_Model> type_models;
    ArrayList<Property_Model> property_models;
    double longitudes, latitudes;
    String currentlatitude, currentlongtide, price_amt;
    private List<String> srtinglist = new ArrayList<>();
    String srtProductlist = "";
    private List<String> srtingBrandlist = new ArrayList<>();
    String strBrandlist = "";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        btn_type = findViewById(R.id.btn_type);
        apply_btn = findViewById(R.id.apply_btn);
        img_back_filter = findViewById(R.id.img_back_filter);


        Recyclerview_producttype = (RecyclerView) findViewById(R.id.Recyclerview_producttype);
        layoutManager = new LinearLayoutManager(this);
        Recyclerview_producttype.setHasFixedSize(true);
        Recyclerview_producttype.setLayoutManager(layoutManager);


        btn_type.setBackgroundColor(Color.parseColor("#20c449"));

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id = sharedpreference.getString("user_id", "");

        gps = new GPSTracker(Filter_Activity.this);
        if (gps.canGetLocation()) {
            latitudes = gps.getLatitude();
            longitudes = gps.getLongitude();

            currentlatitude = String.valueOf(latitudes);
            currentlongtide = String.valueOf(longitudes);

        } else {
            gps.showSettingsAlert();
        }


        btn_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_type.setBackgroundColor(Color.parseColor("#20c449"));


            }
        });

        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences= getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("Catergorylist", srtProductlist);
                editor.putString("Filter", "Filter");
                editor.apply();

              //  onBackPressed();

                Intent ints = new Intent(Filter_Activity.this, Home_Activity.class);
                ints.putExtra("filter_key", "filter_value");
                ints.putExtra("name_key", "true");
                ints.putExtra("Catergorylist", srtProductlist);
                ints.putExtra("barandlist", strBrandlist);
                startActivity(ints);



            }
        });


        img_back_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();


            }
        });


        Get_Typelist_Api();




    }






    private void Get_Typelist_Api() {
        final ProgressDialog pd = new ProgressDialog(Filter_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll = Utility.Base_URL + "category_list";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                    JSONObject jsonObject = new JSONObject(response);
                    type_models = new ArrayList<>();
                    srtinglist.clear();

                    //  Recyclerview_product_brand.setVisibility(View.GONE);
                    JSONArray jsonArray = jsonObject.getJSONArray("record");

                    for (int i = 0; i < jsonArray.length(); i++)


                    {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Type_Model typemedl = new Type_Model();
                        typemedl.settype_id(jsonObject1.getString("id"));
                        typemedl.settype_name(jsonObject1.getString("name"));
                        typemedl.settype_logo(jsonObject1.getString("icon"));

                        type_models.add(typemedl);
                        filtertype_adapter = new Filtertype_Adapter(Filter_Activity.this, type_models, new Filtertype_Adapter.Filtertype_AdapterListener() {
                            @Override
                            public void isCheckCheckBox(Type_Model type_model, boolean checkBox, int position) {

                                if (checkBox) {
                                    srtinglist.add(type_model.gettype_id());

                                   // Categrylist.add(srtinglist);

                                    srtProductlist = TextUtils.join(",", srtinglist);
                                  //  Toast.makeText(Filter_Activity.this, srtProductlist, Toast.LENGTH_SHORT).show();
                                }

                                else {
                                    try {
                                        srtinglist.remove(type_model.gettype_id());
                                        Object[] st = srtinglist.toArray();
                                        for (Object s : st) {
                                            if (srtinglist.indexOf(s) != srtinglist.lastIndexOf(s)) {
                                                srtinglist.remove(srtinglist.lastIndexOf(s));
                                            }
                                        }
                                        srtProductlist = TextUtils.join(",", srtinglist);

                                    } catch (IndexOutOfBoundsException a) {
                                        a.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    //  ((HideContactListAdapter)recyclerView.getAdapter()).removeItem(position);


                                }
                            }
                        });
                        Recyclerview_producttype.setAdapter(filtertype_adapter);


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
                    Toast.makeText(Filter_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Filter_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Filter_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Filter_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Filter_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Filter_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Filter_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }





    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id = sharedpreference.getString("user_id", "");

        gps = new GPSTracker(Filter_Activity.this);
        if (gps.canGetLocation()) {
            latitudes = gps.getLatitude();
            longitudes = gps.getLongitude();

            currentlatitude = String.valueOf(latitudes);
            currentlongtide = String.valueOf(longitudes);

        } else {
            gps.showSettingsAlert();
        }


        Get_Typelist_Api();



    }
}