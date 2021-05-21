package com.e.myapplication.ui.prefs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.e.myapplication.R;
import com.e.myapplication.reminder.Daily;
import com.e.myapplication.reminder.ReleaseToday;

import static android.provider.Settings.ACTION_LOCALE_SETTINGS;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static java.util.Objects.requireNonNull;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        public static final String RELEASE_REMINDER = "release_remindr";
        public static final String DAILY_REMINDER = "daily_reminder";
        public static final String KEY_LAYOUT_MODE = "LAYOUT_MODE";

        private static void mode(Preference preference) {
            preference.setOnPreferenceChangeListener((preferences, newValue) -> {
                preferences.getContext()
                        .getSharedPreferences(preference.getContext().getPackageName(), MODE_PRIVATE)
                        .edit().putInt(KEY_LAYOUT_MODE, newValue.equals("card") ? R.layout.movies_tv_card_item : R.layout.movies_tv_grid_item)
                        .apply();
                Toast.makeText(preference.getContext(), R.string.refresh, LENGTH_LONG).show();
                return true;
            });
        }

        private static void dailyReminder(Preference preference) {
            preference.setOnPreferenceChangeListener((preferences, newValue) -> {
                new Daily().setRepeatingAlarm(preferences.getContext(), ((boolean) newValue));
                toasReminder(preference.getContext(), preference.getContext().getString(R.string.notification_daily_reminder), newValue);
                return true;
            });
        }

        private static void releaseReminder(Preference preference) {
            preference.setOnPreferenceChangeListener((preferences, newValue) -> {
                new ReleaseToday().setRepeatingAlarm(preferences.getContext(), ((boolean) newValue));
                toasReminder(preference.getContext(), preference.getContext().getString(R.string.notification_release_reminder), newValue);
                return true;
            });
        }

        private static void toasReminder(Context context, String message, Object status) {
            makeText(context, message + ((boolean) status ? context.getString(R.string.on) : context.getString(R.string.off)), LENGTH_LONG).show();
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);

            Preference preference = findPreference(getString(R.string.key_language));
            requireNonNull(preference).setSummary(getResources().getConfiguration().locale.getDisplayCountry());
            preference.setOnPreferenceClickListener(preference1 -> {
                startActivity(new Intent(ACTION_LOCALE_SETTINGS));
                return false;
            });

            dailyReminder(requireNonNull(findPreference(DAILY_REMINDER)));
            releaseReminder(requireNonNull(findPreference(RELEASE_REMINDER)));
            mode(requireNonNull(findPreference("layout_mode")));
        }
    }
}