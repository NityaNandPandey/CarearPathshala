package com.career.pathshala.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.career.pathshala.R;
import com.career.pathshala.activity.MainActivity;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private String key, value;

    int notificationID = 1;//edited by rupesh
    //public static int isnotification = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("------->"+TAG, "From: " + remoteMessage.getFrom());
        Log.d("------->"+TAG, "Title: " + remoteMessage.getNotification().getTitle());
        Log.d("------->"+TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        //Calling method to generate notification by rupeshmishra

        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());//edited by rupesh
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String title, String messageBody) {//changed by rupesh
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       /* PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);*/
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationID, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // notificationBuilder.setSound(uri);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(uri)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //notificationManager.notify(0, notificationBuilder.build());
        notificationManager.notify(notificationID, notificationBuilder.build());
    }
}