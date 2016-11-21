package com.elephantgroup.blog.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.elephantgroup.blog.R;
import com.elephantgroup.blog.custom.RippleTextView;
import com.elephantgroup.blog.listener.NetRequestListener;
import com.elephantgroup.blog.netutils.NetRequestImpl;
import com.elephantgroup.blog.ui.base.BaseFragmentActivity;
import com.elephantgroup.blog.util.Constans;
import com.elephantgroup.blog.util.Utils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 注册界面
 * Created on 2016/11/14.
 */
public class RegisterUI extends BaseFragmentActivity implements NetRequestListener {

    @Bind(R.id.register_account_et)
    MaterialEditText registerAccountEt;
    @Bind(R.id.register_pwd_et)
    MaterialEditText registerPwdEt;
    @Bind(R.id.registerBtn)
    RippleTextView registerBtn;
    @Bind(R.id.userAgreement)
    RippleTextView userAgreement;


    @Override
    protected void setContentView() {
        setContentView(R.layout.register_layout);
    }

    @Override
    protected void initData() {
        setTitle(getString(R.string.register_btn));
    }

    /**
     * 注册监听
     */
    @OnClick({R.id.registerBtn,R.id.userAgreement})
    public void registerMethod(View view) {
        switch (view.getId()){
            case R.id.registerBtn:
                if (checkPhone(registerAccountEt.getText().toString()) && checkPassword(registerPwdEt.getText().toString())) {
                    NetRequestImpl.register(registerAccountEt.getText().toString(), registerPwdEt.getText().toString(), this);
                }
                break;
            case R.id.userAgreement:
                Utils.intentWebView(RegisterUI.this, Constans.URL_USER_AGREEMENY,getString(R.string.user_agreement),true);
                break;
        }
    }

    /**
     * EditText 文本改变监听
     */
    @OnTextChanged(value = {R.id.register_account_et, R.id.register_pwd_et}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable s) {
        int accountC = registerAccountEt.getText().toString().length();
        int passC = registerPwdEt.getText().toString().length();
        if (accountC > 0 && passC > 0) {
            registerBtn.setEnabled(true);
        } else {
            registerBtn.setEnabled(false);
        }
    }

    /**
     * @param password 密码
     * @return 验证码密码是否正确
     */
    public boolean checkPassword(final String password) {
        if (TextUtils.isEmpty(password)) {//密码不能为空
            showToast(getString(R.string.check_password_error_empty));
            return false;
        } else if (password.length() < 6 || password.length() > 16) {//密码长度在6-16之前
            showToast(getString(R.string.check_password_error_length));
            return false;
        }
        return true;
    }

    /**
     * @param phone 手机号
     *              手机号码格式验证
     */
    private boolean checkPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            showToast(getString(R.string.check_phone_empty));
            return false;
        } else {
//			^[1][358][0-9]{9}$     ^((1[358][0-9])|(14[57])|(17[0678]))\d{8}$  ^[1][3578][0-9]{9}$
            Pattern pattern = Pattern.compile("^((1[358][0-9])|(14[57])|(17[0678]))\\d{8}$");
            Matcher matcher = pattern.matcher(phone);
            if (!matcher.matches()) {
                showToast(getString(R.string.check_phone_format_error));
                return false;
            }
        }
        return true;
    }

    @Override
    public void start() {
        showToast("开始");
    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void error(int errorCode, String errorMsg) {
        showToast("失败");
    }

}
