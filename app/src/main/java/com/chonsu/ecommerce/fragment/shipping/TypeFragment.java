package com.chonsu.ecommerce.fragment.shipping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.activity.ShippingActivity;
import com.chonsu.ecommerce.databinding.FragmentShippingTypeBinding;

public class TypeFragment extends Fragment implements View.OnClickListener {
    private FragmentShippingTypeBinding binding;
    private int selectedPosition = 1;
    private ShippingActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Safely initialize activity reference here
        activity = (ShippingActivity) requireActivity();

        if (activity.getShippingType() != null) {
            if (activity.getShippingType().equals(activity.getString(R.string.shipping_type_regular))) {
                selectedPosition = 1;
            } else if (activity.getShippingType().equals(activity.getString(R.string.shipping_type_cargo))) {
                selectedPosition = 2;
            } else if (activity.getShippingType().equals(activity.getString(R.string.shipping_type_fast))) {
                selectedPosition = 3;
            }
        }

        binding = FragmentShippingTypeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.typeCargo.setOnClickListener(this);
        binding.typeFast.setOnClickListener(this);
        binding.typeRegular.setOnClickListener(this);

        // Apply default selection
        updateSelection(selectedPosition);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.type_regular) {
            selectedPosition = 1;
        } else if (id == R.id.type_cargo) {
            selectedPosition = 2;
        } else if (id == R.id.type_fast) {
            selectedPosition = 3;
        }
        updateSelection(selectedPosition);
    }

    private void updateSelection(int position) {
        float unselectedAlpha = 0.6f;
        float selectedAlpha = 1.0f;

        binding.typeRegular.setAlpha(unselectedAlpha);
        binding.typeRegularRadio.setImageResource(R.drawable.ic_radio_unselect);
        binding.typeCargo.setAlpha(unselectedAlpha);
        binding.typeCargoRadio.setImageResource(R.drawable.ic_radio_unselect);
        binding.typeFast.setAlpha(unselectedAlpha);
        binding.typeFastRadio.setImageResource(R.drawable.ic_radio_unselect);

        if (position == 1) {
            binding.typeRegular.setAlpha(selectedAlpha);
            binding.typeRegularRadio.setImageResource(R.drawable.ic_radio_select);
        } else if (position == 2) {
            binding.typeCargo.setAlpha(selectedAlpha);
            binding.typeCargoRadio.setImageResource(R.drawable.ic_radio_select);
        } else if (position == 3) {
            binding.typeFast.setAlpha(selectedAlpha);
            binding.typeFastRadio.setImageResource(R.drawable.ic_radio_select);
        }
        setShippingType(position);
    }

    public void setShippingType(int pos) {
        if (activity == null) return;
        if (pos == 1) {
            activity.setShippingType(getString(R.string.shipping_type_regular));
        } else if (pos == 2) {
            activity.setShippingType(getString(R.string.shipping_type_cargo));
        } else if (pos == 3) {
            activity.setShippingType(getString(R.string.shipping_type_fast));
        }
    }
}
