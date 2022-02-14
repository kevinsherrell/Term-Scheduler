package com.c196.TermScheduler.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.c196.TermScheduler.R;

public class TermReceiver extends BroadcastReceiver {
    static int notificationID;
    String channel_id = "term";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "termNotify")
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("Course Notification")
                .setContentText("Woo hoo you have a course/course starting today!!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationID++, builder.build());
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = "term_channel";
        String description = "term notification channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
