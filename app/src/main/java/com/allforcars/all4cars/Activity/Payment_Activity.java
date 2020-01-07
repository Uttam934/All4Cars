package com.allforcars.all4cars.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.Utility;

public class Payment_Activity extends AppCompatActivity {

    WebView webView;
    WebSettings webSettings;
    String user_id,payment_amount="",discount_amount="",vendor_id;
    ImageView btnsetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        btnsetting = findViewById(R.id.btnsetting);


        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");



        vendor_id= getIntent().getStringExtra("Vender_id");
        payment_amount= getIntent().getStringExtra("total_amount");
        discount_amount= getIntent().getStringExtra("discount_amount");



        btnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ins = new Intent(Payment_Activity.this,Home_Activity.class);
                startActivity(ins);
                finish();
            }
        });

        String urll = Utility.Base_URL+"Payments_paypal?vendor_id="+vendor_id +"&user_id="+user_id +"&payment_amount="+payment_amount +"&discount_amount="+ discount_amount+"";
        webView = findViewById(R.id.web_view);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(urll);
        webView.setWebViewClient(new WebViewClient());

    }

    @Override
    public void onBackPressed()
    {

       Intent ins = new Intent(Payment_Activity.this,Home_Activity.class);
       startActivity(ins);
       finish();

    }
}
