package com.allforcars.all4cars.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allforcars.all4cars.Model.Faq_Model;
import com.allforcars.all4cars.Model.Privacypolicy_Model;
import com.allforcars.all4cars.R;

import java.util.ArrayList;


public class Privacypolicy_Adapter extends RecyclerView.Adapter<Privacypolicy_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<Privacypolicy_Model> Privacypolicy_Model;
    String Ans;

    public Privacypolicy_Adapter(Context context, ArrayList<Privacypolicy_Model> Recyclemodel ) {
        this.context = context;
        this.Privacypolicy_Model = Recyclemodel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.privacypolicy, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(Privacypolicy_Model.get(position));


                Ans = ("<font COLOR=\'BLACK\'>" + Privacypolicy_Model.get(position).getdescription());

        holder.notificatititle.setText(Privacypolicy_Model.get(position).gettitle());
        holder.notificationmsg.setText(Html.fromHtml(Ans));






    }


    @Override
    public int getItemCount() {
        return Privacypolicy_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView notificatititle,notificationdate,notificationmsg;



        public ViewHolder(View itemView) { super(itemView);




            notificatititle = itemView.findViewById(R.id.notificatititle);
            notificationmsg = itemView.findViewById(R.id.notificationmsg);




        }
    }

}