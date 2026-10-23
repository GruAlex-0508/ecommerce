package com.chonsu.ecommerce.fragment.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.activity.CartActivity;
import com.chonsu.ecommerce.activity.FeedBackActivity;
import com.chonsu.ecommerce.activity.MainActivity;
import com.chonsu.ecommerce.activity.ViewCategoryActivity;
import com.chonsu.ecommerce.adapter.LandingCategoryAdapter;
import com.chonsu.ecommerce.adapter.LandingCategoryButtonAdapter;
import com.chonsu.ecommerce.adapter.LandingProductAdapter;
import com.chonsu.ecommerce.adapter.LandingProductGridAdapter;
import com.chonsu.ecommerce.data.CategoryDataProvider;
import com.chonsu.ecommerce.databinding.FragmentLandingBinding;
import com.chonsu.ecommerce.model.Category;
import com.chonsu.ecommerce.model.LargeCategory;
import com.chonsu.ecommerce.model.Product;
import com.chonsu.ecommerce.model.ProductGrid;
import com.chonsu.ecommerce.model.SmallCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eightbitlab.com.blurview.BlurView;

public class LandingFragment extends Fragment {
    private FragmentLandingBinding binding;
    private LandingCategoryButtonAdapter smallCategoryAdapter;
    private LandingCategoryAdapter circularCategoryAdapter;
    private List<LargeCategory> largeCategories;
    private int selectedLargePosition = 0;
    private int selectedSmallPosition = 0;
    private final int selectedColor = Color.parseColor("#79543D");
    private final int unselectedColor = Color.parseColor("#DDDDDD");

    private LandingProductAdapter carouselProductAdapter;

    private LandingProductAdapter carouselRecommendProductAdapter;
    private List<Product> carouselProducts;
    private List<Product> carouselRecommendProducts;
    private GridLayoutManager gridLayoutManager;
    private List<ProductGrid> productGridList;
    private LandingProductGridAdapter productGridAdapter;

    public LandingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLandingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createSampleCategoryData();
        CategoryDataProvider.setLargeCategories(largeCategories);

        initCategoryRecyclerViews();
        setupCategoryAdapters();
        setupMainCategoryClickListeners();

        updateMainCategorySelection();
        updateSmallCategories();

        initProductRecyclerViews();
        createSampleProducts();
        setupProductAdapters();
        initClickListeners();

        createSampleProductGrid();
        initProductGridRecyclerViews();
        setupProductGridAdapters();


        binding.btnLandingClear.setVisibility(View.GONE);
        binding.edtLandingSearch.addTextChangedListener(new TextWatcher() {
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
            binding.edtLandingSearch.setText("");
            binding.edtLandingSearch.requestFocus();
        });

        setupSearchBarBlur();
    }

    private void initCategoryRecyclerViews() {
        binding.rvSmallCategories.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvCategoryCircles.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvSmallCategories.setNestedScrollingEnabled(false);
        binding.rvCategoryCircles.setNestedScrollingEnabled(false);
    }

    private void initProductRecyclerViews() {
        binding.rvProductCarousel.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        binding.rvRecommendProductCarousel.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        binding.rvProductCarousel.setNestedScrollingEnabled(false);
        binding.rvRecommendProductCarousel.setNestedScrollingEnabled(false);

        binding.rvProductCarousel.setVisibility(View.VISIBLE);
        binding.rvProductCarousel.setHasFixedSize(false);
        binding.rvRecommendProductCarousel.setVisibility(View.VISIBLE);
        binding.rvRecommendProductCarousel.setHasFixedSize(false);
    }

    private void initProductGridRecyclerViews() {
        gridLayoutManager = new GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false);
        binding.rvProductGrid.setLayoutManager(gridLayoutManager);
    }

    private void createSampleCategoryData() {

        largeCategories = new ArrayList<>();

        List<SmallCategory> furnitureSmallCategories = new ArrayList<>();
        furnitureSmallCategories.add(
                new SmallCategory("living_room", "Living Room", Arrays.asList(
                        new Category("sofas", "Sofas", R.drawable.category),
                        new Category("chairs", "Chairs", R.drawable.category),
                        new Category("tables", "Tables", R.drawable.category),
                        new Category("tv_stands", "TV Stands", R.drawable.category),
                        new Category("shelves", "Shelves", R.drawable.category))
                )
        );
        furnitureSmallCategories.add(
                new SmallCategory("bedroom", "Bedroom", Arrays.asList(
                        new Category("beds", "Beds", R.drawable.category),
                        new Category("wardrobes", "Wardrobes", R.drawable.category),
                        new Category("nightstands", "Nightstands", R.drawable.category))
                )
        );
        furnitureSmallCategories.add(
                new SmallCategory("office", "Office", Arrays.asList(
                        new Category("office_chairs", "Office Chairs", R.drawable.category),
                        new Category("office_desks", "Desks", R.drawable.category),
                        new Category("office_shelves", "Shelves", R.drawable.category))
                )
        );
        largeCategories.add(new LargeCategory("furniture", "Furniture", R.drawable.ic_landing_cart, furnitureSmallCategories));

        List<SmallCategory> carItemCategories = new ArrayList<>();
        carItemCategories.add(
                new SmallCategory("wheel", "Wheel", Arrays.asList(
                        new Category("camry", "CAMRY", R.drawable.category),
                        new Category("crown", "CROWN", R.drawable.category),
                        new Category("lexus", "LEXUS", R.drawable.category))
                )
        );
        carItemCategories.add(
                new SmallCategory("handler", "Handler", Arrays.asList(
                        new Category("crown", "CROWN", R.drawable.category),
                        new Category("camry", "CAMRY", R.drawable.category),
                        new Category("lexus", "LEXUS", R.drawable.category))
                )
        );
        carItemCategories.add(
                new SmallCategory("handler", "Handler", Arrays.asList(
                        new Category("crown", "CROWN", R.drawable.category),
                        new Category("camry", "CAMRY", R.drawable.category),
                        new Category("lexus", "LEXUS", R.drawable.category))
                )
        );
        carItemCategories.add(
                new SmallCategory("handler", "Handler", Arrays.asList(
                        new Category("crown", "CROWN", R.drawable.category),
                        new Category("camry", "CAMRY", R.drawable.category),
                        new Category("lexus", "LEXUS", R.drawable.category))
                )
        );
        carItemCategories.add(
                new SmallCategory("handler", "Handler", Arrays.asList(
                        new Category("crown", "CROWN", R.drawable.category),
                        new Category("camry", "CAMRY", R.drawable.category),
                        new Category("lexus", "LEXUS", R.drawable.category))
                )
        );
        carItemCategories.add(
                new SmallCategory("handler", "Handler", Arrays.asList(
                        new Category("crown", "CROWN", R.drawable.category),
                        new Category("camry", "CAMRY", R.drawable.category),
                        new Category("lexus", "LEXUS", R.drawable.category))
                )
        );
        carItemCategories.add(
                new SmallCategory("handler", "Handler", Arrays.asList(
                        new Category("crown", "CROWN", R.drawable.category),
                        new Category("camry", "CAMRY", R.drawable.category),
                        new Category("lexus", "LEXUS", R.drawable.category))
                )
        );
        carItemCategories.add(
                new SmallCategory("handler", "Handler", Arrays.asList(
                        new Category("crown", "CROWN", R.drawable.category),
                        new Category("camry", "CAMRY", R.drawable.category),
                        new Category("lexus", "LEXUS", R.drawable.category))
                )
        );
        carItemCategories.add(
                new SmallCategory("handler", "Handler", Arrays.asList(
                        new Category("crown", "CROWN", R.drawable.category),
                        new Category("camry", "CAMRY", R.drawable.category),
                        new Category("lexus", "LEXUS", R.drawable.category))
                )
        );
        largeCategories.add(new LargeCategory("car", "Car Items", R.drawable.ic_landing_cart, carItemCategories));
    }

    private void createSampleProducts() {
        carouselProducts = new ArrayList<>();

        carouselProducts.add(
                new Product("P0001", "Product1", "Furniture product", 360, R.drawable.product1, true)
        );
        carouselProducts.add(
                new Product("P0002", "Product2", "Furniture product", 360, R.drawable.product2, false)
        );
        carouselProducts.add(
                new Product("P0003", "Product3", "Furniture product", 360, R.drawable.product3, false)
        );
        carouselProducts.add(
                new Product("P0004", "Product4", "Furniture product", 360, R.drawable.product1, true)
        );
        carouselProducts.add(
                new Product("P0005", "Product5", "Furniture product", 360, R.drawable.product2, false)
        );
        carouselProducts.add(
                new Product("P0006", "Product6", "Furniture product", 360, R.drawable.product1, true)
        );
        carouselProducts.add(
                new Product("P0007", "Product7", "Furniture product", 360, R.drawable.product3, true)
        );
        carouselProducts.add(
                new Product("P0008", "Product8", "Furniture product", 360, R.drawable.product2, false)
        );
        carouselProducts.add(
                new Product("P0009", "Product9", "Furniture product", 360, R.drawable.product3, true)
        );
        carouselProducts.add(
                new Product("P0010", "Product10", "Furniture product", 360, R.drawable.product1, false)
        );
        carouselProducts.add(
                new Product("P0011", "Product11", "Furniture product", 360, R.drawable.product3, true)
        );
        carouselProducts.add(
                new Product("P0012", "Product12", "Furniture product", 360, R.drawable.product2, false)
        );
        carouselProducts.add(
                new Product("P0013", "Product13", "Furniture product", 360, R.drawable.product3, false)
        );
        carouselProducts.add(
                new Product("P0014", "Product14", "Furniture product", 360, R.drawable.product1, true)
        );

        carouselRecommendProducts = new ArrayList<>();

        carouselRecommendProducts.add(
                new Product("P0001", "Product1", "Furniture product", 360, R.drawable.product1, true)
        );
        carouselRecommendProducts.add(
                new Product("P0002", "Product2", "Furniture product", 360, R.drawable.product2, false)
        );
        carouselRecommendProducts.add(
                new Product("P0003", "Product3", "Furniture product", 360, R.drawable.product3, false)
        );
        carouselRecommendProducts.add(
                new Product("P0004", "Product4", "Furniture product", 360, R.drawable.product1, true)
        );
        carouselRecommendProducts.add(
                new Product("P0005", "Product5", "Furniture product", 360, R.drawable.product2, false)
        );
        carouselRecommendProducts.add(
                new Product("P0006", "Product6", "Furniture product", 360, R.drawable.product1, true)
        );
        carouselRecommendProducts.add(
                new Product("P0007", "Product7", "Furniture product", 360, R.drawable.product3, true)
        );
        carouselRecommendProducts.add(
                new Product("P0008", "Product8", "Furniture product", 360, R.drawable.product2, false)
        );
        carouselRecommendProducts.add(
                new Product("P0009", "Product9", "Furniture product", 360, R.drawable.product3, true)
        );
        carouselRecommendProducts.add(
                new Product("P0010", "Product10", "Furniture product", 360, R.drawable.product1, false)
        );
        carouselRecommendProducts.add(
                new Product("P0011", "Product11", "Furniture product", 360, R.drawable.product3, true)
        );
        carouselRecommendProducts.add(
                new Product("P0012", "Product12", "Furniture product", 360, R.drawable.product2, false)
        );
        carouselRecommendProducts.add(
                new Product("P0013", "Product13", "Furniture product", 360, R.drawable.product3, false)
        );
        carouselRecommendProducts.add(
                new Product("P0014", "Product14", "Furniture product", 360, R.drawable.product1, true)
        );
    }

    private void createSampleProductGrid() {
        productGridList = new ArrayList<>();

        productGridList.add(new ProductGrid(1, "Chair", "Furniture", 360.00, R.drawable.landing_chair, true, false));
        productGridList.add(new ProductGrid(2, "Chair", "Furniture", 360.00, R.drawable.landing_chair, true, true));
        productGridList.add(new ProductGrid(3, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, false));
        productGridList.add(new ProductGrid(4, "Chair", "Furniture", 360.00, R.drawable.landing_chair, true, true));
        productGridList.add(new ProductGrid(5, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, false));
        productGridList.add(new ProductGrid(6, "Chair", "Furniture", 360.00, R.drawable.landing_chair, true, true));
        productGridList.add(new ProductGrid(7, "Chair", "Furniture", 360.00, R.drawable.landing_chair, true, false));
        productGridList.add(new ProductGrid(8, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, true));
        productGridList.add(new ProductGrid(9, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, true));
        productGridList.add(new ProductGrid(10, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, true));
        productGridList.add(new ProductGrid(11, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, true));
        productGridList.add(new ProductGrid(12, "Chair", "Furniture", 360.00, R.drawable.landing_chair, false, true));
    }

    private void setupCategoryAdapters() {

        smallCategoryAdapter = new LandingCategoryButtonAdapter(position -> {
            selectedSmallPosition = position;
            updateCircularCategories();
        });
        binding.rvSmallCategories.setAdapter(smallCategoryAdapter);

        circularCategoryAdapter = new LandingCategoryAdapter(this::onCircularCategorySelected);
        binding.rvCategoryCircles.setAdapter(circularCategoryAdapter);
    }

    private void setupProductAdapters() {
        carouselProductAdapter = new LandingProductAdapter(product -> {
            Toast.makeText(requireContext(), "Selected: " + product.getName(), Toast.LENGTH_SHORT).show();
        });
        binding.rvProductCarousel.setAdapter(carouselProductAdapter);
        carouselProductAdapter.setProducts(carouselProducts);


        carouselRecommendProductAdapter = new LandingProductAdapter(product -> {
            Toast.makeText(requireContext(), "Selected: " + product.getName(), Toast.LENGTH_SHORT).show();
        });
        binding.rvRecommendProductCarousel.setAdapter(carouselRecommendProductAdapter);
        carouselRecommendProductAdapter.setProducts(carouselRecommendProducts);
    }

    private void setupProductGridAdapters() {
        productGridAdapter = new LandingProductGridAdapter(productGridList);
        binding.rvProductGrid.setAdapter(productGridAdapter);

        Log.d("ProductGrid", "Product count: " + productGridList.size());
    }

    private void setupMainCategoryClickListeners() {
        binding.cardFurniture.setOnClickListener(v -> {
            selectedLargePosition = 0;
            selectedSmallPosition = 0;
            updateMainCategorySelection();
            updateSmallCategories();
        });

        binding.cardCarItem.setOnClickListener(v -> {
            selectedLargePosition = 1;
            selectedSmallPosition = 0;
            updateMainCategorySelection();
            updateSmallCategories();
        });
    }

    private void updateMainCategorySelection() {

        if (binding == null) return;

        binding.viewFurnitureOverlay.setVisibility(selectedLargePosition == 1 ? View.GONE : View.VISIBLE);
        binding.viewCarItemOverlay.setVisibility(selectedLargePosition == 1 ? View.VISIBLE : View.GONE);
        binding.cardFurniture.setStrokeWidth(selectedLargePosition == 0 ? 0 : 4);
        binding.cardCarItem.setStrokeWidth(selectedLargePosition == 0 ? 4 : 0);
    }

    private void updateSmallCategories() {
        if (largeCategories == null || largeCategories.isEmpty()) return;
        if (selectedLargePosition < 0 || selectedLargePosition >= largeCategories.size()) return;

        LargeCategory selectedLarge = largeCategories.get(selectedLargePosition);
        List<SmallCategory> smallCategories = selectedLarge.getSmallCategories();
        List<String> smallNames = new ArrayList<>();

        if (smallCategories != null) {
            for (SmallCategory category : smallCategories) {
                smallNames.add(category.getName());
            }
        }

        smallCategoryAdapter.setCategories(smallNames);
        selectedSmallPosition = 0;
        smallCategoryAdapter.setSelectedPosition(selectedSmallPosition);
        updateCircularCategories();
    }

    private void updateCircularCategories() {

        if (largeCategories == null || largeCategories.isEmpty()) return;

        LargeCategory selectedLarge = largeCategories.get(selectedLargePosition);
        List<SmallCategory> smallCategories = selectedLarge.getSmallCategories();

        if (smallCategories == null || smallCategories.isEmpty()) {
            circularCategoryAdapter.setCategories(new ArrayList<>());
            return;
        }

        if (selectedSmallPosition < 0 || selectedSmallPosition >= smallCategories.size()) return;

        SmallCategory selectedSmall = smallCategories.get(selectedSmallPosition);
        circularCategoryAdapter.setCategories(selectedSmall.getCategories());
        smallCategoryAdapter.setSelectedPosition(selectedSmallPosition);
    }

    private void onCircularCategorySelected(Category category) {
        if (category == null) return;
        if (largeCategories == null || largeCategories.isEmpty()) return;

        if (selectedLargePosition < 0 || selectedLargePosition >= largeCategories.size()) return;
        LargeCategory selectedLargeCategory = largeCategories.get(selectedLargePosition);

        if (selectedLargeCategory == null) return;
        List<SmallCategory> smallCategories = selectedLargeCategory.getSmallCategories();

        if (smallCategories == null || smallCategories.isEmpty()) return;
        if (selectedSmallPosition < 0 || selectedSmallPosition >= smallCategories.size()) return;

        SmallCategory selectedSmallCategory = smallCategories.get(selectedSmallPosition);

        openViewCategoryActivity(selectedLargeCategory.getId(), selectedSmallCategory.getId(), category.getId(), category.getName());
    }

    private void openViewCategoryActivity(String largeCategoryId, String smallCategoryId, String categoryId, String categoryName) {
        Intent intent = new Intent(requireContext(), ViewCategoryActivity.class);
        intent.putExtra("largeCategoryId", largeCategoryId);
        intent.putExtra("smallCategoryId", smallCategoryId);
        intent.putExtra("categoryId", categoryId);
        intent.putExtra("categoryName", categoryName);

        startActivity(intent);
    }

    private void initClickListeners() {
        binding.btnLandingCart.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CartActivity.class);
            startActivity(intent);
        });
        binding.btnLandingCamera.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FeedBackActivity.class);
            getActivity().startActivity(intent);
        });
        binding.btnSeeAll.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "See All Selected", Toast.LENGTH_SHORT).show();
        });
        binding.btnSeeAll2.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "See All Selected", Toast.LENGTH_SHORT).show();
        });

        binding.btnLandingMenu.setOnClickListener(v -> {
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

    }

    public void updateMenuIcon(boolean isDrawerOpen) {
        if (binding == null) return;

        View menuButton = binding.btnLandingMenu;
        int newIcon = isDrawerOpen ? R.drawable.ic_back_arrow : R.drawable.ic_landing_menu;

        menuButton.animate().rotation(90f).alpha(0f).setDuration(150).withEndAction(() -> {
            if (binding == null) return;
            binding.btnLandingMenu.setImageResource(newIcon);
            binding.btnLandingMenu.setRotation(-90f);
            binding.btnLandingMenu.animate().rotation(0f).alpha(1f).setDuration(200).start();
        }).start();
    }

    private void setupSearchBarBlur() {
        if (!isAdded() || binding == null) return;

        BlurView blurView = binding.blurSearchBackground;

        View decorView = requireActivity().getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);

        Drawable windowBackground = decorView.getBackground();

        blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        blurView.setClipToOutline(true);

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(18f)
                .setBlurAutoUpdate(true)
                .setOverlayColor(Color.parseColor("#55FFFFFF"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}