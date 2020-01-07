package com.allforcars.all4cars.Test.TestLargeAPI.Code;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.API;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.Constant;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.CustomProgressDialog;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.Record;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.RequestInterface;
import com.allforcars.all4cars.Test.TestLargeAPI.Model.Mainapitest;
import com.allforcars.all4cars.Test.TestLargeAPI.Model.Result;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recylrlargeapi_Activity extends AppCompatActivity {

    RecyclerView _recyclerView;
    Recylerlist_adapter historyReportAdapter;
    SwipeRefreshLayout sw_refresh;
    CustomProgressDialog progressDialog;
     CompositeDisposable mCompositeDisposable;
    ArrayList<Result> historyReportArrayList = new ArrayList<Result>();
    SharedPreferences shared;
    Record arrPackage;
    List<Task>tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recylerviewlist_);


        mCompositeDisposable = new CompositeDisposable();
        progressDialog = new CustomProgressDialog(Recylrlargeapi_Activity.this, R.drawable.custom_progress_layout);

        sw_refresh = (SwipeRefreshLayout) findViewById(R.id.sw_refresh_historyFragment);
        _recyclerView = (RecyclerView) findViewById(R.id.recyclerView_historyFragment);


            historyReportAdapter = new Recylerlist_adapter(Recylrlargeapi_Activity.this,historyReportArrayList);
            _recyclerView.setLayoutManager(new LinearLayoutManager(Recylrlargeapi_Activity.this));
            _recyclerView.setAdapter(historyReportAdapter);
            _recyclerView.setItemAnimator(new DefaultItemAnimator());
            _recyclerView.setNestedScrollingEnabled(false);

        historyReportNetCall();
    }







    //===================call get shopping api==============================//
    public void historyReportNetCall() {
        if (Constant.isNetworkAvailable(getApplicationContext())) {
            progressDialog.show();
            progressDialog.setCancelable(false);



            RequestInterface requestInterface = new Retrofit.Builder()
                    .baseUrl(API.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RequestInterface.class);

                   mCompositeDisposable.add(requestInterface.largeapi("gvnlhMtCR9rAXEvM6266C3KsArNRAGZV")

                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
    }

    private void handleResponse(Mainapitest mainapitest) {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
        historyReportArrayList.clear();
        if (mainapitest.getStatus().equalsIgnoreCase("Ok")) {


            historyReportArrayList.addAll(mainapitest.getResults());
            historyReportAdapter.notifyDataSetChanged();




        } else {
            //Constant.toast(getApplicationContext(), mainapitest.getResults());
        }
    }
    private void handleError(Throwable error) {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
        Constant.toast(getApplicationContext(), "Server Error");
    }








}
