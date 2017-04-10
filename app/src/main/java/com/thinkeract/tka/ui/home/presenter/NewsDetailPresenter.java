package com.thinkeract.tka.ui.home.presenter;

import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.NewsItem;
import com.thinkeract.tka.data.api.request.IdRequest;
import com.thinkeract.tka.data.api.response.NewsDetailData;
import com.thinkeract.tka.ui.home.contract.NewsDetailContract;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymh on 2016/10/30 10:53
 * e-mail:minhengyan@gmail.com
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter {
    private NewsDetailContract.View mView;

    public NewsDetailPresenter(NewsDetailContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getNewsDetail(String newsId) {
        IdRequest body = new IdRequest();
        body.setId(newsId);

        ApiFactory.newsDetail(body).subscribe(new ProgressSubscriber<ApiResponse<NewsDetailData>>(mView.getContext()){
            @Override
            public void onNext(ApiResponse<NewsDetailData> value) {
                super.onNext(value);
                mView.showSuccess(convertToListData(value.getData()));
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError(e.getMessage());
            }
        });
    }

    private List<NewsDetailData> convertToListData(NewsDetailData data) {
        List<NewsDetailData> newsDetailDataList = new ArrayList<>();
        NewsDetailData newsDetailDataHead = new NewsDetailData();
        newsDetailDataHead.setNews(data.getNews());
        newsDetailDataList.add(newsDetailDataHead);
        List<NewsItem> newsItems = data.getCommendList();
        if(newsItems != null&&newsItems.size()>0){
            for(NewsItem item:newsItems){
                NewsDetailData newsDetailDataItem = new NewsDetailData();
                newsDetailDataItem.setNewsItem(item);
                newsDetailDataList.add(newsDetailDataItem);
            }
        }
        return newsDetailDataList;
    }
}
