package com.lyf.okmvp.widget.verticalprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lyf.okmvp.R;


/**
 * @Author 李岳锋
 * @CreateTime 2018/1/25
 * @Description 垂直进度条样式.
 **/
public class VerticalProgress extends View implements IVerticalProgress {

    // 创建画笔
    private Context mContext;
    private Paint mPaint; // 画笔
    private Paint mVerticalPaint; // 垂直线的画笔
    private Paint mHalationPaint; // 光晕画笔
    private int mHalationColor =Color.parseColor("#FFE052"); // 光晕的颜色
    private int mBackGroundColor = Color.parseColor("#f2f2f2"); // 进度条背景颜色
    protected float mRadius; // 圆的半径
    private float mDividerLength; // 圆的间距
    private int mMaxPointCount = 0; // 圆点数量
    private int mCurrentPoint = 1; // 当前到达的位置
    private int mMeasuredHeight; // 控件高
    private int mMeasuredWidth; // 控件宽
    private float mVerticalLineWidth; // 直线宽度


    // 圆点的坐标
    protected float mPointPositions[][];

    // 透明度
    private int mAlpha = 255;
    // 光晕
    private int mHalation = 255; //透明度
    private float mHalationWidthTimes = 3.0f;// 光晕相对于圆点的大小，倍数。

    // 起始颜色的RGB
    private int mRedStart;
    private int mGreenStart;
    private int mBlueStart;

    // 终止颜色的RGB
    private int mRedEnd;
    private int mGreenEnd;
    private int mBlueEnd;

    // 过度颜色差
    private int mRedFlag = 1;
    private int mGreenFlag = 1;
    private int mBlueFlag = 1;

    // 每一帧，光晕的透明度的变化值，该值越大，透明度变淡的速度越慢。
    // 该值必须在这个范围 mReduceHalationAlpha < 255 && mReduceHalationAlpha > 0
    private float mReduceHalationAlpha = 16.0f;
    // 动画效果是否已准备好
    private boolean mIsAnimationPrepared = false;
    // 是否停止动画
    private boolean isForceStopAnimation = false;
    // 当前的帧
    private int mFrameCount = 1;
    // 最大帧
    private int mMaxFrameCount = 29;
    // 最小帧
    private int mMinFrameCount = 1;


    public VerticalProgress(Context context) {
        super(context);
        initView(context, null);
    }

    public VerticalProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public VerticalProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        mMeasuredWidth = getMeasuredWidth();

        // 半径长 == 控件宽度的一半，再除以光晕的倍数
        if(mRadius == 0) {
            mRadius = (mMeasuredWidth / 2 / mHalationWidthTimes);
        }

        mMeasuredHeight = getMeasuredHeight() - (int) mRadius * 2;

        // 计算分割线的长度
        countDividerLength(0);
    }

    @Override
    public void countDividerLength(int extraLength){
        mDividerLength = (mMeasuredHeight - mRadius * 2 * mMaxPointCount - mRadius * mHalationWidthTimes) / (mMaxPointCount - 1)  + extraLength;
    }

    @Override
    public void initView(Context context, @Nullable AttributeSet attrs) {
        mContext = context;
        mPaint = new Paint();
        mHalationPaint = new Paint();
        mVerticalPaint = new Paint();

        mPaint.setAntiAlias(true);
        mVerticalPaint.setAntiAlias(true);

        if (attrs != null) {

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerticalProgress);

            mRadius = typedArray.getDimension(R.styleable.VerticalProgress_point_radius,0f);
            mBackGroundColor = typedArray.getColor(R.styleable.VerticalProgress_background_color, mBackGroundColor);
            mMaxPointCount = typedArray.getInt(R.styleable.VerticalProgress_point_count, mMaxPointCount);
            mCurrentPoint = typedArray.getInt(R.styleable.VerticalProgress_current_point, mMaxPointCount);

            int startColor = typedArray.getColor(R.styleable.VerticalProgress_start_color, mBackGroundColor);
            int endColor = typedArray.getColor(R.styleable.VerticalProgress_end_color, mBackGroundColor);

            mRedStart = Color.red(startColor);
            mGreenStart = Color.green(startColor);
            mBlueStart = Color.blue(startColor);

            mRedEnd = Color.red(endColor);
            mGreenEnd = Color.green(endColor);
            mBlueEnd = Color.blue(endColor);

            typedArray.recycle();
        }

        if (mMaxPointCount == 0) {
            throw new RuntimeException("必须设置app:point_count属性，并且值要大于0");
        }

        // 初始化圆点坐标数组
        mPointPositions = new float[mMaxPointCount][2];
        // 开始动画
        mAnimateThread.start();
        // 直线宽
        mVerticalLineWidth =px2dp(mContext,3);

    }

    @Override
    public void setCurrentPoint(int currentPoint) {

        if (currentPoint == 0) {
            throw new RuntimeException("currentPoint必须大于0");
        }

        if (currentPoint > mMaxPointCount) {
            throw new RuntimeException("currentPoint不能大于mMaxPointCount");
        }

        mCurrentPoint = currentPoint;
        invalidate();
    }

    @Override
    public void setMaxPointCount(int maxPointCount) {
        if (maxPointCount < mCurrentPoint) {
            throw new RuntimeException("maxPointCount不能小于mCurrentPoint");
        }

        mMaxPointCount = maxPointCount;
        invalidate();
    }

    @Override
    public int getCurrentPoint() {
        return mCurrentPoint;
    }

    @Override
    public int getMaxPointCount() {
        return mMaxPointCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 计算点的位置
        countPointPositions();
        // 画直线
        drawVerticalLine(canvas);
        // 画圆点
        drawPoints(canvas);
    }

    @Override
    public void countPointPositions() {

        // 当前正在绘制的点
        int drawingPoint = 0;

        for (float cy = getDrawingNextCircleY(); cy < mMeasuredHeight && drawingPoint < mMaxPointCount;
             cy = getDrawingNextCircleY(cy), ++drawingPoint) {
            // 保存当前绘制点的坐标
            mPointPositions[drawingPoint][0] = getDrawingStartCircleX();
            mPointPositions[drawingPoint][1] = cy;
        }

    }

    @Override
    public void drawPoints(Canvas canvas) {

        // 分段的颜色比率
        float radio;
        // 当前的比率
        float currentRadio = 0;

        // 计算分段颜色比率
        if (mCurrentPoint == 1) {
            radio = 0;
        } else {
            radio = 1.0f / (mCurrentPoint - 1);
        }

        // 画圆
        for (int drawingPoint = 1; drawingPoint <= mMaxPointCount; ++drawingPoint, currentRadio += radio) {

            // 已到达的
            if (isSearched(drawingPoint)) {
                if (drawingPoint == mCurrentPoint) {
                    // 最后一个点，额外处理
                    drawLastPoint(canvas);
                } else {
                    // 不是最后一个点，设置渐变色
                    mPaint.setColor(getGradientColor(currentRadio));
                }
            } else {
                // 未到达的，设置背景色
                mPaint.setColor(mBackGroundColor);
            }
            canvas.drawCircle(mPointPositions[drawingPoint - 1][0], mPointPositions[drawingPoint - 1][1], mRadius, mPaint);
        }

    }


    @Override
    public void drawVerticalLine(Canvas canvas) {

        if(mPointPositions == null) {
            return;
        }

        // 直线的位置
        float startY = getDrawingNextCircleY();
        float stopY = startY + 1;

        // 直线的宽度
        float startX = (int) (getDrawingStartCircleX() - mVerticalLineWidth / 2);
        float stopX = (int) (getDrawingStartCircleX() + mVerticalLineWidth / 2);

        // 绘制的颜色
        int drawingColor;

        // 直线长度
        float lineHeight = mPointPositions[mMaxPointCount-1][1] - mRadius;

        // 渐变线的长度
        float gradientLineHeight = mPointPositions[mCurrentPoint - 1][1] - mRadius;

        // 画一条直线
        while (startY <= lineHeight) {

            if (isSearched(startY)) {
                // 绘制已到达的部分的渐变色
                if (startY == mRadius * mHalationWidthTimes) {
                    drawingColor = getGradientColor(0);
                } else {
                    drawingColor = getGradientColor(startY / gradientLineHeight);
                }
            } else {
                // 绘制未到达部分的渐变色
                drawingColor = mBackGroundColor;
            }

            // 设置颜色
            mVerticalPaint.setColor(drawingColor);
            // 画线
            canvas.drawLine(startX, startY, stopX, stopY, mVerticalPaint);
            // 不断的增长
            ++startY;
            ++stopY;
        }

    }

    @Override
    public boolean isSearched(int drawingPoint) {
        return (drawingPoint <= mCurrentPoint);
    }

    @Override
    public boolean isSearched(float drawingY) {
        if (mCurrentPoint == 1) {
            return false;
        }
        return (drawingY <= (mPointPositions[mCurrentPoint - 1][1]));
    }

    @Override
    public void drawLastPoint(Canvas canvas) {

        float mLastPointX = mPointPositions[mCurrentPoint - 1][0];
        float mLastPointY = mPointPositions[mCurrentPoint - 1][1];

        if (mLastPointX == 0 && mLastPointY == 0) {
            return;
        }

        // 光晕
        mHalationPaint.setColor(mHalationColor);
        mHalationPaint.setAlpha(mHalation);

        if (mFrameCount == 1 || mFrameCount > 16) {
            canvas.drawCircle(mLastPointX, mLastPointY, mRadius, mHalationPaint);
        } else {
            float radius = (mFrameCount / 16.0f) * (mRadius * mHalationWidthTimes);
            canvas.drawCircle(mLastPointX, mLastPointY, radius, mHalationPaint);
        }

        // 最后一个圆点
        mPaint.setColor(getGradientColor(1));
        canvas.drawCircle(mLastPointX, mLastPointY, mRadius, mPaint);

        // 可以开始动画效果
        mIsAnimationPrepared = true;
    }

    @Override
    public float getDrawingStartCircleX() {
        return mRadius * mHalationWidthTimes;
    }

    @Override
    public float getDrawingNextCircleY() {
        return mRadius * mHalationWidthTimes;
    }

    @Override
    public float getDrawingNextCircleY(float cy) {
        return cy + mDividerLength + mRadius * 2;
    }

    @Override
    public int getGradientColor(float radio) {

        if (radio == 0) {
            return Color.argb(mAlpha, mRedStart, mGreenStart, mBlueStart);
        } else if (radio >= 1) {
            return Color.argb(mAlpha, mRedEnd, mGreenEnd, mBlueEnd);
        }

        int red = (int) (mRedStart + ((mRedEnd - mRedStart) * radio + mRedFlag));
        int green = (int) (mGreenStart + ((mGreenEnd - mGreenStart) * radio + mGreenFlag));
        int blue = (int) (mBlueStart + ((mBlueEnd - mBlueStart) * radio + mBlueFlag));

        return Color.argb(mAlpha, red, green, blue);
    }

    @Override
    public float getDividerLength() {
        return mDividerLength;
    }

    @Override
    public float[][] getPointPositions() {
        return mPointPositions;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 停止动画
        isForceStopAnimation = true;
    }

    /**
     * 动画线程
     */
    private Thread mAnimateThread = new Thread(new Runnable() {

        @Override
        public void run() {


            while (true) {
                try {
                    if (isForceStopAnimation) {
                        break;
                    }
                    if (mIsAnimationPrepared) {
                        mRefreshUI.sendEmptyMessage(0);
                    }
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }

            }
        }
    });

    /**
     * 刷新界面
     */
    private Handler mRefreshUI = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (mReduceHalationAlpha < 255 && mReduceHalationAlpha > 0) {
                mHalation -= 255 / mReduceHalationAlpha;
            }

            invalidate();

            // 达到最大帧，重置
            if (mFrameCount == mMaxFrameCount) {
                mHalation = 255;
                mFrameCount = mMinFrameCount;
            } else {
                ++mFrameCount;
            }

            return false;
        }
    });

    /**
     * px转换成dp
     */
    private int px2dp(Context context,float pxValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }

}