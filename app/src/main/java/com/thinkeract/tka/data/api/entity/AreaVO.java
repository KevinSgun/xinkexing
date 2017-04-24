package com.thinkeract.tka.data.api.entity;

/**
 * Created by minHeng on 2017/4/18 17:46.
 * mail:minhengyan@gmail.com
 */

public class AreaVO {
    private int key;
    private String val;
    private String id;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        if (key < 100) {
            return 1;
        } else if (key > 999 && key < 10000) {
            return 2;
        }
        return 3;
    }

    public int getParentId() {
        int parentId= key / 100;
        if(parentId==1102||parentId==1202||parentId==3102||parentId==5002){
            parentId=parentId-1;
        }
        return parentId;
    }

    public String getCirclename(){
        return val;
    }
}
