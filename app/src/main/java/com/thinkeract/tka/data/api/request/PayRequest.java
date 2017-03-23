package com.thinkeract.tka.data.api.request;

/**
 * Created by minHeng on 2016/12/8 19:39.
 * mail:minhengyan@gmail.com
 */

public class PayRequest {

    /**
     * payType : 1
     * actuallyAmount : 400
     * orderId : 20161206160343
     */

    private String payType;
    private String actuallyAmount;
    private String orderId;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getActuallyAmount() {
        return actuallyAmount;
    }

    public void setActuallyAmount(String actuallyAmount) {
        this.actuallyAmount = actuallyAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
