package com.thinkeract.tka.ui.home.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.NewsItem;
import com.thinkeract.tka.ui.home.NewsDetailActivity;
import com.zitech.framework.widget.RemoteImageView;

import java.util.List;

/**
 * Created by ymh on 2017/4/8 18:01
 * e-mail:minhengyan@gmail.com
 */

public class NewsListAdapter extends RecyclerView.Adapter  implements IDataAdapter<List<NewsItem>> {

    private List<NewsItem> mList;
    private Activity mContext;

    public NewsListAdapter(Activity activity){
        mContext = activity;
//        mList = new ArrayList<>();
    }

    public void setItemList(List<NewsItem> itemList){
        mList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NewsItem item = mList.get(position);
        NewsHolder newsHolder = (NewsHolder) holder;
        if(position == 0){
            newsHolder.newsTopLayout.setVisibility(View.VISIBLE);
        }else{
            newsHolder.newsTopLayout.setVisibility(View.GONE);
        }
        if(item != null){
            newsHolder.newsItemPicIv.setImageUri(Constants.ImageDefResId.DEF_LAND_PIC_NORMAL,item.getCover());
            newsHolder.newsTitleTv.setText(item.getTitle());
            newsHolder.newsSubTitleTv.setText(item.getSubTitle());

            newsHolder.newsItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 进入新闻详情页面
                    NewsDetailActivity.launch(mContext,item.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    public static class NewsHolder extends RecyclerView.ViewHolder{
        private LinearLayout newsTopLayout;
        private RemoteImageView newsItemPicIv;
        private TextView newsTitleTv;
        private TextView newsSubTitleTv;
        private RelativeLayout newsItemLayout;
        public NewsHolder(View itemView) {
            super(itemView);

            newsTopLayout = (LinearLayout) itemView.findViewById(R.id.newsTopLayout);
            newsItemPicIv = (RemoteImageView) itemView.findViewById(R.id.newsItemPicIv);
            newsTitleTv = (TextView) itemView.findViewById(R.id.newsTitleTv);
            newsSubTitleTv = (TextView) itemView.findViewById(R.id.newsSubTitleTv);
            newsItemLayout = (RelativeLayout) itemView.findViewById(R.id.newsItemLayout);
        }
    }

    @Override
    public void notifyDataChanged(List<NewsItem> items, boolean isRefresh) {
        boolean empty = this.mList.isEmpty();
        int sizeBeforeChange = this.mList.size();
        if (isRefresh) {
            this.mList.clear();
        }
        int size = this.mList.size();
        this.mList.addAll(items);
        if (isRefresh || empty) {
            if (items.size() >= sizeBeforeChange) {
                notifyItemRangeChanged(0, items.size());
            } else {
                notifyDataSetChanged();
            }
        } else {
            notifyItemRangeInserted(size, items.size());
        }
    }

    @Override
    public List<NewsItem> getData() {
        return mList;
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }

}
