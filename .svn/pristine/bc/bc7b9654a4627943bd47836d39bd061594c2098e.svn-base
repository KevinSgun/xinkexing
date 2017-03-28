package com.zitech.framework.data.network.response;


/**
 * @author makk
 * @version V1.0
 * @Project: PersonalAccount
 * @Package com.zitech.personalaccount.data.network.response
 * @Description: 响应基类
 * @date 2016/5/17 9:47
 */
public class ApiResponse<T> {

    private ResponseHeader head;
    T data;

    public ResponseHeader getHeader() {
        return head;
    }

    public void setHeader(ResponseHeader header) {
        this.head = header;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return head != null && head.getStatus() == 1;
    }

    public int getStatusCode(){
        return head==null?-1:head.getStatus();
    }

    public String getResultCode() {
        return head != null ? head.getDatecode() : null;
    }


    public String getMsg() {
        return head != null ? head.getMsg() : null;
    }


}
