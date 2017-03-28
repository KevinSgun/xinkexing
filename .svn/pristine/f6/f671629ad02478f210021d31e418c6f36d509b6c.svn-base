package com.thinkeract.tka.data.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lu on 2016/10/31.
 */

public class ListData<T> {
    private List<T> items;
    /**
     * pageInfo : {"count":1,"pageCount":1}
     */

    private PageInfoBean pageInfo;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }


    public static class PageInfoBean {
        /**
         * count : 1
         * pageCount : 1
         */

        @SerializedName("count")
        private int countX;
        private int pageCount;

        public int getCountX() {
            return countX;
        }

        public void setCountX(int countX) {
            this.countX = countX;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }
    }
}
