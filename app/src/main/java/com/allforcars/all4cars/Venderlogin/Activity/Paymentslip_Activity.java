package com.allforcars.all4cars.Venderlogin.Activity;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.Loyalaty_Activity;
import com.allforcars.all4cars.Activity.LoyatliyHistoy_Activity;
import com.allforcars.all4cars.Adapter.LoyalityHistory_Adapter;
import com.allforcars.all4cars.Adapter.Loyality_Adapter;
import com.allforcars.all4cars.Model.LoyalityHistory_Model;
import com.allforcars.all4cars.Model.Objectclass;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Response.Loyalityhistry_Response;
import com.allforcars.all4cars.Response.PaymentslipResponse;
import com.allforcars.all4cars.Response.ResultResponse;
import com.allforcars.all4cars.RetroifitApi.APIService;
import com.allforcars.all4cars.RetroifitApi.ApiClient;
import com.allforcars.all4cars.Venderlogin.Adapter.PaymentHistory_Adapter;
import com.allforcars.all4cars.Venderlogin.model.Paymenthistory_Model;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Paymentslip_Activity extends AppCompatActivity {

    private ImageView btnsetting;
    RelativeLayout text_notfound;
    RecyclerView recycler_paymentlsip;
    ProgressDialog progressDialog;
    String vendor_id;
    PaymentHistory_Adapter paymentHistory_adapter;
    List<Paymenthistory_Model> paymenthistory_model=new ArrayList<>();
    List<Objectclass> objectclasses=new ArrayList<>();
    TextView text_totlcommision,text_bunus_bal,remaining_amt,Paid_Commission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentslip);

        remaining_amt=findViewById(R.id.remaining_amt);
        btnsetting=findViewById(R.id.btnsettings);
        text_bunus_bal=findViewById(R.id.text_outscomminon);
        text_totlcommision=findViewById(R.id.text_totlcommision);
        text_notfound=findViewById(R.id.text_notfound);
        recycler_paymentlsip=findViewById(R.id.recycler_paymentlsip);
        Paid_Commission=findViewById(R.id.Paid_Commission);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        vendor_id =sharedpreference.getString("user_id","");




        btnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Paymentslip_Activity.this);
        recycler_paymentlsip.setLayoutManager(mLayoutManager);
        recycler_paymentlsip.setHasFixedSize(true);
        recycler_paymentlsip.setItemAnimator(new DefaultItemAnimator());

        getpaymet_list();


    }

    private void getpaymet_list()
    {

        progressDialog = new ProgressDialog(Paymentslip_Activity.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        APIService service = ApiClient.getClient().create(APIService.class);
        retrofit2.Call<PaymentslipResponse> call= service.paymethistory(vendor_id);

        //calling the api
        call.enqueue(new Callback<PaymentslipResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PaymentslipResponse> call, retrofit2.Response<PaymentslipResponse> response) {

                progressDialog.dismiss();

                DecimalFormat df = new DecimalFormat("####0.00");
                Double mycommiosn=0.00,all4carcommission=0.00,remainingamount=0.00,paidcomios=0.00,totlsle=0.00;

                try
                {
                    if (response.isSuccessful())
                    {

                        PaymentslipResponse result=response.body();
                        Objectclass objectclasses = result.getObjectclasses();
                        String my_commission= objectclasses.getDeuamount();
                        String all4car_commission = objectclasses.getTotal_commission();
                        String remaining_amount = objectclasses.getRemaining_amount();
                        String totalsales = objectclasses.getTotalsales();

                        mycommiosn= Double.parseDouble(my_commission);
                        all4carcommission= Double.parseDouble(totalsales);
                        remainingamount=  Double.parseDouble(remaining_amount);
                        paidcomios= all4carcommission-remainingamount;


//                        text_totlcommision.setText(all4car_commission);
//                        text_bunus_bal.setText(my_commission);
//                        remaining_amt.setText(remaining_amount);

                        text_totlcommision.setText(df.format(mycommiosn)+"₦");
                        text_bunus_bal.setText(df.format(all4carcommission)+"₦");
                        remaining_amt.setText(df.format(remainingamount)+"₦");
                        Paid_Commission.setText(df.format(paidcomios)+"₦");

                        paymenthistory_model= objectclasses.getpaymetslip();
                        paymentHistory_adapter=new PaymentHistory_Adapter(Paymentslip_Activity.this,paymenthistory_model);
                        recycler_paymentlsip.setAdapter(paymentHistory_adapter);

                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Paymentslip_Activity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
                                case 400:
                                    Toast.makeText(Paymentslip_Activity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Paymentslip_Activity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Paymentslip_Activity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Paymentslip_Activity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Paymentslip_Activity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Paymentslip_Activity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Paymentslip_Activity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Paymentslip_Activity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        catch (Exception e)
                        {
                            Toast.makeText(Paymentslip_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<PaymentslipResponse> call, Throwable t) {
                progressDialog.dismiss();

                // Toast.makeText(Login_Activity.this, "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Login_Activity.this,t.getMessage(), Toast.LENGTH_LONG).show();

                if (t instanceof IOException) {
                    Toast.makeText(Paymentslip_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Log.e("conversion issue",t.getMessage());
                    Toast.makeText(Paymentslip_Activity.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(Login_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }
            }
        });
    }

}
