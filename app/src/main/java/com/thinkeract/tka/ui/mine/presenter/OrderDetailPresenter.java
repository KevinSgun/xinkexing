package com.thinkeract.tka.ui.mine.presenter;

import com.thinkeract.tka.common.subscribe.HandlingErrorsProgressSubscriber;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.OrderDetailData;
import com.thinkeract.tka.data.api.request.PoBody;
import com.thinkeract.tka.ui.mine.adapter.OrderDetailAdapter;
import com.thinkeract.tka.ui.mine.contract.OrderDetailContract;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minHeng on 2016/11/19 16:11.
 * mail:minhengyan@gmail.com
 */

public class OrderDetailPresenter implements OrderDetailContract.Presenter {
    private OrderDetailContract.View mView;

    public OrderDetailPresenter(OrderDetailContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getOrderDetail(String po) {
        PoBody poBody = new PoBody();
        poBody.setPo(po);
        ApiFactory.orderDetail(poBody).subscribe(new HandlingErrorsProgressSubscriber<ApiResponse<OrderDetailData>>(mView.getContext()) {
            @Override
            public void onNext(ApiResponse<OrderDetailData> value) {
                super.onNext(value);
                mView.showSuccess(convertOrderDetail(value.getData()));
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError(e.getMessage());
            }
        });
    }

    private List<OrderDetailData> convertOrderDetail(OrderDetailData data) {
        List<OrderDetailData> orderDetailDatas = new ArrayList<>();
        OrderDetailData addressAndStatus = new OrderDetailData();
        addressAndStatus.setItemType(OrderDetailAdapter.ADDRESS_AND_STATUS);
        addressAndStatus.setPo(data.getPo());
        addressAndStatus.setStatus(data.getStatus());
        addressAndStatus.setDate(data.getDate());
        orderDetailDatas.add(addressAndStatus);
        for(OrderDetailData.OrderDetailGoods goods:data.getGoods()){
            OrderDetailData goodsItem = new OrderDetailData();
            goodsItem.setItemType(OrderDetailAdapter.GOODS_ITEM);
            goodsItem.setDetailGoods(goods);
            orderDetailDatas.add(goodsItem);
        }

        orderDetailDatas.add(data);
        return orderDetailDatas;
    }
}
