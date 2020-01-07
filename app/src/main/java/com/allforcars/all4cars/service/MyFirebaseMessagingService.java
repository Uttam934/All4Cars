package com.allforcars.all4cars.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;


import com.allforcars.all4cars.Activity.Home_Activity;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Venderlogin.Activity.Vender_Home_Activity;
import com.allforcars.all4cars.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    int notifyID = 0 ;

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    String usertype,title;
    private NotificationUtils notificationUtils;


    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        usertype =sharedpreference.getString("usertype","");


//        String message = remoteMessage.getNotification().getBody();
        if(usertype.equals("2")) {

            title = remoteMessage.getNotification().getTitle();
            Log.d("msg", "onMessageReceived: " + remoteMessage.getData().get("message"));
            Intent intent = new Intent(this, Home_Activity.class);
            intent.putExtra("filter_key","filter_value");
            intent.putExtra("name_key","1");
            //intent.putExtra("class","notification_parent");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String channelId = "Default";
            NotificationCompat.Builder builders = new NotificationCompat.Builder(this);
            builders.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)

                    .setSmallIcon(R.mipmap.app_logo)
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))
                    .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }
            manager.notify(0, builder.build());

        }

        else {



            title = remoteMessage.getNotification().getTitle();
            Log.d("msg", "onMessageReceived: " + remoteMessage.getData().get("message"));
            Intent intent = new Intent(this, Vender_Home_Activity.class);

            //intent.putExtra("class","notification_parent");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String channelId = "Default";
            NotificationCompat.Builder builders = new NotificationCompat.Builder(this);
            builders.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)

                    .setSmallIcon(R.mipmap.app_logo)
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))
                    .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }
            manager.notify(0, builder.build());









        }

    }


    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}