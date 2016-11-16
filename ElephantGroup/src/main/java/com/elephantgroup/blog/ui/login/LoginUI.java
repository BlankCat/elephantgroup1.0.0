package com.elephantgroup.blog.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.elephantgroup.blog.R;
import com.elephantgroup.blog.listener.NetRequestListener;
import com.elephantgroup.blog.netutils.NetRequestImpl;
import com.elephantgroup.blog.ui.base.BaseFragmentActivity;
import com.elephantgroup.blog.ui.home.MainHomePageUI;
import com.elephantgroup.blog.util.MySharedPrefs;
import com.elephantgroup.blog.util.Utils;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * 登陆界面
 * Created on 2016/11/11.
 */
public class LoginUI extends BaseFragmentActivity implements NetRequestListener {

    @Bind(R.id.login_account_et)
    EditText loginAccountEt;
    @Bind(R.id.login_pwd_et)
    EditText loginPwdEt;
    @Bind(R.id.loginBtn)
    TextView loginBtn;
    @Bind(R.id.app_back)
    ImageView appBack;


    @Override
    protected void setContentView() {
        setContentView(R.layout.login_layout);
    }

    @Override
    protected void initData() {
        appBack.setVisibility(View.GONE);
        setTitle(getString(R.string.login_btn));
    }

    /**
     * 登陆页面监听
     */
    @OnClick({R.id.login_register_btn, R.id.loginBtn, R.id.login_pwd_forget})
    public void loginMethod(View view) {
        switch (view.getId()) {
            case R.id.login_register_btn://注册
                startActivity(new Intent(LoginUI.this, RegisterUI.class));
                Utils.openNewActivityAnim(LoginUI.this, false);
                break;
            case R.id.login_pwd_forget://忘记密码
                startActivity(new Intent(LoginUI.this, ForgetPwdUI.class));
                Utils.openNewActivityAnim(LoginUI.this, false);
                break;
            case R.id.loginBtn://登陆
                if (!TextUtils.isEmpty(loginAccountEt.getText().toString()) && !TextUtils.isEmpty(loginPwdEt.getText().toString())){
                    NetRequestImpl.login(loginAccountEt.getText().toString(), loginPwdEt.getText().toString(), this);
                }else{
                    showToast(getString(R.string.login_warning));
                }
                break;
        }
    }

    /**
     * EditText 文本改变监听
     */
    @OnTextChanged(value = {R.id.login_account_et, R.id.login_pwd_et}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable s) {
        int accountC = loginAccountEt.getText().toString().length();
        int passC = loginPwdEt.getText().toString().length();
        if (accountC > 0 && passC > 0) {
            loginBtn.setEnabled(true);
        } else {
            loginBtn.setEnabled(false);
        }
    }


    @Override
    public void start() {
        showToast("开始");
    }

    @Override
    public void onResponse(JSONObject response) {
        showToast(response.toString());

        //是个人信息
        MySharedPrefs.write(this, MySharedPrefs.FILE_USER, MySharedPrefs.KEY_LOGIN_USERINFO, response.toString());

        startActivity(new Intent(LoginUI.this, MainHomePageUI.class));
        Utils.openNewActivityAnim(LoginUI.this, true);
    }

    @Override
    public void error(int errorCode, String errorMsg) {
        showToast("失败");
    }

}
