package com.allforcars.all4cars.Test.CollapsingToolbarLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.CustomProgressDialog;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Collapse_Activity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    CustomProgressDialog progressDialog;
    private CompositeDisposable mCompositeDisposable;
    public static int refresh;
    Toolbar toolbar;
    ImageView _ivEditProfile;
    String ethAddress;
    TextView _tvName, _tvUserId,_tvRegDate,_tvMobileNo, _tvEmail, _tvCountry, _tvPlacement, _tvSponserId, _tvBtcAddrress, _tvLastLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Account");

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
       mCompositeDisposable = new CompositeDisposable();
        progressDialog = new CustomProgressDialog(this, R.drawable.custom_progress_layout);

        _tvName = findViewById(R.id.tv_myAccountActivity_name);
        _tvUserId = findViewById(R.id.tv_myAccountActivity_userId);
        _tvRegDate = findViewById(R.id.tv_myAccountActivity_regDate);
        _tvMobileNo = findViewById(R.id.tv_myAccountActivity_mobile);
        _tvEmail = findViewById(R.id.tv_myAccountActivity_email);
        _tvCountry = findViewById(R.id.tv_myAccountActivity_country);

        _tvPlacement = findViewById(R.id.tv_myAccountActivity_placement);
        _tvSponserId = findViewById(R.id.tv_myAccountActivity_sponserId);
        _tvBtcAddrress = findViewById(R.id.tv_myAccountActivity_btcAddress);
        _tvLastLogin = findViewById(R.id.tv_myAccountActivity_lastLogin);

        _ivEditProfile = (ImageView) findViewById(R.id.iv_userProfile_editProfile);



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (refresh==1){

            refresh=0;
        }
    }
}
