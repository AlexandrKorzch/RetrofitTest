package com.korzh.user.retrofittest.model;

/**
 * Created by user on 09.08.17.
 */

public class RegisteredUser extends User {

    private String id;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RegisteredUser{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                '}'+
                super.toString();
    }
}
