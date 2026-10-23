package com.chonsu.ecommerce.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.activity.CartActivity;
import com.chonsu.ecommerce.activity.MainActivity;
import com.chonsu.ecommerce.activity.ViewCategoryActivity;
import com.chonsu.ecommerce.adapter.CategoryAdapter;
import com.chonsu.ecommerce.data.CategoryDataProvider;
import com.chonsu.ecommerce.databinding.FragmentCategoryBinding;
import com.chonsu.ecommerce.model.Category;
import com.chonsu.ecommerce.model.LargeCategory;
import com.chonsu.ecommerce.model.SmallCategory;
import com.chonsu.ecommerce.common.Global;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private RecyclerView rvMainCategories;

    private TextView tvFurniture;
    private TextView tvCarItem;

    private ImageButton btnCategoryMenu;
    private ImageButton btnCategoryCart;
    private ImageButton btnCategoryNews;

    private CategoryAdapter categoryAdapter;

    private List<LargeCategory> largeCategories;

    private LargeCategory furnitureCategory;
    private LargeCategory carItemCategory;

    private String selectedLargeCategoryId = null;

    private final String FURNITURE_STRING = "furniture";
    private final String CAR_STRING = "car items";

    public CategoryFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        Global.applyFont(binding.getRoot());
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        loadSharedCategoryData();
        findLargeCategories();
        setupCategoryRecyclerView();
        setupLargeCategoryButtons();
        setupHeaderButtons();
        selectFurniture();
    }

    private void initializeViews(View view) {
        rvMainCategories = view.findViewById(R.id.rvMainCategories);
        tvFurniture = view.findViewById(R.id.tvFurniture);
        tvCarItem = view.findViewById(R.id.tvCarItem);

        btnCategoryMenu = view.findViewById(R.id.btn_category_menu);
        btnCategoryCart = view.findViewById(R.id.btn_category_cart);
        btnCategoryNews = view.findViewById(R.id.btn_category_search);
    }

    private void loadSharedCategoryData() {
        largeCategories = CategoryDataProvider.getLargeCategories();
    }

    private void findLargeCategories() {
        furnitureCategory = null;
        carItemCategory = null;

        for (LargeCategory largeCategory : largeCategories) {
            if (largeCategory == null || largeCategory.getName() == null) continue;

            String name = largeCategory.getName().trim().toLowerCase(Locale.ROOT);

            if (name.equals(FURNITURE_STRING)) {
                furnitureCategory = largeCategory;
            } else if (name.equals(CAR_STRING)) carItemCategory = largeCategory;
        }
    }

    private void setupCategoryRecyclerView() {
        rvMainCategories.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        rvMainCategories.setNestedScrollingEnabled(true);
        categoryAdapter = new CategoryAdapter(new ArrayList<>(), this::onCategorySelected);

        rvMainCategories.setAdapter(categoryAdapter);
    }

    private void setupLargeCategoryButtons() {
        tvFurniture.setOnClickListener( v -> {
            selectFurniture();
        });

        tvCarItem.setOnClickListener(v -> {
            selectCarItem();
        });
    }

    private void selectFurniture() {
        if (furnitureCategory == null) {
            showCategoryNotFound(FURNITURE_STRING);
            return;
        }
        selectedLargeCategoryId = furnitureCategory.getId();
        updateLargeCategorySelection(true);
        showSmallCategories(furnitureCategory);
    }

    private void selectCarItem() {
        if (carItemCategory == null) {
            showCategoryNotFound(CAR_STRING);
            return;
        }
        selectedLargeCategoryId = carItemCategory.getId();
        updateLargeCategorySelection(false);
        showSmallCategories(carItemCategory);
    }

    private void updateLargeCategorySelection(boolean furnitureSelected) {
        tvFurniture.setBackgroundResource(furnitureSelected ? R.drawable.bg_large_category_selected : R.drawable.bg_large_category_unselected);
        tvCarItem.setBackgroundResource(furnitureSelected ? R.drawable.bg_large_category_unselected : R.drawable.bg_large_category_selected);

        tvFurniture.setTextColor(ContextCompat.getColor(requireContext(), furnitureSelected ? R.color.white : R.color.building_primary));
        tvCarItem.setTextColor(ContextCompat.getColor(requireContext(), furnitureSelected ? R.color.building_primary : R.color.white));
    }

    private void showSmallCategories(LargeCategory largeCategory) {
        if (largeCategory == null || largeCategory.getSmallCategories() == null) {
            categoryAdapter.setCategories(new ArrayList<>());
            return;
        }

        List<SmallCategory> smallCategories = largeCategory.getSmallCategories();

        categoryAdapter.setCategories(smallCategories == null ? new ArrayList<>() : smallCategories);
        rvMainCategories.scrollToPosition(0);
    }

    private void onCategorySelected(SmallCategory smallCategory, Category category) {
        if (smallCategory == null || category == null) return;

        if (selectedLargeCategoryId == null || selectedLargeCategoryId.isEmpty()) return;

        openViewCategoryActivity(selectedLargeCategoryId, smallCategory.getId(), category.getId(), category.getName());
    }

    private void openViewCategoryActivity(String largeCategoryId, String smallCategoryId, String categoryId, String categoryName) {
        Intent intent = new Intent(requireContext(), ViewCategoryActivity.class);

        intent.putExtra("largeCategoryId", largeCategoryId);
        intent.putExtra("smallCategoryId", smallCategoryId);
        intent.putExtra("categoryId", categoryId);
        intent.putExtra("categoryName", categoryName);

        startActivity(intent);
    }

    private void showCategoryNotFound(String categoryName) {
        Toast.makeText(requireContext(), categoryName + R.string.not_found, Toast.LENGTH_LONG).show();
    }

    private void setupHeaderButtons() {
        btnCategoryMenu.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                MainActivity activity = (MainActivity) getActivity();

                boolean opening = !activity.isDrawerOpen();

                if (!opening) {
                    activity.closeDrawer();
                } else {
                    activity.openDrawer();
                }
            }
        });
        btnCategoryCart.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CartActivity.class);
            startActivity(intent);
        });
        btnCategoryNews.setOnClickListener(v -> {});
    }

    public void updateMenuIcon(boolean isDrawerOpen) {
        if (binding == null) return;

        View menuButton = binding.btnCategoryMenu;
        int newIcon = isDrawerOpen ? R.drawable.ic_back_arrow : R.drawable.ic_landing_menu;

        menuButton.animate().rotation(90f).alpha(0f).setDuration(150).withEndAction(() -> {
            if (binding == null) return;
            binding.btnCategoryMenu.setImageResource(newIcon);
            ImageViewCompat.setImageTintList(btnCategoryMenu, ContextCompat.getColorStateList(requireContext(), R.color.building_primary));
            binding.btnCategoryMenu.setRotation(-90f);
            binding.btnCategoryMenu.animate().rotation(0f).alpha(1f).setDuration(200).start();
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshCategoryData();
    }

    private void refreshCategoryData() {
        if (categoryAdapter == null) return;

        largeCategories = CategoryDataProvider.getLargeCategories();
        findLargeCategories();
        if (selectedLargeCategoryId != null && furnitureCategory != null && selectedLargeCategoryId.equals(furnitureCategory.getId())) {
            updateLargeCategorySelection(true);
            showSmallCategories(furnitureCategory);
        }
        else if (selectedLargeCategoryId != null && carItemCategory != null && selectedLargeCategoryId.equals(carItemCategory.getId())) {
            updateLargeCategorySelection(false);
            showSmallCategories(carItemCategory);
        }
        else {
            selectFurniture();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}