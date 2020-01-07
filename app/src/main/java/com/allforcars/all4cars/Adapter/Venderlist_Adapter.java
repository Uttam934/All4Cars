package com.allforcars.all4cars.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allforcars.all4cars.Activity.Branchlist_Activity;
import com.allforcars.all4cars.Activity.Deatail_Activity;
import com.allforcars.all4cars.Model.Venderlist_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;

import java.util.List;

public class Venderlist_Adapter extends RecyclerView.Adapter<Venderlist_Adapter.MessageViewHolder> {


    private List<Venderlist_Model> Venderlist_Model;
    Context context;
    View view = null;
    Handler handler;
    String url_image,brach;

    public Venderlist_Adapter(Context context, List<Venderlist_Model> Venderlist_Model) {
        this.context = context;
        this.Venderlist_Model = Venderlist_Model;
        // this.refreshInterface = refreshInterface;

    }

    @NonNull
    @Override
    public Venderlist_Adapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        try {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fulfinderlistview, parent, false);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Venderlist_Adapter.MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Venderlist_Adapter.MessageViewHolder holder, final int position) {

        try {

            holder.fueualstaion_name.setText(Venderlist_Model.get(position).getcompany_name());
            holder.fulstion_address.setText(Venderlist_Model.get(position).getaddress());
            holder.fulstion_km.setText(Venderlist_Model.get(position).getdistance()+"Km");
            String url_images = Utility.logo_url + Venderlist_Model.get(position).getlogo();
//
            Glide.with(context).load(url_images).asBitmap()
                    .centerCrop()
                    .transform(new CircleTransform(context))
                    .override(100, 100)
                    .into(holder.Fuelstaion_logo);

            brach = Venderlist_Model.get(position).getbranch_number();
            if(Integer.parseInt(brach)>=3)
            {
                holder.txt_braches.setText("Branches");
            }
            else {
                holder.txt_braches.setText("Branch");

            }

            holder.braches.setText(Venderlist_Model.get(position).getbranch_number());

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


                    String url_image = Utility.logo_url + Venderlist_Model.get(pos).getlogo();
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

                            url_image = Venderlist_Model.get(pos).getsmall_logo();
                            Intent intent = new Intent(context, Deatail_Activity.class);
                            intent.putExtra("fld_admin_id", Venderlist_Model.get(pos).getfld_admin_id());
                            intent.putExtra("companynm", Venderlist_Model.get(pos).getcompany_name());
                            intent.putExtra("km", Venderlist_Model.get(pos).getdistance()+"Km");
                            intent.putExtra("Address", Venderlist_Model.get(pos).getaddress());
                            intent.putExtra("Phone", Venderlist_Model.get(pos).getphone());
                            intent.putExtra("Email", Venderlist_Model.get(pos).getfld_email());
                            intent.putExtra("Banner_image", Venderlist_Model.get(pos).getimage());
                            intent.putExtra("category_id", Venderlist_Model.get(pos).getcategory());
                            intent.putExtra("url_link", Venderlist_Model.get(pos).getwebsite_url());
                            intent.putExtra("latitude", Venderlist_Model.get(pos).getlatitude());
                            intent.putExtra("logitude", Venderlist_Model.get(pos).getlongitude());
                            intent.putExtra("brachlist", Venderlist_Model.get(pos).getbranch_number());
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



                    url_image = Venderlist_Model.get(pos).getlogo();
                    brach = Venderlist_Model.get(pos).getbranch_number();

                    if  (brach.equals("0"))
                    {


                    }

                    else {

                        Intent intent = new Intent(context, Branchlist_Activity.class);
                        intent.putExtra("fld_admin_id", Venderlist_Model.get(pos).getfld_admin_id());
                        intent.putExtra("url_link", Venderlist_Model.get(pos).getwebsite_url());
                        intent.putExtra("Banner_image", Venderlist_Model.get(pos).getimage());
                        intent.putExtra("logo", url_image);
                        context.startActivity(intent);


                    }








                }
            });



        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("str", ex.toString());
        }
    }

    @Override
    public int getItemCount() {
        return Venderlist_Model.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder

    {
        public ImageView Fuelstaion_logo;
        public TextView fueualstaion_name;
        public TextView fulstion_address;
        public TextView fulstion_km, braches,txt_braches;
        LinearLayout Vender_km, braches_list, deatil_option;

        public MessageViewHolder(View itemView) {
            super(itemView);


            Fuelstaion_logo = itemView.findViewById(R.id.Fuelstaion_logo);
            fueualstaion_name = itemView.findViewById(R.id.fueualstaion_name);
            fulstion_address = itemView.findViewById(R.id.fulstion_address);
            fulstion_km = itemView.findViewById(R.id.fulstion_km);
            braches_list = itemView.findViewById(R.id.braches_list);
            Vender_km = itemView.findViewById(R.id.Vender_km);
            deatil_option = itemView.findViewById(R.id.deatil_option);
            braches = itemView.findViewById(R.id.braches);
            txt_braches = itemView.findViewById(R.id.txt_braches);


        }
    }

}











