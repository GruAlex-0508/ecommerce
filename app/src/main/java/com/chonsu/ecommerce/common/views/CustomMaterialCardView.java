package com.chonsu.ecommerce.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.chonsu.ecommerce.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

public class CustomMaterialCardView extends MaterialCardView{

    private float topLeftRadius;
    private float topRightRadius;
    private float bottomLeftRadius;
    private float bottomRightRadius;

    public CustomMaterialCardView(Context context) {
        super(context);
        init(context, null);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);

        if (child instanceof ShapeableImageView) {
            ShapeableImageView imageView = (ShapeableImageView) child;
            imageView.setShapeAppearanceModel(getShapeAppearanceModel());
        }
    }

    public CustomMaterialCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomMaterialCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        topLeftRadius = 0f;
        topRightRadius = 0f;
        bottomLeftRadius = 0f;
        bottomRightRadius = 0f;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomMaterialCardView);

            try {
                topLeftRadius = a.getDimension(R.styleable.CustomMaterialCardView_cornerTopLeftRadius, 0f);
                topRightRadius = a.getDimension(R.styleable.CustomMaterialCardView_cornerTopRightRadius, 0f);
                bottomLeftRadius = a.getDimension(R.styleable.CustomMaterialCardView_cornerBottomLeftRadius, 0f);
                bottomRightRadius = a.getDimension(R.styleable.CustomMaterialCardView_cornerBottomRightRadius, 0f);
            } finally {
                a.recycle();
            }
        }

        applyCornerRadius();
    }

    private void applyCornerRadius() {
        ShapeAppearanceModel shapeAppearanceModel = ShapeAppearanceModel.builder()
                .setTopLeftCorner(CornerFamily.ROUNDED, topLeftRadius)
                .setTopRightCorner(CornerFamily.ROUNDED, topRightRadius)
                .setBottomLeftCorner(CornerFamily.ROUNDED, bottomLeftRadius)
                .setBottomRightCorner(CornerFamily.ROUNDED, bottomRightRadius)
                .build();

        setShapeAppearanceModel(shapeAppearanceModel);
        setClipToOutline(true);
    }

    private void setTopLeftRadius(float radius) {
        topLeftRadius = radius;
        applyCornerRadius();
    }

    private void setTopRightRadius(float radius) {
        topRightRadius = radius;
        applyCornerRadius();
    }

    private void setBottomLeftRadius(float radius) {
        bottomLeftRadius = radius;
        applyCornerRadius();
    }

    private void setBottomRightRadius(float radius) {
        bottomRightRadius = radius;
        applyCornerRadius();
    }

    public float getTopLeftRadius() {
        return topLeftRadius;
    }

    public float getTopRightRadius() {
        return topRightRadius;
    }

    public float getBottomLeftRadius() {
        return bottomLeftRadius;
    }

    public float getBottomRightRadius() {
        return bottomRightRadius;
    }
}
