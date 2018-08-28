package com.framgia.music_16.screen.main;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class UnSwipeViewpager extends ViewPager {

    private boolean mIsEnable;

    public UnSwipeViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mIsEnable = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsEnable) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mIsEnable) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        mIsEnable = enabled;
    }
}
