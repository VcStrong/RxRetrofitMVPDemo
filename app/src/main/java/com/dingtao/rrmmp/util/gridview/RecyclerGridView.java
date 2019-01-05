package com.dingtao.rrmmp.util.gridview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.dingtao.rrmmp.util.LogUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

public class RecyclerGridView extends XRecyclerView {

    private int lastVisibleItemPosition;
    private int firstVisibleItemPosition;
    private float mLastY = 0;// 记录上次Y位置
    private boolean isTopToBottom = false;
    private boolean isBottomToTop = false;
    public RecyclerGridView(Context context) {
        this(context, null);
    }

    public RecyclerGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


}
