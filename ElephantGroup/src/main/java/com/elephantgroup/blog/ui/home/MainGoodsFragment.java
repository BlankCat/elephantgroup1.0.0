package com.elephantgroup.blog.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elephantgroup.blog.R;
import com.elephantgroup.blog.ui.base.BaseFragment;

/**
 * 干货页面
 * Created on 2016/11/15.
 */
public class MainGoodsFragment extends BaseFragment {
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
        view = inflater.inflate(R.layout.main_goods_layout, container, false);
        initData();
        return view;
    }

    private void initData(){

    }
}
