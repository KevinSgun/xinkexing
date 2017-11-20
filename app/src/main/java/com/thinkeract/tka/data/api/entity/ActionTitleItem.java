package com.thinkeract.tka.data.api.entity;

import com.thinkeract.tka.R;

/**
 * Author：YMH on 2017/10/16 0016 11:39
 * E-mail：minheng_yan@163.com
 */
public class ActionTitleItem {
    private String title;
    /**
     * 0代表黄色,'影响评分详细'，
     * <br>1代表紫色,'介入性治疗'，
     * <br>2代表红色,'非介入性治疗'，
     * <br>3代表蓝色,'自体康复'
     */
    private int colorType;

    public static final int YELLOW = 0;
    public static final int PURPLE = 1;
    public static final int RED = 2;
    public static final int BLUE = 3;

    public ActionTitleItem(){
    }

    public ActionTitleItem(String title,int colorType){
        this.title = title;
        this.colorType = colorType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColorTypeResId() {
        switch (colorType){
            case YELLOW:
                return R.drawable.bg_trapezoid_effect;
            case PURPLE:
                return R.drawable.bg_trapezoid_intervention_treatment;
            case RED:
                return R.drawable.bg_trapezoid_intervention_treatment_no;
            case BLUE:
                return R.drawable.bg_trapezoid_self_treatment;
        }
        return colorType;
    }

    public void setColorType(int colorType) {
        this.colorType = colorType;
    }
}
