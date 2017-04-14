package com.thinkeract.tka.ui.mall.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.GoodsClassifyItem;
import com.thinkeract.tka.ui.mall.contract.FilterDataContract;
import com.thinkeract.tka.widget.filterview.FilterEntity;
import com.thinkeract.tka.widget.filterview.FilterTwoEntity;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minHeng on 2016/11/19 16:11.
 * mail:minhengyan@gmail.com
 */

public class FilterDataPresenter implements FilterDataContract.Presenter {
    private FilterDataContract.View mView;

    public FilterDataPresenter(FilterDataContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getGoodsClassify() {
        ApiFactory.goodsClassify().safeSubscribe(new ProgressSubscriber<ApiResponse<String>>(mView.getContext(),false){
            @Override
            public void onNext(ApiResponse<String> value) {
                super.onNext(value);
                Gson gson = new Gson();
                List<GoodsClassifyItem> goodsClassifyItemList =gson.fromJson(value.getData()
                        , new TypeToken<List<GoodsClassifyItem>>() {}.getType());
                mView.showSuccess(getCategoryData(goodsClassifyItemList),getSortData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError(e.getMessage());
            }
        });
    }

    // 分类数据
    public static List<FilterTwoEntity> getCategoryData(List<GoodsClassifyItem> classifyItemList) {
        List<FilterTwoEntity> list = new ArrayList<>();
        List<FilterEntity> secondList = new ArrayList<>();
        FilterEntity filterEntity =new FilterEntity("所有类别", null);
        filterEntity.setSelected(true);
        secondList.add(filterEntity);
        FilterTwoEntity filterTwoEntity = new FilterTwoEntity("商品分类",secondList);
        filterTwoEntity.setSelected(true);
        list.add(filterTwoEntity);
        if(classifyItemList != null&&classifyItemList.size()>0){
            for(GoodsClassifyItem classifyItem:classifyItemList){
                list.add(new FilterTwoEntity(classifyItem.getName(),getFilterData(classifyItem.getItems())));
            }
        }
        return list;
    }

    // 筛选数据
    public static List<FilterEntity> getFilterData(List<GoodsClassifyItem.ItemsBean> itemsBeanList) {
        List<FilterEntity> list = new ArrayList<>();
        if(itemsBeanList != null&&itemsBeanList.size()>0) {
            for (GoodsClassifyItem.ItemsBean itemsBean : itemsBeanList) {
                list.add(new FilterEntity(itemsBean.getName(), String.valueOf(itemsBean.getId())));
            }
        }
        return list;
    }

    // 排序数据
    public static List<FilterEntity> getSortData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("综合排序", "1"));
        list.add(new FilterEntity("按销量", "2"));
        list.add(new FilterEntity("价格从高到低", "3"));
        list.add(new FilterEntity("价格从低到高", "4"));
        list.add(new FilterEntity("最新上架", "5"));
        return list;
    }
}
