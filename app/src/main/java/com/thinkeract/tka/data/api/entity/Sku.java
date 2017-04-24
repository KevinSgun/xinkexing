package com.thinkeract.tka.data.api.entity;

import java.util.List;

/**
 * Created by minHeng on 2017/4/7 13:53.
 * mail:minhengyan@gmail.com
 */

public class Sku {
    private int id;
    private int pid;
    private String name;
    private List<Spec> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Spec> getItems() {
        return items;
    }

    public void setItems(List<Spec> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class Spec{
        private int id;
        private int pid;
        private String name;
        private boolean hasStock = true;
        private boolean isSel;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public boolean isHasStock() {
            return hasStock;
        }

        public void setHasStock(boolean hasStock) {
            this.hasStock = hasStock;
        }

        public boolean isSel() {
            return isSel;
        }

        public void setSel(boolean sel) {
            isSel = sel;
        }
    }
}
