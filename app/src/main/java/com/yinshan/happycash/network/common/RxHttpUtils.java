package com.yinshan.happycash.network.common;


import android.text.TextUtils;

import com.yinshan.happycash.network.common.base.BaseURL;
import com.yinshan.happycash.network.common.network.GsonAdapter;
import com.yinshan.happycash.network.common.network.NullOnEmptyConverterFactory;
import com.yinshan.happycash.network.common.network.RequestInterceptor;
import com.yinshan.happycash.network.common.network.ResponseInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.OkHttpClient.*;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 *        ┃　　　┃   神兽保佑
 *        ┃　　　┃   代码无BUG！
 *        ┃　　　┗━━━┓
 *        ┃　　　　　　　┣┓
 *        ┃　　　　　　　┏┛
 *        ┗┓┓┏━┳┓┏┛
 *           ┃┫┫　┃┫┫
 *           ┗┻┛　┗┻┛
 *
 *  @author  admin
 *  on 2018/1/30
 *
 */

public class RxHttpUtils {
    private static RxHttpUtils instance;
    private static String baseUrl;
    public static final String HARVESTER_URL = BaseURL.UPLOADDATA;

    //单例
    public static RxHttpUtils getInstance() {
        if (instance == null) {
            synchronized (RxHttpUtils.class) {
                if (instance == null) {
                    instance = new RxHttpUtils();
                }
            }
        }
        return instance;
    }
    public RxHttpUtils baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
//    private  RxHttpUtils() {
//        //Retrofit
//         mRetrofit = new Retrofit.Builder()
//                .client(getOkHttpClient())
//                 .addConverterFactory(GsonConverterFactory.create(GsonAdapter.buildGson()))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                 .addConverterFactory(new NullOnEmptyConverterFactory())
//                .baseUrl(BaseURL.getBaseURL())//替换为你自己的BaseUrl
//                .build();
//    }

    public  <T> T createApi(Class<T> serviceClass) {
        return getRetrofitBuilder().build().create(serviceClass);
    }

    private static Retrofit.Builder getRetrofitBuilder(){
        Retrofit.Builder builder= new Retrofit.Builder();
        if (TextUtils.isEmpty(baseUrl)) {
            builder.baseUrl(BaseURL.getBaseURL());
        } else {
            builder.baseUrl(baseUrl);
        }
         builder.client(getOkHttpClient())
                 .addConverterFactory(GsonConverterFactory.create(GsonAdapter.buildGson()))
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .addConverterFactory(new NullOnEmptyConverterFactory());
        return builder;
    }

    private static OkHttpClient getOkHttpClient(){
        return getOkHttpBuilder().build();
    }

    private static Builder getOkHttpBuilder(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //OkHttpClient
        return new Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .addInterceptor(new RequestInterceptor())
                .addInterceptor(new ResponseInterceptor())
                .addInterceptor(httpLoggingInterceptor);
    }
}
