package com.thinkeract.tka.common.receiver;

/**
 * Created by minHeng on 2017/1/5 18:36.
 * mail:minhengyan@gmail.com
 */

public interface NetChangeObserver {
    /**
     * 网络状态连接时调用
     */
    public void OnConnect(int netType);

    /**
     * 网络状态断开时调用
     */
    public void OnDisConnect();
}
