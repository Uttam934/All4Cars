package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.github.siyamed.shapeimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditProfile_Activity extends AppCompatActivity {
    String user_id,user_name,user_email,phone_number,user_image,usertype,birthday_day,created_at;
    CircularImageView Edituser_image;
    TextView edittxt_user_email,txt_submitstud;
    EditText edittxt_user_name,edittxt_user_phone,edittxt_dateofbirth,edittxt_dateofsubrionn;
    SharedPreferences sharedPreferences;
    ImageView img_back_edit_prof;
    String finalString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Edituser_image = findViewById(R.id.Edituser_image);

        edittxt_dateofbirth = findViewById(R.id.edittxt_dateofbirth);
        edittxt_user_name = findViewById(R.id.edittxt_user_name);
        edittxt_user_email = findViewById(R.id.edittxt_user_email);
        edittxt_user_phone = findViewById(R.id.edittxt_user_phone);
        txt_submitstud = findViewById(R.id.txt_editmitstud);
        img_back_edit_prof = findViewById(R.id.img_back_edit);
        edittxt_dateofsubrionn = findViewById(R.id.edittxt_dateofsubrionn);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");

        txt_submitstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ints = new Intent(EditProfile_Activity.this,UserProfile_Activity.class);
                startActivity(ints);
                finish();

            }
        });

        img_back_edit_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


        Getuserinfor_api();

    }

    private void Getuserinfor_api() {
        final ProgressDialog pd = new ProgressDialog(EditProfile_Activity.this);
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

                            user_name = (jsonObject1.getString("name"));
                            user_email = (jsonObject1.getString("email"));
                            phone_number = (jsonObject1.getString("phone_number"));
                            user_image = (jsonObject1.getString("user_image"));
                            birthday_day = (jsonObject1.getString("birthday_day"));
                            created_at = (jsonObject1.getString("created_at"));
                            edittxt_dateofbirth.setText(birthday_day);
                            edittxt_dateofsubrionn.setText(created_at);

                            if(birthday_day.equals(""))
                            {

                            }
                            else {

                                try {
                                    String date=new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(birthday_day));


                                } catch (Exception e) {

                                }

                            // edittxt_dateofbirth.setText(day + "/" + month + "/" + year);
                            }


                            edittxt_user_name.setText(user_name);
                            edittxt_user_email.setText(user_email);
                            edittxt_user_phone.setText(phone_number);


                            sharedPreferences= getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("user_name",user_name);
                            editor.putString("user_email", user_email);
                            editor.putString("phone_number", phone_number);
                            editor.putString("Profile_image", user_image);
                            editor.apply();




                            if(user_image.equals("null"))
                            {
                                Edituser_image.setImageDrawable(getResources().getDrawable(R.drawable.users));

                            }
                            else {

                                String url_image = Utility.Base_Image_Url + user_image;
                                Glide.with(EditProfile_Activity.this).load(url_image)
                                        .crossFade()
                                        .thumbnail(0.5f)
                                        .bitmapTransform(new CircleTransform(EditProfile_Activity.this))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(Edituser_image);

                            }

                        }


                    }
                    else{

                    }

                } catch (JSONException e) {


                   Toast.makeText(EditProfile_Activity.this, ""+e, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(EditProfile_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(EditProfile_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(EditProfile_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(EditProfile_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(EditProfile_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(EditProfile_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditProfile_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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


}
