package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Model.InfoWindow;
import com.allforcars.all4cars.Model.Marker_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.CustomInfoWindowGoogleMap;
import com.allforcars.all4cars.classes.GPSTracker;
import com.allforcars.all4cars.classes.apicall.CustomInfoWindowGasPrice;
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

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class ComparePrice_Activity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    RelativeLayout Comparre_back;
    String comppanyname,phone,branch_list,Km,Distance,getstrlatitude="",getstrlogtitude="",Catergorylist="",name,name_key="1";
    TextView Name_text;
    GPSTracker gps;
    double longitudes=0.00,latitudes=0.00;
    String currentlatitude="0.00", currentlongtide="0.00",user_id,kilomiter,Banner_image,seekbarValue;
    ArrayList<Marker_Model> Arraylist_marker;
    String fld_admin_id,fld_email,company_name,phones,websie_url,Address,latitude,logitude;
    CustomInfoWindowGasPrice customInfoWindow;
    InfoWindow info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_price);


        Comparre_back =findViewById(R.id.Comparre_back);
        SupportMapFragment mapFragments = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_cpomare);
        mapFragments.getMapAsync(ComparePrice_Activity.this);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        Catergorylist =sharedpreference.getString("Catergorylist","");
        seekbarValue =sharedpreference.getString("seekbarValue","");

        if (seekbarValue.equals(""))
        {
            seekbarValue="30";

        }

        else {


        }


        Comparre_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        gps = new GPSTracker(ComparePrice_Activity.this);
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


        if (isOnline()) {
            Get_listonmap_Api();

        } else {
            Toast.makeText(ComparePrice_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

        }


    }






    public void  Get_listonmap_Api()

    {
        String urljsonobj_group = Base_URL+"gas_maping?longitude="+longitudes+"&latitude="+latitudes+"";
        final ProgressDialog progressDialog = new ProgressDialog(ComparePrice_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.getString("success").equalsIgnoreCase("true")) {

                        String Petrol="",Diesel="",Gas="";
                        Arraylist_marker = new ArrayList<>();
                        JSONArray jsonArray = response.getJSONArray("record");

                        for(int i=0;i<jsonArray.length();i++)

                        {


                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            Marker_Model marker = new Marker_Model();
                            comppanyname = jsonObject1.getString("company_name");
                            getstrlatitude = jsonObject1.getString("latitude");
                            getstrlogtitude = jsonObject1.getString("longitude");
                            Distance = jsonObject1.getString("distance");
                            phone = jsonObject1.getString("phone");
                            JSONArray jsonArray1=jsonObject1.getJSONArray("products");
                            String jsonarry = jsonArray1.toString();

                            if(!jsonarry.equals("[]"))
                            {
                                for(int j=0;j<jsonArray1.length();j++)

                                {
                                    JSONObject jsonObject2=jsonArray1.getJSONObject(j);

                                    {
                                        name = jsonObject2.getString("name");
                                        if(name.equals("Petrol") || name.equals("petrol"))
                                        {
                                            Petrol = jsonObject2.getString("price")+"₦";
                                        }
                                        else if(name.equals("Diesel")|| name.equals("diesel"))
                                        {
                                            Diesel = jsonObject2.getString("price")+"₦";
                                        }
                                        else if(name.equals("gas")|| name.equals("Gas"))
                                        {
                                            Gas = jsonObject2.getString("price")+"₦";
                                        }




                                    }



                                }
                            }
                            else
                            {
                                Petrol="0$";
                                Diesel="0$";
                                Gas="0$";
                            }

                            if(getstrlatitude.equals("null")||getstrlogtitude.equals("null") ||Petrol.equals("null")||Diesel.equals("null")||Gas.equals("null"))
                            {

                            }
                            else {

                                double latitude=Double.parseDouble(getstrlatitude);
                                double longitude=Double.parseDouble(getstrlogtitude);
                                createMarker(latitude,longitude,comppanyname,Petrol,Diesel,Gas);
                            }
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
                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                    Toast.makeText(ComparePrice_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(ComparePrice_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(ComparePrice_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(ComparePrice_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(ComparePrice_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(ComparePrice_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ComparePrice_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        branch_list=Arraylist_marker.get(i).getBranchlist();
                        Banner_image=Arraylist_marker.get(i).getvender_bannerimage(); }

                }


                Intent ints = new Intent(ComparePrice_Activity.this,Deatail_Activity.class);
                ints.putExtra("Map_show", "Compare_price");
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
                ints.putExtra("brachlist", branch_list);

                startActivity(ints);





            }
        });


    }




    public void createMarker(double latitude, double longitude,String comppanyname, String Petrol,String Diesel,String Gas) {



        InfoWindow   info = new InfoWindow();
        info.setStrTitle(comppanyname);
        info.setstrpetrol(Petrol);
        info.setstrDesial(Diesel);
        info.setstrGas(Gas);

        customInfoWindow = new CustomInfoWindowGasPrice(ComparePrice_Activity.this);
        mMap.setInfoWindowAdapter(customInfoWindow);




        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.fuel);
        LatLng Noida = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions().position(Noida)
                .title(comppanyname)
                .snippet(Petrol)
                .icon(icon);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(Noida, 13);
        // mMap.addMarker(markerOptions);
        mMap.animateCamera(yourLocation);
        Marker m = mMap.addMarker(markerOptions);
        m.setTag(info);
        m.showInfoWindow();




    }







    public boolean isOnline() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
}
