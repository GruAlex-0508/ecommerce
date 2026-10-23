package com.chonsu.ecommerce.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.chonsu.ecommerce.common.Global;
import com.chonsu.ecommerce.databinding.FragmentPromotionBinding;

public class PromotionFragment extends Fragment {

    private FragmentPromotionBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentPromotionBinding.inflate(inflater, container, false);
        Global.applyFont(binding.getRoot());
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}