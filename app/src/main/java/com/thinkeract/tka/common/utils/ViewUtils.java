package com.thinkeract.tka.common.utils;

import android.view.MotionEvent;
import android.view.View;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.OrderItem;

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


    public static int getOrderStatusColorRes(int orderStatus){
        switch (orderStatus){
            case OrderItem.IS_FINISH:
                return R.color.text_green_light;
            case OrderItem.IS_SEND:
                return R.color.text_green_light;
            case OrderItem.WAIT_PAY:
                return R.color.text_red;
            case OrderItem.IS_CANCEL:
                return R.color.textColorPrimaryGray;
            default:
                return R.color.textColorPrimary;
        }
    }

    public static String getOrderStatusString(int orderStatus){
        switch (orderStatus){
            case OrderItem.IS_FINISH:
                return "已完成";
            case OrderItem.WAIT_PAY:
                return "待支付";
            case OrderItem.IS_CANCEL:
                return "已取消";
            default:
                return "已发货";
        }
    }

    public static String getOrderStatusLongString(int orderStatus){
        switch (orderStatus){
            case OrderItem.IS_FINISH:
                return "交易成功";
            case OrderItem.WAIT_PAY:
                return "订单待支付";
            case OrderItem.IS_CANCEL:
                return "订单已取消";
            default:
                return "订单已发货，请耐心等待";
        }
    }

    public static int getOrderBusinessBackgroundRes(int orderStatus){
        switch (orderStatus){
            case OrderItem.IS_FINISH:
                return R.drawable.bg_black_stroke_rectangle_corner_r100;
            case OrderItem.WAIT_PAY:
                return R.drawable.bg_red_stroke_rectangle_corner_r100;
            case OrderItem.IS_CANCEL:
                return R.drawable.bg_black_stroke_rectangle_corner_r100;
            default:
                return R.drawable.bg_black_stroke_rectangle_corner_r100;
        }
    }

    public static String getOrderBusinessString(int orderStatus){
        switch (orderStatus){
            case OrderItem.IS_FINISH:
                return "已完成";
            case OrderItem.WAIT_PAY:
                return "立即支付";
            case OrderItem.IS_CANCEL:
                return "已取消";
            default:
                return "查看物流";
        }
    }

}
