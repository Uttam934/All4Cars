package com.allforcars.all4cars.Test.Retrofit_Rxjava;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.allforcars.all4cars.Adapter.Orderhistory_Adapter;
import com.allforcars.all4cars.R;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recylerviewlist_Activity extends AppCompatActivity {

    RecyclerView _recyclerView;
    Orderhistory_Adapter historyReportAdapter;
    SwipeRefreshLayout sw_refresh;
    CustomProgressDialog progressDialog;
    private CompositeDisposable mCompositeDisposable;
    ArrayList<Record> historyReportArrayList = new ArrayList<Record>();

    SharedPreferences shared;
    Record arrPackage;
    List<Task>tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recylerviewlist_);


        mCompositeDisposable = new CompositeDisposable();
        progressDialog = new CustomProgressDialog(Recylerviewlist_Activity.this, R.drawable.custom_progress_layout);

        sw_refresh = (SwipeRefreshLayout) findViewById(R.id.sw_refresh_historyFragment);
        _recyclerView = (RecyclerView) findViewById(R.id.recyclerView_historyFragment);

      //  arrPackage   =  getArrayList("Uttam");

        SharedPreferences appSharedPrefs = PreferenceManager  .getDefaultSharedPreferences(getApplicationContext());
        String json = appSharedPrefs.getString("currentTasks", "");

        if(json.equals(""))
        {

           historyReportNetCall();

             historyReportAdapter = new Orderhistory_Adapter(Recylerviewlist_Activity.this,historyReportArrayList);
            _recyclerView.setLayoutManager(new LinearLayoutManager(Recylerviewlist_Activity.this));
            _recyclerView.setAdapter(historyReportAdapter);
            _recyclerView.setItemAnimator(new DefaultItemAnimator());
            _recyclerView.setNestedScrollingEnabled(false);

        }

        else {

            Gson gson = new Gson();
            Type dd = new TypeToken<List<Record>>(){}.getType();
            List<Record> contactList = gson.fromJson(json, dd);
            for (Record contact : contactList){
                historyReportArrayList.addAll(contactList);
            }

          //  editor.remove(key).apply()
            historyReportAdapter = new Orderhistory_Adapter(Recylerviewlist_Activity.this,historyReportArrayList);
            _recyclerView.setLayoutManager(new LinearLayoutManager(Recylerviewlist_Activity.this));
            _recyclerView.setAdapter(historyReportAdapter);
            _recyclerView.setItemAnimator(new DefaultItemAnimator());
            _recyclerView.setNestedScrollingEnabled(false);




        }









        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                historyReportNetCall();
                sw_refresh.setRefreshing(false);
            }
        });


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

            mCompositeDisposable.add(requestInterface.order_list("2","24","0")

                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
    }

    private void handleResponse(Responses_Model responses_model) {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
        historyReportArrayList.clear();
        if (responses_model.getSuccess().equals("true")) {

//            historyReportArrayList.addAll(Orderhistory_Adapter());
//            historyReportAdapter.notifyDataSetChanged();

            historyReportArrayList.addAll(responses_model.getRecord());
            historyReportAdapter.notifyDataSetChanged();

           // saveArrayList(historyReportArrayList,"Uttam");

            SharedPreferences appSharedPrefs = PreferenceManager  .getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(historyReportArrayList); //tasks is an ArrayList instance variable
            prefsEditor.putString("currentTasks", json);
            prefsEditor.commit();





        } else {
            Constant.toast(getApplicationContext(), responses_model.getMessage());
        }
    }
    private void handleError(Throwable error) {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
        Constant.toast(getApplicationContext(), "Server Error");
    }








}
