package com.thinkeract.tka.data.api.entity;

import java.util.List;

/**
 * Created by minHeng on 2017/4/12 16:40.
 * mail:minhengyan@gmail.com
 */

public class GoodsItem {

    /**
     * minprice : 20
     * comments : 20
     * name : 天山脑残片
     * cover : http://120.24.95.8:11111/upload/20170408/1491641557384.jpg
     * id : 4
     */

    private int id;
    private int comments;
    private float minprice;
    private String name;
    private String cover;
    private String spec;
    private String subtitle;
    private List<String> photos;

    public float getMinprice() {
        return minprice;
    }

    public void setMinprice(float minprice) {
        this.minprice = minprice;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
