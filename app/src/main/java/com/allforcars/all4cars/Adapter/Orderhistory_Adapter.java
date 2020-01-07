package com.allforcars.all4cars.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allforcars.all4cars.Activity.OrderDeatil_Activity;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.Record;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Orderhistory_Adapter extends RecyclerView.Adapter<Orderhistory_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<Record> Record;
    String order_number,subamt,totaldis,totalamt;
    String Total_amt;

    DecimalFormat df = new DecimalFormat("####0.00");

    public Orderhistory_Adapter(Context context, ArrayList<Record> Recyclemodel ) {
        this.context = context;
        this.Record = Recyclemodel;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_orderhistory, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setTag(Record.get(position));

        holder.txt_ordernumber.setText(Record.get(position).getCategoryId());
        holder.txt_ordrdate.setText(Record.get(position).getProductId());
        holder.paymenttype.setText(Record.get(position).getVenderId());
        holder.paymenstatus.setText(Record.get(position).getCategoryId());
        Total_amt= Record.get(position).getTotalAmount();
        double dd=Double.parseDouble(Total_amt);
        holder.txt_totlamt.setText(df.format(dd)+"₦");







    }


    @Override
    public int getItemCount() {
        return Record.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView productlist_logo;
        public TextView txt_ordernumber,txt_ordrdate,paymenttype,paymenstatus,btn_view,txt_totlamt;




        public ViewHolder(View itemView) {
            super(itemView);

            txt_ordernumber = itemView.findViewById(R.id.txt_ordernumber);
            txt_ordrdate = itemView.findViewById(R.id.txt_ordrdate);
            paymenttype = itemView.findViewById(R.id.paymenttype);
            paymenstatus = itemView.findViewById(R.id.paymenstatus);
            btn_view = itemView.findViewById(R.id.btn_view);
            txt_totlamt = itemView.findViewById(R.id.txt_totlamt);



        }
    }



}