package com.allforcars.all4cars.Test.Webview;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.allforcars.all4cars.R;

import java.io.File;

public class Webview_Activity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        webView = (WebView) findViewById(R.id.web_types);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.adinedcare.com/launcher.html");
        webView.setWebViewClient(new WebViewClient());


//        webView = (WebView)findViewById(R.id.web_types);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.setWebViewClient(new WebViewClient() {
//
//
//            @Override
//            public boolean  shouldOverrideUrlLoading(WebView view, String url) {
//                if(url != null && url.startsWith("whatsapp://"))
//                {
//                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                    return true;
//
//                }else
//                {
//                    return false;
//                }
//            }
//        });


//        webView.loadUrl("https://www.adinedcare.com/launcher.html");
//        webView.setHorizontalScrollBarEnabled(false);
//        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
//        webView.setBackgroundColor(128);










    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack();
            clearApplicationData();
        } else {

            super.onBackPressed();
        }

    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        clearApplicationData();
    }


    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("EEEEEERRRRRROOOOOOORRRR", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            int i = 0;
            while (i < children.length) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
                i++;
            }
        }

        assert dir != null;
        return dir.delete();
    }

    @Override
    protected void onResume() {
        super.onResume();

        clearApplicationData();
    }





}
