package com.thinkeract.tka.ui.mine;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.ui.preview.PhotoPickingActivity;
import com.thinkeract.tka.widget.MultiRadioGroup;
import com.zitech.framework.widget.RemoteImageView;

/**
 *
 * Created by minHeng on 2017/4/5 17:27.
 * mail:minhengyan@gmail.com
 */

public class IdentityReviewActivity extends PhotoPickingActivity {
    private MultiRadioGroup mainRadioGroup;
    private ViewAnimator identityAnimator;
    private EditText userNameEt;
    private EditText hospitalNameEt;
    private TextView departmentNameTv;
    private RelativeLayout departmentLayout;
    private TextView positionNameTv;
    private RelativeLayout positionsLayout;
    private EditText departmentPhoneNameEt;
    private EditText areaNumNameEt;
    private EditText goodAtIntroduceEt;
    private Button nextBtn;
    private RemoteImageView doctorCertificateIv;
    private Button commitForReviewBtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_identity_review;
    }

    @Override
    protected void initView() {
        setTitle(R.string.identity_review);
        initializeView();

        nextBtn.setOnClickListener(this);
        commitForReviewBtn.setOnClickListener(this);
        departmentLayout.setOnClickListener(this);
        positionsLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private void initializeView() {

        mainRadioGroup = (MultiRadioGroup) findViewById(R.id.mainRadioGroup);
        identityAnimator = (ViewAnimator) findViewById(R.id.identityAnimator);

        //first step
        userNameEt = (EditText) findViewById(R.id.userNameEt);
        hospitalNameEt = (EditText) findViewById(R.id.hospitalNameEt);
        departmentNameTv = (TextView) findViewById(R.id.departmentNameTv);
        departmentLayout = (RelativeLayout) findViewById(R.id.departmentLayout);
        positionNameTv = (TextView) findViewById(R.id.positionNameTv);
        positionsLayout = (RelativeLayout) findViewById(R.id.positionsLayout);
        departmentPhoneNameEt = (EditText) findViewById(R.id.departmentPhoneNameEt);
        areaNumNameEt = (EditText) findViewById(R.id.areaNumNameEt);
        goodAtIntroduceEt = (EditText) findViewById(R.id.goodAtIntroduceEt);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        //

        //second step
        doctorCertificateIv = (RemoteImageView) findViewById(R.id.doctorCertificateIv);
        commitForReviewBtn = (Button) findViewById(R.id.commitForReviewBtn);
        //

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.nextBtn:
                identityAnimator.setDisplayedChild(1);
                mainRadioGroup.check(R.id.identityRb);
                break;
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
}
