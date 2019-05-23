package com.project.movice.modules;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.dev.sacot41.scviewpager.DotsView;
import com.dev.sacot41.scviewpager.SCPositionAnimation;
import com.dev.sacot41.scviewpager.SCViewAnimation;
import com.dev.sacot41.scviewpager.SCViewAnimationUtil;
import com.dev.sacot41.scviewpager.SCViewPager;
import com.dev.sacot41.scviewpager.SCViewPagerAdapter;
import com.project.movice.R;

public class WelcomeActivity extends FragmentActivity {

    private static final int NUM_PAGES = 4;

    private SCViewPager mViewPager;
    private SCViewPagerAdapter mPageAdapter;
    private DotsView mDotsView;
    private int[] imgArray = {R.mipmap.one_bg, R.mipmap.two_bg, R.mipmap.three_bg, R.mipmap.four_bg};
    private View raspberryView;

    private ImageView img_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            initView();
    }
   private void  initView(){
       getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       setContentView(R.layout.activity_welcome);
       img_bg = findViewById(R.id.img_bg);
       mViewPager = (SCViewPager) findViewById(R.id.viewpager_main_activity);
       mDotsView = (DotsView) findViewById(R.id.dotsview_main);
       mDotsView.setDotRessource(R.mipmap.dot_selected, R.mipmap.dot_unselected);
       mDotsView.setNumberOfPage(NUM_PAGES);
       mPageAdapter = new SCViewPagerAdapter(getSupportFragmentManager());
       mPageAdapter.setNumberOfPage(NUM_PAGES);
       mPageAdapter.setFragmentBackgroundColor(R.color.theme_100);
       mViewPager.setAdapter(mPageAdapter);

       mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
           }

           @Override
           public void onPageSelected(int position) {
               mDotsView.selectDot(position);
               img_bg.setBackgroundResource(imgArray[position]);

               if (position == 3) {
                   Animation animation = new AlphaAnimation(0.1f, 1.0f);
                   animation.setDuration(3500);
                   raspberryView.setAnimation(animation);
               }


           }

           @Override
           public void onPageScrollStateChanged(int state) {
           }
       });

       final Point size = SCViewAnimationUtil.getDisplaySize(this);
//右边
       View nameTag = findViewById(R.id.imageview_main_activity_name_tag);
       SCViewAnimation nameTagAnimation = new SCViewAnimation(nameTag);
       nameTagAnimation.addPageAnimation(new SCPositionAnimation(this, 0, size.x, 0));
       mViewPager.addAnimation(nameTagAnimation);
//左边
       View currentlyWork = findViewById(R.id.imageview_main_activity_currently_work);
       SCViewAnimation currentlyWorkAnimation = new SCViewAnimation(currentlyWork);
       currentlyWorkAnimation.addPageAnimation(new SCPositionAnimation(this, 0, -size.x, 0));
       mViewPager.addAnimation(currentlyWorkAnimation);


       View atSkex = findViewById(R.id.ll_one);
       SCViewAnimationUtil.prepareViewToGetSize(atSkex);
       SCViewAnimation atSkexAnimation = new SCViewAnimation(atSkex);
       atSkexAnimation.addPageAnimation(new SCPositionAnimation(getApplicationContext(), 0, 0, -(size.y - atSkex.getHeight())));
       atSkexAnimation.addPageAnimation(new SCPositionAnimation(getApplicationContext(), 1, -size.x, 0));
       mViewPager.addAnimation(atSkexAnimation);


       View djangoView = findViewById(R.id.imageview_main_activity_django_python);
       SCViewAnimation djangoAnimation = new SCViewAnimation(djangoView);
       djangoAnimation.startToPosition(null, -size.y);
       djangoAnimation.addPageAnimation(new SCPositionAnimation(this, 0, 0, size.y));
       djangoAnimation.addPageAnimation(new SCPositionAnimation(this, 1, 0, size.y));
       mViewPager.addAnimation(djangoAnimation);

       View commonlyView = findViewById(R.id.imageview_main_activity_commonly);
       SCViewAnimation commonlyAnimation = new SCViewAnimation(commonlyView);
       commonlyAnimation.startToPosition(size.x, null);
       commonlyAnimation.addPageAnimation(new SCPositionAnimation(this, 0, -size.x, 0));
       commonlyAnimation.addPageAnimation(new SCPositionAnimation(this, 1, -size.x, 0));
       mViewPager.addAnimation(commonlyAnimation);

       View butView = findViewById(R.id.imageview_main_activity_diploma);
       SCViewAnimation butAnimation = new SCViewAnimation(butView);
       butAnimation.startToPosition(-size.x, null);
       butAnimation.addPageAnimation(new SCPositionAnimation(this, 1, size.x, 0));
       butAnimation.addPageAnimation(new SCPositionAnimation(this, 2, -size.x, 0));
       mViewPager.addAnimation(butAnimation);

       View diplomeView = findViewById(R.id.imageview_main_activity_but);//
       SCViewAnimation diplomeAnimation = new SCViewAnimation(diplomeView);
       diplomeAnimation.startToPosition(null, size.y);
       diplomeAnimation.addPageAnimation(new SCPositionAnimation(this, 1, 0, -size.y));
       diplomeAnimation.addPageAnimation(new SCPositionAnimation(this, 2, 0, size.y));
       mViewPager.addAnimation(diplomeAnimation);

       View whyView = findViewById(R.id.img_right2);//左1
       SCViewAnimation whyAnimation = new SCViewAnimation(whyView);
       whyAnimation.startToPosition(null, -size.y);
       whyAnimation.addPageAnimation(new SCPositionAnimation(this, 1, 0, size.y));
       whyAnimation.addPageAnimation(new SCPositionAnimation(this, 2, 0, -size.y));
       mViewPager.addAnimation(whyAnimation);

       View whyView2 = findViewById(R.id.imageview_main_activity_why);
       SCViewAnimation whyAnimation2 = new SCViewAnimation(whyView2);
       whyAnimation2.startToPosition(size.x, null);
       whyAnimation2.addPageAnimation(new SCPositionAnimation(this, 1, -size.x, 0));
       whyAnimation2.addPageAnimation(new SCPositionAnimation(this, 2, size.x, 0));
       mViewPager.addAnimation(whyAnimation2);


       View ll_three = findViewById(R.id.ll_three);//
       SCViewAnimation ll_threeAnimation = new SCViewAnimation(ll_three);
       ll_threeAnimation.startToPosition(null, size.y);
       ll_threeAnimation.addPageAnimation(new SCPositionAnimation(this, 1, 0, -size.y));
       ll_threeAnimation.addPageAnimation(new SCPositionAnimation(this, 2, 0, size.y));
       mViewPager.addAnimation(ll_threeAnimation);


       raspberryView = findViewById(R.id.imageview_main_raspberry_pi);
       SCViewAnimation raspberryAnimation = new SCViewAnimation(raspberryView);
       raspberryAnimation.startToPosition(-size.x, null);
       raspberryAnimation.addPageAnimation(new SCPositionAnimation(this, 2, size.x, 0));
       raspberryAnimation.addPageAnimation(new SCPositionAnimation(this, 3, -size.x, 0));

       mViewPager.addAnimation(raspberryAnimation);


       View connectedDeviceView = findViewById(R.id.imageview_main_connected_device);
       SCViewAnimation connectedDeviceAnimation = new SCViewAnimation(connectedDeviceView);
       connectedDeviceAnimation.startToPosition((int) (size.x * 1.5), null);
       connectedDeviceAnimation.addPageAnimation(new SCPositionAnimation(this, 2, -(int) (size.x * 1.5), 0));
       connectedDeviceAnimation.addPageAnimation(new SCPositionAnimation(this, 3, -size.x, 0));
       mViewPager.addAnimation(connectedDeviceAnimation);
       connectedDeviceView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(WelcomeActivity.this, SplashActivity.class));

               finish();
           }
       });
   }
}
