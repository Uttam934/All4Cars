package com.allforcars.all4cars.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.allforcars.all4cars.Model.Loyality_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class Loyality_Adapter extends RecyclerView.Adapter<Loyality_Adapter.MessageViewHolder>{


    private List<Loyality_Model> Loyality_Model;
    Context context;
    View view=null;
    String image="",image_url="";
    String msg_count="",str_ad_id="",str_count="";
    private String StrReadStatus="";
    //   RefreshInterface refreshInterface;
    SharedPreferences sharedPreferences;
    public Loyality_Adapter(Context context, List<Loyality_Model> Loyality_Model)
    {
        this.context=context;
        this.Loyality_Model = Loyality_Model;
        // this.refreshInterface = refreshInterface;

    }

    @NonNull
    @Override
    public Loyality_Adapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        try{

            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.loyalityitems,parent,false);


        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Loyality_Adapter.MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Loyality_Adapter.MessageViewHolder holder, final int position)
    {

        try
        {


            holder.text_offe_name.setText(Loyality_Model.get(position).getloyalty_name());
            holder.text_years.setText(Loyality_Model.get(position).getloyality_years());
            holder.text_date.setText(Loyality_Model.get(position).getcreated_on());


            String url_image = Utility.loyalty_icon + Loyality_Model.get(position).getloyalty_image();


            Glide.with(context).load(url_image).asBitmap()
                    .centerCrop()
                    .transform(new CircleTransform(context))
                    .override(100, 100)
                    .into(holder.offer_imageredem);


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e("str",ex.toString());
        }
    }

    @Override
    public int getItemCount() {
        return Loyality_Model.size();
    }
    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position)
    {
        return super.getItemId(position);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder

    {
        TextView text_offe_name,text_years,text_date,tv_message_row;
        Button btn_next;
        ImageView offer_imageredem;
        public MessageViewHolder(View itemView)
        {
            super(itemView);


            offer_imageredem = itemView.findViewById(R.id.offer_imageredem);
            text_offe_name = itemView.findViewById(R.id.text_offe_name);
            text_years = itemView.findViewById(R.id.text_years);
            text_date = itemView.findViewById(R.id.text_date);



        }
    }



}
