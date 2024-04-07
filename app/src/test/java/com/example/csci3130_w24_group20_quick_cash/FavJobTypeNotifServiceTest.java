package com.example.csci3130_w24_group20_quick_cash;

import static org.junit.Assert.assertNotNull;

import android.content.pm.PackageManager;
import com.google.firebase.messaging.RemoteMessage;
import org.junit.Test;
import org.mockito.Mock;


public class FavJobTypeNotifServiceTest {

    @Mock
    PackageManager packageManager;

    @Test
    public void testOnMessageImmediatelyReceived() {
        FavJobTypeNotifService favJobTypeNotifService = new FavJobTypeNotifService();
        RemoteMessage.Builder builder = new RemoteMessage.Builder("1234@gcm.googleapis.com");
        builder.addData("jobTitle", "Software Engineer");
        RemoteMessage remoteMessage = builder.build();
        assertNotNull(favJobTypeNotifService);
    }

    @Test
    public void testOnMessageDelayedReceived() {
        FavJobTypeNotifService favJobTypeNotifServiceDelayed = new FavJobTypeNotifService();
        RemoteMessage.Builder builder = new RemoteMessage.Builder("54321@gcm.googleapis.com");
        builder.addData("jobTitle", "Software Engineer");
        RemoteMessage remoteDelayedMessage = builder.build();
        assertNotNull(favJobTypeNotifServiceDelayed);
    }
}
