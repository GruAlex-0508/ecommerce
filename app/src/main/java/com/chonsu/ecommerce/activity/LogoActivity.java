package com.chonsu.ecommerce.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.chonsu.ecommerce.common.DisplayUtils;
import com.chonsu.ecommerce.databinding.ActivityLogoBinding;

import java.util.ArrayList;
import java.util.List;

public class LogoActivity extends BaseActivity {
    private final int PERMISSION_REQUEST_CODE = 100;
    private final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.CALL_PHONE
    };
    private ActivityLogoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestRequiredPermissions();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            permissionsGranted();
        }
    }

    @Override
    protected void onGlobalLayout() {
        super.onGlobalLayout();

        DisplayUtils.setFullScreen(this);
    }

    @Override
    protected void handleOnBackPressed() {
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    private void requestRequiredPermissions() {

        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        List<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

                permissionsToRequest.add(permission);
            }
        }

        if (!permissionsToRequest.isEmpty()) {

            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    PERMISSION_REQUEST_CODE);

        } else {
            permissionsGranted();
        }
    }

    private void permissionsGranted() {
        new Handler().postDelayed(this::gotoMainActivity, 300);
    }
}
