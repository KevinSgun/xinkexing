package com.thinkeract.tka.data.api.entity;

import com.thinkeract.tka.data.api.response.GoodsDetailData;

/**
 * Created by minHeng on 2017/4/14 17:00.
 * mail:minhengyan@gmail.com
 */

public class GoodsDetailItem {
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_COMMENT = 1;
    private int itemType;
    private GoodsDetailData goodsDetailData;
    private GoodsComment goodsComment;

    public GoodsDetailData getGoodsDetailData() {
        return goodsDetailData;
    }

    public void setGoodsDetailData(GoodsDetailData goodsDetailData) {
        this.itemType = TYPE_HEAD;
        this.goodsDetailData = goodsDetailData;
    }

    public GoodsComment getGoodsComment() {
        return goodsComment;
    }

    public void setGoodsComment(GoodsComment goodsComment) {
        this.itemType = TYPE_COMMENT;
        this.goodsComment = goodsComment;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
