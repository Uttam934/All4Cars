package com.allforcars.all4cars.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allforcars.all4cars.Activity.Branchlist_Activity;
import com.allforcars.all4cars.Activity.Deatail_Activity;
import com.allforcars.all4cars.Activity.Home_Activity;
import com.allforcars.all4cars.Model.Feaulserher_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


public class Fuelsearcher_Adapter extends RecyclerView.Adapter<Fuelsearcher_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<Feaulserher_Model> Feaulserher_Model;
    String url_image;
    Handler handler;
    String  brach;


    public Fuelsearcher_Adapter(Context context, ArrayList<Feaulserher_Model> Recyclemodel) {
        this.context = context;
        this.Feaulserher_Model = Recyclemodel;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fulfinderlistview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(Feaulserher_Model.get(position));


        try
        {
            holder.fueualstaion_name.setText(Feaulserher_Model.get(position).getfeaul_stationname());
            holder.fulstion_address.setText(Feaulserher_Model.get(position).getfeaul_stationaddress());
            holder.fulstion_km.setText(Feaulserher_Model.get(position).getfeaul_stationkm());
            String url_image = Utility.logo_url + Feaulserher_Model.get(position).getfeaul_logo();
//
            Glide.with(context).load(url_image).asBitmap()
                    .centerCrop()
                    .transform(new CircleTransform(context))
                    .override(100, 100)
                    .into(holder.Fuelstaion_logo);

            holder.braches.setText(Feaulserher_Model.get(position).getbranchnumber());
           // holder.Vender_km.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        final int pos= position;


       holder.Fuelstaion_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.viewiamge);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageView profilePicFullScreen = (ImageView) dialog.findViewById(R.id.profilePicFullScreen);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.close);


                String url_image = Utility.logo_url + Feaulserher_Model.get(pos).getfeaul_logo();
                Glide.with(context).load(url_image)
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

        holder.deatil_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        url_image = Feaulserher_Model.get(pos).getfeaul_logo();
                        Intent intent = new Intent(context, Deatail_Activity.class);
                        intent.putExtra("fld_admin_id", Feaulserher_Model.get(pos).getfld_admin_id());
                        intent.putExtra("companynm", Feaulserher_Model.get(pos).getfeaul_stationname());
                        intent.putExtra("km", Feaulserher_Model.get(pos).getfeaul_stationkm());
                        intent.putExtra("Address", Feaulserher_Model.get(pos).getfeaul_stationaddress());
                        intent.putExtra("Phone", Feaulserher_Model.get(pos).getlist_phone());
                        intent.putExtra("Email", Feaulserher_Model.get(pos).getfld_email());
                        intent.putExtra("Banner_image", Feaulserher_Model.get(pos).getlist_image());
                        intent.putExtra("category_id", Feaulserher_Model.get(pos).getlist_category_id());
                        intent.putExtra("category_id", Feaulserher_Model.get(pos).getlist_category_id());
                        intent.putExtra("url_link", Feaulserher_Model.get(pos).geturl_link());
                        intent.putExtra("latitude", Feaulserher_Model.get(pos).getlattitude());
                        intent.putExtra("logitude", Feaulserher_Model.get(pos).getlongitude());
                        intent.putExtra("brachlist", Feaulserher_Model.get(pos).getbranchnumber());
                        intent.putExtra("Map_show", "list");
                        intent.putExtra("Tabopens","Home");
                        intent.putExtra("logo", url_image);
                        context.startActivity(intent);



                    }
                },200);






            }
        });

        holder.braches_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                url_image = Feaulserher_Model.get(pos).getfeaul_logo();
               brach = Feaulserher_Model.get(pos).getbranchnumber();

               if  (brach.equals("0"))
                {


                }

                else {

                   Intent intent = new Intent(context, Branchlist_Activity.class);
                   intent.putExtra("fld_admin_id", Feaulserher_Model.get(pos).getfld_admin_id());
                   intent.putExtra("url_link", Feaulserher_Model.get(pos).geturl_link());
                   intent.putExtra("Banner_image", Feaulserher_Model.get(pos).getlist_image());
                   intent.putExtra("logo", url_image);
                   context.startActivity(intent);
               }





            }
        });




    }


    @Override
    public int getItemCount() {
        return Feaulserher_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView Fuelstaion_logo;
        public TextView fueualstaion_name;
        public TextView fulstion_address;
        public TextView fulstion_km,braches;
        LinearLayout Vender_km,braches_list,deatil_option;






        public ViewHolder(View itemView) { super(itemView);

            Fuelstaion_logo = itemView.findViewById(R.id.Fuelstaion_logo);
            fueualstaion_name = itemView.findViewById(R.id.fueualstaion_name);
            fulstion_address = itemView.findViewById(R.id.fulstion_address);
            fulstion_km = itemView.findViewById(R.id.fulstion_km);
            braches_list = itemView.findViewById(R.id.braches_list);
            Vender_km = itemView.findViewById(R.id.Vender_km);
            deatil_option = itemView.findViewById(R.id.deatil_option);
            braches = itemView.findViewById(R.id.braches);






        }
    }

}