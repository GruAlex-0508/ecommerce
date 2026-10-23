package com.chonsu.ecommerce.common.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class WaveShadowView extends View {

    private final Paint shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path wavePath = new Path();

    public WaveShadowView(Context context) {
        super(context);
        init();
    }

    public WaveShadowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setColor(Color.BLACK);
        shadowPaint.setShadowLayer(dpToPx(6), 0, dpToPx(5), 0x70000000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();

        if (width <= 0 || height <= 0) {
            return;
        }

        float scaleX = width / 360f;
        float scaleY = height / 167f;

        canvas.save();
        canvas.scale(scaleX, scaleY);

        wavePath.reset();

        wavePath.moveTo(0, 0);
        wavePath.lineTo(360, 0);
        wavePath.lineTo(360, 93);
        wavePath.cubicTo(320, 102, 280, 116, 240, 129);
        wavePath.cubicTo(200, 143, 160, 161, 120, 164);
        wavePath.cubicTo(85, 167, 40, 165, 0, 145);

        wavePath.close();
        canvas.drawPath(wavePath, shadowPaint);
        canvas.restore();
    }

    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }
}