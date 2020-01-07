package com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.Presenter;

import android.content.Context;
import android.util.Log;

import com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.Api.Constants;
import com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.Api.FeedApi;
import com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.Api.GetDataContract;
import com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.Model.JsonResponse;
import com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.Model.Record;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Intractor implements GetDataContract.Interactor{
    private GetDataContract.onGetDataListener mOnGetDatalistener;
    List<Record> allRecords = new ArrayList<>();
    List<String> allRecordsData = new ArrayList<>();

    public Intractor(GetDataContract.onGetDataListener mOnGetDatalistener){
        this.mOnGetDatalistener = mOnGetDatalistener;
    }
    @Override
    public void initRetrofitCall(Context context, String url) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        FeedApi request = retrofit.create(FeedApi.class);
        retrofit2.Call<JsonResponse> call = request.getFeedList("24","2","0");
        call.enqueue(new retrofit2.Callback<JsonResponse>() {
            @Override
            public void onResponse(retrofit2.Call<JsonResponse> call, retrofit2.Response<JsonResponse> response) {
                JsonResponse jsonResponse = response.body();
                allRecords = jsonResponse.getRecord();
               /* for(int i=0;i<allRecords.size();i++){
                    allRecordsData.add(allRecords.get(i).getName());
                }*/
                Log.d("Data", "Refreshed");
                mOnGetDatalistener.onSuccess("List Size: " + allRecordsData.size(), allRecords);



            }
            @Override
            public void onFailure(retrofit2.Call<JsonResponse> call, Throwable t) {
                Log.v("Error",t.getMessage());
                mOnGetDatalistener.onFailure(t.getMessage());
            }
        });
    }
}