package com.thinkeract.tka;

import android.os.Handler;
import android.os.Looper;

import com.shizhefei.mvc.MVCHelper;
import com.squareup.leakcanary.LeakCanary;
import com.thinkeract.tka.common.utils.EnhancedPlaceManagerUtil;
import com.thinkeract.tka.widget.MyLoadViewFactory;
import com.zitech.framework.BaseApplication;

/**
 * Created by minHeng on 2017/3/10 18:51.
 * mail:minhengyan@gmail.com
 */

public class ThinkerActApplication extends BaseApplication{

    private User user;
    private Handler mainThreadHandler;
    private Config config;
    private EnhancedPlaceManagerUtil enhancedPlaceUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        user = new User();
        config = new Config();
        mainThreadHandler = new Handler(Looper.getMainLooper());
        enhancedPlaceUtil = new EnhancedPlaceManagerUtil();
        enhancedPlaceUtil.asyncInitRegion();

        // 设置LoadView的factory，用于创建使用者自定义的加载失败，加载中，加载更多等布局,写法参照DeFaultLoadViewFactory
        MVCHelper.setLoadViewFactory(new MyLoadViewFactory());
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

    public Config getConfig() {
        return config;
    }

    public EnhancedPlaceManagerUtil getEnhancedPlace(){
        return enhancedPlaceUtil;
    }
}
