package com.thinkeract.tka.data.api.entity;

import java.util.List;

/**
 * Created by minHeng on 2017/4/7 13:53.
 * mail:minhengyan@gmail.com
 */

public class GoodsSpec {
    private String specType;
    private List<Spec> specItems;

    public String getSpecType() {
        return specType;
    }

    public void setSpecType(String specType) {
        this.specType = specType;
    }

    public List<Spec> getSpecItems() {
        return specItems;
    }

    public void setSpecItems(List<Spec> specItems) {
        this.specItems = specItems;
    }

    public static class Spec{
        private String specValue;
        private String specId;
        private boolean hasStock;

        public String getSpecValue() {
            return specValue;
        }

        public void setSpecValue(String specValue) {
            this.specValue = specValue;
        }

        public String getSpecId() {
            return specId;
        }

        public void setSpecId(String specId) {
            this.specId = specId;
        }

        public boolean isHasStock() {
            return hasStock;
        }

        public void setHasStock(boolean hasStock) {
            this.hasStock = hasStock;
        }
    }
}
