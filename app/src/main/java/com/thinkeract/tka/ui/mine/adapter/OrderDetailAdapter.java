package com.thinkeract.tka.ui.mine.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.Utils;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.entity.OrderDetailData;
import com.thinkeract.tka.data.api.entity.OrderItem;
import com.thinkeract.tka.ui.ListAdapter;
import com.zitech.framework.widget.RemoteImageView;

import java.util.List;

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
                return new AddressAndStatusHolder(LayoutInflater.from(mContext).inflate(R.layout.item_order_address_and_status,parent,false));
            case GOODS_ITEM:
                return new GoodsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_order_goods,parent,false));
            default:
                return new StatisticsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_order_statistics,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderDetailData item = mList.get(position);
        int itemType = getItemViewType(position);
        switch (itemType){
            case ADDRESS_AND_STATUS:
                AddressAndStatusHolder andStatusHolder = (AddressAndStatusHolder) holder;
                andStatusHolder.orderStatusTopTv.setBackgroundColor(ViewUtils.getOrderStatusColorRes(item.getStatus()));
                andStatusHolder.shippingStatusTv.setTextColor(ViewUtils.getOrderStatusColorRes(item.getStatus()));

                andStatusHolder.orderStatusTopTv.setText(ViewUtils.getOrderStatusLongString(item.getStatus()));
                andStatusHolder.shippingStatusTv.setText(ViewUtils.getOrderStatusString(item.getStatus()));

                andStatusHolder.orderNameTv.setText(item.getName());

//                andStatusHolder.addressTv.setText(item.get);
//                andStatusHolder.contactNameTv.setText(item);
//                andStatusHolder.phoneNumTv.setText(item.get);
                andStatusHolder.orderNumTv.setText(String.format(mContext.getResources().getString(R.string.order_num),item.getPo()));
                andStatusHolder.dateTv.setText(item.getDate());
                break;
            case GOODS_ITEM:
                GoodsHolder goodsHolder = (GoodsHolder) holder;
                OrderDetailData.OrderDetailGoods detailGoods = item.getDetailGoods();
                goodsHolder.goodsPicIv.setImageUri(Constants.ImageDefResId.DEF_SQUARE_PIC_NORMAL,detailGoods.getCover());
                goodsHolder.goodsTitleTv.setText(detailGoods.getName());
                goodsHolder.goodsPriceTv.setText(String.format(mContext.getResources().getString(R.string.rmb),detailGoods.getPrice()));
                goodsHolder.goodsCountTv.setText(String.format(mContext.getResources().getString(R.string.x_count),detailGoods.getQuantity()));
                if(item.getStatus() == OrderItem.IS_FINISH){
                    goodsHolder.postCommentBtn.setVisibility(View.VISIBLE);
                    goodsHolder.postCommentBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO 发表评论
                        }
                    });
                }else {
                    goodsHolder.postCommentBtn.setVisibility(View.GONE);
                }

                break;
            case ORDER_STATISTICS:
                StatisticsHolder statisticsHolder = (StatisticsHolder) holder;
                statisticsHolder.goodsAmountTv.setText(String.format(mContext.getResources().getString(R.string.rmb),getTotalAmount(item.getGoods())));
                statisticsHolder.freightTv.setText(item.getFare()>0?String.format(mContext.getResources().getString(R.string.rmb),item.getFare()):"免运费");
                statisticsHolder.actuallyAmountTv.setText(String.format(mContext.getResources().getString(R.string.rmb),getActualAmount(getTotalAmount(item.getGoods()),item.getFare())));
                statisticsHolder.deleteOrderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 删除订单
                    }
                });

                if(item.getStatus() == OrderItem.IS_FINISH ||item.getStatus() == OrderItem.IS_SEND ){
                    statisticsHolder.viewLogisticsBtn.setVisibility(View.VISIBLE);
                    statisticsHolder.viewLogisticsBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO 查看物流
                        }
                    });
                }else {
                    statisticsHolder.viewLogisticsBtn.setVisibility(View.GONE);
                }
                break;
        }

    }

    private double getTotalAmount(List<OrderDetailData.OrderDetailGoods> goods) {
        double totalAmount = 0.0;
        for(OrderDetailData.OrderDetailGoods goodItem:goods){
            totalAmount = Utils.doubleAddDouble(totalAmount,Utils.doubleMultiplyDouble(goodItem.getPrice(), (double) goodItem.getQuantity()));
        }
        return totalAmount;
    }

    private double getActualAmount(double goodsAmount,double freight) {
        return Utils.doubleAddDouble(goodsAmount,freight);
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
