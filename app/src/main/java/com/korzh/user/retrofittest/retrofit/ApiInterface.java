package com.korzh.user.retrofittest.retrofit;


import com.korzh.user.retrofittest.model.Model;
import com.korzh.user.retrofittest.model.RegisteredUser;
import com.korzh.user.retrofittest.model.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 03.08.17.
 */

public interface ApiInterface {


    @POST("users")
    Observable<RegisteredUser> registration(@Body User user);


    @PUT("users/{id}")
    Observable<RegisteredUser> updateUser(@Header("Authorization") String authHeader, @Path("id") String id, @Body User user);




}
