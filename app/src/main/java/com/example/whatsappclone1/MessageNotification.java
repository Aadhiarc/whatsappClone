package com.example.whatsappclone1;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MessageNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //to change the activity while click the notification
        Intent intent1=new Intent(context,PersonalChat.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent1,0);
        String msg=intent.getStringExtra("sendMessage");
        //create notification
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"Message");
        builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Alarming !!!")
                .setContentText(msg)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        //show notification
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());
    }
}
