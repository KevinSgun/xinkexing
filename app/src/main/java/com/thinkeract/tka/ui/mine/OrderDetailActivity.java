package com.thinkeract.tka.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.OrderDetailData;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.mine.adapter.OrderDetailAdapter;
import com.thinkeract.tka.ui.mine.contract.OrderDetailContract;
import com.thinkeract.tka.ui.mine.presenter.OrderDetailPresenter;

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
        po = getIntent().getStringExtra(Constants.ActivityExtra.PO);

        OrderDetailRv = (RecyclerView) findViewById(R.id.OrderDetailRv);

        mAdapter = new OrderDetailAdapter(this);

        OrderDetailRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        OrderDetailRv.setAdapter(mAdapter);
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


    public static void launch(Activity activity, String po){
        Intent intent = new Intent(activity,OrderDetailActivity.class);
        intent.putExtra(Constants.ActivityExtra.PO,po);
        activity.startActivity(intent);
    }
}
