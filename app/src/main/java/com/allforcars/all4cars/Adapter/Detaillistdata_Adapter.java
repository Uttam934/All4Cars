package com.allforcars.all4cars.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.allforcars.all4cars.Activity.Login_Activity;
import com.allforcars.all4cars.Activity.Mycart_Activity;
import com.allforcars.all4cars.Model.Product_detail_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.CircleTransform;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Detaillistdata_Adapter extends RecyclerView.Adapter<Detaillistdata_Adapter.ViewHolder> {



    private ArrayList<Product_detail_Model> product_detail_models;
    Context context;
    LayoutInflater inflater;
    String  Qty_calcte,Clicki_id="2",Product_nm,vendor_id="",product_id="",price=",",quantity="",user_id="",incartvalue="",price_amt="",category_id="";
    int Total_amt;
    RefreshInterface refreshInterface;
    Dialog dialog_main;
    EditText enter_amt,Qty_enter,enter_amtbyqty;
    EditText Amt_totl;
    TextView txt_take,Qty_tot,amt_byqty,quantity_txt;
    RelativeLayout usremailsfdd;
    double tot_amt=0.00;
    String Str_entamt="",Unit,qty_entr,Enter_Click;
    DecimalFormat df = new DecimalFormat("####0.00");



    public Detaillistdata_Adapter(Context context, ArrayList<Product_detail_Model> Recyclemodel,RefreshInterface refreshInterface ) {
        this.context = context;
        this.product_detail_models = Recyclemodel;
        this.refreshInterface = refreshInterface;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_productlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemView.setTag(product_detail_models.get(position));

        SharedPreferences sharedpreference = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");


        String name  = product_detail_models.get(position).getproduct_category_names();
        Product_nm = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();

        holder.txt_productcompnaynm.setText(Product_nm);

        holder.txt_productprice.setText(product_detail_models.get(position).getproduct_Pricetot()+"₦");
        holder.txt_unit.setText(product_detail_models.get(position).getunit());

        String url_image = Utility.product_image + product_detail_models.get(position).getproduct_image();

        Glide.with(context).load(url_image).asBitmap()
                .centerCrop()
                .transform(new CircleTransform(context))
                .override(100, 100)
                .into(holder.img_list_productlogo);



        final int pos= position;


        quantity ="1";

        price_amt= product_detail_models.get(position).getproduct_Pricetot();



        holder.btn_pluss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  count= Integer.parseInt(String.valueOf(holder.txt_quantynumber.getText()));
                price  = product_detail_models.get(pos).getproduct_Pricetot();
                Clicki_id="1";

                count++;
                holder.txt_quantynumber.setText(String.valueOf(count));
                holder.txt_productprice.setText("$"+Integer.parseInt(price)*count+""+"₦");
                quantity= (String.valueOf(count));
                price_amt=(Integer.parseInt(price)*count+"");


            }
        });

        holder.btn_minaus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  count= Integer.parseInt(String.valueOf(holder.txt_quantynumber.getText()));
                price  = product_detail_models.get(pos).getproduct_Pricetot();

                Clicki_id="1";

                if(count>1){

                    //2. enter code here

                    count--;


                    holder.txt_quantynumber.setText(String.valueOf(count));
                    holder.txt_productprice.setText("$"+Integer.parseInt(price)*count+""+"₦");
                    quantity= (String.valueOf(count));
                    price_amt=(Integer.parseInt(price)*count+"");

                }



            }
        });





        holder.btn_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Clicki_id.equals("1"))
                {
                    vendor_id  = product_detail_models.get(pos).getproduct_vernder_id();
                    product_id  = product_detail_models.get(pos).getproduct_id();
                    price  = product_detail_models.get(pos).getproduct_Pricetot();
                    //  price_amt  = product_detail_models.get(pos).getproduct_Pricetot();
                    category_id = product_detail_models.get(pos).getproduct_category_id();

                    Addto_cartlist();




                }

                else

                {

                    vendor_id  = product_detail_models.get(pos).getproduct_vernder_id();
                    product_id  = product_detail_models.get(pos).getproduct_id();
                    // price  = product_detail_models.get(pos).getproduct_Pricetot();
                    price_amt  = product_detail_models.get(pos).getproduct_Pricetot();
                    category_id = product_detail_models.get(pos).getproduct_category_id();


                    Addto_cartlist();



                }




            }
        });

        holder.btn_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Clicki_id.equals("1"))
                {
                    vendor_id  = product_detail_models.get(pos).getproduct_vernder_id();
                    product_id  = product_detail_models.get(pos).getproduct_id();
                    price  = product_detail_models.get(pos).getproduct_Pricetot();
                    category_id = product_detail_models.get(pos).getproduct_category_id();

                    buynow_cartlist();




                }

                else

                {

                    vendor_id  = product_detail_models.get(pos).getproduct_vernder_id();
                    product_id  = product_detail_models.get(pos).getproduct_id();
                    // price  = product_detail_models.get(pos).getproduct_Pricetot();
                    price_amt  = product_detail_models.get(pos).getproduct_Pricetot();
                    category_id = product_detail_models.get(pos).getproduct_category_id();


                    buynow_cartlist();




                }




            }
        });

        holder.btn_calcuate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vendor_id  = product_detail_models.get(pos).getproduct_vernder_id();
                product_id  = product_detail_models.get(pos).getproduct_id();
                price  = product_detail_models.get(pos).getproduct_Pricetot();
                category_id = product_detail_models.get(pos).getproduct_category_id();
                Unit = product_detail_models.get(pos).getunit();

                dialog_main = new Dialog(context);
                dialog_main.setContentView(R.layout.amountcarttouser);
                dialog_main.setCancelable(false);


                enter_amt = dialog_main.findViewById(R.id.enter_amt);
                Qty_tot = dialog_main.findViewById(R.id.Qty_tot);
                txt_take = dialog_main.findViewById(R.id.txt_take);
                amt_byqty = dialog_main.findViewById(R.id.amt_byqty);
                Qty_enter = dialog_main.findViewById(R.id.Qty_enter);
                quantity_txt = dialog_main.findViewById(R.id.quantity_txt);



                enter_amt.setText(price);
                quantity_txt.setText(Unit);
                Qty_tot.setText("1");
                Qty_calcte =Qty_tot.getText().toString();
                Str_entamt =product_detail_models.get(pos).getproduct_Pricetot();





                Qty_tot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Qty_tot.setVisibility(View.GONE);
                        enter_amt.setVisibility(View.GONE);
                        Qty_enter.setVisibility(View.VISIBLE);
                        amt_byqty.setVisibility(View.VISIBLE);

                        Qty_enter.setText("");
                        amt_byqty.setText("");
                    }
                });

                amt_byqty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Qty_tot.setVisibility(View.VISIBLE);
                        enter_amt.setVisibility(View.VISIBLE);
                        enter_amt.setText("");
                        Qty_tot.setText("");
                        Qty_enter.setVisibility(View.GONE);
                        amt_byqty.setVisibility(View.GONE);
                    }
                });



                Qty_enter.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {

                        try
                        {

                            qty_entr =Qty_enter.getText().toString();
                            tot_amt = Double.parseDouble(qty_entr)* Double.parseDouble(price);
                            amt_byqty.setText(df.format(tot_amt));
                            Qty_calcte = qty_entr;
                            Str_entamt = String.valueOf(tot_amt);

//
//                            Qty_enter.setText("");
//                            amt_byqty.setText("");




                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }

                    public void afterTextChanged(Editable s)
                    {



                    };
                });



                enter_amt.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {

                        try
                        {

                            double qty=1;
                            if(qty==1)
                            {

                                Str_entamt =enter_amt.getText().toString();
                                qty = Double.parseDouble(Str_entamt)/Double.parseDouble(price);
                                Qty_tot.setText(df.format(qty));
                                Qty_calcte = df.format(qty);

                                //   Qty_tot.setVisibility(View.GONE);


                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }

                    public void afterTextChanged(Editable s)
                    {


                    };
                });







                txt_take.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(Str_entamt.equals(""))
                        {

                            Toast toast = Toast.makeText(context,"Please Enter Amount", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else
                        {
                            calculate_cartlist();


                        }





                    }
                });


                dialog_main.show();

                dialog_main.setCanceledOnTouchOutside(true);



            }
        });







    }


    @Override
    public int getItemCount() {
        return product_detail_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img_list_productlogo,btn_minaus,btn_pluss;
        public TextView txt_productcompnaynm;
        public TextView txt_unit;
        public TextView txt_productprice,btn_buynow,btn_addtocart,txt_quantynumber,btn_calcuate;





        public ViewHolder(View itemView) { super(itemView);

            img_list_productlogo = itemView.findViewById(R.id.img_list_productlogo);
            txt_productcompnaynm = itemView.findViewById(R.id.txt_productcompnaynm);
            txt_productprice = itemView.findViewById(R.id.txt_productprice);
            btn_buynow = itemView.findViewById(R.id.btn_buynow);
            btn_addtocart = itemView.findViewById(R.id.btn_addtocart);
            btn_minaus = itemView.findViewById(R.id.btn_minaus);
            btn_pluss = itemView.findViewById(R.id.btn_pluss);
            txt_quantynumber = itemView.findViewById(R.id.txt_quantynumber);
            btn_calcuate = itemView.findViewById(R.id.btn_calcuate);
            txt_unit = itemView.findViewById(R.id.txt_unit);




        }
    }





    private void Addto_cartlist() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"add_to_cart";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {
                        refreshInterface.Refresh();
                        Toast toast = Toast.makeText(context,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pd.dismiss();




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
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                   Toast.makeText(context, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(context, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("vendor_id", vendor_id);
                map.put("product_id",product_id );
                map.put("price",price_amt );
                map.put("user_id", user_id);
                map.put("quantity",quantity );
                map.put("category_id",category_id );

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    private void buynow_cartlist() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"add_to_cart";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {

                        Intent ins= new Intent(context, Mycart_Activity.class);
                        ins.putExtra("Mycart","Buy_now");
                        context.startActivity(ins);





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
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(context, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("vendor_id", vendor_id);
                map.put("product_id",product_id );
                map.put("price",price_amt );
                map.put("user_id", user_id);
                map.put("quantity",quantity );
                map.put("category_id",category_id );

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void calculate_cartlist() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"add_to_cart";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    dialog_main.dismiss();

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {

                        Intent ins= new Intent(context, Mycart_Activity.class);
                        ins.putExtra("Mycart","Buy_now");
                        context.startActivity(ins);





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
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(context, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("vendor_id", vendor_id);
                map.put("product_id",product_id );
                map.put("price",Str_entamt );
                map.put("user_id", user_id);
                map.put("quantity",Qty_calcte );
                map.put("category_id",category_id );

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}