package com.thinkeract.tka.ui.login.presenter;

import com.thinkeract.tka.common.utils.Md5;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.request.FindPasswordBody;
import com.thinkeract.tka.data.api.request.LoginBody;
import com.thinkeract.tka.ui.login.contract.BindingPhoneContract;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

/**
 * Created by luqian on 2016/10/30.
 */

public class BindingPhonePresenter implements BindingPhoneContract.Presenter {
    private BindingPhoneContract.View mView;

    public BindingPhonePresenter(BindingPhoneContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void bindingPhone(String mobile,String validateNum) {
        ApiFactory.bindingPhone(mobile,validateNum).subscribe(new ProgressSubscriber<ApiResponse>(mView.getContext()) {
            @Override
            public void onNext(ApiResponse apiResponse) {
                mView.showSuccess();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError(e.getMessage());
            }
        });
    }
}
