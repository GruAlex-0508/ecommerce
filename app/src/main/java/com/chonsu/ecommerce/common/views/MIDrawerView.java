package com.chonsu.ecommerce.common.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.common.DisplayUtils;
import com.google.android.material.card.MaterialCardView;

/**
 * Here is a view for the navigation Drawer.
 * From which you can set the drawer style as requirements with only one param.
 */
public class MIDrawerView extends DrawerLayout {

    public static final int MI_TYPE_SLIDE_WITH_CONTENT = 1; // For sliding view with content
    public static final int MI_TYPE_DOOR_IN = 2; // Open a drawer inside with content sliding with scale and rotation both.
    public static final int MI_TYPE_DOOR_OUT = 3; // Open a drawer outside with content sliding with scale and rotation both.
    public static final int MI_TYPE_SLIDE = 4; // Open a drawer with content sliding with scale only
    public static final int MI_TYPE_SLIDE_ROTATE = 5; // Open a drawer with content sliding with scale and rotate

    private final float min = 0.8f;
    private final float max = 1.0f;
    private final int rotationXOffset = 15;
    private final int rotationYOffset = 15;

    private int sliderType = 0;

    DrawerLayout.DrawerListener drawerListener = null;

    private MIDrawerEvents mMIDrawerEvents = null;

    public MIDrawerView(Context context) {
        this(context, null);
    }

    @SuppressLint("CustomViewStyleable")
    public MIDrawerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.MIDrawerView);
        sliderType = arr.getInt(R.styleable.MIDrawerView_sliderType, 0);
        setDrawerView(sliderType);
        arr.recycle();
    }

    public MIDrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDrawerView(final int slideType) {
        if (drawerListener != null) {
            // Remove callback after adding new listener with new type.
            removeDrawerListener(drawerListener);
        }
        drawerListener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Getting content view.
                View contentView = getChildAt(0);

                // Getting navigation view.
                View navigationView = getChildAt(1);

                // Check app configuration for RTL or LTR
                Configuration config = getContext().getResources().getConfiguration();

                if (contentView instanceof MaterialCardView) {
                    MaterialCardView cardView = (MaterialCardView) contentView;
                    cardView.setCardElevation(DisplayUtils.dpToPx(getContext(), 5) * slideOffset);
                    cardView.setRadius(DisplayUtils.dpToPx(getContext(), 25) * slideOffset);
                }

                switch (slideType) {
                    case MI_TYPE_SLIDE_WITH_CONTENT:
                        if (navigationView != null && contentView != null) {
                            if (config.getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                                contentView.setTranslationX(-(navigationView.getWidth() * slideOffset));
                            } else {
                                contentView.setTranslationX(navigationView.getWidth() * slideOffset);
                            }
                        }
                        break;
                    case MI_TYPE_DOOR_IN:
                        if (contentView != null) {
                            float scaleFactor = max - (max - min) * slideOffset;
                            int width = drawerView.getWidth();
                            float moveFactor = width * slideOffset;
                            moveFactor *= scaleFactor;
                            if (config.getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                                // If app in RTL mode.
                                contentView.setTranslationX(-moveFactor);
                                contentView.setRotationY(-(moveFactor * -rotationYOffset) / width);
//                                contentView.setRotationX((moveFactor * -rotationXOffset) / width);
                            } else {
                                // If app in LTR mode.
                                contentView.setTranslationX(moveFactor);
                                contentView.setRotationY(moveFactor * -rotationYOffset / width);
//                                contentView.setRotationX((moveFactor * -rotationXOffset) / width);
                            }
                            contentView.setScaleY(scaleFactor);
                            contentView.setScaleX(scaleFactor);
                        }
                        break;
                    case MI_TYPE_DOOR_OUT:
                        if (contentView != null) {
                            float scaleFactor = max - (max - min) * slideOffset;
                            int width = drawerView.getWidth();
                            float moveFactor = width * slideOffset;
                            moveFactor *= scaleFactor;
                            if (config.getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                                // If app in RTL mode.
                                contentView.setTranslationX(-moveFactor);
                                contentView.setRotationY(-(moveFactor * -rotationYOffset) / width);
                            } else {
                                // If app in LTR mode.
                                contentView.setTranslationX(moveFactor);
                                contentView.setRotationY(moveFactor * rotationYOffset / width);
                            }
                            contentView.setScaleY(scaleFactor);
                            contentView.setScaleX(scaleFactor);
                        }
                        break;
                    case MI_TYPE_SLIDE:
                        if (contentView != null) {
                            float scaleFactor = max - (max - min) * slideOffset;
                            int width = drawerView.getWidth();
                            float moveFactor = width * slideOffset;
                            moveFactor *= scaleFactor;
                            if (config.getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                                // If app in RTL mode.
                                contentView.setTranslationX(-moveFactor);
                            } else {
                                // If app in LTR mode.
                                contentView.setTranslationX(moveFactor);
                            }
                            contentView.setScaleY(scaleFactor);
                            contentView.setScaleX(scaleFactor);
                        }
                        break;
                    case MI_TYPE_SLIDE_ROTATE:
                        if (contentView != null) {
                            float scaleFactor = max - (max - min) * slideOffset;
                            float rotateFactor = 8 * slideOffset;
                            int width = drawerView.getWidth();
                            float moveFactor = width * slideOffset;
                            moveFactor *= scaleFactor;
//                            contentView.setPivotX(0);
//                            contentView.setPivotY(0);
//                            contentView.resetPivot();
                            if (config.getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                                // If app in RTL mode.
                                contentView.setTranslationX(-moveFactor);
//                                contentView.setRotationX(-rotateFactor);
                            } else {
                                // If app in LTR mode.
                                contentView.setTranslationX(moveFactor);
//                                contentView.setRotationX(rotateFactor);
                            }
                            contentView.setScaleY(scaleFactor);
                            contentView.setScaleX(scaleFactor);

                            if (config.getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                                contentView.setRotation(rotateFactor);
                            } else {
                                contentView.setRotation(-rotateFactor);
                            }
                        }
                        break;
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (mMIDrawerEvents != null) {
                    mMIDrawerEvents.onDrawerOpened(drawerView);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (mMIDrawerEvents != null) {
                    mMIDrawerEvents.onDrawerClosed(drawerView);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        };
        if (drawerListener != null) {
            addDrawerListener(drawerListener);
        }
    }

    public void setSliderType(int sliderType) {
        this.sliderType = sliderType;
        // If drawer was open while changing the drawer type, it will close.
        if (isDrawerOpen(GravityCompat.START)) {
            closeDrawers();
        }
        setDrawerView(sliderType);
    }

    /**
     * Set the listener for the drawer open and close events.
     */
    public void setMIDrawerListener(MIDrawerEvents mDrawerEvents) {
        this.mMIDrawerEvents = mDrawerEvents;
    }

    /**
     * Interface for the drawer events.
     */
    public interface MIDrawerEvents {
        void onDrawerOpened(View drawerView);

        void onDrawerClosed(View drawerView);
    }
}