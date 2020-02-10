package com.android.websocialall.network.provider;

import com.android.websocialall.network.repositories.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL="http://binimoi.com/wavesocial/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;
    private RetrofitClient(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpCLient)
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if (mInstance==null)
            mInstance=new RetrofitClient();
        return mInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
    final static OkHttpClient okHttpCLient=new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60,TimeUnit.SECONDS)
            .build();
}
