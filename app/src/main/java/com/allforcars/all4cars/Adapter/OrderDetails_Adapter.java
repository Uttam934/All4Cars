package com.allforcars.all4cars.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allforcars.all4cars.Activity.Home_Activity;
import com.allforcars.all4cars.Model.Feaulserher_Model;
import com.allforcars.all4cars.Model.OrderDeatils_Model;
import com.allforcars.all4cars.Model.Product_detail_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


public class OrderDetails_Adapter extends RecyclerView.Adapter<OrderDetails_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<OrderDeatils_Model> OrderDeatils_Model;

    public OrderDetails_Adapter(Context context, ArrayList<OrderDeatils_Model> Recyclemodel ) {
        this.context = context;
        this.OrderDeatils_Model = Recyclemodel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlistdetail, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(OrderDeatils_Model.get(position));

        holder.txt_productcompnaynm.setText(OrderDeatils_Model.get(position).getproduct_name());
        holder.txt_productprice.setText(OrderDeatils_Model.get(position).getproduct_Pricetot()+"₦");
        holder.txt_quantitiy.setText(OrderDeatils_Model.get(position).getproduct_quantity());
        holder.txt_quantitiy.setText(OrderDeatils_Model.get(position).getproduct_quantity());
        holder.txt_unitdetail.setText(OrderDeatils_Model.get(position).getunit());


        if(OrderDeatils_Model.get(position).getdiscounthide().equals("1"))
        {
            holder.txtdiscoutn.setVisibility(View.GONE);
        }
        else
        {
            holder.txt_dis.setText(OrderDeatils_Model.get(position).getproduct_dis_Amount()+"₦");
        }

        String url_image = Utility.product_image + OrderDeatils_Model.get(position).getproduct_image();


        Glide.with(context).load(url_image).asBitmap()
                .centerCrop()
                .transform(new CircleTransform(context))
                .override(100, 100)
                .into(holder.img_product);



    }


    @Override
    public int getItemCount() {
        return OrderDeatils_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img_product;
        public TextView txt_productcompnaynm,txt_productprice,txt_quantitiy,txt_dis,txt_unitdetail;
        LinearLayout txtdiscoutn;


        public ViewHolder(View itemView) { super(itemView);

            txt_productcompnaynm = itemView.findViewById(R.id.txt_productcompnaynm);
            txt_productprice = itemView.findViewById(R.id.txt_productprice);
            txt_quantitiy = itemView.findViewById(R.id.txt_quantitiy);
            img_product = itemView.findViewById(R.id.img_product);
            txt_dis = itemView.findViewById(R.id.txt_dis);
            txtdiscoutn = itemView.findViewById(R.id.txtdiscoutn);
            txt_unitdetail = itemView.findViewById(R.id.txt_unitdetail);






        }
    }

}