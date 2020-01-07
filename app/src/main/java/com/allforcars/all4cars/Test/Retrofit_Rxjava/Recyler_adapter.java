package com.allforcars.all4cars.Test.Retrofit_Rxjava;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Recyler_adapter extends RecyclerView.Adapter<Recyler_adapter.ViewHolder> {

    private Context context;
    private ArrayList<com.allforcars.all4cars.Model.Histroyorder_Model> Histroyorder_Model;
    String order_number,subamt,totaldis,totalamt;
    String Total_amt;

    DecimalFormat df = new DecimalFormat("####0.00");

    public Recyler_adapter(Context context, ArrayList<com.allforcars.all4cars.Model.Histroyorder_Model> Recyclemodel ) {
        this.context = context;
        this.Histroyorder_Model = Recyclemodel;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_orderhistory, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setTag(Histroyorder_Model.get(position));

        holder.txt_ordernumber.setText(Histroyorder_Model.get(position).getorder_number());
        holder.txt_ordrdate.setText(Histroyorder_Model.get(position).getorderdate());
        holder.paymenttype.setText(Histroyorder_Model.get(position).getpaymettype());
        holder.paymenstatus.setText(Histroyorder_Model.get(position).getstatus());
        Total_amt= Histroyorder_Model.get(position).gettotoalpayamt();
        double dd=Double.parseDouble(Total_amt);
        holder.txt_totlamt.setText(df.format(dd)+"₦");



        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                order_number=Histroyorder_Model.get(position).getorder_number();
                subamt=Histroyorder_Model.get(position).getsubtotoal();
                totaldis=Histroyorder_Model.get(position).gettotdiscount();
                totalamt=Histroyorder_Model.get(position).gettotoalpayamt();

                Intent ints = new Intent(context, OrderDeatil_Activity.class);
                ints.putExtra("order_number", order_number);
                ints.putExtra("subamt", subamt);
                ints.putExtra("totaldis", totaldis);
                ints.putExtra("totalamt", totalamt);
                context.startActivity(ints);

            }
        });





    }


    @Override
    public int getItemCount() {
        return Histroyorder_Model.size();
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