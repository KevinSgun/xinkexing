package com.thinkeract.tka.ui.home.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.NewsItem;
import com.thinkeract.tka.data.api.response.HomePageData;
import com.thinkeract.tka.ui.home.AllergyListActivity;
import com.thinkeract.tka.ui.home.NewsDetailActivity;
import com.thinkeract.tka.ui.home.OrganListActivity;
import com.thinkeract.tka.ui.home.SurroundingsListActivity;
import com.thinkeract.tka.widget.DashboardView;
import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：YMH on 2017/11/1 0001 10:23
 * E-mail：minheng_yan@163.com
 */
public class HomeAdapter extends RecyclerView.Adapter{

    private Activity mContext;
    private HomePageData mHead;
    private List<NewsItem> mList;
    private String mOrganId;
    private String mAllergyId;
    private String mSourroundingsId;
    private int mSumScore;

    public HomeAdapter(Activity activity){
        mContext = activity;
        mList = new ArrayList<>();
    }

    public void setData(HomePageData response){
        mHead = response;
        mList.clear();
        mList.add(new NewsItem());
        mList.addAll(response.getNews());
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return 0;
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0)
            return new HeadHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_head,parent,false));
        return new NewsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NewsItem item = mList.get(position);
        if(holder instanceof NewsHolder) {
            NewsHolder newsHolder = (NewsHolder) holder;
            if (position == 1) {
                newsHolder.newsTopLayout.setVisibility(View.VISIBLE);
            } else {
                newsHolder.newsTopLayout.setVisibility(View.GONE);
            }
            if (item != null) {
                newsHolder.newsItemPicIv.setImageUri(Constants.ImageDefResId.DEF_LAND_PIC_NORMAL, item.getCover());
                newsHolder.newsTitleTv.setText(item.getTitle());
                newsHolder.newsSubTitleTv.setText(item.getSubTitle());

                newsHolder.newsItemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //进入新闻详情页面
                        NewsDetailActivity.launch(mContext, item.getId());
                    }
                });
            }
        }else if(holder instanceof HeadHolder){
            HeadHolder headHolder = (HeadHolder) holder;
            refreshUI(headHolder);
        }
    }

    private void refreshUI(HeadHolder holder) {
        mSumScore = mHead.getSumScore();
        holder.mHealthDescriptionTv.setText(statusDescription());
        holder.mDashboardView.setCreditValue(mSumScore);
        List<HomePageData.ScoreBean> scoreBeanList = mHead.getScore();
        for (int i = 0, size = scoreBeanList.size(); i < size; i++) {
            HomePageData.ScoreBean scoreBean = scoreBeanList.get(i);
            if (i == 0) {
                holder.leftTotalScore.setText("/" + String.valueOf(scoreBean.getMaxScore()));
                holder.leftCurrentScore.setText(String.valueOf(scoreBean.getScore()));
                holder.leftItemName.setText(scoreBean.getName());
                mOrganId = String.valueOf(scoreBean.getId());
            } else if (i == 1) {
                holder.middleTotalScore.setText("/" + String.valueOf(scoreBean.getMaxScore()));
                holder.middleCurrentScore.setText(String.valueOf(scoreBean.getScore()));
                holder.middleItemName.setText(scoreBean.getName());
                mAllergyId = String.valueOf(scoreBean.getId());
            } else {
                holder.rightTotalScore.setText("/" + String.valueOf(scoreBean.getMaxScore()));
                holder.rightCurrentScore.setText(String.valueOf(scoreBean.getScore()));
                holder.rightItemName.setText(scoreBean.getName());
                mSourroundingsId = String.valueOf(scoreBean.getId());
            }
        }

        holder.leftItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrganListActivity.launch(mContext, mOrganId);
            }
        });

        holder.middleItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllergyListActivity.launch(mContext, mAllergyId);
            }
        });

        holder.rightItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SurroundingsListActivity.launch(mContext, mSourroundingsId);
            }
        });
    }

    public String getOrganId(){
        return mOrganId;
    }

    public String getAllergyId(){
        return mAllergyId;
    }

    public String getSourroundingsId(){
        return mSourroundingsId;
    }

    public int getSumScore(){
        return mSumScore;
    }

    private String statusDescription() {
        if (mSumScore >= 90) {
            return "当前状态极佳，请继续保持!";
        } else if (mSumScore >= 80) {
            return "当前状态良好，请继续保持!";
        } else if (mSumScore >= 70) {
            return "当前状态一般，请引起注意!";
        } else if (mSumScore >= 60) {
            return "当前状态欠佳，请引起注意";
        }
        return "当前状态较差，请找专业医生检查";
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

    public static class HeadHolder extends RecyclerView.ViewHolder{
        private TextView leftCurrentScore;
        private TextView leftTotalScore;
        private TextView middleCurrentScore;
        private TextView middleTotalScore;
        private TextView rightCurrentScore;
        private TextView rightTotalScore;
        private TextView leftItemName;
        private TextView middleItemName;
        private TextView rightItemName;
        private DashboardView mDashboardView;
        private TextView mHealthDescriptionTv;
        private ImageView mCoverIv;
        private RelativeLayout leftItemLayout;
        private RelativeLayout middleItemLayout;
        private RelativeLayout rightItemLayout;

        public HeadHolder(View itemView) {
            super(itemView);

            mCoverIv = (ImageView) itemView.findViewById(R.id.coverIv);
            leftCurrentScore = (TextView) itemView.findViewById(R.id.leftCurrentScore);
            leftTotalScore = (TextView) itemView.findViewById(R.id.leftTotalScore);
            leftItemLayout = (RelativeLayout) itemView.findViewById(R.id.leftItemLayout);
            middleCurrentScore = (TextView) itemView.findViewById(R.id.middleCurrentScore);
            middleTotalScore = (TextView) itemView.findViewById(R.id.middleTotalScore);
            middleItemLayout = (RelativeLayout) itemView.findViewById(R.id.middleItemLayout);
            rightCurrentScore = (TextView) itemView.findViewById(R.id.rightCurrentScore);
            rightTotalScore = (TextView) itemView.findViewById(R.id.rightTotalScore);
            rightItemLayout = (RelativeLayout) itemView.findViewById(R.id.rightItemLayout);
            leftItemName = (TextView) itemView.findViewById(R.id.leftItemName);
            middleItemName = (TextView) itemView.findViewById(R.id.middleItemName);
            rightItemName = (TextView) itemView.findViewById(R.id.rightItemName);
            mDashboardView = (DashboardView) itemView.findViewById(R.id.dashboardView);
            mHealthDescriptionTv = (TextView) itemView.findViewById(R.id.healthDescriptionTv);
        }
    }
}
