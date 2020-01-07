package com.allforcars.all4cars.Test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.allforcars.all4cars.R;
import java.util.ArrayList;


public class Add_Adapter extends RecyclerView.Adapter<Add_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<Add_Model> addModelList = new ArrayList<>();
    private ArrayList<Add_Model> addModelList1 = new ArrayList<>();
    String name, add,mob;
    View v;



    public Add_Adapter(Context context, ArrayList<Add_Model> Recyclemodel) {
        this.context = context;
        this.addModelList = Recyclemodel;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_add, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final int pos = position;
        final Add_Model am=new Add_Model();


        holder.txt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = holder.txt_name.getText().toString();
                am.setName(name);
                if(!addModelList1.contains(am))
                addModelList1.add(pos,am);
            }

            @Override
            public void afterTextChanged(Editable s) {



            }



        });

        holder.txt_mob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mob = holder.txt_mob.getText().toString();
                am.setMob(mob);
                if(!addModelList1.contains(am))
                addModelList1.add(pos,am);


            }

            @Override
            public void afterTextChanged(Editable s) { //addModelList1.add(position,am);

}




        });



       holder.txt_add.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                add = holder.txt_add.getText().toString();
                am.setAdd(add);
                if(!addModelList1.contains(am))
                addModelList1.add(pos,am);

            }

            @Override
            public void afterTextChanged(Editable s) { //.addModelList.add(position, am);

//
            }
        });



    }

       public ArrayList<Add_Model> getAddModelList() {
        ArrayList<Add_Model> add_models=new ArrayList<>();
        for (int i = 0; i < addModelList1.size(); i++) {
            if(addModelList1.get(i).add!=null &&addModelList1.get(i).mob!=null &&addModelList1.get(i).name!=null )
            {
                add_models.add(addModelList1.get(i));
            }
        }

        return add_models;
    }






    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView txt_varity;
        EditText txt_add, txt_mob,txt_name;


        public ViewHolder(View itemView) {
            super(itemView);

            txt_add = itemView.findViewById(R.id.txt_add);
            txt_mob = itemView.findViewById(R.id.txt_mob);
            txt_name = itemView.findViewById(R.id.txt_name);


        }
    }



}
