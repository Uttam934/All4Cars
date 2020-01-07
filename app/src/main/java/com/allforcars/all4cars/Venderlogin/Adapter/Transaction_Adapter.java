package com.allforcars.all4cars.Venderlogin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Venderlogin.model.Categorylist_Model;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


public class Transaction_Adapter extends RecyclerView.Adapter<Transaction_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<com.allforcars.all4cars.Venderlogin.model.Categorylist_Model> Categorylist_Model;

    public Transaction_Adapter(Context context, ArrayList<Categorylist_Model> Recyclemodel ) {
        this.context = context;
        this.Categorylist_Model = Recyclemodel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactoinlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(Categorylist_Model.get(position));

        holder.product_name.setText(Categorylist_Model.get(position).getCategory_name());
        holder.allforcarcommsion.setText(Categorylist_Model.get(position).getall4carcommison());
        holder.userdiscoutn.setText(Categorylist_Model.get(position).getCategory_discoutn());
        String url_image = Utility.Calteroy_icon + Categorylist_Model.get(position).getCategory_image();
        Glide.with(context).load(url_image)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.vendercategory_image);



    }


    @Override
    public int getItemCount() {
        return Categorylist_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView vendercategory_image;
        public TextView product_name,userdiscoutn,allforcarcommsion;




        public ViewHolder(View itemView) { super(itemView);

            vendercategory_image = itemView.findViewById(R.id.vendercategory_image);
            product_name = itemView.findViewById(R.id.product_name);
            userdiscoutn = itemView.findViewById(R.id.userdiscoutn);
            allforcarcommsion = itemView.findViewById(R.id.allforcarcommsion);


        }
    }

}