package com.allforcars.all4cars.Test;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.allforcars.all4cars.R;


public class Update extends Activity {
    String empid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        try{
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        }
        catch(Exception er){
            Log.e("Update Activity:", er.getLocalizedMessage());
        }
        setContentView(R.layout.update);

        Button bn = (Button) findViewById(R.id.download);

        Button download = (Button)findViewById(R.id.download);
        download.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.aksha.unnati"));
                    startActivity(intent);
                    finish();
                }catch(Exception e){

                }
            }
        });

        Button cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

    }


}