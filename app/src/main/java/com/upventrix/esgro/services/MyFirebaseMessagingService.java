package com.upventrix.esgro.services;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.upventrix.esgro.R;
import com.upventrix.esgro.resource.LocalData;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message) {

        System.out.println("From "+message.getFrom());

        if (message.getData().size()>0){
            System.out.println("Message Data Payload "+message.getData());

        }

        if (message.getNotification()!=null){
            System.out.println("Message Notification Body "+message.getNotification().getBody());

            String title = message.getNotification().getTitle();
            String msg = message.getNotification().getBody();

            System.out.println("Title "+title +"  /  "+msg);

        }
        super.onMessageReceived(message);
        show( message.getNotification().getTitle(), message.getNotification().getBody());
    }

    public void show(String titile,String msg){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(titile)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(999, notificationBuilder.build());
        System.out.println("titel "+titile+"  "+msg);

    }
    public MyFirebaseMessagingService() {
        super();
        System.out.println("MyFirebaseMessagingService");
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        System.out.println("onDeletedMessages");
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        System.out.println("onMessageSent");
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
        System.out.println("onSendError");
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
//        sendRegistrationToServer(token);

        System.out.println("onNewToken  "+s);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        new LocalData().setNotificationToken(sharedPref,s);

    }
}
