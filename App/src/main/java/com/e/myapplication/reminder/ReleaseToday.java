package com.e.myapplication.reminder;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.e.myapplication.R;
import com.e.myapplication.base.BaseReceiver;
import com.e.myapplication.db.themoviedb.dto.ResultData;
import com.e.myapplication.ui.movie_tv.MovieTvViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.app.AlarmManager.RTC_WAKEUP;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.app.PendingIntent.getBroadcast;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.bumptech.glide.Glide.with;
import static com.e.myapplication.BuildConfig.IMAGE_URL_W185;
import static com.e.myapplication.reminder.ReminderNotification.CHANNEL_ID_RELEASE_TO_DAY_REMINDER;
import static com.e.myapplication.reminder.ReminderNotification.CHANNEL_NAME_RELEASE_TO_DAY_REMINDER;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.getInstance;
import static java.util.Locale.getDefault;

public class ReleaseToday extends BaseReceiver {

    public static final String IS_FIRST_KEY = "IS_FIRST_KEY";
    private static final int ID_REPEATING = 8;

    public static boolean isFirst(Context context) {
        return context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE).getBoolean(IS_FIRST_KEY, true);
    }

    @Override
    protected void onReceive(ContextWrapper context, Intent intent) {
        new MovieTvViewModel((Application) context.getApplicationContext())
                .getData("movie", new SimpleDateFormat("yyyy-MM-dd", getDefault()).format(getInstance().getTime()))
                .observeForever(dataModel -> {
                    for (ResultData data : dataModel.getResults()) {
                        showNotification(context, data);
                    }
                });
    }


    public void setRepeatingAlarm(Context context, boolean isActive) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (isActive) {
            Calendar calendar = getInstance();
            calendar.set(HOUR_OF_DAY, 8);
            calendar.set(MINUTE, 0);
            calendar.set(SECOND, 0);

            if (alarmManager != null)
                alarmManager.setInexactRepeating(RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL_DAY, getBroadcast(
                        context, ID_REPEATING, new Intent(context, ReleaseToday.class), FLAG_UPDATE_CURRENT
                ));
        } else {
            PendingIntent pendingIntent = getBroadcast(context, ID_REPEATING, new Intent(context, ReleaseToday.class), 0);
            pendingIntent.cancel();
            if (alarmManager != null) alarmManager.cancel(pendingIntent);
        }

        if (isFirst(context))
            context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE).edit().putBoolean(IS_FIRST_KEY, false).apply();
    }

    private void showNotification(Context context, ResultData data) {
        new Thread(() -> {
            try {
                new ReminderNotification(context).showNotification(
                        data.getId().intValue(),
                        CHANNEL_NAME_RELEASE_TO_DAY_REMINDER,
                        new NotificationCompat.Builder(context, CHANNEL_ID_RELEASE_TO_DAY_REMINDER)
                                .setSmallIcon(R.drawable.ic_setting_new_release_to_day)
                                .setContentTitle(context.getString(R.string.new_release_to_day))
                                .setContentText(data.getTitle())
                                .setLargeIcon(with(context).asBitmap().load(IMAGE_URL_W185 + data.getPosterPath()).submit().get())
                                .setAutoCancel(true)
                                .setGroupSummary(true)
                );
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
