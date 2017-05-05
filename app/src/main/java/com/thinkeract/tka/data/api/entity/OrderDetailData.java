package com.thinkeract.tka.data.api.entity;

import java.util.List;

/**
 * Created by ymh on 2017/4/9 22:47
 * e-mail:minhengyan@gmail.com
 */

public class OrderDetailData {
    private int itemType;
    private int status;
    /**
     * amount : 20
     * status : 1
     * name : 豹胎易筋丸
     * goods : [{"comment":0,"quantity":1,"gid":5,"name":"三尸脑神丹","price":10,"cover":"http://120.24.95.8:11111/upload/20170412/1491999942136.png","id":44,"spec":"包装规格:单号装;药品成分:中成药"},{"comment":0,"quantity":1,"gid":6,"name":"豹胎易筋丸","price":10,"cover":"http://120.24.95.8:11111/upload/20170412/1491999764302.png","id":45,"spec":"包装规格:单号装"}]
     * fare : 0
     * po : 20170412220903
     * goodPrice : 20
     * date : 2017-04-12 21:22:04
     */

    private double amount;
    private double fare;
    private double goodPrice;
    private String name;
    private String po;
    private String date;
    private OrderDetailGoods detailGoods;
    private List<OrderDetailGoods> goods;


    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<OrderDetailGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<OrderDetailGoods> goods) {
        this.goods = goods;
    }

    public OrderDetailGoods getDetailGoods() {
        return detailGoods;
    }

    public void setDetailGoods(OrderDetailGoods detailGoods) {
        this.detailGoods = detailGoods;
    }

    public static class OrderDetailGoods {
        /**
         * comment : 0
         * quantity : 1
         * gid : 5
         * name : 三尸脑神丹
         * price : 10
         * cover : http://120.24.95.8:11111/upload/20170412/1491999942136.png
         * id : 44
         * spec : 包装规格:单号装;药品成分:中成药
         */

        private int comment;
        private int quantity;
        private int gid;
        private double price;
        private int id;
        private String name;
        private String cover;
        private String spec;

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }
    }
}
