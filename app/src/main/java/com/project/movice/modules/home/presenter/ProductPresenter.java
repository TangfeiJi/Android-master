package com.project.movice.modules.home.presenter;

import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.dialog.NoRepaymentDialog;
import com.project.movice.modules.home.base.BeanLoan;
import com.project.movice.modules.home.bean.BeanBorrowingStatus;
import com.project.movice.modules.home.contract.ProductContract;
import com.project.movice.utils.Constant;
import com.project.movice.utils.ConstantParams;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.DateTimeUtil;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;
import com.project.movice.utils.StringUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class ProductPresenter extends BasePresenter<ProductContract.View> implements ProductContract.Presenter {

    @Inject
    ProductPresenter() {
    }

    @Override
    public void reload() {

    }

    @Override
    public void requestProduct() {
        check();
        addSubscribe(mDataManager.get026(RequestBeanUtils.getRequestBean(new HashMap<>()))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        BeanBorrowingStatus beanBorrowingStatus = FastJsonTools.parseObject(data, BeanBorrowingStatus.class);
//                        BeanBorrowingStatus beanBorrowingStatus = GsonUtil.GsonToBean(data, BeanBorrowingStatus.class);
                        mView.setData(beanBorrowingStatus);
                    }
                }));


    }




    DecimalFormat df = new DecimalFormat("0");


    private void check() {
        //判断所有项是否已选
        try {
            Map<String, String> borrowingParams = new HashMap<>();
            String borrowingPurpose = mView.getBorrowingPurpose();
            borrowingParams.put("borrowingMoney", String.valueOf(mView.getMoney()));//借款选择金额
            borrowingParams.put("borrowingPurpose", ConstantParams.borrowingPurposes(true, borrowingPurpose));//借款用途
            borrowingParams.put("borrowingTimeLimit", mView.getLoanDays() + "");//产品id
            borrowingParams.put("numberLoanDays", mView.getNumberLoanDays() + "");//借款天数
            borrowingParams.put("payBackMoney", getRepaymentAmount());//到期还款金额
            borrowingParams.put("getMoney", getObtainMoney());//到手金额
            borrowingParams.put("rate", String.valueOf(mView.getShowRate()));//利息
            String headRest = df.format(mView.getMoney() * mView.getHeadRest());
            borrowingParams.put("managementFee", headRest);//管理费
            DataUtils.put(mView.getContext(), Constant.KEY_BORROWING, new Gson().toJson(borrowingParams));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void progressChanged(BeanLoan bl, int progress) {
        try {
            int i = progress / bl.getUnit();
            if (bl.getMinMoney().intValue() == bl.getMaxMoney().intValue()) {
                mView.setMoney(bl.getMaxMoney());
            } else if (progress == 0) {
                mView.setMoney(bl.getMinMoney());
                Log.e("1111111455",bl.getMinMoney()+"");
            } else if (i > 0) {//
                String d = df.format(i * bl.getUnit());
                d = df.format(Integer.parseInt(d) + bl.getMinMoney());
                mView.setMoney(Long.parseLong(d));

            }
            calculation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculation() {
        try {
            String borrowingMoney = String.format(mView.getContext().getResources().getString(R.string.borrowing_money),
                    StringUtils.getAmount(mView.getMoney() + "", 3));
            mView.setBorrowingMoney(borrowingMoney);//显示借款金额
            String repaymentMoney = String.format(mView.getContext().getResources().getString(R.string.borrowing_money),
                    StringUtils.getAmount(getRepaymentAmount(), 3));
            mView.setRepaymentMoney(repaymentMoney);//借款金额，到期还款金额


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 到期还款金额
     *
     * @return
     */
    private String getRepaymentAmount() {
        String money = df.format(mView.getMoney() + getRate());
        return money;
    }


    /**
     * 获取总利息
     *
     * @return
     */
    private float getRate() {
        String lm = df.format(mView.getMoney() * mView.getRate());
        String ll = df.format(Float.valueOf(lm) * mView.getNumberLoanDays());
        return Float.valueOf(ll);
    }

    /**
     * 获取总管理费
     *
     * @return
     */
    private float getHeadRest() {
        String headRest = df.format(mView.getMoney() * mView.getHeadRest());
        return Float.valueOf(headRest);
    }


    /**
     * 获取去掉砍头息后金额
     *
     * @return
     */
    private String getObtainMoney() {
        String m = df.format(mView.getMoney() - getHeadRest());
        return m;
    }


    /**
     * 还有未还款错误提示
     *
     * @param data
     */
    public void showError(long data, String content) {
        try {
            String d = "";
            try {
                d = DateTimeUtil.formatEnDateTime(DateTimeUtil.getTimeDatePlusOnDday(data), DateTimeUtil.EN_DF_YYYY_MM_DD);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String content1 = String.format(content, d);
            NoRepaymentDialog dialog = new NoRepaymentDialog(mView.getContext(),
                    R.mipmap.lingyi44,
                    mView.getContext().getResources().getString(R.string.warm_prompt),
                    content1,
                    mView.getContext().getResources().getString(R.string.i_see), true);
            dialog.setOnClickListener(new NoRepaymentDialog.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
