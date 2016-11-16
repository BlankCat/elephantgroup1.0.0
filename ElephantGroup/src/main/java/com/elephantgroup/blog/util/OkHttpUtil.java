package com.elephantgroup.blog.util;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;


public class OkHttpUtil {
	
	private static final String CHARSET_NAME = "UTF-8";
	private static final OkHttpClient mOkHttpClient = new OkHttpClient();
	
	// 默认5秒超时
	static {
		mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
	}
	
//	/**
//	 * 异步http get请求
//	 * @param url 请求的url
//	 * @param params 附带的参数
//	 * @param responseCallback 回调接口
//	 */
//	public static Call asynHttpGet(String url, List<BasicNameValuePair> params,
//			Callback responseCallback) {
//
//		if (params != null && params.size() > 0) {
//			url = attachHttpGetParams(url, params);
//		}
//
//		Request request = new Request.Builder().url(url).build();
//
//		if (responseCallback == null) {
//			return enqueue(request);
//		} else {
//		    return enqueue(request, responseCallback);
//		}
//	}

	//post之前进行预处理，其中exclusiveParamKeys为不参与sign加密的key的数组
	public static TreeMap<String, String> postWithTokenPreHandle(TreeMap<String, String> params){
//		if(Utils.validateToken()){
//			TokenVo tokenVo = Utils.getTokenFromCache();
//			params.put("token", tokenVo.getToken());
//		}else{
//			TokenVo tokenVo = Utils.getHttpToken();
//			if(tokenVo==null){
//				MyToast.showToast(AppAplication.context,"token验证失败!");
//			}
//		}
		params.put("token", "A6ADB8C2256D18F5770793CFE4FAA804");
		params.put("timeStamp", String.valueOf(System.currentTimeMillis()));
		return params;
	}

	//带token的网络请求
	public static void postSyncWithToken(String url,final TreeMap<String, String> params, Callback responseCallback){
		TreeMap<String, String> params2 = postWithTokenPreHandle(params);
		asynHttpPost(url,params2,responseCallback);
	}
	//带token的网络请求
	public static void postSync(String url,final TreeMap<String, String> params, Callback responseCallback){
		params.put("timeStamp", String.valueOf(System.currentTimeMillis()));
		asynHttpPost(url,params,responseCallback);
	}
	/**
	 * 异步http post请求
	 * @param url 请求的url
	 * @param params 附带的参数
	 * @param responseCallback 回调接口
	 */
	public static void asynHttpPost(String url, Map<String, String> params,
			Callback responseCallback) {
		RequestBody body = null;
		if (params != null && params.size() > 0) {
			FormEncodingBuilder builder = new FormEncodingBuilder();
			for (Entry<String, String> entry : params.entrySet()) {
				builder.add(entry.getKey(), entry.getValue());
			}
			body = builder.build();
		}

		Request request = null;
		
		if (body != null) {
			request = new Request.Builder().url(url).post(body).build();
		} else {
			request = new Request.Builder().url(url).build();
		}
		
		if (responseCallback == null) {
			enqueue(request);
		} else {
			enqueue(request, responseCallback);
		}
	}


	private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
	/**
	 * 异步http post请求
	 * 表单上传多张文件
	 */
	public static void postMultipart(String url, Map<String, String> params,List<Map<String,File>> files,Callback responseCallback) {
		RequestBody body =null;
		if (params != null && params.size() > 0) {
			MultipartBuilder builder= new MultipartBuilder().type(MultipartBuilder.FORM);
			for (Entry<String, String> entry : params.entrySet()) {
				builder.addFormDataPart(entry.getKey(), entry.getValue());
			}

			if(files!=null&&files.size()>0){
				for(Map<String,File> filesMap:files){
					if(filesMap!=null){
						for (String key:filesMap.keySet()) {
							builder.addFormDataPart(key, key, RequestBody.create(MEDIA_TYPE_PNG, filesMap.get(key)));
						}
					}
				}
			}
			body = builder.build();
		}

		Request request = null;

		if (body != null) {
			request = new Request.Builder().url(url).post(body).build();
		} else {
			request = new Request.Builder().url(url).build();
		}
		if (responseCallback == null) {
			enqueue(request);
		} else {
			enqueue(request, responseCallback);
		}
	}

	public static void asynHttpPostWithUpload(String url, RequestBody requestBody,
            Callback responseCallback) {
	    Request request = new Request.Builder().url(url).post(requestBody).build();
	    if (responseCallback == null) {
            enqueue(request);
        } else {
            enqueue(request, responseCallback);
        }
	}

//	//同步不带token的方法
//	public static Response postSync(String url, TreeMap<String,String> params) throws IOException {
//		params.put("timeStamp", String.valueOf(System.currentTimeMillis()));
//		params.put("sign", ParamsSign.value(params, Constants.SIGN));
//		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
//		for (Entry<String, String> entry : params.entrySet()) {
//			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//		}
//		return execute(url,list);
//	}

//	//同步带token的方法
//	public static Response postSyncWithToken(String url, TreeMap<String,String> params) throws IOException {
//		TreeMap<String,String> params2 = postWithTokenPreHandle(params);
//		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
//		for (Entry<String, String> entry : params2.entrySet()) {
//			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//		}
//		return execute(url,list);
//	}

	/**
	 * 同步执行 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
//    public static Response execute(String url, List<BasicNameValuePair> params)
//            throws IOException {
//		if (params != null && params.size() > 0) {
//            url = attachHttpGetParams(url, params);
//        }
//
//        Request request = new Request.Builder().url(url).build();
//        return mOkHttpClient.newCall(request).execute();
//    }
	
	/**
	 * 同步执行，不会开启异步线程。
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static Response execute(Request request) throws IOException {
		return mOkHttpClient.newCall(request).execute();
	}

	/**
	 * 开启异步线程访问网络
	 * 
	 * @param request
	 * @param responseCallback
	 */
	public static Call enqueue(Request request, Callback responseCallback) {
	    Call call = mOkHttpClient.newCall(request);
		/*mOkHttpClient.newCall(request)*/call.enqueue(responseCallback);
		return call;
	}

	/**
	 * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
	 * 
	 * @param request
	 */
	public static Call enqueue(Request request) {
	    Call call = mOkHttpClient.newCall(request);
		/*mOkHttpClient.newCall(request)*/call.enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {

			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {

			}
		});
		return call;
	}

	public static String getStringFromServer(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();
		Response response = execute(request);
		if (response.isSuccessful()) {
			String responseUrl = response.body().string();
			return responseUrl;
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}

	/**
	 * 这里使用了HttpClinet的API。只是为了方便
	 * 
	 * @param params
	 * @return
	 */
//	public static String formatParams(List<BasicNameValuePair> params) {
//		return URLEncodedUtils.format(params, CHARSET_NAME);
//	}

	/**
	 * 为HttpGet 的 url 方便的添加多个name value 参数。
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
//	public static String attachHttpGetParams(String url,
//			List<BasicNameValuePair> params) {
//		return url + "?" + formatParams(params);
//	}

	/**
	 * 为HttpGet 的 url 方便的添加1个name value 参数。
	 * 
	 * @param url
	 * @param name
	 * @param value
	 * @return
	 */
	public static String attachHttpGetParam(String url, String name,
			String value) {
		return url + "?" + name + "=" + value;
	}
	
	/**
	 * 获取服务器返回的json串中的state值
	 * @param json
	 * @return 如果解析失败返回 -100
	 */
	public static int getState(String json) {
		int result = -100;
		
		//{"state":0,"msg":"ok!","data":{"notice":"0","solve":"1"}}

		try {
			JSONObject jsonObject = new JSONObject(json);
			if (!jsonObject.isNull("state")) {
				result = jsonObject.getInt("state");
			}
		} catch (JSONException e) {
//			e.printStackTrace();
		}
		return result;
	}
}
