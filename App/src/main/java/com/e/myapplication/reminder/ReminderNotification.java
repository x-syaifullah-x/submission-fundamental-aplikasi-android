package com.e.myapplication.reminder;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;

import androidx.core.app.NotificationCompat.Builder;
import androidx.core.app.NotificationManagerCompat;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.O;

class ReminderNotification {
    static final String CHANNEL_ID_DAILY_REMINDER = "ID_DAILY_REMINDER_01";
    static final String CHANNEL_NAME_DAILY_REMINDER = "Daily Reminder";
    static final String CHANNEL_ID_RELEASE_TO_DAY_REMINDER = "ID_RELEASE_TO_DAY_REMINDER_01";
    static final String CHANNEL_NAME_RELEASE_TO_DAY_REMINDER = "Release To Day Reminder";
    private static final String GROUP_ID_DAILY_REMINDER = "ID_DAILY_REMINDER_01";
    private static final String GROUP_NAME_DAILY_REMINDER = "Daily Reminder";
    private NotificationManagerCompat notificationManager;

    ReminderNotification(Context context) {
        notificationManager = NotificationManagerCompat.from(context);
        if (!notificationManager.getNotificationChannelGroups().contains(notificationManager.getNotificationChannelGroup(GROUP_ID_DAILY_REMINDER)))
            if (SDK_INT >= O)
                notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(GROUP_ID_DAILY_REMINDER, GROUP_NAME_DAILY_REMINDER));
    }

    void showNotification(int id, String chanelName, Builder notification) {
        if (SDK_INT >= O) {
            notification.setChannelId(chanelName);
            notificationManager.createNotificationChannel(new NotificationChannel(chanelName, chanelName, IMPORTANCE_DEFAULT));
        }
        notificationManager.notify(id, notification.build());
    }
}
