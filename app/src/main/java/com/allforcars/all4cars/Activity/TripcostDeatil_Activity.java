package com.allforcars.all4cars.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allforcars.all4cars.R;

import java.text.DecimalFormat;

public class TripcostDeatil_Activity extends AppCompatActivity {

   TextView txt_totldistance,txt_perliter,txt_tripcost,txt_tritime;
   ImageView btnsettingd;
   String Distance,Perlistcost,Totalcost,Total_time;
    DecimalFormat df = new DecimalFormat("####0.00");
    Double Distance_tot,Perlistcost_tot,Totalcost_tot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripcost_deatil);

        btnsettingd=findViewById(R.id.btnsettingd);
        txt_totldistance=findViewById(R.id.txt_totldistance);
        txt_tripcost=findViewById(R.id.txt_tripcost);
        txt_tritime=findViewById(R.id.txt_tritime);

        Distance= getIntent().getStringExtra("Distance");
        Perlistcost= getIntent().getStringExtra("Perlistcost");
        Totalcost= getIntent().getStringExtra("Totalcost");
        Total_time= getIntent().getStringExtra("TotalTime");

                Perlistcost_tot=Double.parseDouble(Perlistcost);
        Totalcost_tot=Double.parseDouble(Totalcost);

        txt_totldistance.setText(Distance);
        txt_tripcost.setText(df.format(Totalcost_tot)+"₦");
        txt_tritime.setText(Total_time);


        btnsettingd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }
}
