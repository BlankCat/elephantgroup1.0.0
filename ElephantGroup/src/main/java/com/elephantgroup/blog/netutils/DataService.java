package com.elephantgroup.blog.netutils;


import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 创建业务请求接口
 * Created  on 2016/11/10.
 * @ FormUrlEncoded 将会自动将请求参数的类型调整为application/x-www-form-urlencoded (不能用于GET请求)
 * @ Field 传递少量参数
 * @ FieldMap 传递多个参数
 * @ Body 传递更多参数
 */
public interface DataService {

    /**
     * 登录
     * login 动态url
     * phonenumber 用户名
     * password 密码
     * */
    @FormUrlEncoded
    @POST(UrlConstansApi.LOGIN_URL)
    Observable<ResponseBody> login(@Field("phonenumber") String userPhone, @Field("password") String userPwd);

    /**
     * 注册
     * register 动态url
     * phonenumber 手机号
     * password 密码
     * */
    @FormUrlEncoded
    @POST(UrlConstansApi.REGISTER_URL)
    Observable<ResponseBody> register(@Field("phonenumber") String userPhone, @Field("password") String userPwd);

    /**
     * 获取所有用户
     * getAllUser 动态url
     * */
    @POST(UrlConstansApi.GETALLUSER_URL)
    Observable<ResponseBody> getAllUser();

    /**
     * 获取文章列表
     * getAllUser 动态url
     * */
    @POST(UrlConstansApi.GETAllARTICEL_URL)
    Observable<ResponseBody> getAllArticel();

}
