package com.thinkeract.tka.pay;

public interface PayCallBack {

    void onCallBack(String msg);
    void onFailure(String msg);
}