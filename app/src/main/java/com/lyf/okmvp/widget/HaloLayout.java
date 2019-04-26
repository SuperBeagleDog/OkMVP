package com.lyf.okmvp.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;

/**
 * This layout will animate a halo from the end of his child view(only for circle view)
 * To see how to use it, read activity_halo_layout.xml.
 */
public class HaloLayout extends ConstraintLayout {

    int padding = dp2px(getContext(), 20);
    int mRadius = 0;
    int cx, cy;
    private Paint mPaint; // 画笔
    private ValueAnimator valueAnimator;

    public HaloLayout(Context context) {
        this(context, null);
    }

    public HaloLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HaloLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setPadding(padding, padding, padding, padding);

        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        cx = cy = getMeasuredWidth() / 2;

        if (valueAnimator == null) {
            final int start = (getMeasuredWidth() - padding * 2) / 2;
            valueAnimator = ObjectAnimator.ofFloat(0, padding).setDuration(1500);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float alpha = (float) animation.getAnimatedValue() / padding;
                    mRadius = (int) (start + (float) animation.getAnimatedValue());
                    int al = (int) (alpha * 255);
                    mPaint.setAlpha(1-al);
                    invalidate();
                }
            });
            valueAnimator.start();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(cx, cy, mRadius, mPaint);
    }

    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void log(String msg) {
        Log.e("test", msg);
    }

}