package com.e.myapplication.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import androidx.core.app.NotificationCompat.Builder;

import com.e.myapplication.MainActivity;
import com.e.myapplication.R;
import com.e.myapplication.base.BaseReceiver;

import java.util.Calendar;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.app.AlarmManager.RTC_WAKEUP;
import static android.app.PendingIntent.getActivity;
import static android.app.PendingIntent.getBroadcast;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static androidx.core.app.NotificationCompat.VISIBILITY_PRIVATE;
import static com.e.myapplication.reminder.ReleaseToday.IS_FIRST_KEY;
import static com.e.myapplication.reminder.ReleaseToday.isFirst;
import static com.e.myapplication.reminder.ReminderNotification.CHANNEL_ID_DAILY_REMINDER;
import static com.e.myapplication.reminder.ReminderNotification.CHANNEL_NAME_DAILY_REMINDER;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.getInstance;

public class Daily extends BaseReceiver {

    private static final int ID_REPEATING = 7;

    @Override
    protected void onReceive(ContextWrapper context, Intent intent) {
        new ReminderNotification(context).showNotification(
                86,
                CHANNEL_NAME_DAILY_REMINDER,
                new Builder(context, CHANNEL_ID_DAILY_REMINDER)
                        .setSmallIcon(R.drawable.ic_the_movie)
                        .setContentTitle("The Movie DB")
                        .setContentText(context.getString(R.string.greeting))
                        .setGroup(Daily.class.getName())
                        .setAutoCancel(true)
                        .setVisibility(VISIBILITY_PRIVATE)
                        .setContentIntent(getActivity(context, 0, new Intent(context, MainActivity.class), 0))
        );
    }

    public void setRepeatingAlarm(Context context, boolean active) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (active) {
            Calendar calendar = getInstance();
            calendar.set(HOUR_OF_DAY, 7);
            calendar.set(MINUTE, 0);
            calendar.set(SECOND, 0);

            if (alarmManager != null)
                alarmManager.setInexactRepeating(
                        RTC_WAKEUP, calendar.getTimeInMillis(),
                        INTERVAL_DAY,
                        getBroadcast(context, ID_REPEATING, new Intent(context, Daily.class), 0)
                );
        } else {
            PendingIntent pendingIntent = getBroadcast(context, ID_REPEATING, new Intent(context, Daily.class), 0);
            pendingIntent.cancel();
            if (alarmManager != null)
                alarmManager.cancel(pendingIntent);
        }

        if (isFirst(context))
            context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE).edit().putBoolean(IS_FIRST_KEY, false).apply();
    }
}