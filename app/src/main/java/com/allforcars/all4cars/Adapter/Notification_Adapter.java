package com.allforcars.all4cars.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allforcars.all4cars.Model.Notification_Model;
import com.allforcars.all4cars.R;
import java.util.ArrayList;


public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<Notification_Model> Notification_Model;
    private int lastPosition = -1;

    public Notification_Adapter(Context context, ArrayList<Notification_Model> Recyclemodel ) {
        this.context = context;
        this.Notification_Model = Recyclemodel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemslist_notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(Notification_Model.get(position));


        holder.notificatititle.setText(Notification_Model.get(position).gettitle());
        holder.notificationdate.setText(Notification_Model.get(position).getdate());
        holder.notificationmsg.setText(Notification_Model.get(position).getmessage());

        setAnimationaa(holder.itemView, position);



    }



    public void setAnimationaa(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }




        if (position < lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return Notification_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView notificatititle,notificationdate,notificationmsg;



        public ViewHolder(View itemView) { super(itemView);

            notificatititle = itemView.findViewById(R.id.notificatititle);
            notificationmsg = itemView.findViewById(R.id.notificationmsg);
            notificationdate = itemView.findViewById(R.id.notificationdate);






        }
    }

}