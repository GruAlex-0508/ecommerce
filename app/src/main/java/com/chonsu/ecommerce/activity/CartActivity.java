package com.chonsu.ecommerce.activity;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.adapter.CartAdapter;
import com.chonsu.ecommerce.common.DisplayUtils;
import com.chonsu.ecommerce.config.ThemeManager;
import com.chonsu.ecommerce.databinding.ActivityCartBinding;
import com.chonsu.ecommerce.model.CartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends BaseActivity {

    private ActivityCartBinding binding;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DisplayUtils.setStatusBarColor(this, Color.TRANSPARENT, false, true);
        DisplayUtils.setNavigationBarColor(this, ThemeManager.getThemeColor(this, R.attr.bgGradient2), true);


        setupHeader();
        setupCartRecyclerView();
        setupContinueButton();
    }

    private void setupHeader () {
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
        binding.btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShippingActivity.class);
            startActivity(intent);
        });
    }

    private void setupCartRecyclerView() {
        cartItems = new ArrayList<>();

        cartItems.add(new CartItem(R.drawable.landing_chair, "Chair", "XL", 380, 1));
        cartItems.add(new CartItem(R.drawable.landing_chair, "Chair", "XL", 380, 1));
        cartItems.add(new CartItem(R.drawable.landing_chair, "Chair", "XL", 380, 1));
        cartItems.add(new CartItem(R.drawable.landing_chair, "Chair", "XL", 380, 1));
        cartItems.add(new CartItem(R.drawable.landing_chair, "Chair", "XL", 380, 1));
        cartItems.add(new CartItem(R.drawable.landing_chair, "Chair", "XL", 380, 1));
        cartItems.add(new CartItem(R.drawable.landing_chair, "Chair", "XL", 380, 1));
        cartItems.add(new CartItem(R.drawable.landing_chair, "Chair", "XL", 380, 1));
        cartItems.add(new CartItem(R.drawable.landing_chair, "Chair", "XL", 380, 1));
        cartItems.add(new CartItem(R.drawable.landing_chair, "Chair", "XL", 380, 1));
        cartItems.add(new CartItem(R.drawable.landing_chair, "Chair", "XL", 380, 1));

        Log.d(TAG, "Cart items: " + cartItems.size());

        LinearLayoutManager layoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);

        binding.recyclerCart.setLayoutManager(layoutManager);

        cartAdapter = new CartAdapter(CartActivity.this, cartItems, new CartAdapter.CartListener() {
            @Override
            public void onQuantityChanged(CartItem item, int quantity) {}

            @Override
            public void onDeleteRequested(CartItem item) {
                showRemoveConfirmation(item);
            }
        });

        binding.recyclerCart.setAdapter(cartAdapter);
        binding.recyclerCart.setHasFixedSize(true);
        binding.recyclerCart.setNestedScrollingEnabled(true);
        binding.recyclerCart.setVisibility(View.VISIBLE);
    }

    private void setupContinueButton() {
        binding.btnContinue.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShippingActivity.class);
            startActivity(intent);
        });
    }

    private void showRemoveConfirmation(CartItem item) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_remove_cart, null);
        dialog.setContentView(dialogView);

        ImageView imgProduct = dialogView.findViewById(R.id.imgProduct);
        TextView tvProductName = dialogView.findViewById(R.id.tvProductName);
        TextView tvProductSize = dialogView.findViewById(R.id.tvProductSize);
        TextView tvProductPrice = dialogView.findViewById(R.id.tvProductPrice);

        imgProduct.setImageResource(item.getImageResId());
        tvProductName.setText(item.getName());
        tvProductSize.setText(String.format(Locale.US, "Size : %s", item.getSize()));
        tvProductPrice.setText(String.format(Locale.US, "$%.0f", item.getPrice()));

        TextView btnNo = dialogView.findViewById(R.id.btnNo);
        TextView btnYes = dialogView.findViewById(R.id.btnYes);

        btnNo.setOnClickListener(v -> {
            cartAdapter.closeSwipe(item);
            dialog.dismiss();
        });
        btnYes.setOnClickListener(v -> {
            cartAdapter.removeItem(item);
            dialog.dismiss();
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams params = dialogWindow.getAttributes();

            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            params.dimAmount = 0.45f;
            dialogWindow.setAttributes(params);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    protected void handleOnBackPressed() {
        finish();
    }
}