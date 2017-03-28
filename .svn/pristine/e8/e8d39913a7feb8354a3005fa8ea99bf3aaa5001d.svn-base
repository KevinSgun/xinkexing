package com.thinkeract.tka.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.PermissionUtils;
import com.thinkeract.tka.data.api.request.ValidationCodeBody;
import com.thinkeract.tka.ui.login.contract.FindPasswordContract;
import com.thinkeract.tka.ui.login.presenter.FindPasswordPresenter;
import com.zitech.framework.utils.ToastMaster;

/**
 * Created by minHeng on 2017/3/14 18:12.
 * mail:minhengyan@gmail.com
 */

public class ForgetPsdActivity extends ValidateActivity implements FindPasswordContract.View {
    private EditText inputPhoneNumEt;
    private EditText inputValidateNumEt;
    private EditText inputPsdEt;
    private Button commitRegisterBtn;
    private FindPasswordPresenter findPasswordPresenter;
    private TextView goLoginTv;
    private boolean isFromLoginAct;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_forget_psd;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            isFromLoginAct = bundle.getBoolean(Constants.ActivityExtra.IS_FROM_LOGIN,false);
        }
        initializeView();
        PermissionUtils.isGrantPhoneStatus(this);
    }

    @Override
    protected void initData() {
        findPasswordPresenter = new FindPasswordPresenter(this);
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
        return ValidationCodeBody.FIND_PASSWORD;
    }

    private void initializeView() {
        inputPhoneNumEt = (EditText) findViewById(R.id.inputPhoneNumEt);
        inputValidateNumEt = (EditText) findViewById(R.id.inputValidateNumEt);
        inputPsdEt = (EditText) findViewById(R.id.inputPsdEt);
        commitRegisterBtn  = (Button) findViewById(R.id.commitRegisterBtn);
        goLoginTv = (TextView) findViewById(R.id.goLoginTv);

        commitRegisterBtn.setOnClickListener(this);
        goLoginTv.setOnClickListener(this);
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
            findPasswordPresenter.findPassword(name, password, code);
        }else if(v.getId() == R.id.goLoginTv){
            if(isFromLoginAct)
                back();
            else
                showActivity(LoginActivity.class);
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSuccess() {
        ToastMaster.shortToast("成功重置密码，请重新登陆");
        finish();
    }
}
