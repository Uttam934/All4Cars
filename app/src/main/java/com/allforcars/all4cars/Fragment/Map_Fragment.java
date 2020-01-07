package com.allforcars.all4cars.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.Changepassword_Activity;
import com.allforcars.all4cars.Activity.Deatail_Activity;
import com.allforcars.all4cars.Activity.Home_Activity;
import com.allforcars.all4cars.Activity.Login_Activity;
import com.allforcars.all4cars.Activity.Mycart_Activity;
import com.allforcars.all4cars.Adapter.Fuelsearcher_Adapter;
import com.allforcars.all4cars.Model.Feaulserher_Model;
import com.allforcars.all4cars.Model.InfoWindow;
import com.allforcars.all4cars.Model.Marker_Model;
import com.allforcars.all4cars.Model.Notification_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.CustomInfoWindowGoogleMap;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.allforcars.all4cars.classes.Utility.Base_URL;


public class Map_Fragment extends Fragment implements OnMapReadyCallback
{
    View fragmentView;
    private GoogleMap mMap;
    String vender_address,comppanyname,phone,icons,Km,Distance,getstrlatitude="",getstrlogtitude="",Branchlist,Catergorylist="",barandlist,name_key="1";
    double distanceInMiles =0;
    double Floatkm=0;
    ImageView imageView;
    SharedPreferences sharedPreferences;
    GPSTracker gps;
    double longitudes=0.00,latitudes=0.00;
    String currentlatitude="0.00", currentlongtide="0.00",user_id,kilomiter,Banner_image,seekbarValue;
    CustomInfoWindowGoogleMap customInfoWindow;
    ArrayList<Marker_Model> Arraylist_marker;
    InfoWindow info;
    String ImageUrl="",latitude,logitude;
    Bitmap bitmap=null;
    String fld_admin_id,fld_email,company_name,phones,websie_url,Address;
    SwipeRefreshLayout mSwipeRefresh;
    Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_map, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SharedPreferences sharedpreference = getContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        Catergorylist =sharedpreference.getString("Catergorylist","");
        seekbarValue =sharedpreference.getString("seekbarValue","");

        sharedPreferences= getContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Filter", "NotMatch");
        editor.apply();

        if (seekbarValue.equals(""))
        {
            seekbarValue="30";

        }

        else {


        }




        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapone);
        map.getMapAsync(this);



        gps = new GPSTracker(getActivity());
        if (gps.canGetLocation())
        {
            latitudes = gps.getLatitude();
            longitudes = gps.getLongitude();

            currentlatitude = String.valueOf(latitudes);
            currentlongtide = String.valueOf(longitudes);

        }
        else
        {
            gps.showSettingsAlert();
        }



        if (Catergorylist.equals(""))
        {

            if (isOnline()) {

                handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Get_firsttime_Api();
                    }
                },1000);


            } else {
                Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

            }
        }

        else {

            if (isOnline()) {


                handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Get_listonmap_Api();
                    }
                },1000);


            } else {
                Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

            }

        }



        // imageView.setImageBitmap(bitmap);

        return fragmentView;
    }







    public void  Get_listonmap_Api()

    {

        String urljsonobj_group = Base_URL+"filter_multiple_category?longitude="+longitudes+"&latitude="+latitudes+"&category="+Catergorylist+"&rang="+seekbarValue+"";
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.getString("success").equalsIgnoreCase("true")) {

                        Arraylist_marker = new ArrayList<>();
                        JSONArray jsonArray=response.getJSONArray("record");
                        for(int i=0;i<jsonArray.length();i++)

                        {


                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            Marker_Model marker = new Marker_Model();
                            marker.setvender_id(jsonObject1.getString("fld_admin_id"));
                            marker.setvender_email(jsonObject1.getString("fld_email"));
                            Distance = jsonObject1.getString("distance");
                            marker.setvender_km(Distance + " " + "Km");
                            marker.setvender_name(jsonObject1.getString("company_name"));
                            marker.setvender_Addrs(jsonObject1.getString("address"));
                            marker.setvender_phone(jsonObject1.getString("phone"));
                            marker.setvender_website(jsonObject1.getString("website_url"));
                            marker.setvender_bannerimage(jsonObject1.getString("image"));
                            marker.setlattitude(jsonObject1.getString("latitude"));
                            marker.setlongitude(jsonObject1.getString("longitude"));
                            marker.setBranchlist(jsonObject1.getString("branch_number"));

                            Arraylist_marker.add(marker);
                            icons=  jsonObject1.getString("small_logo");
                            comppanyname = jsonObject1.getString("company_name");
                            getstrlatitude = jsonObject1.getString("latitude");
                            getstrlogtitude = jsonObject1.getString("longitude");
                            if(getstrlatitude.equals("null")||getstrlogtitude.equals("null")||icons.equals("null"))
                            {

                            }
                            else {


                                phone = jsonObject1.getString("phone");
                                vender_address = jsonObject1.getString("address");
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
                    Toast.makeText(getActivity(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getActivity(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getActivity(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getActivity(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(getActivity(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }

    public void  Get_firsttime_Api()

    {

        String urljsonobj_group = Base_URL+"filter_multiple_category?longitude="+longitudes+"&latitude="+latitudes+"&category="+"0"+"&rang="+seekbarValue+"";
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.getString("success").equalsIgnoreCase("true")) {

                        Arraylist_marker = new ArrayList<>();
                        JSONArray jsonArray=response.getJSONArray("record");
                        for(int i=0;i<jsonArray.length();i++)

                        {


                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            Marker_Model marker = new Marker_Model();
                            marker.setvender_id(jsonObject1.getString("fld_admin_id"));
                            marker.setvender_email(jsonObject1.getString("fld_email"));
                            marker.setvender_km(Distance + " " + "Km");
                            marker.setvender_name(jsonObject1.getString("company_name"));
                            marker.setvender_Addrs(jsonObject1.getString("address"));
                            marker.setvender_phone(jsonObject1.getString("phone"));
                            marker.setvender_website(jsonObject1.getString("website_url"));
                            marker.setvender_bannerimage(jsonObject1.getString("image"));
                            marker.setlattitude(jsonObject1.getString("latitude"));
                            marker.setlongitude(jsonObject1.getString("longitude"));
                            marker.setBranchlist(jsonObject1.getString("branch_number"));
                            Arraylist_marker.add(marker);
                            icons=  jsonObject1.getString("small_logo");
                            comppanyname = jsonObject1.getString("company_name");
                            getstrlatitude = jsonObject1.getString("latitude");
                            getstrlogtitude = jsonObject1.getString("longitude");
                            if(getstrlatitude.equals("null")||getstrlogtitude.equals("null")||icons.equals("null"))
                            {

                            }
                            else {

                                Distance = jsonObject1.getString("distance");
                                phone = jsonObject1.getString("phone");
                                vender_address = jsonObject1.getString("address");
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
                    Toast.makeText(getActivity(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getActivity(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getActivity(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getActivity(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(getActivity(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Branchlist=Arraylist_marker.get(i).getBranchlist();
                        Banner_image=Arraylist_marker.get(i).getvender_bannerimage(); }

                }

                Intent ints = new Intent(getActivity(),Deatail_Activity.class);
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
                ints.putExtra("brachlist", Branchlist);
                ints.putExtra("latitude", latitude);
                ints.putExtra("logitude", logitude);
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
            customInfoWindow = new CustomInfoWindowGoogleMap(getActivity());
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
                    bitmap = Glide.with(getActivity())
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
                    CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(Noida, 12);
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



    public boolean isOnline() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }



    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }




    public Map_Fragment() {
    }





}
