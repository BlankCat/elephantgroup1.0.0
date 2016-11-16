package com.elephantgroup.blog.netutils;

import android.util.Log;

import com.elephantgroup.blog.listener.NetRequestListener;
import com.elephantgroup.blog.vo.UserInfo;

import java.util.List;
import java.util.logging.Logger;

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
     * 登录
     * @param userPhone 用户名
     * @param userPwd 密码
     * @param requestListener 回调接口
     * */
    public static void login(String userPhone, String userPwd,final NetRequestListener requestListener){
        if (requestListener != null){
            requestListener.start();
        }
        Observable<UserInfo> observable =  BaseModelImpl.getInstance().login(userPhone,userPwd);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("**********","error");
                    }

                    @Override
                    public void onNext(Object userInfo) {
                      Log.e("zjf---->",userInfo.toString());
                        if (requestListener != null){
                            Log.e("zjf---->",userInfo.toString());
                            requestListener.onResponse(userInfo);
                        }
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
        Observable<UserInfo> observable =  BaseModelImpl.getInstance().register(userPhone,userPwd);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object userInfo) {
                        if (requestListener != null){
                            requestListener.onResponse(userInfo);
                        }
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
        Observable<List<UserInfo>> observable = BaseModelImpl.getInstance().getAllUser();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object userList) {
                if (requestListener != null){
                    requestListener.onResponse(userList);
                }
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
                .subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object userList) {
                if (requestListener != null){
                    requestListener.onResponse(userList);
                }
            }
        });
    }

}
