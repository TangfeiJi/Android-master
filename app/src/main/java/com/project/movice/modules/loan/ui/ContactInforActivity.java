package com.project.movice.modules.loan.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.project.movice.R;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.core.event.EventPersonalInformation;
import com.project.movice.dialog.ConfirmPromptDialog;
import com.project.movice.dialog.ListDialog;
import com.project.movice.dialog.OnItemClickListener;
import com.project.movice.dialog.PromptTwoBtDialog;
import com.project.movice.modules.loan.bean.BeanMyContact;
import com.project.movice.modules.loan.contract.ContactContract;
import com.project.movice.modules.loan.presenter.ContactPresenter;
import com.project.movice.utils.ComUtils;
import com.project.movice.utils.ConstantParams;
import com.project.movice.utils.StringUtils;
import com.project.movice.utils.ToastUtils;
import com.project.movice.utils.des.Des3;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 联系人信息
 * Created by wy on 2018/1/16 10:40.
 */


public class ContactInforActivity extends BaseActivity<ContactPresenter> implements TextWatcher, ContactContract.View {
    private static final int RC_GET_DEFAULT = 0x04;
    private static final int RC_GET_ACCOUNTS = 0x05;
    private static final int RC_GET_ACCOUNTS_COLLEAGUE = 0x06;

    @BindView(R.id.scrollview)
    ScrollView mScrollView;

    @BindView(R.id.relationship_layout1)
    RelativeLayout mRelationshipLayout1;//关系
    @BindView(R.id.relationship1)
    TextView mRelationship1;//关系
    @BindView(R.id.name1)
    EditText mName1;//姓名
    @BindView(R.id.colleague_phone_number_error1)
    TextView mPhoneNumberErrpr1;//
    @BindView(R.id.phone_number1)
    EditText mPhoneNumber1;//电话

    @BindView(R.id.relationship_layout2)
    RelativeLayout mRelationshipLayout2;//关系
    @BindView(R.id.relationship2)
    TextView mRelationship2;//关系
    @BindView(R.id.name2)
    EditText mName2;//姓名
    @BindView(R.id.colleague_phone_number_error2)
    TextView mPhoneNumberErrpr2;//
    @BindView(R.id.phone_number2)
    EditText mPhoneNumber2;//电话

    @BindView(R.id.relationship_layout3)
    RelativeLayout mRelationshipLayout3;//关系
    @BindView(R.id.relationship3)
    TextView mRelationship3;//关系
    @BindView(R.id.name3)
    EditText mName3;//姓名
    @BindView(R.id.colleague_phone_number_error3)
    TextView mPhoneNumberErrpr3;//
    @BindView(R.id.phone_number3)
    EditText mPhoneNumber3;//电话

    @BindView(R.id.relationship_layout4)
    RelativeLayout mRelationshipLayout4;//关系
    @BindView(R.id.relationship4)
    TextView mRelationship4;//关系
    @BindView(R.id.name4)
    EditText mName4;//姓名
    @BindView(R.id.colleague_phone_number_error4)
    TextView mPhoneNumberErrpr4;//
    @BindView(R.id.phone_number4)
    EditText mPhoneNumber4;//电话

    @BindView(R.id.relationship_layout5)
    RelativeLayout mRelationshipLayout5;//关系
    @BindView(R.id.relationship5)
    TextView mRelationship5;//关系
    @BindView(R.id.name5)
    EditText mName5;//姓名
    @BindView(R.id.colleague_phone_number_error5)
    TextView mPhoneNumberErrpr5;//
    @BindView(R.id.phone_number5)
    EditText mPhoneNumber5;//电话
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;

    private Boolean fuBool = false, muBool = false;
    private List<BeanMyContact> myContacts = new ArrayList<>();

    private boolean isNextPage = false; //是否下一页   true 保存后进入下一项，false 保存后返回上一级


    @Override
    protected int getLayoutId() {
        return R.layout.contact_lnfor;
    }

    @Override
    public void initView() {

        mPhoneNumber1.addTextChangedListener(this);
        mPhoneNumber2.addTextChangedListener(this);
        mPhoneNumber3.addTextChangedListener(this);
        mPhoneNumber4.addTextChangedListener(this);
        mPhoneNumber5.addTextChangedListener(this);

        mPhoneNumber1.setRawInputType(Configuration.KEYBOARD_QWERTY);
        mPhoneNumber2.setRawInputType(Configuration.KEYBOARD_QWERTY);
        mPhoneNumber3.setRawInputType(Configuration.KEYBOARD_QWERTY);
        mPhoneNumber4.setRawInputType(Configuration.KEYBOARD_QWERTY);
        mPhoneNumber5.setRawInputType(Configuration.KEYBOARD_QWERTY);
        editViewListener();

//        mPresenter.requestPermissions(new RxPermissions(ContactInforActivity.this),0);
    }


    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitle.setText(R.string.contact_information);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initEventAndData() {
        mPresenter.request008(new HashMap<>());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @OnClick({R.id.relationship_layout1,
            R.id.relationship_layout2, R.id.relationship_layout3, R.id.relationship_layout4,
            R.id.relationship_layout5, R.id.next_step})
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.left_text:
//                if (exitVerification()) {
//                   finish();
//                } else {
//                    showSave();
//                }
//                break;
            case R.id.relationship_layout1://选择直系亲属关系
                final String[] contact_array = getResources().getStringArray(R.array.contact_relationship);
                ListDialog contactDialog = new ListDialog(this, contact_array);
                contactDialog.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == 0 && fuBool) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.fuqin1));
                            return;
                        }
                        if (position == 1 && muBool) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.muqin1));
                            return;
                        }
                        if (verifyParents(contact_array[position])) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.parents_can_only_choose_two));
                            return;
                        }
                        if (verifySpouse(contact_array[position])) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.spouse_can_only_choose_one));
                            return;
                        }
                        if (position == 0) {
                            fuBool = true;
                        }
                        if (position == 1) {
                            muBool = true;
                        }
                        isShow(mRelationship1, contact_array[position]);
                    }
                });
                contactDialog.show();
                break;
            case R.id.relationship_layout2://选择同学/同事关系
                final String[] colleague_array = getResources().getStringArray(R.array.contact_relationship);
                ListDialog contactDialog1 = new ListDialog(this, colleague_array);
                contactDialog1.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == 0 && fuBool) {

                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.fuqin1));
                            return;
                        }
                        if (position == 1 && muBool) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.muqin1));
                            return;
                        }
                        if (verifyParents(colleague_array[position])) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.parents_can_only_choose_two));
                            return;
                        }
                        if (verifySpouse(colleague_array[position])) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.spouse_can_only_choose_one));
                            return;
                        }
                        if (position == 0) {
                            fuBool = true;
                        }
                        if (position == 1) {
                            muBool = true;
                        }
                        isShow(mRelationship2, colleague_array[position]);
                    }
                });
                contactDialog1.show();
                break;
            case R.id.relationship_layout3://选择同学/同事关系
                final String[] colleague3 = getResources().getStringArray(R.array.colleague_and_schoolmate);
                ListDialog dialog3 = new ListDialog(this, colleague3);
                dialog3.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == 0 && fuBool) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.fuqin1));
                            return;
                        }
                        if (position == 1 && muBool) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.muqin1));
                            return;
                        }
                        if (verifyParents(colleague3[position])) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.parents_can_only_choose_two));
                            return;
                        }
                        if (verifySpouse(colleague3[position])) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.spouse_can_only_choose_one));
                            return;
                        }
                        if (position == 0) {
                            fuBool = true;
                        }
                        if (position == 1) {
                            muBool = true;
                        }
                        isShow(mRelationship3, colleague3[position]);
                    }
                });
                dialog3.show();
                break;
            case R.id.relationship_layout4://选择同学/同事关系
                final String[] colleague4 = getResources().getStringArray(R.array.colleague_and_schoolmate);
                ListDialog dialog4 = new ListDialog(this, colleague4);
                dialog4.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == 0 && fuBool) {

                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.fuqin1));
                            return;
                        }
                        if (position == 1 && muBool) {

                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.muqin1));
                            return;
                        }
                        if (verifyParents(colleague4[position])) {

                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.parents_can_only_choose_two));
                            return;
                        }
                        if (verifySpouse(colleague4[position])) {

                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.spouse_can_only_choose_one));
                            return;
                        }
                        if (position == 0) {
                            fuBool = true;
                        }
                        if (position == 1) {
                            muBool = true;
                        }
                        isShow(mRelationship4, colleague4[position]);
                    }
                });
                dialog4.show();
                break;
            case R.id.relationship_layout5://选择同学/同事关系
                final String[] colleague5 = getResources().getStringArray(R.array.colleague_and_schoolmate);
                ListDialog dialog5 = new ListDialog(this, colleague5);
                dialog5.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == 0 && fuBool) {

                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.fuqin1));
                            return;
                        }
                        if (position == 1 && muBool) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.muqin1));
                            return;
                        }
                        if (verifyParents(colleague5[position])) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.parents_can_only_choose_two));
                            return;
                        }
                        if (verifySpouse(colleague5[position])) {
                            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.spouse_can_only_choose_one));
                            return;
                        }
                        if (position == 0) {
                            fuBool = true;
                        }
                        if (position == 1) {
                            muBool = true;
                        }
                        isShow(mRelationship5, colleague5[position]);
                    }
                });
                dialog5.show();
                break;
            case R.id.next_step:
                isNextPage = true;
                submit();
                break;
            case R.id.right_text:
                isNextPage = false;
                submit();
                break;
        }
    }

    private void submit() {
        String relationship1 = mRelationship1.getText().toString();
        String name1 = mName1.getText().toString();
        String phoneNumber1 = mPhoneNumber1.getText().toString();

        String relationship2 = mRelationship2.getText().toString();
        String name2 = mName2.getText().toString();
        String phoneNumber2 = mPhoneNumber2.getText().toString();

        String relationship3 = mRelationship3.getText().toString();
        String name3 = mName3.getText().toString();
        String phoneNumber3 = mPhoneNumber3.getText().toString();

        String relationship4 = mRelationship4.getText().toString();
        String name4 = mName4.getText().toString();
        String phoneNumber4 = mPhoneNumber4.getText().toString();

        String relationship5 = mRelationship5.getText().toString();
        String name5 = mName5.getText().toString();
        String phoneNumber5 = mPhoneNumber5.getText().toString();
        if (StringUtils.isEmpty(relationship1)) {


            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.please_select_a_direct_relative));
            return;
        }
        if (StringUtils.isEmpty(name1)) {

            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.please_enter_the_name_of_the_contact));
            return;
        }
        if (StringUtils.isEmpty(phoneNumber1)) {


            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.please_enter_the_contact_phone_number));
            return;
        }
        if (!stringFilter(phoneNumber1)) {
            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.wrong_number_format));
            return;
        }
        if (StringUtils.isEmpty(relationship2)) {

            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.please_select_a_colleague));
            return;
        }
        if (StringUtils.isEmpty(name2)) {

            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.please_enter_the_name_of_the_contact));
            return;
        }
        if (StringUtils.isEmpty(phoneNumber2)) {

            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.please_enter_the_contact_phone_number));
            return;
        }
        if (!stringFilter(phoneNumber2)) {

            ToastUtils.showToast(ContactInforActivity.this, getResources().getString(R.string.wrong_number_format));
            return;
        }

        List<BeanMyContact> list = new ArrayList<>();
        BeanMyContact contact1 = getContact(relationship1, name1, phoneNumber1);
        BeanMyContact contact2 = getContact(relationship2, name2, phoneNumber2);
        BeanMyContact contact3 = getContact(relationship3, name3, phoneNumber3);
        BeanMyContact contact4 = getContact(relationship4, name4, phoneNumber4);
        BeanMyContact contact5 = getContact(relationship5, name5, phoneNumber5);
        if (null != contact1)
            list.add(contact1);
        if (null != contact2)
            list.add(contact2);
        if (null != contact3)
            list.add(contact3);
        if (null != contact4)
            list.add(contact4);
        if (null != contact5)
            list.add(contact5);


        String contact = new Gson().toJson(list);
        try {
            contact = Des3.encode(contact, Des3.key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        confirmationInformation(contact);
    }

    private void getContactList() {

    }

    public static boolean stringFilter(String str) throws PatternSyntaxException {
        if (null == str)
            return true;
        // 只允许字母、和空格,+号
        String regEx = "[0-9+ -]+";
        return str.matches(regEx);
    }

    private BeanMyContact getContact(String relationship, String name, String phone) {
        if (StringUtils.isEmpty(relationship) || StringUtils.isEmpty(name) || StringUtils.isEmpty(phone)) {
            return null;
        }
        BeanMyContact contact = new BeanMyContact();
        contact.setRelationship(ConstantParams.getColleague(true, relationship));
        contact.setName(name);
        contact.setMobile(phone);
        return contact;
    }

    /**
     * 验证关系，选择父母不能大于2
     *
     * @return
     */
    private boolean verifyParents(String str) {
        int number = 0;
        String relationship1 = mRelationship1.getText().toString();
        String relationship2 = mRelationship2.getText().toString();
        String relationship3 = mRelationship3.getText().toString();
        String relationship4 = mRelationship4.getText().toString();
        String relationship5 = mRelationship5.getText().toString();
        if ((!StringUtils.isEmpty(relationship1) && ConstantParams.getColleague(true, relationship1).equals("11"))) {
            number++;
        }
        if ((!StringUtils.isEmpty(relationship1) && ConstantParams.getColleague(true, relationship1).equals("12"))) {
            number++;
        }

        if ((!StringUtils.isEmpty(relationship2) && ConstantParams.getColleague(true, relationship2).equals("11"))) {
            number++;
        }
        if ((!StringUtils.isEmpty(relationship2) && ConstantParams.getColleague(true, relationship2).equals("12"))) {
            number++;
        }
        if ((!StringUtils.isEmpty(relationship3) && ConstantParams.getColleague(true, relationship3).equals("11"))) {
            number++;
        }
        if ((!StringUtils.isEmpty(relationship3) && ConstantParams.getColleague(true, relationship3).equals("12"))) {
            number++;
        }
        if ((!StringUtils.isEmpty(relationship4) && ConstantParams.getColleague(true, relationship4).equals("11"))) {
            number++;
        }
        if ((!StringUtils.isEmpty(relationship4) && ConstantParams.getColleague(true, relationship4).equals("12"))) {
            number++;
        }
        if ((!StringUtils.isEmpty(relationship5) && ConstantParams.getColleague(true, relationship5).equals("11"))) {
            number++;
        }
        if ((!StringUtils.isEmpty(relationship5) && ConstantParams.getColleague(true, relationship5).equals("12"))) {
            number++;
        }
        if (ConstantParams.getColleague(true, str).equals("11")) {
            number++;
        }
        if (ConstantParams.getColleague(true, str).equals("12")) {
            number++;
        }
        if (number > 2) {
            return true;
        }
        return false;
    }

    /**
     * 验证关系，选择配偶不能大于2
     *
     * @return
     */
    private boolean verifySpouse(String str) {
        int number = 0;
        String relationship1 = mRelationship1.getText().toString();
        String relationship2 = mRelationship2.getText().toString();
        String relationship3 = mRelationship3.getText().toString();
        String relationship4 = mRelationship4.getText().toString();
        String relationship5 = mRelationship5.getText().toString();
        if (!StringUtils.isEmpty(relationship1) && ConstantParams.getColleague(true, relationship1).equals("3")) {
            number++;
        }
        if (!StringUtils.isEmpty(relationship2) && ConstantParams.getColleague(true, relationship2).equals("3")) {
            number++;
        }
        if (!StringUtils.isEmpty(relationship3) && ConstantParams.getColleague(true, relationship3).equals("3")) {
            number++;
        }
        if (!StringUtils.isEmpty(relationship4) && ConstantParams.getColleague(true, relationship4).equals("3")) {
            number++;
        }
        if (!StringUtils.isEmpty(relationship5) && ConstantParams.getColleague(true, relationship5).equals("3")) {
            number++;
        }
        if (ConstantParams.getColleague(true, str).equals("3")) {
            number++;
        }
        if (number > 1) {
            return true;
        }
        return false;
    }


    private void isShow(TextView text, String str) {
        if (!StringUtils.isEmpty(str)) {
            text.setText(str);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (StringUtils.isEmpty(mPhoneNumber1.getText().toString())) {
            mPhoneNumberErrpr1.setVisibility(View.VISIBLE);
        } else {
            mPhoneNumberErrpr1.setVisibility(View.GONE);
        }
        if (StringUtils.isEmpty(mPhoneNumber2.getText().toString())) {
            mPhoneNumberErrpr2.setVisibility(View.VISIBLE);
        } else {
            mPhoneNumberErrpr2.setVisibility(View.GONE);
        }
        if (StringUtils.isEmpty(mPhoneNumber3.getText().toString())) {
            mPhoneNumberErrpr3.setVisibility(View.VISIBLE);
        } else {
            mPhoneNumberErrpr3.setVisibility(View.GONE);
        }
        if (StringUtils.isEmpty(mPhoneNumber4.getText().toString())) {
            mPhoneNumberErrpr4.setVisibility(View.VISIBLE);
        } else {
            mPhoneNumberErrpr4.setVisibility(View.GONE);
        }
        if (StringUtils.isEmpty(mPhoneNumber5.getText().toString())) {
            mPhoneNumberErrpr5.setVisibility(View.VISIBLE);
        } else {
            mPhoneNumberErrpr5.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    private boolean exitVerification() {
        String relationship1 = mRelationship1.getText().toString();
        String name1 = mName1.getText().toString();
        String phoneNumber1 = mPhoneNumber1.getText().toString();

        String relationship2 = mRelationship2.getText().toString();
        String name2 = mName2.getText().toString();
        String phoneNumber2 = mPhoneNumber2.getText().toString();

        String relationship3 = mRelationship3.getText().toString();
        String name3 = mName3.getText().toString();
        String phoneNumber3 = mPhoneNumber3.getText().toString();

        String relationship4 = mRelationship4.getText().toString();
        String name4 = mName4.getText().toString();
        String phoneNumber4 = mPhoneNumber4.getText().toString();

        String relationship5 = mRelationship5.getText().toString();
        String name5 = mName5.getText().toString();
        String phoneNumber5 = mPhoneNumber5.getText().toString();
        List<BeanMyContact> list = new ArrayList<>();
        BeanMyContact contact1 = getContact(relationship1, name1, phoneNumber1);
        BeanMyContact contact2 = getContact(relationship2, name2, phoneNumber2);
        BeanMyContact contact3 = getContact(relationship3, name3, phoneNumber3);
        BeanMyContact contact4 = getContact(relationship4, name4, phoneNumber4);
        BeanMyContact contact5 = getContact(relationship5, name5, phoneNumber5);
        if (null != contact1)
            list.add(contact1);
        if (null != contact2)
            list.add(contact2);
        if (null != contact3)
            list.add(contact3);
        if (null != contact4)
            list.add(contact4);
        if (null != contact5)
            list.add(contact5);
        if (myContacts.size() != list.size()) {
            return false;
        }
        for (int i = 0; i < myContacts.size(); i++) {
            if (!myContacts.get(0).getRelationship().equals(list.get(0).getRelationship())) {
                return false;
            }
            if (!myContacts.get(0).getName().equals(list.get(0).getName())) {
                return false;
            }
            if (!myContacts.get(0).getMobile().equals(list.get(0).getMobile())) {
                return false;
            }
        }
        return true;
    }

    private void showSave() {
        ConfirmPromptDialog confirm = new ConfirmPromptDialog(this,
                R.mipmap.lingyi5,
                getResources().getString(R.string.are_you_sure_you_quit_without_saving),
                getResources().getString(R.string.cancel),
                getResources().getString(R.string.confirm));
        confirm.setOnClickListener(new ConfirmPromptDialog.OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        confirm.show();
    }

    /**
     * 确认信息
     *
     * @param contact
     */
    private void confirmationInformation(final String contact) {
        PromptTwoBtDialog prompt = new PromptTwoBtDialog(this,
                getResources().getString(R.string.warm_prompt),
                getResources().getString(R.string.select_all_contacts),
                getResources().getString(R.string.confirm),
                getResources().getString(R.string.return_to_add));
        prompt.setOnLeftClickListener(new PromptTwoBtDialog.OnClickListener() {
            @Override
            public void onClick() {
                Map<String, String> params = new HashMap<>();
                params.put("contact", contact);
                mPresenter.request014(params);
            }
        });
        prompt.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (exitVerification()) {
                finish();
            } else {
                showSave();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    public void editViewListener() {

        mName1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (TextUtils.isEmpty(mName1.getText().toString())) {
                        requestPhone(1);
                    }
                }
            }
        });
        mName2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (TextUtils.isEmpty(mName2.getText().toString())) {
                        requestPhone(2);
                    }
                }
            }
        });
        mName3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (TextUtils.isEmpty(mName3.getText().toString())) {
                        requestPhone(3);

                    }
                }
            }
        });
        mName4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (TextUtils.isEmpty(mName4.getText().toString())) {
                        requestPhone(4);
                    }
                }
            }
        });
        mName5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (TextUtils.isEmpty(mName5.getText().toString())) {
                        requestPhone(5);

                    }
                }
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case 1:
                    if (data == null) {
                        return;
                    }
                    //处理返回的data,获取选择的联系人信息**
                    Uri uri = data.getData();
                    String[] contacts = getPhoneContacts(uri);
                    mName1.setText(contacts[0]);
                    mPhoneNumber1.setText(contacts[1]);
                    break;

                case 2:
                    if (data == null) {
                        return;
                    }
                    //处理返回的data,获取选择的联系人信息**
                    Uri uri2 = data.getData();
                    String[] contacts2 = getPhoneContacts(uri2);
                    mName2.setText(contacts2[0]);
                    mPhoneNumber2.setText(contacts2[1]);
                    break;
                case 3:
                    if (data == null) {
                        return;
                    }
                    //处理返回的data,获取选择的联系人信息**
                    Uri uri3 = data.getData();
                    String[] contacts3 = getPhoneContacts(uri3);
                    mName3.setText(contacts3[0]);
                    mPhoneNumber3.setText(contacts3[1]);
                    break;
                case 4:
                    if (data == null) {
                        return;
                    }
                    //处理返回的data,获取选择的联系人信息**
                    Uri uri4 = data.getData();
                    String[] contacts4 = getPhoneContacts(uri4);
                    mName4.setText(contacts4[0]);
                    mPhoneNumber4.setText(contacts4[1]);
                    break;
                case 5:
                    if (data == null) {
                        return;
                    }
                    //处理返回的data,获取选择的联系人信息**
                    Uri uri5 = data.getData();
                    String[] contacts5 = getPhoneContacts(uri5);
                    mName5.setText(contacts5[0]);
                    mPhoneNumber5.setText(contacts5[1]);
                    break;

            }
        } catch (Exception e) {

        }

    }

    private String[] getPhoneContacts(Uri uri) {

        String[] contact = new String[2];
        //得到ContentResolver对象**
        ContentResolver cr = getContentResolver();
        //取得电话本中开始一项的光标**
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //取得联系人姓名**
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0] = cursor.getString(nameFieldColumnIndex);
            //取得电话号码**
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if (phone != null) {
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }


    void requestPhone( int number) {
        mPresenter.requestPermissions(new RxPermissions(ContactInforActivity.this),number);


    }


    @Override
    public void get014() {
        EventBus.getDefault().post(new EventPersonalInformation(EventPersonalInformation.CONTACT, true, false));
        finish();
    }

    @Override
    public void get008(List<BeanMyContact> contacts) {
        this.myContacts = contacts;
        try {
            for (int i = 0; i < contacts.size(); i++) {
                BeanMyContact contact = contacts.get(i);
                if (i == 0) {
                    mName1.setText(contact.getName());
                    mPhoneNumber1.setText(contact.getMobile());
                    isShow(mRelationship1, ConstantParams.getColleague(false, contact.getRelationship()));
                } else if (i == 1) {
                    mName2.setText(contact.getName());
                    mPhoneNumber2.setText(contact.getMobile());
                    isShow(mRelationship2, ConstantParams.getColleague(false, contact.getRelationship()));
                } else if (i == 2) {
                    mName3.setText(contact.getName());
                    mPhoneNumber3.setText(contact.getMobile());
                    isShow(mRelationship3, ConstantParams.getColleague(false, contact.getRelationship()));
                } else if (i == 3) {
                    mName4.setText(contact.getName());
                    mPhoneNumber4.setText(contact.getMobile());
                    isShow(mRelationship4, ConstantParams.getColleague(false, contact.getRelationship()));
                } else if (i == 4) {
                    mName5.setText(contact.getName());
                    mPhoneNumber5.setText(contact.getMobile());
                    isShow(mRelationship5, ConstantParams.getColleague(false, contact.getRelationship()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void permissionsSuccess(int type) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, type);
    }

    @Override
    public void permissionsCancle() {
        ComUtils.showPermissions(getResources().getString(R.string.permission_contacts), ContactInforActivity.this);
    }
}