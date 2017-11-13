package com.thinkeract.tka.pay.presenter;

import android.app.Activity;

import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.request.PoBody;
import com.thinkeract.tka.pay.PayCallBack;
import com.thinkeract.tka.pay.alipay.PayInfo;
import com.thinkeract.tka.pay.alipay.PayInfoResponseData;
import com.thinkeract.tka.pay.contract.OrderPayContract;
import com.thinkeract.tka.pay.wxpay.WXPay;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

/**
 * Created by minHeng on 2016/11/2 15:54.
 * mail:minhengyan@gmail.com
 */

public class OrderPayPresenter implements OrderPayContract.Presenter {

    private OrderPayContract.View mView;


    public OrderPayPresenter(OrderPayContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void payByWxPay(String orderId) {
        PoBody body = new PoBody();
        body.setPo(orderId);
        ApiFactory.payForOrderByWx(body).subscribe(new ProgressSubscriber<ApiResponse<PayInfoResponseData<PayInfo>>>(mView.getContext()) {
            @Override
            public void onNext(ApiResponse<PayInfoResponseData<PayInfo>> response) {
                PayInfo payInfo = response.getData().getPayInfo();
                WXPay.getInstance((Activity) mView.getContext()).startPay(payInfo.toPayReq(), new PayCallBack() {
                    @Override
                    public void onCallBack(String msg) {
                        mView.showPaySuccess(msg);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mView.showPayFailed(msg,-1);
                    }
                });
            }
        });
    }

    @Override
    public void payByAliPay(String orderId) {
//        PoBody body = new PoBody();
//        body.setPo(orderId);
//        ApiFactory.payForOrderByZFBbody).subscribe(new ProgressSubscriber<ApiResponse<PayInfoResponseData<String>>>(mView.getContext()) {
//            @Override
//            public void onNext(ApiResponse<PayInfoResponseData<String>> response) {
//                AliPay.getInstance().startPay(mView.getContext(),response.getData().getPayInfo(), new PayCallBack() {
//                    @Override
//                    public void onCallBack(String msg) {
//                        mView.showPaySuccess(msg);
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        mView.showPayFailed(msg,-1);
//                    }
//                });
//            }
//        });
    }

}
