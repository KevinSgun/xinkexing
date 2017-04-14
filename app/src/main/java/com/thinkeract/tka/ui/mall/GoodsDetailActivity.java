package com.thinkeract.tka.ui.mall;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.ui.BaseActivity;

/**
 * Created by minHeng on 2017/4/13 18:19.
 * mail:minhengyan@gmail.com
 */

public class GoodsDetailActivity extends BaseActivity{
    private LinearLayout actionBarLeft;
    private TextView actionBarTitle;
    private View goodsBarLine;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingtoolbar;
    private AppBarLayout appBar;
    private RecyclerView goodsDetailRv;
    private CoordinatorLayout rootView;
    private String mGoodsId;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView() {
        setTranslucentStatus(true);
        mGoodsId = getIntent().getStringExtra(Constants.ActivityExtra.GOODS_ID);
        initializeView();
    }

    @Override
    protected void initData() {

    }

    private void initializeView() {
        actionBarLeft = (LinearLayout) findViewById(R.id.actionBarLeft);
        actionBarTitle = (TextView) findViewById(R.id.actionBarTitle);
        goodsBarLine = findViewById(R.id.goodsBarLine);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingtoolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBar = (AppBarLayout) findViewById(R.id.appBar);
        goodsDetailRv = (RecyclerView) findViewById(R.id.goodsDetailRv);
        rootView = (CoordinatorLayout) findViewById(R.id.rootView);

        goodsDetailRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    public static void launch(Activity activity,String goodsId){
        Intent intent = new Intent(activity,GoodsDetailActivity.class);
        intent.putExtra(Constants.ActivityExtra.GOODS_ID,goodsId);
        activity.startActivity(intent);
    }
}
