package com.dingtao.rrmmp.util.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class RecyclerGridView extends GridView {

    public RecyclerGridView(Context context) {
        super(context);
    }

    public RecyclerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}

