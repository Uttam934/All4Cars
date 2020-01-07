package com.allforcars.all4cars.classes.apicall;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


//@ReportsCrashes(mailTo = "dvkrrao21@gmail.com",
//        formUri = "https://{myusername}.cloudant.com/acra-{myapp}/_design/acra-storage/_update/report",
//        reportType = HttpSender.Type.JSON,
//        httpMethod = HttpSender.Method.POST,
//        formUriBasicAuthLogin = "GENERATED_USERNAME_WITH_WRITE_PERMISSIONS",
//        formUriBasicAuthPassword = "GENERATED_PASSWORD",
//        formKey = "", // This is required for backward compatibility but not used
//        customReportContent = {
//                ReportField.APP_VERSION_CODE,
//                ReportField.APP_VERSION_NAME,
//                ReportField.ANDROID_VERSION,
//                ReportField.PACKAGE_NAME,
//                ReportField.REPORT_ID,
//                ReportField.BUILD,
//                ReportField.CUSTOM_DATA,
//                ReportField.STACK_TRACE,
//                ReportField.PHONE_MODEL,
//                ReportField.LOGCAT
//        })
//import android.support.multidex.MultiDex;
//import android.support.multidex.MultiDexApplication;

public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();
    private static AppController mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context context;
    public static boolean activityVisible;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        ACRA.init(this);
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG);
//        OneSignal.startInit(this).unsubscribeWhenNotificationsAreDisabled(true).init();
//        AdWordsConversionReporter.reportWithConversionId(getApplicationContext(), "869594500", "RQjeCLW_1nAQhOvTngM", "10.00", false);

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

//    public ImageLoader getImageLoader() {
//        getRequestQueue();
//        if (mImageLoader == null) {
//            mImageLoader = new ImageLoader(this.mRequestQueue,
//                    new LruBitmapCache());
//        }
//        return this.mImageLoader;
//    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void setContext(Context ctx) {
        this.context = ctx;
    }

    public Context getContext() {
        return context;
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    public static void DialogactivityResumed() {
        activityVisible = true;
    }

    public static void DialogactivityPaused() {
        activityVisible = false;
    }

}