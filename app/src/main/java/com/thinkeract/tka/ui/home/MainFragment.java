package com.thinkeract.tka.ui.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.NewsItem;
import com.thinkeract.tka.ui.BaseFragment;
import com.thinkeract.tka.ui.home.adapter.NewsListAdapter;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.List;

/**
 * Created by minHeng on 2017/3/14 16:53.
 * mail:minhengyan@gmail.com
 */

public class MainFragment extends BaseFragment {
//    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView healthKnowledgeRv;
    private NewsListAdapter mAdapter;
//    private MVCSwipeRefreshHelper<List<NewsItem>> mvcHelper;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
//        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipeRefreshLayout);
        healthKnowledgeRv = (RecyclerView) contentView.findViewById(R.id.healthKnowledgeRv);

//        swipeRefreshLayout.setColorSchemeResources(R.color.gplus_color_1, R.color.gplus_color_2, R.color.gplus_color_3, R.color.gplus_color_4);

    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();

        mAdapter = new NewsListAdapter(getActivity());
        healthKnowledgeRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        healthKnowledgeRv.setAdapter(mAdapter);

        ApiFactory.newsRecommendList().subscribe(new ProgressSubscriber<ApiResponse<List<NewsItem>>>(getActivity()){
            @Override
            public void onNext(ApiResponse<List<NewsItem>> value) {
                super.onNext(value);
                mAdapter.setItemList(value.getData());
            }
        });

//        mvcHelper = new MVCSwipeRefreshHelper<List<NewsItem>>(swipeRefreshLayout);
//        // 设置数据源
//        mvcHelper.setDataSource(new NewsDataSource());
//        // 设置适配器
//        mvcHelper.setAdapter(mAdapter);
//        // 加载数据
//        mvcHelper.refresh();
    }


}
