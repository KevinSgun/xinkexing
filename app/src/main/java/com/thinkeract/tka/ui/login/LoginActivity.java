package com.thinkeract.tka.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.Events;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.request.ValidationCodeBody;
import com.thinkeract.tka.data.api.entity.GoodsSpec;
import com.thinkeract.tka.ui.home.MainActivity;
import com.thinkeract.tka.ui.login.contract.LoginContract;
import com.thinkeract.tka.ui.login.presenter.LoginPresenter;
import com.thinkeract.tka.widget.ChooseGoodsSpecDialog;
import com.zitech.framework.utils.ToastMaster;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minHeng on 2017/3/14 17:51.
 * mail:minhengyan@gmail.com
 */

public class LoginActivity extends ValidateActivity implements LoginContract.View {
    private EditText inputPhoneNumEt;
    private EditText inputValidateNumEt;
    private Button doctorLoginBtn;
    private Button loginBtn;
    private LoginPresenter loginPresenter;
    private LinearLayout weChatLoginLayout;
    private TextView userProtocolTv;
    private long exitTime;
    private boolean launchHome;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        setTitle(R.string.sign_in);
        launchHome = getIntent().getBooleanExtra(Constants.ActivityExtra.LAUNCH_HOME,false);
        setTranslucentStatus(true);
        initializeView();
        doctorLoginBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        weChatLoginLayout.setOnClickListener(this);
        userProtocolTv.setOnClickListener(this);
    }

    private void initializeView() {
        inputPhoneNumEt = (EditText) findViewById(R.id.inputPhoneNumEt);
        inputValidateNumEt = (EditText) findViewById(R.id.inputValidateNumEt);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        doctorLoginBtn = (Button) findViewById(R.id.doctorLoginBtn);
        weChatLoginLayout = (LinearLayout) findViewById(R.id.weChatLoginLayout);
        userProtocolTv = (TextView) findViewById(R.id.userProtocolTv);

        userProtocolTv.getPaint().setUnderlineText(true);
        userProtocolTv.getPaint().setAntiAlias(true);
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter(this);
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.loginBtn://普通登录请求
                String name = this.inputPhoneNumEt.getText().toString();
                if (!validatePhone(name)) {
                    return;
                }
                String validateNum = this.inputValidateNumEt.getText().toString();
                if (!validateCode(inputValidateNumEt.getText().toString())) {
                    return;
                }
                loginPresenter.login(name, validateNum);
                break;
            case R.id.doctorLoginBtn: //医生登录请求
//                showActivity(MainActivity.class);
//                showActivity(IdentityReviewActivity.class);
//                showActivity(ShoppingCartActivity.class);
                ChooseGoodsSpecDialog chooseGoodsSpecDialog = new ChooseGoodsSpecDialog(this);
                List<GoodsSpec> gList = new ArrayList<>();
                GoodsSpec goodsSpec = new GoodsSpec();
                GoodsSpec goodsSpec2 = new GoodsSpec();
                GoodsSpec goodsSpec3 = new GoodsSpec();
                List<GoodsSpec.Spec> gSpecList = new ArrayList<>();
                List<GoodsSpec.Spec> gSpecList2 = new ArrayList<>();
                List<GoodsSpec.Spec> gSpecList3 = new ArrayList<>();
                GoodsSpec.Spec gs1 = new GoodsSpec.Spec();
                gs1.setSpecValue("500ml");
                GoodsSpec.Spec gs11 = new GoodsSpec.Spec();
                gs11.setSpecValue("1000ml");
                GoodsSpec.Spec gs22 = new GoodsSpec.Spec();
                gs22.setSpecValue("2000ml");
                GoodsSpec.Spec gs23 = new GoodsSpec.Spec();
                gs23.setSpecValue("20000ml");
                GoodsSpec.Spec gs24 = new GoodsSpec.Spec();
                gs24.setSpecValue("40000ml");
                GoodsSpec.Spec gs25 = new GoodsSpec.Spec();
                gs25.setSpecValue("800000ml");
                gSpecList.add(gs1);
                gSpecList.add(gs11);
                gSpecList.add(gs22);
//                gSpecList.add(gs23);
//                gSpecList.add(gs24);
//                gSpecList.add(gs25);
                goodsSpec.setSpecType("容量");
                goodsSpec.setSpecItems(gSpecList);

                GoodsSpec.Spec gs2 = new GoodsSpec.Spec();
                gs2.setSpecValue("黄色");
                GoodsSpec.Spec gs3 = new GoodsSpec.Spec();
                gs3.setSpecValue("红色");
                GoodsSpec.Spec gs4 = new GoodsSpec.Spec();
                gs4.setSpecValue("蓝色");
                GoodsSpec.Spec gs5 = new GoodsSpec.Spec();
                gs5.setSpecValue("绿色");
                GoodsSpec.Spec gs6 = new GoodsSpec.Spec();
                gs6.setSpecValue("梦幻紫色");
                gSpecList2.add(gs2);
                gSpecList2.add(gs3);
                gSpecList2.add(gs4);
                gSpecList2.add(gs5);
                gSpecList2.add(gs6);

                goodsSpec2.setSpecType("炫酷的颜色");
                goodsSpec2.setSpecItems(gSpecList2);

                GoodsSpec.Spec gs44 = new GoodsSpec.Spec();
                gs44.setSpecValue("黄色");
                GoodsSpec.Spec gs41 = new GoodsSpec.Spec();
                gs41.setSpecValue("红色");
                GoodsSpec.Spec gs42 = new GoodsSpec.Spec();
                gs42.setSpecValue("蓝色");
                GoodsSpec.Spec gs43 = new GoodsSpec.Spec();
                gs43.setSpecValue("绿色");
                GoodsSpec.Spec gs45 = new GoodsSpec.Spec();
                gs45.setSpecValue("梦幻紫色");
                gSpecList3.add(gs44);
                gSpecList3.add(gs41);
                gSpecList3.add(gs42);
                gSpecList3.add(gs43);
                gSpecList3.add(gs45);

                goodsSpec3.setSpecType("不同的颜色");
                goodsSpec3.setSpecItems(gSpecList3);

                gList.add(goodsSpec);
                gList.add(goodsSpec2);
                gList.add(goodsSpec3);

                chooseGoodsSpecDialog.setData(gList);
                chooseGoodsSpecDialog.show();
                break;
            case R.id.weChatLoginLayout: //微信登录请求

                break;
            case R.id.userProtocolTv: //查看用户协议

                break;
        }
    }


    @Override
    public String getValidateCodeType() {
        return ValidationCodeBody.LOG_IN;
    }

    private void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) { // 第一次System.currentTimeMillis()无论何时调用，差值肯定大于2000
            ToastMaster.shortToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            EventBus.getDefault().post(new Events.CloseEvent(Events.CloseEvent.FINISH_ALL));
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSuccess() {
        EventBus.getDefault().post(new Events.CloseEvent(Events.CloseEvent.FINISH_PERFECT_DATA));
    }

    @Override
    public void onNotifyClose(Events.CloseEvent closeEvent) {
        super.onNotifyClose(closeEvent);
        if (Events.CloseEvent.FINISH_PERFECT_DATA.equals(closeEvent.eventType)) {
            launchHome();
        }
    }

    private void launchHome() {
        if (launchHome) {
            showActivity(MainActivity.class);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        if (launchHome)
            exitApp();
        else
            super.onBackPressed();
    }

    /**
     * @param context
     * @param launchHome 是否需要打开一个新的MainActivity
     */
    public static void launch(Activity context, boolean launchHome) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.ActivityExtra.LAUNCH_HOME, launchHome);
        context.startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN,context);
    }
}
