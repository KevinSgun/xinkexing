package com.thinkeract.tka.ui;

import android.os.Bundle;
import android.view.View;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.thinkeract.tka.R;
import com.thinkeract.tka.widget.CustomToolBar;
import com.thinkeract.tka.widget.ToolBarHelper;

/**
 * Created by lu on 2016/6/14.
 */
public abstract class AppBarActivity extends BaseActivity {
    public CustomToolBar toolbar;
    protected static final int ITEM_RIGHT = ToolBarHelper.ITEM_RIGHT;
    protected static final int ITEM_LEFT = ToolBarHelper.ITEM_LEFT;

    @Override
    protected void setContentView() {
        ToolBarHelper toolBarHelper = new ToolBarHelper(this, getContentViewId());
        toolbar = toolBarHelper.getToolBar();
        initToolBarEvent();
        setContentView(toolBarHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        // StatusBarCompat.compat(this);

        setTranslucentStatus(true);
//            findViewById(R.id.contentLayout).setPadding(0, ViewUtils.getStatuBarHeight(this), 0, 0);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.drawable.bg_toolbar);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //在Swipebacklayout里面的attachToActivity方法里面再用SystemBarTint设置一下状态栏的颜色即可。
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.drawable.bg_toolbar);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        toolbar.setTitleText(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        toolbar.setTitleText(titleId);
    }

    public CustomToolBar getToolbar() {
        return toolbar;
    }

    private void initToolBarEvent() {
        toolbar.setOnLeftClickListenr(leftOnClickListener);
        toolbar.setOnRightClickListener(rightOnClickListener);
    }

    protected void setLeftVisible(int visiable) {
        toolbar.setLeftVisible(visiable);
    }

//    public void setLeftText(String text){
//        toolbar.setLeftText(text);
//    }

    protected void setRightVisible(int visiable) {
        toolbar.setRightVisible(visiable);
    }

    protected void setRightText(int titleId) {
        toolbar.setRightText(titleId);
    }

    protected void setRightText(String title) {
        toolbar.setRightText(title);
    }

    protected void setRightIcon(int imgId) {
        toolbar.setRightIcon(imgId);
    }

    protected void setLeftIcon(int imgId) {
        toolbar.setLeftIcon(imgId);
    }


    protected void onActionBarItemClick(int position) {
        if (position == ToolBarHelper.ITEM_LEFT)
            back();
    }

    public View.OnClickListener leftOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onActionBarItemClick(ToolBarHelper.ITEM_LEFT);
        }
    };

    public View.OnClickListener rightOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onActionBarItemClick(ToolBarHelper.ITEM_RIGHT);
        }
    };

    @Override
    protected void onDestroy() {
        toolbar.destroyView();
        super.onDestroy();
    }

}
