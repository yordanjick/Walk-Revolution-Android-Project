package com.example.cse110_project.team;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.cse110_project.AcceptActivity;
import com.example.cse110_project.R;

import com.example.cse110_project.WWRApplication;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class WWRFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "WWRFirebaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        /*String notificationBody = "";
        String notificationTitle = "";
        String notificationData = "";
        try{
            notificationData = remoteMessage.getData().toString();
            notificationTitle = remoteMessage.getNotification().getTitle();
            notificationBody = remoteMessage.getNotification().getBody();
        }catch (NullPointerException e){
            Log.e(TAG, "onMessageReceived: NullPointerException: " + e.getMessage() );
        }
        Log.d(TAG, "onMessageReceived: data: " + notificationData);
        Log.d(TAG, "onMessageReceived: notification body: " + notificationBody);
        Log.d(TAG, "onMessageReceived: notification title: " + notificationTitle);


        String dataType = remoteMessage.getData().get(getString(R.string.message_type));
        if(dataType.equals(getString(R.string.team_invitation))){
            Log.d(TAG, "onMessageReceived: new team invitation");
            String senderEmail = remoteMessage.getData().get(getString(R.string.sender_email));
            String senderName = remoteMessage.getData().get(getString(R.string.sender_name));
            String messageId = remoteMessage.getData().get(getString(R.string.data_message_id));

            //TODO notify user through: notifyMessage(title, message, messageId);

        } else if(dataType.equals(getString(R.string.team_walk_invitation))) {
            Log.d(TAG, "onMessageReceived: new team walk invitation");
            //TODO
        }*/
    }

    private void notifyMessage(String title, String message, String messageId){
        /*Log.d(TAG, "sendChatmessageNotification: building a chatmessage notification");

        //get the notification id
        int notificationId = buildNotificationId(messageId);

        // Instantiate a Builder object.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                getString(R.string.notification_channel_id));
        // Creates an Intent for the Activity
        Intent pendingIntent = new Intent(this, AcceptActivity.class);
        if(title.equals(getString(R.string.team_invitation))) {
            pendingIntent.putExtra("team_id", message);
        } else if(title.equals(getString(R.string.team_walk_invitation))) {
            //TODO: suggestion, put route id of proposed team walk?
            pendingIntent.putExtra("route_id", message);
        }
        // Creates the PendingIntent
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        pendingIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        //add properties to the builder
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(title)
                .setColor(getColor(R.color.colorPrimary))
                .setAutoCancel(true)
                //.setSubText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setOnlyAlertOnce(true);

        builder.setContentIntent(notifyPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationId, builder.build());*/

    }

    private int buildNotificationId(String id){
        /*Log.d(TAG, "buildNotificationId: building a notification id.");

        int notificationId = 0;
        for(int i = 0; i < 9; i++){
            notificationId = notificationId + id.charAt(i);
        }
        Log.d(TAG, "buildNotificationId: id: " + id);
        Log.d(TAG, "buildNotificationId: notification id:" + notificationId);
        return notificationId;*/
        return  0;
    }

    public void sendInvitation(String messageType, String receiverEmail, String receiverName, String senderEmail) {

    }
}
