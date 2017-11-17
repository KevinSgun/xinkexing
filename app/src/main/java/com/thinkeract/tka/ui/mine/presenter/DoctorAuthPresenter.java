package com.thinkeract.tka.ui.mine.presenter;

import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.request.DoctorDataReviewBody;
import com.thinkeract.tka.ui.mine.contract.DoctorAuthContract;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

/**
 * Created by ymh on 2017/10/18 21:57
 * e-mail:minhengyan@gmail.com
 */

public class DoctorAuthPresenter implements DoctorAuthContract.Presenter {

    private DoctorAuthContract.View mView;

    public DoctorAuthPresenter(DoctorAuthContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void doDoctorAuth(DoctorDataReviewBody body) {
        ApiFactory.doctorDataReview(body).subscribe(new ProgressSubscriber<ApiResponse>(mView.getContext()){
            @Override
            public void onNext(ApiResponse value) {
                super.onNext(value);
                mView.showSuccess(value.getMsg());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError(e.getMessage());
            }
        });
    }
}
