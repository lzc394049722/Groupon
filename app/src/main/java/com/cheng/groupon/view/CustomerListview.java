package com.cheng.groupon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class CustomerListview extends ListView {
    public CustomerListview(Context context) {
        super(context);
    }

    public CustomerListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
