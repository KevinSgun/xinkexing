package com.thinkeract.tka;

import android.os.Handler;
import android.os.Looper;

import com.zitech.framework.BaseApplication;

/**
 * Created by minHeng on 2017/3/10 18:51.
 * mail:minhengyan@gmail.com
 */

public class ThinkerActApplication extends BaseApplication{

    private User user;
    private Handler mainThreadHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        user = new User();
        mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    public static ThinkerActApplication getInstance() {
        return (ThinkerActApplication) BaseApplication.getInstance();
    }

    public void post(Runnable r) {
        mainThreadHandler.post(r);
    }

    public void postDelay(Runnable r, int delayMillis) {
        mainThreadHandler.postDelayed(r, delayMillis);
    }

    public User getUser(){
        return user;
    }
}
