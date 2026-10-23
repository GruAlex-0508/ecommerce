package com.chonsu.ecommerce.common.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.config.ThemeManager;

/**
 * Background of the profile page: a white area with a curved bottom edge, and a brown gradient
 * crescent peeking out underneath it (thick at the sides, thin in the middle).
 * Colours come from the theme, so it follows the light / dark theme automatically.
 */
public class ProfileHeaderView extends View {

    private static final float DESIGN_WIDTH = 360f;
    private static final float DESIGN_HEIGHT = 270f;

    private static final float WHITE_EDGE_Y = 165f;
    private static final float WHITE_BOTTOM_Y = 248f;

    private static final float BROWN_EDGE_Y = 216f;
    private static final float BROWN_BOTTOM_Y = 255f;

    private final Paint whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint brownPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path whitePath = new Path();
    private final Path brownPath = new Path();
    private final RectF arcRect = new RectF();

    private int brownLight;
    private int brownDark;

    public ProfileHeaderView(Context context) {
        super(context);
        init(context);
    }

    public ProfileHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProfileHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        brownLight = ThemeManager.getThemeColor(context, R.attr.bottomNavGradient2);
        brownDark = ThemeManager.getThemeColor(context, R.attr.bottomNavGradient1);

        whitePaint.setStyle(Paint.Style.FILL);
        whitePaint.setColor(ThemeManager.getThemeColor(context, R.attr.bgGradient1));
        whitePaint.setShadowLayer(dpToPx(5), 0, dpToPx(2), 0x40000000);

        brownPaint.setStyle(Paint.Style.FILL);
        brownPaint.setShadowLayer(dpToPx(6), 0, dpToPx(3), 0x55000000);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float scaleY = h / DESIGN_HEIGHT;

        buildCurvedBottom(brownPath, w, BROWN_EDGE_Y * scaleY, BROWN_BOTTOM_Y * scaleY);
        buildCurvedBottom(whitePath, w, WHITE_EDGE_Y * scaleY, WHITE_BOTTOM_Y * scaleY);

        brownPaint.setShader(new LinearGradient(0, 0, w, 0, brownLight, brownDark, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED
                ? Math.round(width * DESIGN_HEIGHT / DESIGN_WIDTH)
                : MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(brownPath, brownPaint);
        canvas.drawPath(whitePath, whitePaint);
    }

    /**
     * Builds a rectangle from the top of the view whose bottom edge is a circular arc that
     * touches y = edgeY at both sides and y = bottomY in the middle.
     */
    private void buildCurvedBottom(Path path, float width, float edgeY, float bottomY) {
        float half = width / 2f;
        float sagitta = bottomY - edgeY;

        float radius = (half * half + sagitta * sagitta) / (2f * sagitta);
        float centerY = bottomY - radius;

        float startAngle = (float) Math.toDegrees(Math.atan2(edgeY - centerY, half));
        float sweepAngle = 180f - 2f * startAngle;

        arcRect.set(half - radius, centerY - radius, half + radius, centerY + radius);

        path.reset();
        path.moveTo(0, 0);
        path.lineTo(width, 0);
        path.lineTo(width, edgeY);
        path.arcTo(arcRect, startAngle, sweepAngle);
        path.close();
    }

    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }
}
