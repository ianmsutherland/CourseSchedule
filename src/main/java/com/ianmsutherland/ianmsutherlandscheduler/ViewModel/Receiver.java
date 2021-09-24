package com.ianmsutherland.ianmsutherlandscheduler.ViewModel;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.ianmsutherland.ianmsutherlandscheduler.R;

public class Receiver extends BroadcastReceiver {

    static int courseId;
    String channelId = "Scheduler";

    @Override
    public void onReceive(Context context, Intent intent) {
        courseId = intent.getIntExtra("courseId",0);
        createNotificationChannel(context,channelId);
        Notification notification = new Notification.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("text"))
                .setContentTitle(intent.getStringExtra("title")).build();
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(courseId,notification);
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Scheduler";
            String desc = "Notifications for Scheduler app";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(desc);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
