package com.allforcars.all4cars.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.Notification_Adapter;
import com.allforcars.all4cars.Adapter.RedeemHistory_Adapter;
import com.allforcars.all4cars.Model.Notification_Model;
import com.allforcars.all4cars.Model.RedeemHistory_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
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
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class User_Notification_Activity extends AppCompatActivity {

    ImageView btnback;
    String user_id,usertype;
    RecyclerView.LayoutManager layoutManager;
    Notification_Adapter notification_adapter;
    ArrayList<Notification_Model> Arraylist_notification;
    RecyclerView recycler_notification;
    String notification_type;
    LinearLayout internetnotconnet;
    private ShimmerFrameLayout mShimmerViewContainer;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Button btn_internet;


  int[] animationList = {R.anim.layout_animation_up_to_down, R.anim.layout_animation_right_to_left, R.anim.layout_animation_down_to_up, R.anim.layout_animation_left_to_right};

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__notification);

        btnback= findViewById(R.id.btnback);
        btn_internet= findViewById(R.id.btn_internet);
        internetnotconnet= findViewById(R.id.internetnotconnet);
        recycler_notification= findViewById(R.id.recycler_notification);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Get_Notification_user();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        btn_internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Get_Notification_user();
            }
        });



        recycler_notification.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
        });

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");
        notification_type= getIntent().getStringExtra("notification");


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        Get_Notification_user();






    }

    public void  Get_Notification_user()

    {

        String urljsonobj_group = Base_URL+"get_allnotification?user_id="+user_id+"&user_type="+usertype+"";

        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Arraylist_notification = new ArrayList<>();
                    if (response.getString("success").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = response.getJSONArray("record");
                        internetnotconnet.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setVisibility(View.VISIBLE);


                        for (int i = 0; i < jsonArray.length(); i++)

                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Notification_Model addtcart = new Notification_Model();
                            addtcart.setdate(jsonObject1.getString("created_at"));
                            addtcart.setmessage(jsonObject1.getString("message"));
                            addtcart.setnotification_id(jsonObject1.getString("notification_id"));
                            addtcart.settitle(jsonObject1.getString("title"));

                            Arraylist_notification.add(addtcart);



                        }


                        notification_adapter = new Notification_Adapter(User_Notification_Activity.this, Arraylist_notification);
                        recycler_notification.setLayoutManager(new LinearLayoutManager(User_Notification_Activity.this, LinearLayoutManager.VERTICAL, false));
                        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(User_Notification_Activity.this, animationList[i]);

                        recycler_notification.setLayoutAnimation(controller);
                        notification_adapter.notifyDataSetChanged();
                        recycler_notification.scheduleLayoutAnimation();
                        recycler_notification.setAdapter(notification_adapter);


                    }
                    else {



                    }



                }
                catch (JSONException e) {
                    AppController.getInstance().getRequestQueue().cancelAll("survey_list");
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AppController.getInstance().getRequestQueue().cancelAll("survey_list");
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                if (error instanceof NetworkError) {

                    mSwipeRefreshLayout.setVisibility(View.GONE);
                    internetnotconnet.setVisibility(View.VISIBLE);
                } else if (error instanceof ServerError) {
                    mSwipeRefreshLayout.setVisibility(View.GONE);
                    internetnotconnet.setVisibility(View.VISIBLE);

                } else if (error instanceof AuthFailureError) {
                    mSwipeRefreshLayout.setVisibility(View.GONE);
                    internetnotconnet.setVisibility(View.VISIBLE);
                } else if (error instanceof ParseError) {

                    Toast.makeText(User_Notification_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {

                    mSwipeRefreshLayout.setVisibility(View.GONE);
                    internetnotconnet.setVisibility(View.VISIBLE);

                } else {
                    if (error instanceof TimeoutError) {

                        mSwipeRefreshLayout.setVisibility(View.GONE);
                        internetnotconnet.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(User_Notification_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }


    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
}
