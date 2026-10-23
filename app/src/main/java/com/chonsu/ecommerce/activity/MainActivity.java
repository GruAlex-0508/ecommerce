package com.chonsu.ecommerce.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.common.DisplayUtils;
import com.chonsu.ecommerce.config.ThemeManager;
import com.chonsu.ecommerce.databinding.ActivityMainBinding;
import com.chonsu.ecommerce.fragment.main.CategoryFragment;
import com.chonsu.ecommerce.fragment.main.ForeignFragment;
import com.chonsu.ecommerce.fragment.main.LandingFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private boolean bExit = false;

    ActivityMainBinding binding;

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayUtils.setStatusBarColor(this, Color.TRANSPARENT, false, true);
        DisplayUtils.setNavigationBarColor(this, ThemeManager.getThemeColor(this, R.attr.bgGradient2), true);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    @Override
    protected void onGlobalLayout() {
        super.onGlobalLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, 50);
    }

    @Override
    public void finish() {
        super.finish();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, 50);
    }

    @Override
    protected void handleOnBackPressed() {
        if (binding.drawerMain.isDrawerOpen(Gravity.START)) {
            binding.drawerMain.closeDrawer(Gravity.START);


            return;
        }

        if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() != R.id.LandingFragment) {
            navigateFragment(R.id.LandingFragment);
            return;
        }

        if (bExit)
            finish();
        else {
            Snackbar.make(binding.getRoot(), R.string.exit_confirm, Snackbar.LENGTH_LONG).show();

            bExit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bExit = false;
                }
            }, 2000);
        }
    }

    @Override
    public void onClick(View v) {
    }

    private void initViews() {
        initDrawer();
        initNavController();
        initBottomNavigation();
        initNavigationView();
    }

    private void initDrawer() {
        binding.drawerMain.setScrimColor(Color.TRANSPARENT);
        binding.drawerMain.setElevation(0f);
        binding.drawerMain.setDrawerElevation(0f);

        binding.drawerMain.addDrawerListener(
                new DrawerLayout.SimpleDrawerListener() {
                    @Override
                    public void onDrawerOpened(@NonNull View drawerView) {
                        updateLandingMenuIcon(true);
                    }

                    @Override
                    public void onDrawerClosed(@NonNull View drawerView) {
                        updateLandingMenuIcon(false);
                    }
                }
        );
    }

    private void initNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_main);
        navController = navHostFragment != null ? navHostFragment.getNavController() : null;

        if (navController == null)
            return;

        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            resetBottomNavigationState();
        });
    }

    private void updateLandingMenuIcon(boolean isOpen) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);

        if (fragment instanceof NavHostFragment) {
            Fragment current = ((NavHostFragment) fragment).getChildFragmentManager().getPrimaryNavigationFragment();

            if (current instanceof LandingFragment) {
                ((LandingFragment) current).updateMenuIcon((isOpen));
            }

            if (current instanceof CategoryFragment) {
                ((CategoryFragment) current).updateMenuIcon(isOpen);
            }

            if (current instanceof ForeignFragment) {
                ((ForeignFragment) current).updateMenuIcon(isOpen);
            }
        }
    }

    private void initBottomNavigation() {
        binding.btnNavLanding.setOnClickListener(v -> {
            navigateFragment(R.id.LandingFragment);
        });
        binding.btnNavCategory.setOnClickListener(v -> {
            navigateFragment(R.id.CategoryFragment);
        });
        binding.btnNavPromotion.setOnClickListener(v -> {
            navigateFragment(R.id.PromotionFragment);
        });
        binding.btnNavForeign.setOnClickListener(v -> {
            navigateFragment(R.id.ForeignFragment);
        });
        binding.btnNavProfile.setOnClickListener(v -> {
            navigateFragment(R.id.ProfileFragment);
        });

        resetBottomNavigationState();
    }

    private void resetBottomNavigationState() {
        int id = navController.getCurrentDestination() == null ? R.id.LandingFragment : navController.getCurrentDestination().getId();

        animateBottomNavigationButton(binding.btnNavLanding, id == R.id.LandingFragment);
        animateBottomNavigationButton(binding.btnNavCategory, id == R.id.CategoryFragment);
        animateBottomNavigationButton(binding.btnNavPromotion, id == R.id.PromotionFragment);
        animateBottomNavigationButton(binding.btnNavForeign, id == R.id.ForeignFragment);
        animateBottomNavigationButton(binding.btnNavProfile, id == R.id.ProfileFragment);

        Log.e("BottomNav", "Selected");
    }

    private void animateBottomNavigationButton(MaterialButton button, boolean selected) {
        if (!selected) {
            button.setSelected(false);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
            return;
        }

        if (button.isSelected())
            return;

        button.setSelected(true);

        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), Color.TRANSPARENT, Color.WHITE);
        colorAnimator.setDuration(250);
        colorAnimator.addUpdateListener(animation -> button.setBackgroundTintList(
                ColorStateList.valueOf((Integer) animation.getAnimatedValue())));
        colorAnimator.start();
    }

    private void initNavigationView() {
        View headerView = binding.navigationMain.getHeaderView(0);
        ViewGroup.MarginLayoutParams mParm = (ViewGroup.MarginLayoutParams) headerView.getLayoutParams();
        mParm.topMargin = DisplayUtils.dpToPx(this, 20) + DisplayUtils.getStatusBarHeight(this);
        headerView.setLayoutParams(mParm);
    }

    private void navigateFragment(int navResId) {
        if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() == navResId)
            return;

        navController.navigate(navResId);
    }

    public void openDrawer() {
        if (!binding.drawerMain.isDrawerOpen(GravityCompat.START)) {
            binding.drawerMain.openDrawer(GravityCompat.START);
        }

    }

    public void closeDrawer() {
        if (binding.drawerMain.isDrawerOpen(GravityCompat.START)) {
            binding.drawerMain.closeDrawer(GravityCompat.START);
        }

    }

    public boolean isDrawerOpen() {
        return binding.drawerMain.isDrawerOpen(GravityCompat.START);
    }
}