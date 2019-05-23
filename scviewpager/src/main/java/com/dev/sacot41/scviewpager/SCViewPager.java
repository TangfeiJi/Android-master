package com.dev.sacot41.scviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * Created by Samuel on 2015-06-04.
 */

public class SCViewPager extends ViewPager {

    private ArrayList<SCViewAnimation> mViewAnimation;

    public SCViewPager(Context context) {
        super(context);
        this.mViewAnimation = new ArrayList<SCViewAnimation>();
        setListener();
    }

    public SCViewPager(Context context, AttributeSet attr) {
        super(context, attr);
        this.mViewAnimation = new ArrayList<SCViewAnimation>();
        setListener();
    }

    private void setListener(){
        this.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == SCROLL_STATE_IDLE){
                    for(int j = getChildCount() ; j >= 0 ; j --){
                        for (int i = 0; i < mViewAnimation.size(); i++) {
                            mViewAnimation.get(i).applyAnimation(j, 0);
                        }
                    }
                    for(int j = 0 ; j < getCurrentItem() ; j ++){
                        for (int i = 0; i < mViewAnimation.size(); i++) {
                            mViewAnimation.get(i).applyAnimation(j, 1);
                        }
                    }
                }
            }
        });
    }

    public void addAnimation(SCViewAnimation inViewAnimation) {
        mViewAnimation.add(inViewAnimation);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);

        for (int i = 0; i < mViewAnimation.size(); i++) {
            mViewAnimation.get(i).applyAnimation(position, positionOffset);
        }
    }
}