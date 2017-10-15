package com.thinkeract.tka.ui.home.presenter;

import com.thinkeract.tka.common.subscribe.HandlingErrorsProgressSubscriber;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.request.IdRequest;
import com.thinkeract.tka.data.api.response.CheckResultData;
import com.thinkeract.tka.ui.home.contract.CheckResultStuffContract;
import com.zitech.framework.data.network.response.ApiResponse;

/**
 * Created by ymh on 2016/10/30 10:53
 * e-mail:minhengyan@gmail.com
 */

public class CheckResultStuffPresenter implements CheckResultStuffContract.Presenter {
    private CheckResultStuffContract.View mView;

    public CheckResultStuffPresenter(CheckResultStuffContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getCheckResultData(String id) {
        IdRequest body = new IdRequest();
        body.setId(id);
        ApiFactory.getCheckResult(body).subscribe(new HandlingErrorsProgressSubscriber<ApiResponse<CheckResultData>>(mView.getContext()) {
            @Override
            public void onNext(ApiResponse<CheckResultData> value) {
                super.onNext(value);
                mView.showSuccess(value.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError(e.getMessage());
            }
        });
    }
}
