package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.VenderBranch_Adapter;
import com.allforcars.all4cars.Model.VenderBranch_Model;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Rating_Activity extends AppCompatActivity {

   String user_id,Profile_image,user_name,ratings,str_Comment,Vender_id,rating,message;
   ImageView member_image_rating_profile;
   TextView user_name_rating;
   EditText comment_rating_profile;
   Button txt_post;
   RelativeLayout backbtn_contactus;
   RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        user_name_rating=findViewById(R.id.user_name_rating);
        member_image_rating_profile=findViewById(R.id.member_image_rating_profile);
        comment_rating_profile=findViewById(R.id.comment_rating_profile);
        txt_post=findViewById(R.id.txt_post);
        backbtn_contactus=findViewById(R.id.backbtn_contactus);
        ratingBar = findViewById(R.id.rating);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        Vender_id = getIntent().getStringExtra("Vender_id");
        user_name = getIntent().getStringExtra("list_companyname");
        Profile_image = getIntent().getStringExtra("url_imagelogo");




        user_name_rating.setText(user_name);
        String url_image = Utility.logo_url + Profile_image;
        Glide.with(Rating_Activity.this).load(url_image)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(Rating_Activity.this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(member_image_rating_profile);

        backbtn_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        Get_Rating();



        txt_post.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                ratings =""+ratingBar.getRating();

                str_Comment=comment_rating_profile.getText().toString();


                if(ratings.equals("0.0"))
                {
                    Toast toast = Toast.makeText(Rating_Activity.this,"Please Do Rating", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }

                else
                {
                    add_rating();

                }

            }

        });

    }

    private void add_rating() {
        final ProgressDialog pd = new ProgressDialog(Rating_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"add_rating";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {

                        Toast toast = Toast.makeText(Rating_Activity.this,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pd.dismiss();
                        onBackPressed();
                    }
                    else {

                        Toast toast = Toast.makeText(Rating_Activity.this,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pd.dismiss();

                    }



                } catch (JSONException e) {


                    //   Toast.makeText(getActivity(), ,+e Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Rating_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    //Toast.makeText(Rating_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Rating_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Rating_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Rating_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Rating_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Rating_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("user_id",user_id);
                map.put("vendor_id", Vender_id);
                map.put("rating",ratings);
                map.put("message",str_Comment);


                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }

    public void  Get_Rating(){

        String urljsonobj_group = Base_URL+"get_rating?user_id="+user_id+"&vendor_id="+Vender_id+"";
        final ProgressDialog progressDialog = new ProgressDialog(Rating_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                        JSONObject jsonObject = response.getJSONObject("record");

                            rating = jsonObject.getString("rating");
                            message = jsonObject.getString("message");
                            comment_rating_profile.setText(message);
                            ratingBar.setRating(Float.parseFloat(rating));


                }
                catch (JSONException e) {
                    AppController.getInstance().getRequestQueue().cancelAll("survey_list");
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AppController.getInstance().getRequestQueue().cancelAll("survey_list");
                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                    Toast.makeText(Rating_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Rating_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Rating_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Rating_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Rating_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Rating_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Rating_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }
}
