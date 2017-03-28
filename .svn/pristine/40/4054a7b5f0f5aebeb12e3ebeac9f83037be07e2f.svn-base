package com.thinkeract.tka.ui.login.presenter;

import com.thinkeract.tka.R;
import com.thinkeract.tka.User;
import com.thinkeract.tka.common.utils.Md5;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.request.RegisterBody;
import com.thinkeract.tka.data.api.response.UserData;
import com.thinkeract.tka.ui.login.contract.RegisterContract;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ToastMaster;

/**
 * Created by lu on 2016/10/23.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View mView;

    public RegisterPresenter(RegisterContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void register(final String phone, final String password, String code) {
        RegisterBody registerBody = new RegisterBody();
        registerBody.setCode(code);
        registerBody.setPassword(Md5.ecryptSpecial(password));
        registerBody.setMobile(phone);
        ApiFactory.register(registerBody).subscribe(new ProgressSubscriber<ApiResponse<UserData>>(mView.getContext()) {
            @Override
            public void onNext(ApiResponse<UserData> apiResponse) {
                ToastMaster.shortToast(R.string.register_success);
                User.get().updateFrom(apiResponse.getData());
                mView.showSuccess(true);
            }

            @Override
            public void onError (Throwable e){
                super.onError(e);
                mView.showError(e.getMessage());
            }
        });
    }

}
