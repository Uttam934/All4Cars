package com.allforcars.all4cars.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allforcars.all4cars.Model.Notification_Model;
import com.allforcars.all4cars.Model.VenderBranch_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class VenderBranch_Adapter extends RecyclerView.Adapter<VenderBranch_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<VenderBranch_Model> VenderBranch_Model;

    public VenderBranch_Adapter(Context context, ArrayList<VenderBranch_Model> Recyclemodel ) {
        this.context = context;
        this.VenderBranch_Model = Recyclemodel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.branchlistvender, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(VenderBranch_Model.get(position));

        holder.brach_name.setText(VenderBranch_Model.get(position).getBranch_name());
        holder.brach_address.setText(VenderBranch_Model.get(position).getBranch_address());
        holder.brach_km.setText(VenderBranch_Model.get(position).getBranch_km());


        String url_image = Utility.logo_url + VenderBranch_Model.get(position).getBranch_image();
        Glide.with(context).load(url_image)
                .crossFade()
                .thumbnail(0.5f)
                .transform(new CircleTransform(context))
                .override(100, 100)
                .into(holder.barch_logo);



    }


    @Override
    public int getItemCount() {
        return VenderBranch_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView brach_name,brach_address,brach_km;
        ImageView barch_logo;



        public ViewHolder(View itemView) { super(itemView);

            barch_logo = itemView.findViewById(R.id.barch_logo);
            brach_name = itemView.findViewById(R.id.brach_name);
            brach_address = itemView.findViewById(R.id.brach_address);
            brach_km = itemView.findViewById(R.id.brach_km);






        }
    }

}