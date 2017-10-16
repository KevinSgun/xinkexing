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

package com.thinkeract.tka.ui.home.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.GoodsItem;
import com.thinkeract.tka.ui.mall.GoodsDetailActivity;
import com.zitech.framework.widget.RemoteImageView;

import java.util.Collections;
import java.util.List;

/**
 * @author ymh
 */
public class GoodsGridAdapter extends RecyclerView.Adapter<GoodsGridAdapter.ViewHolder> {

    private List<GoodsItem> goodsItems = Collections.emptyList();
    private Activity mContext;

    public GoodsGridAdapter(Activity activity){
        mContext = activity;
    }

    public void setGoodsItems(@NonNull List<GoodsItem> goodsItems) {
        this.goodsItems = goodsItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goods_grid, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GoodsItem item = goodsItems.get(position);
        holder.mGoodsPicIv.setImageUri(Constants.ImageDefResId.DEF_SQUARE_PIC_NORMAL,item.getCover());
        holder.mGoodsNameTv.setText(item.getName());
        holder.mPriceTv.setText(String.format(mContext.getResources().getString(R.string.rmb),item.getMinprice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入商品详情页
                GoodsDetailActivity.launch(mContext,String.valueOf(item.getId()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return goodsItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RemoteImageView mGoodsPicIv;
        private TextView mGoodsNameTv;
        private TextView mPriceTv;

        ViewHolder(View itemView) {
            super(itemView);
            mGoodsPicIv = (RemoteImageView) itemView.findViewById(R.id.goodsPicIv);
            mGoodsNameTv = (TextView) itemView.findViewById(R.id.goodsNameTv);
            mPriceTv = (TextView) itemView.findViewById(R.id.priceTv);
        }
    }
}
