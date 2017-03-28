package com.thinkeract.tka.ui;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.thinkeract.tka.Events;
import com.zitech.framework.BuildConfig;
import com.zitech.framework.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by minHeng on 2017/3/10 14:11.
 * mail:minhengyan@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    //是否需要统计该页面
    private static boolean needTrack = !BuildConfig.DEBUG;
    private boolean animOut=true;

    protected void setContentView() {
        setContentView(getContentViewId());
    }

    protected abstract int getContentViewId();

    protected abstract void initView();

    protected abstract void initData();

    public boolean isAnimOut() {
        return animOut;
    }

    public void setAnimOut(boolean animOut) {
        this.animOut = animOut;
    }

    public void setNeedTrack(boolean needTrack) {
        this.needTrack = needTrack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreate(savedInstanceState,true);
    }
    private void onCreate(Bundle savedInstanceState,boolean initUI) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        EventBus.getDefault().register(this);
        setContentView();
        if(initUI) {
            initView();
            initData();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotifyClose(Events.CloseEvent closeEvent) {
        if (Events.CloseEvent.FINISH_ALL.equals(closeEvent.eventType)) {
            finish();
        }
    }

    protected void onResume() {
        super.onResume();
        if (needTrack) {
//            MobclickAgent.onPageStart(getClass().getSimpleName()); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
//            MobclickAgent.onResume(this);          //统计时长
        }
    }

    protected void onPause() {
        super.onPause();
        if (needTrack) {
//            MobclickAgent.onPageEnd(getClass().getSimpleName()); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
//            MobclickAgent.onPause(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * @param cls 目标activity
     *            跳转并finish当前activity
     * @throws ActivityNotFoundException
     */
    public void skipActivity(Class<?> cls) {
        showActivity(cls);
        playActivityOpenAnim();
        finish();
    }

    /***
     * @param it
     */
    public void skipActivity(Intent it) {
        super.startActivity(it);
        playActivityOpenAnim();
        finish();
    }

    /***
     * @param it
     */
    public void showActivity(Intent it) {
        super.startActivity(it);
        playActivityOpenAnim();
    }

    /**
     * @param cls
     * @param extras
     */
    public void skipActivity(Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(this, cls);
        startActivity(intent);
        playActivityOpenAnim();
        finish();
    }

    public void showActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        super.startActivity(intent);
        playActivityOpenAnim();

    }

    public void showActivity(Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.putExtras(extras);
        super.startActivity(intent);
        playActivityOpenAnim();
    }

    public void showActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.putExtras(bundle);
        super.startActivityForResult(intent, requestCode);
        playActivityOpenAnim();
    }

    @Override
    public void finish() {
        super.finish();
        if(animOut)
            playActivityOpenAnim();
    }

    public void playActivityOpenAnim() {
        ViewUtils.anima(ViewUtils.RIGHT_IN, this);
    }

    @Override
    public void onClick(View v) {

    }
    protected void back() {
        try {
            onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Context getContext() {
        return this;
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }
}
