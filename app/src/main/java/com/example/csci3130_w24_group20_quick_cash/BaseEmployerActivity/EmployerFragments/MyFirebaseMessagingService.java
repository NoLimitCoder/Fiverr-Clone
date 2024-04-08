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


/**
 * Service class responsible for handling Firebase Cloud Messaging (FCM) messages.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {


    /**
     * Called when a new token for the device is generated.
     *
     * @param token The new token generated for the device.
     */
    @Override
    public void onNewToken(@NonNull String token){
        super.onNewToken(token);
    }


    /**
     * Called when a message is received from Firebase Cloud Messaging.
     *
     * @param remoteMessage The message received from Firebase Cloud Messaging.
     */

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

        Intent intent = new Intent(this, FavJobTypeNotifService.class);
        intent.putExtra("title", title);
        intent.putExtra("body", body);
        intent.putExtra("jobID", jobID);
        intent.putExtra("jobLocation", jobLocation);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 10, intent, PendingIntent.FLAG_IMMUTABLE);

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
}