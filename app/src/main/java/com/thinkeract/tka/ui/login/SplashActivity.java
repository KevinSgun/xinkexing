package com.thinkeract.tka.ui.login;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.thinkeract.tka.R;
import com.thinkeract.tka.User;
import com.thinkeract.tka.ui.home.MainActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by minHeng on 2015/12/24 09:53.
 * mail:minhengyan@gmail.com
 */
public class SplashActivity extends Activity {
//    private static final int GO_MAIN = 1000;
//    private static final int GO_GUIDE = 1001;
//    private static final int GO_PROFILE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        // // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
        Observable.just(new Object()).delaySubscription(1, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (User.get().isVisitor()) {
                    LoginActivity.launch(SplashActivity.this, true);
                } else {
                    MainActivity.launch(SplashActivity.this);
                }
//                if (!isFinishing()) {
//                    if (Config.get().isFirstOpenApp()) {
//                        GuideActivity.launch(SplashActivity.this);
//                    } else {
//                        MainActivity.launch(SplashActivity.this);
//                    }
//                }
                finish();
            }

        });
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
