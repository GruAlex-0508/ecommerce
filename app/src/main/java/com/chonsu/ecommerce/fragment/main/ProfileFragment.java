package com.chonsu.ecommerce.fragment.main;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.adapter.ProfileMenuAdapter;
import com.chonsu.ecommerce.common.Global;
import com.chonsu.ecommerce.config.ThemeManager;
import com.chonsu.ecommerce.databinding.FragmentProfileBinding;
import com.chonsu.ecommerce.databinding.ItemProfileOrderStepBinding;
import com.chonsu.ecommerce.model.ProfileMenuItem;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements ProfileMenuAdapter.OnProfileMenuClickListener {

    private FragmentProfileBinding binding;

    public ProfileFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        Global.applyFont(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUserProfile();
        setupOrderSteps();
        setupMenu();
    }

    private void setupUserProfile() {
        binding.imgProfileAvatar.setImageResource(R.drawable.img_avatar);
        binding.tvProfileName.setText(R.string.profile_username);
        binding.tvProfilePhone.setText(R.string.profile_cid);
    }

    private void setupOrderSteps() {
        configureOrderStep(binding.stepApplying, R.drawable.ic_order_applying, R.string.profile_applying);
        configureOrderStep(binding.stepReceived, R.drawable.ic_order_received, R.string.profile_received);
        configureOrderStep(binding.stepTransfer, R.drawable.ic_order_transfer, R.string.profile_transfer);
        configureOrderStep(binding.stepCompleted, R.drawable.ic_order_completed, R.string.profile_completed);
    }

    private void configureOrderStep(ItemProfileOrderStepBinding step, @DrawableRes int iconRes, @StringRes int labelRes) {
        int primary = ThemeManager.getThemeColor(requireContext(), androidx.appcompat.R.attr.colorPrimary);

        step.imgOrderStepIcon.setImageResource(iconRes);
        ImageViewCompat.setImageTintList(step.imgOrderStepIcon, ColorStateList.valueOf(primary));
        step.tvOrderStepLabel.setText(labelRes);

        step.getRoot().setOnClickListener(v -> showMessage(getString(labelRes)));
    }

    private void setupMenu() {
        ProfileMenuAdapter adapter = new ProfileMenuAdapter(buildMenuItems(), this);

        binding.rvProfileMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvProfileMenu.setAdapter(adapter);
    }

    private List<ProfileMenuItem> buildMenuItems() {
        List<ProfileMenuItem> items = new ArrayList<>();

        items.add(ProfileMenuItem.header(R.string.profile_section_account));
        items.add(ProfileMenuItem.item(ProfileMenuItem.ID_SETTINGS, R.string.profile_menu_settings, R.drawable.ic_setting, 0));
        items.add(ProfileMenuItem.item(ProfileMenuItem.ID_NOTIFICATION, R.string.profile_menu_notification, R.drawable.ic_notification, 0));
        items.add(ProfileMenuItem.item(ProfileMenuItem.ID_ORDER_HISTORY, R.string.profile_menu_order_history, R.drawable.ic_history, 0));

        items.add(ProfileMenuItem.header(R.string.profile_section_support));
        items.add(ProfileMenuItem.item(ProfileMenuItem.ID_PRIVACY, R.string.profile_menu_privacy, R.drawable.ic_privacy, 0));
        items.add(ProfileMenuItem.item(ProfileMenuItem.ID_ADDRESS, R.string.profile_menu_address, R.drawable.ic_location, 0));
        items.add(ProfileMenuItem.item(ProfileMenuItem.ID_LOGOUT, R.string.profile_menu_logout, R.drawable.ic_sign_out, 0));

        return items;
    }

    @Override
    public void onProfileMenuClick(ProfileMenuItem item) {
        showMessage(getString(R.string.profile_menu_clicked, getString(item.getTitleRes())));
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