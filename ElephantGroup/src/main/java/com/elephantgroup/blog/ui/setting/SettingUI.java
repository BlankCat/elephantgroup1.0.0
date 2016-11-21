package com.elephantgroup.blog.ui.setting;

import android.view.View;

import com.elephantgroup.blog.R;
import com.elephantgroup.blog.ui.base.BaseFragmentActivity;
import com.elephantgroup.blog.util.Constans;
import com.elephantgroup.blog.util.Utils;

import butterknife.OnClick;

/**
 * 设置页面
 * Created on 2016/11/21.
 */
public class SettingUI extends BaseFragmentActivity {

    @Override
    protected void setContentView() {
        setContentView(R.layout.setting_layout);
    }

    @Override
    protected void initData() {
        setTitle(getString(R.string.setting));
    }

    @OnClick({R.id.updatePwd,R.id.clearCarch,R.id.userBack,R.id.userAgreement,R.id.aboutUs,R.id.exitApp})
    public void clickMehthod(View view){
        switch (view.getId()){
            case R.id.updatePwd://修改密码
                break;
            case R.id.clearCarch://清除缓存
                break;
            case R.id.userBack://用户反馈
                break;
            case R.id.userAgreement://用户协议
                Utils.intentWebView(SettingUI.this, Constans.URL_USER_AGREEMENY,getString(R.string.user_agreement),true);
                break;
            case R.id.aboutUs://关于我们
                Utils.intentWebView(SettingUI.this, Constans.URL_USER_AGREEMENY,getString(R.string.user_agreement),true);
                break;
            case R.id.exitApp://退出登录
                exitApp();
                break;
        }
    }

}
