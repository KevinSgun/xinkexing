package com.thinkeract.tka.data.api.request;

/**
 * Created by minHeng on 2017/3/14 17:38.
 * mail:minhengyan@gmail.com
 */

public class ValidationCodeBody {
    public static final String REGISTER = "register" ;
    public static final String FIND_PASSWORD = "retrievePwd" ;
    public static final String LOG_IN = "login" ;
    public static final String BINDING = "binding" ;
    private String apkind;//验证码类型，注册为register,找回密码为retrievePwd
    private String mobile;

    public String getApkind() {
        return apkind;
    }

    public void setApkind(String apkind) {
        this.apkind = apkind;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
