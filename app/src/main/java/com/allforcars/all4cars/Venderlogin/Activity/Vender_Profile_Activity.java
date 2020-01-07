package com.allforcars.all4cars.Venderlogin.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Vender_Profile_Activity extends AppCompatActivity {

    ImageView vender_img_back;
    ImageView venderEdituser_image,img_banner_profile;
  private   EditText vender_dittxt_user_name,vender_edittxt_user_email,vender_edittxt_user_phone,vender_compnyname,edittxt_dateofsub;
    String  user_name,user_email,phone_number,user_image,user_id,usertype,company_name,Vender_banner_image,created_at;
    TextView vender_txt_editmitstud,txt_venrdrname_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender__profile);

        vender_img_back= findViewById(R.id.vender_img_back);
        venderEdituser_image= findViewById(R.id.venderEdituser_image);
        vender_dittxt_user_name= findViewById(R.id.vender_dittxt_user_name);
        vender_edittxt_user_email= findViewById(R.id.vender_edittxt_user_email);
        vender_edittxt_user_phone= findViewById(R.id.vender_edittxt_user_phone);
        vender_txt_editmitstud= findViewById(R.id.vender_txt_editmitstud);
        vender_compnyname= findViewById(R.id.vender_compnyname);
        txt_venrdrname_main= findViewById(R.id.txt_venrdrname_main);
        img_banner_profile= findViewById(R.id.img_banner_profile);
        edittxt_dateofsub= findViewById(R.id.edittxt_dateofsub);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");


        vender_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        vender_txt_editmitstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ints = new Intent(Vender_Profile_Activity.this,VenderPorfileupdate_Activity.class);
                startActivity(ints);
                finish();
            }
        });

        Getuserinfor_api();
    }


    private void Getuserinfor_api() {
        final ProgressDialog pd = new ProgressDialog(Vender_Profile_Activity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait..");
        pd.show();
        String urll= Utility.Base_URL+"get_user_data";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("success").equalsIgnoreCase("true"))
                    {
                        JSONArray jsonArray=jsonObject.getJSONArray("record");

                        for(int i=0;i<jsonArray.length();i++)


                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            user_name = (jsonObject1.getString("fld_name"));
                            company_name = (jsonObject1.getString("company_name"));
                            user_email = (jsonObject1.getString("fld_email"));
                            phone_number = (jsonObject1.getString("phone"));
                            user_image = (jsonObject1.getString("logo"));
                            Vender_banner_image = (jsonObject1.getString("image"));
                            created_at = (jsonObject1.getString("created_at"));

                            txt_venrdrname_main.setText(company_name);
                            vender_dittxt_user_name.setText(user_name);
                            vender_edittxt_user_email.setText(user_email);
                            vender_edittxt_user_phone.setText(phone_number);
                            vender_compnyname.setText(company_name);
                            edittxt_dateofsub.setText(created_at);




                            if(user_image.equals(null))
                            {
                                venderEdituser_image.setImageDrawable(getResources().getDrawable(R.drawable.user_icon));

                            }
                            else {

                                String url_image = Utility.logo_url + user_image;
                                Glide.with(Vender_Profile_Activity.this).load(url_image)
                                        .crossFade()
                                        .thumbnail(0.5f)
                                        .bitmapTransform(new CircleTransform(Vender_Profile_Activity.this))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(venderEdituser_image);

                            }

                            if(Vender_banner_image.equals(null))
                            {
                                img_banner_profile.setImageDrawable(getResources().getDrawable(R.drawable.user_icon));

                            }
                            else {

                                String url_image = Utility.Vender_banner + Vender_banner_image;
                                Glide.with(Vender_Profile_Activity.this).load(url_image)
                                        .crossFade()
//                                        .thumbnail(0.5f)
//                                        .bitmapTransform(new CircleTransform(Vender_Profile_Activity.this))
//                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(img_banner_profile);

                            }

                        }


                    }
                    else{

                    }

                } catch (JSONException e) {


                    //Toast.makeText(Create_New_Check_Activity.this, "Please Enter Registered Email And Password", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppController.getInstance().getRequestQueue().cancelAll("user_Sign_up");
                pd.dismiss();
                if (error instanceof NetworkError) {
                    Toast.makeText(Vender_Profile_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Vender_Profile_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Vender_Profile_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Vender_Profile_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Vender_Profile_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Vender_Profile_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Vender_Profile_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError { HashMap<String, String> map = new HashMap<>();
                map.put("user_id", user_id);
                map.put("user_type", usertype);


                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
//        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");
        user_name =sharedpreference.getString("user_name","");
        user_image =sharedpreference.getString("Profile_image","");


        if(user_image.equals("null"))
        {
            venderEdituser_image.setImageDrawable(getResources().getDrawable(R.drawable.user_white));

        }
        else {



            String url_image = Utility.logo_url + user_image;
            Glide.with(Vender_Profile_Activity.this).load(url_image)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(Vender_Profile_Activity.this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(venderEdituser_image);

        }


        Getuserinfor_api();

    }
}
