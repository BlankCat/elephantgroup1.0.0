package com.elephantgroup.blog.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.elephantgroup.blog.R;
import com.elephantgroup.blog.listener.NetRequestListener;
import com.elephantgroup.blog.netutils.NetRequestImpl;
import com.elephantgroup.blog.netutils.UrlConstansApi;
import com.elephantgroup.blog.ui.base.BaseFragmentActivity;
import com.elephantgroup.blog.util.LogUtil;
import com.elephantgroup.blog.util.OkHttpUtil;
import com.elephantgroup.blog.util.Utils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void setContentView() {
        setContentView(R.layout.login_layout);
    }

    @Override
    protected void initData() {
        appBack.setVisibility(View.GONE);
        setTitle(getString(R.string.login_btn));
    }

    private static TreeMap<String, String> paramsMap = new TreeMap<String, String>();

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
                String path = UrlConstansApi.BASE_URL + UrlConstansApi.LOGIN_URL;
                paramsMap.clear();
                paramsMap.put("phonenumber", loginAccountEt.getText().toString());
                paramsMap.put("password", loginPwdEt.getText().toString());

                OkHttpUtil.asynHttpPost(path, paramsMap, new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        LogUtil.e("------------>"+e);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        int code = response.code();
                        String result = response.body().string();
                        Map<String, Object> jsonMap = JSONObject.parseObject(result);
                        LogUtil.e("------------>"+jsonMap.toString());
                        if ("200".equals(jsonMap.get("code"))) {
                            String message = jsonMap.get("message").toString();
                            LogUtil.e("------------>"+message);
                            LogUtil.e("登录成功消息"+message);

                        }

                    }
                });

                break;

//                if (!TextUtils.isEmpty(loginAccountEt.getText().toString()) && !TextUtils.isEmpty(loginPwdEt.getText().toString())){
//                    NetRequestImpl.login(loginAccountEt.getText().toString(), loginPwdEt.getText().toString(), this);
////                    startActivity(new Intent(LoginUI.this, MainHomePageUI.class));
////                    Utils.openNewActivityAnim(LoginUI.this, true);
//                }else{
//                    showToast(getString(R.string.login_warning));
//                }
//                break;
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
    public void onResponse(Object response) {
        Log.e("zjf----1--->", response.toString());

        Log.e("zjf----1--->", response.toString());
    }

    @Override
    public void error(int errorCode, String errorMsg) {
        showToast("失败");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "LoginUI Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.elephantgroup.blog.ui.login/http/host/path")
        );
//        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "LoginUI Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.elephantgroup.blog.ui.login/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
