package com.allforcars.all4cars.Test.PinLock.managers;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Test.PinLock.PinActivity;
import com.allforcars.all4cars.Test.PinLock.PinView_6;
import com.github.orangegangsters.lollipin.lib.enums.KeyboardButtonEnum;
import com.github.orangegangsters.lollipin.lib.interfaces.KeyboardButtonClickedListener;
import com.github.orangegangsters.lollipin.lib.views.KeyboardView;
import com.github.orangegangsters.lollipin.lib.views.PinCodeRoundView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public abstract class ScreenOffCheckPinActivity extends PinActivity implements KeyboardButtonClickedListener, View.OnClickListener, FingerprintUiHelper.Callback {

    public static final String TAG = ScreenOffCheckPinActivity.class.getSimpleName();
    public static final String ACTION_CANCEL = TAG + ".actionCancelled";
    private static final int DEFAULT_PIN_LENGTH = 6;

    protected TextView mStepTextView;
    protected TextView mForgotTextView;
    protected PinCodeRoundView mPinCodeRoundView;
    protected KeyboardView mKeyboardView;
    protected ImageView mFingerprintImageView;
    protected TextView mFingerprintTextView;

    protected LockManager mLockManager;
    protected FingerprintManager mFingerprintManager;
    protected FingerprintUiHelper mFingerprintUiHelper;

    protected int mType = AppLock.UNLOCK_PIN;
    protected int mAttempts = 1;
    protected String mPinCode;

    protected String mOldPinCode;

    private boolean isCodeSuccessful = false;

    PinView_6 _checkPinView;

    /**
     * First creation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());
        initLayout(getIntent());
    }

    /**
     * If called in singleTop mode
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        initLayout(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Init layout for Fingerprint
       // initLayoutForFingerprint();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mFingerprintUiHelper != null) {
            mFingerprintUiHelper.stopListening();
        }
    }


    private void initLayout(Intent intent) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
      //  progressDialog=new CustomProgressDialog(ScreenOffCheckPinActivity.this,R.drawable.custom_progress_layout);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            //Animate if greater than 2.3.3
            overridePendingTransition(R.anim.nothing, R.anim.nothing);
        }

        Bundle extras = intent.getExtras();
        if (extras != null) {
            mType = extras.getInt(AppLock.EXTRA_TYPE, AppLock.UNLOCK_PIN);
        }
        mLockManager = LockManager.getInstance();
        mPinCode = "";
        mOldPinCode = "";

        enableAppLockerIfDoesNotExist();
        mLockManager.getAppLock().setPinChallengeCancelled(false);

        //=========rectangle pinview===========//
        _checkPinView= (PinView_6) findViewById(R.id.pv_pinView);
        _checkPinView.setAnimationEnable(true);

        mStepTextView = (TextView) this.findViewById(R.id.pin_code_step_textview);
        mPinCodeRoundView = (PinCodeRoundView) findViewById(R.id.pin_code_round_view);
        mPinCodeRoundView.setPinLength(this.getPinLength());
        mForgotTextView = (TextView) this.findViewById(R.id.pin_code_forgot_textview);
        mForgotTextView.setOnClickListener(this);
        mKeyboardView = (KeyboardView) this.findViewById(R.id.pin_code_keyboard_view);
        mKeyboardView.setKeyboardButtonClickedListener(this);

        int logoId = mLockManager.getAppLock().getLogoId();
        ImageView logoImage = ((ImageView) findViewById(R.id.pin_code_logo_imageview));
        if (logoId != AppLock.LOGO_ID_NONE) {
            logoImage.setVisibility(View.VISIBLE);
            logoImage.setImageResource(logoId);
        }
        mForgotTextView.setText(getForgotText());
        setForgotTextVisibility();
        setStepText();

        //========set icon on toolbar============//
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Check PIN");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Init {@link FingerprintManager} of the {@link Build.VERSION#SDK_INT} is > to Marshmallow
     * and {@link FingerprintManager#isHardwareDetected()}.
     */

    private void enableAppLockerIfDoesNotExist() {
        try {
            if (mLockManager.getAppLock() == null) {
                mLockManager.enableAppLock(this, getCustomAppLockActivityClass());
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * Init the {@link #mStepTextView} based on {@link #mType}
     */
    private void setStepText() {
        mStepTextView.setText(getStepText(mType));
    }


    public String getStepText(int reason) {
        String msg = null;
        switch (reason) {
            case AppLock.DISABLE_PINLOCK:
                msg = getString(R.string.pin_code_step_disable, this.getPinLength());
                break;
            case AppLock.ENABLE_PINLOCK:
                msg = getString(R.string.pin_code_step_create, this.getPinLength());
                break;
            case AppLock.CHANGE_PIN:
                msg = getString(R.string.pin_code_step_change, this.getPinLength());
                break;
            case AppLock.UNLOCK_PIN:
                msg = getString(R.string.pin_code_step_unlock, this.getPinLength());
                break;
            case AppLock.CONFIRM_PIN:
                msg = getString(R.string.pin_code_step_enable_confirm, this.getPinLength());
                break;
        }
        return msg;
    }

    public String getForgotText() {
        return getString(R.string.pin_code_forgot_text);
    }

    private void setForgotTextVisibility(){
        mForgotTextView.setVisibility(mLockManager.getAppLock().shouldShowForgot(mType) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onKeyboardClick(KeyboardButtonEnum keyboardButtonEnum) {
        if (mPinCode.length() < this.getPinLength()) {
            int value = keyboardButtonEnum.getButtonValue();
            int a= KeyboardButtonEnum.BUTTON_CLEAR.getButtonValue();
            if (value == KeyboardButtonEnum.BUTTON_CLEAR.getButtonValue()) {
                if (!mPinCode.isEmpty()) {
                    setPinCode(mPinCode.substring(0, mPinCode.length() - 1));
                } else {
                    setPinCode("");
                }
            } else {
                setPinCode(mPinCode + value);
            }
        }
    }


    @Override
    public void onRippleAnimationEnd() {
        if (mPinCode.length() == this.getPinLength()) {
            onPinCodeInputed();
        }
    }

    /**
     * Switch over the {@link #mType} to determine if the password is ok, if we should pass to the next step etc...
     */
    protected void onPinCodeInputed() {
        switch (mType) {
            case AppLock.UNLOCK_PIN:
                mType = 5;
              //  checkPinCall();
                break;
            default:
                break;
        }
    }

    /**
     * Override {@link #onBackPressed()} to prevent user for finishing the activity
     */


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        ActivityCompat.finishAffinity(this);
    }
    @Override
    public void onAuthenticated() {
        Log.e(TAG, "Fingerprint READ!!!");
        onPinCodeSuccess();
    }
    @Override
    public void onError() {
        Log.e(TAG, "Fingerprint READ ERROR!!!");
    }

    /**
     * Displays the information dialog when the user clicks the
     * {@link #mForgotTextView}
     */
    public abstract void showForgotDialog();

    /**
     * Run a shake animation when the password is not valid.
     */
    protected void onPinCodeError() {
        Toast.makeText(this,"Incorrect pin code", Toast.LENGTH_LONG).show();
        onPinFailure(mAttempts++);
        Thread thread = new Thread() {
            public void run() {
                mPinCode = "";
                _checkPinView.setText(mPinCode);
                mPinCodeRoundView.refresh(mPinCode.length());
                Animation animation = AnimationUtils.loadAnimation(
                        ScreenOffCheckPinActivity.this, R.anim.shake);
                mKeyboardView.startAnimation(animation);
            }
        };
        runOnUiThread(thread);
    }

    protected void onPinCodeSuccess() {
        isCodeSuccessful = true;
        onPinSuccess(mAttempts);
        mAttempts = 1;
    }


    public void setPinCode(String pinCode) {
        mPinCode = pinCode;
        _checkPinView.setText(mPinCode);
        mPinCodeRoundView.refresh(mPinCode.length());
    }



    public int getType() {
        return mType;
    }

    /**
     * When we click on the {@link #mForgotTextView} handle the pop-up
     * dialog
     *
     * @param view {@link #mForgotTextView}
     */
    @Override
    public void onClick(View view) {
        showForgotDialog();
    }

    /**
     * When the user has failed a pin challenge
     *
     * @param attempts the number of attempts the user has used
     */
    public abstract void onPinFailure(int attempts);

    /**
     * When the user has succeeded at a pin challenge
     *
     * @param attempts the number of attempts the user had used
     */
    public abstract void onPinSuccess(int attempts);


    public int getContentView() {
        return R.layout.activity_pin_rectangle_code;
    }

    /**
     * Gets the number of digits in the pin code.  Subclasses can override this to change the
     * length of the pin.
     *
     * @return the number of digits in the PIN
     */
    public int getPinLength() {
        return ScreenOffCheckPinActivity.DEFAULT_PIN_LENGTH;
    }

    /**
     * Get the current class extending {@link ScreenOffCheckPinActivity} to re-enable {@link AppLock}
     * in case it has been collected
     *
     * @return the current class extending {@link ScreenOffCheckPinActivity}
     */
    public Class<? extends ScreenOffCheckPinActivity> getCustomAppLockActivityClass() {
        return this.getClass();
    }





}
