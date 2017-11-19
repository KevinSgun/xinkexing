package com.thinkeract.tka.ui.home;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.Logger;
import com.thinkeract.tka.common.utils.Utils;
import com.thinkeract.tka.data.api.entity.StepItem;
import com.thinkeract.tka.ui.BaseActivity;
import com.thinkeract.tka.ui.home.contract.StepStuffContract;
import com.thinkeract.tka.ui.home.presenter.StepStuffPresenter;
import com.thinkeract.tka.widget.MultiRadioGroup;
import com.today.step.lib.ISportStepInterface;
import com.today.step.lib.TodayStepService;
import com.zitech.framework.SP;
import com.zitech.framework.utils.ToastMaster;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnTouchListener, MultiRadioGroup.OnCheckedChangeListener, StepStuffContract.View {
    private RadioButton mainMenuHomeRb;
    private FrameLayout mainMenuHomeLayout;
    private RadioButton mainMenuMine;
    private FrameLayout mainMenuMineLayout;
    private MultiRadioGroup mainRadioGroup;
    private long exitTime;
    private ISportStepInterface iSportStepInterface;
    private long mLastStepCount;
    private long mCurrentStepCount;
    private Handler mDelayHandler = new Handler(new TodayStepCounterCall());
    private StepStuffPresenter mPresenter;

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
        mLastStepCount = SP.getDefaultSP().getLong(Constants.STEP_COUNT, 0);
        bindStepCount();
    }

    private void bindStepCount() {
        //开启计步Service，同时绑定Activity进行aidl通信
        Intent intent = new Intent(this, TodayStepService.class);
        startService(intent);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //Activity和Service通过aidl进行通信
                iSportStepInterface = ISportStepInterface.Stub.asInterface(service);
                try {
                    mCurrentStepCount = iSportStepInterface.getCurrentTimeSportStep();
                    SP.getDefaultSP().putLong(Constants.STEP_COUNT, mCurrentStepCount+mLastStepCount);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mDelayHandler.sendEmptyMessageDelayed(0, 1000);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
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
        mPresenter = new StepStuffPresenter(this);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSuccess(List<StepItem> response) {

    }

    class TodayStepCounterCall implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            //每隔1000毫秒获取一次计步数据刷新UI
            if (null != iSportStepInterface) {
                int step = 0;
                try {
                    step = iSportStepInterface.getCurrentTimeSportStep();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (mCurrentStepCount != step) {
                    mCurrentStepCount = step;
                    Logger.i("qwert","存mCurrentStepCount步数："+mCurrentStepCount);
                    SP.getDefaultSP().putLong(Constants.STEP_COUNT, mLastStepCount+mCurrentStepCount);
                    long total = mLastStepCount+mCurrentStepCount;
                    Logger.i("qwert","存步数总："+(total));
                }
            }
            mDelayHandler.sendEmptyMessageDelayed(0, 1000);

            return false;
        }
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
                showFragment(MineFragment.class);
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

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.upStepCount(String.valueOf(mCurrentStepCount+mLastStepCount));
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}
