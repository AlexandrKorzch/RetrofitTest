package com.korzh.user.retrofittest.retrofit;



import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 03.08.17.
 */

public class ApiManager {

    private static final String BASE_URL  = "https://maps.googleapis.com/";

    private static ApiManager mApiManager;
    private ApiInterface service;

    public static ApiManager getInstance() {
        if(mApiManager == null){
            mApiManager = new ApiManager();
        }
        return mApiManager;
    }

    private ApiManager() {
        createRetrofit();
    }

    private void createRetrofit(){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        service = retrofit.create(ApiInterface.class);

    }

    public ApiInterface getService() {
        return service;
    }
}
