package com.project.movice.widget.behavior;

/**
 * 选择优惠券
 * Created by wy on 2019/1/25.
 */

public class EventCoupon {

    private String couponId;

    public EventCoupon(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }
}
