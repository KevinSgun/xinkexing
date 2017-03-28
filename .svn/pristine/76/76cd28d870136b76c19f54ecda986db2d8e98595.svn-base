package com.thinkeract.tka.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zitech.framework.utils.NetworkUtil;

import java.util.ArrayList;

/**
 *
 *  检测网络状态改变的广播接收器  <br>
 *  在网络状态改变监听过程<观察者模式>中  我们可以把他看做是一个被观察者 <br>
 * Created by minHeng on 2017/1/5 18:37.
 * mail:minhengyan@gmail.com
 */

public class NetWorkStateReceiver extends BroadcastReceiver {

    private static final String TAG = "NetWorkStateReceiver";
    private static final String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    /** 储存所有的网络状态观察者集合   */
    private static ArrayList<NetChangeObserver> netChangeObserverArrayList = new ArrayList<NetChangeObserver>();
    private static boolean networkAvailable = true;
    private int netType;


    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)){
            Log.d(TAG, "网络状态发生了改变...");
//            NetworkUtil netWorkHelper = NetworkUtil.getInstance(context);
            if(!NetworkUtil.isNetworkAvailable()){
                networkAvailable = false;
                Log.d(TAG, "网络连接断开...");
            }else{
                netType = NetworkUtil.getNetworkType(context);
                networkAvailable = true;
                Log.d(TAG, "网络连接成功..."+"| 当前的网络类型为: "+netType);
            }
            // 通知所有注册了的网络状态观察者
            notifyObserver();
        }
    }


    /**
     * 添加/注册网络连接状态观察者
     * @param observer
     */
    public static void registerNetStateObserver(NetChangeObserver observer){
        if(netChangeObserverArrayList == null){
            netChangeObserverArrayList = new ArrayList<NetChangeObserver>();
        }
        netChangeObserverArrayList.add(observer);
    }


    /**
     * 删除/注销网络连接状态观察者
     * @param observer
     */
    public static void unRegisterNetStateObserver(NetChangeObserver observer){
        if(netChangeObserverArrayList != null){
            netChangeObserverArrayList.remove(observer);
        }
    }


    /**
     * 向所有的观察者发送通知：网络状态发生改变咯...
     */
    private void notifyObserver(){
        if(netChangeObserverArrayList !=null && netChangeObserverArrayList.size() >0){
            for(NetChangeObserver observer : netChangeObserverArrayList){
                if(observer != null){
                    if(networkAvailable){
                        observer.OnConnect(netType);
                    }else{
                        observer.OnDisConnect();
                    }
                }
            }
        }
    }
}
