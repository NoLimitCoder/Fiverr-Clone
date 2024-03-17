package com.example.csci3130_w24_group20_quick_cash;

import androidx.core.app.NotificationCompat;

public class NotificationActivity {

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
}
