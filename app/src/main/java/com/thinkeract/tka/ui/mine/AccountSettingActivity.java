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
            genderTv.setText(User.get().getName());
        }

        if (User.get().getAge()>0) {
            genderTv.setText(String.format(getString(R.string.age),User.get().getAge()));
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
                //TODO 选择性别
                break;
            case R.id.ageLayout:
                //TODO 选择年龄
                break;
            case R.id.logoutBtn:
                showLogOutDialog();
                break;
        }
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
        requestTakePhoto(getString(R.string.choose_picture), EFFECT_TYPE_CUT, new PhotoTakeListener() {
            @Override
            public void onPhotoTake(String picturePath) {

            }

            @Override
            public void onPhotoCut(String picturePath, String cutPicturePath) {
                avatarIv.setImageUri("file://" + cutPicturePath);
                ApiFactory.upload(Constants.UPLOAD_TYPE_AVATAR, new File(cutPicturePath)).subscribe(new Consumer<ApiResponse<String>>() {

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
            public void onFailure() {

            }
        });
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
