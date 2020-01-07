package com.allforcars.all4cars.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allforcars.all4cars.Model.Feaulserher_Model;
import com.allforcars.all4cars.Model.Property_Model;
import com.allforcars.all4cars.R;

import java.util.ArrayList;


public class FilterProperty_Adapter extends RecyclerView.Adapter<FilterProperty_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<com.allforcars.all4cars.Model.Property_Model> Property_Model;

    public FilterProperty_Adapter(Context context, ArrayList<Property_Model> Recyclemodel ) {
        this.context = context;
        this.Property_Model = Recyclemodel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fulfinderlistview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(Property_Model.get(position));

//        holder.fueualstaion_name.setText(Property_Model.get(position).getfeaul_stationname());
//        holder.fulstion_address.setText(Property_Model.get(position).getfeaul_stationaddress());
//        holder.fulstion_km.setText(Property_Model.get(position).getfeaul_stationkm());






    }


    @Override
    public int getItemCount() {
        return Property_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView Fuelstaion_logo;
        public TextView fueualstaion_name;
        public TextView fulstion_address;
        public TextView fulstion_km;





        public ViewHolder(View itemView) { super(itemView);

//            Fuelstaion_logo = itemView.findViewById(R.id.Fuelstaion_logo);
//            fueualstaion_name = itemView.findViewById(R.id.fueualstaion_name);
//            fulstion_address = itemView.findViewById(R.id.fulstion_address);
//            fulstion_km = itemView.findViewById(R.id.fulstion_km);







        }
    }

}