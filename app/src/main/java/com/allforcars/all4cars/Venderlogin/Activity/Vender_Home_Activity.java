package com.allforcars.all4cars.Venderlogin.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

import com.allforcars.all4cars.Activity.Help_Activity;
import com.allforcars.all4cars.Activity.Home_Activity;
import com.allforcars.all4cars.Activity.Login_Activity;
import com.allforcars.all4cars.Activity.Recommend_user_Activity;
import com.allforcars.all4cars.Activity.Setting_Activity;
import com.allforcars.all4cars.Activity.User_Notification_Activity;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Response.PaymentslipResponse;
import com.allforcars.all4cars.Venderlogin.Adapter.PaymentHistory_Adapter;
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
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Vender_Home_Activity extends SlidingFragmentActivity {

    RelativeLayout vender_button_Menu;
    ImageButton vender_btnMenu;
    Vender_Home_Activity vender_home_Activity;
    public static int width, height;
    RelativeLayout vender_reconmendation,vender_subscription,vender_paymetslip,vender_menu_help,vender_home_btn,vender_menu_profile,vender_setting,vender_menu_history,vender_logout;
    String notification_status,user_id,usertype,status_count;
    String user_name,user_image,user_email,phone_number,logo_image,banner_image,contactperson,address;
    SharedPreferences sharedPreferences;
    TextView vender_profilename,txt_venrdrname_main,text_notification_vender;
    ImageView venderuserprofile_image,img_banner_main,img_logo_main,blur_image;
    CardView categorylist,vender_product_list,myprofile,Vender_myaccount;
    RelativeLayout select_img_vender,vender_products,vender_categry,vender_menu_orderhistory,vender_menu_myaccount,vender_menu_orderlist,vender_menu_contacat_is;
    LinearLayout notification_Vender;
    int noticount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender__home);
        setBehindContentView(R.layout.activity_vender_menu);

        vender_subscription=(RelativeLayout)findViewById(R.id.vender_subscription);
        vender_reconmendation=(RelativeLayout)findViewById(R.id.vender_reconmendation);
        select_img_vender=(RelativeLayout)findViewById(R.id.select_img_vender);
        vender_paymetslip=(RelativeLayout)findViewById(R.id.vender_paymetslip);
        blur_image=(ImageView)findViewById(R.id.blur_image_vender);
        vender_menu_help=(RelativeLayout)findViewById(R.id.vender_menu_help);
        vender_menu_orderhistory=(RelativeLayout)findViewById(R.id.vender_menu_orderhistory);
        vender_menu_contacat_is=(RelativeLayout)findViewById(R.id.vender_menu_contacat_is);
        vender_menu_orderlist=(RelativeLayout)findViewById(R.id.vender_menu_orderlist);
        vender_menu_myaccount=(RelativeLayout)findViewById(R.id.vender_menu_myaccount);
        vender_categry=(RelativeLayout)findViewById(R.id.vender_categry);
        vender_products=(RelativeLayout)findViewById(R.id.vender_products);
        vender_home_btn=(RelativeLayout)findViewById(R.id.vender_home_btn);
        vender_menu_profile=(RelativeLayout)findViewById(R.id.vender_menu_profile);
        vender_setting=(RelativeLayout)findViewById(R.id.vender_setting);
        vender_logout=(RelativeLayout)findViewById(R.id.vender_logout);
        vender_profilename=(TextView)findViewById(R.id.vender_profilename);
        venderuserprofile_image=(ImageView)findViewById(R.id.venderuserprofile_image);
        txt_venrdrname_main=(TextView)findViewById(R.id.txt_venrdrname_main);
        text_notification_vender=(TextView)findViewById(R.id.text_notification_vender);
        img_banner_main=(ImageView)findViewById(R.id.img_banner_main);
        img_logo_main=(ImageView)findViewById(R.id.img_logo_main);
        categorylist=(CardView) findViewById(R.id.categorylist);
        vender_product_list=(CardView) findViewById(R.id.vender_product_list);
        myprofile=(CardView) findViewById(R.id.myprofile);
        Vender_myaccount=(CardView) findViewById(R.id.Vender_myaccount);
        notification_Vender=(LinearLayout) findViewById(R.id.notification_Vender);

        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }


        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");


        select_img_vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnline()) {


                    Intent ints = new Intent(Vender_Home_Activity.this,VenderPorfileupdate_Activity.class);
                    startActivity(ints);

                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }


            }
        });

        categorylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));

                if (isOnline()) {


                    Intent ints = new Intent(Vender_Home_Activity.this,VenderCategory_Activity.class);
                    startActivity(ints);


                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }


            }
        });

        vender_reconmendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));

                if (isOnline()) {


                    Intent ints = new Intent(Vender_Home_Activity.this,Recommend_user_Activity.class);
                    ints.putExtra("Recomendation","Venderside");
                    startActivity(ints);


                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }



            }
        });

        vender_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));

                if (isOnline()) {


                    Intent ints = new Intent(Vender_Home_Activity.this,Subscription_Activity.class);
                    startActivity(ints);

                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }




            }
        });




        vender_menu_myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));

                if (isOnline()) {


                    Intent ints = new Intent(Vender_Home_Activity.this,Vender_Transaction_Activity.class);
                    startActivity(ints);


                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }


            }
        });

        Vender_myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnline()) {


                    Intent ints = new Intent(Vender_Home_Activity.this,Vender_Transaction_Activity.class);
                    startActivity(ints);



                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }


            }
        });

        vender_paymetslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));

                if (isOnline()) {


                    Intent ints = new Intent(Vender_Home_Activity.this,Paymentslip_Activity.class);
                    startActivity(ints);


                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }



            }
        });

        venderuserprofile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Vender_Home_Activity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.viewiamge);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageView profilePicFullScreen = (ImageView) dialog.findViewById(R.id.profilePicFullScreen);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.close);

                String url_image = Utility.logo_url + logo_image;
                Glide.with(Vender_Home_Activity.this).load(url_image)
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

        notification_Vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnline()) {


                    Intent ints = new Intent(Vender_Home_Activity.this,User_Notification_Activity.class);
                    ints.putExtra("notification","Venderside");
                    startActivity(ints);

                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }



            }
        });
        vender_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));
                getSlidingMenu().toggle();

            }
        });



        vender_menu_orderhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));

                if (isOnline()) {


                    Intent ints = new Intent(Vender_Home_Activity.this,VenderOrderHistory_Activity.class);
                    startActivity(ints);
                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }



            }
        });

        vender_categry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));

                if (isOnline()) {

                    Intent ints = new Intent(Vender_Home_Activity.this,VenderCategory_Activity.class);
                    startActivity(ints);

                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }


            }
        });

        vender_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));

                if (isOnline()) {

                    Intent ints = new Intent(Vender_Home_Activity.this,Vender_Setting_Activity.class);
                    startActivity(ints);
                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }



            }
        });

        vender_menu_contacat_is.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));

                if (isOnline()) {

                    Intent ints = new Intent(Vender_Home_Activity.this,Contactus_Activity.class);
                    startActivity(ints);
                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }



            }
        });

        vender_menu_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));

                if (isOnline()) {

                    Intent ints = new Intent(Vender_Home_Activity.this,Vender_Profile_Activity.class);
                    startActivity(ints);
                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }



            }
        });

        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnline()) {

                    Intent ints = new Intent(Vender_Home_Activity.this,Vender_Profile_Activity.class);
                    startActivity(ints);
                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }




            }
        });

        vender_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));

                if (isOnline()) {

                    Intent ints = new Intent(Vender_Home_Activity.this,ProductService_Activity.class);
                    startActivity(ints);
                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }



            }
        });

        vender_product_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));

                if (isOnline()) {

                    Intent ints = new Intent(Vender_Home_Activity.this,ProductService_Activity.class);
                    startActivity(ints);

                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }


            }
        });


        vender_menu_orderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));


                if (isOnline()) {
                    Intent ints = new Intent(Vender_Home_Activity.this,Orderlist_Activity.class);
                    startActivity(ints);

                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }




            }
        });

        vender_menu_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));


                if (isOnline()) {

                    Intent ints = new Intent(Vender_Home_Activity.this,Help_Activity.class);
                    ints.putExtra("help","Venderside");
                    startActivity(ints);

                } else {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }



            }
        });

        Getuserinfor_api();










        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        vender_button_Menu       = (RelativeLayout)findViewById(R.id.vender_button_Menu);
        vender_btnMenu           = (ImageButton)findViewById(R.id.vender_btnMenu);


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
        vender_home_Activity = Vender_Home_Activity.this;

        vender_button_Menu.setOnClickListener(new View.OnClickListener() {
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

        vender_btnMenu.setOnClickListener(new View.OnClickListener() {
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


        vender_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                vender_menu_myaccount.setBackgroundColor(getResources().getColor(R.color.white_color));
                categorylist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_paymetslip.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_home_btn.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderhistory.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_categry.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_setting.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_contacat_is.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_profile.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_orderlist.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_menu_help.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_logout.setBackgroundColor(getResources().getColor(R.color.menu_btn));
                vender_product_list.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_products.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_subscription.setBackgroundColor(getResources().getColor(R.color.white_color));
                vender_reconmendation.setBackgroundColor(getResources().getColor(R.color.white_color));



                new AlertDialog.Builder(Vender_Home_Activity.this)
                        .setIcon(R.mipmap.app_logo)
                        .setTitle("Logout")
                        .setMessage("Are You sure want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = ProgressDialog.show(Vender_Home_Activity.this,"", "Logout Sucessfully", true);
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
                                        Intent intent = new Intent(Vender_Home_Activity.this, Login_Activity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        progressDialog.dismiss();
                                        Vender_Home_Activity.this.finish();
                                    }
                                }, 2000);
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();


            }
        });


    }

    private void Getuserinfor_api() {
        final ProgressDialog pd = new ProgressDialog(Vender_Home_Activity.this);
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

                            user_name = (jsonObject1.getString("company_name"));
                            user_email = (jsonObject1.getString("fld_email"));
                            phone_number = (jsonObject1.getString("phone"));
                            logo_image = (jsonObject1.getString("logo"));
                            banner_image = (jsonObject1.getString("image"));
                            notification_status = (jsonObject1.getString("notification_status"));
                            contactperson = (jsonObject1.getString("fld_name"));
                            address = (jsonObject1.getString("address"));



                            vender_profilename.setText(user_name);
                            txt_venrdrname_main.setText(user_name);


                            sharedPreferences= getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("user_name",user_name);
                            editor.putString("user_email", user_email);
                            editor.putString("phone_number", phone_number);
                            editor.putString("Profile_image", user_image);
                            editor.putString("contactperson", contactperson);
                            editor.putString("address", address);
                            editor.putString("notification_status", notification_status);
                            editor.apply();




                            if(user_image.equals(null))
                            {
                                venderuserprofile_image.setImageDrawable(getResources().getDrawable(R.drawable.users));

                            }
                            else {

                                String url_image = Utility.logo_url + logo_image;
                                Glide.with(Vender_Home_Activity.this).load(url_image)
                                        .crossFade()
                                        .thumbnail(0.5f)
                                        .bitmapTransform(new CircleTransform(Vender_Home_Activity.this))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(venderuserprofile_image);

                                String url_images = Utility.Vender_banner + banner_image;
                                Glide.with(Vender_Home_Activity.this).load(url_images)
                                        .crossFade()
//                                        .thumbnail(0.5f)
//                                        .bitmapTransform(new CircleTransform(Vender_Home_Activity.this))
//                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(img_banner_main);
                                String url_imag = Utility.logo_url + logo_image;
                                Glide.with(Vender_Home_Activity.this).load(url_imag)
                                        .crossFade()
                                        .thumbnail(0.5f)
                                        .bitmapTransform(new CircleTransform(Vender_Home_Activity.this))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(img_logo_main);
                                Glide.with(Vender_Home_Activity.this).load(url_image)
                                        .bitmapTransform(new BlurTransformation(Vender_Home_Activity.this))
                                        .into((blur_image));


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
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Vender_Home_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Vender_Home_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Vender_Home_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Vender_Home_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        Getuserinfor_api();


        if(user_image.equals("null"))
        {
            venderuserprofile_image.setImageDrawable(getResources().getDrawable(R.drawable.user_white));

        }
        else {


            vender_profilename.setText(user_name);
            String url_image = Utility.logo_url + logo_image;
            Glide.with(Vender_Home_Activity.this).load(url_image)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(Vender_Home_Activity.this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(venderuserprofile_image);

            Glide.with(Vender_Home_Activity.this).load(url_image)
                    .bitmapTransform(new BlurTransformation(Vender_Home_Activity.this))
                    .into((blur_image));

        }


        Get_Notification();




    }
    public boolean isOnline() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    public void  Get_Notification()

    {

        String urljsonobj_group = Base_URL+"get_allnotification?user_id="+user_id+"&user_type="+usertype+"";
        final ProgressDialog progressDialog = new ProgressDialog(Vender_Home_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    int sumstatus=0;
                    if (response.getString("success").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = response.getJSONArray("record");

                        for(int i=0;i<jsonArray.length();i++)


                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            status_count = (jsonObject1.getString("status"));
                            sumstatus =sumstatus+(Integer.parseInt(status_count));


                        }
                        text_notification_vender.setText((String.valueOf(sumstatus)));



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
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Vender_Home_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Vender_Home_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Vender_Home_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Vender_Home_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Vender_Home_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }

}
