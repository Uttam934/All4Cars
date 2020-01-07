package com.allforcars.all4cars.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.allforcars.all4cars.Model.Feaulserher_Model;
import com.allforcars.all4cars.Model.Type_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class Filtertype_Adapter extends RecyclerView.Adapter<Filtertype_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<com.allforcars.all4cars.Model.Brand_Model> Brand_Model;
    private JSONArray dulicateQuestionArray = new JSONArray();
    String Catergory,filteritem_id;
    ArrayList<String> listdata = new ArrayList<String>();
    private Filtertype_AdapterListener filtertype_adapterListener;
    private ArrayList<Type_Model> Type_Model;
    JSONObject json = new JSONObject();
    List<String> stList=new ArrayList<>();;
    public Filtertype_Adapter(Context context, ArrayList<Type_Model> Recyclemodel,Filtertype_AdapterListener filtertype_adapterListener ) {
        this.context = context;
        this.Type_Model = Recyclemodel;
        this.filtertype_adapterListener = filtertype_adapterListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.typefilterlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setTag(Type_Model.get(position));
        SharedPreferences sharedpreference = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        Catergory = sharedpreference.getString("Catergorylist", "");

        holder.filterprod_name.setText(Type_Model.get(position).gettype_name());
        String url_image = Utility.Calteroy_icon + Type_Model.get(position).gettype_logo();

        Glide.with(context).load(url_image).asBitmap()
                .centerCrop()
                .transform(new CircleTransform(context))
                .override(100, 100)
                .into(holder.productlist_logo);


        filteritem_id= Type_Model.get(position).gettype_id();

      if(Catergory.equals(""))
      {


      }
      else {

          String strArray[] = Catergory.split(",");
          for(int i=0;i<strArray.length;i++)
          {
              if(filteritem_id.equals(strArray[i])){
                  holder.checked_typefilter.setChecked(true);
                  holder.checked_typefilter.setEnabled(true);
              }else {

              }
          }

      }





    }


    @Override
    public int getItemCount() {
        return Type_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView productlist_logo;
        public TextView filterprod_name;
        public TextView fulstion_address;
        public TextView fulstion_km;
        public CheckBox checked_typefilter;





        public ViewHolder(View itemView) {
            super(itemView);

            productlist_logo = itemView.findViewById(R.id.typefilterlist_logo);
            filterprod_name = itemView.findViewById(R.id.filterproductnm_name);
            checked_typefilter = itemView.findViewById(R.id.checked_typefilter);




            checked_typefilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    filtertype_adapterListener.isCheckCheckBox(Type_Model.get(getAdapterPosition()),isChecked,getAdapterPosition());
                }
            });


        }
    }

    public interface Filtertype_AdapterListener {

        void isCheckCheckBox(Type_Model type_model,boolean checkBox,int position);
    }

}