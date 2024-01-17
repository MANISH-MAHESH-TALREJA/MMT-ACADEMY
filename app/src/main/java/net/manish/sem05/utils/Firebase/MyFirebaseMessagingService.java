package com.pixelnx.eacademy.utils.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.ui.splash.SplashActivity;

import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static String WHERE = "",BatchName="",BatchID="", vId = "", topicVideo = "",videoType,videoUrl;


    String str1 = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        if (remoteMessage.getData().size() > 0) {
            try {

                JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("message"));
                Log.v("saloni123","str1---  "+jsonObject);
                str1 = jsonObject.getString("title");
                Log.v("saloni123","str1---  "+str1);
                JSONObject jsonObjectWhere = jsonObject.getJSONObject("body");

                WHERE = jsonObjectWhere.getString("where");
                BatchName = jsonObjectWhere.getString("batch_name");
                BatchID = jsonObjectWhere.getString("batch_id");


                if (jsonObjectWhere.has("videoId")) {
                    Log.v("salonishrivastava","saloni "+jsonObject);
                    vId = jsonObjectWhere.getString("videoId");
                    topicVideo = jsonObjectWhere.getString("videoName");
                    videoUrl=jsonObjectWhere.getString("url");
                    videoType=jsonObjectWhere.getString("videoType");
                }
                sendNotification();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void sendNotification() throws NullPointerException {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(str1+" ("+BatchName+")")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
               .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent);



        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }


        notificationManager.notify(0, notificationBuilder.build());
    }
}

