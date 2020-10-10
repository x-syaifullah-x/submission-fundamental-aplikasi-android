package com.e.myapplication.receiver;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import com.e.myapplication.base.BaseReceiver;
import com.e.myapplication.reminder.Daily;
import com.e.myapplication.reminder.ReleaseToday;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.ACTION_BOOT_COMPLETED;
import static com.e.myapplication.ui.prefs.Settings.SettingsFragment.DAILY_REMINDER;
import static com.e.myapplication.ui.prefs.Settings.SettingsFragment.RELEASE_REMINDER;

public class BootReceiver extends BaseReceiver {

    @Override
    protected void onReceive(ContextWrapper wrapper, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ACTION_BOOT_COMPLETED)) {
                if (isReminderActive(wrapper, DAILY_REMINDER))
                    new Daily().setRepeatingAlarm(wrapper, true);
                if (isReminderActive(wrapper, RELEASE_REMINDER))
                    new ReleaseToday().setRepeatingAlarm(wrapper, true);
            }
        }
    }

    private boolean isReminderActive(Context context, String key) {
        return context.getSharedPreferences(context.getPackageName() + "_preferences", MODE_PRIVATE).getBoolean(key, false);
    }
}
