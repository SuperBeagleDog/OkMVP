package com.lyf.okmvp.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;

/**
 * TimerView is a circle shape to animate a count down time animation with a text in the middle of the circle.
 * You can use it to cover where is a circle, especially in a circle profile photo.
 * <p>
 * To see how to use it, read activity_timer_layout.xml.
 */
public class TimerView extends ConstraintLayout {

    private Paint mPaint; // 画笔

    /**
     * This field is one of the parameters of drawArc() method.
     * <p>
     * The drawArc() method will draw angle in a clockwise way.
     * As the 0 or 360 START equals to the right side of the view
     * <p>
     * Try to image a plus symbol '+' in which there are four '-'.
     * in this case:
     * <p>
     * the one in the right is the angle of 0.
     * the one in the bottom is the angle 90
     * the one in the left is the angle 180，
     * the one in the top is the angle 270，
     */
    private final static int START_ANGLE = -90; // anticlockwise

    // clockwise way.
    // private final static int getStartAngle = 270;

    /**
     * This field is one of the parameters of drawArc() method.
     * <p>
     * From the START, how much angle you want to draw.
     * if you set it as 180, so it will draw a half of circle view.
     * other number of mSweepAngle will draw a sector.
     */
    private int mSweepAngle = 0;

    // anticlockwise
    private final static int MIN_ANGLE = -360;
    private final static int MAX_ANGLE = 0;

    // clockwise way.
    // private final static int MIN_ANGLE = 0;
    // private final static int MAX_ANGLE = 360;

    // Text in the middle of the circle
    private String mTimeText = "";

    // Duration time of Animator
    private short mDuration = 15000;
    private ValueAnimator valueAnimator;

    // Cover color
    private final static String COVER_COLOR = "#80000000";
    // Text color and size
    private final static String TEXT_COLOR = "#ffffffff";
    private final static short TEXT_SIZE = 40;

    private RectF mRect = new RectF();

    public TimerView(Context context) {
        this(context, null);
    }

    public TimerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        if (valueAnimator == null) {
            valueAnimator = ObjectAnimator.ofInt(MIN_ANGLE, MAX_ANGLE).setDuration(mDuration);
            valueAnimator.setRepeatCount(0);
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mTimeText = getTimeFormat(mDuration - animation.getCurrentPlayTime());
                    int angle = (int) animation.getAnimatedValue();
                    setSweepAngle(angle);
                }
            });
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (valueAnimator != null && valueAnimator.isRunning()) {
            mPaint.setColor(Color.parseColor(COVER_COLOR));
            mRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawArc(mRect, START_ANGLE, mSweepAngle, true, mPaint);
            drawText(canvas);
        }
    }

    // 绘制文本
    private void drawText(Canvas canvas) {

        mPaint.setColor(Color.parseColor(TEXT_COLOR));
        mPaint.setTextSize(TEXT_SIZE);
        float textWidth = mPaint.measureText(mTimeText);
        // 设置文本居中
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        //为基线到字体上边框的距离
        float top = fontMetrics.top;
        //为基线到字体下边框的距离
        float bottom = fontMetrics.bottom;
        //基线中间点的y轴计算公式
        float baseLineY = (getMeasuredHeight() / 2 - top / 2 - bottom / 2);
        float dx = getMeasuredWidth() / 2 - textWidth / 2;
        // 开始绘制文本
        canvas.drawText(mTimeText, dx, baseLineY, mPaint);
    }

    public void startCount() {
        if (valueAnimator != null) valueAnimator.start();
    }

    public void stopCount() {
        if (valueAnimator != null) valueAnimator.cancel();
    }

    public TimerView setDuartion(short duartion) {
        mDuration = duartion;
        return this;
    }

    private TimerView setSweepAngle(int sweepAngle) {
        this.mSweepAngle = sweepAngle;
        invalidate();
        return this;
    }

    /**
     * 获取时分秒
     *
     * @param millisecond 秒
     */
    public static String getTimeFormat(long millisecond) {

        long hour, minute, second;

        if (millisecond <= 0)
            return "00:00";
        else {
            long time = millisecond / 1000;
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                return unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99) return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                return unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }

    }

    private static String unitFormat(long i) {
        if (i >= 0 && i < 10) return "0" + i;
        else return "" + i;
    }

    private void log(String msg) {
        Log.e("test", msg);
    }
}