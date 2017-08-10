package com.korzh.user.retrofittest.retrofit;


import com.korzh.user.retrofittest.manager.SharedPrefManager;
import com.korzh.user.retrofittest.model.RegisteredUser;
import com.korzh.user.retrofittest.model.User;
import com.korzh.user.retrofittest.util.MyLogInterceptor;
import com.korzh.user.retrofittest.util.TokenAuthenticator;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user on 03.08.17.
 */

public class ApiManager {

    private static final String BASE_URL = "https://testyapi.herokuapp.com/";

    private static ApiManager mApiManager;
    private ApiInterface service;

    public static ApiManager getInstance() {
        if (mApiManager == null) {
            mApiManager = new ApiManager();
        }
        return mApiManager;
    }

    private ApiManager() {
        createRetrofit();
    }

    private void createRetrofit() {

        MyLogInterceptor logInterceptor = new MyLogInterceptor();
        logInterceptor.setLevel(MyLogInterceptor.Level.BODY);

        TokenAuthenticator tokenAuthInterceptor = new TokenAuthenticator();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .addInterceptor(tokenAuthInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        service = retrofit.create(ApiInterface.class);

    }

    public Observable<RegisteredUser> registration(User user) {
        return service.registration(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(registeredUser -> {
                    SharedPrefManager.setId(registeredUser.getId());
                    SharedPrefManager.setToken(registeredUser.getToken());
                });

    }

//    public Observable<RegisteredUser> getAvatar(User user) {
//        return service.getAvatar(user)
//                .subscribeOn(Schedulers.io())
//                .retryWhen((Func1<Observable<? extends Throwable>, Observable<?>>) observable ->
//                        observable.flatMap((Func1<Throwable, Observable<?>>) throwable -> {
//                            if (throwable instanceof HttpException) {
//                                if (((HttpException) throwable).response().code() == 401) {
//                                    return registration(user);
//                                }
//                            }
//                            return Observable.error(throwable);
//                        }))
//                .observeOn(AndroidSchedulers.mainThread()).doOnNext((Action1<RegisteredUser>) registeredUser -> {
//                    SharedPrefManager.setId(registeredUser.getId());
//                    SharedPrefManager.setToken(registeredUser.getToken());
//                });
//
//    }
}
