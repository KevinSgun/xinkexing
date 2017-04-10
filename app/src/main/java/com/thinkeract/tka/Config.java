package com.thinkeract.tka;

import android.text.TextUtils;

import com.zitech.framework.SP;

import static android.view.View.X;
import static com.thinkeract.tka.Constants.WX_APP_ID;


/**
 * App配置信息与角色是否登录或切换无关
 */
public class Config {

    private SP sp;
    private boolean firstOpenApp;
    private static final String FIRST_OPEN_APP = "firstOpen";

    public Config() {
        super();
        sp = new SP("CONFIG_DATA");

        firstOpenApp=sp.getBoolean(FIRST_OPEN_APP,true);
    }

    public static Config get() {
        return ThinkerActApplication.getInstance().getConfig();
    }

    public boolean isFirstOpenApp() {
        return firstOpenApp;
    }
    public  void markAppOpened(){
        sp.putBoolean(FIRST_OPEN_APP,false);
        firstOpenApp=false;
    }
}
