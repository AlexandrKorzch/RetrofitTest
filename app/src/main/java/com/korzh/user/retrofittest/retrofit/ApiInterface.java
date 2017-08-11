package com.korzh.user.retrofittest.retrofit;


import com.korzh.user.retrofittest.model.User;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by user on 03.08.17.
 */

public interface ApiInterface {


    @POST("register")
    Observable<User> registration(@Body User user);

    @POST("login")
    Observable<User> login(@Body User user);

//    @DELETE("users/{id}")
//    Observable<Object> deleteUser(@Header("Authorization") String authHeader, @Path("id") String id);

}
