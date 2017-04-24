package com.thinkeract.tka.ui.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.request.ValidationCodeBody;

/**
 * Created by minHeng on 2017/4/5 14:15.
 * mail:minhengyan@gmail.com
 */

public class BindingActivity extends ValidateActivity{
    private EditText inputPhoneNumEt;
    private EditText inputValidateNumEt;
    private Button doctorLoginBtn;
    private Button commitBtn;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_binding;
    }

    @Override
    protected void initView() {
        setTitle(R.string.bing_phone_num);
        initializeView();
        commitBtn.setOnClickListener(this);
    }

    private void initializeView() {
        inputPhoneNumEt = (EditText) findViewById(R.id.inputPhoneNumEt);
        inputValidateNumEt = (EditText) findViewById(R.id.inputValidateNumEt);
        commitBtn = (Button) findViewById(R.id.submitBtn);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.submitBtn){
            String name = this.inputPhoneNumEt.getText().toString();
            if (!validatePhone(name)) {
                return;
            }
            String validateNum = this.inputValidateNumEt.getText().toString();
            if (!validateCode(inputValidateNumEt.getText().toString())) {
                return;
            }
        }
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
        return ValidationCodeBody.BINDING;
    }
}
