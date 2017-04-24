package com.thinkeract.tka.ui.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.entity.DataEditVO;
import com.thinkeract.tka.ui.AppBarActivity;
import com.zitech.framework.utils.ToastMaster;

import java.util.Locale;

/**
 * Created by minHeng on 2017/3/22 16:01.
 * mail:minhengyan@gmail.com
 */

public class DataEditActivity extends AppBarActivity {
    private EditText editContentEt;
    private int maxLength = 10;
    private Button commitBtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_data_edit;
    }

    @Override
    protected void initView() {
        DataEditVO dataEditVO = getIntent().getParcelableExtra(Constants.ActivityExtra.DATA_EDIT_VO);
        String title = dataEditVO.getTitle();
        String content = dataEditVO.getContent();
        String hintContent = dataEditVO.getHintTxt();
        maxLength = dataEditVO.getLimitLength();
        setTitle(title);
        editContentEt = (EditText) findViewById(R.id.editContentEt);

        commitBtn = (Button) findViewById(R.id.submitBtn);

        if(!TextUtils.isEmpty(content)){
            editContentEt.setText(content);
            editContentEt.setSelection(content.length());
        }
        if(!TextUtils.isEmpty(hintContent)){
            editContentEt.setHint(hintContent);
        }

        editContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() >=maxLength+1){
                    editContentEt.setText(s.toString().substring(0,maxLength));
                    editContentEt.setSelection(maxLength);
                    ToastMaster.shortToast(String.format(Locale.CHINESE,"最多不能超过%d个字符",maxLength));
                }
            }
        });

        commitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.submitBtn){
            if(TextUtils.isEmpty(editContentEt.getText().toString())){
                ToastMaster.shortToast("内容不能为空");
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(Constants.ActivityExtra.DATA_CONTENT,editContentEt.getText().toString());
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    protected void initData() {

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

    public static void launchForResult(Activity act, int requestCode, DataEditVO dataEditVO){
        Intent intent = new Intent(act,DataEditActivity.class);
        intent.putExtra(Constants.ActivityExtra.DATA_EDIT_VO,dataEditVO);
        act.startActivityForResult(intent,requestCode);
        ViewUtils.anima(ViewUtils.RIGHT_IN,act);
    }

}
