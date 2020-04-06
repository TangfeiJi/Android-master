package com.project.movice.modules.home.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.movice.R;
import com.project.movice.base.fragment.BaseFragment;
import com.project.movice.core.event.EventPersonalInformation;
import com.project.movice.core.http.api.ApiService;
import com.project.movice.dialog.ListDialog;
import com.project.movice.dialog.LoanAgreementDialog;
import com.project.movice.dialog.OnItemClickListener;
import com.project.movice.modules.home.adappter.ProTwo_adapter;
import com.project.movice.modules.home.base.BeanLoan;
import com.project.movice.modules.home.bean.BeanBorrowingStatus;
import com.project.movice.modules.home.contract.ProductContract;
import com.project.movice.modules.home.presenter.ProductPresenter;
import com.project.movice.utils.Arith;
import com.project.movice.utils.ComUtils;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.LoginUtil;
import com.project.movice.utils.StringUtils;


import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.project.movice.application.MoviceApp.loanClick;

/**
 * 单产品，可选金额
 * Created by wy on 2018/12/17.
 */

public class HomeLoanFragment extends BaseFragment<ProductPresenter> implements SeekBar.OnSeekBarChangeListener, ProductContract.View<BeanBorrowingStatus> {
    @BindView(R.id.borrowing_money)
    TextView mBorrowingMoney;//借款金額
    @BindView(R.id.min_money)
    TextView mMinMoney;
    @BindView(R.id.max_money)
    TextView mMaxMoney;
    @BindView(R.id.seekBar)
    SeekBar mSeekBar;
    @BindView(R.id.borrowing_period)
    TextView mBorrowingPeriod;//借款期限
    @BindView(R.id.select_purpose)
    RelativeLayout mSelectPurpose;//选择借款用途
    @BindView(R.id.borrowing_purpose)
    TextView mBorrowingPurpose;//借款用途
    @BindView(R.id.rate)
    TextView mRate;//日利率
    @BindView(R.id.expire_repayment_money)
    TextView mExpireRepaymentMoney;//还款金额
    @BindView(R.id.protocol_rate)
    TextView mProtocolRate;
    @BindView(R.id.borrowing_immediately)
    Button mBorrowingImmediately;//立即借款
    @BindView(R.id.flipper)
    ViewFlipper flipper;
    Unbinder unbinder;


    private ArrayList<BeanLoan> loan = new ArrayList<>();
    private BeanLoan bl;//当前选择的借款
    private long money = 0;//借款总额

    @BindView(R.id.tv_moneyAll)
    TextView tv_moneyAll;


    @BindView(R.id.max_notMoney)
    TextView max_notMoney;

    private int days;
    private float jisuanRate;


    @BindView(R.id.rv_days)
    RecyclerView rv_days;

    private ProTwo_adapter proTwo_adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.product_two;
    }
    @Override
    protected void initView() {
        if (null != getArguments()) {
            try {
                loan = getArguments().getParcelableArrayList("data");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_days.setLayoutManager(linearLayoutManager);
        proTwo_adapter = new ProTwo_adapter(getActivity(), null);
        rv_days.setAdapter(proTwo_adapter);
        mBorrowingImmediately.setSelected(true);
        initData();
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    private void initViewDliper() {
        for (int i = 0; i < 80; i++) {
            View ll_content = View.inflate(getActivity(), R.layout.layout_view_fliper, null);
            TextView tv_content = ll_content.findViewById(R.id.tv_content);
            final Random random = new Random();
//            int one = (random.nextInt(3) % (3 - 1 + 1) + 1);
//
//            int two = (random.nextInt(9) % (9 - 0 + 0) + 0);

            int one = Integer.parseInt(bl.getMinScrollAmout()) / 100000;
            int two = Integer.parseInt(bl.getMaxScrollAmout()) / 100000;
            int oneNum = (random.nextInt(two) % (two - one + one) + one);
            ;
            oneNum = oneNum * 100000;

//            Selamat nasabah 0813****2924 limit naik hingga 2.000.000    int s = random.nextInt(max)%(max-min+1) + min;
            tv_content.setText("Selamat nasabah " + ComUtils.getTel() + " limit naik hingga" + StringUtils.getAmount(oneNum + "", 3));
            flipper.addView(ll_content);
            flipper.setFocusable(false);


            proTwo_adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (bl.getLoanDaysInfo().get(position).getEnable().equals("1")) {
                        days = bl.getLoanDaysInfo().get(position).getDay();
                        jisuanRate = bl.getLoanDaysInfo().get(position).getRate();
                        mRate.setText(Arith.round(Arith.mul(jisuanRate, 100), 3) + "%");


                        mBorrowingPeriod.setText(days + " Hari");
                        mPresenter.calculation();
                        for (int i = 0; i < bl.getLoanDaysInfo().size(); i++) {
                            bl.getLoanDaysInfo().get(i).setSelect(false);
                        }
                        bl.getLoanDaysInfo().get(position).setSelect(true);
                        proTwo_adapter.setNewData(bl.getLoanDaysInfo());
                    }
                }
            });
        }
    }

    protected void initData() {


        if (null != loan && loan.size() > 0&&!loan.isEmpty()) {
            try {
                bl = loan.get(0);
                if(null == bl.getLoanDaysInfo() || bl.getLoanDaysInfo().size() == 0||bl.getLoanDaysInfo().isEmpty()){

                    return;
                }

                for (int j = 0; j < bl.getLoanDaysInfo().size(); j++) {
                    bl.getLoanDaysInfo().get(j).setSelect(false);
                }
                bl.getLoanDaysInfo().get(0).setSelect(true);
                proTwo_adapter.setNewData(bl.getLoanDaysInfo());
                days = bl.getLoanDaysInfo().get(0).getDay();
                initViewDliper();
                max_notMoney.setText(StringUtils.getAmount(bl.getMaxRollAmout() + "", 3));
                max_notMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
                //最小最大金额
                mMinMoney.setText(StringUtils.getAmount(bl.getMinMoney() + "", 3));
                mMaxMoney.setText(StringUtils.getAmount(bl.getMaxMoney() + "", 3));
                //设置进度条
                DecimalFormat df = new DecimalFormat("0");
                String lm = df.format(bl.getMaxMoney() - bl.getMinMoney());
                mSeekBar.setMax(Integer.valueOf(lm));
                mSeekBar.setProgress((int) (Integer.valueOf(lm) / 2));
                mBorrowingPeriod.setText(days + " Hari");
                jisuanRate = bl.getLoanDaysInfo().get(0).getRate();
                //利率
                double r = Arith.round(Arith.mul(jisuanRate, 100), 3);

                mRate.setText(r + "%");
                progressChanged(bl, (int) (Integer.valueOf(lm) / 2));

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        try {
            String agreement = getResources().getString(R.string.terms_and_conditions);
            Locale locale = Locale.getDefault();
            int start = 0;
            int end = 0;
            if (null != locale && !locale.getCountry().equalsIgnoreCase("CN")) {
                start = 33;
                end = agreement.length();
            } else {
                start = 7;
                end = agreement.length();
            }
            SpannableStringBuilder style = new SpannableStringBuilder(agreement);
            style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.home_blue)),
                    start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mProtocolRate.setText(style);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        whetherCanRob();
    }



    @Override
    protected void initEventAndData() {

    }


    @OnClick({R.id.select_purpose, R.id.interest_rate_question, R.id.borrowing_immediately, R.id.protocol_rate, R.id.img_fap})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_purpose:
                final String[] array = getResources().getStringArray(R.array.borrowing_purposes);
                ListDialog ld = new ListDialog(getActivity(), array);
                ld.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mBorrowingPurpose.setText(array[position]);
                        whetherCanRob();
                    }
                });
                ld.show();
                break;
            case R.id.protocol_rate:
                Bundle bundle = new Bundle();
                String params = "?loanPeriod=" + bl.getLoanPeriod();
                bundle.putString("url", ApiService.getRATE);
                bundle.putString("params", params);//loanPeriod
                LoanAgreementDialog dialog = new LoanAgreementDialog(getActivity(), bundle);
                dialog.show();
                break;
            case R.id.interest_rate_question:
//
                break;
            case R.id.borrowing_immediately:

                if (!TextUtils.isEmpty(DataUtils.getPhone(getActivity()))) {
                    mPresenter.requestProduct();
                } else {
                    LoginUtil.login(getActivity());
                }


                break;
            case R.id.img_fap:
//                Information info = new Information();
//                info.setAppkey(Config.ZHICHI_KEY);  //分配给App的的密钥
//                SobotApi.startSobotChat(getActivity(), info);
                break;

        }

    }


    /**
     * 判断所有填都有
     */
    private void whetherCanRob() {
        if (!StringUtils.isEmpty(mBorrowingPurpose.getText().toString())) {
            mBorrowingImmediately.setSelected(true);
        } else {
            mBorrowingImmediately.setSelected(false);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        try {
            mPresenter.progressChanged(bl, progress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void setData(BeanBorrowingStatus data) {
        if (data.getStatus() == 2) {//2是不可借款
            mPresenter.showError(Long.parseLong(data.getDate()), data.getContent());

        } else {

            EventBus.getDefault().post(new EventPersonalInformation(EventPersonalInformation.JUMP, true));
        }
    }


    @Override
    public long getMoney() {
        return money;
    }

    @Override
    public int getLoanDays() {
        return bl.getLoanPeriod();
    }

    @Override
    public int getNumberLoanDays() {
        return days;
    }

    @Override
    public String getBorrowingPurpose() {
        String borrowingPurpose = mBorrowingPurpose.getText().toString();
        return borrowingPurpose;
    }

    @Override
    public float getRate() {
        return jisuanRate;
    }

    @Override
    public float getShowRate() {
        return jisuanRate;
    }

    @Override
    public float getHeadRest() {
        return bl.getHeadRest();
    }

    @Override
    public void setMoney(long money) {
        this.money = money;

    }

    @Override
    public void setBorrowingMoney(String money) {
        tv_moneyAll.setText(money);
        mBorrowingMoney.setText(money);
    }

    /**
     * @param money 到期还款金额
     */
    @Override
    public void setRepaymentMoney(String money) {
        mExpireRepaymentMoney.setText(money);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Nullable
    @Override
    public Context getContext() {
        return getActivity();
    }

    DecimalFormat df = new DecimalFormat("0");

    public void progressChanged(BeanLoan bl, int progress) {
        try {
            int i = progress / bl.getUnit();
            if (bl.getMinMoney().intValue() == bl.getMaxMoney().intValue()) {
                setMoney(bl.getMaxMoney());
            } else if (progress == 0) {
                setMoney(bl.getMinMoney());
            } else if (i > 0) {//
                String d = df.format(i * bl.getUnit());
                d = df.format(Integer.parseInt(d) + bl.getMinMoney());
                setMoney(Long.parseLong(d));

            }
            try {
                String borrowingMoney = String.format(getContext().getResources().getString(R.string.borrowing_money),
                        StringUtils.getAmount(money + "", 3));
                setBorrowingMoney(borrowingMoney);//显示借款金额
                String repaymentMoney = String.format(getContext().getResources().getString(R.string.borrowing_money),
                        StringUtils.getAmount(getRepaymentAmount(), 3));
                setRepaymentMoney(repaymentMoney);//借款金额，到期还款金额

            } catch (Exception e) {
                e.printStackTrace();
            }
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

        String newmoney = df.format(money + getRateMoney());
        return newmoney;
    }


    /**
     * 获取总利息
     *
     * @return
     */
    private float getRateMoney() {
        String lm = df.format(getMoney() * getRate());
        String ll = df.format(Float.valueOf(lm) * getNumberLoanDays());
        return Float.valueOf(ll);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            Log.e("1111wwww1","0000");
        }else{
            Log.e("1111wwww1","11111");
    }



    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("1111wwww1","onPause");
        flipper.stopFlipping();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("1111wwww1","onResume");
        flipper.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        flipper.stopFlipping();
    }
}
