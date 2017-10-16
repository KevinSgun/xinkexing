/*
 * Copyright 2016 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thinkeract.tka.ui.home.viewbinder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.NewsItem;
import com.thinkeract.tka.ui.home.NewsDetailActivity;
import com.zitech.framework.widget.RemoteImageView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author ymh
 */
public class NewsViewBinder extends ItemViewBinder<NewsItem, NewsViewBinder.ViewHolder> {

    private Activity mContext;

    public NewsViewBinder(Activity activity){
        mContext = activity;
    }

    @NonNull @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder,final @NonNull NewsItem item) {
        holder.newsItemPicIv.setImageUri(Constants.ImageDefResId.DEF_LAND_PIC_NORMAL,item.getCover());
        holder.newsTitleTv.setText(item.getTitle());
        holder.newsSubTitleTv.setText(item.getSubTitle());

        holder.newsItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 进入新闻详情页面
                NewsDetailActivity.launch(mContext,item.getId());
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout newsTopLayout;
        private RemoteImageView newsItemPicIv;
        private TextView newsTitleTv;
        private TextView newsSubTitleTv;
        private RelativeLayout newsItemLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTopLayout = (LinearLayout) itemView.findViewById(R.id.newsTopLayout);
            newsItemPicIv = (RemoteImageView) itemView.findViewById(R.id.newsItemPicIv);
            newsTitleTv = (TextView) itemView.findViewById(R.id.newsTitleTv);
            newsSubTitleTv = (TextView) itemView.findViewById(R.id.newsSubTitleTv);
            newsItemLayout = (RelativeLayout) itemView.findViewById(R.id.newsItemLayout);
        }
    }
}
