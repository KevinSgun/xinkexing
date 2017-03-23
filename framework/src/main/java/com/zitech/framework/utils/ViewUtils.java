package com.zitech.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zitech.framework.BaseApplication;
import com.zitech.framework.R;

/**
 * 涉及到界面的帮助类 比如listview设置每项的高度
 *
 * @author Administrator
 */
public class ViewUtils {


    private static long lastClickTime;

    /**
     * 防止快速点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(500);
    }

    /**
     * 防止快速点击
     *
     * @return
     */
    public static boolean isFastDoubleClick(int limit) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (limit < timeD) {
            lastClickTime = time;
            return false;
        }
        lastClickTime = time;
        return true;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = BaseApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatuBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelOffset(resourceId);
        }
        return result;
    }

    public static Point center(Context context, int width, int height) {
        int x = getDisplayWidth() / 2 - width / 2;
        int offset = getStatuBarHeight(context);
        int contentHeight = getDisplayHeight() - getStatuBarHeight(context);
        int y = contentHeight / 2 - height / 2 + offset;
        return new Point(x, y);

    }

    public static void hideSoftInputFromWindow(Context mContext, EditText view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int getDisplayHeight() {
        return getDisplayMetrics(BaseApplication.getInstance()).heightPixels;
    }

    public static int getDisplayWidth() {
        return getDisplayMetrics(BaseApplication.getInstance()).widthPixels;
    }

    public static int getDimenPx(int dimenId) {
        return BaseApplication.getInstance().getResources().getDimensionPixelOffset(dimenId);
    }

    public static final String RIGHT_IN = "right-in";
    public static final String BOTTOM_IN = "bottom-in";
    public static final String FADE_IN = "fade-in";

    public static void anima(String animation, Activity ac) {
        switch (animation) {

            case RIGHT_IN:
                ac.overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                break;
            case BOTTOM_IN:
                ac.overridePendingTransition(R.anim.abc_slide_in_bottom,
                        R.anim.slide_out_top);
                break;
            case FADE_IN:
                ac.overridePendingTransition(R.anim.fade_in,
                        R.anim.fade_out);
            case "none":
            default:
                break;
        }
    }

    public static Rect getViewFrameRect(Activity context, View v) {

        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int[] location = new int[2];
        v.getLocationOnScreen(location);
        location[1] += statusBarHeight;

        int width = v.getWidth();
        int height = v.getHeight();
        return new Rect(location[0], location[1], location[0] + width, location[1] + height);
    }
}
