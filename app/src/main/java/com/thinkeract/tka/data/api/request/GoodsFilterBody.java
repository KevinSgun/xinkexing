package com.thinkeract.tka.data.api.request;

/**
 * Created by minHeng on 2017/4/13 17:45.
 * mail:minhengyan@gmail.com
 */

public class GoodsFilterBody extends ListBody{
    private String order;
    private String classId;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
