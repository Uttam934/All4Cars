package com.allforcars.all4cars.classes;

import android.app.Application;
import android.content.Context;

/**
 * Created by admin on 04-Jun-18.
 */

public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "id"));
    }
}