package com.allforcars.all4cars.Test.Retrofit_Rxjava;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.allforcars.all4cars.R;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Constant {
    // API URL configuration
    public  static String EncryptKey="key_password";
    private static final String AD_UNIT_ID = "ca-app-pub-6533865907325044/2158640850";

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivity = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Constant.toast(activity, "Connect to the Internet");
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        Constant.toast(activity, "Connect to the Internet");
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Constant.toast(context, "Connect to the Internet");
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        Constant.toast(context, "Connect to the Internet");
        return false;
    }

    public static boolean isMobileValid(String number) {
        //TODO: Replace this with your own logic
        return number.length()==10;
    }

    // method to handle images from server
    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static boolean isEmailValid(String email)
    {
        if(email.contains("@") && email.contains("."))
            return true;
        else
            return false;
    }
    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }
    public static boolean isValidPhone(CharSequence phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }

    public static boolean isValidMail(String email) {
        boolean check;
        Pattern pattern;
        Matcher matcher;
        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_STRING);
        matcher = pattern.matcher(email);
        check = matcher.matches();
        return check;
    }
    public static void CheckScreenOff(Context context,String activity){
        //it is Screen off or locked
    /*    QuickStartPreferences.setString(context,QuickStartPreferences.BACK_STACK_ACTIVITY,activity);
        QuickStartPreferences.set_Boolean(context,QuickStartPreferences.IS_SCREEN_OFF,true);
        Intent intent=new Intent(context,SplashScreenActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //finish();*/
    }

    public static int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static  String Encrypt(String text, String key)  {
        Cipher cipher = null;
        String encryptBase64=null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] keyBytes= new byte[16];
            byte[] b= key.getBytes("UTF-8");
            int len= b.length;
            if (len > keyBytes.length) len = keyBytes.length;
            System.arraycopy(b, 0, keyBytes, 0, len);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
            cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivSpec);

            byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
            encryptBase64 = Base64.encodeToString(results, Base64.DEFAULT);

        } catch (NoSuchAlgorithmException |NoSuchPaddingException |InvalidKeyException |
                InvalidAlgorithmParameterException |IllegalBlockSizeException |
                BadPaddingException |UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encryptBase64;
    }


    public static String Decrypt(String text, String key){
        Cipher cipher = null;
        String decryptBase64 = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] keyBytes= new byte[16];
            byte[] b= key.getBytes("UTF-8");
            int len= b.length;
            if (len > keyBytes.length) len = keyBytes.length;
            System.arraycopy(b, 0, keyBytes, 0, len);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
            cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);
            byte [] results = cipher.doFinal(Base64.decode(text, Base64.DEFAULT));
            decryptBase64 =new String(results,"UTF-8");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |InvalidKeyException |
                InvalidAlgorithmParameterException |IllegalBlockSizeException |
                BadPaddingException |UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decryptBase64;
    }


    public static String getSerialNumber() {
        String serialNo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            serialNo=Build.SERIAL;
        }else {
            try {
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                serialNo = (String) get.invoke(c, "ro.serialno");
            } catch (Exception ignored) {
                ignored.printStackTrace();
                serialNo="";
            }
          /*  try {
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class, String.class);
                serialNo = (String) get.invoke(c, "ril.serialnumber", "unknown");
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }*/
        }
        return serialNo;
    }

    public static String getDeviceID(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "TODO";
        }
        String id = telephonyManager.getDeviceId();
        if (id == null){
            id = "not available";
        }
        int phoneType = telephonyManager.getPhoneType();
        switch(phoneType){
            case TelephonyManager.PHONE_TYPE_NONE:
                return id;
            case TelephonyManager.PHONE_TYPE_GSM:
                return  id;
            case TelephonyManager.PHONE_TYPE_CDMA:
                return id;
            default:
                return id;
        }
    }

    public static void Alertdialog(Activity activity, String message , boolean status) {

        LayoutInflater inflater = activity.getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.customdailog_alert, null);
        TextView _tvDescription,_tvTitle;
        Button _btnOk;
        ImageView _ivok;
        _ivok = alertLayout.findViewById(R.id.iv_customDailogBox_image);
        _btnOk = alertLayout.findViewById(R.id.btn_customDailogBox_ok);
        _tvDescription = alertLayout.findViewById(R.id.tv_customDailogBox_description);
        _tvTitle = alertLayout.findViewById(R.id.tv_customDailogBox_title);
        if (status){
            _ivok.setImageResource(R.drawable.ic_fingerprint_success);
            _tvTitle.setVisibility(View.GONE);
        }else {
            _tvTitle.setVisibility(View.VISIBLE);
        }
        _tvDescription.setText(message);

        // TextView title = (TextView) alertLayout.findViewById(R.id.custom_dialog_title);

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(true);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().setLayout(150, 200);

        dialog.show();
        _btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static boolean checkString(String text){
        boolean status;
        if (TextUtils.isEmpty(text)) {
            status=true;
        }else {
            status=false;
        }
        return status;
    }
    public static String changeDateFormat(String currentDate){
        String dateFormat="";
        if (!TextUtils.isEmpty(currentDate)) {
            try {
                DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                Date d = f.parse(currentDate);
                DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
                dateFormat=  date.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
                dateFormat="";
            }
        }
        return dateFormat;
    }
}
