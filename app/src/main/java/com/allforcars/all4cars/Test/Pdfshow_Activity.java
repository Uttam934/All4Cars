package com.allforcars.all4cars.Test;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allforcars.all4cars.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Pdfshow_Activity extends AppCompatActivity {


    TextView title_text;
    ImageView image_back_btn;
    WebView web;
    PDFView pdfView;
    String url1 ="",url2 ="";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    ProgressBar progressBar1;
    String str_State_Law="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        url2 = "http://itdevelopmentservices.com/studentbp/upload/documents/Federal%20Law%20Harassment.pdf";

        progressBar1 = findViewById(R.id.progressBar1);
        pdfView      = findViewById(R.id.pdfView);

        new RetrievePDFStream().execute(url2);


    }

    class RetrievePDFStream extends AsyncTask<String, Void, InputStream>
    {
        @Override
        protected InputStream doInBackground(String... strings)
        {
            InputStream inputStream = null;
            try
            {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == 200)
                {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            catch (IOException e)
            {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream)
        {
            pdfView.fromStream(inputStream).load();
            progressBar1.setVisibility(View.GONE);
        }
    }
}
