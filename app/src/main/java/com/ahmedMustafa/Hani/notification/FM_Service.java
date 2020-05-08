package com.ahmedMustafa.Hani.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.utils.PrefManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FM_Service extends FirebaseMessagingService {
    private String description;
    private String id;
    private PrefManager manager;
    private String title;
    private String type;
    private int image;
    private Intent intent;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("sendMsg", remoteMessage.getData().toString());
        manager = new PrefManager(this);
        description = remoteMessage.getData().get("message");
        title = remoteMessage.getData().get("title");
        type = remoteMessage.getData().get("type");

        sendNotification();
    }

    private void sendNotification() {
        PendingIntent pendingIntent;
        if (intent != null)
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        else
            pendingIntent = null;

        Uri localUri = RingtoneManager.getDefaultUri(2);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(this.title)
                .setContentText(this.description)
                .setAutoCancel(true)
                .setSound(localUri)
                .setPriority(0)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), image))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(this.description)
                        .setBigContentTitle(this.title)
                        .setSummaryText("إشعار جديد"));

        if (pendingIntent != null) builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

    }

}