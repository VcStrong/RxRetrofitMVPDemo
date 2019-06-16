package com.dingtao.common.util.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author dingtao
 * @date 2019/1/16 11:35
 * qq:1940870847
 */
public class MyView extends View implements ValueAnimator.AnimatorUpdateListener {

    private ValueAnimator valueAnimator;
    private int radius;//半径

    private Paint mPaint;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        valueAnimator = ValueAnimator.ofInt(0,200);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(this);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        radius = (int) animation.getAnimatedValue();
        invalidate();
    }

    public void startDraw(){
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAlpha(255-(int)(radius*(255.0 /200)));
        canvas.drawCircle(getWidth()/2,getWidth()/2,radius,mPaint);
        canvas.drawCircle(getWidth()/2,getWidth()/2,(int)(radius*(150.0/200)),mPaint);
    }
}
