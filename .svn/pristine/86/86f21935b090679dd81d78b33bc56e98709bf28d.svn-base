package com.thinkeract.tka.ui.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.StringUtils;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.request.ValidationCodeBody;
import com.thinkeract.tka.ui.AppBarActivity;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ToastMaster;


/**
 * 需要验证码的地方实在太多，因此抽出来
 *
 * @author lu
 */
public abstract class ValidateActivity extends AppBarActivity {

    private static final int MIN_VALIDATE_CODE_LENGTH = Constants.MIN_VALIDATE_CODE_LENGTH;
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static long lastGetValidateCodeTime = -1;
    private Button validateCodeButton;
    private int maxWaitSeconds = 60;

    protected abstract int getValidateCodeButtonId();

    protected abstract String getPhoneNumber();

    @Override
    protected void onStart() {
        super.onStart();
        if (validateCodeButton == null) {
            validateCodeButton = (Button) findViewById(getValidateCodeButtonId());
        }
        if (validateCodeButton != null) {
            validateCodeButton.setOnClickListener(this);// (this);
        }
    }

    @Override
    public void onClick(View v) {
        if (getValidateCodeButton() != null && v.getId() == getValidateCodeButton().getId()) {
            getValidateCode();
        }
    }

    private Button getValidateCodeButton() {
        return validateCodeButton;
    }

    // protected Button getRegisterButton(){
    //
    // }

    /**
     * 验证用户 名是否有效
     */
    protected boolean validatePhone(String name) {
        if (StringUtils.isPhoneNum(name)) {
            return true;
        } else {
            // userInputPrompt.setText(R.string.enter_right_phone_num);
            ToastMaster.longToast(R.string.enter_right_phone_num);
            return false;
        }
    }

    /**
     * 验证密码
     */
    protected boolean validatePassword(String password) {
        if (!StringUtils.isPasswordValid(password)) {
            // userInputPrompt.setText(R.string.incorrect_passowrd_length);
            ToastMaster.longToast(R.string.incorrect_passowrd_length);
            return false;
        }
        return true;

    }

    protected boolean validateCode(String code) {
        return code.length() >= MIN_VALIDATE_CODE_LENGTH;
    }

    private void getValidateCode() {
        String phoneNum = getPhoneNumber();
        if (!validatePhone(phoneNum))
            return;
        if (lastGetValidateCodeTime != -1 && ((System.currentTimeMillis() / 1000) - lastGetValidateCodeTime) <= 60) {
            ToastMaster.shortToast("请求验证码过于频繁，请稍后再试");
            return;
        }
        // 获取验证码
//		VerificationCodeRequest veri = new VerificationCodeRequest();
        ValidationCodeBody body = new ValidationCodeBody();
        body.setMobile(phoneNum);
        body.setApkind(getValidateCodeType());
        ApiFactory.getValidationCode(body).subscribe(new ProgressSubscriber<ApiResponse>(this) {
            @Override
            public void onNext(ApiResponse apiResponse) {
                getValidateCodeButton().setTextColor(Color.GRAY);
                getValidateCodeButton().setEnabled(false);
                startCountDown();
                lastGetValidateCodeTime = System.currentTimeMillis() / 1000;
                ToastMaster.shortToast(R.string.get_validate_code_success);
            }
        });

    }

    private void startCountDown() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (maxWaitSeconds == 0) {
                    getValidateCodeButton().setText("重新获取");
                    getValidateCodeButton().setEnabled(true);
//					getValidateCodeButton().setTextColor(Color.GRAY);
                    maxWaitSeconds = 60;
                } else {
                    getValidateCodeButton().setText(String.valueOf(maxWaitSeconds) + "秒后重新获取");
                    if (!isFinishing()) {
                        startCountDown();
                    }
                }
                maxWaitSeconds--;
            }

        }, 1000);
    }

    public abstract String getValidateCodeType();

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

}
