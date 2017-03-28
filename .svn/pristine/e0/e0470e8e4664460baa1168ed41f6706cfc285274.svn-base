package com.thinkeract.tka.ui.login;

import android.content.Context;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.PermissionUtils;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.request.ValidationCodeBody;
import com.thinkeract.tka.ui.login.contract.RegisterContract;
import com.thinkeract.tka.ui.login.presenter.RegisterPresenter;
import com.zitech.framework.utils.ToastMaster;

/**
 * Created by minHeng on 2017/3/14 18:04.
 * mail:minhengyan@gmail.com
 */

public class RegisterActivity extends ValidateActivity implements RegisterContract.View {
    private EditText inputPhoneNumEt;
    private EditText inputValidateNumEt;
    private EditText inputPsdEt;
    private Button commitRegisterBtn;
    private TextView userAgreementTv;
    private RegisterPresenter registerPresenter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        setTitle(R.string.register);
        initializeView();
        PermissionUtils.isGrantPhoneStatus(this);
    }

    private void initializeView() {

        inputPhoneNumEt = (EditText) findViewById(R.id.inputPhoneNumEt);
        inputValidateNumEt = (EditText) findViewById(R.id.inputValidateNumEt);
        inputPsdEt = (EditText) findViewById(R.id.inputPsdEt);
        commitRegisterBtn  = (Button) findViewById(R.id.commitRegisterBtn);

        userAgreementTv = (TextView) findViewById(R.id.userAgreementTv);

        userAgreementTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        userAgreementTv.getPaint().setAntiAlias(true);

        userAgreementTv.setOnClickListener(this);
        commitRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.commitRegisterBtn){
            String name = this.inputPhoneNumEt.getText().toString();
            if (!validatePhone(name)) {
                return;
            }
            if (!validatePassword(inputPsdEt.getText().toString())) {
                return;
            }
            if (!validateCode(inputValidateNumEt.getText().toString().trim())) {
                ToastMaster.longToast(R.string.enter_right_validate_code);
                return;
            }
            String code = inputValidateNumEt.getText().toString().trim();
            String password = inputPsdEt.getText().toString();
            // register api
            registerPresenter.register(name, password, code);
        } else if(v.getId() == R.id.userAgreementTv){
//            SpecialEventBrowseActivity.launchSpecial(this, Constants.USER_AGREEMENT_PROTOCOL,"脉响用户协议");
        }
    }

    @Override
    protected void initData() {
        registerPresenter = new RegisterPresenter(this);
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
    public String getValidateCodeType() {
        return ValidationCodeBody.REGISTER;
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

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSuccess(boolean autoLogin) {

    }
}
