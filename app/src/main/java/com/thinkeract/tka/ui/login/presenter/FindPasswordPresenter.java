package com.thinkeract.tka.ui.login.presenter;

import com.thinkeract.tka.common.utils.Md5;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.request.FindPasswordBody;
import com.thinkeract.tka.ui.login.contract.FindPasswordContract;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

/**
 * Created by luqian on 2016/10/30.
 */

public class FindPasswordPresenter implements FindPasswordContract.Presenter {
    private FindPasswordContract.View mView;

    public FindPasswordPresenter(FindPasswordContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void findPassword(String mobile,String password, String validateNum) {
        FindPasswordBody findPasswordBody = new FindPasswordBody();
        findPasswordBody.setCode(validateNum);
        findPasswordBody.setPassword(Md5.ecryptSpecial(password));
        findPasswordBody.setMobile(mobile);
        ApiFactory.findPassword(findPasswordBody).subscribe(new ProgressSubscriber<ApiResponse>(mView.getContext()) {
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
