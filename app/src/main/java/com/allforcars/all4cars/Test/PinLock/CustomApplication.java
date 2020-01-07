package com.allforcars.all4cars.Test.PinLock;

import android.app.Application;

import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Test.PinLock.managers.LockManager;


/**
 * Created by oliviergoutay on 1/14/15.
 */
public class CustomApplication extends Application {

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate() {
        super.onCreate();

        LockManager<CustomCheckPinActivity> lockManager = LockManager.getInstance();
        lockManager.enableAppLock(this, CustomCheckPinActivity.class);
        lockManager.getAppLock().setLogoId(R.drawable.security_lock);
    }
}
