package com.chonsu.ecommerce.fragment.main;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chonsu.ecommerce.databinding.FragmentProfileBinding;
import com.chonsu.ecommerce.databinding.ItemProfileMenuBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private final int brown = Color.rgb(121, 84, 61);

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupProfile();
        setupMenuRows();
        setupOrderSteps();
    }

    private void setupProfile() {
        binding.tvProfileName.setText("巴阿阿");
        binding.tvProfilePhone.setText("13132093406");
    }

    private void setupMenuRows() {
        configureRow(binding.rowSettings, "Settings", android.R.drawable.ic_menu_preferences, Color.rgb(121, 84, 61), "Settings clicked");

        configureRow(binding.rowNotification, "Notification", android.R.drawable.ic_popup_reminder, Color.rgb(17, 153, 165), "Notification clicked");

        configureRow(binding.rowOrderHistory, "Order History", android.R.drawable.ic_menu_recent_history, Color.rgb(255, 152, 0), "Order History clicked");

        configureRow(binding.rowPrivacy, "Privacy & Policy", android.R.drawable.ic_lock_lock, Color.rgb(3, 150, 220), "Privacy & Policy clicked");

        configureRow(binding.rowAddress, "Shipping Address", android.R.drawable.ic_menu_mylocation, Color.rgb(121, 84, 61), "Shipping Address clicked");

        configureRow(binding.rowLogout, "Log Out", android.R.drawable.ic_lock_power_off, Color.rgb(244, 67, 54), "Log Out clicked");
    }

    private void configureRow(ItemProfileMenuBinding row, String title, int iconResource, int iconColor, String message) {

        row.tvMenuTitle.setText(title);
        row.imgMenuIcon.setImageResource(iconResource);
        row.imgMenuIcon.setImageTintList(ColorStateList.valueOf(iconColor));

        row.layoutMenuIcon.setBackgroundTintList(ColorStateList.valueOf(Color.argb(35, Color.red(iconColor), Color.green(iconColor), Color.blue(iconColor))));

        row.tvMenuArrow.setTextColor(brown);
        row.getRoot().setOnClickListener(v -> Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show());
    }

    private void setupOrderSteps() {
        binding.layoutOrderStep1.setOnClickListener(v -> showMessage("Waiting for payment"));

        binding.layoutOrderStep2.setOnClickListener(v -> showMessage("Processing orders"));

        binding.layoutOrderStep3.setOnClickListener(v -> showMessage("Orders in delivery"));

        binding.layoutOrderStep4.setOnClickListener(v -> showMessage("Completed orders"));
    }

    private void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
