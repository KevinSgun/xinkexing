package com.thinkeract.tka.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.Events;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.StringUtils;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.ui.BaseActivity;
import com.thinkeract.tka.ui.home.MainActivity;
import com.thinkeract.tka.ui.login.contract.LoginContract;
import com.thinkeract.tka.ui.login.presenter.LoginPresenter;
import com.zitech.framework.utils.ToastMaster;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by minHeng on 2017/3/14 17:51.
 * mail:minhengyan@gmail.com
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {
    private EditText inputPhoneNumEt;
    private EditText inputPassWordEt;
    private Button forgetPsdBtn;
    private Button loginBtn;
    private TextView registerTv;
    private long exitTime;
    private boolean launchHome;
    private LoginPresenter loginPresenter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        setTranslucentStatus(true);
        initializeView();
        registerTv.setOnClickListener(this);
        forgetPsdBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    private void initializeView() {
        inputPhoneNumEt = (EditText) findViewById(R.id.inputPhoneNumEt);
        inputPassWordEt = (EditText) findViewById(R.id.inputPassWordEt);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerTv = (TextView) findViewById(R.id.registerTv);
        forgetPsdBtn = (Button) findViewById(R.id.forgetPsdBtn);
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
//            case R.id.actionBarLeft:
//                back();
//                break;
            case R.id.loginBtn:
                //登陆请求
                String name = this.inputPhoneNumEt.getText().toString();
                if (!validatePhone(name)) {
                    return;
                }
                String password = this.inputPassWordEt.getText().toString();
                if (!validatePassword(inputPassWordEt.getText().toString())) {
                    return;
                }
                loginPresenter.login(name, password);
                break;
            case R.id.registerTv:
//                RegisterActivity.launch(this);
                break;
            case R.id.forgetPsdBtn:
//                Bundle bundle = new Bundle();
//                bundle.putBoolean(Constants.ActivityExtra.IS_FROM_LOGIN, true);
//                showActivity(ForgetPsdActivity.class, bundle);
                break;
        }
    }

    /**
     * 验证用户 名是否有效
     */
    protected boolean validatePhone(String name) {
        if (StringUtils.isPhoneNum(name)) {
            return true;
        } else {
            ToastMaster.longToast(R.string.enter_right_phone_num);
            return false;
        }
    }

    /**
     * 验证密码
     */
    protected boolean validatePassword(String password) {
        if (!StringUtils.isPasswordValid(password)) {
            ToastMaster.longToast(R.string.incorrect_passowrd_length);
            return false;
        }
        return true;

    }

    private void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) { // 第一次System.currentTimeMillis()无论何时调用，差值肯定大于2000
            ToastMaster.shortToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            EventBus.getDefault().post(new Events.CloseEvent(Events.CloseEvent.FINISH_ALL));
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void onNotifyClose(Events.CloseEvent closeEvent) {
        super.onNotifyClose(closeEvent);
//        if (Events.CloseEvent.FINISH_REGISTER.equals(closeEvent.enventType)) {
//            if (!User.get().isCompleted()) {
//                showActivity(PerfectDataActivity.class);
//                return;
//            }
//            launchHome();
//        } else if (Events.CloseEvent.FINISH_PERFECT_DATA.equals(closeEvent.enventType)) {
//            launchHome();
//        }
    }

    private void launchHome() {
        finish();
        if (launchHome) {
            showActivity(MainActivity.class);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (ViewUtils.isTouchedOutsideView(v, ev)) {
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    /**
     * @param context
     * @param launchHome 是否需要打开一个新的MainActivity
     */
    public static void launch(Activity context, boolean launchHome) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.ActivityExtra.LAUNCH_HOME, launchHome);
        context.startActivity(intent);
    }
}
