package com.thinkeract.tka.ui.mine;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.User;
import com.thinkeract.tka.common.utils.StringUtils;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.DataEditVO;
import com.thinkeract.tka.data.api.request.UpdateUserDataBody;
import com.thinkeract.tka.ui.mine.contract.PerfectDataContract;
import com.thinkeract.tka.ui.mine.presenter.PerfectDataPresenter;
import com.thinkeract.tka.ui.preview.PhotoPickingActivity;
import com.thinkeract.tka.widget.AgePicker;
import com.thinkeract.tka.widget.BottomLinearPicker;
import com.thinkeract.tka.widget.CommonDialog;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.transform.CropCircleTransformation;
import com.zitech.framework.utils.ToastMaster;
import com.zitech.framework.widget.RemoteImageView;
import java.io.File;
import io.reactivex.functions.Consumer;

/**
 * Created by minHeng on 2017/3/22 14:08.
 * mail:minhengyan@gmail.com
 */

public class AccountSettingActivity extends PhotoPickingActivity implements PerfectDataContract.View {
    private RemoteImageView avatarIv;
    private RelativeLayout uploadAvatarLayout;
    private TextView userNameTv;
    private RelativeLayout userNameLayout;
    private TextView genderTv;
    private RelativeLayout genderLayout;
    private TextView ageTv;
    private RelativeLayout ageLayout;
    private TextView identityAgainTv;
    private LinearLayout identityAgainLayout;
    private Button logoutBtn;
    private PerfectDataPresenter perfectDataPresenter;
    private boolean setAvatar;
    private static int EDIT_USER_NAME = 0x31;
    private String mGender;
    private AgePicker mAgePicker;
    private int mAge = 18;
    private BottomLinearPicker mBottomLinearPicker;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_account_setting;
    }

    @Override
    protected void initView() {
        setTitle(R.string.account_setting);
        initializeView();
        uploadAvatarLayout.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        userNameLayout.setOnClickListener(this);
        ageLayout.setOnClickListener(this);
        genderLayout.setOnClickListener(this);
        identityAgainTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        perfectDataPresenter = new PerfectDataPresenter(this);

        avatarIv.setBitmapTransformation(new CropCircleTransformation(getContext()));
        avatarIv.setImageUri(R.mipmap.ic_avatar_def, User.get().getPortrait());

        if (!TextUtils.isEmpty(User.get().getName())) {
            userNameTv.setText(User.get().getName());
        }else{
            userNameTv.setText(StringUtils.hideMiddle(User.get().getMobile()));
        }

        if (!TextUtils.isEmpty(User.get().getGenderValue())) {
            genderTv.setText(User.get().getGenderValue());
        }

        if (!TextUtils.isEmpty(User.get().getAge())) {
            ageTv.setText(String.format(getString(R.string.age_str),User.get().getAge()));
        }

        if(User.get().isDoctor()){
            identityAgainLayout.setVisibility(View.VISIBLE);
        }else{
            identityAgainLayout.setVisibility(View.GONE);
        }

    }

    private void initializeView() {
        avatarIv = (RemoteImageView) findViewById(R.id.avatarIv);
        uploadAvatarLayout = (RelativeLayout) findViewById(R.id.uploadAvatarLayout);

        userNameTv = (TextView) findViewById(R.id.userNameTv);
        userNameLayout = (RelativeLayout) findViewById(R.id.userNameLayout);

        genderTv = (TextView) findViewById(R.id.genderTv);
        genderLayout = (RelativeLayout) findViewById(R.id.genderLayout);

        ageTv = (TextView) findViewById(R.id.ageTv);
        ageLayout = (RelativeLayout) findViewById(R.id.ageLayout);

        identityAgainTv = (TextView) findViewById(R.id.identityAgainTv);
        identityAgainLayout = (LinearLayout) findViewById(R.id.identityLayout);

        logoutBtn = (Button) findViewById(R.id.logoutBtn);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.uploadAvatarLayout:
                chooseAndUploadAvatar();
                break;
            case R.id.userNameLayout:
                DataEditVO nameData = new DataEditVO();
                nameData.setContent(User.get().getName());
                nameData.setTitle("填写资料");
                nameData.setHintTxt("修改姓名");
                nameData.setLimitLength(10);
                DataEditActivity.launchForResult(this,EDIT_USER_NAME,nameData);
                break;
            case R.id.genderLayout:
                chooseGender();
                break;
            case R.id.ageLayout:
                chooseAge();
                break;
            case R.id.identityAgainTv:
                IdentityReviewActivity.launch(this);
                break;
            case R.id.logoutBtn:
                showLogOutDialog();
                break;
        }
    }

    private void chooseAge() {
        if(mAgePicker == null) {
            mAgePicker = new AgePicker(this);
            mAgePicker.setListener(new AgePicker.AgePickerListener() {
                @Override
                public void onPicked(int selectedAge) {
                    mAge = selectedAge;
                    ageTv.setText(String.format(getString(R.string.age),selectedAge));
                    UpdateUserDataBody body = new UpdateUserDataBody();
                    body.setAge(String.valueOf(selectedAge));
                    perfectDataPresenter.completeProfile(body);
                }
            });
        }
        mAgePicker.setLastSelect(mAge);
        mAgePicker.show();
    }

    private void chooseGender() {
        if(mBottomLinearPicker == null) {
            mBottomLinearPicker = new BottomLinearPicker(this, "选择性别");
            mBottomLinearPicker.addText("男", R.color.blue_5080d8);
            mBottomLinearPicker.addText("女", R.color.text_pink);
            mBottomLinearPicker.setPickerListener(new BottomLinearPicker.ItemPickerListener() {
                @Override
                public void onPicked(int itemIndex, String itemStr) {
                    if (itemIndex == 1) {
                        mGender = "1";
                    } else if (itemIndex == 2) {
                        mGender = "0";
                    }
                    genderTv.setText(itemStr);
                    UpdateUserDataBody body = new UpdateUserDataBody();
                    body.setGender(mGender);
                    perfectDataPresenter.completeProfile(body);
                }
            });
        }
        mBottomLinearPicker.show();
    }


    private void showLogOutDialog() {
        CommonDialog centerDialog = new CommonDialog(this, "确定退出当前登录吗？");
        centerDialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
            @Override
            public void onClick(Dialog dialog) {
                User.get().clear();
                AccountSettingActivity.this.finish();
            }
        });
        centerDialog.show();
    }

    private void chooseAndUploadAvatar() {
        requestTakePhoto(getString(R.string.change_avatar), EFFECT_TYPE_CUT,Constants.AVATAR, new PhotoTakeListener() {
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
        avatarIv.setImageUri("file://" + picturePath);
        ApiFactory.uploadSingle(Constants.UPLOAD_TYPE_AVATAR, new File(picturePath)).subscribe(new Consumer<ApiResponse<String>>() {

            @Override
            public void accept(ApiResponse<String> stringApiResponse) throws Exception {
                User.get().storePortraitNotify(stringApiResponse.getData());
                setAvatar = true;
            }

        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastMaster.shortToast("上传头像失败");
            }
        });
    }

    @Override
    public void onFinalPhotoTakeResult(int listenerType, String picturePath) {
        upAvatar(picturePath);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK || data == null)
            return;
        String dataContent = data.getStringExtra(Constants.ActivityExtra.DATA_CONTENT);
        if(TextUtils.isEmpty(dataContent)){
            return;
        }
        UpdateUserDataBody body = new UpdateUserDataBody();
        if(requestCode == EDIT_USER_NAME){
            body.setNickName(dataContent);
            userNameTv.setText(dataContent);
        }
        perfectDataPresenter.completeProfile(body);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSuccess() {
        User.get().notifyChange();
    }
}
