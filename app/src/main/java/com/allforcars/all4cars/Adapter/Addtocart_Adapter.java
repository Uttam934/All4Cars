package com.allforcars.all4cars.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.Mycart_Activity;
import com.allforcars.all4cars.Model.Addtocart_Model;
import com.allforcars.all4cars.Model.Brand_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.CircleTransform;
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
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Addtocart_Adapter extends RecyclerView.Adapter<Addtocart_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<Addtocart_Model> Addtocart_Model;
    String  Check_qty,Product_nm="",product_id="",price=",",quantity="",user_id="",price_amt="";
     String cartqty,cartprice;
     RefreshInterface refreshInterface;
     int Poss;
    public Addtocart_Adapter(Context context, ArrayList<Addtocart_Model> Recyclemodel,RefreshInterface refreshInterface) {
        this.context = context;
        this.refreshInterface = refreshInterface;
        this.Addtocart_Model = Recyclemodel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_addcart, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemView.setTag(Addtocart_Model.get(position));

        SharedPreferences sharedpreference = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");

        String name  = Addtocart_Model.get(position).getcart_name();
        Product_nm = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        Check_qty = Addtocart_Model.get(position).getcart_quqantiy();

        if(Check_qty.contains(".")==true)
        {
            holder.addcart_incredcrise.setVisibility(View.GONE);
        }
        else
        {
            holder.addcart_incredcrise.setVisibility(View.VISIBLE);

        }


        holder.txt_productcompnaynm.setText(Product_nm);
        holder.txt_productprice.setText(Addtocart_Model.get(position).getcart_pirce()+"₦");
        holder.txt_quantitiy.setText(Addtocart_Model.get(position).getcart_quqantiy());
        holder.txt_qtunit.setText(Addtocart_Model.get(position).getunit());

        String url_image = Utility.product_image + Addtocart_Model.get(position).getcart_logo();

        Glide.with(context).load(url_image).asBitmap()
                .centerCrop()
                .transform(new CircleTransform(context))
                .override(100, 100)
                .into(holder.img_list_addtocart);



        final int pos= position;


        holder.addcard_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  count= Integer.parseInt(String.valueOf(holder.txt_quantitiy.getText()));
                price  = Addtocart_Model.get(pos).getproduct_price();
                product_id  = Addtocart_Model.get(pos).getcart_id();
              //  count=(Integer.parseInt(qty));


                count++;
                holder.txt_quantitiy.setText(String.valueOf(count));
                holder.txt_productprice.setText(Integer.parseInt(price)*count+""+"₦");
                quantity= (String.valueOf(count));
                price_amt=(Integer.parseInt(price)*count+"");
             // Toast.makeText(context, " "+quantity, Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, " "+price_amt, Toast.LENGTH_SHORT).show();

                EditQtyamount();



            }
        });

        holder.addcart_minaus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  count= Integer.parseInt(String.valueOf(holder.txt_quantitiy.getText()));
                price  = Addtocart_Model.get(pos).getproduct_price();
                product_id  = Addtocart_Model.get(pos).getcart_id();


                  if(count>1){

                    //2. enter code here

                    count--;

                    holder.txt_quantitiy.setText(String.valueOf(count));
                    holder.txt_productprice.setText(Integer.parseInt(price)*count+""+"₦");
                    quantity= (String.valueOf(count));
                    price_amt=(Integer.parseInt(price)*count+"");
                  //  Toast.makeText(context, " "+quantity, Toast.LENGTH_SHORT).show();
                 //   Toast.makeText(context, " "+price_amt, Toast.LENGTH_SHORT).show();



                }

                EditQtyamount();



            }
        });







        holder.btn_removecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_id  = Addtocart_Model.get(pos).getcart_id();


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Do You Want to remove item?");
                alertDialog.setMessage("Confirm");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Addto_removelist();
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





    public void removeObject (Addtocart_Model position)
    {
        this.Addtocart_Model.remove(position);
    }

    public Addtocart_Model getItem(int position) {
        if (Addtocart_Model != null) {
            return Addtocart_Model.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return Addtocart_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img_list_addtocart,addcart_minaus,addcard_plus;
        public TextView txt_productcompnaynm;
        public TextView txt_productprice;
        public TextView txt_quantitiy,btn_removecart,txt_qtunit;
        LinearLayout addcart_incredcrise;








        public ViewHolder(View itemView) { super(itemView);

            img_list_addtocart = itemView.findViewById(R.id.img_list_addtocart);
            txt_productcompnaynm = itemView.findViewById(R.id.txt_productcompnaynm);
            txt_productprice = itemView.findViewById(R.id.txt_productprice);
            txt_quantitiy = itemView.findViewById(R.id.txt_quantitiy);
            btn_removecart = itemView.findViewById(R.id.btn_removecart);
            addcart_minaus = itemView.findViewById(R.id.addcart_minaus);
            addcard_plus = itemView.findViewById(R.id.addcard_plus);
            txt_qtunit = itemView.findViewById(R.id.txt_qtunit);
            addcart_incredcrise = itemView.findViewById(R.id.addcart_incredcrise);







        }
    }


    private void Addto_removelist() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll= Utility.Base_URL+"delete_cart_list";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");

                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {


                        removeObject(getItem(Poss));
                        notifyDataSetChanged();

                        refreshInterface.Refresh();

                        Toast toast = Toast.makeText(context,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        pd.dismiss();


                    }

                    else{
                        Toast toast = Toast.makeText(context,message, Toast.LENGTH_LONG);
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
                map.put("cart_id",product_id );

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }

    private void EditQtyamount() {


        String urll= Utility.Base_URL+"update_cart_list";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {


                        notifyDataSetChanged();
                        refreshInterface.Refresh();




                    }

                    else{


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
                map.put("cart_id",product_id );
                map.put("price",price_amt );
                map.put("quantity",quantity );

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }

}