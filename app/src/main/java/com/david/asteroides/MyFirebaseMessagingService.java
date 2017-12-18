package com.david.asteroides;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by david on 18/12/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "Noticias";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.d (TAG, "Mensaje recibido de: " + from);

        if(remoteMessage.getNotification() != null){
            String message = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Notificacion: " + message);
        }

    }
}
