package com.thinkeract.tka.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.Events;
import com.thinkeract.tka.R;
import com.thinkeract.tka.User;
import com.thinkeract.tka.ui.BaseFragment;
import com.thinkeract.tka.ui.login.LoginActivity;
import com.thinkeract.tka.ui.mall.MallMainActivity;
import com.thinkeract.tka.ui.mine.AccountSettingActivity;
import com.thinkeract.tka.ui.mine.MyOrderListActivity;
import com.thinkeract.tka.ui.mine.SettingActivity;
import com.thinkeract.tka.ui.mine.ShippingAddressListActivity;
import com.zitech.framework.transform.CropCircleTransformation;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by minHeng on 2017/3/14 16:54.
 * mail:minhengyan@gmail.com
 */

public class MineFragment extends BaseFragment {
    private RemoteImageView userAvatarIv;
    private TextView userNameTv;
    private LinearLayout avatarLayout;
    private LinearLayout orderLayout;
    private LinearLayout bodyCheckupLayout;
    private LinearLayout liveLayout;
    private RelativeLayout shippingAddressLayout;
    private RelativeLayout accountSettingLayout;
    private RelativeLayout customerServiceLayout;
    private RelativeLayout sysSettingLayout;
    private RelativeLayout aboutUsLayout;
    private RelativeLayout mallLayout;

    @Override
    protected void onPreInflate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onPreInflate(inflater, container, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_my;
    }

    @Subscribe
    public void onMainThreadUserDataChange(Events.UserDataEvent data) {
        if (getActivity() != null && isAdded()) {
            removeCacheImageUrl();
            refreshUI(User.get());
        }
    }

    private void removeCacheImageUrl() {
        userAvatarIv.removeUrl();
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        userAvatarIv = (RemoteImageView) contentView.findViewById(R.id.userAvatarIv);
        userNameTv = (TextView) contentView.findViewById(R.id.userNameTv);
        avatarLayout = (LinearLayout) contentView.findViewById(R.id.avatarLayout);
        orderLayout = (LinearLayout) contentView.findViewById(R.id.orderLayout);
        bodyCheckupLayout = (LinearLayout) contentView.findViewById(R.id.bodyCheckupLayout);
        liveLayout = (LinearLayout) contentView.findViewById(R.id.liveLayout);
        shippingAddressLayout = (RelativeLayout) contentView.findViewById(R.id.shippingAddressLayout);
        accountSettingLayout = (RelativeLayout) contentView.findViewById(R.id.accountSettingLayout);
        customerServiceLayout = (RelativeLayout) contentView.findViewById(R.id.customerServiceLayout);
        sysSettingLayout = (RelativeLayout) contentView.findViewById(R.id.sysSettingLayout);
        aboutUsLayout = (RelativeLayout) contentView.findViewById(R.id.aboutUsLayout);
        mallLayout = (RelativeLayout) contentView.findViewById(R.id.mallLayout);

        avatarLayout.setOnClickListener(this);
        orderLayout.setOnClickListener(this);
        bodyCheckupLayout.setOnClickListener(this);
        liveLayout.setOnClickListener(this);
        shippingAddressLayout.setOnClickListener(this);
        accountSettingLayout.setOnClickListener(this);
        customerServiceLayout.setOnClickListener(this);
        sysSettingLayout.setOnClickListener(this);
        aboutUsLayout.setOnClickListener(this);
        mallLayout.setOnClickListener(this);
    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        refreshUI(User.get());
    }

    private void refreshUI(User user) {

        if (user.isVisitor()) {
            userAvatarIv.setImageUri(Constants.ImageDefResId.DEF_AVA_NORMAL,"");
            userNameTv.setText("请先登录");
        }else{
            userAvatarIv.setBitmapTransformation(new CropCircleTransformation(getContext()));
            userAvatarIv.setImageUri(Constants.ImageDefResId.DEF_AVA_NORMAL, user.getPortrait());
            userNameTv.setText(user.getName());
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (ViewUtils.isFastDoubleClick()) return;
        if (User.get().isVisitor()) {
            LoginActivity.launch(getActivity(), false);
            return;
        }
        switch (v.getId()) {
            case R.id.avatarLayout://当未登录时点击跳转登陆
                if(User.get().isVisitor())
                    showActivity(LoginActivity.class);
                break;
            case R.id.orderLayout://跳转到我的订单页面
                showActivity(MyOrderListActivity.class);
                break;
            case R.id.bodyCheckupLayout://跳转到体检报告页面
                break;
            case R.id.liveLayout://跳转到观看直播页面
                break;
            case R.id.shippingAddressLayout:////跳转到收货地址页面
                showActivity(ShippingAddressListActivity.class);
                break;
            case R.id.accountSettingLayout:////跳转到账户设置页面
                showActivity(AccountSettingActivity.class);
                break;
            case R.id.customerServiceLayout:////跳转到在线客服页面
                break;
            case R.id.sysSettingLayout:////跳转到系统设置页面
                showActivity(SettingActivity.class);
                break;
            case R.id.aboutUsLayout:////跳转到关于我们页面
                break;
            case R.id.mallLayout://跳转到商城
                showActivity(MallMainActivity.class);
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
