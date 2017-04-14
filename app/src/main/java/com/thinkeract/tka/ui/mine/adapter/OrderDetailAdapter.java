package com.thinkeract.tka.ui.mine.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.OrderDetailData;
import com.thinkeract.tka.ui.ListAdapter;
import com.zitech.framework.widget.RemoteImageView;

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
        private TextView orderStatusTopTv;
        private TextView addressTv;
        private TextView contactNameTv;
        private TextView phoneNumTv;
        private TextView orderNumTv;
        private TextView dateTv;
        private TextView orderNameTv;
        private TextView shippingStatusTv;
        public AddressAndStatusHolder(View itemView) {
            super(itemView);
            orderStatusTopTv = (TextView) itemView.findViewById(R.id.orderStatusTopTv);
            addressTv = (TextView) itemView.findViewById(R.id.addressTv);
            contactNameTv = (TextView) itemView.findViewById(R.id.contactNameTv);
            phoneNumTv = (TextView) itemView.findViewById(R.id.phoneNumTv);
            orderNumTv = (TextView) itemView.findViewById(R.id.orderNumTv);
            dateTv = (TextView) itemView.findViewById(R.id.dateTv);
            orderNameTv = (TextView) itemView.findViewById(R.id.orderNameTv);
            shippingStatusTv = (TextView) itemView.findViewById(R.id.shippingStatusTv);
        }
    }

    public static class GoodsHolder extends RecyclerView.ViewHolder{
        private TextView goodsCountTv;
        private RemoteImageView goodsPicIv;
        private TextView goodsTitleTv;
        private TextView goodsPriceTv;
        private Button postCommentBtn;

        public GoodsHolder(View itemView) {
            super(itemView);
            goodsPicIv = (RemoteImageView) itemView.findViewById(R.id.goodsPicIv);
            goodsTitleTv = (TextView) itemView.findViewById(R.id.goodsTitleTv);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.goodsPriceTv);
            goodsCountTv = (TextView) itemView.findViewById(R.id.goodsCountTv);
            postCommentBtn = (Button) itemView.findViewById(R.id.postCommentBtn);
        }
    }

    public static class StatisticsHolder extends RecyclerView.ViewHolder{
        private Button viewLogisticsBtn;
        private TextView goodsAmountTv;
        private TextView freightTv;
        private TextView actuallyAmountTv;
        private Button deleteOrderBtn;
        public StatisticsHolder(View itemView) {
            super(itemView);
            viewLogisticsBtn = (Button) itemView.findViewById(R.id.viewLogisticsBtn);
            goodsAmountTv = (TextView) itemView.findViewById(R.id.goodsAmountTv);
            freightTv = (TextView) itemView.findViewById(R.id.freightTv);
            actuallyAmountTv = (TextView) itemView.findViewById(R.id.actuallyAmountTv);
            deleteOrderBtn = (Button) itemView.findViewById(R.id.deleteOrderBtn);
        }
    }
}
