package com.thinkeract.tka.pay.alipay;

/**
 * Author：YMH on 2017/11/13 0013 15:21
 * E-mail：minheng_yan@163.com
 */
public class PayInfoResponseData<T> {
    private T payInfo;

    public T getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(T payInfo) {
        this.payInfo = payInfo;
    }
}
