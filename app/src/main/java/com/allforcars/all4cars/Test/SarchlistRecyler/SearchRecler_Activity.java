package com.allforcars.all4cars.Test.SarchlistRecyler;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.Loyalaty_Activity;
import com.allforcars.all4cars.Activity.LoyatliyHistoy_Activity;
import com.allforcars.all4cars.Adapter.Loyality_Adapter;
import com.allforcars.all4cars.Model.Loyality_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Response.ResultResponse;
import com.allforcars.all4cars.RetroifitApi.APIService;
import com.allforcars.all4cars.RetroifitApi.ApiClient;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.Recylerviewlist_Activity;
import com.allforcars.all4cars.Test.modelCreateBorrowers.Borrower;
import com.allforcars.all4cars.Test.modelCreateBorrowers.CreateBorrowerResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchRecler_Activity extends AppCompatActivity {

    RelativeLayout back_loyaltiy, text_nolyality;
    LinearLayout loyatlity_history;
    String user_id;
    RecyclerView recyclerView_borrwer;
    ProgressDialog progressDialog;
    List_Adapter Listadapter;
    List<Borrower> Borrowerlist = new ArrayList<>();

    // private RefreshInterface refreshInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recler);


        recyclerView_borrwer = findViewById(R.id.recyclerView_borrwer);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_borrwer.setLayoutManager(mLayoutManager);
        recyclerView_borrwer.setHasFixedSize(true);
        recyclerView_borrwer.setItemAnimator(new DefaultItemAnimator());

        ViewShowMessageCount();


    }




    private void ViewShowMessageCount() {



        APIService service = ApiClient.getClient().create(APIService.class);

        Call<CreateBorrowerResponse> call = service.Reportsends();
        //calling the api
        call.enqueue(new Callback<CreateBorrowerResponse>() {
            @Override
            public void onResponse(Call<CreateBorrowerResponse> call, retrofit2.Response<CreateBorrowerResponse> response) {
///


                try {

                    CreateBorrowerResponse result = response.body();
                    String name=response.body().getPayload().getData().getBorrowers().get(0).getName();
                    result.getPayload().getMessage();
                    Integer status = result.getCode();
                    Borrowerlist = result.getPayload().getData().getBorrowers();

                    Listadapter = new List_Adapter(getApplicationContext(), Borrowerlist);
                    recyclerView_borrwer.setAdapter(Listadapter);





                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CreateBorrowerResponse> call, Throwable t) {
                //  progressDialog.dismiss();
                // Toast.makeText(Loyalaty_Activity.this,"Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();
            }
        });
    }




}