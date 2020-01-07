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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.Loyality_Adapter;
import com.allforcars.all4cars.Model.Loyality_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Response.ResultResponse;
import com.allforcars.all4cars.RetroifitApi.APIService;
import com.allforcars.all4cars.RetroifitApi.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Loyalaty_Activity extends AppCompatActivity {

    RelativeLayout back_loyaltiy,text_nolyality;
    LinearLayout loyatlity_history;
    String user_id;
    RecyclerView recycler_loyatliy;
    ProgressDialog progressDialog;
    Loyality_Adapter loyality_Adapter;
    List<Loyality_Model> loyality_models=new ArrayList<>();

    // private RefreshInterface refreshInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalaty);

        back_loyaltiy=findViewById(R.id.back_loyaltiy);
        text_nolyality=findViewById(R.id.text_nolyality);
        loyatlity_history=findViewById(R.id.loyatlity_history);
        recycler_loyatliy=findViewById(R.id.recycler_loyatliy);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");


        loyatlity_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ints = new Intent(Loyalaty_Activity.this,LoyatliyHistoy_Activity.class);
                startActivity(ints);
            }
        });

        back_loyaltiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Loyalaty_Activity.this);
        recycler_loyatliy.setLayoutManager(mLayoutManager);
        recycler_loyatliy.setHasFixedSize(true);
        recycler_loyatliy.setItemAnimator(new DefaultItemAnimator());

        ViewShowMessageCount();


    }

    private void ViewShowMessageCount()
    {

        progressDialog = new ProgressDialog(Loyalaty_Activity.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<ResultResponse> call= service.postMessageCountList(user_id);
        //calling the api
        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, retrofit2.Response<ResultResponse> response) {
///
                progressDialog.dismiss();

                try
                {

                    ResultResponse result=response.body();
                    String success=result.getSuccess();
                    //String message=result.getMessage();
                    loyality_models=result.getLoyality();
                    if (success.equals("True"))
                    {
                        try
                        {
//                            if (loyality_models.size()==0)
//                            {
//                                 text_nolyality.setVisibility(View.VISIBLE);
//                            }

                            loyality_Adapter=new Loyality_Adapter(Loyalaty_Activity.this,loyality_models);
                            recycler_loyatliy.setAdapter(loyality_Adapter);

//
                        }
                        catch (Exception er)
                        {
                            Toast.makeText(Loyalaty_Activity.this, er.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        //  Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Loyalaty_Activity.this,"Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
