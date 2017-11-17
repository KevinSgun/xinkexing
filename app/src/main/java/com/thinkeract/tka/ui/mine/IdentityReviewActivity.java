package com.thinkeract.tka.ui.mine;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ViewAnimator;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.ThinkerActApplication;
import com.thinkeract.tka.User;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.request.DoctorDataReviewBody;
import com.thinkeract.tka.ui.mine.contract.DoctorAuthContract;
import com.thinkeract.tka.ui.mine.presenter.DoctorAuthPresenter;
import com.thinkeract.tka.ui.preview.PhotoPickingActivity;
import com.thinkeract.tka.widget.MultiRadioGroup;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.utils.ToastMaster;
import com.zitech.framework.widget.RemoteImageView;

import java.io.File;

import io.reactivex.functions.Consumer;

/**
 *
 * Created by minHeng on 2017/4/5 17:27.
 * mail:minhengyan@gmail.com
 */

public class IdentityReviewActivity extends PhotoPickingActivity implements DoctorAuthContract.View {
    private MultiRadioGroup mainRadioGroup;
    private ViewAnimator identityAnimator;
    private EditText userNameEt;
    private EditText hospitalNameEt;
//    private TextView departmentNameTv;
    private RelativeLayout departmentLayout;
//    private TextView positionNameTv;
    private RelativeLayout positionsLayout;
    private EditText departmentPhoneNameEt;
    private EditText areaNumNameEt;
    private EditText goodAtIntroduceEt;
    private Button nextBtn;
    private RemoteImageView doctorCertificateIv;
    private Button commitForReviewBtn;
    private String mName;
    private String mHospital;
    private EditText mDepartmentNameEt;
    private EditText mPositionNameEt;
    private String mDepartmentName;//科室
    private String mPositionName;//职称
    private String mAreaNum;//区号
    private String mDepartmentPhone;//科室电话号码
    private String mGoodAtIntroduce;
    private DoctorAuthPresenter mPresenter;

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
        doctorCertificateIv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPresenter = new DoctorAuthPresenter(this);
    }

    private void initializeView() {

        mainRadioGroup = (MultiRadioGroup) findViewById(R.id.mainRadioGroup);
        identityAnimator = (ViewAnimator) findViewById(R.id.identityAnimator);

        //first step
        userNameEt = (EditText) findViewById(R.id.userNameEt);
        hospitalNameEt = (EditText) findViewById(R.id.hospitalNameEt);
//        departmentNameTv = (TextView) findViewById(R.id.departmentNameTv);
        departmentLayout = (RelativeLayout) findViewById(R.id.departmentLayout);
//        positionNameTv = (TextView) findViewById(R.id.positionNameTv);
        mDepartmentNameEt = (EditText) findViewById(R.id.departmentNameEt);
        mPositionNameEt = (EditText) findViewById(R.id.positionNameEt);
        positionsLayout = (RelativeLayout) findViewById(R.id.positionsLayout);
        areaNumNameEt = (EditText) findViewById(R.id.areaNumNameEt);
        departmentPhoneNameEt = (EditText) findViewById(R.id.departmentPhoneNameEt);
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
                if(checkBasicDataStatus()) {
                    identityAnimator.setDisplayedChild(1);
                    mainRadioGroup.check(R.id.identityRb);
                }
                break;
            case R.id.doctorCertificateIv:
                chooseAndUploadCertificate();
                break;
        }
    }

    private void chooseAndUploadCertificate() {
        requestTakePhoto(getString(R.string.doctor_certificate), EFFECT_TYPE_CUT, Constants.DOCTOR_AUTH, new PhotoTakeListener() {
            @Override
            public void onPhotoTake(String picturePath) {

            }

            @Override
            public void onPhotoCut(String picturePath, String cutPicturePath) {
                upAvatar(cutPicturePath);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void upAvatar(String picturePath) {
        if(TextUtils.isEmpty(picturePath)) return;
        doctorCertificateIv.setImageUri("file://" + picturePath);
        ApiFactory.uploadSingle(Constants.UPLOAD_TYPE_AVATAR, new File(picturePath)).subscribe(new Consumer<ApiResponse<String>>() {

            @Override
            public void accept(ApiResponse<String> stringApiResponse) throws Exception {
                User.get().notifyChange();
                DoctorDataReviewBody body = new DoctorDataReviewBody();
                body.setName(mName);
                body.setHospital(mHospital);
                body.setSection(mDepartmentName);
                body.setJobTitle(mPositionName);
                body.setQualifications(stringApiResponse.getData());
                body.setPhoneNumber(mAreaNum+mDepartmentPhone);
                body.setRemark(mGoodAtIntroduce);
                mPresenter.doDoctorAuth(body);
//                User.get().storePortraitNotify(stringApiResponse.getData());
//                setAvatar = true;
            }

        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastMaster.shortToast("上传失败");
            }
        });
    }


    @Override
    public void showSuccess(String message) {
        ToastMaster.shortToast(message);
        ThinkerActApplication.getInstance().postDelay(new Runnable() {
            @Override
            public void run() {
                showActivity(IdentityResultActivity.class);
                finish();
            }
        },400);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onFinalPhotoTakeResult(int listenerType, String picturePath) {
        upAvatar(picturePath);
    }

    private boolean checkBasicDataStatus() {
        mName = userNameEt.getText().toString();
        if(TextUtils.isEmpty(mName)){
            ToastMaster.shortToast("姓名不能为空");
            return false;
        }

        mHospital = hospitalNameEt.getText().toString();
        if(TextUtils.isEmpty(mHospital)){
            ToastMaster.shortToast("所在医院不能为空");
            return false;
        }

        mDepartmentName = mDepartmentNameEt.getText().toString();
        if(TextUtils.isEmpty(mDepartmentName)){
            ToastMaster.shortToast("所在科室不能为空");
            return false;
        }

        mPositionName = mPositionNameEt.getText().toString();
        if(TextUtils.isEmpty(mPositionName)){
            ToastMaster.shortToast("职称不能为空");
            return false;
        }

        mAreaNum = areaNumNameEt.getText().toString();
        if(TextUtils.isEmpty(mAreaNum)){
            ToastMaster.shortToast("区号不能为空");
            return false;
        }

        mDepartmentPhone = departmentPhoneNameEt.getText().toString();
        if(TextUtils.isEmpty(mDepartmentPhone)){
            ToastMaster.shortToast("电话号码不能为空");
            return false;
        }


        mGoodAtIntroduce = goodAtIntroduceEt.getText().toString();
        return true;
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
