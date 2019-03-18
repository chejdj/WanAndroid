package com.chejdj.wanandroid.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
    private boolean scroll = true;

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public void setIsScroll(boolean scroll) {
        this.scroll = scroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!scroll) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
