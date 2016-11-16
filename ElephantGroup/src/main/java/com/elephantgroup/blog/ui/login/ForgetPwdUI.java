package com.elephantgroup.blog.ui.login;

import com.elephantgroup.blog.R;
import com.elephantgroup.blog.ui.base.BaseFragmentActivity;

/**
 * 忘记密码页面
 * Created on 2016/11/14.
 */
public class ForgetPwdUI extends BaseFragmentActivity {
    @Override
    protected void setContentView() {
        setContentView(R.layout.forget_pwd_layout);
    }

    @Override
    protected void initData() {
        setTitle(getString(R.string.login_forgot));
    }
}
