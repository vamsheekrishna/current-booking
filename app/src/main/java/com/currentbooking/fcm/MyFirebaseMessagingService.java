package com.currentbooking.fcm;


import androidx.annotation.NonNull;

import com.currentbooking.utilits.LoggerInfo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Satya Seshu on 13/07/20.
 */
public class MyFireBaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        LoggerInfo.printLog( "Refreshed token: " , token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        LoggerInfo.printLog("From: " , remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            LoggerInfo.printLog( "Message data payload: ", remoteMessage.getData());

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            LoggerInfo.printLog( "Message Notification Body: ", remoteMessage.getNotification().getBody());
        }
    }
}
