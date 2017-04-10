package com.thinkeract.tka.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.response.NewsDetailData;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.home.adapter.NewsDetailAdapter;
import com.thinkeract.tka.ui.home.contract.NewsDetailContract;
import com.thinkeract.tka.ui.home.presenter.NewsDetailPresenter;

import java.util.List;

/**
 * Created by ymh on 2017/4/9 10:47
 * e-mail:minhengyan@gmail.com
 */

public class NewsDetailActivity extends AppBarActivity implements NewsDetailContract.View {
    private RecyclerView newsDetailRv;
    private NewsDetailPresenter mPresenter;
    private String mNewsId;
    private NewsDetailAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initView() {
        mNewsId = getIntent().getStringExtra(Constants.ActivityExtra.NEWS_ID);

        setTitle(R.string.topic_detail);
        newsDetailRv = (RecyclerView) findViewById(R.id.newsDetailRv);

        mAdapter = new NewsDetailAdapter(this);

        newsDetailRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        newsDetailRv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mPresenter = new NewsDetailPresenter(this);

        mPresenter.getNewsDetail(mNewsId);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSuccess(List<NewsDetailData> response) {
        mAdapter.setItemList(response);
    }

    public static void launch(Activity activity,String newsId){
        Intent intent = new Intent(activity,NewsDetailActivity.class);
        intent.putExtra(Constants.ActivityExtra.NEWS_ID,newsId);
        activity.startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN,activity);
    }
}
