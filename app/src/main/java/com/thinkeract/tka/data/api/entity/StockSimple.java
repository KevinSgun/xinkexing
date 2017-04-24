package com.thinkeract.tka.data.api.entity;

/**
 * Created by minHeng on 2017/4/18 14:22.
 * mail:minhengyan@gmail.com
 */

public class StockSimple {

    /**
     * id : 5
     * price : 10
     * sid : 16
     * quantity : 1
     */

    private String id;
    private String price;
    private String sid;
    private String quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
