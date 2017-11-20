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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.GoodsList;
import com.thinkeract.tka.ui.home.adapter.GoodsGridAdapter;

import me.drakeet.multitype.ItemViewBinder;

/**
 * 非介入性治疗
 * @author ymh
 */
public class HorizontalGoodsViewBinder
    extends ItemViewBinder<GoodsList, HorizontalGoodsViewBinder.ViewHolder> {

    private Activity mContext;

    public HorizontalGoodsViewBinder(Activity activity){
        mContext = activity;
    }

    @NonNull @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_horizontal_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GoodsList postList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(layoutManager);
        GoodsGridAdapter adapter = new GoodsGridAdapter(mContext);
        adapter.setGoodsItems(postList.getGoodsItems());
        holder.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        assertGetAdapterNonNull();
    }

    private void assertGetAdapterNonNull() {
        // noinspection ConstantConditions
        if (getAdapter() == null) {
            throw new NullPointerException("getAdapter() == null");
        }
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.post_list);
        }
    }
}
