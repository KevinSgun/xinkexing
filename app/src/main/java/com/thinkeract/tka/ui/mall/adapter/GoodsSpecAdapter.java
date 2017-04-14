package com.thinkeract.tka.ui.mall.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.Sku;
import com.thinkeract.tka.widget.TagCloudView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minHeng on 2017/4/7 13:51.
 * mail:minhengyan@gmail.com
 */

public class GoodsSpecAdapter extends RecyclerView.Adapter<GoodsSpecAdapter.GoodsSpecViewHolder> {

    private Activity mContext;
    private List<Sku> mList;

    public GoodsSpecAdapter(Activity context){
        mContext = context;
    }

    public void setItemList(List<Sku> itemList){
        mList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public GoodsSpecAdapter.GoodsSpecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GoodsSpecViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goods_spec,parent,false));
    }

    @Override
    public void onBindViewHolder(GoodsSpecAdapter.GoodsSpecViewHolder holder, int position) {
        Sku item = mList.get(position);
        holder.specTypeTv.setText(item.getName()+":");
        holder.specNameTag.setTags(getTagStrList(item.getItems()));
        holder.specNameTag.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String attrNameString, boolean isChoose) {

            }
        });
    }

    private List<String> getTagStrList(List<Sku.Spec> specItems) {
        List<String> stringList = new ArrayList<>();
        for(Sku.Spec specItem:specItems){
            stringList.add(specItem.getName());
        }
        return stringList;
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    public static class GoodsSpecViewHolder extends RecyclerView.ViewHolder{
        private TextView specTypeTv;
        private TagCloudView specNameTag;
        public GoodsSpecViewHolder(View itemView) {
            super(itemView);

            specTypeTv = (TextView) itemView.findViewById(R.id.specTypeTv);
            specNameTag = (TagCloudView) itemView.findViewById(R.id.specNameTag);
        }
    }
}
