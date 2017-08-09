package com.korzh.user.retrofittest.retrofit;


import com.korzh.user.retrofittest.model.Model;
import com.korzh.user.retrofittest.model.RegisteredUser;
import com.korzh.user.retrofittest.model.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 03.08.17.
 */

public interface ApiInterface {

//    @GET("maps/api/place/nearbysearch/json")
//    Observable<Model> getData(
//            @Query("location")String location,
//            @Query("radius")String radius,
//            @Query("type")String type,
//            @Query("keyword")String keyword,
//            @Query("key")String key);


    @POST("users")
    Observable<RegisteredUser> registration(@Body User user);



}
