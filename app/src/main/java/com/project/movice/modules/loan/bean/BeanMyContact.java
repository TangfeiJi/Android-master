package com.project.movice.modules.loan.bean;

import com.project.movice.modules.main.bean.BeanBase;

/**
 * 联系人
 * Created by wy on 2019/1/8.
 */

public class BeanMyContact extends BeanBase {

    private String relationship;//关系
    private String name;//姓名
    private String mobile;//手机号

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
