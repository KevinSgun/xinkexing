package com.thinkeract.tka.data.api.response;

/**
 * Created by minHeng on 2017/4/12 14:10.
 * mail:minhengyan@gmail.com
 */

public class LogisticsData {
    private int itemType;
    private int id;
    /**
     * corp : 顺丰快递
     * odd : SX1234567890
     * date : 2017-03-21 14:44:27
     * po : 201703080674329382
     * id : 1
     */

    private String corp;
    private String odd;
    private String date;
    private String po;


    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getOdd() {
        return odd;
    }

    public void setOdd(String odd) {
        this.odd = odd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
