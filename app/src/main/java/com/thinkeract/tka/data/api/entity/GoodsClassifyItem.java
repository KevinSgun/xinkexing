package com.thinkeract.tka.data.api.entity;

import java.util.List;

/**
 * Created by minHeng on 2017/4/12 16:44.
 * mail:minhengyan@gmail.com
 */

public class GoodsClassifyItem {

    /**
     * id : 13
     * items : [{"id":48,"items":[],"name":"总体器官检查","pId":13}]
     * name : 脏器类
     * pId : 0
     */

    private int id;
    private int pId;
    private String name;
    private List<ItemsBean> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPId() {
        return pId;
    }

    public void setPId(int pId) {
        this.pId = pId;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * id : 48
         * items : []
         * name : 总体器官检查
         * pId : 13
         */

        private int id;
        private String name;
        private int pId;
        private List<?> items;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPId() {
            return pId;
        }

        public void setPId(int pId) {
            this.pId = pId;
        }

        public List<?> getItems() {
            return items;
        }

        public void setItems(List<?> items) {
            this.items = items;
        }
    }
}
