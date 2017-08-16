package com.korzh.user.retrofittest.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.korzh.user.retrofittest.App;

/**
 * Created by user on 10.08.17.
 */

public class SharedPrefManager {

    private static final String ID_KEY = "ID_KEY";
    private static final String TOKEN_KEY = "TOKEN_KEY";

    private static SharedPreferences sSharedPref;

    public static void createSPManager() {
        sSharedPref = App.sContext.getSharedPreferences("my_shared", Context.MODE_PRIVATE);
    }

    public static String getId() {
        return getValue(ID_KEY);
    }

    public static void setId(String id) {
        saveValue(ID_KEY, id);
    }

    public static String getToken() {
        return getValue(TOKEN_KEY);
    }

    public static void setToken(String token) {
        saveValue(TOKEN_KEY, token);
    }

    private static void saveValue(String key, String value) {
        SharedPreferences.Editor editor = sSharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static String getValue(String key) {
        return sSharedPref.getString(key, "");
    }

    public static void clear() {
        setId("");
        setToken("");
    }
}
