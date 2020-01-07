package com.allforcars.all4cars.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.Deatail_Activity;
import com.allforcars.all4cars.Activity.Home_Activity;
import com.allforcars.all4cars.Activity.User_Notification_Activity;
import com.allforcars.all4cars.Adapter.Fuelsearcher_Adapter;
import com.allforcars.all4cars.Adapter.Notification_Adapter;
import com.allforcars.all4cars.Adapter.Venderlist_Adapter;
import com.allforcars.all4cars.Model.Catagory_Model;
import com.allforcars.all4cars.Model.Feaulserher_Model;
import com.allforcars.all4cars.Model.Notification_Model;
import com.allforcars.all4cars.Model.Venderlist_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Response.Venderlist_Response;
import com.allforcars.all4cars.RetroifitApi.APIService;
import com.allforcars.all4cars.RetroifitApi.ApiClient;
import com.allforcars.all4cars.Venderlogin.Adapter.PaymentHistory_Adapter;
import com.allforcars.all4cars.Venderlogin.model.Paymenthistory_Model;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.GPSTracker;
import com.allforcars.all4cars.classes.RecyclerTouchListener;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.allforcars.all4cars.classes.Utility.Base_URL;


public class List_Fragment extends Fragment  {
    View fragmentView;
    String ToKm,Distance="",Catergorylist="",seekbarValue;
    int count;
    RecyclerView Recyclerview_stationlist;
    Fuelsearcher_Adapter fuelsearcher_Adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Feaulserher_Model> Arraylist_feaulserher;
    ArrayList<Feaulserher_Model> product_array_list;
    ArrayList<Feaulserher_Model> catagoty_arraylist=new ArrayList<>();;
    ArrayList<Feaulserher_Model> catagory_modelArrayList;
    LinearLayout et_recordmatch;
    SwipeRefreshLayout mSwipeRefreshLayout;
    GPSTracker gps;
    String Zero ="0";
    double longitudes,latitudes;
    ProgressDialog progressDialog;
    String currentlatitude="0.00", currentlongtide="0.00",user_id,name_key="1";

    Venderlist_Adapter venderlist_adapter;
    List<Venderlist_Model> venderlist_models=new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_list, container, false);




        Recyclerview_stationlist = (RecyclerView) fragmentView.findViewById(R.id.Recyclerview_stationlist);
        et_recordmatch = (LinearLayout) fragmentView.findViewById(R.id.et_recordmatch);


        mSwipeRefreshLayout = fragmentView.findViewById(R.id.swipe_container);





        SharedPreferences sharedpreference = getContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        Catergorylist =sharedpreference.getString("Catergorylist","");
        seekbarValue =sharedpreference.getString("seekbarValue","");

      //  Toast.makeText(getContext(), ""+Catergorylist, Toast.LENGTH_LONG).show();


        if (seekbarValue.equals(""))
        {
            seekbarValue="100";

        }

        else {


        }




        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

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


        Recyclerview_stationlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        Recyclerview_stationlist.setLayoutManager(layoutManager);







        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                ((Home_Activity)getActivity()).refreshMyData();
                if (Catergorylist.equals(""))
                {

                    if (isOnline()) {
                        getfirsttime_list();

                    } else {
                        Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                    }
                }

                else {

                    if (isOnline()) {
                        filter_list();

                    } else {
                        Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                    }

                }

            }
        });

        if (Catergorylist.equals(""))
        {

            if (isOnline()) {

                getfirsttime_list();

            } else {
                Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

            }
        }

        else {

            if (isOnline()) {
                filter_list();

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





    private void getfirsttime_list()
    {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        APIService service = ApiClient.getClient().create(APIService.class);
        retrofit2.Call<Venderlist_Response> call= service.venderlist(currentlatitude,currentlongtide,Zero,seekbarValue);

        //calling the api
        call.enqueue(new Callback<Venderlist_Response>() {
            @Override
            public void onResponse(retrofit2.Call<Venderlist_Response> call, retrofit2.Response<Venderlist_Response> response) {

                progressDialog.dismiss();

                try
                {
                    if (response.isSuccessful())
                    {

                        Venderlist_Response result=response.body();


                        venderlist_models=result.getVenderlist();
                        venderlist_adapter=new Venderlist_Adapter(getActivity(),venderlist_models);
                        Recyclerview_stationlist.setAdapter(venderlist_adapter);


                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        catch (Exception e)
                        {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Venderlist_Response> call, Throwable t) {
                progressDialog.dismiss();

                // Toast.makeText(Login_Activity.this, "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Login_Activity.this,t.getMessage(), Toast.LENGTH_LONG).show();
                try
                {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Log.e("conversion issue",t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(Login_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


    private void filter_list()
    {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        APIService service = ApiClient.getClient().create(APIService.class);
        retrofit2.Call<Venderlist_Response> call= service.venderlist(currentlatitude,currentlongtide,Catergorylist,seekbarValue);

        //calling the api
        call.enqueue(new Callback<Venderlist_Response>() {
            @Override
            public void onResponse(retrofit2.Call<Venderlist_Response> call, retrofit2.Response<Venderlist_Response> response) {

                progressDialog.dismiss();

                try
                {
                    if (response.isSuccessful())
                    {

                        Venderlist_Response result=response.body();
                        venderlist_models=result.getVenderlist();
                        count = venderlist_models.size();
                        if(count==0)
                        {
                            et_recordmatch.setVisibility(View.VISIBLE);
                            Recyclerview_stationlist.setVisibility(View.GONE);

                        }
                        venderlist_adapter=new Venderlist_Adapter(getActivity(),venderlist_models);
                        Recyclerview_stationlist.setAdapter(venderlist_adapter);







                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        catch (Exception e)
                        {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Venderlist_Response> call, Throwable t) {
                progressDialog.dismiss();

                // Toast.makeText(Login_Activity.this, "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Login_Activity.this,t.getMessage(), Toast.LENGTH_LONG).show();

                try
                {
                    if (t instanceof IOException) {
                        Toast.makeText(getActivity(), "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                        // logging probably not necessary
                    }
                    else {
                        Log.e("conversion issue",t.getMessage());
                        Toast.makeText(getActivity(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(Login_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                        // todo log to some central bug tracking service
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }






    public List_Fragment() {
    }



    public boolean isOnline() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }




}
