package com.thinkeract.tka.data.api.response;

/**
 * Created by lu on 2016/11/8.
 */

public class ZFBPayData {
    private  String orderId;
    private String payInfo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }
}
