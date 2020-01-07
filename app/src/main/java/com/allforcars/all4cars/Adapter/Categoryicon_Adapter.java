package com.allforcars.all4cars.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allforcars.all4cars.Model.Feaulserher_Model;
import com.allforcars.all4cars.Model.Catergoyicons_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


public class Categoryicon_Adapter extends RecyclerView.Adapter<Categoryicon_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<com.allforcars.all4cars.Model.Catergoyicons_Model> Catergoyicons_Model;

    public Categoryicon_Adapter(Context context, ArrayList<Catergoyicons_Model> Recyclemodel ) {
        this.context = context;
        this.Catergoyicons_Model = Recyclemodel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryicons, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(Catergoyicons_Model.get(position));



        String url_image = Utility.Calteroy_icon + Catergoyicons_Model.get(position).getcategroy_icons();

        Glide.with(context).load(url_image).asBitmap()
                .centerCrop()
                .transform(new CircleTransform(context))
                .override(100, 100)
                .into(holder.btn_categroy);





    }


    @Override
    public int getItemCount() {
        return Catergoyicons_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView btn_categroy;




        public ViewHolder(View itemView) { super(itemView);

            btn_categroy = itemView.findViewById(R.id.btn_categroy);
//



        }
    }

}