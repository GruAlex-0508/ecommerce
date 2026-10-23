package com.chonsu.ecommerce.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.adapter.LandingProductGridAdapter;
import com.chonsu.ecommerce.common.DisplayUtils;
import com.chonsu.ecommerce.config.ThemeManager;
import com.chonsu.ecommerce.databinding.ActivityViewCategoryBinding;
import com.chonsu.ecommerce.model.ProductGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewCategoryActivity extends BaseActivity {

    private ActivityViewCategoryBinding binding;
    private LandingProductGridAdapter productAdapter;

    private final List<ProductGrid> categoryProducts = new ArrayList<>();

    private final List<ProductGrid> filteredProducts = new ArrayList<>();
    private String largeCategoryId;
    private String smallCategoryId;
    private String categoryId;
    private String categoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityViewCategoryBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        DisplayUtils.setStatusBarColor(this, Color.TRANSPARENT, false, true);
        DisplayUtils.setNavigationBarColor(this, ThemeManager.getThemeColor(this, R.attr.bgGradient2), true);

        readCategoryData();
        setupViews();
        setupProductGrid();
        setupSearch();
        loadCategoryProducts();

        binding.btnLandingClear.setVisibility(View.GONE);
        binding.etCategorySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    binding.btnLandingClear.setVisibility(View.VISIBLE);
                } else {
                    binding.btnLandingClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.btnLandingClear.setOnClickListener(v -> {
            binding.etCategorySearch.setText("");
            binding.etCategorySearch.requestFocus();
        });
    }

    private void readCategoryData() {
        if (getIntent() == null) return;

        largeCategoryId = getIntent().getStringExtra("largeCategoryId");
        smallCategoryId = getIntent().getStringExtra("smallCategoryId");
        categoryId = getIntent().getStringExtra("categoryId");
        categoryName = getIntent().getStringExtra("categoryName");

        if (categoryName == null || categoryName.trim().isEmpty()) categoryName = "Category";
    }


    private void setupViews() {

        binding.tvCategoryHeaderTitle.setText(categoryName);

        binding.btnCategoryBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnCategoryCart.setOnClickListener(v -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });
    }


    private void setupProductGrid() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.rvCategoryProducts.setLayoutManager(layoutManager);
        productAdapter = new LandingProductGridAdapter(filteredProducts);
        binding.rvCategoryProducts.setAdapter(productAdapter);
        binding.rvCategoryProducts.setNestedScrollingEnabled(true);
    }


    private void setupSearch() {
        binding.etCategorySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterProducts(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void loadCategoryProducts() {
        categoryProducts.clear();

        categoryProducts.add(new ProductGrid(1, "Chair", "Furniture", 360.00, R.drawable.landing_chair, true, false));
        categoryProducts.add(new ProductGrid(2, "Chair", "Furniture", 360.00, R.drawable.landing_chair, true, true));
        categoryProducts.add(new ProductGrid(3, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, false));
        categoryProducts.add(new ProductGrid(4, "Chair", "Furniture", 360.00, R.drawable.landing_chair, true, true));
        categoryProducts.add(new ProductGrid(5, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, false));
        categoryProducts.add(new ProductGrid(6, "Chair", "Furniture", 360.00, R.drawable.landing_chair, true, true));
        categoryProducts.add(new ProductGrid(7, "Chair", "Furniture", 360.00, R.drawable.landing_chair, true, false));
        categoryProducts.add(new ProductGrid(8, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, true));
        categoryProducts.add(new ProductGrid(9, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, true));
        categoryProducts.add(new ProductGrid(10, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, true));
        categoryProducts.add(new ProductGrid(11, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, true));
        categoryProducts.add(new ProductGrid(12, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, true));

        filteredProducts.clear();

        filteredProducts.addAll(categoryProducts);
        updateProductCount();
        productAdapter.notifyDataSetChanged();
    }

    private void filterProducts(String query) {
        String searchText = query.trim().toLowerCase(Locale.US);
        filteredProducts.clear();

        if (searchText.isEmpty()) {
            filteredProducts.addAll(categoryProducts);
        } else {
            for (ProductGrid product : categoryProducts) {
                String name = product.getName();
                String subtitle = product.getSubtitle();

                boolean nameMatches = name != null && name.toLowerCase(Locale.US).contains(searchText);

                boolean subtitleMatches = subtitle != null && subtitle.toLowerCase(Locale.US).contains(searchText);

                if (nameMatches || subtitleMatches) filteredProducts.add(product);
            }
        }
        updateProductCount();
        productAdapter.notifyDataSetChanged();
    }

    private void updateProductCount() {
        binding.tvProductCount.setText(String.valueOf(filteredProducts.size()));
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