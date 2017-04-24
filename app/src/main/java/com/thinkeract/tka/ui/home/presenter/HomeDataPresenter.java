package com.thinkeract.tka.ui.home.presenter;

import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.response.HomePageData;
import com.thinkeract.tka.ui.home.contract.HomeDataContract;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

/**
 * Created by ymh on 2016/10/30 10:53
 * e-mail:minhengyan@gmail.com
 */

public class HomeDataPresenter implements HomeDataContract.Presenter {
    private HomeDataContract.View mView;

    public HomeDataPresenter(HomeDataContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getHomeData(boolean showProgress) {
        ApiFactory.homePageData().subscribe(new ProgressSubscriber<ApiResponse<HomePageData>>(mView.getContext(),showProgress){
            @Override
            public void onNext(ApiResponse<HomePageData> value) {
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
