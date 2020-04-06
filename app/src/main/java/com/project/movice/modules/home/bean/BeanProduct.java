package com.project.movice.modules.home.bean;

import com.project.movice.modules.home.base.BeanLoan;

import java.io.Serializable;
import java.util.ArrayList;

/** 产品数据
 * Created by wy on 2019/3/18.
 */

public class BeanProduct implements Serializable {
    private int productType;
    private ArrayList<BeanLoan> loan = new ArrayList<>();

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public ArrayList<BeanLoan> getLoan() {
        return loan;
    }

    public void setLoan(ArrayList<BeanLoan> loan) {
        this.loan = loan;
    }
}
