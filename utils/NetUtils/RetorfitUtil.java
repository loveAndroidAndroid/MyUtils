package com.rmyh.myutils.utils.NetUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rmyh.yanxun.config.AddCookiesInterceptor;
import com.rmyh.yanxun.config.NetConfig;
import com.rmyh.yanxun.config.ReceivedCookiesInterceptor;
import com.rmyh.yanxun.config.RmyhApplication;
import com.rmyh.yanxun.model.http.ApiUrls;
import com.rmyh.yanxun.model.http.ResponseInfoAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ResponseInfoAPI工具类
 * 2017/2/17 19:21
 */

public class RetorfitUtil {

    private static RetorfitUtil instance;
    //记得关联
    private Retrofit retrofit;
    private Gson gson;

    private RetorfitUtil() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
                    .create();
        }
        retrofit = new Retrofit.Builder()
                //1.配置主机地址，baseUrl必须以斜杠结尾
                .baseUrl(ApiUrls.BaseUrl)
                //.client(new NetConfig().getClient())
                .client(genericClient())
                //2.解析json的工具
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private OkHttpClient genericClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new ReceivedCookiesInterceptor(RmyhApplication.getContext()))
                .addInterceptor(new AddCookiesInterceptor(RmyhApplication.getContext()))
                .build();
        return client;
    }

    /**
     * 静态内部类
     */
    public static RetorfitUtil getInstance() {
        if (instance == null) {
            synchronized (RetorfitUtil.class) {
                instance = new RetorfitUtil();
            }
        }
        return instance;
    }

    /**
     * 得到ResponseInfoAPI的对象
     *
     * @return
     */
    public ResponseInfoAPI create() {
        return retrofit.create(ResponseInfoAPI.class);
    }
}
