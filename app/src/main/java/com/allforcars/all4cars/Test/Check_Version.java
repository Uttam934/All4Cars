package com.allforcars.all4cars.Test;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.allforcars.all4cars.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class Check_Version extends AppCompatActivity {


    int versionCode;
    String versionName,Latestvesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // forceUpdate();
        new VersionChecker().execute();


        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            versionCode = info.versionCode;
          versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
        }



    }



    public class VersionChecker extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String newVersion = null;


            Document document = null;
            try {
                document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "com.aksha.unnati" + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();



                              if(!newVersion.equals(versionName))
                              {
                                  Intent intent = new Intent(getApplicationContext(), Update.class);
                                  startActivity(intent);
                                  finish();
                              }

                                Log.e("Uttam", "---" + newVersion);

                                Latestvesion = newVersion;

                            }
                        }
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }



            return newVersion;


            }



    }





}
