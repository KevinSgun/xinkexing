package com.thinkeract.tka.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.Events;
import com.thinkeract.tka.R;
import com.thinkeract.tka.User;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.request.ValidationCodeBody;
import com.thinkeract.tka.ui.home.MainActivity;
import com.thinkeract.tka.ui.login.contract.LoginContract;
import com.thinkeract.tka.ui.login.presenter.LoginPresenter;
import com.thinkeract.tka.ui.mine.IdentityReviewActivity;
import com.zitech.framework.utils.ToastMaster;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by minHeng on 2017/3/14 17:51.
 * mail:minhengyan@gmail.com
 */

public class LoginActivity extends ValidateActivity implements LoginContract.View {
    private EditText inputPhoneNumEt;
    private EditText inputValidateNumEt;
    private Button doctorLoginBtn;
    private Button loginBtn;
    private LoginPresenter loginPresenter;
    private LinearLayout weChatLoginLayout;
    private TextView userProtocolTv;
    private long exitTime;
    private boolean launchHome;
    private boolean mIsDoctorLogin;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        setTitle(R.string.sign_in);
        launchHome = getIntent().getBooleanExtra(Constants.ActivityExtra.LAUNCH_HOME, false);
        setTranslucentStatus(true);
        initializeView();
        doctorLoginBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        weChatLoginLayout.setOnClickListener(this);
        userProtocolTv.setOnClickListener(this);
    }

    private void initializeView() {
        inputPhoneNumEt = (EditText) findViewById(R.id.inputPhoneNumEt);
        inputValidateNumEt = (EditText) findViewById(R.id.inputValidateNumEt);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        doctorLoginBtn = (Button) findViewById(R.id.doctorLoginBtn);
        weChatLoginLayout = (LinearLayout) findViewById(R.id.weChatLoginLayout);
        userProtocolTv = (TextView) findViewById(R.id.userProtocolTv);

        userProtocolTv.getPaint().setUnderlineText(true);
        userProtocolTv.getPaint().setAntiAlias(true);
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    protected int getValidateCodeButtonId() {
        return R.id.getValidateBtn;
    }

    @Override
    protected String getPhoneNumber() {
        return inputPhoneNumEt.getText().toString();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.loginBtn://普通登录请求
                mIsDoctorLogin = false;
                loginStuff();
                break;
            case R.id.doctorLoginBtn: //医生登录请求
                mIsDoctorLogin = true;
                loginStuff();
                break;
            case R.id.weChatLoginLayout: //微信登录请求

                break;
            case R.id.userProtocolTv: //查看用户协议

                break;
        }
    }

    private void loginStuff() {
        String name = this.inputPhoneNumEt.getText().toString();
        if (!validatePhone(name)) {
            return;
        }
        String validateNum = this.inputValidateNumEt.getText().toString();
        if (!validateCode(inputValidateNumEt.getText().toString())) {
            return;
        }
        loginPresenter.login(name, validateNum);
    }


    @Override
    public String getValidateCodeType() {
        return ValidationCodeBody.REGISTER;
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
        if (mIsDoctorLogin && !User.get().isDoctor()) {
            ToastMaster.shortToast("您还不是医生，请先进行身份审核");
            showActivity(IdentityReviewActivity.class);
        } else {
            EventBus.getDefault().post(new Events.CloseEvent(Events.CloseEvent.FINISH_PERFECT_DATA));
        }
    }

    @Override
    public void onNotifyClose(Events.CloseEvent closeEvent) {
        super.onNotifyClose(closeEvent);
        if (Events.CloseEvent.FINISH_PERFECT_DATA.equals(closeEvent.eventType)) {
            launchHome();
        }
    }

    private void launchHome() {
        if (launchHome) {
            showActivity(MainActivity.class);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        if (launchHome)
            exitApp();
        else
            super.onBackPressed();
    }

    /**
     * @param context
     * @param launchHome 是否需要打开一个新的MainActivity
     */
    public static void launch(Activity context, boolean launchHome) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.ActivityExtra.LAUNCH_HOME, launchHome);
        context.startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN, context);
    }
}
