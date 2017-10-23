package com.thinkeract.tka.ui.mall;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.DBUtils;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.AddressItem;
import com.thinkeract.tka.data.api.entity.GoodsComment;
import com.thinkeract.tka.data.api.entity.GoodsDetailItem;
import com.thinkeract.tka.data.api.response.GoodsDetailData;
import com.thinkeract.tka.data.db.greendao.GDAddress;
import com.thinkeract.tka.ui.BaseActivity;
import com.thinkeract.tka.ui.mall.adapter.GoodsDetailAdapter;
import com.thinkeract.tka.ui.mall.contract.GoodsDetailDataContract;
import com.thinkeract.tka.ui.mall.datasource.GoodsCommentDataSource;
import com.thinkeract.tka.ui.mall.presenter.GoodsDetailDataPresenter;
import com.thinkeract.tka.widget.ChooseGoodsSpecDialog;
import com.thinkeract.tka.widget.GlideImageLoader;
import com.thinkeract.tka.widget.MVCSwipeRefreshHelper;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.transformer.DepthPageTransformer;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import static com.thinkeract.tka.R.id.buyNowLayout;

/**
 * Created by minHeng on 2017/4/13 18:19.
 * mail:minhengyan@gmail.com
 */

public class GoodsDetailActivity extends BaseActivity implements GoodsDetailDataContract.View {
    private LinearLayout actionBarLeft;
    private TextView actionBarTitle;
    private Toolbar toolbar;
    private AppBarLayout appBar;
    private RecyclerView goodsDetailRv;
    private String mGoodsId;
    private Banner banner;
    private GoodsDetailDataPresenter goodsDetailPresenter;
    private CollapsingToolbarLayoutState state;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GoodsDetailAdapter goodsDetailAdapter;
    private MVCSwipeRefreshHelper<List<GoodsComment>> mvcHelper;
    private GoodsDetailData mGoodsDetailData;
    private ChooseGoodsSpecDialog mSpecDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView() {
        setTranslucentStatus(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);

        tintManager.setStatusBarTintResource(R.drawable.bg_black_shadow_light);
        mGoodsId = getIntent().getStringExtra(Constants.ActivityExtra.GOODS_ID);
        initializeView();
//        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            toolbar.setPadding(0, ViewUtils.getStatuBarHeight(getContext()), 0, 0);
        }
        actionBarTitle.setText("");
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        actionBarTitle.setText("");//设置title为EXPANDED
                        goodsDetailRv.smoothScrollToPosition(0);
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                        actionBarTitle.setText("商品详情");//设置title显示
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                        actionBarTitle.setText("");//设置title为INTERNEDIATE
                    }
                }
            }
        });

        actionBarLeft.setOnClickListener(this);
    }

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    @Override
    protected void initData() {
        goodsDetailPresenter = new GoodsDetailDataPresenter(this);
        goodsDetailPresenter.getGoodsDetail(mGoodsId);

        goodsDetailAdapter = new GoodsDetailAdapter(this);
        goodsDetailAdapter.setOnItemClickListener(new GoodsDetailAdapter.OnItemClickListener() {
            @Override
            public void chooseSpec(GoodsDetailData item) {
                showChooseSpecDialog(0);
            }

            @Override
            public void lookServiceDescription(GoodsDetailData item) {

            }
        });
        goodsDetailRv.setItemAnimator(new DefaultItemAnimator());
        goodsDetailRv.setAdapter(goodsDetailAdapter);

        GDAddress gdAddress = DBUtils.queryDefAddress();
        if (gdAddress == null) {
            requestAddressFromService();
        }
    }

    private void requestAddressFromService() {
        ApiFactory.getUserAddressList().subscribe(new ProgressSubscriber<ApiResponse<List<AddressItem>>>(getContext(), false) {
            @Override
            public void onNext(ApiResponse<List<AddressItem>> value) {
                super.onNext(value);
                DBUtils.insertAddressList(DBUtils.convertToGDAddressItemList(value.getData()));
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    private void requestCommentData() {
        if (mvcHelper == null) {
            mvcHelper = new MVCSwipeRefreshHelper<List<GoodsComment>>(swipeRefreshLayout);
            // 设置数据源
            mvcHelper.setDataSource(new GoodsCommentDataSource(mGoodsId));
            // 设置适配器
            mvcHelper.setAdapter(goodsDetailAdapter);
            // 加载数据
            mvcHelper.refresh();
        } else {
            mvcHelper.refresh();
        }
    }

    private void initializeView() {
        actionBarLeft = (LinearLayout) findViewById(R.id.actionBarLeft);
        actionBarTitle = (TextView) findViewById(R.id.actionBarTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBar = (AppBarLayout) findViewById(R.id.appBar);
        goodsDetailRv = (RecyclerView) findViewById(R.id.goodsDetailRv);
        banner = (Banner) findViewById(R.id.banner);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setEnabled(false);

        FrameLayout buyNowLayout = (FrameLayout) findViewById(R.id.buyNowLayout);
        FrameLayout addToCartLayout = (FrameLayout) findViewById(R.id.addToCartLayout);
        LinearLayout goToShoppingCartLayout = (LinearLayout) findViewById(R.id.goToShoppingCartLayout);

        goodsDetailRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        buyNowLayout.setOnClickListener(this);
        addToCartLayout.setOnClickListener(this);
        goToShoppingCartLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.actionBarLeft:
                back();
                break;
            case R.id.goToShoppingCartLayout:
                showActivity(ShoppingCartActivity.class);
                break;
            case R.id.addToCartLayout:
                showChooseSpecDialog(0);
                break;
            case buyNowLayout:
                showChooseSpecDialog(1);
                break;
        }
    }

    private void showChooseSpecDialog(int buyType) {
        if(mSpecDialog == null) {
            mSpecDialog = new ChooseGoodsSpecDialog(this,buyType);
        }

        mSpecDialog.setData(mGoodsDetailData);
        mSpecDialog.setType(buyType);
        mSpecDialog.show();
    }

    public static void launch(Activity activity, String goodsId){
        Intent intent = new Intent(activity,GoodsDetailActivity.class);
        intent.putExtra(Constants.ActivityExtra.GOODS_ID,goodsId);
        activity.startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN,activity);
    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void showSuccess(GoodsDetailData goodsDetailData) {
        mGoodsDetailData = goodsDetailData;
        List<String> stringList =  goodsDetailData.getGoods().getPhotos();
        if(stringList != null&&stringList.size()>0){
            banner.setImages(stringList)
                    .setImageLoader(new GlideImageLoader())
                    .setBannerStyle(BannerConfig.NUM_INDICATOR)
                    .setBannerAnimation(DepthPageTransformer.class)
                    .start();
        }
        GoodsDetailItem goodsDetailItem = new GoodsDetailItem();
        goodsDetailItem.setGoodsDetailData(goodsDetailData);
        List<GoodsDetailItem> goodsDetailItems = new ArrayList<>();
        goodsDetailItems.add(goodsDetailItem);
        goodsDetailAdapter.setItemList(goodsDetailItems);
        requestCommentData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.stopAutoPlay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.startAutoPlay();
    }
}
