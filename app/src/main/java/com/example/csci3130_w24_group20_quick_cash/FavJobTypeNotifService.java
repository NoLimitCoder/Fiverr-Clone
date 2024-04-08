package com.example.csci3130_w24_group20_quick_cash;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Service for handling favorite job type notifications.
 */
public class FavJobTypeNotifService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "job_notification_channel";
    private static final String CHANNEL_NAME = "Job Notifications";
    private static final int NOTIFICATION_ID = 1;


    /**
     * Called when a new message is received.
     *
     * @param remoteMessage The received remote message.
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            String jobTitle = remoteMessage.getData().get("jobTitle");
            showNotification(jobTitle);
        }
    }


    /**
     * Shows a notification for the new job posting.
     *
     * @param jobTitle The title of the new job posting.
     */

    private void showNotification(String jobTitle) {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("New Job Posting")
                .setContentText("New job: " + jobTitle)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }


    /**
     * Creates a notification channel for job notifications.
     */
    private void createNotificationChannel() {
        String description = "Job Notifications";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
        channel.setDescription(description);

        // Register the channel with the system
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}

