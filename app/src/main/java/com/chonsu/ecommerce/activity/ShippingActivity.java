package com.chonsu.ecommerce.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.common.DisplayUtils;
import com.chonsu.ecommerce.common.Global;
import com.chonsu.ecommerce.config.ThemeManager;
import com.chonsu.ecommerce.databinding.ActivityShippingBinding;
import com.chonsu.ecommerce.fragment.shipping.AddressFragment;
import com.chonsu.ecommerce.fragment.shipping.CheckoutFragment;
import com.chonsu.ecommerce.fragment.shipping.TypeFragment;
import com.chonsu.ecommerce.model.ShippingAddress;

public class ShippingActivity extends BaseActivity implements View.OnClickListener {

    private ActivityShippingBinding binding;
    private int position = 1;
    private ShippingAddress selectedShippingAddress = null;

    public void setPosition(int position) {
        this.position = position;
    }

    private FragmentTransaction transaction;

    public String getShippingAddressName() {
        return shippingAddressName;
    }

    public void setShippingAddressName(String shippingAddressName) {
        this.shippingAddressName = shippingAddressName;
    }

    public String getShippingLocation() {
        return shippingLocation;
    }

    public void setShippingLocation(String shippingLocation) {
        this.shippingLocation = shippingLocation;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    private String shippingAddressName = null;
    private String shippingLocation = null;
    private String shippingType = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShippingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment shippingAddress = fragmentManager.findFragmentById(R.id.shipping_frag);
        if (shippingAddress != null)
            fragmentManager.beginTransaction().show(shippingAddress).commit();

        DisplayUtils.setStatusBarColor(this, Color.TRANSPARENT, false, true);
        DisplayUtils.setNavigationBarColor(this, ThemeManager.getThemeColor(this, R.attr.bgGradient2), true);

        binding.btnContinue.setOnClickListener(this);
        binding.shippingBack.setOnClickListener(v -> {
            finish();
        });
        binding.shippingForward.setOnClickListener(this);

        shippingType = Global.getString(this, R.string.shipping_type_regular);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // Transition of Fragments in Activity by using Activity's button ==============================

    @Override
    public void onClick(View view) {
        int id = view.getId();

        transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        if (id == R.id.btn_continue && position == 1) {
            binding.shippingTitle.setText(R.string.shipping_type);
            transaction.replace(R.id.shipping_frag, new TypeFragment()).commit();
            position = 2;
        } else if (id == R.id.btn_continue && position == 2) {
            binding.shippingTitle.setText(R.string.shipping_checkout);
            binding.btnTitle.setText(R.string.shipping_apply);
            transaction.replace(R.id.shipping_frag, new CheckoutFragment()).commit();
            setForwardDisable();
            position = 3;
        } else if (id == R.id.shipping_back && position == 2) {
            binding.shippingTitle.setText(R.string.shipping_address);
            transaction.replace(R.id.shipping_frag, new AddressFragment()).commit();
            position = 1;
        } else if (id == R.id.shipping_back && position == 3) {
            binding.shippingTitle.setText(R.string.shipping_type);
            binding.btnTitle.setText(R.string.shipping_btn_continue);
            setForwardVisible();
            transaction.replace(R.id.shipping_frag, new TypeFragment()).commit();
            position = 2;
        } else if (id == R.id.shipping_forward && position == 1) {
            binding.shippingTitle.setText(R.string.shipping_type);
            transaction.replace(R.id.shipping_frag, new TypeFragment()).commit();
            position = 2;
        } else if (id == R.id.shipping_forward && position == 2) {
            binding.shippingTitle.setText(R.string.shipping_checkout);
            binding.btnTitle.setText(R.string.shipping_apply);
            setForwardDisable();
            transaction.replace(R.id.shipping_frag, new CheckoutFragment()).commit();
            position = 3;
        }
    }

    @Override
    protected void handleOnBackPressed() {
        transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        if (position == 2) {
            binding.shippingTitle.setText(R.string.shipping_address);
            transaction.replace(R.id.shipping_frag, new AddressFragment()).commit();
            position = 1;
        } else if (position == 3) {
            binding.shippingTitle.setText(R.string.shipping_address);
            transaction.replace(R.id.shipping_frag, new AddressFragment()).commit();
            position = 2;
            setForwardVisible();
        } else {
            super.handleOnBackPressed();
        }
    }

    public void setShippingInfo(int shippingTitle, int btnTitle) {
        binding.shippingTitle.setText(shippingTitle);
        binding.btnTitle.setText(btnTitle);
    }

    public void setSelectedShippingAddress(ShippingAddress shippingAddress) {
        selectedShippingAddress = shippingAddress;
    }

    public ShippingAddress getSelectedShippingAddress() {
        return selectedShippingAddress;
    }

    public void setForwardVisible() {
        binding.shippingForward.setVisibility(View.VISIBLE);
    }

    public void setForwardDisable() {
        binding.shippingForward.setVisibility(View.GONE);
    }
}
