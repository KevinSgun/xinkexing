package com.thinkeract.tka.ui.mall.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.GoodsItem;
import com.thinkeract.tka.ui.ListAdapter;
import com.thinkeract.tka.ui.mall.GoodsDetailActivity;
import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minHeng on 2017/4/13 16:38.
 * mail:minhengyan@gmail.com
 */

public class GoodsListAdapter extends ListAdapter<GoodsItem> implements IDataAdapter<List<GoodsItem>> {

    private AddToCartListener addToCartListener;

    public GoodsListAdapter(Activity context) {
        super(context);
        mList = new ArrayList<>();
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GoodsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goods,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodsHolder goodsHolder = (GoodsHolder) holder;
        final GoodsItem item = mList.get(position);
        goodsHolder.goodsPicIv.setImageUri(Constants.ImageDefResId.DEF_SQUARE_PIC_NORMAL,item.getCover());
        goodsHolder.goodsTitleTv.setText(item.getName());
        goodsHolder.goodsPriceTv.setText(String.format(mContext.getResources().getString(R.string.rmb),item.getMinprice()));
        goodsHolder.commentCountTv.setText(String.valueOf(item.getComments()));
        goodsHolder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加入购物车
                if(addToCartListener != null){
                    addToCartListener.addToCart(item);
                }
            }
        });

        goodsHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入商品详情页
                GoodsDetailActivity.launch(mContext,String.valueOf(item.getId()));
            }
        });
    }

    public static class GoodsHolder extends RecyclerView.ViewHolder{
        private RemoteImageView goodsPicIv;
        private TextView goodsTitleTv;
        private TextView goodsPriceTv;
        private TextView commentCountTv;
        private ImageView addToCartBtn;

        public GoodsHolder(View itemView) {
            super(itemView);

            goodsPicIv = (RemoteImageView) itemView.findViewById(R.id.goodsPicIv);
            goodsTitleTv = (TextView) itemView.findViewById(R.id.goodsTitleTv);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.goodsPriceTv);
            commentCountTv = (TextView) itemView.findViewById(R.id.commentCountTv);
            addToCartBtn = (ImageView) itemView.findViewById(R.id.addToCartBtn);
        }
    }

    @Override
    public void notifyDataChanged(List<GoodsItem> items, boolean isRefresh) {
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
    public List<GoodsItem> getData() {
        return mList;
    }

    public void setAddToCartListener(AddToCartListener addToCartListener){
        this.addToCartListener = addToCartListener;
    }

    public interface AddToCartListener{
        void addToCart(GoodsItem item);
    }

}
