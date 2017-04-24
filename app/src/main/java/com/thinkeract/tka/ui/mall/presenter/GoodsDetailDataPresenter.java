package com.thinkeract.tka.ui.mall.presenter;

import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.request.IdRequest;
import com.thinkeract.tka.data.api.response.GoodsDetailData;
import com.thinkeract.tka.ui.mall.contract.GoodsDetailDataContract;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

/**
 * Created by minHeng on 2016/11/19 16:11.
 * mail:minhengyan@gmail.com
 */

public class GoodsDetailDataPresenter implements GoodsDetailDataContract.Presenter {
    private GoodsDetailDataContract.View mView;

    public GoodsDetailDataPresenter(GoodsDetailDataContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getGoodsDetail(String goodsId) {
        IdRequest idRequest = new IdRequest();
        idRequest.setId(goodsId);
        ApiFactory.goodsDetail(idRequest).subscribe(new ProgressSubscriber<ApiResponse<GoodsDetailData>>(mView.getContext()){
            @Override
            public void onNext(ApiResponse<GoodsDetailData> value) {
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
