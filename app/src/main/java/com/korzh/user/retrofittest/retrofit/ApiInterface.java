package com.korzh.user.retrofittest.retrofit;


import com.korzh.user.retrofittest.model.RegisteredUser;
import com.korzh.user.retrofittest.model.User;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by user on 03.08.17.
 */

public interface ApiInterface {


    @POST("users")
    Observable<RegisteredUser> registration(@Body User user);

    @DELETE("users/{id}")
    Observable<Object> deleteUser(@Header("Authorization") String authHeader, @Path("id") String id);

}
