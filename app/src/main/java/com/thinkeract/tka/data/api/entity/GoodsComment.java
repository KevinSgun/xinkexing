package com.thinkeract.tka.data.api.entity;

/**
 * Created by minHeng on 2017/4/12 17:01.
 * mail:minhengyan@gmail.com
 */

public class GoodsComment {

    /**
     * date : 2017-04-11 14:53:34
     * gid : 4
     * uname : 单独的
     * id : 1
     * uid : 10000
     * content : 的地方是对方撒旦
     * photos : http://120.24.95.8:11111/upload/20170409/100190/head/1491754530827.jpg
     */

    private int id;
    private int gid;
    private int uid;
    private String date;
    private String uname;
    private String content;
    private String photos;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }
}
