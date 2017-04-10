package com.thinkeract.tka.data.api.request;

/**
 * Created by minHeng on 2016/10/30 19:01.
 * mail:minhengyan@gmail.com
 */

public class LoginBody {
    /**
     "mobile": "15914087331",
     "password": "123456"

     */
    private String mobile;
    private String password;
    private String code;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String phone_number) {
        this.mobile = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
