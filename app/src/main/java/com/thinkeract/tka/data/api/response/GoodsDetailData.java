package com.thinkeract.tka.data.api.response;

import com.thinkeract.tka.data.api.entity.GoodsItem;
import com.thinkeract.tka.data.api.entity.Sku;

import java.util.List;

/**
 * Created by minHeng on 2017/4/12 16:49.
 * mail:minhengyan@gmail.com
 */

public class GoodsDetailData {

    /**
     * stock : [{"cost":5,"code":"19:24;27:28","barcode":"0000000000000","name":"包装规格:单号装;药品成分:中成药","inventory":10000,"price":10,"id":16},{"cost":5,"code":"19:25;27:28","barcode":"0000000000000","name":"包装规格:1疗程4盒装;药品成分:中成药","inventory":10000,"price":10,"id":17}]
     * goods : {"spec":"<p>三尸脑神丹三尸脑神丹三尸脑神丹三尸脑神丹<\/p>\n","photos":["http://localhost:8080/upload/20170412/1491970385169.jpg","http://localhost:8080/upload/20170412/1491970393844.png"],"id":5,"cover":"http://localhost:8080/upload/20170412/1491970376964.jpg","name":"三尸脑神丹","subtitle":"","comments":"0","minPrice":10}
     * sku : [{"id":19,"items":[{"id":24,"items":[],"name":"单号装","pid":0},{"id":25,"items":[],"name":"1疗程4盒装","pid":0},{"id":26,"items":[],"name":"2疗程8盒装","pid":0}],"name":"包装规格","pid":0},{"id":27,"items":[{"id":28,"items":[],"name":"中成药","pid":0},{"id":29,"items":[],"name":"西成药","pid":0}],"name":"药品成分","pid":0}]
     */

    private GoodsItem goods;
    private List<StockBean> stock;
    private List<Sku> sku;

    public GoodsItem getGoods() {
        return goods;
    }

    public void setGoods(GoodsItem goods) {
        this.goods = goods;
    }

    public List<StockBean> getStock() {
        return stock;
    }

    public void setStock(List<StockBean> stock) {
        this.stock = stock;
    }

    public List<Sku> getSku() {
        return sku;
    }

    public void setSku(List<Sku> sku) {
        this.sku = sku;
    }

    public static class StockBean {
        /**
         * cost : 5
         * code : 19:24;27:28
         * barcode : 0000000000000
         * name : 包装规格:单号装;药品成分:中成药
         * inventory : 10000
         * price : 10
         * id : 16
         */

        private int id;
        private int inventory;
        private int price;
        private int cost;
        private String code;
        private String barcode;
        private String name;

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getInventory() {
            return inventory;
        }

        public void setInventory(int inventory) {
            this.inventory = inventory;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
