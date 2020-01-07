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

import com.allforcars.all4cars.Model.Bonus_Model;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.allforcars.all4cars.classes.Utility.Base_URL;

public class Loyality_Bonus_Adapter extends RecyclerView.Adapter<Loyality_Bonus_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<Bonus_Model> Loyality_bonus_Model;
    String Status_Activate,user_id,loyalty_id;
    RefreshInterface refreshInterface;

    public Loyality_Bonus_Adapter(Context context, ArrayList<Bonus_Model> Recyclemodel, RefreshInterface refreshInterface ) {
        this.context = context;
        this.refreshInterface = refreshInterface;
        this.Loyality_bonus_Model = Recyclemodel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loyality_bonus_adapter, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
            }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
       holder.itemView.setTag(Loyality_bonus_Model.get(position));

            SharedPreferences sharedpreference = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
            user_id =sharedpreference.getString("user_id","");

//
        holder.text_offer_name.setText(Loyality_bonus_Model.get(position).getloyalty_name());
        holder.text_points.setText(Loyality_bonus_Model.get(position).getbonus_point());
        Status_Activate= Loyality_bonus_Model.get(position).getloyalty_status();


        if(Status_Activate.equals("1"))
        {
            holder.redeem_activiate.setBackgroundResource(R.drawable.btn_desing);

        }
        else {
            holder.redeem_activiate.setBackgroundResource(R.drawable.btn_enbale);
            holder.redeem_activiate.setFocusable(false);;

        }


         String url_image = Utility.loyalty_icon + Loyality_bonus_Model.get(position).getloyalty_image();
            Glide.with(context).load(url_image).asBitmap()
                    .centerCrop()
                    .transform(new CircleTransform(context))
                    .override(100, 100)
                    .into(holder.offer_image);



            final int pos= position;

            holder.redeem_activiate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Status_Activate= Loyality_bonus_Model.get(pos).getloyalty_status();
                    loyalty_id=Loyality_bonus_Model.get(pos).getloyalty_id();

                    if(Status_Activate.equals("1"))
                    {


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("Use Bonus");
                        alertDialog.setIcon(R.drawable.bonus);
                        alertDialog.setMessage("Do You Want to Use Bonus?");

                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int which)
                            {
                                Get_Loyalitybnounus();
                            }
                        });

                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        alertDialog.show();

                    }
                    else {

                    }






                }
            });


        }


    @Override
    public int getItemCount() {
        return Loyality_bonus_Model.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

            public ImageView offer_image;
            public TextView text_offer_name,text_points,text_redeem;
            LinearLayout redeem_activiate;






    public ViewHolder(View itemView) { super(itemView);

        offer_image = itemView.findViewById(R.id.offer_image);
        text_offer_name = itemView.findViewById(R.id.text_offer_name);
        text_points = itemView.findViewById(R.id.text_points);
        text_redeem = itemView.findViewById(R.id.text_redeem);
        redeem_activiate = itemView.findViewById(R.id.redeem_activiate);







    }
}
    public void  Get_Loyalitybnounus(){

        String urljsonobj_group = Base_URL+"redeem_loyalty?user_id="+user_id+"&loyalty_id="+loyalty_id;
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String message = response.getString("message");
                    if (response.getString("success").equalsIgnoreCase("true")) {

                        notifyDataSetChanged();
                        refreshInterface.Refresh();


                        Toast toast = Toast.makeText(context,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();



                    }
                    else {

                        Toast toast = Toast.makeText(context,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

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
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }


}
