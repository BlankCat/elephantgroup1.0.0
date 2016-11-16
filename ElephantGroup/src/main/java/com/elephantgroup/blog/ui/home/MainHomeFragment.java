package com.elephantgroup.blog.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.elephantgroup.blog.R;
import com.elephantgroup.blog.custom.AlwaysMarqueeTextView;
import com.elephantgroup.blog.ui.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页fragment
 * Created on 2016/11/15.
 */
public class MainHomeFragment extends BaseFragment {
    @Bind(R.id.app_back)
    ImageView appBack;
    @Bind(R.id.app_title)
    AlwaysMarqueeTextView appTitle;
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
        view = inflater.inflate(R.layout.main_home_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        appTitle.setVisibility(View.VISIBLE);
        appTitle.setText(getString(R.string.it_elephant));
        appBack.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
