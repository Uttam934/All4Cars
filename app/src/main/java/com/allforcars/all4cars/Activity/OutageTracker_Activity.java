package com.allforcars.all4cars.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.RedeemHistory_Adapter;
import com.allforcars.all4cars.Model.Notification_Model;
import com.allforcars.all4cars.Model.RedeemHistory_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class OutageTracker_Activity extends AppCompatActivity {


    RelativeLayout forget_outctaker;
    String strvalue,btn,latitude,longitude,str_strtingpoint,str_destinaitonpoint,str_fuelselcted,Price_amt="null",str_parmilage,Starpoint,Destinationpoint,strdistance,strdurationTime;
    EditText pickup_startupoint, pickup_distination;
    PlacePicker.IntentBuilder builder;
    private int PLACE_PICKER_REQUEST = 1;
    ArrayAdapter adapter;
    Spinner spinner_carmilage,spinner_fuel;
    Double lat1,lng1,lat2,lng2,totalDistance,Fueatlrate,FuelCost,Tot_Distance;
    DecimalFormat df = new DecimalFormat("####0.00");
    String spinner_cal_type="",str_cal_type="",str_pay_form="",str_total="",spinner_pay_form="";;
    private Button buttonCalculate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outage_tracker);

        forget_outctaker = findViewById(R.id.forget_outctaker);
        pickup_startupoint = findViewById(R.id.pickup_startupoint);
        pickup_distination = findViewById(R.id.pickup_distination);
        spinner_carmilage = findViewById(R.id.spinner_carmilage);
        buttonCalculate = findViewById(R.id.btn_calculate);
        spinner_fuel = findViewById(R.id.spinner_fuel);

        adapter = new ArrayAdapter(OutageTracker_Activity.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.option_milage));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_carmilage.setAdapter(adapter);

        adapter = new ArrayAdapter(OutageTracker_Activity.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.option_feaul));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fuel.setAdapter(adapter);



        spinner_fuel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                str_fuelselcted = spinner_fuel.getSelectedItem().toString().trim();
                if (str_fuelselcted.equals("Petrol"))
                {
                    str_fuelselcted = "Petrol";
                    Get_Redmeehistory();
                }
                else if (str_fuelselcted.equals("Diesel"))
                {
                    str_fuelselcted = "Diesel";
                    Get_Redmeehistory();
                }
                else
                {

                }




            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        spinner_carmilage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                str_parmilage = spinner_carmilage.getSelectedItem().toString().trim();



                str_cal_type=spinner_carmilage.getSelectedItem().toString();
                str_pay_form=spinner_fuel.getSelectedItem().toString();
                str_strtingpoint=pickup_startupoint.getText().toString();
                str_destinaitonpoint=pickup_distination.getText().toString();

                if (str_parmilage.equals("Milage per kilometer"))
                {

                }
                else
                {
                    validation_user();
                }






            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });



        forget_outctaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        pickup_startupoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               btn="1"  ;
                builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(OutageTracker_Activity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //  totalDistance=  distFrom(lat1,lng1,lat2,lng2);



            }
        });
        pickup_distination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn="2";
                builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(OutageTracker_Activity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        if (ContextCompat.checkSelfPermission(OutageTracker_Activity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(OutageTracker_Activity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(OutageTracker_Activity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }


    }

    public void  Get_Redmeehistory()
    {

        String urljsonobj_group = Base_URL+"get_fuel_price?lat="+lat1+"&lng="+lng1+"&fuel="+str_fuelselcted+"";
        final ProgressDialog progressDialog = new ProgressDialog(OutageTracker_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                         JSONObject jsonObject = response.getJSONObject("res");
                           Price_amt = jsonObject.getString("price");
//                           Deseal = jsonObject.getString("bonus_point");






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
                    Toast.makeText(OutageTracker_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(OutageTracker_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(OutageTracker_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(OutageTracker_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(OutageTracker_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(OutageTracker_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OutageTracker_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                 latitude = String.valueOf(place.getLatLng().latitude);
                longitude = String.valueOf(place.getLatLng().longitude);


                String address = String.format("%s", place.getAddress());
               stBuilder.append(placename+" ,");
//                stBuilder.append("\n");
//                stBuilder.append("Latitude: ");
//                stBuilder.append(latitude);
//                stBuilder.append("\n");
//                stBuilder.append("Logitude: ");
//                stBuilder.append(longitude);
//                stBuilder.append("\n");
                stBuilder.append("");
                stBuilder.append(address);
                if(btn.equals("1"))
                {
                    pickup_startupoint.setText(stBuilder.toString());
                    Starpoint = stBuilder.toString();

                            lat1 = Double.parseDouble(latitude);
                            lng1=Double.parseDouble(longitude);
                }
                else
                {
                    pickup_distination.setText(stBuilder.toString());
                    Destinationpoint =stBuilder.toString();
                    lat2 = Double.parseDouble(latitude);
                    lng2=Double.parseDouble(longitude);
                }


            }
        }
    }

    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius =6371;  //this is in miles I believe
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;
        return dist;


    }


    public void  Get_Disatance()
    {

        String urljsonobj_group ="https://maps.googleapis.com/maps/api/distancematrix/json?origins="+lat1+","+lng1+"&destinations="+lat2+","+lng2+"&mode=driving&key=AIzaSyAB6At98ZBAhzPWv_tdixMpaw2IVbpB7Ak";

        final ProgressDialog progressDialog = new ProgressDialog(OutageTracker_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONArray jsonArray = response.getJSONArray("rows");
                    for (int i = 0; i < jsonArray.length(); i++)

                    {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONArray elements = jsonObject1.getJSONArray("elements");

                        for (int r = 0; r < elements.length(); r++)

                        {
                            JSONObject jsonObject2 = elements.getJSONObject(i);
                            JSONObject strDistance=jsonObject2.getJSONObject("distance");
                            strdistance=strDistance.getString("text");
                             strvalue=strDistance.getString("value");
                            JSONObject strduration=jsonObject2.getJSONObject("duration");
                            strdurationTime=strduration.getString("text");
                            String strdurationvalue=strduration.getString("value");
                             Tot_Distance= Double.parseDouble(strvalue)/1000;

                            Fueatlrate= Tot_Distance/Double.parseDouble(str_parmilage);
                            FuelCost = Fueatlrate*Double.parseDouble(Price_amt);

                            Intent intent = new Intent(OutageTracker_Activity.this, TripcostDeatil_Activity.class);
                            intent.putExtra("Distance",strdistance);
                            intent.putExtra("Perlistcost",Price_amt);
                            intent.putExtra("Totalcost",String.valueOf(FuelCost));
                            intent.putExtra("TotalTime",strdurationTime);
                            startActivity(intent);


                        }



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
                    Toast.makeText(OutageTracker_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(OutageTracker_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(OutageTracker_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(OutageTracker_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(OutageTracker_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(OutageTracker_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OutageTracker_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }


    public void validation_user(){


        int pos =spinner_carmilage.getSelectedItemPosition();
        int post =spinner_fuel.getSelectedItemPosition();
        if(pos!=0)
        {
            spinner_cal_type = spinner_carmilage.getSelectedItem().toString();
        }
        else{
            Toast toast = Toast.makeText(OutageTracker_Activity.this,"Select Km/l", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        if(!str_cal_type.equals("Select km/l"))
        {
            spinner_cal_type = spinner_carmilage.getSelectedItem().toString();
        }
        else{

            Toast toast = Toast.makeText(OutageTracker_Activity.this,"Select Km/l", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }

        if(post!=0)
        {
            spinner_pay_form = spinner_fuel.getSelectedItem().toString();
        }
        else{

            Toast toast = Toast.makeText(OutageTracker_Activity.this,"Select Fuel", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return;
        }
        if(!str_pay_form.equals("Select Cash Type"))
        {
            spinner_pay_form = spinner_fuel.getSelectedItem().toString();
        }
        else{
            Toast toast = Toast.makeText(OutageTracker_Activity.this,"Select Fuel", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return;
        }


        if (spinner_carmilage.getSelectedItem().toString().trim().equals("Pick one")) {
            Toast.makeText(OutageTracker_Activity.this, "Error", Toast.LENGTH_SHORT).show();
        }
        else if (spinner_fuel.getSelectedItem().toString().trim().equals("Pick one")) {
            Toast.makeText(OutageTracker_Activity.this, "Error", Toast.LENGTH_SHORT).show();
        }

        else if(str_strtingpoint.equals("")) {
            pickup_startupoint.setError("Choose Start point");
            pickup_startupoint.requestFocus();
        }
        else if(str_destinaitonpoint.equals("")) {
            pickup_distination.setError("Choose Destination point");
            pickup_distination.requestFocus();
        }
        else if(Price_amt.equals("null")) {

            Toast toast = Toast.makeText(OutageTracker_Activity.this,"Select Fuel Again", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


        }

        else {
            Get_Disatance();

        }
    }


}