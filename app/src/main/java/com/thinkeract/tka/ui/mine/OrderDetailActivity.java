package com.thinkeract.tka.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.OrderDetailData;
import com.thinkeract.tka.data.api.request.PoBody;
import com.thinkeract.tka.pay.PayCallBack;
import com.thinkeract.tka.pay.alipay.PayInfo;
import com.thinkeract.tka.pay.alipay.PayInfoResponseData;
import com.thinkeract.tka.pay.wxpay.WXPay;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.mine.adapter.OrderDetailAdapter;
import com.thinkeract.tka.ui.mine.contract.OrderDetailContract;
import com.thinkeract.tka.ui.mine.presenter.OrderDetailPresenter;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ToastMaster;

import java.util.List;

/**
 * Created by ymh on 2017/4/9 22:38
 * e-mail:minhengyan@gmail.com
 */

public class OrderDetailActivity extends AppBarActivity implements OrderDetailContract.View {
    private String po;
    private RecyclerView OrderDetailRv;
    private OrderDetailAdapter mAdapter;
    private OrderDetailPresenter mPresenter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        setTitle(R.string.order_detail);
        po = getIntent().getStringExtra(Constants.ActivityExtra.PO);

        OrderDetailRv = (RecyclerView) findViewById(R.id.OrderDetailRv);

        mAdapter = new OrderDetailAdapter(this);
        mAdapter.setOnStuffClickListener(new OrderDetailAdapter.OnStuffClickListener() {
            @Override
            public void onPay(String po) {
                payByWX(po);
            }

            @Override
            public void lookLogistics(String po) {
                LogisticsDetailActivity.launch(OrderDetailActivity.this,po);
            }

            @Override
            public void comment() {

            }

            @Override
            public void deleteOrder(String po) {

            }
        });
        OrderDetailRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        OrderDetailRv.setAdapter(mAdapter);
    }

    public void payByWX(String orderId) {
        PoBody body = new PoBody();
        body.setPo(orderId);
        ApiFactory.payForOrderByWx(body).subscribe(new ProgressSubscriber<ApiResponse<PayInfoResponseData<PayInfo>>>(OrderDetailActivity.this) {
            @Override
            public void onNext(ApiResponse<PayInfoResponseData<PayInfo>> response) {
                PayInfo payInfo = response.getData().getPayInfo();
                WXPay.getInstance(OrderDetailActivity.this).startPay(payInfo.toPayReq(), new PayCallBack() {
                    @Override
                    public void onCallBack(String msg) {
                        ToastMaster.shortToast(msg);
                        mPresenter.getOrderDetail(po);
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastMaster.shortToast(msg);
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new OrderDetailPresenter(this);
        mPresenter.getOrderDetail(po);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSuccess(List<OrderDetailData> orderDetailDatas) {
        mAdapter.setItemList(orderDetailDatas);
    }


    public static void launch(Activity activity, String po) {
        Intent intent = new Intent(activity, OrderDetailActivity.class);
        intent.putExtra(Constants.ActivityExtra.PO, po);
        activity.startActivity(intent);
    }
}
