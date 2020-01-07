package com.allforcars.all4cars.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allforcars.all4cars.Model.Faq_Model;
import com.allforcars.all4cars.Model.Notification_Model;
import com.allforcars.all4cars.R;

import java.util.ArrayList;


public class Faq_Adapter extends RecyclerView.Adapter<Faq_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<Faq_Model> Faq_Model;
    String Ans;
    Boolean isClick= true;

    public Faq_Adapter(Context context, ArrayList<Faq_Model> Recyclemodel ) {
        this.context = context;
        this.Faq_Model = Recyclemodel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.faqitmelist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemView.setTag(Faq_Model.get(position));


                Ans = ("<font COLOR=\'BLACK\'>" + Faq_Model.get(position).getAns());

        holder.notificatititle.setText(Faq_Model.get(position).getQuestion());
        holder.notificationmsg.setText(Html.fromHtml(Ans));

        final int pos= position;

        holder.notificatititle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (!isClick){
                    isClick = true;
                    holder.notificationmsg.setVisibility(View.VISIBLE);
                    holder.imagearrow_douwn.setVisibility(View.GONE);
                    holder.imagearrow_up.setVisibility(View.VISIBLE);

                }else{
                    isClick = false;
                    holder.notificationmsg.setVisibility(View.GONE);
                    holder.imagearrow_douwn.setVisibility(View.VISIBLE);
                    holder.imagearrow_up.setVisibility(View.GONE);
                }

            }
        });

        holder.imagearrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (!isClick){
                    isClick = true;
                    holder.notificationmsg.setVisibility(View.VISIBLE);
                    holder.imagearrow_douwn.setVisibility(View.GONE);
                    holder.imagearrow_up.setVisibility(View.VISIBLE);

                }else{
                    isClick = false;
                    holder.notificationmsg.setVisibility(View.GONE);
                    holder.imagearrow_douwn.setVisibility(View.VISIBLE);
                    holder.imagearrow_up.setVisibility(View.GONE);
                }

            }
        });





    }


    @Override
    public int getItemCount() {
        return Faq_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView notificatititle,notificationdate,notificationmsg;
        ImageView imagearrow_douwn,imagearrow_up;
        LinearLayout imagearrow;



        public ViewHolder(View itemView) { super(itemView);



            imagearrow_up = itemView.findViewById(R.id.imagearrow_up);
            imagearrow_douwn = itemView.findViewById(R.id.imagearrow_douwn);
            notificatititle = itemView.findViewById(R.id.notificatititle);
            notificationmsg = itemView.findViewById(R.id.notificationmsg);
            imagearrow = itemView.findViewById(R.id.imagearrow);




        }
    }

}