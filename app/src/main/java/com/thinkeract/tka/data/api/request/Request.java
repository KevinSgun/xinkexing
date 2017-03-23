package com.thinkeract.tka.data.api.request;

import com.google.gson.Gson;
import com.thinkeract.tka.Constants;
import com.thinkeract.tka.common.utils.Md5;
import com.thinkeract.tka.common.utils.Utils;

/**
 * 服务器相关API请求基础类
 *
 * @author Ludaiqian
 */
public class Request<T> {
    private RequestHeader head;
    private T data;

    public Request(RequestHeader header, T requestBody) {
        super();
        this.head = header;
        if (requestBody == null)
            requestBody = (T) new Object();
        this.data = requestBody;
    }


    public Request sign() {
        if (head == null) {
            head = new RequestHeader();
        }
        String json;
        try {
            if (data != null)
                json = Utils.sort(data);//bean转换成json,会触发Global中的 getToken()方法
            else
                json = new Gson().toJson(new Object());
            head.setSign(encrypt(json));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return this;
    }

    private String encrypt(String source) {
       return Md5.encrypt32(Constants.SALT+source).toUpperCase();
    }

    public RequestHeader getHeader() {
        return head;
    }

    public void setHeader(RequestHeader header) {
        this.head = header;
    }

    public T getRequestBody() {
        return data;
    }

    public void setRequestBody(T data) {
        this.data = data;
    }

}
