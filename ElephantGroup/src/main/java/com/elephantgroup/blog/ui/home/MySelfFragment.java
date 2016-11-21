package com.elephantgroup.blog.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elephantgroup.blog.R;
import com.elephantgroup.blog.custom.AlwaysMarqueeTextView;
import com.elephantgroup.blog.ui.base.BaseFragment;
import com.elephantgroup.blog.ui.setting.SettingUI;
import com.elephantgroup.blog.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的页面
 * Created on 2016/11/14.
 */
public class MySelfFragment extends BaseFragment {

    @Bind(R.id.app_title)
    AlwaysMarqueeTextView appTitle;
    @Bind(R.id.app_back)
    ImageView appBack;
    @Bind(R.id.nickTv)
    TextView nickTv;//昵称
    @Bind(R.id.singTv)
    TextView singTv;//签名
    private boolean isFirstLoadedData;
    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
        isFirstLoadedData = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!isFirstLoadedData && view != null) {
            ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.main_myself_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        appTitle.setVisibility(View.VISIBLE);
        appTitle.setText(getString(R.string.it_elephant));
        appBack.setVisibility(View.GONE);
    }

    @OnClick({R.id.myMark,R.id.labelManger,R.id.setting})
    private void clickMethod(View view){
        switch (view.getId()){
            case R.id.myMark://我的收藏

                break;
            case R.id.labelManger://标签管理

                break;
            case R.id.setting://设置
                startActivity(new Intent(getActivity(), SettingUI.class));
                Utils.openNewActivityAnim(getActivity(), false);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
