package com.yinshan.happycash.view.login.model;

/**
 * Created by admin on 2018/1/30.
 */

public class LoginTokenResponse {

    private String token;
    private String action;
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
