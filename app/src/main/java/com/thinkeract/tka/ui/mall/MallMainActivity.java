package com.thinkeract.tka.ui.mall;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.shizhefei.mvc.OnRefreshStateChangeListener;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.DBUtils;
import com.thinkeract.tka.data.api.entity.GoodsItem;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.mall.adapter.GoodsListAdapter;
import com.thinkeract.tka.ui.mall.contract.FilterDataContract;
import com.thinkeract.tka.ui.mall.datasource.AllGoodsDataSource;
import com.thinkeract.tka.ui.mall.presenter.FilterDataPresenter;
import com.thinkeract.tka.widget.MVCSwipeRefreshHelper;
import com.thinkeract.tka.widget.filterview.FilterData;
import com.thinkeract.tka.widget.filterview.FilterEntity;
import com.thinkeract.tka.widget.filterview.FilterTwoEntity;
import com.thinkeract.tka.widget.filterview.FilterView;

import java.util.List;

/**
 * Created by minHeng on 2017/4/13 11:22.
 * mail:minhengyan@gmail.com
 */

public class MallMainActivity extends AppBarActivity implements FilterDataContract.View {
    private RecyclerView mallMainRv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FilterView filterView;
    private FilterDataPresenter filterDataPresenter;
    private FilterData filterData;
    private MVCSwipeRefreshHelper<List<GoodsItem>> mvcHelper;
    private AllGoodsDataSource allGoodsDataSource;
    private FrameLayout addCartLayout;
    private TextView cartGoodsCountTv;
    private int goodsCount;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mall_main;
    }

    @Override
    protected void initView() {
        setTitle(R.string.mall);
        initializationView();
        addCartLayout.setOnClickListener(this);
    }

    private void initializationView() {
        filterView = (FilterView) findViewById(R.id.filterView);
        mallMainRv = (RecyclerView) findViewById(R.id.mallMainRv);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        addCartLayout = (FrameLayout) findViewById(R.id.goToCartLayout);
        cartGoodsCountTv = (TextView) findViewById(R.id.cartGoodsCountTv);

        mallMainRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    protected void initData() {
        filterDataPresenter = new FilterDataPresenter(this);
        filterView.setClickEnabled(false);
        filterDataPresenter.getGoodsClassify();

        GoodsListAdapter goodsListAdapter = new GoodsListAdapter(this);
        goodsListAdapter.setAddToCartListener(new GoodsListAdapter.AddToCartListener() {
            @Override
            public void addToCart(GoodsItem item) {
                showCartGoodsCount();
            }
        });
        mvcHelper = new MVCSwipeRefreshHelper<List<GoodsItem>>(swipeRefreshLayout);
        allGoodsDataSource = new AllGoodsDataSource();
        // 设置数据源
        mvcHelper.setDataSource(allGoodsDataSource);
        // 设置适配器
        mvcHelper.setAdapter(goodsListAdapter);
        // 加载数据
        mvcHelper.refresh();
        mvcHelper.setOnStateChangeListener(new OnRefreshStateChangeListener<List<GoodsItem>>() {
            @Override
            public void onStartRefresh(IDataAdapter<List<GoodsItem>> adapter) {

            }

            @Override
            public void onEndRefresh(IDataAdapter<List<GoodsItem>> adapter, List<GoodsItem> result) {
                if(filterView.isDataEmpty())
                    filterDataPresenter.getGoodsClassify();
            }
        });

        showCartGoodsCount();
    }

    /**
     * 是否需要显示购物车商品数目
     */
    private void showCartGoodsCount(){
        goodsCount = DBUtils.queryAllGoodsCount();
        if (goodsCount == 0){
            cartGoodsCountTv.setVisibility(View.GONE);
        }else {
            cartGoodsCountTv.setVisibility(View.VISIBLE);
            cartGoodsCountTv.setText(String.valueOf(goodsCount));
        }
    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.goToCartLayout){
            showActivity(ShoppingCartActivity.class);
        }
    }

    @Override
    public void onBackPressed() {
        if(filterView.isShowing()){
            filterView.resetAllStatus();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void showSuccess(List<FilterTwoEntity> filterTwoEntityList, List<FilterEntity> sortFilterList) {
        if (filterData == null)
            filterData = new FilterData();
        filterView.setClickEnabled(true);
        filterData.setCategory(filterTwoEntityList);
        filterData.setSorts(sortFilterList);
        filterView.setFilterData(MallMainActivity.this, filterData);
        filterView.setOnItemCategoryClickListener(new FilterView.OnItemCategoryClickListener() {
            @Override
            public void onItemCategoryClick(FilterTwoEntity leftEntity, FilterEntity rightEntity) {
                allGoodsDataSource.setClassifyFilter(rightEntity.getValue());
                mvcHelper.refresh();
            }
        });

        filterView.setOnItemSortClickListener(new FilterView.OnItemSortClickListener() {
            @Override
            public void onItemSortClick(FilterEntity entity) {
                allGoodsDataSource.setOrderFilter(entity.getValue());
                mvcHelper.refresh();
            }
        });
    }
}
