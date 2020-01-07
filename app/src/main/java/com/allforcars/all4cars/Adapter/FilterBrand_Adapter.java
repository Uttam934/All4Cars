package com.allforcars.all4cars.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Model.Brand_Model;
import com.allforcars.all4cars.Model.Feaulserher_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class FilterBrand_Adapter extends RecyclerView.Adapter<FilterBrand_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<Brand_Model> Brand_Model;
    private JSONArray BrandjsonArray = new JSONArray();
    JSONObject json = new JSONObject();
    List<String> brandlist = new ArrayList<>();
    ;
    private FilterBrand_AdapterListener filterBrand_adapterListener;

    public FilterBrand_Adapter(Context context, ArrayList<Brand_Model> Recyclemodel, FilterBrand_AdapterListener filterBrand_adapterListener) {
        this.context = context;
        this.Brand_Model = Recyclemodel;
        this.filterBrand_adapterListener = filterBrand_adapterListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.brandfileter_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setTag(Brand_Model.get(position));

        holder.rproductnm_name.setText(Brand_Model.get(position).getBrand_name());


        String url_image = Utility.logo_url + Brand_Model.get(position).getBrand_logo();
        Glide.with(context).load(url_image)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.productlist_logo);





    }


    @Override
    public int getItemCount() {
        return Brand_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView productlist_logo;
        public TextView rproductnm_name;
        public TextView fulstion_address;
        public TextView fulstion_km;
        public CheckBox filtertype;


        public ViewHolder(View itemView) {
            super(itemView);

            productlist_logo = itemView.findViewById(R.id.productlist_logo);
            rproductnm_name = itemView.findViewById(R.id.rproductnm_name);
            filtertype = itemView.findViewById(R.id.filtertype);


            filtertype.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    filterBrand_adapterListener.isCheckCheckBox(Brand_Model.get(getAdapterPosition()), isChecked, getAdapterPosition());
                }
            });


        }
    }

    public interface FilterBrand_AdapterListener {

        void isCheckCheckBox(Brand_Model brand_model, boolean checkBox, int position);
    }
}