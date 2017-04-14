package com.thinkeract.tka.ui.home.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.StringUtils;
import com.thinkeract.tka.data.api.entity.NewsItem;
import com.thinkeract.tka.data.api.response.NewsDetailData;
import com.thinkeract.tka.ui.home.NewsDetailActivity;
import com.thinkeract.tka.widget.SimpleWebView;
import com.zitech.framework.widget.RemoteImageView;

import java.util.List;

/**
 * Created by ymh on 2017/4/8 18:01
 * e-mail:minhengyan@gmail.com
 */

public class NewsDetailAdapter extends RecyclerView.Adapter {

    private List<NewsDetailData> mList;
    private Activity mContext;
    private static final int NEWS_ITEM_TYPE = 1;
    private static final int NEWS_HEAD_TYPE = 0;

    public NewsDetailAdapter(Activity activity) {
        mContext = activity;
    }

    public void setItemList(List<NewsDetailData> itemList) {
        mList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getNewsItem() != null)
            return NEWS_ITEM_TYPE;
        else
            return NEWS_HEAD_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NEWS_ITEM_TYPE)
            return new NewsItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false));
        else
            return new NewsHeadHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news_head, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsDetailData item = mList.get(position);
        int itemType = getItemViewType(position);
        if(itemType == NEWS_ITEM_TYPE) {
            NewsItemHolder newsItemHolder = (NewsItemHolder) holder;
            final NewsItem newsItem = item.getNewsItem();

            newsItemHolder.newsTopLayout.setVisibility(View.GONE);
            if (newsItem != null) {
                newsItemHolder.newsItemPicIv.setImageUri(Constants.ImageDefResId.DEF_LAND_PIC_NORMAL, newsItem.getCover());
                newsItemHolder.newsTitleTv.setText(newsItem.getTitle());
                newsItemHolder.newsSubTitleTv.setText(newsItem.getSubTitle());

                newsItemHolder.newsItemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewsDetailActivity.launch(mContext,newsItem.getId());
                    }
                });
            }
        }else if(itemType == NEWS_HEAD_TYPE){
            NewsHeadHolder newsHeadHolder = (NewsHeadHolder) holder;
            NewsDetailData.NewsHead newsHead = item.getNews();
            if(newsHead != null){
                newsHeadHolder.titleTv.setText(newsHead.getTitle());
                newsHeadHolder.contentWebView.setWebViewClient(new WebViewClient(){
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                   return true;
                    }});
                newsHeadHolder.contentWebView.loadDataWithBaseURL(
                        null, StringUtils.convertHtmlTxt(newsHead.getDescr()),
                        "text/html", "utf-8", null);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class NewsItemHolder extends RecyclerView.ViewHolder {
        private LinearLayout newsTopLayout;
        private RemoteImageView newsItemPicIv;
        private TextView newsTitleTv;
        private TextView newsSubTitleTv;
        private RelativeLayout newsItemLayout;

        public NewsItemHolder(View itemView) {
            super(itemView);

            newsTopLayout = (LinearLayout) itemView.findViewById(R.id.newsTopLayout);
            newsItemPicIv = (RemoteImageView) itemView.findViewById(R.id.newsItemPicIv);
            newsTitleTv = (TextView) itemView.findViewById(R.id.newsTitleTv);
            newsSubTitleTv = (TextView) itemView.findViewById(R.id.newsSubTitleTv);
            newsItemLayout = (RelativeLayout) itemView.findViewById(R.id.newsItemLayout);
        }
    }

    public static class NewsHeadHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private SimpleWebView contentWebView;

        public NewsHeadHolder(View itemView) {
            super(itemView);

            titleTv = (TextView) itemView.findViewById(R.id.titleTv);
            contentWebView = (SimpleWebView) itemView.findViewById(R.id.contentWebView);

        }
    }

}
