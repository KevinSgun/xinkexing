package com.thinkeract.tka.data.api.entity;

/**
 * Created by minHeng on 2016/11/8 18:55.
 * mail:minhengyan@gmail.com
 */

public class WXPayInfo {

    /**
     * wx_notify_url : httx.do
     * wx_app_id : wx99bdd99d488c9d27
     * wx_seller : 1304926101
     * noncestr : 805163a0f0f128e473726ccda5f91bac
     * timestamp : 1477489042
     * prepay_id : wx20161026213723e9d9ed4a250673501212
     * sign : 1D2E8C505AADC06B954A3004ED2EC674
     * packageValue : Sign=WXPay
     */

    private String wx_notify_url;
    private String wx_app_id;
    private String wx_seller;
    private String noncestr;
    private int timestamp;
    private String prepay_id;
    private String sign;
    private String packageValue;

    public String getWx_notify_url() {
        return wx_notify_url;
    }

    public void setWx_notify_url(String wx_notify_url) {
        this.wx_notify_url = wx_notify_url;
    }

    public String getWx_app_id() {
        return wx_app_id;
    }

    public void setWx_app_id(String wx_app_id) {
        this.wx_app_id = wx_app_id;
    }

    public String getWx_seller() {
        return wx_seller;
    }

    public void setWx_seller(String wx_seller) {
        this.wx_seller = wx_seller;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }
}
