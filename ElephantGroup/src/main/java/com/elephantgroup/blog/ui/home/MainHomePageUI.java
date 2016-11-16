package com.elephantgroup.blog.ui.home;

import android.view.View;
import android.widget.TextView;

import com.elephantgroup.blog.R;
import com.elephantgroup.blog.ui.base.BaseFragment;
import com.elephantgroup.blog.ui.base.BaseFragmentActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 首页
 * Created 2016/11/14.
 */
public class MainHomePageUI extends BaseFragmentActivity {


    @Bind(R.id.main_home)
    TextView mainHome;
    @Bind(R.id.main_goods)
    TextView mainGoods;
    @Bind(R.id.main_myself)
    TextView mainMyself;

    private Stack<String> mStack = new Stack<>();
    private Map<String, BaseFragment> map = new HashMap<>();

    @Override
    protected void setContentView() {
        setContentView(R.layout.home_page_layout);
    }

    @OnClick({R.id.main_home, R.id.main_goods, R.id.main_myself})
    public void switchPageMethod(View view) {
        switch (view.getId()) {
            case R.id.main_home:
                showFragment(MainHomeFragment.class);
                break;
            case R.id.main_goods:
                showFragment(MainGoodsFragment.class);
                break;
            case R.id.main_myself:
                showFragment(MySelfFragment.class);
                break;
        }
        selectChanged(view.getId());
    }

    @Override
    protected void initData() {
        mainHome.setSelected(true);
        showFragment(MainHomeFragment.class);
        showFragment(MainGoodsFragment.class);
        showFragment(MySelfFragment.class);
    }

    /**
     * 显示新的Fragment
     *
     * @param cs fragment
     */
    private void showFragment(Class<? extends BaseFragment> cs) {
        try {
            BaseFragment fragment;
            // 如果mStack大于0，则隐藏
            if (mStack.size() > 0) {
                getSupportFragmentManager().beginTransaction().hide(map.get(mStack.pop())).commitAllowingStateLoss();
            }
            mStack.add(cs.getSimpleName());
            // 如果已经存在，则显示之前的，如果不存在，则创建新的
            if (map.containsKey(cs.getSimpleName())) {
                fragment = map.get(cs.getSimpleName());
                getSupportFragmentManager().beginTransaction().show(fragment).commitAllowingStateLoss();
            } else {
                fragment = cs.newInstance();
                map.put(cs.getSimpleName(), fragment);
                getSupportFragmentManager().beginTransaction().add(R.id.main_frame, fragment, cs.getSimpleName()).commitAllowingStateLoss();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void selectChanged(final int resId) {
        mainHome.setSelected(false);
        mainGoods.setSelected(false);
        mainMyself.setSelected(false);
        switch (resId) {
            case R.id.main_home:
                mainHome.setSelected(true);
                break;
            case R.id.main_goods:
                mainGoods.setSelected(true);
                break;

            case R.id.main_myself:
                mainMyself.setSelected(true);
                break;
        }
    }

}
