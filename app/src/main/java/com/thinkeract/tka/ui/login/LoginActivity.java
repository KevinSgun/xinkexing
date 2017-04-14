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
import com.thinkeract.tka.ui.home.MainActivity;
import com.thinkeract.tka.ui.login.contract.LoginContract;
import com.thinkeract.tka.ui.login.presenter.LoginPresenter;
import com.zitech.framework.utils.ToastMaster;

import org.greenrobot.eventbus.EventBus;

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
//                ChooseGoodsSpecDialog chooseGoodsSpecDialog = new ChooseGoodsSpecDialog(this);
//                List<Sku> gList = new ArrayList<>();
//                Sku goodsSpec = new Sku();
//                Sku goodsSpec2 = new Sku();
//                Sku goodsSpec3 = new Sku();
//                List<Sku.Spec> gSpecList = new ArrayList<>();
//                List<Sku.Spec> gSpecList2 = new ArrayList<>();
//                List<Sku.Spec> gSpecList3 = new ArrayList<>();
//                Sku.Spec gs1 = new Sku.Spec();
//                gs1.setName("500ml");
//                Sku.Spec gs11 = new Sku.Spec();
//                gs11.setName("1000ml");
//                Sku.Spec gs22 = new Sku.Spec();
//                gs22.setName("2000ml");
//                Sku.Spec gs23 = new Sku.Spec();
//                gs23.setName("20000ml");
//                Sku.Spec gs24 = new Sku.Spec();
//                gs24.setName("40000ml");
//                Sku.Spec gs25 = new Sku.Spec();
//                gs25.setName("800000ml");
//                gSpecList.add(gs1);
//                gSpecList.add(gs11);
//                gSpecList.add(gs22);
//
//                goodsSpec.setName("容量");
//                goodsSpec.setItems(gSpecList);
//
//                Sku.Spec gs2 = new Sku.Spec();
//                gs2.setName("黄色");
//                Sku.Spec gs3 = new Sku.Spec();
//                gs3.setName("红色");
//                Sku.Spec gs4 = new Sku.Spec();
//                gs4.setName("蓝色");
//                Sku.Spec gs5 = new Sku.Spec();
//                gs5.setName("绿色");
//                Sku.Spec gs6 = new Sku.Spec();
//                gs6.setName("梦幻紫色");
//                gSpecList2.add(gs2);
//                gSpecList2.add(gs3);
//                gSpecList2.add(gs4);
//                gSpecList2.add(gs5);
//                gSpecList2.add(gs6);
//
//                goodsSpec2.setName("炫酷的颜色");
//                goodsSpec2.setItems(gSpecList2);
//
//                Sku.Spec gs44 = new Sku.Spec();
//                gs44.setName("黄色");
//                Sku.Spec gs41 = new Sku.Spec();
//                gs41.setName("红色");
//                Sku.Spec gs42 = new Sku.Spec();
//                gs42.setName("蓝色");
//                Sku.Spec gs43 = new Sku.Spec();
//                gs43.setName("绿色");
//                Sku.Spec gs45 = new Sku.Spec();
//                gs45.setName("梦幻紫色");
//                gSpecList3.add(gs44);
//                gSpecList3.add(gs41);
//                gSpecList3.add(gs42);
//                gSpecList3.add(gs43);
//                gSpecList3.add(gs45);
//
//                goodsSpec3.setName("不同的颜色");
//                goodsSpec3.setItems(gSpecList3);
//
//                gList.add(goodsSpec);
//                gList.add(goodsSpec2);
//                gList.add(goodsSpec3);
//
//                chooseGoodsSpecDialog.setData(gList);
//                chooseGoodsSpecDialog.show();
                break;
            case R.id.weChatLoginLayout: //微信登录请求

                break;
            case R.id.userProtocolTv: //查看用户协议

                break;
        }
    }


    @Override
    public String getValidateCodeType() {
        return ValidationCodeBody.REGISTER;
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
