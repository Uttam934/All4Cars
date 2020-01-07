package com.allforcars.all4cars.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allforcars.all4cars.Model.RedeemHistory_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class RedeemHistory_Adapter extends RecyclerView.Adapter<RedeemHistory_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<com.allforcars.all4cars.Model.RedeemHistory_Model> RedeemHistory_Model;
    String Status_Activate;

    public RedeemHistory_Adapter(Context context, ArrayList<RedeemHistory_Model> Recyclemodel ) {
        this.context = context;
        this.RedeemHistory_Model = Recyclemodel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.redeemhistorylist, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
            }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
       holder.itemView.setTag(RedeemHistory_Model.get(position));
//
        holder.text_offe_name.setText(RedeemHistory_Model.get(position).getloyalty_name());
        holder.text_redempoints.setText(RedeemHistory_Model.get(position).getbonus_point());
            holder.text_date.setText(RedeemHistory_Model.get(position).getredeem_date());
            if(RedeemHistory_Model.get(position).getloyalty_status().equals("0"))
            {
                holder.btn_statas.setText("Pending");
            }

            else if(RedeemHistory_Model.get(position).getloyalty_status().equals("1"))
            {
                holder.btn_statas.setText("Completed");
            }

            else if(RedeemHistory_Model.get(position).getloyalty_status().equals("2"))
            {
                holder.btn_statas.setText("Decline");
            }
            else {


            }


         String url_image = Utility.loyalty_icon + RedeemHistory_Model.get(position).getloyalty_image();

            Glide.with(context).load(url_image).asBitmap()
                    .centerCrop()
                    .transform(new CircleTransform(context))
                    .override(100, 100)
                    .into(holder.offer_imageredem);




        }


    @Override
    public int getItemCount() {
        return RedeemHistory_Model.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

            public ImageView offer_imageredem;
            public TextView text_offe_name,text_redempoints,text_date,btn_statas;
            LinearLayout redeem_activiate;






    public ViewHolder(View itemView) { super(itemView);

        offer_imageredem = itemView.findViewById(R.id.offer_imageredem);
        text_offe_name = itemView.findViewById(R.id.text_offe_name);
        text_redempoints = itemView.findViewById(R.id.text_redempoints);
        text_date = itemView.findViewById(R.id.text_date);
        btn_statas = itemView.findViewById(R.id.btn_statas);







    }
}

}
