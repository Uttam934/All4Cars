package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.RedeemHistory_Adapter;
import com.allforcars.all4cars.Model.RedeemHistory_Model;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Howtouse_Activity extends AppCompatActivity {

   RelativeLayout howtouse;
   ImageView img_about_us;
   TextView first_heading;
   String Description,document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtouse);

        howtouse= findViewById(R.id.howtouse);
        img_about_us= findViewById(R.id.img_about_us);
        first_heading= findViewById(R.id.first_heading);

        howtouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        Get_howtouse();
    }

    public void  Get_howtouse(){

        String urljsonobj_group = Base_URL+"how_to_use";
        final ProgressDialog progressDialog = new ProgressDialog(Howtouse_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    if (response.getString("success").equalsIgnoreCase("true")) {
                        JSONObject Jsoobj = response.getJSONObject("record");

                        JSONArray jsonArray = Jsoobj.getJSONArray("document");

                        for(int i=0;i<jsonArray.length();i++)


                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            document = jsonObject1.getString("document");

                            String url_image = Utility.Calteroy_icon + document;
//
//
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_image));
                            startActivity(browserIntent);



                        }






                    }
                    else {



                    }



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
                    Toast.makeText(Howtouse_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Howtouse_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Howtouse_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Howtouse_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Howtouse_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Howtouse_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Howtouse_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }

}
