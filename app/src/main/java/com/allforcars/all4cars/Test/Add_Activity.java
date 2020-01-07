package com.allforcars.all4cars.Test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.allforcars.all4cars.R;

import java.util.ArrayList;

public class Add_Activity extends AppCompatActivity {

    RecyclerView recycler_add;
    Add_Adapter add_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Add_Model> addmodelArrayList;
    Button btn_check;


    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        recycler_add = findViewById(R.id.recycler_add);
        btn_check = findViewById(R.id.btn_check);
        recycler_add.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Add_Activity.this);
        recycler_add.setLayoutManager(layoutManager);


        add_adapter = new Add_Adapter(Add_Activity.this, addmodelArrayList);
        recycler_add.setLayoutManager(new LinearLayoutManager(Add_Activity.this, LinearLayoutManager.VERTICAL, false));
        recycler_add.setAdapter(add_adapter);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmodelArrayList=   add_adapter.getAddModelList();

              Toast.makeText(getApplicationContext(),""+addmodelArrayList.toArray(), Toast.LENGTH_SHORT).show();
            }
        });

    }




}
