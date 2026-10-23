package com.chonsu.ecommerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.common.Global;
import com.chonsu.ecommerce.config.ThemeManager;

public class BaseActivity extends AppCompatActivity {
    private View rootView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ThemeManager.applyTextSize(this);
        ThemeManager.applyTheme(this);

        registerBackPressedDispatcher();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.activity_back_enter, R.anim.activity_back_exit);

        rootView = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        rootView = null;
    }

    public void setContentView(int layoutId) {
        super.setContentView(layoutId);

        setOnGlobalLayoutListener();
    }

    public void setContentView(View view) {
        super.setContentView(view);

        setOnGlobalLayoutListener();
    }

    private void setOnGlobalLayoutListener() {
        rootView = findViewById(android.R.id.content);

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = this::onGlobalLayout;

    protected void onGlobalLayout() {
        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);

        Global.init(this);
        Global.applyFont(rootView);
    }

    private void registerBackPressedDispatcher() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                BaseActivity.this.handleOnBackPressed();
            }
        });
    }

    protected void handleOnBackPressed() {
        getOnBackPressedDispatcher().onBackPressed();
    }
}
