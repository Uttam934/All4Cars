package com.allforcars.all4cars.Test.PinLock.managers;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.Home_Activity;
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



public abstract class SetPinAppLockActivity extends PinActivity implements KeyboardButtonClickedListener, View.OnClickListener, FingerprintUiHelper.Callback {

    public static final String TAG = SetPinAppLockActivity.class.getSimpleName();
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
        //initLayoutForFingerprint();
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
        _checkPinView = (PinView_6) findViewById(R.id.pv_pinView);
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


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    private void initLayoutForFingerprint() {

        mFingerprintImageView = (ImageView) this.findViewById(R.id.pin_code_fingerprint_imageview);
        mFingerprintTextView = (TextView) this.findViewById(R.id.pin_code_fingerprint_textview);
        if (mType == AppLock.UNLOCK_PIN && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mFingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
            mFingerprintUiHelper = new FingerprintUiHelper.FingerprintUiHelperBuilder(mFingerprintManager).build(mFingerprintImageView, mFingerprintTextView, this);
            try {
                if (mFingerprintManager.isHardwareDetected() && mFingerprintUiHelper.isFingerprintAuthAvailable()
                        && mLockManager.getAppLock().isFingerprintAuthEnabled()) {
               /*     mFingerprintImageView.setVisibility(View.VISIBLE);
                    mFingerprintTextView.setVisibility(View.VISIBLE);
                    mFingerprintUiHelper.startListening();*/

                    mFingerprintImageView.setVisibility(View.GONE);
                    mFingerprintTextView.setVisibility(View.GONE);
                } else {
                    mFingerprintImageView.setVisibility(View.GONE);
                    mFingerprintTextView.setVisibility(View.GONE);
                }
            } catch (SecurityException e) {
                Log.e(TAG, e.toString());
                mFingerprintImageView.setVisibility(View.GONE);
                mFingerprintTextView.setVisibility(View.GONE);
            }
        } else {
            mFingerprintImageView.setVisibility(View.GONE);
            mFingerprintTextView.setVisibility(View.GONE);
        }
    }


    private void enableAppLockerIfDoesNotExist() {
        try {
            if (mLockManager.getAppLock() == null) {
                mLockManager.enableAppLock(this, getCustomAppLockActivityClass());
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


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

    private void setForgotTextVisibility() {
        mForgotTextView.setVisibility(mLockManager.getAppLock().shouldShowForgot(mType) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onKeyboardClick(KeyboardButtonEnum keyboardButtonEnum) {
        if (mPinCode.length() < this.getPinLength()) {
            int value = keyboardButtonEnum.getButtonValue();
            int a = KeyboardButtonEnum.BUTTON_CLEAR.getButtonValue();
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
            case AppLock.ENABLE_PINLOCK:
                mOldPinCode = mPinCode;
                setPinCode("");
                mType = AppLock.CONFIRM_PIN;
                setStepText();
                setForgotTextVisibility();
                break;
            case AppLock.CONFIRM_PIN:
                if (mPinCode.equals(mOldPinCode)) {
                    mType = 5;
                   // setPinCall();
                } else {
                    mOldPinCode = "";
                    setPinCode("");
                    mType = AppLock.ENABLE_PINLOCK;
                    setStepText();
                    setForgotTextVisibility();
                    onPinCodeError();
                }
                break;
            default:
                break;
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
//        Toast.makeText(this, "Incorrect pin code", Toast.LENGTH_LONG).show();
//        onPinFailure(mAttempts++);
//        Thread thread = new Thread() {
//            public void run() {
//                mPinCode = "";
//                _checkPinView.setText(mPinCode);
//                mPinCodeRoundView.refresh(mPinCode.length());
//                Animation animation = AnimationUtils.loadAnimation(
//                        SetPinAppLockActivity.this, R.anim.shake);
//                mKeyboardView.startAnimation(animation);
//            }
//        };
//        runOnUiThread(thread);
        Intent intent = new Intent(SetPinAppLockActivity.this, Home_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

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
        return SetPinAppLockActivity.DEFAULT_PIN_LENGTH;
    }


    public Class<? extends SetPinAppLockActivity> getCustomAppLockActivityClass() {
        return this.getClass();
    }




}
