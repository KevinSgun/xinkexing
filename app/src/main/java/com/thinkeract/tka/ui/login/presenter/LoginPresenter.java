package com.thinkeract.tka.ui.login.presenter;

import com.thinkeract.tka.User;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.response.UserData;
import com.thinkeract.tka.ui.login.contract.LoginContract;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ToastMaster;

/**
 * Created by lu on 2016/10/23.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void login(String username, String password) {
        ApiFactory.login(username, password).subscribe(new ProgressSubscriber<ApiResponse<UserData>>(mView.getContext()) {
            @Override
            public void onNext(ApiResponse<UserData> userDataApiResponse) {
                User.get().updateFrom(userDataApiResponse.getData());
                ToastMaster.shortToast(userDataApiResponse.getMsg());
            }

            @Override
            public void onComplete() {
                super.onComplete();
                mView.showSuccess();
            }
        });
    }
}
