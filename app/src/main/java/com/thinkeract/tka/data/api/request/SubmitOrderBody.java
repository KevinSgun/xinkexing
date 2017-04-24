package com.thinkeract.tka.data.api.request;

/**
 * Created by minHeng on 2017/4/17 10:56.
 * mail:minhengyan@gmail.com
 */

public class SubmitOrderBody {

    /**
     * totleAmount : 20
     * stock : [{"id":"5","price":"10","sid":"16","quantity":"1"},{"id":"6","price":"10","sid":"18","quantity":"1"}]
     * addressId : 2
     */

    private String totleAmount;
    private String stock;
    private String addressId;

    public String getTotleAmount() {
        return totleAmount;
    }

    public void setTotleAmount(String totleAmount) {
        this.totleAmount = totleAmount;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}
