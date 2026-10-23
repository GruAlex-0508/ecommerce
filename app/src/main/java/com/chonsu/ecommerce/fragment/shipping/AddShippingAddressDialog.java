package com.chonsu.ecommerce.fragment.shipping;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.chonsu.ecommerce.R;
import com.google.android.material.button.MaterialButton;

public class AddShippingAddressDialog extends Dialog {

    private final String REQUIRE_TEXT = "Please input all info";

    public interface OnAddressAddedListener {
        void onAddressAdded(String title, String address);
    }

    private EditText etShippingTitle;
    private EditText etShippingAddress;

    private MaterialButton btnShippingNo;
    private MaterialButton btnShippingYes;

    private final OnAddressAddedListener listener;

    public AddShippingAddressDialog(@NonNull Context context, OnAddressAddedListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_shipping_address);

        initializeViews();
        setupButtons();
        setupWindow();
    }

    private void initializeViews() {
        etShippingTitle = findViewById(R.id.etShippingTitle);
        etShippingAddress = findViewById(R.id.etShippingAddress);

        btnShippingNo = findViewById(R.id.btnShippingNo);
        btnShippingYes = findViewById(R.id.btnShippingYes);
    }

    private void setupButtons() {
        btnShippingNo.setOnClickListener(v -> dismiss());
        btnShippingYes.setOnClickListener(v -> saveAddress());
    }

    private void saveAddress() {
        String title = etShippingTitle.getText().toString().trim();
        String address = etShippingAddress.getText().toString().trim();

        if (title.isEmpty()) {
            etShippingTitle.setError(REQUIRE_TEXT);
            etShippingTitle.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            etShippingAddress.setError(REQUIRE_TEXT);
            etShippingAddress.requestFocus();
            return;
        }

        if (listener != null) {
            listener.onAddressAdded(title, address);
        }

        dismiss();
    }

    private void setupWindow() {
        Window window = getWindow();

        if (window == null) return;

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = window.getAttributes();

        params.dimAmount = 0.45f;
        window.setAttributes(params);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();

        if (window == null) return;

        WindowManager.LayoutParams params = window.getAttributes();

        params.width = (int)(getContext().getResources().getDisplayMetrics().widthPixels * 0.8f);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        params.dimAmount = 0.45f;
        window.setAttributes(params);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
