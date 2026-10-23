package com.chonsu.ecommerce.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.chonsu.ecommerce.R;

public class Global {
    private static boolean isInitialized = false;
    public static Typeface font = null;

    public static void init(Activity activity) {
        if (isInitialized)
            return;

        font = Typeface.createFromAsset(activity.getAssets(), "fonts/roboto.ttf");

        isInitialized = true;
    }

    public static void applyFont(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            Typeface current = textView.getTypeface();

            int style = current != null
                    ? current.getStyle()
                    : Typeface.NORMAL;

            textView.setTypeface(font, style);
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int i = 0; i < group.getChildCount(); i++) {
                applyFont(group.getChildAt(i));
            }
        }
    }

    public static String getString(Context context, int resId) {
        return context.getApplicationContext().getString(resId);
    }
}
