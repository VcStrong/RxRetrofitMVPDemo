package com.dingtao.rrmmp.bean;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author dingtao
 * @date 2019/4/22 14:58
 * qq:1940870847
 */
public class MyView extends View {

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    /**
     * 测量控件本身的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    //控件位置，左上右下坐标
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        getWidth();
    }

    //控件内部绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
