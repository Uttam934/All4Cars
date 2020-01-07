package com.allforcars.all4cars.Test.Retrofit_Rxjava;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.SpinnerModel.Result;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.SpinnerModel.Spinnerlist;
import com.allforcars.all4cars.Venderlogin.Activity.AddProduct_Activity;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Spineer_Activity extends AppCompatActivity {

    SearchableSpinner spinner_categroylistd;
    CustomProgressDialog progressDialog;
    private CompositeDisposable mCompositeDisposable;
    ArrayList<Result> customerArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spineer);

        spinner_categroylistd=(SearchableSpinner)findViewById(R.id.spinner_categroylist);

        mCompositeDisposable = new CompositeDisposable();
        progressDialog = new CustomProgressDialog(Spineer_Activity.this, R.drawable.custom_progress_layout);

        customerReportNetCall();
    }


    //===================call get shopping api==============================//
    private void customerReportNetCall() {
        if (Constant.isNetworkAvailable(getApplicationContext())) {
            progressDialog.show();
            progressDialog.setCancelable(false);

            RequestInterface requestInterface = new Retrofit.Builder()
                    .baseUrl(API.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RequestInterface.class);


            mCompositeDisposable.add(requestInterface.spinner("1")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
    }

    private void handleResponse(Spinnerlist spinnerlist) {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }

           customerArrayList.addAll(spinnerlist.getResult());
            setCustomerMobile();


    }

    //=======set Currency spainner Data==========//
    public void setCustomerMobile() {
        ArrayList<String> mobileArrayList = new ArrayList<String>();

        for (int i = 0; i < customerArrayList.size(); i++) {
            mobileArrayList.add(customerArrayList.get(i).getName());
        }
        ArrayAdapter<String> mobileAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, mobileArrayList);
        mobileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_categroylistd.setAdapter(mobileAdapter);

        spinner_categroylistd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String customerMobile = customerArrayList.get(i).getCode();

                Toast.makeText(getApplicationContext(), ""+customerMobile, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }



    private void handleError(Throwable error) {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
        Constant.toast(getApplicationContext(), "Server Error");
    }
}
