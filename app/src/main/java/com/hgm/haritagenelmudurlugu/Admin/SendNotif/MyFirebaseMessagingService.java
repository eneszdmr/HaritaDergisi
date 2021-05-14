package com.hgm.haritagenelmudurlugu.Admin.SendNotif;

import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hgm.haritagenelmudurlugu.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String title,message;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        title=remoteMessage.getData().get("Title");
        message=remoteMessage.getData().get("Message");

        NotificationCompat.Builder builder=
                new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.search)
                .setContentTitle(title)
                .setContentText(message);
        NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }
}
