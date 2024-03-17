package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.csci3130_w24_group20_quick_cash.FavJobTypeNotifService;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token){
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification() == null){
            return;
        }

        final String title = remoteMessage.getNotification().getTitle();
        final String body = remoteMessage.getNotification().getBody();

        final Map<String, String> data = remoteMessage.getData();
        final String jobID = data.get("jobID");
        final String jobLocation = data.get("jobLocation");

        Intent intenet = new Intent(this, FavJobTypeNotifService.class);
        intenet.putExtra("title", title);
        intenet.putExtra("body", body);
        intenet.putExtra("jobID", jobID);
        intenet.putExtra("jobLocation", jobLocation);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 10, intenet, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "JOBS")
                        .setSmallIcon(R.drawable.baseline_list_alt_24)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int id = (int) System.currentTimeMillis();

        NotificationChannel channel = new NotificationChannel("JOBS", "JOBS", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(id, notificationBuilder.build());
    }

    private void createNotification(String title, String body) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "channel_id";
        String channelName = "Channel Name";

        NotificationChannel channel = new NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri);

        notificationManager.notify(0, notificationBuilder.build());
    }
}