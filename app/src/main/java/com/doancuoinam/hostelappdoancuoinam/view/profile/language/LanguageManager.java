package com.doancuoinam.hostelappdoancuoinam.view.profile.language;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LanguageManager {
    private static final String LANGUAGE_PREF_KEY = "language";

    public static void setLanguage(Context context, String languageCode) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(LANGUAGE_PREF_KEY, languageCode).apply();
        updateLocale(context, languageCode);
    }

    public static String getLanguage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LANGUAGE_PREF_KEY, "");
    }
    public static void applySavedLanguage(Context context) {
        String savedLanguage = getLanguage(context);
        if (!savedLanguage.isEmpty()) {
            updateLocale(context, savedLanguage);
        }
    }

    private static void updateLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.setLocale(locale);

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
