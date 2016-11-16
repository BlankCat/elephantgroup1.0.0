package com.elephantgroup.blog.netutils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求工具类
 * Created on 2016/11/10.
 * 创建一个Retrofit的示例，并完成相应的配置
 * baseUrl网络请求URL相对固定的地址
 * addConverterFactory方法表示需要用什么转换器来解析返回值
 */
public class BaseModelImpl {

    private static DataService blueService;

    public static DataService getInstance(){
        if(blueService == null ){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UrlConstansApi.BASE_URL)
                    //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(GsonConverterFactory.create())
                    //增加返回值为Oservable<T>的支持
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            blueService = retrofit.create(DataService.class);
        }
        return blueService;
    }

}
