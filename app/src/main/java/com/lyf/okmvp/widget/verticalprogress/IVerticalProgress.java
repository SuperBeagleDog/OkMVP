package com.lyf.okmvp.widget.verticalprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * @Author 李岳锋
 * @CreateTime 2018/1/26
 * @Description 垂直进度条样式的功能定义
 * 当需要给VerticalProgress增加新功能时，要在这里定义方法
 **/
public interface IVerticalProgress {

    /**
     * 初始化视图
     *
     * @param context 上下文
     * @param attrs   属性
     */
    void initView(Context context, @Nullable AttributeSet attrs);

    /**
     * 设置当前所在的点(位置)
     *
     * @param currentPoint 该值必须大于0。并且小于或等于最大的点数，不然会抛出运行时异常。
     *                     详见{{@link #setMaxPointCount(int)}}.
     */
    void setCurrentPoint(int currentPoint);

    /**
     * 设置最大圆点数
     *
     * @param maxPointCount 最大圆点数。这个值,必须并且大于或等于，当前点的位置，否则会抛出运行时异常。
     *                      详见{{@link #setMaxPointCount(int)}}
     */
    void setMaxPointCount(int maxPointCount);

    /**
     * 计算分割线的大小
     * @param extraLength 额外添加的长度。该值大于0时，分割线的长度增加。小于0时，分割线的长度减少。为0时，不做处理。
     */
    void countDividerLength(int extraLength);

    /**
     * 获取当前点的位置
     */
    int getCurrentPoint();

    /**
     * 获取最大圆点数
     */
    int getMaxPointCount();

    /**
     * 计算点的位置
     */
    void countPointPositions();

    /**
     * 画直线,多条水平线组成一条垂直线，来实现渐变色
     */
    void drawVerticalLine(Canvas canvas);

    /**
     * 画圆点
     */
    void drawPoints(Canvas canvas);

    /**
     * 绘制最后一个点,需要加光晕效果,圆点一样，但半径是3倍
     */
    void drawLastPoint(Canvas canvas);

    /**
     * 获取圆的中心点的起始x坐标
     */
    float getDrawingStartCircleX();

    /**
     * 获取圆的中心点的起始y坐标
     */
    float getDrawingNextCircleY();

    /**
     * 获取下个要绘制圆点的中心点的Y坐标
     *
     * @param cy 根据cy，计算出下个要绘制点的Y坐标
     * @return 计算出下个要绘制点的Y坐标
     */
    float getDrawingNextCircleY(float cy);

    /**
     * @param drawingPoint 当前正在绘制的点的下标,比如，第x个。
     * @return 是否达到该点
     */
    boolean isSearched(int drawingPoint);

    /**
     * @param drawingY 正在绘制的Y坐标
     * @return 是否达到该点
     */
    boolean isSearched(float drawingY);

    /**
     * @param radio 当前的渐变比率
     * @return 获取渐变色
     */
    int getGradientColor(float radio);

    /**
     * 获取分割线的长度
     */
    float getDividerLength();

    /**
     * 获取点的坐标
     */
    float[][] getPointPositions();

}
