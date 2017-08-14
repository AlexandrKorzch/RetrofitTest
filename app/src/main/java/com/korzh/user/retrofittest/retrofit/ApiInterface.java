package com.korzh.user.retrofittest.retrofit;


import com.korzh.user.retrofittest.model.User;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by user on 03.08.17.
 */

public interface ApiInterface {


    @POST("register")
    Observable<User> registration(@Body User user);

    @POST("login")
    Observable<User> login(@Body User user);

    @Multipart
    @POST("users/avatar")
    Observable<User> uploadAvatar(@Part MultipartBody.Part image);

}
