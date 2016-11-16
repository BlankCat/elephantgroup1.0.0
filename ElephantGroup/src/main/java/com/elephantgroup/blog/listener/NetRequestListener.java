package com.elephantgroup.blog.listener;


import com.alibaba.fastjson.JSONObject;

/**
 * 网络请求结果接口
 * Created  on 2016/11/10.
 */
public interface NetRequestListener {

    /**
     * 网络请求开始
     * */
    void start();

    /**
     * 网络请求成功
     * @param response 返回数据
     * */
    void onResponse(JSONObject response);


    /** 网络请求失败
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     * */
    void error(int errorCode, String errorMsg);

}
