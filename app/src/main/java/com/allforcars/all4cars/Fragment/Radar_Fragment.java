package com.allforcars.all4cars.Fragment;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.Deatail_Activity;
import com.allforcars.all4cars.Model.Marker_Model;
import com.allforcars.all4cars.Model.RadarModelList;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.GPSTracker;
import com.allforcars.all4cars.classes.Utility;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import it.smasini.radar.RadarPoint;
import it.smasini.radar.RadarView;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Radar_Fragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    View fragmentView;
    LinearLayout C_qestion2_left,C_qestion2_right;
    private RadarView radarView;
    private ArrayList<RadarPoint> points = new ArrayList<RadarPoint>();
    TextView mProgressText;
    TextView mTrackingText;
    String  seekbarValue;
    String user_id;
    GPSTracker gps;
    SharedPreferences sharedPreferences;
    double longitudes,latitudes;
    String currentlatitude="0.00",Distance,Branchlist, currentlongtide="0.00",Catergorylist="";
    List<RadarModelList> radarModelLists=new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshL;
    ArrayList<Marker_Model> Arraylist_marker;
    String fld_admin_id,fld_email,company_name,phones,websie_url,Address,kilomiter,latitude,logitude,Banner_image,phone,vender_address;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_radar, container, false);


        radarView = (RadarView)fragmentView.findViewById(R.id.radar_view);
        ((SeekBar)fragmentView.findViewById(R.id.seek)).setOnSeekBarChangeListener(this);
        mProgressText = (TextView)fragmentView.findViewById(R.id.progress);
        mTrackingText = (TextView)fragmentView.findViewById(R.id.tracking);

        SharedPreferences sharedpreference = getContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        Catergorylist =sharedpreference.getString("Catergorylist","");
        seekbarValue =sharedpreference.getString("seekbarValue","");

//        sharedPreferences= getContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=sharedPreferences.edit();
//        editor.putString("Filter", "NotMatch");
//        editor.apply();

        if (seekbarValue.equals(""))
        {
            seekbarValue="30";

        }

        else {


        }





        gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) {
            latitudes = gps.getLatitude();
            longitudes = gps.getLongitude();

            currentlatitude = String.valueOf(latitudes);
            currentlongtide = String.valueOf(longitudes);

        } else {
            gps.showSettingsAlert();
        }




        radarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startAll();
                int poss;

                //Strname = Arraylist_marker.get(poss).getvender_name();


            }
        });


        radarView.setOnRadarPinClickListener(new RadarView.OnRadarPinClickListener() {
            @Override
            public void onPinClicked(String strCompanyName) {




//                Toast.makeText(getActivity(), strCompanyName, Toast.LENGTH_LONG).show();
               String title =  strCompanyName;

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
                        Banner_image=Arraylist_marker.get(i).getvender_bannerimage();

                    }



                }

                Intent ints = new Intent(getActivity(),Deatail_Activity.class);
                ints.putExtra("Map_show", "Map");
                ints.putExtra("Tabopens","Map");
                ints.putExtra("companynm", title);
                ints.putExtra("fld_admin_id", fld_admin_id);
                ints.putExtra("Email", fld_email);
                ints.putExtra("company_name", company_name);
                ints.putExtra("Phone", phones);
                ints.putExtra("km", kilomiter);
                ints.putExtra("url_link", websie_url);
                ints.putExtra("Address", Address);
                ints.putExtra("Banner_image", Banner_image);
                ints.putExtra("latitude", latitude);
                ints.putExtra("logitude", logitude);
                ints.putExtra("brachlist", Branchlist);
                startActivity(ints);





            }
        });



        double latitude=Double.parseDouble(currentlatitude);
        double longitude=Double.parseDouble(currentlongtide);
        //  createMarker(latitude,longitude,comppanyname,Km,phone);
        float flatitude = (float)latitude;
        float flongitude = (float)longitude;
        radarView.setReferencePoint(new RadarPoint("center", flatitude,flongitude));







        if (Catergorylist.equals(""))
        {

            if (isOnline()) {

                Get_listradarfirst_Api();
                startAll();
            } else {
                Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

            }
        }

        else {

            if (isOnline()) {
                Get_listonmap_Api();
                startAll();

            } else {
                Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

            }

        }




        return fragmentView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        //  mTrackingText.setText(getString(R.string.seekbar_tracking_off));
    }
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        mProgressText.setText(progress + " " + getString(R.string.seekbar_from_touch));
    }
    public void onStartTrackingTouch(SeekBar seekBar) {
        // mTrackingText.setText(getString(R.string.seekbar_tracking_on));
    }


    private void startAll(){
        points = new ArrayList<RadarPoint>();
        radarView.resetPoints();
        radarView.startAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try
                {
                    for(int r=0;r<radarModelLists.size();r++)
                    {
                        String strLogo= radarModelLists.get(r).getLogo();
                        String strCompanyName= radarModelLists.get(r).getCompany_name();
                        String strLatitude= radarModelLists.get(r).getLatitude();
                        String strLongitude= radarModelLists.get(r).getLongitude();
                        double latitude=Double.parseDouble(strLatitude);
                        double longitude=Double.parseDouble(strLongitude);
                        //  createMarker(latitude,longitude,comppanyname,Km,phone);
                        float flatitude = (float)latitude;
                        float flongitude = (float)longitude;



                     // String   strImages="http://itdevelopmentservices.com/all4cars/upload/logo/"+strLogo;

                       String   strImages="http://itdevelopmentservices.com/all4cars/upload/logo/location.png";



                        RadarPoint radarPoint = new RadarPoint(strCompanyName, flatitude,flongitude,strImages);
                        points.add(radarPoint);

                        radarView.setPoints(points);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, 10000);
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }


    public Radar_Fragment() {
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
                            RadarModelList radarModelList=new RadarModelList();
                            Marker_Model marker = new Marker_Model();
                            marker.setvender_id(jsonObject1.getString("fld_admin_id"));
                            marker.setvender_email(jsonObject1.getString("fld_email"));
                            Distance = jsonObject1.getString("distance");
                            phone = jsonObject1.getString("phone");
                            vender_address = jsonObject1.getString("address");
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

                            String    icons=  jsonObject1.getString("small_logo");
                            String     comppanyname = jsonObject1.getString("company_name");
                            String      getstrlatitude = jsonObject1.getString("latitude");
                            String     getstrlogtitude = jsonObject1.getString("longitude");

                            radarModelList.setCompany_name(comppanyname);
                            radarModelList.setLatitude(getstrlatitude);
                            radarModelList.setLongitude(getstrlogtitude);
                            radarModelList.setLogo(icons);
                            radarModelLists.add(radarModelList);
                            if(getstrlatitude.equals("null")||getstrlogtitude.equals("null") || icons.equals("null"))
                            {

                            }
                            else {


                                double latitude=Double.parseDouble(getstrlatitude);
                                double longitude=Double.parseDouble(getstrlogtitude);

                                float flatitude = (float)latitude;
                                float flongitude = (float)longitude;

                            }



                        }
                    }


                    else
                    {



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
                //  progressDialog.dismiss();
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

    public void  Get_listradarfirst_Api()

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
                            RadarModelList radarModelList=new RadarModelList();
                            Marker_Model marker = new Marker_Model();
                            marker.setvender_id(jsonObject1.getString("fld_admin_id"));
                            marker.setvender_email(jsonObject1.getString("fld_email"));
                            Distance = jsonObject1.getString("distance");
                            phone = jsonObject1.getString("phone");
                            vender_address = jsonObject1.getString("address");
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


                            String    icons=  jsonObject1.getString("small_logo");
                            String     comppanyname = jsonObject1.getString("company_name");
                            String      getstrlatitude = jsonObject1.getString("latitude");
                            String     getstrlogtitude = jsonObject1.getString("longitude");

                            radarModelList.setCompany_name(comppanyname);
                            radarModelList.setLatitude(getstrlatitude);
                            radarModelList.setLongitude(getstrlogtitude);
                            radarModelList.setLogo(icons);
                            radarModelLists.add(radarModelList);
                            if(getstrlatitude.equals("null")||getstrlogtitude.equals("null") || icons.equals("null"))
                            {

                            }
                            else {


                                double latitude=Double.parseDouble(getstrlatitude);
                                double longitude=Double.parseDouble(getstrlogtitude);

                                float flatitude = (float)latitude;
                                float flongitude = (float)longitude;

                            }



                        }
                    }


                    else
                    {



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
                //  progressDialog.dismiss();
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




}
