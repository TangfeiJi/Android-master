package anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 *  订单状态展开和收缩动画
 * Created by wy on 2018/5/30 17:00.
 */


public class OrderStatusAnimator {

    ObjectAnimator animator;
    ObjectAnimator animator1;
    boolean isAnimatorRun = false;//是否正在动画
    Config config;

    public OrderStatusAnimator(Config config) {
        this.config = config;
    }

    public void start() {
        if (!isAnimatorRun) {
            if (config.isShow) {//去隐藏
                animator1 = ObjectAnimator.ofFloat(config.mCancelBt, "rotation", 180f, 0f);
                animator = ObjectAnimator.ofFloat(config.mView, "translationY", 0, config.mView.getHeight() - config.heartHeight);
                config.isShow = false;
            } else {//去显示
                animator1 = ObjectAnimator.ofFloat(config.mCancelBt, "rotation", 0f, 180f);
                animator = ObjectAnimator.ofFloat(config.mView, "translationY", config.mView.getHeight() - config.heartHeight, 0);
                config.isShow = true;
            }
            animator1.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    isAnimatorRun = true;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    isAnimatorRun = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.setDuration(200);
            animator.start();
            animator1.setDuration(500);
            animator1.start();
        }
    }

    public static class Config {
        public View mCancelBt;
        public View mView;
        public int heartHeight;
        public boolean isShow;

        static public Config fromTypeArray(View cancelBt, View view, int height, boolean isShow) {
            Config config = new Config();
            config.mCancelBt = cancelBt;
            config.mView = view;
            config.heartHeight = height;
            config.isShow = isShow;
            return config;
        }
    }
}
