package com.korzh.user.retrofittest.util;

import android.util.Log;

import com.korzh.user.retrofittest.manager.SharedPrefManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by user on 10.08.17.
 */

public class TokenAuthenticator implements Interceptor {

    private static final String TAG = "TokenAuthenticator";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder().header("Authorization", SharedPrefManager.getToken()).build();
        Log.d(TAG, "intercept: newRequest token in header - "+newRequest.header("Authorization"));
        return chain.proceed(newRequest);
    }
}