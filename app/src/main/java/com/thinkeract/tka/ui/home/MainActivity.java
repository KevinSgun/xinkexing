package com.thinkeract.tka.ui.home;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.Utils;
import com.thinkeract.tka.ui.BaseActivity;
import com.thinkeract.tka.widget.MultiRadioGroup;
import com.zitech.framework.utils.ToastMaster;

public class MainActivity extends BaseActivity implements View.OnTouchListener, MultiRadioGroup.OnCheckedChangeListener {
    private RadioButton mainMenuHomeRb;
    private FrameLayout mainMenuHomeLayout;
    private RadioButton mainMenuMine;
    private FrameLayout mainMenuMineLayout;
    private MultiRadioGroup mainRadioGroup;
    private long exitTime;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setTranslucentStatus(true);
        initializeView();
        showFragment(MainFragment.class);
        mainMenuHomeLayout.setOnTouchListener(this);
        mainMenuMineLayout.setOnTouchListener(this);
        mainRadioGroup.setOnCheckedChangeListener(this);
    }

    private void initializeView() {
        mainMenuHomeRb = (RadioButton) findViewById(R.id.mainMenuHomeRb);
        mainMenuHomeLayout = (FrameLayout) findViewById(R.id.mainMenuHomeLayout);
        mainMenuMine = (RadioButton) findViewById(R.id.mainMenuMineRb);
        mainMenuMineLayout = (FrameLayout) findViewById(R.id.mainMenuMineLayout);
        mainRadioGroup = (MultiRadioGroup) findViewById(R.id.mainRadioGroup);
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            switch (view.getId()) {
                case R.id.mainMenuHomeLayout:
                    mainRadioGroup.check(R.id.mainMenuHomeRb);
                    break;
                case R.id.mainMenuMineLayout:
                    mainRadioGroup.check(R.id.mainMenuMineRb);
                    break;
                default:
                    break;

            }
        }
        return true;
    }

    @Override
    public void onCheckedChanged(MultiRadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.mainMenuHomeRb:
                if (!mainMenuHomeRb.isChecked()) return;
                showFragment(MainFragment.class);
                break;
            case R.id.mainMenuMineRb:
                if (!mainMenuMine.isChecked()) return;
                showFragment(MyFragment.class);
                break;
            default:
                break;
        }
    }

    public Fragment showFragment(Class<? extends Fragment> fragmentClass) {
        return Utils.replace(getSupportFragmentManager(), R.id.contentFrame, fragmentClass);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) { // 第一次System.currentTimeMillis()无论何时调用，差值肯定大于2000
                ToastMaster.shortToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
