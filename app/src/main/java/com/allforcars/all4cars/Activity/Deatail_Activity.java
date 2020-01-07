package com.allforcars.all4cars.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.Addtocart_Adapter;
import com.allforcars.all4cars.Adapter.Categoryicon_Adapter;
import com.allforcars.all4cars.Adapter.Detaillistdata_Adapter;
import com.allforcars.all4cars.Adapter.Fuelsearcher_Adapter;
import com.allforcars.all4cars.Adapter.Loyality_Bonus_Adapter;
import com.allforcars.all4cars.Model.Addtocart_Model;
import com.allforcars.all4cars.Model.Bonus_Model;
import com.allforcars.all4cars.Model.Catergoyicons_Model;
import com.allforcars.all4cars.Model.Feaulserher_Model;
import com.allforcars.all4cars.Model.Product_detail_Model;
import com.allforcars.all4cars.Model.Property_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.GPSTracker;
import com.allforcars.all4cars.classes.RecyclerTouchListener;
import com.allforcars.all4cars.classes.Singleton;
import com.allforcars.all4cars.classes.Utility;
import com.allforcars.all4cars.interfaces.RefreshInterface;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Deatail_Activity extends AppCompatActivity implements RefreshInterface {

    ImageView img_back_deatiil,list_bannerimage,btn_verndarwebsite,btn_phone,feedback;
    String list_Address,list_km,url_imagelogo,Tabopens,category_id,list_companyname,Catergorylist="",urllink;
    TextView text_km,text_listaddress,Companyname,notification_countmsg,txt_braches,txt_brachesnm;
    RecyclerView list_productdeatil,list_categorylsit;
    GPSTracker gps;
    LinearLayout img_notification_cart,btn_maprout,braches_listdetail;
    int count=0;
    private RefreshInterface refreshInterface;
    double longitudes,latitudes;
    String currentlatitude, currentlongtide,total_rating,Banner_image,verder_Email,verder_phone,brachlist,priceamt,Total_Dis;
    Detaillistdata_Adapter detaillistdata_adapter;
    Categoryicon_Adapter categoryicon_adapter;
    RecyclerView.LayoutManager layoutManager,layoutManagers;
    ArrayList<Product_detail_Model> Arraylist_productdetial ;
    ArrayList<Catergoyicons_Model> Arraylist_categrorylist;
    int Final_Dis=0;
    String seekbarValue="",Catergorylist_choose,Map_show="",latitude,logitude,fld_admin="",user_id;
    LinearLayout btn_email,text_productdetailnumber;
    Dialog dialog_main;
    SwipeRefreshLayout SwipeRefresh;
    RatingBar ratingbar_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deatail);



        refreshInterface= this;
        txt_brachesnm                     = findViewById(R.id.txt_brachesnm);
        braches_listdetail                     = findViewById(R.id.braches_listdetail);
        SwipeRefresh                     = findViewById(R.id.swipe_details);
        txt_braches                     = findViewById(R.id.txt_braches);
        ratingbar_rating                 = findViewById(R.id.ratingbar_rating);
        img_back_deatiil                 = findViewById(R.id.img_back_deatiil);
        feedback                 = findViewById(R.id.feedback);
        list_bannerimage                 = findViewById(R.id.image_listbanner);
        text_km                          = findViewById(R.id.text_km);
        btn_email                        = findViewById(R.id.btn_email);
        btn_verndarwebsite               = findViewById(R.id.btn_verndarwebsite);
        btn_phone                        = findViewById(R.id.btn_phone);
        text_productdetailnumber         = findViewById(R.id.text_productdetailnumber);
        btn_maprout                      = findViewById(R.id.btn_maprout);
        list_categorylsit                = findViewById(R.id.list_categorylsit);
        text_listaddress                 = findViewById(R.id.text_listaddress);
        Companyname                      = findViewById(R.id.Companyname);
        list_productdeatil               = (RecyclerView)findViewById(R.id.list_productdeatil);
        notification_countmsg            = findViewById(R.id.notification_countmsg);
        img_notification_cart            = (LinearLayout)findViewById(R.id.img_notification_cart);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        Catergorylist =sharedpreference.getString("Catergorylist","");
        seekbarValue =sharedpreference.getString("seekbarValue","");


        final Intent intent = getIntent();
        fld_admin = intent.getStringExtra("fld_admin_id");
        list_companyname = intent.getStringExtra("companynm");
        list_km = intent.getStringExtra("km");
        list_Address = intent.getStringExtra("Address");
        Banner_image = intent.getStringExtra("Banner_image");
        verder_Email = intent.getStringExtra("Email");
        verder_phone = intent.getStringExtra("Phone");
        category_id = intent.getStringExtra("category_id");
        urllink = intent.getStringExtra("url_link");
        latitude = intent.getStringExtra("latitude");
        logitude = intent.getStringExtra("logitude");
        Map_show = intent.getStringExtra("Map_show");
        Tabopens = intent.getStringExtra("Tabopens");
        brachlist = intent.getStringExtra("brachlist");
        url_imagelogo = intent.getStringExtra("logo");





        if (seekbarValue.equals(""))
        {
            seekbarValue="30";

        }

        else {


        }






        Get_Rating();


        gps = new GPSTracker(Deatail_Activity.this);
        if (gps.canGetLocation()) {
            latitudes = gps.getLatitude();
            longitudes = gps.getLongitude();

            currentlatitude = String.valueOf(latitudes);
            currentlongtide = String.valueOf(longitudes);

        } else {
            gps.showSettingsAlert();
        }





        Companyname.setText(list_companyname);
        text_listaddress.setText("Address :" +" "+list_Address);
        text_km.setText(list_km);

        if(brachlist.equals("100"))
        {
            braches_listdetail.setVisibility(View.GONE);
        }



        if(Integer.parseInt(brachlist)>=3)
        {
            txt_brachesnm.setText("Branches");
            txt_braches.setText(brachlist);
        }
        else {
            txt_brachesnm.setText("Branch");
            txt_braches.setText(brachlist);

        }


        list_categorylsit.addOnItemTouchListener(new RecyclerTouchListener(Deatail_Activity.this, list_categorylsit, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                String name = Arraylist_categrorylist.get(position).getcategroy_name();
                Catergorylist_choose = Arraylist_categrorylist.get(position).getcategory_id();

               GetProduct_bycategry();

                Toast toast = Toast.makeText(Deatail_Activity.this,""+name, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();


            }
            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(Deatail_Activity.this, "Long press on position :"+position, Toast.LENGTH_LONG).show();
            }
        }));

        String url_image = Utility.Vender_banner + Banner_image;
        Glide.with(Deatail_Activity.this).load(url_image)
                .crossFade()
                .thumbnail(0.5f)
                .into(list_bannerimage);


        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + verder_Email));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                    intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                    startActivity(intent);
                }catch(ActivityNotFoundException e){
                    //TODO smth
                }

            }
        });

        braches_listdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(brachlist.equals("0"))
               {

               }
               else
               {
                   Intent ints= new Intent(Deatail_Activity.this,Branchlist_Activity.class);
                   ints.putExtra("fld_admin_id", fld_admin);
                   ints.putExtra("url_link", urllink);
                   ints.putExtra("Banner_image", Banner_image);
                   ints.putExtra("logo", url_imagelogo);
                   startActivity(ints);

               }



            }
        });

        btn_maprout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String geoUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + logitude + " (" + list_Address + ")";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ints= new Intent(Deatail_Activity.this,Rating_Activity.class);
                ints.putExtra("Vender_id",fld_admin);
                ints.putExtra("list_companyname",list_companyname);
                ints.putExtra("url_imagelogo",url_imagelogo);
                startActivity(ints);


            }
        });
        btn_verndarwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                openURL.setData(Uri.parse(urllink));
                startActivity(openURL);
            }
        });


        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                custom_dialog_box();



            }
        });




        list_productdeatil.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Deatail_Activity.this);
        list_productdeatil.setLayoutManager(layoutManager);


        list_categorylsit.setHasFixedSize(true);
        layoutManagers = new LinearLayoutManager(Deatail_Activity.this);
        list_categorylsit.setLayoutManager(layoutManagers);





        img_back_deatiil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//               if(Tabopens.equals("Map"))
////               {
////                   Intent ints= new Intent(Deatail_Activity.this,Home_Activity.class);
////                   ints.putExtra("Tabopens","Map");
////                   startActivity(ints);
////                   finish();
////
////               }
////               else if(Tabopens.equals("Home"))
////               {
////                   Intent ints= new Intent(Deatail_Activity.this,Home_Activity.class);
////                   ints.putExtra("Tabopen","Home");
////                   startActivity(ints);
////                   finish();
////
////               }
                onBackPressed();
            }
        });
        img_notification_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isOnline()) {
                    Intent ints= new Intent(Deatail_Activity.this,Mycart_Activity.class);
                    ints.putExtra("Mycart","AddtoCart");
                    startActivity(ints);
                } else {

                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }






            }
        });

        if (Map_show.equals("Compare_price"))
        {
            GetProduct_FuelApi();

        }

        else if (Map_show.equals("Gas_price"))
        {

            GetProduct_FuelApi();
        }

        else
        {

            if (Catergorylist.equals(""))
            {

                if (isOnline()) {
                  GetProductfirsttiem_api();

                } else {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }
            }

            else {

                if (isOnline()) {
                    GetProduct_api();

                } else {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }

            }


        }

        Get_cartnumber();


        Get_categorylist();




        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefresh.setRefreshing(false);
                if (isOnline()) {
                    Get_cartnumber();


                    Get_categorylist();





                    if (Map_show.equals("Compare_price"))
                    {
                        GetProduct_FuelApi();

                    }

                    else if (Map_show.equals("Gas_price"))
                    {

                        GetProduct_FuelApi();
                    }

                    else
                    {

                        if (Catergorylist.equals(""))
                        {

                            if (isOnline()) {
                                GetProductfirsttiem_api();

                            } else {
                                Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                            }
                        }

                        else {

                            if (isOnline()) {
                                GetProduct_api();

                            } else {
                                Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                            }

                        }


                    }

                } else {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }

            }
        });

    }





    private void Get_cartnumber() {
        final ProgressDialog pd = new ProgressDialog(Deatail_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"cart_list";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        count = jsonArray.length();
                        notification_countmsg.setText(String.valueOf(count));



                    }

                    else{
                        //Toast.makeText(getActivity(), " "+message, Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Deatail_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Deatail_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Deatail_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Deatail_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                map.put("vender_id",fld_admin );

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }



    public void  GetProduct_FuelApi()

    {

        String urljsonobj_group = Base_URL+"gas_maping?longitude="+longitudes+"&latitude="+latitudes+"";
        final ProgressDialog progressDialog = new ProgressDialog(Deatail_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.getString("success").equalsIgnoreCase("true")) {


                        JSONArray jsonArray = response.getJSONArray("record");

                        Arraylist_productdetial = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++)


                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String fld_admin_id = jsonObject1.getString("fld_admin_id");

                            if (fld_admin_id.equals(fld_admin)) {
                                JSONArray products = jsonObject1.getJSONArray("products");
                                count = products.length();
                                if (count == 0) {
                                    text_productdetailnumber.setVisibility(View.VISIBLE);

                                }

                                for (int j = 0; j < products.length(); j++) {


                                    JSONObject jsonObject2 = products.getJSONObject(j);
                                    Product_detail_Model productdetail = new Product_detail_Model();
                                    productdetail.setproduct_id(jsonObject2.getString("id"));
                                    productdetail.setproduct_vernder_id(fld_admin);
                                    productdetail.setproduct_name(jsonObject2.getString("category_name"));
                                    productdetail.setproduct_category_names(jsonObject2.getString("name"));
                                    productdetail.setproduct_image(jsonObject2.getString("product_image"));
                                    productdetail.setproduct_Pricetot(jsonObject2.getString("price"));
                                    productdetail.setproduct_category_id(jsonObject2.getString("category"));
                                    productdetail.setproduct_dis(jsonObject2.getString("customer_discount"));
                                    productdetail.setunit(jsonObject2.getString("unit"));



//
                                    Arraylist_productdetial.add(productdetail);

                                }

                                detaillistdata_adapter = new Detaillistdata_Adapter(Deatail_Activity.this, Arraylist_productdetial, refreshInterface);
                                list_productdeatil.setAdapter(detaillistdata_adapter);
                            }


                        }
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
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Deatail_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Deatail_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Deatail_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Deatail_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }

    public void  GetProduct_api()

    {

        String urljsonobj_group = Base_URL+"filter_multiple_category?longitude="+longitudes+"&latitude="+latitudes+"&category="+Catergorylist+"&rang="+seekbarValue+"";
        final ProgressDialog progressDialog = new ProgressDialog(Deatail_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.getString("success").equalsIgnoreCase("true")) {


                        JSONArray jsonArray = response.getJSONArray("record");

                        Arraylist_productdetial = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++)


                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String fld_admin_id = jsonObject1.getString("fld_admin_id");

                            if (fld_admin_id.equals(fld_admin)) {
                                JSONArray products = jsonObject1.getJSONArray("products");
                                count = products.length();
                                if (count == 0) {
                                    text_productdetailnumber.setVisibility(View.VISIBLE);

                                }

                                for (int j = 0; j < products.length(); j++) {


                                    JSONObject jsonObject2 = products.getJSONObject(j);
                                    Product_detail_Model productdetail = new Product_detail_Model();
                                    productdetail.setproduct_id(jsonObject2.getString("product_id"));
                                    productdetail.setproduct_vernder_id(fld_admin);
                                    // productdetail.setproduct_name(jsonObject2.getString("category_name"));
                                    productdetail.setproduct_category_names(jsonObject2.getString("product_name"));
                                    productdetail.setproduct_image(jsonObject2.getString("banner_image"));
                                    productdetail.setproduct_Pricetot(jsonObject2.getString("price"));
                                    productdetail.setproduct_category_id(jsonObject2.getString("category"));
                                    // productdetail.setproduct_dis(jsonObject2.getString("customer_discount"));
                                    productdetail.setunit(jsonObject2.getString("unit"));

//
                                    Arraylist_productdetial.add(productdetail);

                                }

                                detaillistdata_adapter = new Detaillistdata_Adapter(Deatail_Activity.this, Arraylist_productdetial, refreshInterface);
                                list_productdeatil.setAdapter(detaillistdata_adapter);
                            }


                        }
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
                    progressDialog.dismiss();
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
                 else if (error instanceof AuthFailureError) {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Deatail_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    progressDialog.dismiss();
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        progressDialog.dismiss();
                        Toast.makeText(Deatail_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Deatail_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }

    public void  GetProductfirsttiem_api()

    {

        String urljsonobj_group = Base_URL+"filter_multiple_category?longitude="+longitudes+"&latitude="+latitudes+"&category="+"0"+"&rang="+seekbarValue+"";
        final ProgressDialog progressDialog = new ProgressDialog(Deatail_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.getString("success").equalsIgnoreCase("true")) {


                        JSONArray jsonArray = response.getJSONArray("record");

                        Arraylist_productdetial = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++)


                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String fld_admin_id = jsonObject1.getString("fld_admin_id");

                            if (fld_admin_id.equals(fld_admin)) {
                                JSONArray products = jsonObject1.getJSONArray("products");
                                count = products.length();
                                if (count == 0) {
                                    text_productdetailnumber.setVisibility(View.VISIBLE);

                                }

                                for (int j = 0; j < products.length(); j++) {


                                    JSONObject jsonObject2 = products.getJSONObject(j);
                                    Product_detail_Model productdetail = new Product_detail_Model();
                                    productdetail.setproduct_id(jsonObject2.getString("product_id"));
                                    productdetail.setproduct_vernder_id(fld_admin);
                                    // productdetail.setproduct_name(jsonObject2.getString("category_name"));
                                    productdetail.setproduct_category_names(jsonObject2.getString("product_name"));
                                    productdetail.setproduct_image(jsonObject2.getString("banner_image"));
                                    productdetail.setproduct_Pricetot(jsonObject2.getString("price"));
                                    productdetail.setproduct_category_id(jsonObject2.getString("category"));
                                    // productdetail.setproduct_dis(jsonObject2.getString("customer_discount"));
                                    productdetail.setunit(jsonObject2.getString("unit"));

//
                                    Arraylist_productdetial.add(productdetail);

                                }

                                detaillistdata_adapter = new Detaillistdata_Adapter(Deatail_Activity.this, Arraylist_productdetial, refreshInterface);
                                list_productdeatil.setAdapter(detaillistdata_adapter);
                            }


                        }
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
                    progressDialog.dismiss();
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError) {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Deatail_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    progressDialog.dismiss();
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        progressDialog.dismiss();
                        Toast.makeText(Deatail_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Deatail_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }


    public void  GetProduct_bycategry()

    {

        String urljsonobj_group = Base_URL+"filter_multiple_category?longitude="+longitudes+"&latitude="+latitudes+"&category="+Catergorylist_choose+"&rang="+seekbarValue+"";
        final ProgressDialog progressDialog = new ProgressDialog(Deatail_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.getString("success").equalsIgnoreCase("true")) {


                        JSONArray jsonArray = response.getJSONArray("record");

                        Arraylist_productdetial = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++)


                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String fld_admin_id = jsonObject1.getString("fld_admin_id");

                            if (fld_admin_id.equals(fld_admin)) {
                                JSONArray products = jsonObject1.getJSONArray("products");
                                count = products.length();
                                if (count == 0) {
                                    text_productdetailnumber.setVisibility(View.VISIBLE);

                                }

                                for (int j = 0; j < products.length(); j++) {


                                    JSONObject jsonObject2 = products.getJSONObject(j);
                                    Product_detail_Model productdetail = new Product_detail_Model();
                                    productdetail.setproduct_id(jsonObject2.getString("product_id"));
                                    productdetail.setproduct_vernder_id(fld_admin);
                                    // productdetail.setproduct_name(jsonObject2.getString("category_name"));
                                    productdetail.setproduct_category_names(jsonObject2.getString("product_name"));
                                    productdetail.setproduct_image(jsonObject2.getString("banner_image"));
                                    productdetail.setproduct_Pricetot(jsonObject2.getString("price"));
                                    productdetail.setproduct_category_id(jsonObject2.getString("category"));
                                    // productdetail.setproduct_dis(jsonObject2.getString("customer_discount"));
                                    productdetail.setunit(jsonObject2.getString("unit"));

//
                                    Arraylist_productdetial.add(productdetail);

                                }

                                detaillistdata_adapter = new Detaillistdata_Adapter(Deatail_Activity.this, Arraylist_productdetial, refreshInterface);
                                list_productdeatil.setAdapter(detaillistdata_adapter);
                            }


                        }
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
                    progressDialog.dismiss();
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError) {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Deatail_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    progressDialog.dismiss();
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        progressDialog.dismiss();
                        Toast.makeText(Deatail_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Deatail_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }



    private void Get_categorylist() {
        final ProgressDialog pd = new ProgressDialog(Deatail_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"near_products";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray=jsonObject.getJSONArray("record");
                    Arraylist_categrorylist = new ArrayList<>();

                    for(int i=0;i<jsonArray.length();i++)


                    {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String fld_admin_id = jsonObject1.getString("fld_admin_id");

                        if(fld_admin_id.equals(fld_admin))
                        {

                            JSONArray category = jsonObject1.getJSONArray("category_list");


                            for (int j = 0; j < category.length(); j++)
                            {
                                JSONObject jsonObject2 = category.getJSONObject(j);
                                Catergoyicons_Model catericons = new Catergoyicons_Model();

                                catericons.setcategory_id(jsonObject2.getString("category_id"));
                                catericons.setcategroy_icons(jsonObject2.getString("category_image"));
                                catericons.setcategroy_name(jsonObject2.getString("category_names"));
                                Arraylist_categrorylist.add(catericons);

                            }

                            categoryicon_adapter = new Categoryicon_Adapter(Deatail_Activity.this, Arraylist_categrorylist);
                            list_categorylsit.setLayoutManager(new LinearLayoutManager(Deatail_Activity.this, LinearLayoutManager.HORIZONTAL, false));
                            list_categorylsit.setAdapter(categoryicon_adapter);



                        }



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
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Deatail_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Deatail_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Deatail_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Deatail_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();



                map.put("latitude", currentlatitude);
                map.put("longitude", currentlongtide);
                map.put("distance", "30");
                map.put("user_id", user_id);

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }

    private void Get_Rating() {
        final ProgressDialog pd = new ProgressDialog(Deatail_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"raiting_list";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject jsonObject1 = jsonObject.getJSONObject("record");

                    total_rating = jsonObject1.getString("total_rating");
                    ratingbar_rating.setRating(Float.parseFloat(total_rating));



                }


                catch (JSONException e) {


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
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Deatail_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Deatail_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Deatail_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Deatail_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Deatail_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("vendor_id", fld_admin);

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }





    public void custom_dialog_box(){
        dialog_main = new Dialog(Deatail_Activity.this);
        dialog_main.setContentView(R.layout.phonecustome);
        dialog_main.setCancelable(false);

        TextView lblUsername = dialog_main.findViewById(R.id.lblUsername);
        ImageView phon = dialog_main.findViewById(R.id.btn_phone);
        lblUsername.setText(verder_phone);

        lblUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", verder_phone, null));
                startActivity(intent);

            }
        });

        phon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog_main.show();

        dialog_main.setCanceledOnTouchOutside(true);

    }


    @Override
    public void Refresh() {
        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");

        GetProduct_api();
        Get_cartnumber();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        Get_cartnumber();



    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        Intent ints = new Intent(Deatail_Activity.this,Home_Activity.class);
//        ints.putExtra("Tabopen","Home");
//        startActivity(ints);

        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("Deatail_Activity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("Deatail_Activity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }
}


