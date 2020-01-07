package com.allforcars.all4cars.Venderlogin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Venderlogin.model.ProudctService_Model;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


public class ProductService_Adapter extends RecyclerView.Adapter<ProductService_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<com.allforcars.all4cars.Venderlogin.model.ProudctService_Model> ProudctService_Model;

    public ProductService_Adapter(Context context, ArrayList<ProudctService_Model> Recyclemodel ) {
        this.context = context;
        this.ProudctService_Model = Recyclemodel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.productservicelist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(ProudctService_Model.get(position));

        holder.product_name.setText(ProudctService_Model.get(position).getProduct_name());
        holder.vender_productprice.setText(ProudctService_Model.get(position).getProduct_price()+"₦");
        String url_image = Utility.product_image + ProudctService_Model.get(position).getProduct_image();
        Glide.with(context).load(url_image)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.product_image);



    }


    @Override
    public int getItemCount() {
        return ProudctService_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView product_image;
        public TextView product_name,vender_productprice;




        public ViewHolder(View itemView) { super(itemView);

            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            vender_productprice = itemView.findViewById(R.id.vender_productprice);



        }
    }

}