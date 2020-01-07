package com.allforcars.all4cars.Venderlogin.Adapter;

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
import com.allforcars.all4cars.Venderlogin.model.Paymenthistory_Model;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class PaymentHistory_Adapter extends RecyclerView.Adapter<PaymentHistory_Adapter.MessageViewHolder>{


    private List<Paymenthistory_Model> Paymenthistory_Model;
    Context context;
    View view=null;
    public PaymentHistory_Adapter(Context context, List<Paymenthistory_Model> Paymenthistory_Model)
    {
        this.context=context;
        this.Paymenthistory_Model = Paymenthistory_Model;
        // this.refreshInterface = refreshInterface;

    }

    @NonNull
    @Override
    public PaymentHistory_Adapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        try{

            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.paymntslip,parent,false);


        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new PaymentHistory_Adapter.MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentHistory_Adapter.MessageViewHolder holder, final int position)
    {

        try
        {


            holder.text_Pyamentdate.setText(Paymenthistory_Model.get(position).getpayment_date());
            holder.text_paymentamt.setText(Paymenthistory_Model.get(position).getpayment_amount()+"₦");
            holder.text_remainamt.setText(Paymenthistory_Model.get(position).getremainning_amount()+"₦");




        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e("str",ex.toString());
        }
    }

    @Override
    public int getItemCount() {
        return Paymenthistory_Model.size();
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
        TextView text_paymentamt,text_remainamt,text_Pyamentdate,tv_message_row;
        public MessageViewHolder(View itemView)
        {
            super(itemView);


            text_paymentamt = itemView.findViewById(R.id.text_paymentamt);
            text_remainamt = itemView.findViewById(R.id.text_remainamt);
            text_Pyamentdate = itemView.findViewById(R.id.text_Pyamentdate);




        }
    }



}
