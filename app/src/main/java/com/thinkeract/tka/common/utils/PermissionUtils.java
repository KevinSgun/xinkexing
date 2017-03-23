package com.thinkeract.tka.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by minHeng on 2016/12/28 11:06.
 * mail:minhengyan@gmail.com
 */

public class PermissionUtils {

    public static final int WR_PERMISSION = 100;//读写权限
    public static final int PHONE_STATUS_PERMISSION = 101;//读取手机状态权限
    public static final int CAMERA_AUDIO_PERMISSION = 102;//摄像头、录音权限
    public static final int CAMERA_PERMISSION = 103;//摄像头权限
    public static final int AUDIO_PERMISSION = 104;//录音权限

    /**
     * 检查读写权限
     *
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        return isGrantExternalRW(activity, WR_PERMISSION);
    }

    /**
     * 检查读写权限
     *
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, requestCode);
            return false;//第一次开启应用并执行权限检查，虽然返回了false，但是已经调用过了申请权限的方法
        }
        return true;//非第一次开启应用并执行权限检查，或者6.0以下的Android版本
    }

    /**
     * 检查读取手机状态权限
     *
     * @param activity
     * @return
     */
    public static boolean isGrantPhoneStatus(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE,
            }, PHONE_STATUS_PERMISSION);
            return false;//第一次开启应用并执行权限检查，虽然返回了false，但是已经调用过了申请权限的方法
        }
        return true;//非第一次开启应用并执行权限检查，或者6.0以下的Android版本
    }

    /**
     * 检查使用摄像头、麦克风权限
     *
     * @param activity
     * @return
     */
    public static boolean isGrantCameraAndAudio(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                }, CAMERA_AUDIO_PERMISSION);
                return false;
            }else if(activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{
                        Manifest.permission.RECORD_AUDIO
                }, CAMERA_AUDIO_PERMISSION);
                return false;
            }
                return true;//第一次开启应用并执行权限检查，虽然返回了false，但是已经调用过了申请权限的方法
        }
        return true;//非第一次开启应用并执行权限检查，或者6.0以下的Android版本
    }

    /**
     * 检查使用摄像头权限
     *
     * @param activity
     * @return
     */
    public static boolean isGrantCamera(Activity activity) {
        return isGrantCamera(activity, CAMERA_PERMISSION);
    }

    /**
     * 检查使用摄像头权限
     *
     * @param activity
     * @return
     */
    public static boolean isGrantCamera(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
            return false;//第一次开启应用并执行权限检查，虽然返回了false，但是已经调用过了申请权限的方法
        }
        return true;//非第一次开启应用并执行权限检查，或者6.0以下的Android版本
    }

    /**
     * 检查使用麦克风权限
     *
     * @param activity
     * @return
     */
    public static boolean isGrantAudio(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO
            }, AUDIO_PERMISSION);
            return false;//第一次开启应用并执行权限检查，虽然返回了false，但是已经调用过了申请权限的方法
        }
        return true;//非第一次开启应用并执行权限检查，或者6.0以下的Android版本
    }

    /**
     * 检查读写权限
     *
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRWAndCamera(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            }, requestCode);
            return false;//第一次开启应用并执行权限检查，虽然返回了false，但是已经调用过了申请权限的方法
        }
        return true;//非第一次开启应用并执行权限检查，或者6.0以下的Android版本
    }
}
