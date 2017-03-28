package com.thinkeract.tka.common.utils;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ymh on 16/11/29
 */
public class ViewUtils extends com.zitech.framework.utils.ViewUtils {

    public static boolean isTouchedOutsideView(View v, MotionEvent event) {
        if (v != null) {
            int[] leftTop = { 0, 0 };
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

//    public static void setIndicator(Context context, TabLayout tabs, int leftDip, int rightDip) {
//        Class<?> tabLayout = tabs.getClass();
//        Field tabStrip = null;
//        try {
//            tabStrip = tabLayout.getDeclaredField("mTabStrip");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//
//        tabStrip.setAccessible(true);
//        LinearLayout ll_tab = null;
//        try {
//            ll_tab = (LinearLayout) tabStrip.get(tabs);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        int left = (int) (getDisplayMetrics(context).density * leftDip);
//        int right = (int) (getDisplayMetrics(context).density * rightDip);
//        if(ll_tab != null)
//            for (int i = 0; i < ll_tab.getChildCount(); i++) {
//                View child = ll_tab.getChildAt(i);
//                child.setPadding(0, 0, 0, 0);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//                params.leftMargin = left;
//                params.rightMargin = right;
//                child.setLayoutParams(params);
//                child.invalidate();
//            }
//    }

//    public static int getHotViewTypeIvRes(int hotType){
//        switch (hotType){
//            case HotAdapter.PROFESSOR:
//                return R.mipmap.ic_professor_hot;
//            case HotAdapter.LIVING:
//                return R.mipmap.ic_livingnow_hot;
//            case HotAdapter.HOT_TRAILER:
//                return R.mipmap.ic_prelive_hot;
//            default:
//                return R.mipmap.ic_playback_hot;
//        }
//    }

}
