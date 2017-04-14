package com.thinkeract.tka.ui.mine.presenter;

import com.thinkeract.tka.User;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.request.UpdateUserDataBody;
import com.thinkeract.tka.ui.mine.contract.PerfectDataContract;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

/**
 * Created by minHeng on 2016/11/19 16:11.
 * mail:minhengyan@gmail.com
 */

public class PerfectDataPresenter implements PerfectDataContract.Presenter {
    private PerfectDataContract.View mView;

    public PerfectDataPresenter(PerfectDataContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void completeProfile(final UpdateUserDataBody body) {
        ApiFactory.updateUserData(body).subscribe(new ProgressSubscriber<ApiResponse>(mView.getContext()) {
            @Override
            public void onNext(ApiResponse apiResponse) {
                User.get().updateFrom(body);
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
