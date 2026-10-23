package com.chonsu.ecommerce.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.activity.MainActivity;
import com.chonsu.ecommerce.common.Global;
import com.chonsu.ecommerce.databinding.FragmentForeignProductBinding;

public class ForeignFragment extends Fragment implements View.OnClickListener {

    private FragmentForeignProductBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentForeignProductBinding.inflate(inflater, container, false);
        Global.applyFont(binding.getRoot());
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupClicks();
    }

    private void setupClicks() {
        binding.btnForeignMenu.setOnClickListener(this);
        binding.btnRefresh.setOnClickListener(this);
        binding.btnForeignNew.setOnClickListener(this);

        binding.cardForeignEvent1.setOnClickListener(this);
        binding.cardForeignEvent2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnForeignMenu) {
            if (getActivity() instanceof MainActivity) {
                MainActivity activity = (MainActivity) getActivity();

                boolean opening = !activity.isDrawerOpen();

                if (!opening) {
                    activity.closeDrawer();
                } else {
                    activity.openDrawer();
                }
            }
        } else if (id == R.id.btnRefresh) {
            Toast.makeText(requireContext(), "Refresh selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.btnForeignNew) {
            Toast.makeText(requireContext(), "New Button Clicked", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateMenuIcon(boolean isDrawerOpen) {
        if (binding == null) return;

        View menuButton = binding.btnForeignMenu;
        int newIcon = isDrawerOpen ? R.drawable.ic_back_arrow : R.drawable.ic_landing_menu;

        menuButton.animate().rotation(90f).alpha(0f).setDuration(150).withEndAction(() -> {
            if (binding == null) return;
            binding.btnForeignMenu.setImageResource(newIcon);
            ImageViewCompat.setImageTintList(binding.btnForeignMenu, ContextCompat.getColorStateList(requireContext(), R.color.building_primary));
            binding.btnForeignMenu.setRotation(-90f);
            binding.btnForeignMenu.animate().rotation(0f).alpha(1f).setDuration(200).start();
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}