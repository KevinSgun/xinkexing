package com.thinkeract.tka.data.api.request;

/**
 * Author：YMH on 2017/7/4 0004 10:03
 * E-mail：minheng_yan@163.com
 */
public class OrderIdBody {

    /**
     * po : 20170701180253
     */

    private String po;
    private String payType;

    public void setOrderId(String orderId) {
        this.po = orderId;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
