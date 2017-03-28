package com.thinkeract.tka.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkeract.tka.R;

/**
 * Created by lu on 2016/6/14.
 * 自定义ToolBar
 */
public class CustomToolBar extends Toolbar {
    private Context mContext;
    private ImageView mActionLeftIcon;
    private View mActionbarLeft;
    private TextView mActionbarTitle;
    private TextView mActionTightText;
    private ImageView mActionRightIcon;
    private FrameLayout mActionbarRight;
    private FrameLayout mBackground;
//    private TextView mActionLeftText;
    public CustomToolBar(Context context) {
        super(context);
        this.mContext = context;
        initView();

    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.include_toolbar, this);
        mActionLeftIcon = (ImageView) view.findViewById(R.id.action_left_img);
        mActionbarLeft = view.findViewById(R.id.action_bar_left);
        mActionbarTitle = (TextView) view.findViewById(R.id.action_bar_title);
        mActionTightText = (TextView) view.findViewById(R.id.action_right_txt);
        mActionRightIcon = (ImageView) view.findViewById(R.id.action_right_img);
        mActionbarRight = (FrameLayout) view.findViewById(R.id.action_bar_right);
//        mActionLeftText= (TextView) view.findViewById(R.id.action_left_text);
//        MaterialRippleLayout.on(mActionbarLeft).create();
    }

//    public void setLeftText(String text){
//        mActionLeftText.setText(text);
//    }


    public void setLeftIcon(int icon) {
        mActionLeftIcon.setVisibility(View.VISIBLE);
        mActionLeftIcon.setImageResource(icon);
    }

    public void setOnLeftClickListenr(OnClickListener listener) {
        mActionbarLeft.setOnClickListener(listener);
    }

    public void setOnRightClickListener(OnClickListener listener) {
        mActionbarRight.setOnClickListener(listener);
    }

    /**
     * 设置背景颜色
     */
    public void setBackgroundColor(int color) {
        mBackground.setBackgroundColor(color);
    }

    /**
     * @param visible 左边按钮显示状态
     */
    public void setLeftVisible(int visible) {
        mActionbarLeft.setVisibility(visible);
    }

    /**
     * @param right 右边按钮显示状态
     */
    public void setRightVisible(int right) {
        mActionbarRight.setVisibility(right);
    }

    public void setTitleColor(int color) {
        mActionbarTitle.setTextColor(color);
    }

    public void setTitleText(int resId) {
        mActionbarTitle.setText(getResources().getString(resId));
    }

    public void setTitleText(CharSequence title) {
        mActionbarTitle.setText(title);
    }

    public void setRightText(String txtStr) {
        mActionbarRight.setVisibility(VISIBLE);
        mActionTightText.setText(txtStr);
    }

    public void setRightText(int txtStrId) {
        mActionbarRight.setVisibility(VISIBLE);
        mActionTightText.setText(getResources().getString(txtStrId));
    }

    public void setRightIcon(int icon) {
        mActionbarRight.setVisibility(View.VISIBLE);
        mActionRightIcon.setVisibility(View.VISIBLE);
        mActionRightIcon.setImageResource(icon);
    }


    @SuppressLint("NewApi")
    public void destroyView() {
        if (mActionbarTitle != null) {
            mActionbarTitle.setText(null);
        }
        if (mActionTightText != null) {
            mActionTightText.setText(null);
        }
        if (mActionbarLeft != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mActionbarLeft.setBackground(null);
            }else {
                mActionbarLeft.setBackgroundDrawable(null);
            }
        }

        if (mActionLeftIcon != null) {
            mActionLeftIcon.setImageDrawable(null);
        }

        if (mActionRightIcon != null) {
            mActionRightIcon.setImageDrawable(null);
        }
    }

    public View getLeftView() {
        return mActionbarLeft;
    }

    public View getLeftIcon() {
        return mActionLeftIcon;
    }

    public View getRightView() {
        return mActionbarRight;
    }

    public View getRightIcon() {
        return mActionRightIcon;
    }


    public View getRightText(){
        return  mActionTightText;
    }

}
