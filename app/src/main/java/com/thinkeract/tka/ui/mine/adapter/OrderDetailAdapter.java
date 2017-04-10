package com.thinkeract.tka.ui.mine.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.OrderDetailData;
import com.thinkeract.tka.ui.ListAdapter;

/**
 * Created by ymh on 2017/4/9 22:42
 * e-mail:minhengyan@gmail.com
 */

public class OrderDetailAdapter extends ListAdapter<OrderDetailData>{
    public static final int ADDRESS_AND_STATUS = 0;
    public static final int GOODS_ITEM = 1;
    public static final int ORDER_STATISTICS = 2;

    private Activity mContext;

    public OrderDetailAdapter(Activity context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ADDRESS_AND_STATUS:
                return new AddressAndStatusHolder(LayoutInflater.from(mContext).inflate(R.layout.item_address_and_status,parent,false));
            case GOODS_ITEM:
                return new GoodsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_order_goods,parent,false));
            default:
                return new StatisticsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_order_statistics,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderDetailData item = mList.get(position);
    }

    public static class AddressAndStatusHolder extends RecyclerView.ViewHolder{

        public AddressAndStatusHolder(View itemView) {
            super(itemView);
        }
    }

    public static class GoodsHolder extends RecyclerView.ViewHolder{

        public GoodsHolder(View itemView) {
            super(itemView);
        }
    }

    public static class StatisticsHolder extends RecyclerView.ViewHolder{

        public StatisticsHolder(View itemView) {
            super(itemView);
        }
    }
}
