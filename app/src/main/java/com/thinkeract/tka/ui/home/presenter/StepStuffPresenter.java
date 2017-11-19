package com.thinkeract.tka.ui.home.presenter;

import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.StepItem;
import com.thinkeract.tka.data.api.request.StepsBody;
import com.thinkeract.tka.ui.home.contract.StepStuffContract;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.List;

/**
 * Created by ymh on 2016/10/30 10:53
 * e-mail:minhengyan@gmail.com
 */

public class StepStuffPresenter implements StepStuffContract.Presenter {
    private StepStuffContract.View mView;

    public StepStuffPresenter(StepStuffContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getStepCountData() {
        ApiFactory.stepCountData().subscribe(new ProgressSubscriber<ApiResponse<List<StepItem>>>(mView.getContext()){
            @Override
            public void onNext(ApiResponse<List<StepItem>> value) {
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

    @Override
    public void upStepCount(String steps) {
        StepsBody body = new StepsBody();
        body.setSteps(steps);
        ApiFactory.upStepCount(body).subscribe(new ProgressSubscriber<ApiResponse>(mView.getContext(),false){
            @Override
            public void onNext(ApiResponse value) {
                super.onNext(value);
                mView.showSuccess(null);
            }

            @Override
            public void onError(Throwable e) {
                mView.showError(e.getMessage());
            }
        });
    }
}
