package com.project.movice.widget.behavior;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wy on 2018/1/6 14:41.
 */


public class CustomViewPager extends ViewPager {
    private boolean noScroll = false;
    private boolean smoothScroll = true;

    public CustomViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CustomViewPager(Context context) {
        super(context);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {//005448109933
		/* return false;//super.onTouchEvent(arg0); */
        if (!noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, smoothScroll);
    }

    public boolean isScrollble() {
        return noScroll;
    }

    public void setScrollble(boolean scrollble) {
        this.noScroll = scrollble;
    }

    public void setSmoothScroll(boolean smoothScroll) {
        this.smoothScroll = smoothScroll;
    }

}
