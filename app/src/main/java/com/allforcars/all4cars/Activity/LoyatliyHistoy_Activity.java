package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.LoyalityHistory_Adapter;
import com.allforcars.all4cars.Adapter.Loyality_Adapter;
import com.allforcars.all4cars.Model.LoyalityHistory_Model;
import com.allforcars.all4cars.Model.Loyality_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Response.Loyalityhistry_Response;
import com.allforcars.all4cars.Response.ResultResponse;
import com.allforcars.all4cars.RetroifitApi.APIService;
import com.allforcars.all4cars.RetroifitApi.ApiClient;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LoyatliyHistoy_Activity extends AppCompatActivity {

  RelativeLayout back_loyltyhistoy;
    LinearLayout record_notfound;
    RecyclerView Recyclerview_loyatyhistoy;
    String user_id;
    ProgressDialog progressDialog;
    LoyalityHistory_Adapter LoyalityHistory_Adapter;
    List<LoyalityHistory_Model> loyalityhistry_models=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyatliy_histoy);

        back_loyltyhistoy= findViewById(R.id.back_loyltyhistoy);
        record_notfound= findViewById(R.id.record_notfound);
        Recyclerview_loyatyhistoy=findViewById(R.id.Recyclerview_loyatyhistoy);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");



        back_loyltyhistoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(LoyatliyHistoy_Activity.this);
        Recyclerview_loyatyhistoy.setLayoutManager(mLayoutManager);
        Recyclerview_loyatyhistoy.setHasFixedSize(true);
        Recyclerview_loyatyhistoy.setItemAnimator(new DefaultItemAnimator());

        getjob_list();


    }


    private void getjob_list()
    {

        progressDialog = new ProgressDialog(LoyatliyHistoy_Activity.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        APIService service = ApiClient.getClient().create(APIService.class);
        retrofit2.Call<Loyalityhistry_Response> call= service.localityhistory(user_id);

        //calling the api
        call.enqueue(new Callback<Loyalityhistry_Response>() {
            @Override
            public void onResponse(retrofit2.Call<Loyalityhistry_Response> call, retrofit2.Response<Loyalityhistry_Response> response) {

                progressDialog.dismiss();

                try
                {
                    if (response.isSuccessful())
                    {

                        Loyalityhistry_Response result=response.body();
                        String success=result.getSuccess();

                        loyalityhistry_models=result.getloylisthistory();
                        LoyalityHistory_Adapter=new LoyalityHistory_Adapter(LoyatliyHistoy_Activity.this,loyalityhistry_models);
                        Recyclerview_loyatyhistoy.setAdapter(LoyalityHistory_Adapter);



                        if (success.equals("true") || success.equals("True"))
                        {
                           if (loyalityhistry_models.size()==0)
                           {
                               record_notfound.setVisibility(View.VISIBLE);
                           }

                        }
                        else
                        {
                            //   Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(LoyatliyHistoy_Activity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
                                case 400:
                                    Toast.makeText(LoyatliyHistoy_Activity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(LoyatliyHistoy_Activity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(LoyatliyHistoy_Activity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(LoyatliyHistoy_Activity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(LoyatliyHistoy_Activity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(LoyatliyHistoy_Activity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(LoyatliyHistoy_Activity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(LoyatliyHistoy_Activity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        catch (Exception e)
                        {
                            Toast.makeText(LoyatliyHistoy_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Loyalityhistry_Response> call, Throwable t) {
                progressDialog.dismiss();

                // Toast.makeText(Login_Activity.this, "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Login_Activity.this,t.getMessage(), Toast.LENGTH_LONG).show();

                if (t instanceof IOException) {
                    Toast.makeText(LoyatliyHistoy_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Log.e("conversion issue",t.getMessage());
                    Toast.makeText(LoyatliyHistoy_Activity.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(Login_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }
            }
        });
    }

}
