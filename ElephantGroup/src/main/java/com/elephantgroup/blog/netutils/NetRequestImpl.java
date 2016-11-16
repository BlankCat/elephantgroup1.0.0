package com.elephantgroup.blog.netutils;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.elephantgroup.blog.listener.NetRequestListener;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 网络请求实现类
 * Created on 2016/11/10.
 */
public class NetRequestImpl {

    /**
     * 请求数据成功回调
     * @param requestListener 回调监听
     * @param responseBody 回调数据
     * */
    public static void netRequestResponse(NetRequestListener requestListener,ResponseBody responseBody){
        if (requestListener != null){
            try {
                JSONObject jsonObject = JSONObject.parseObject(responseBody.string());
                requestListener.onResponse(jsonObject);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 登录
     * @param userPhone 用户名
     * @param userPwd 密码
     * @param requestListener 回调接口
     * */
    public static void login(String userPhone, String userPwd,final NetRequestListener requestListener){
        if (requestListener != null){
            requestListener.start();
        }
        Observable<ResponseBody> observable =  BaseModelImpl.getInstance().login(userPhone,userPwd);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("**********","error");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        netRequestResponse(requestListener,responseBody);
                    }
                });
    }


    /**
     * 登录
     * @param userPhone 用户名
     * @param userPwd 密码
     * @param requestListener 回调接口
     * */
    public static void register(String userPhone, String userPwd,final NetRequestListener requestListener){
        if (requestListener != null){
            requestListener.start();
        }
        Observable<ResponseBody> observable =  BaseModelImpl.getInstance().register(userPhone,userPwd);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        netRequestResponse(requestListener,responseBody);
                    }
                });

    }

    /**
     * 获取所有用户信息
     * @param requestListener 回调接口
     * */
    public static void getAllUser(final NetRequestListener requestListener){
        if (requestListener != null){
            requestListener.start();
        }
        Observable<ResponseBody> observable = BaseModelImpl.getInstance().getAllUser();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                netRequestResponse(requestListener,responseBody);
            }
        });
    }

    /**
     * 获取文章列表
     * @param requestListener 回调接口
     * */
    public static void getAllArticel(final NetRequestListener requestListener){
        if (requestListener != null){
            requestListener.start();
        }
        Observable<ResponseBody> observable = BaseModelImpl.getInstance().getAllArticel();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                netRequestResponse(requestListener,responseBody);
            }
        });
    }

}
