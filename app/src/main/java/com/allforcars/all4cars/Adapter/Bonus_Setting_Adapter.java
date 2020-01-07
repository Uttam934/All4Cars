package com.allforcars.all4cars.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allforcars.all4cars.Model.bonus_Setting_Model;
import com.allforcars.all4cars.R;

import java.util.ArrayList;

public class Bonus_Setting_Adapter extends RecyclerView.Adapter<Bonus_Setting_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<com.allforcars.all4cars.Model.bonus_Setting_Model> bonus_Setting_Model;

    public Bonus_Setting_Adapter(Context context, ArrayList<bonus_Setting_Model> Recyclemodel ) {
        this.context = context;
        this.bonus_Setting_Model = Recyclemodel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bonusseetinglist, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
            }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
       holder.itemView.setTag(bonus_Setting_Model.get(position));
//
        holder.text_bonus_point.setText(bonus_Setting_Model.get(position).getbonus_point());
        holder.text_bonus_amt.setText(bonus_Setting_Model.get(position).getamount());






        }


    @Override
    public int getItemCount() {
        return bonus_Setting_Model.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView text_bonus_point,text_bonus_amt,text_bonusstatus;






    public ViewHolder(View itemView) { super(itemView);

        text_bonus_point = itemView.findViewById(R.id.text_bonus_point);
        text_bonus_amt = itemView.findViewById(R.id.text_bonus_amt);








    }
}

}
