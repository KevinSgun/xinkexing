package com.thinkeract.tka.data.api.entity;

/**
 * Created by ymh on 2017/4/8 17:21
 * e-mail:minhengyan@gmail.com
 */

public class NewsItem {

    /**
     * title : 你头部的治疗傻逼药吃了吗？
     * cover : http://localhost:8080/upload/20170401//head/1491015489432.png
     * subTitle : 你头部的治疗傻逼药吃了吗？
     * id : 13
     */

    private int id;
    private String title;
    private String cover;
    private String subTitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
