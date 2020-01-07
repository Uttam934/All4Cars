package com.allforcars.all4cars.Test.SarchlistRecyler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Browser;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Test.modelCreateBorrowers.Borrower;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;

import java.util.List;

public class List_Adapter extends RecyclerView.Adapter<List_Adapter.MessageViewHolder>{


    private List<Borrower> Borrower;
    Context context;
    View view=null;
    String image="",image_url="";
    String msg_count="",str_ad_id="",str_count="";
    private String StrReadStatus="";
    //   RefreshInterface refreshInterface;
    SharedPreferences sharedPreferences;
    public List_Adapter(Context context, List<Borrower> Borrower)
    {
        this.context=context;
        this.Borrower = Borrower;
        // this.refreshInterface = refreshInterface;

    }

    @NonNull
    @Override
    public List_Adapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        try{

            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.lists_items,parent,false);


        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new List_Adapter.MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List_Adapter.MessageViewHolder holder, final int position)
    {


        holder.borrowe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent ins= new Intent(context,Borwer_Activity.this);
//                context.startActivity(ins);


            }
        });

        try
        {

            Log.d("Test", "onBindViewHolder: "+(Borrower.get(position).getAddress()));

            holder.frammer_name.setText(Borrower.get(position).getName());
            holder.text_loan.setText(Borrower.get(position).getAddress());
            holder.text_mobile.setText(Borrower.get(position).getMNo());
            holder.qtybrower.setText(Borrower.get(position).getAadharNo());
            holder.text_discout.setText(Borrower.get(position).getTotalCurrentEmiAmount());



        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e("str",ex.toString());
        }
    }

    @Override
    public int getItemCount() {
        return Borrower.size();
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
        TextView text_discout,qtybrower,text_loan,text_mobile,frammer_name;
        ImageView borrowe_btn;

        public MessageViewHolder(View itemView)
        {
            super(itemView);


            text_loan = itemView.findViewById(R.id.text_loan);
            frammer_name = itemView.findViewById(R.id.frammer_name);
            text_mobile = itemView.findViewById(R.id.text_mobile);
            qtybrower = itemView.findViewById(R.id.qtybrower);
            text_discout = itemView.findViewById(R.id.text_discout);
            borrowe_btn = itemView.findViewById(R.id.borrowe_btn);


        }
    }



}
