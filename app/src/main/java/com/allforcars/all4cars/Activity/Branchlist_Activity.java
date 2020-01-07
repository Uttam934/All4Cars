package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.Detaillistdata_Adapter;
import com.allforcars.all4cars.Adapter.Orderhistory_Adapter;
import com.allforcars.all4cars.Adapter.VenderBranch_Adapter;
import com.allforcars.all4cars.Model.Bonus_Model;
import com.allforcars.all4cars.Model.Histroyorder_Model;
import com.allforcars.all4cars.Model.InfoWindow;
import com.allforcars.all4cars.Model.Marker_Model;
import com.allforcars.all4cars.Model.Product_detail_Model;
import com.allforcars.all4cars.Model.VenderBranch_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.CustomInfoWindowGoogleMap;
import com.allforcars.all4cars.classes.GPSTracker;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Branchlist_Activity extends AppCompatActivity implements OnMapReadyCallback {

    private RelativeLayout forget_histroy;
    private  String user_id,usertype,payment_status;
    VenderBranch_Adapter venderBranch_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<VenderBranch_Model> Arraylist_brachlist;
    RecyclerView Recyclerview_brachlist;
    private LinearLayout record_notfund;
    private int count;
    String vendor_id,Banner_image,logo,Website,Distance;
    double longitudes,latitudes;
    String currentlatitude, currentlongtide;
    GPSTracker gps;;
    String fld_admin_id,fld_email,company_name,phones,websie_url,Address;
    String vender_address,comppanyname,phone,kilomiter,icons,Km,getstrlatitude="",getstrlogtitude="",Catergorylist="",barandlist,name_key="1";
    CustomInfoWindowGoogleMap customInfoWindow;
    ArrayList<Marker_Model> Arraylist_marker;
    InfoWindow info;
    private GoogleMap mMap;
    String ImageUrl="",latitude,logitude;
    Bitmap bitmap=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branchlist);

        forget_histroy=findViewById(R.id.forget_histroy);


        vendor_id=getIntent().getStringExtra("fld_admin_id");
        Banner_image=getIntent().getStringExtra("Banner_image");
        icons=getIntent().getStringExtra("logo");
        Website=getIntent().getStringExtra("url_link");

        SupportMapFragment mapFragments = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_brachlistu);
        mapFragments.getMapAsync(Branchlist_Activity.this);


        gps = new GPSTracker(Branchlist_Activity.this);
        if (gps.canGetLocation()) {
            latitudes = gps.getLatitude();
            longitudes = gps.getLongitude();

            currentlatitude = String.valueOf(latitudes);
            currentlongtide = String.valueOf(longitudes);

        } else {
            gps.showSettingsAlert();
        }







        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");


        forget_histroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        Branchlist();
    }






    public void  Branchlistdd()

    {

        String urljsonobj_group = Base_URL+"vendor_branch_list?vendor_id="+vendor_id+"&longitude="+longitudes+"&latitude="+latitudes+"";
        final ProgressDialog progressDialog = new ProgressDialog(Branchlist_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.getString("success").equalsIgnoreCase("true")) {
                        JSONObject jsonObject = response.getJSONObject("record");

                          JSONArray jsonArray = jsonObject.getJSONArray("branch_list");

                          Arraylist_brachlist = new ArrayList<>();
                          for (int i = 0; i < jsonArray.length(); i++)
                          {

                              JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                              VenderBranch_Model brachvendr = new VenderBranch_Model();
                              brachvendr.setBranch_id(jsonObject1.getString("branch_id"));
                              brachvendr.setBranch_address(jsonObject1.getString("branch_address"));
                              brachvendr.setbranch_email(jsonObject1.getString("branch_email"));
                              brachvendr.setbranch_phone(jsonObject1.getString("branch_phone"));
                              brachvendr.setlatitude(jsonObject1.getString("latitude"));
                              brachvendr.setlogitituede(jsonObject1.getString("longitude"));
                              brachvendr.setBranch_name(jsonObject1.getString("company_name"));
                              brachvendr.setBranch_km(jsonObject1.getString("distance")+"Km");
                              brachvendr.setBranch_image(jsonObject1.getString("small_logo"));


                              Arraylist_brachlist.add(brachvendr);

                          }


                                venderBranch_adapter = new VenderBranch_Adapter(Branchlist_Activity.this, Arraylist_brachlist);
                                Recyclerview_brachlist.setLayoutManager(new LinearLayoutManager(Branchlist_Activity.this, LinearLayoutManager.VERTICAL, false));
                                Recyclerview_brachlist.setAdapter(venderBranch_adapter);

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
                    Toast.makeText(Branchlist_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Branchlist_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Branchlist_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Branchlist_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Branchlist_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Branchlist_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Branchlist_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }


    public void  Branchlist()

    {

        String urljsonobj_group = Base_URL+"vendor_branch_list?vendor_id="+vendor_id+"&longitude="+longitudes+"&latitude="+latitudes+"";
        final ProgressDialog progressDialog = new ProgressDialog(Branchlist_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.getString("success").equalsIgnoreCase("true")) {

                        Arraylist_marker = new ArrayList<>();
                        JSONObject jsonObject = response.getJSONObject("record");

                        JSONArray jsonArray = jsonObject.getJSONArray("branch_list");

                        for(int i=0;i<jsonArray.length();i++)

                        {


                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            Marker_Model marker = new Marker_Model();


                            marker.setvender_id(vendor_id);
                            marker.setvender_email(jsonObject1.getString("branch_email"));
                            Distance = jsonObject1.getString("distance");
                            marker.setvender_km(Distance + " " + "Km");
                            marker.setvender_name(jsonObject1.getString("company_name"));
                            marker.setvender_Addrs(jsonObject1.getString("branch_address"));
                            marker.setvender_phone(jsonObject1.getString("branch_phone"));
                            marker.setvender_website(Website);
                            marker.setvender_bannerimage(Banner_image);
                            marker.setlattitude(jsonObject1.getString("latitude"));
                            marker.setlongitude(jsonObject1.getString("longitude"));
                            Arraylist_marker.add(marker);
                            icons=  jsonObject1.getString("small_logo");
                            comppanyname = jsonObject1.getString("company_name");
                            getstrlatitude = jsonObject1.getString("latitude");
                            getstrlogtitude = jsonObject1.getString("longitude");
                            if(getstrlatitude.equals("null")|| (getstrlatitude.equals("")|| getstrlogtitude.equals("")|| getstrlogtitude.equals("null")||icons.equals("null")))
                            {

                            }
                            else {


                                phone = jsonObject1.getString("branch_phone");
                                vender_address = jsonObject1.getString("branch_address");
                                double latitude=Double.parseDouble(getstrlatitude);
                                double longitude=Double.parseDouble(getstrlogtitude);

                                createMarker(latitude,longitude,comppanyname,Distance,phone,icons,vender_address);
                            }


                            progressDialog.dismiss();

                        }
                    }


                    else
                    {

                        progressDialog.dismiss();

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
                    Toast.makeText(Branchlist_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Branchlist_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Branchlist_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Branchlist_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Branchlist_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Branchlist_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Branchlist_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // TODO
                String title =  marker.getTitle();
                for(int i=0;i<Arraylist_marker.size();i++){
                    company_name= Arraylist_marker.get(i).getvender_name();
                    if(company_name.equals(title))
                    {
                        fld_admin_id= Arraylist_marker.get(i).getvender_id();
                        fld_email    = Arraylist_marker.get(i).getvender_email();
                        company_name= Arraylist_marker.get(i).getvender_name();
                        phones= Arraylist_marker.get(i).getvender_phone();
                        kilomiter= Arraylist_marker.get(i).getvender_km();
                        websie_url=Arraylist_marker.get(i).getvender_website();
                        Address=Arraylist_marker.get(i).getvender_Addrs();
                        latitude=Arraylist_marker.get(i).getlattitude();
                        logitude=Arraylist_marker.get(i).getlongitude();
                        Banner_image=Arraylist_marker.get(i).getvender_bannerimage(); }

                }

                Intent ints = new Intent(Branchlist_Activity.this,Deatail_Activity.class);
                ints.putExtra("Map_show", "Map");
                ints.putExtra("Tabopens","Map");
                ints.putExtra("companynm", title);
                ints.putExtra("fld_admin_id", fld_admin_id);
                ints.putExtra("Email", fld_email);
                ints.putExtra("company_name", company_name);
                ints.putExtra("Phone", phone);
                ints.putExtra("km", kilomiter);
                ints.putExtra("url_link", websie_url);
                ints.putExtra("Address", Address);
                ints.putExtra("Banner_image", Banner_image);
                ints.putExtra("latitude", latitude);
                ints.putExtra("logitude", logitude);
                ints.putExtra("brachlist", "100");
                startActivity(ints);






            }
        });


    }




    public void createMarker(double latitude, double longitude,String comppanyname, String Km,String phone,String icons,String vender_address) {

        InfoWindow   info = new InfoWindow();
        info.setStrTitle(comppanyname);
        info.setStrDistance(Km);
        info.setStrPhoneNumber (phone);
        info.setstraddress (vender_address);
        // info.setStrUserIcon ();

        try{
            customInfoWindow = new CustomInfoWindowGoogleMap(Branchlist_Activity.this);
            mMap.setInfoWindowAdapter(customInfoWindow);
        }catch (NullPointerException e){

        }



        try
        {
            ImageUrl="http://itdevelopmentservices.com/all4cars/upload/logo/"+icons;

            loadBitMap(ImageUrl,comppanyname,latitude,longitude,info);

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    private void loadBitMap(final String image1, final String comppanyname, final double latitude, final double longitude, final InfoWindow info)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                if (Looper.myLooper()==null) {
                    Looper.prepare();
                }
                try {
                    bitmap = Glide.with(Branchlist_Activity.this)
                            .load(image1)
                            .asBitmap()
                            .centerCrop()
                            // .transform(new CircleTransform(getActivity()))
                            .into(50, 50)
                            .get();
                } catch (final ExecutionException e) {
                    Log.e("", e.getMessage());
                } catch (final InterruptedException e) {
                    Log.e("", e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void dummy) {
                // progressDialog.hide();
                if (null != bitmap) {
                    // The full bitmap should be available here
                    // imgpicture.setImageBitmap(theBitmap);
                    Log.d("", "Image loaded");

                    BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);
                    LatLng Noida = new LatLng(latitude, longitude);
                    MarkerOptions markerOptions = new MarkerOptions().position(Noida)
                            .title(comppanyname)
                            .snippet(Km)
                            .icon(icon);
                    CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(Noida, 11);
                    // mMap.addMarker(markerOptions);
                    mMap.animateCamera(yourLocation);
                    Marker m = mMap.addMarker(markerOptions);
                    m.setTag(info);

                    m.showInfoWindow();
                }

            }

            @Override
            protected void onPreExecute() {
                //textView.setText("Hello !!!");
                // progressDialog.show();
                super.onPreExecute();
            }

        }.execute();
    }



}
