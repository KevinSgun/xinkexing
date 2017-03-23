package com.zitech.framework;


import android.support.multidex.MultiDexApplication;

import com.zitech.framework.utils.Utils;

import java.io.File;

/**
 * Created by lu on 2016/6/12.
 */
public abstract class BaseApplication extends MultiDexApplication {
    private static BaseApplication mInstance;
    private Session mSession;
    private static final String DOWNLOAD_DIR_NAME = "common_cache";
    private static final String UPLOAD_DIR_NAME = "upload_dir";


    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mSession = new Session(this);
    }

    public Session getSession() {
        return mSession;
    }

    public File getUploadCacheDir() {
        File dir= Utils.getDiskCachePath(UPLOAD_DIR_NAME);
        if(!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }

    public File getDownloadCacheDir() {
        File dir=   Utils.getDiskCachePath(DOWNLOAD_DIR_NAME);
        if(!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }

}
