package com.chonsu.ecommerce.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatDelegate;

import com.chonsu.ecommerce.R;

public class ThemeManager {
    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;
    public static final int THEME_SYSTEM = 2;
    public static final int TEXT_NORMAL = 0;
    public static final int TEXT_LARGE = 1;
    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_THEME = "selected_theme";
    private static final String KEY_TEXT_SIZE = "selected_text_size";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void saveTheme(Context context, int theme) {
        getPrefs(context).edit().putInt(KEY_THEME, theme).apply();
    }

    public static int getTheme(Context context) {
        return getPrefs(context).getInt(KEY_THEME, THEME_SYSTEM);
    }

    public static void saveTextSize(Context context, int textSize) {
        getPrefs(context)
                .edit()
                .putInt(KEY_TEXT_SIZE, textSize)
                .apply();
    }

    public static int getTextSize(Context context) {
        return getPrefs(context).getInt(KEY_TEXT_SIZE, TEXT_NORMAL);
    }

    public static void applyTheme(Context context) {
        switch (getTheme(context)) {
            case THEME_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case THEME_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    public static void applyTextSize(Context context) {
        if (getTextSize(context) == TEXT_LARGE) {
            context.setTheme(R.style.Theme_ChonsuApp_LargeText);
        } else {
            context.setTheme(R.style.Theme_ChonsuApp);
        }
    }

    public static void toggleTheme(Context context) {
        int newTheme = 1 - ThemeManager.getTheme(context);
        ThemeManager.saveTheme(context, newTheme);
    }

    public static int getThemeColor(Context context, int attrResId) {
        TypedValue typedValue = new TypedValue();

        if (context.getTheme().resolveAttribute(attrResId, typedValue, true)) {
            return typedValue.data;
        }

        return 0;
    }

    public static int getThemeDimension(Context context, int attrResId) {
        TypedValue typedValue = new TypedValue();

        if (context.getTheme().resolveAttribute(attrResId, typedValue, true)) {
            return TypedValue.complexToDimensionPixelOffset(typedValue.data, context.getResources().getDisplayMetrics());
        }

        return 0;
    }
}
