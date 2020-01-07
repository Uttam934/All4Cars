package com.allforcars.all4cars.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.allforcars.all4cars.R;

public class Km_Setting_Activity extends AppCompatActivity {

    SeekBar seekbar;
    TextView textview;
    SharedPreferences sharedPreferences;
    String seekbarValue="30";
    RelativeLayout back_loyaltiy;
    LinearLayout km_homepage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_km__setting);

        seekbar = (SeekBar)findViewById(R.id.seekBar1);
        textview = (TextView)findViewById(R.id.textView1);
        back_loyaltiy = (RelativeLayout)findViewById(R.id.back_loyaltiy);
        km_homepage = (LinearLayout)findViewById(R.id.km_homepage);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        seekbarValue =sharedpreference.getString("seekbarValue","");

        if (seekbarValue.equals(""))
        {
            seekbarValue="30";
            textview.setText(seekbarValue+" "+"Km");
            seekbar.setProgress(Integer.parseInt(seekbarValue));
        }

        else {
            textview.setText(seekbarValue+" "+"Km");
            seekbar.setProgress(Integer.parseInt(seekbarValue));

        }


        textview.setText(seekbarValue+" "+"Km");

        back_loyaltiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        km_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Km_Setting_Activity.this,Home_Activity.class);
                startActivity(in);
                finish();

            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                seekbarValue = String.valueOf(i);

                textview.setText(seekbarValue+" "+"Km");

                sharedPreferences= getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("seekbarValue", seekbarValue);
                editor.apply();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
