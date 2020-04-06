package anim;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * 订单状态展开和收缩动画
 * Created by wy on 2018/5/30 17:00.
 */


public class BtStatusAnimator {

    ObjectAnimator animator1;
    Config config;

    public BtStatusAnimator(Config config) {
        this.config = config;
    }

    public void start() {
        if (config.isShow) {//去隐藏
            animator1 = ObjectAnimator.ofFloat(config.mCancelBt, "rotation", 180f, 0f);
            config.isShow = false;
        } else {//去显示
            animator1 = ObjectAnimator.ofFloat(config.mCancelBt, "rotation", 0f, 180f);
            config.isShow = true;
        }
        animator1.setDuration(500);
        animator1.start();
    }

    public static class Config {
        public View mCancelBt;
        public boolean isShow;

        static public Config fromTypeArray(View cancelBt, boolean isShow) {
            Config config = new Config();
            config.mCancelBt = cancelBt;
            config.isShow = isShow;
            return config;
        }
    }
}
