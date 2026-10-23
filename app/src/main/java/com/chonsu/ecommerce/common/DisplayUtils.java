package com.chonsu.ecommerce.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class DisplayUtils {
    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();

        if (context.getTheme().resolveAttribute(androidx.appcompat.R.attr.actionBarSize, tv, true)) {
            return context.getResources().getDimensionPixelSize(tv.resourceId);
        }

        return 0;
    }

    public static int dpToPx(Context context, float dp) {
        return Math.round(
                dp * context.getResources()
                        .getDisplayMetrics().density
        );
    }

    public static int pxToDp(Context context, float px) {
        return Math.round(
                px / context.getResources()
                        .getDisplayMetrics().density
        );
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");

        return resourceId > 0
                ? context.getResources().getDimensionPixelSize(resourceId)
                : 0;
    }

    public static int getStatusBarHeightDp(Context context) {
        int px = getStatusBarHeight(context);

        return pxToDp(context, px);
    }

    public static int getNavigationBarHeight(Activity activity) {
        Window window = activity.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+
            WindowInsets insets = window.getDecorView().getRootWindowInsets();

            if (insets != null) {
                return insets.getInsets(
                        WindowInsets.Type.navigationBars()
                ).bottom;
            }

            return 0;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6–10
            View decorView = window.getDecorView();
            WindowInsets insets = decorView.getRootWindowInsets();

            if (insets != null) {
                return insets.getSystemWindowInsetBottom();
            }
        }

        // Android 5.0
        int resourceId = activity.getResources().getIdentifier(
                "navigation_bar_height",
                "dimen",
                "android"
        );

        if (resourceId > 0) {
            return activity.getResources().getDimensionPixelSize(resourceId);
        }

        return 0;
    }

    public static int getNavigationBarHeightDp(Activity activity) {
        int px = getNavigationBarHeight(activity);

        return pxToDp(activity, px);
    }

    public static void setStatusBarColor(Activity activity, int color, boolean lightIcons, boolean isOverscan) {
        Window window = activity.getWindow();
        window.setStatusBarColor(color);

        if (isOverscan) {
            window.getDecorView().setSystemUiVisibility(
                    window.getDecorView().getSystemUiVisibility()
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        } else {
            window.getDecorView().setSystemUiVisibility(
                    window.getDecorView().getSystemUiVisibility()
                            & (~View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                            & (~View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            );
        }

        WindowInsetsControllerCompat controller =
                new WindowInsetsControllerCompat(activity.getWindow(), activity.getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(!lightIcons); // false = light icons
    }

    public static void setStatusBarColorResId(Activity activity, int colorResId, boolean lightIcons, boolean isOverscan) {
        setStatusBarColor(activity, ContextCompat.getColor(activity, colorResId), lightIcons, isOverscan);
    }

    public static void setNavigationBarColor(Activity activity, int color, boolean lightIcons) {
        Window window = activity.getWindow();

        window.setNavigationBarColor(color);

        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(window, window.getDecorView());
        controller.setAppearanceLightNavigationBars(!lightIcons);
    }

    public static void setNavigationBarColorResId(Activity activity, int colorResId, boolean lightIcons) {
        setNavigationBarColor(activity, ContextCompat.getColor(activity, colorResId), lightIcons);
    }

    public static void setNavigationBarTransparent(Activity activity) {
        Window window = activity.getWindow();

        WindowCompat.setDecorFitsSystemWindows(window, false);
        window.setNavigationBarColor(Color.TRANSPARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.setNavigationBarContrastEnforced(false);
        }
    }

    public static void setStatusBarVisibility(Activity activity, boolean visible) {
        WindowInsetsControllerCompat controller =
                new WindowInsetsControllerCompat(activity.getWindow(), activity.getWindow().getDecorView());

        if (visible)
            controller.show(WindowInsetsCompat.Type.statusBars());
        else
            controller.hide(WindowInsetsCompat.Type.statusBars());
    }

    public static void setNavigationBarVisibility(Activity activity, boolean visible) {
        WindowInsetsControllerCompat controller =
                new WindowInsetsControllerCompat(activity.getWindow(), activity.getWindow().getDecorView());

        if (visible)
            controller.show(WindowInsetsCompat.Type.navigationBars());
        else
            controller.hide(WindowInsetsCompat.Type.navigationBars());
    }

    public static void setSystemBarsVisibility(Activity activity, boolean visible) {
        WindowInsetsControllerCompat controller =
                new WindowInsetsControllerCompat(activity.getWindow(),
                        activity.getWindow().getDecorView());
        if (!visible) {
            controller.hide(WindowInsetsCompat.Type.systemBars());
            controller.setSystemBarsBehavior(
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        } else {
            controller.show(WindowInsetsCompat.Type.systemBars());
        }
    }

    public static void setFullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.getWindow().setDecorFitsSystemWindows(false);

            WindowInsetsController controller = activity.getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(
                        WindowInsets.Type.statusBars() |
                                WindowInsets.Type.navigationBars()
                );
                controller.setSystemBarsBehavior(
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                );
            }
        } else {
            activity.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );

            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        }
    }
}
