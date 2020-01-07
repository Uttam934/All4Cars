package com.allforcars.all4cars.Test.TestLargeAPI.Code;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allforcars.all4cars.Activity.Deatail_Activity;
import com.allforcars.all4cars.Encript_Dcript.MCrypt;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Test.TestLargeAPI.Model.Result;

import java.util.ArrayList;


public class Recylerlist_adapter extends RecyclerView.Adapter<Recylerlist_adapter.ViewHolder> {

    private Context context;
    private ArrayList<Result> Result;
    String order_number,subamt,totaldis,totalamt;
    String Total_amt;
    String testlist;
    String[] strArray ;
    MCrypt mCrypt;



    public Recylerlist_adapter(Context context, ArrayList<Result> Recyclemodel ) {
        this.context = context;
        this.Result = Recyclemodel;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listmail, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setTag(Result.get(position));

        mCrypt = new MCrypt();

       //  holder.title.setText(Result.get(position).getTitle());
        String getPerfact = null;
        try {
            getPerfact = Result.get(position).getPerFacet().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(getPerfact!=null)
        {


            try {
                holder.title.setText(mCrypt.Encrypt(Result.get(position).getPerFacet().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            holder.title.setText("");

        }
        int pos = position;

        holder.id_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String getPerfact = null;
                try {
                    getPerfact = Result.get(pos).getPerFacet().toString();
                    if(getPerfact!=null)
                    {
                        testlist=getPerfact;
                        strArray = new String[] {testlist};

                    }
                    else
                    {
                        testlist="";

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                Intent ins =  new Intent(context, Datalist_Activity.class);
//                ins.putExtra("value",testlist);
//                context.startActivity(ins);





            }
        });






    }


    @Override
    public int getItemCount() {
        return Result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView productlist_logo;
        public TextView title,byline,paymenttype,paymenstatus,btn_view,txt_totlamt;
        public RelativeLayout id_list;




        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            byline = itemView.findViewById(R.id.byline);
            paymenttype = itemView.findViewById(R.id.paymenttype);
            paymenstatus = itemView.findViewById(R.id.paymenstatus);
            btn_view = itemView.findViewById(R.id.btn_view);
            txt_totlamt = itemView.findViewById(R.id.txt_totlamt);
            id_list = itemView.findViewById(R.id.id_list);



        }
    }



}