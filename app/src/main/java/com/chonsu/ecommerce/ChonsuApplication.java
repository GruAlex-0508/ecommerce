package com.chonsu.ecommerce;

import android.app.Application;

import com.chonsu.ecommerce.config.ThemeManager;

public class ChonsuApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ThemeManager.applyTheme(this);
    }
}
