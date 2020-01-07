package com.allforcars.all4cars.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Adapter.Addtocart_Adapter;
import com.allforcars.all4cars.Adapter.Detaillistdata_Adapter;
import com.allforcars.all4cars.Model.Addtocart_Model;
import com.allforcars.all4cars.Model.Product_detail_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.Utility;
import com.allforcars.all4cars.interfaces.RefreshInterface;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mycart_Activity extends AppCompatActivity implements RefreshInterface {

    Addtocart_Adapter addtocart_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Addtocart_Model> Arraylist_addtocart ;
    ImageView img_back_addcart;
    private RefreshInterface refreshInterface;
    RadioButton check_radio_button;
    RecyclerView Recyclerview_mycart;
    String user_id,stutotal,totalamt,DisPercentage,vendor_id,Card_id,product_id,category_id,Mycart;
    LinearLayout record_notfound,txt_subtotalarea,recyclerviewarea;
    TextView txt_subtotal,txt_totalpayble,btn_checkout,txt_totaldis,mycart,btn_payment;
    String quantity,All4cars_DisPercentage,price_Amt,total_commission,TotalDiscount_Amt,Payment_Type,Vender_id,Total_Amt,product_amt;
    int count;
    Double discount,Paybleamt,All4carsDis,Product_Amount;
    int toatalsum;
    private List<String> srtingCartlist = new ArrayList<>();
    private List<String> srtingCategroy_id = new ArrayList<>();
    private List<String> srtingProduct_id = new ArrayList<>();
    private List<String> srtingquantity = new ArrayList<>();
    private List<String> srtingprodamt = new ArrayList<>();
    private List<String> srtingdiscount = new ArrayList<>();
    private List<String> srtingAll4carsdiscount = new ArrayList<>();
    private List<String> srtingTotalDiscount = new ArrayList<>();
    private List<String> srtingTotalCommision = new ArrayList<>();


    String strtotalcommision = "";
    String strAll14carsdiscount = "";
    String strtotaldiscount ="";
    String strdiscount = "";
    String strprodamt = "";
    String strquantity = "";
    String strcartlist = "";
    String strCategroyid = "";
    String strProduct_id = "";
    Dialog dialog_main;
    int check_radio_btn;

    RadioButton  rb_cash,rb_onlinepayment,rb_creditdebit,rb_Paypal;
    RadioGroup radio_gr_lan_login;
    DecimalFormat df = new DecimalFormat("####0.00");
    SwipeRefreshLayout swipe_mycart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);
        refreshInterface= this;
        img_back_addcart = findViewById(R.id.img_back_addcart);
        Recyclerview_mycart = findViewById(R.id.Recyclerview_mycart);
        txt_subtotal = findViewById(R.id.txt_subtotal);
        txt_totalpayble = findViewById(R.id.txt_totalpayble);
        record_notfound = findViewById(R.id.record_notfound);
        recyclerviewarea = findViewById(R.id.recyclerviewarea);
        txt_subtotalarea = findViewById(R.id.txt_subtotalarea);
        btn_checkout = findViewById(R.id.btn_checkout);
        txt_totaldis = findViewById(R.id.txt_totaldis);
     //   swipe_mycart = findViewById(R.id.swipe_mycart);
        mycart = findViewById(R.id.mycart);
        btn_payment = findViewById(R.id.btn_payment);


        Recyclerview_mycart.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Mycart_Activity.this);
        Recyclerview_mycart.setLayoutManager(layoutManager);
        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        Mycart=getIntent().getStringExtra("Mycart");

        if(Mycart.equals("AddtoCart"))
        {


        }
        else {

            mycart.setText("Check Out");

        }

        Get_cartlist();

        img_back_addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });



        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    custom_dialog_box();

                } else {
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();

                }



            }
        });


        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Mycart_Activity.this);
                alertDialog.setTitle("Order Complete");
                alertDialog.setIcon(R.drawable.complete_icon);
                alertDialog.setMessage("Do You Want to Pay Now?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int which)
                    {
                        Get_requestorder();
                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });




    }

    public void custom_dialog_box(){
        dialog_main = new Dialog(Mycart_Activity.this);
        dialog_main.setContentView(R.layout.attendance_custom_layout);
        dialog_main.setCancelable(false);

        final TextView txt_take_attendance = dialog_main.findViewById(R.id.txt_take_attendance);
        rb_cash = dialog_main.findViewById(R.id.rb_cash);
        rb_onlinepayment = dialog_main.findViewById(R.id.rb_onlinepayment);
        rb_creditdebit = dialog_main.findViewById(R.id.rb_creditdebit);
        rb_Paypal = dialog_main.findViewById(R.id.rb_Paypal);
        radio_gr_lan_login = dialog_main.findViewById(R.id.radio_gr_lan_login);


        rb_cash.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Payment_Type ="CASH";

           }
       });

        rb_Paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Payment_Type ="PAYPAL";

            }
        });

        rb_creditdebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payment_Type ="Credit/Debit Card";

            }
        });

        rb_onlinepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payment_Type ="Online Transfer";

            }
        });




        txt_take_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (radio_gr_lan_login.getCheckedRadioButtonId() == -1)
                {
                    Toast toast = Toast.makeText(Mycart_Activity.this,"Select Payment Options", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                {
                    if(Payment_Type.equals("CASH"))
                    {

                        Get_cashlist();
                        btn_payment.setBackgroundResource(R.drawable.btn_desing);
                        btn_payment.setEnabled(true);

                    }
                    else if(Payment_Type.equals("PAYPAL"))
                    {

                        Get_requestorder_Paypal();



                    }
                    else if(Payment_Type.equals("Online Transfer"))
                    {
                        Get_requestorder_onlientransper();

                    }

                    dialog_main.dismiss();
                }










            }
        });




        dialog_main.show();

        dialog_main.setCanceledOnTouchOutside(true);

    }



    private void Get_cartlist() {
        String urll= Utility.Base_URL+"cart_list";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Arraylist_addtocart = new ArrayList<>();
                    srtingCartlist.clear();
                    srtingProduct_id.clear();
                    srtingCategroy_id.clear();
                    srtingquantity.clear();
                    srtingprodamt.clear();
                    srtingAll4carsdiscount.clear();
                    srtingTotalCommision.clear();
                    srtingTotalDiscount.clear();
                    srtingdiscount.clear();

                    Double sum=0.00,dis=0.00,comision=0.00;
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("record");


                        count = jsonArray.length();
                        if(count==0)
                        {
                            record_notfound.setVisibility(View.VISIBLE);
                            txt_subtotalarea.setVisibility(View.GONE);
                            recyclerviewarea.setVisibility(View.GONE);

                        }

                        for (int i = 0; i < jsonArray.length(); i++)


                        {
                            Addtocart_Model addtcart = new Addtocart_Model();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            addtcart.setcart_pirce(jsonObject1.getString("price"));
                            addtcart.setproduct_id(jsonObject1.getString("product_id"));
                            addtcart.setcart_quqantiy(jsonObject1.getString("quantity"));
                            addtcart.setcart_id(jsonObject1.getString("id"));
                            addtcart.setunit(jsonObject1.getString("unit"));
                            Card_id = jsonObject1.getString("id");
                            product_amt = jsonObject1.getString("product_price");
                            product_id = jsonObject1.getString("product_id");
                            category_id = jsonObject1.getString("category_id");
                            quantity =jsonObject1.getString("quantity");

                            srtingCartlist.add(Card_id);
                            strcartlist = TextUtils.join(",", srtingCartlist);

                            srtingProduct_id.add(product_id);
                            strProduct_id = TextUtils.join(",", srtingProduct_id);

                            srtingCategroy_id.add(category_id);
                            strCategroyid = TextUtils.join(",", srtingCategroy_id);

                            srtingquantity.add(quantity);
                            strquantity = TextUtils.join(",", srtingquantity);

                            Product_Amount = Double.parseDouble(quantity)* Double.parseDouble(product_amt);

                            srtingprodamt.add(String.valueOf(Product_Amount));
                            strprodamt = TextUtils.join(",", srtingprodamt);


                            addtcart.setcart_name(jsonObject1.getString("product_name"));
                            price_Amt = jsonObject1.getString("price");

                            DisPercentage= jsonObject1.getString("userdiscount");
                            All4cars_DisPercentage= jsonObject1.getString("all4carscomission");



                            discount= Double.parseDouble(((Double.parseDouble(price_Amt) * Integer.parseInt(DisPercentage)) / 100) + "");
                            All4carsDis= Double.parseDouble(((Double.parseDouble(price_Amt) * Integer.parseInt(All4cars_DisPercentage)) / 100) + "");



                            srtingdiscount.add(String.valueOf(discount));
                            strdiscount = TextUtils.join(",", srtingdiscount);

                            srtingAll4carsdiscount.add(String.valueOf(All4carsDis));
                            strAll14carsdiscount = TextUtils.join(",", srtingAll4carsdiscount);

                            comision=comision+All4carsDis;

                            strtotalcommision=String.valueOf(comision);
//                            srtingTotalCommision.add(String.valueOf(comision));
//                            strtotalcommision = TextUtils.join(",", srtingTotalCommision);




                            sum=sum+Double.parseDouble(price_Amt);

                            dis=dis+discount;
                            TotalDiscount_Amt=String.valueOf(dis);
                            Total_Amt=String.valueOf(sum);


                            Paybleamt = sum-dis;
                            txt_totalpayble.setText(df.format(Paybleamt)+"₦");
                            txt_subtotal.setText(df.format(Double.parseDouble(Total_Amt))+"₦");

                            stutotal = "$"+String.valueOf(sum);
                            totalamt = "$"+String.valueOf(Paybleamt);
                            txt_totaldis.setText(df.format(dis)+"₦");

                            srtingTotalDiscount.add(String.valueOf(dis));
                            strtotaldiscount = TextUtils.join(",", srtingTotalDiscount);



                            addtcart.setproduct_disamt(String.valueOf(dis));
                            addtcart.settotalpayable_amt(String.valueOf(Paybleamt));


                            addtcart.setcart_logo(jsonObject1.getString("product_image"));
                            vendor_id = jsonObject1.getString("vendor_id");
                            addtcart.setproduct_price(jsonObject1.getString("product_price"));

                            Arraylist_addtocart.add(addtcart);
                            addtocart_adapter = new Addtocart_Adapter(Mycart_Activity.this, Arraylist_addtocart,refreshInterface);
                            Recyclerview_mycart.setAdapter(addtocart_adapter);

                        }




                    }

                    else{
                        //Toast.makeText(getActivity(), " "+discount, Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Mycart_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Mycart_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Mycart_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Mycart_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
              //  map.put("vender_id",Vender_id );

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }
    private void Get_cashlist() {
        String urll= Utility.Base_URL+"cart_list";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Arraylist_addtocart = new ArrayList<>();
                    srtingCartlist.clear();
                    srtingProduct_id.clear();
                    srtingCategroy_id.clear();
                    srtingquantity.clear();
                    srtingprodamt.clear();
                    srtingAll4carsdiscount.clear();
                    srtingTotalCommision.clear();
                    srtingTotalDiscount.clear();
                    srtingdiscount.clear();

                    Double sum=0.00,dis=0.00,comision=0.00;
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("record");


                        count = jsonArray.length();
                        if(count==0)
                        {
                            record_notfound.setVisibility(View.VISIBLE);
                            txt_subtotalarea.setVisibility(View.GONE);
                            recyclerviewarea.setVisibility(View.GONE);

                        }

                        for (int i = 0; i < jsonArray.length(); i++)


                        {
                            Addtocart_Model addtcart = new Addtocart_Model();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            addtcart.setcart_pirce(jsonObject1.getString("price"));
                            addtcart.setproduct_id(jsonObject1.getString("product_id"));
                            addtcart.setcart_quqantiy(jsonObject1.getString("quantity"));
                            addtcart.setcart_id(jsonObject1.getString("id"));
                            Card_id = jsonObject1.getString("id");
                            product_amt = jsonObject1.getString("product_price");
                            product_id = jsonObject1.getString("product_id");
                            category_id = jsonObject1.getString("category_id");
                            quantity =jsonObject1.getString("quantity");

                            srtingCartlist.add(Card_id);
                            strcartlist = TextUtils.join(",", srtingCartlist);

                            srtingProduct_id.add(product_id);
                            strProduct_id = TextUtils.join(",", srtingProduct_id);

                            srtingCategroy_id.add(category_id);
                            strCategroyid = TextUtils.join(",", srtingCategroy_id);

                            srtingquantity.add(quantity);
                            strquantity = TextUtils.join(",", srtingquantity);

                            Product_Amount = Double.parseDouble(quantity)* Double.parseDouble(product_amt);

                            srtingprodamt.add(String.valueOf(Product_Amount));
                            strprodamt = TextUtils.join(",", srtingprodamt);


                            addtcart.setcart_name(jsonObject1.getString("product_name"));
                            price_Amt = jsonObject1.getString("price");

                            DisPercentage= jsonObject1.getString("userdiscount");
                            All4cars_DisPercentage= jsonObject1.getString("all4carscomission");


                            //   Total_Dis = ((Integer.parseInt(price_Amt) * Integer.parseInt(DisPercentage)) / 100) + "";

                            discount= 0.00;
                            All4carsDis= Double.parseDouble(((Double.parseDouble(price_Amt) * Integer.parseInt(All4cars_DisPercentage)) / 100) + "");



                            srtingdiscount.add(String.valueOf(discount));
                            strdiscount = TextUtils.join(",", srtingdiscount);

                            srtingAll4carsdiscount.add(String.valueOf(All4carsDis));
                            strAll14carsdiscount = TextUtils.join(",", srtingAll4carsdiscount);

                            comision=comision+All4carsDis;



                            strtotalcommision=String.valueOf(comision);


                            sum=sum+Double.parseDouble(price_Amt);

                            dis=dis+discount;
                            TotalDiscount_Amt=String.valueOf(dis);
                            Total_Amt=String.valueOf(sum);




                            Paybleamt = sum-dis;
                            txt_totalpayble.setText(df.format(Paybleamt)+"₦");
                            txt_subtotal.setText(df.format(Double.parseDouble(Total_Amt))+"₦");

                            stutotal = "$"+String.valueOf(sum);
                            totalamt = "$"+String.valueOf(Paybleamt);
                            txt_totaldis.setText(df.format(dis)+"₦");

                            srtingTotalDiscount.add(String.valueOf(dis));
                            strtotaldiscount = TextUtils.join(",", srtingTotalDiscount);



                            addtcart.setproduct_disamt(String.valueOf(dis));
                            addtcart.settotalpayable_amt(String.valueOf(Paybleamt));


                            addtcart.setcart_logo(jsonObject1.getString("product_image"));
                            vendor_id = jsonObject1.getString("vendor_id");
                            addtcart.setproduct_price(jsonObject1.getString("product_price"));

                            Arraylist_addtocart.add(addtcart);
                            addtocart_adapter = new Addtocart_Adapter(Mycart_Activity.this, Arraylist_addtocart,refreshInterface);
                            Recyclerview_mycart.setAdapter(addtocart_adapter);

                        }




                    }

                    else{
                        //Toast.makeText(getActivity(), " "+discount, Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Mycart_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Mycart_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Mycart_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Mycart_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                //  map.put("vender_id",Vender_id );

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }

    private void Get_requestorder() {
        final ProgressDialog pd = new ProgressDialog(Mycart_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"request_order";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    dialog_main.dismiss();

                        Toast toast = Toast.makeText(Mycart_Activity.this,"Order Successful", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        Intent ints = new Intent(Mycart_Activity.this,Home_Activity.class);
                        startActivity(ints);
                        finish();

                        pd.dismiss();




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
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                   Toast.makeText(Mycart_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Mycart_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Mycart_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Mycart_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("category_id",strCategroyid );
                map.put("total_amount",Total_Amt );
                map.put("discount_amount",strdiscount);
                map.put("total_discount",TotalDiscount_Amt);
                map.put("commission",strAll14carsdiscount );
                map.put("total_commission",strtotalcommision );
                map.put("payment_amount",String.valueOf(Paybleamt) );
                map.put("cart_listid",strcartlist );
                map.put("vender_id",vendor_id );
                map.put("user_id",user_id );
                map.put("product_id",strProduct_id );
                map.put("payment_type","cash" );
                map.put("quantity",strquantity );
                map.put("product_amount",strprodamt );

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    private void Get_requestorder_Paypal() {
        final ProgressDialog pd = new ProgressDialog(Mycart_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"sentrequest_order_with_Paypal";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    dialog_main.dismiss();

                    Intent payinfo= new Intent(Mycart_Activity.this,Payment_Activity.class);
                    payinfo.putExtra("total_amount",String.valueOf(Paybleamt));
                    payinfo.putExtra("discount_amount",TotalDiscount_Amt);
                    payinfo.putExtra("Vender_id",vendor_id);
                    startActivity(payinfo);

                    pd.dismiss();




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
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Mycart_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Mycart_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Mycart_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Mycart_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("category_id",strCategroyid );
                map.put("total_amount",Total_Amt );
                map.put("discount_amount",strdiscount);
                map.put("total_discount",TotalDiscount_Amt);
                map.put("commission",strAll14carsdiscount );
                map.put("total_commission",strtotalcommision );
                map.put("payment_amount",String.valueOf(Paybleamt) );
                map.put("cart_listid",strcartlist );
                map.put("vender_id",vendor_id );
                map.put("user_id",user_id );
                map.put("product_id",strProduct_id );
                map.put("payment_type","Paypal" );
                map.put("quantity",strquantity );
                map.put("product_amount",strprodamt );

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    private void Get_requestorder_onlientransper() {
        final ProgressDialog pd = new ProgressDialog(Mycart_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"sentrequest_order_with_Paypal";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    dialog_main.dismiss();

                    Intent payinfo= new Intent(Mycart_Activity.this,Payment_Activity.class);
                    payinfo.putExtra("total_amount",String.valueOf(Paybleamt));
                    payinfo.putExtra("discount_amount",TotalDiscount_Amt);
                    payinfo.putExtra("Vender_id",vendor_id);
                    startActivity(payinfo);


                    pd.dismiss();




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
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Mycart_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Mycart_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Mycart_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Mycart_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Mycart_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("category_id",strCategroyid );
                map.put("total_amount",Total_Amt );
                map.put("discount_amount",strdiscount);
                map.put("total_discount",TotalDiscount_Amt);
                map.put("commission",strAll14carsdiscount );
                map.put("total_commission",strtotalcommision );
                map.put("payment_amount",String.valueOf(Paybleamt) );
                map.put("cart_listid",strcartlist );
                map.put("vender_id",vendor_id );
                map.put("user_id",user_id );
                map.put("product_id",strProduct_id );
                map.put("payment_type","Online Transfer" );
                map.put("quantity",strquantity );
                map.put("product_amount",strprodamt );

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public void Refresh() {
        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");

        Get_cartlist();
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }


}
