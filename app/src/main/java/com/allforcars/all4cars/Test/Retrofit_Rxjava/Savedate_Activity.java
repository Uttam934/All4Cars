package com.allforcars.all4cars.Test.Retrofit_Rxjava;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.database.Observable;



import com.allforcars.all4cars.R;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Savedate_Activity extends AppCompatActivity {

    RelativeLayout backbtn_contactus;
    String user_name,Recomendation,user_id,usertype,str_txt_name,str_txt_phone,str_txt_eamil,str_txt_addres,str_txt_description;
    EditText txt_name,txt_phone,txt_eamil,txt_addres,txt_description;
    Button btn_submit;
    TextView text_recomndaton;

    CustomProgressDialog progressDialog;
    private CompositeDisposable mCompositeDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_user);

        backbtn_contactus= findViewById(R.id.backbtn_contactus);

        txt_name=findViewById(R.id.txt_name);
        txt_phone=findViewById(R.id.txt_phone);
        txt_eamil=findViewById(R.id.txt_eamil);
        txt_addres=findViewById(R.id.txt_addres);
        txt_description=findViewById(R.id.txt_description);
        btn_submit=findViewById(R.id.btn_submit);
        text_recomndaton=findViewById(R.id.text_recomndaton);

        mCompositeDisposable = new CompositeDisposable();
        progressDialog = new CustomProgressDialog(this, R.drawable.custom_progress_layout);






        {
            text_recomndaton.setText("Add Recommendation");

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    fundTransferNetCall();


                }
            });
        }




        backbtn_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    //=========check Validation on Attribute=======//
    private boolean checkValidation() {
        final View focusview;


        str_txt_name=txt_name.getText().toString();
        str_txt_phone=txt_phone.getText().toString();
        str_txt_eamil=txt_eamil.getText().toString();
        str_txt_addres=txt_addres.getText().toString();
        str_txt_description=txt_description.getText().toString();

        if (str_txt_name.equals("")) {
            txt_name.setError("Please enter the amount");
            focusview = txt_name;
            focusview.requestFocus();
            return false;
        } else if (str_txt_phone.equals("")) {
            txt_phone.setError("Please enter the member id");
            focusview = txt_phone;
            focusview.requestFocus();
            return false;
        } else if (str_txt_eamil.equals("")) {
            txt_eamil.setError("Please enter the member name");
            focusview = txt_eamil;
            focusview.requestFocus();
            return false;
        }

        else if (str_txt_addres.equals("")) {
            txt_addres.setError("Please enter the add name");
            focusview = txt_addres;
            focusview.requestFocus();
            return false;
        }

        else if (str_txt_description.equals("")) {
            txt_description.setError("Please enter the add name");
            focusview = txt_description;
            focusview.requestFocus();
            return false;
        }
        else if (Constant.isNetworkAvailable(getApplicationContext())) {
            return true;
        } else {
            return false;
        }
    }



    //===================call get shopping api==============================//
    private void fundTransferNetCall() {

        if (checkValidation()) {
            progressDialog.show();
            progressDialog.setCancelable(false);

            RequestInterface requestInterface = new Retrofit.Builder()
                    .baseUrl(API.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RequestInterface.class);

            mCompositeDisposable.add(requestInterface.Addsubscripiton(

                    "24", "jdf", "dfdf", "dfdf", "dfdff", "dfd", "2", "fg")

                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
    }


    private void handleResponse(CheckStatusModel checkStatusModel) {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }

        if (checkStatusModel.getSuccess().equals("True")) {

            Constant.toast(getApplicationContext(), checkStatusModel.getMessage());
        } else {
            Constant.toast(getApplicationContext(), checkStatusModel.getMessage());
        }
    }

    private void handleError(Throwable error) {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
        Constant.toast(this, "Server Error");
    }



















}
