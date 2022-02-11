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
    static int notificationID = 0;
    String channel_id = "term";

    @Override
    public void onReceive(Context context, Intent intent) {
        String TITLE = intent.getStringExtra("TITLE");
        String TEXT = intent.getStringExtra("TEXT");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "termNotify")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(TITLE)
                .setContentText(TEXT)
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
