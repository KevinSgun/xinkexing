package com.thinkeract.tka.data.api.request;

import com.thinkeract.tka.User;
import com.zitech.framework.Session;

/**
 * 请求头
 *
 * @author Ludaiqian
 */
public class RequestHeader {

    private String datacode;
    // 手机唯一编号
//    private String mobileCode;
    // 手机类型 2 iphone 1 安卓
    private String os;
    private String token;
    private String version;
    private String sign;
    private String appKey;

    public RequestHeader() {
        super();
    }

    public RequestHeader(String datecode) {
        super();
        this.version = String.valueOf(Session.getInstance().getVersionName());
//        this.mobileCode = Session.getInstance().getDeviceId();
        this.os = String.valueOf(1);
        this.datacode = datecode;
        this.token = User.get().getToken();
    }

    public String getDatecode() {
        return datacode;
    }

    public static RequestHeader create(String datecode) {
        return new RequestHeader(datecode);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}