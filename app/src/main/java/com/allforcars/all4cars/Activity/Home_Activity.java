package com.allforcars.all4cars.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.LoyalityHistory_Adapter;
import com.allforcars.all4cars.Fragment.List_Fragment;
import com.allforcars.all4cars.Fragment.Map_Fragment;
import com.allforcars.all4cars.Fragment.Radar_Fragment;
import com.allforcars.all4cars.Model.Getuserdata_Model;
import com.allforcars.all4cars.Model.Notification_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Response.Getuserdata_Response;
import com.allforcars.all4cars.Response.Loyalityhistry_Response;
import com.allforcars.all4cars.Response.Notification_Response;
import com.allforcars.all4cars.RetroifitApi.APIService;
import com.allforcars.all4cars.RetroifitApi.ApiClient;
import com.allforcars.all4cars.Venderlogin.Activity.Contactus_Activity;
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
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;

import static com.allforcars.all4cars.classes.Utility.Base_URL;


public class Home_Activity extends SlidingFragmentActivity  {

    private  RelativeLayout button_Menu;
    private Home_Activity home_Activity;
    private  ImageButton btnMenu;
    private  RelativeLayout image_background, menu_howtouse,select_img,menu_report,menu_recomended,Compare_price,menu_privacy,Gas_pricemap,menu_faq,menu_profile,user_logout,radar_data,menu_bonus_reddem,Map_data,menu_aboutus,list_items,change_password,home_btn,menu_history,menu_loyaliyt,menu_catogtrancker,menu_help,menu_bonus_setting;
    public   LinearLayout _linearnotification_parent;
    public  TextView text_notification_countmsg;
    private  String contactperson,user_id="",address,Tabopens="sda",Filterkey="",Catergorylist,notification_status;
    public static int width, height;
    TextView user_profilename,text_notification_user;
    private   ImageView blur_image;
    private String user_name,user_image,user_email,phone_number,document,Cartlistnumber;
    SharedPreferences sharedPreferences;
    private LinearLayout filter_btn;
    String name_key="",status_count;
    ArrayList<Notification_Model> Arraylist_notification;
    ArrayList<Getuserdata_Model> Arraylist_userdata;
    String strfilter="",usertype;
    int count,noticount;
    public static int sCorner = 15;
    public static int sMargin = 2;
    public static int sBorder = 10;
    public static String sColor = "#7D9067";
    private LinearLayout notification_user;
    private  ImageView image_list,image_map,image_radar;
    private  TextView list_name,text_map,text_radar;
    de.hdodenhof.circleimageview.CircleImageView userprofile_image;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {

            setBehindContentView(R.layout.activity_menu);


            if (!isTaskRoot()
                    && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                    && getIntent().getAction() != null
                    && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

                finish();
                return;
            }

            image_background                 = (RelativeLayout) findViewById(R.id.image_background);
            menu_howtouse                 = (RelativeLayout) findViewById(R.id.menu_howtouse);
            select_img                 = (RelativeLayout) findViewById(R.id.select_img);
            menu_report                 = (RelativeLayout) findViewById(R.id.menu_report);
            menu_recomended                 = (RelativeLayout) findViewById(R.id.menu_recomended);
            image_map                 = (ImageView) findViewById(R.id.image_map);
            image_radar                 = (ImageView) findViewById(R.id.image_radar);
            text_map                 = (TextView) findViewById(R.id.text_map);
            text_radar                 = (TextView) findViewById(R.id.text_radar);
            Compare_price                 = (RelativeLayout)findViewById(R.id.Compare_price);
            Gas_pricemap                 = (RelativeLayout)findViewById(R.id.Gas_pricemap);
            menu_privacy                 = (RelativeLayout)findViewById(R.id.menu_privacy);
            menu_faq                     = (RelativeLayout)findViewById(R.id.menu_faq);
            menu_bonus_reddem            = (RelativeLayout)findViewById(R.id.menu_bonus_reddem);
            menu_aboutus                 = (RelativeLayout)findViewById(R.id.menu_aboutus);
            menu_history                 = (RelativeLayout)findViewById(R.id.menu_history);
            menu_loyaliyt                = (RelativeLayout)findViewById(R.id.menu_loyaliyt);
            menu_catogtrancker           = (RelativeLayout)findViewById(R.id.menu_catogtrancker);
            menu_help                    = (RelativeLayout)findViewById(R.id.menu_help);
            home_btn                     = (RelativeLayout)findViewById(R.id.home_btn);
            change_password              = (RelativeLayout)findViewById(R.id.change_password);
            radar_data                   = (RelativeLayout)findViewById(R.id.radar_data);
            Map_data                     = (RelativeLayout)findViewById(R.id.Map_data);
            list_items                   = (RelativeLayout)findViewById(R.id.list_items);
            user_logout                  = (RelativeLayout)findViewById(R.id.user_logout);
            menu_profile                 = (RelativeLayout)findViewById(R.id.menu_profile);
            menu_bonus_setting           = (RelativeLayout)findViewById(R.id.menu_bonus_setting);
            userprofile_image            = (de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.userprofile_image);
            user_profilename             = (TextView)findViewById(R.id.user_profilename);
            text_notification_user       = (TextView)findViewById(R.id.text_notification_user);
            filter_btn       = (LinearLayout)findViewById(R.id.filter_btn);
            notification_user       = (LinearLayout)findViewById(R.id.notification_user);
            blur_image                   =(ImageView)findViewById(R.id.blur_image);
            image_list                   =(ImageView)findViewById(R.id.image_list);
            list_name                   =(TextView)findViewById(R.id.list_name);

            _linearnotification_parent       = (LinearLayout)findViewById(R.id._linearnotification_parent);
            text_notification_countmsg       = (TextView) findViewById(R.id.text_notification_countmsg);

            SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
            user_id =sharedpreference.getString("user_id","");
            usertype =sharedpreference.getString("usertype","");
            Tabopens = getIntent().getStringExtra("Tabopens");
            user_image =sharedpreference.getString("Profile_image","");
            strfilter=getIntent().getStringExtra("Filter");





//

            List_Fragment dashboardFragment = new List_Fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_contaner, dashboardFragment);
            fragmentTransaction.commit();

            image_list.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tab_btn), android.graphics.PorterDuff.Mode.MULTIPLY);
            list_name.setTextColor(getResources().getColor(R.color.tab_btn));

            image_map.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white_color), android.graphics.PorterDuff.Mode.MULTIPLY);
            text_map.setTextColor(getResources().getColor(R.color.white_color));

            image_radar.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white_color), android.graphics.PorterDuff.Mode.MULTIPLY);
            text_radar.setTextColor(getResources().getColor(R.color.white_color));






            select_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent ints = new Intent(Home_Activity.this,UserProfile_Activity.class);
                    startActivity(ints);


                }
            });

            menu_howtouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));



                    String url_image = Utility.Calteroy_icon + document;

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_image));
                    startActivity(browserIntent);


                }
            });


            list_items.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*opening dashboard fragment*/
                    List_Fragment dashboardFragment = new List_Fragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_contaner, dashboardFragment);
                    fragmentTransaction.commit();

                    image_list.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tab_btn), android.graphics.PorterDuff.Mode.MULTIPLY);
                    list_name.setTextColor(getResources().getColor(R.color.tab_btn));

                    image_map.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                    text_map.setTextColor(getResources().getColor(R.color.white_color));

                    image_radar.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                    text_radar.setTextColor(getResources().getColor(R.color.white_color));


                }
            });

            home_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    getSlidingMenu().toggle();
                    List_Fragment dashboardFragment = new List_Fragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_contaner, dashboardFragment);
                    fragmentTransaction.commit();
                    image_list.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tab_btn), android.graphics.PorterDuff.Mode.MULTIPLY);
                    list_name.setTextColor(getResources().getColor(R.color.tab_btn));

                    image_map.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                    text_map.setTextColor(getResources().getColor(R.color.white_color));

                    image_radar.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                    text_radar.setTextColor(getResources().getColor(R.color.white_color));






                }
            });


            Map_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*opening dashboard fragment*/
                    Map_Fragment dashboardFragment = new Map_Fragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_contaner, dashboardFragment);
                    fragmentTransaction.commit();

                    image_list.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                    list_name.setTextColor(getResources().getColor(R.color.white_color));

                    image_map.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tab_btn), android.graphics.PorterDuff.Mode.MULTIPLY);
                    text_map.setTextColor(getResources().getColor(R.color.tab_btn));

                    image_radar.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                    text_radar.setTextColor(getResources().getColor(R.color.white_color));



                }
            });



            radar_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*opening dashboard fragment*/
                    Radar_Fragment dashboardFragment = new Radar_Fragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_contaner, dashboardFragment);
                    fragmentTransaction.commit();

                    image_list.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                    list_name.setTextColor(getResources().getColor(R.color.white_color));

                    image_map.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                    text_map.setTextColor(getResources().getColor(R.color.white_color));

                    image_radar.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tab_btn), android.graphics.PorterDuff.Mode.MULTIPLY);
                    text_radar.setTextColor(getResources().getColor(R.color.tab_btn));

                }
            });

            menu_bonus_reddem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,RedmeeHistory_Activity.class);
                    startActivity(ints);

                }
            });

            menu_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,History_Activity.class);
                    startActivity(ints);

                }
            });

            Gas_pricemap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,GasPrice_Map_Activity.class);
                    startActivity(ints);

                }
            });

            menu_faq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,Faq_Activity.class);
                    startActivity(ints);

                }
            });
            Compare_price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,ComparePrice_Activity.class);
                    startActivity(ints);

                }
            });

            menu_recomended.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,Recommend_user_Activity.class);
                    ints.putExtra("Recomendation","Userside");
                    startActivity(ints);

                }
            });

            image_background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(Home_Activity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.viewiamge);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ImageView profilePicFullScreen = (ImageView) dialog.findViewById(R.id.profilePicFullScreen);
                    ImageView imageView = (ImageView) dialog.findViewById(R.id.close);

                    String url_image = Utility.Base_Image_Url + user_image;
                    Glide.with(Home_Activity.this).load(url_image)
                            .crossFade()
                            .thumbnail(0.5f)
                            .into(profilePicFullScreen);


                    dialog.show();
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });


            _linearnotification_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (isOnline()) {

                        if(Cartlistnumber.equals("0"))
                        {

                        }
                        else {

                            Intent ints = new Intent(Home_Activity.this,Mycart_Activity.class);
                            ints.putExtra("Mycart","AddtoCart");
                            startActivity(ints);
                        }


                    } else {
                        Toast.makeText(Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                    }



                }
            });

            notification_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent ints = new Intent(Home_Activity.this,User_Notification_Activity.class);
                    ints.putExtra("notification","Userside");
                    startActivity(ints);
                }
            });

            menu_loyaliyt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,Bonus_Activity.class);
                    startActivity(ints);

                }
            });
            menu_bonus_setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,Loyalaty_Activity.class);
                    startActivity(ints);

                }
            });
            menu_help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu_help.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,Help_Activity.class);
                    ints.putExtra("help","Userside");
                    startActivity(ints);

                }
            });

            menu_privacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,PrivacyPolicy_Activity.class);
                    startActivity(ints);
                }
            });

            menu_catogtrancker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,OutageTracker_Activity.class);
                    startActivity(ints);
                }
            });
            menu_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_profile.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,EditProfile_Activity.class);
                    startActivity(ints);

                }
            });
            menu_aboutus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,Contactus_Activity.class);
                    startActivity(ints);

                }
            });



            change_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Intent ints = new Intent(Home_Activity.this,Setting_Activity.class);
                    startActivity(ints);

                }
            });

            menu_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_report.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    Intent ints = new Intent(Home_Activity.this,UserReport_Activity.class);
                    startActivity(ints);

                }
            });

            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            button_Menu       = (RelativeLayout)findViewById(R.id.button_Menu);
            btnMenu           = (ImageButton)findViewById(R.id.btnMenu);


            final Display display = getWindowManager().getDefaultDisplay();
            int screenWidth = display.getWidth();
            //int screenWidth = getScreenWidthInPixel();
            final int slidingmenuWidth = (int) (screenWidth - (screenWidth / 4.9) + 23);
            final int offset = Math.max(0, screenWidth - slidingmenuWidth);
            getSlidingMenu().setBehindOffset(offset);
            getSlidingMenu().toggle();
            getSlidingMenu().isVerticalFadingEdgeEnabled();
            getSlidingMenu().isHorizontalFadingEdgeEnabled();
            getSlidingMenu().setMode(SlidingMenu.LEFT);
            getSlidingMenu().setFadeEnabled(true);
            getSlidingMenu().setFadeDegree(0.8f);
            getSlidingMenu().setFadingEdgeLength(13);
            getSlidingMenu().setEnabled(false);
            width = display.getWidth(); // deprecated
            height = display.getHeight();
            home_Activity = Home_Activity.this;

            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showMenu();
                        //button_Menu.setBackgroundResource(R.drawable.menuicon);
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        if (inputMethodManager != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                            }
                        }
                    } catch (NullPointerException e) {
                        // TODO: handle exception
                    }
                }
            });

            button_Menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showMenu();
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        assert inputMethodManager != null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                        }
                    } catch (NullPointerException e) {
                        // TODO: handle exception
                    }
                }
            });




            user_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    menu_catogtrancker.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_reddem.setBackgroundColor(getResources().getColor(R.color.white_color));
                    user_logout.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                    home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_history.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Gas_pricemap.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_faq.setBackgroundColor(getResources().getColor(R.color.white_color));
                    Compare_price.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_recomended.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_howtouse.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_loyaliyt.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_bonus_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_privacy.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                    menu_aboutus.setBackgroundColor(getResources().getColor(R.color.white_color));
                    change_password.setBackgroundColor(getResources().getColor(R.color.white_color));


                    new AlertDialog.Builder(Home_Activity.this)
                            .setIcon(R.mipmap.app_logo)
                            .setTitle("Logout")
                            .setMessage("Are You sure want to logout?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final ProgressDialog progressDialog = ProgressDialog.show(Home_Activity.this,"", "Logout Successfully", true);
                                    progressDialog.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            SharedPreferences sharedPreferences= getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor=sharedPreferences.edit();
                                            editor.putString("user_id", "");
                                            editor.putString("usertype","");
                                            editor.clear();
                                            editor.apply();
//                        LoginManager.getInstance().logOut();
                                            Intent intent = new Intent(Home_Activity.this, Login_Activity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            progressDialog.dismiss();
                                            Home_Activity.this.finish();
                                        }
                                    }, 2000);
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();


                }
            });


            filter_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent ints = new Intent(Home_Activity.this,Filter_Activity.class);
                    startActivity(ints);


                }
            });




        } catch (Exception e) {

            e.printStackTrace();
        }





















        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }


    }



    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        user_name =sharedpreference.getString("user_name","");
        user_image =sharedpreference.getString("Profile_image","");
        Catergorylist =sharedpreference.getString("Catergorylist","");
        strfilter=sharedpreference.getString("Filter","");


              if(strfilter.equals("Filter")) {

                  List_Fragment dashboardFragment = new List_Fragment();
                  FragmentManager fragmentManager = getSupportFragmentManager();
                  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                  fragmentTransaction.replace(R.id.fragment_contaner, dashboardFragment);
                  fragmentTransaction.commit();
                  image_list.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tab_btn), android.graphics.PorterDuff.Mode.MULTIPLY);
                  list_name.setTextColor(getResources().getColor(R.color.tab_btn));

                  image_map.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                  text_map.setTextColor(getResources().getColor(R.color.white_color));

                  image_radar.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                  text_radar.setTextColor(getResources().getColor(R.color.white_color));

              }

              else {


              }

            if(user_image.equals("null"))
            {
                userprofile_image.setImageDrawable(getResources().getDrawable(R.drawable.user_white));

            }
            else {


                user_profilename.setText(user_name);
                String url_image = Utility.Base_Image_Url + user_image;
                Glide.with(Home_Activity.this).load(url_image)
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(Home_Activity.this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(userprofile_image);

                Glide.with(Home_Activity.this).load(url_image)
                        .bitmapTransform(new BlurTransformation(Home_Activity.this))
                        .into((blur_image));


            }





            Getuserinfor_api();
            Get_cartnumber();
            Get_Notification();
            Get_howtouse();




    }

    public void  Get_howtouse(){

        String urljsonobj_group = Base_URL+"how_to_use";
        final ProgressDialog progressDialog = new ProgressDialog(Home_Activity.this);
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
                    Toast.makeText(Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Home_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Home_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Home_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Home_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }






    private void Getuserinfor_api()
    {

        final ProgressDialog progressDialog = new ProgressDialog(Home_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();;

        APIService service = ApiClient.getClient().create(APIService.class);
        retrofit2.Call<Getuserdata_Response> call= service.getrespnce(user_id,usertype);

        //calling the api
        call.enqueue(new Callback<Getuserdata_Response>() {
            @Override
            public void onResponse(retrofit2.Call<Getuserdata_Response> call, retrofit2.Response<Getuserdata_Response> response) {

                progressDialog.dismiss();

                try
                {
                    if (response.isSuccessful())
                    {

                        Getuserdata_Response result=response.body();

                        Arraylist_userdata=result.getUserdata();

                        for(int i=0;i<Arraylist_userdata.size();i++) {

                            user_name = Arraylist_userdata.get(i).getname();
                            user_email = Arraylist_userdata.get(i).getemail();
                            phone_number = Arraylist_userdata.get(i).getphone_number();
                            user_image = Arraylist_userdata.get(i).getuser_image();
                            notification_status = Arraylist_userdata.get(i).getnotification_status();


                            user_profilename.setText(user_name);



                            sharedPreferences= getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("user_name",user_name);
                            editor.putString("user_email", user_email);
                            editor.putString("phone_number", phone_number);
                            editor.putString("Profile_image", user_image);
                            editor.putString("notification_status", notification_status);
                            editor.apply();




                            if(user_image.equals(null))
                            {
                                userprofile_image.setImageDrawable(getResources().getDrawable(R.drawable.users));

                            }
                            else {

                                String url_image = Utility.Base_Image_Url + user_image;
                                Glide.with(Home_Activity.this).load(url_image)
                                        .crossFade()
                                        .thumbnail(0.5f)
                                        .bitmapTransform(new CircleTransform(Home_Activity.this))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(userprofile_image);

                                Glide.with(Home_Activity.this).load(url_image)
                                        .bitmapTransform(new BlurTransformation(Home_Activity.this))
                                        .into((blur_image));
                                progressDialog.dismiss();


                            }


                        }





                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Home_Activity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
                                case 400:
                                    Toast.makeText(Home_Activity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Home_Activity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Home_Activity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Home_Activity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Home_Activity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Home_Activity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Home_Activity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Home_Activity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        catch (Exception e)
                        {
                            Toast.makeText(Home_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Getuserdata_Response> call, Throwable t) {
                progressDialog.dismiss();

                // Toast.makeText(Login_Activity.this, "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Login_Activity.this,t.getMessage(), Toast.LENGTH_LONG).show();

                if (t instanceof IOException) {
                    Toast.makeText(Home_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Log.e("conversion issue",t.getMessage());
                    Toast.makeText(Home_Activity.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(Login_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }
            }
        });
    }



    private void Get_cartnumber() {
        String urll= Utility.Base_URL+"cart_list";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        count = jsonArray.length();
                        text_notification_countmsg.setText(String.valueOf(count));
                        Cartlistnumber = String.valueOf(count);



                    }

                    else{
                        //Toast.makeText(getActivity(), " "+message, Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {


                    //   Toast.makeText(getActivity(), ,+e Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppController.getInstance().getRequestQueue().cancelAll("user_Sign_up");

                if (error instanceof NetworkError) {

                    Toast.makeText(Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Home_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {

                    Toast.makeText(Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Home_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {

                    Toast.makeText(Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {

                        Toast.makeText(Home_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Home_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("user_id",user_id );

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }



    private void Get_Notification()
    {

        final ProgressDialog progressDialog = new ProgressDialog(Home_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();;

        APIService service = ApiClient.getClient().create(APIService.class);
        retrofit2.Call<Notification_Response> call= service.notificationstaus(user_id,usertype);

        //calling the api
        call.enqueue(new Callback<Notification_Response>() {
            @Override
            public void onResponse(retrofit2.Call<Notification_Response> call, retrofit2.Response<Notification_Response> response) {

                progressDialog.dismiss();

                try
                {
                    if (response.isSuccessful())
                    {
                        int sumstatus=0;
                        Notification_Response result=response.body();

                        Arraylist_notification=result.getnotificatin();

                        for(int i=0;i<Arraylist_notification.size();i++) {
                            status_count = Arraylist_notification.get(i).getstatus();
                            sumstatus =sumstatus+(Integer.parseInt(status_count));
                        }
                        text_notification_user.setText((String.valueOf(sumstatus)));
                        progressDialog.dismiss();



                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Home_Activity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
                                case 400:
                                    Toast.makeText(Home_Activity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Home_Activity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Home_Activity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Home_Activity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Home_Activity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Home_Activity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Home_Activity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Home_Activity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        catch (Exception e)
                        {
                            Toast.makeText(Home_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Notification_Response> call, Throwable t) {
                progressDialog.dismiss();

                // Toast.makeText(Login_Activity.this, "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Login_Activity.this,t.getMessage(), Toast.LENGTH_LONG).show();

                if (t instanceof IOException) {
                    Toast.makeText(Home_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Log.e("conversion issue",t.getMessage());
                    Toast.makeText(Home_Activity.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(Login_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }
            }
        });
    }


    public boolean isOnline() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    public void refreshMyData(){
        //Code to refresh Activity Data hear
    }


}
