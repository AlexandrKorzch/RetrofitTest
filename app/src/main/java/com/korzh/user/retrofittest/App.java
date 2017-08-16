package com.korzh.user.retrofittest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.korzh.user.retrofittest.manager.SharedPrefManager;

/**
 * Created by user on 03.08.17.
 */

public class App extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        SharedPrefManager.createSPManager();
        Fresco.initialize(this);
    }
}
