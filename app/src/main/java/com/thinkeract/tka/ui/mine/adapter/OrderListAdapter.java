package com.thinkeract.tka.ui.mine.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.Utils;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.entity.OrderItem;
import com.thinkeract.tka.ui.mine.OrderDetailActivity;
import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minHeng on 2016/12/1 19:50.
 * mail:minhengyan@gmail.com
 */

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IDataAdapter<List<OrderItem>> {

    private Activity mContext;
    private List<OrderItem> mList;

    public OrderListAdapter(Activity context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public void setItemList(List<OrderItem> itemList) {
        mList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderHolder(LayoutInflater.from(mContext).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final OrderItem item = mList.get(position);
        OrderHolder viewHolder = (OrderHolder) holder;
        if(item.getGoods() != null&&item.getGoods().size()>0) {
            viewHolder.totalGoodsCountTv.setText(String.format(mContext.getResources().getString(R.string.total_goods_count), getTotalGoodsCount(item.getGoods())));
            viewHolder.orderStatusTv.setTextColor(mContext.getResources().getColor(ViewUtils.getOrderStatusColorRes(item.getStatus())));
            viewHolder.orderStatusTv.setText(ViewUtils.getOrderStatusString(item.getStatus()));

            viewHolder.goodsPicIv.setImageUri(Constants.ImageDefResId.DEF_SQUARE_PIC_NORMAL, item.getGoods().get(0).getCover());
            viewHolder.goodsPriceTv.setText(String.format(mContext.getResources().getString(R.string.rmb), item.getGoods().get(0).getPrice()));
            viewHolder.actuallyPayTv.setText(String.format(mContext.getResources().getString(R.string.rmb), getTotalAmount(item.getGoods())));
            viewHolder.goodsTitleTv.setText(item.getName());

            if (item.getStatus() == OrderItem.IS_SEND||item.getStatus() == OrderItem.WAIT_PAY) {
                viewHolder.businessBtn.setVisibility(View.VISIBLE);
                viewHolder.businessBtn.setBackgroundResource(ViewUtils.getOrderBusinessBackgroundRes(item.getStatus()));
                viewHolder.businessBtn.setText(ViewUtils.getOrderStatusString(item.getStatus()));

                viewHolder.businessBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getStatus() == OrderItem.IS_SEND) {
                            //TODO 查看物流
                        } else if (item.getStatus() == OrderItem.WAIT_PAY) {
                            //TODO 立即支付
                        }
                    }
                });
            }else {
                viewHolder.businessBtn.setVisibility(View.GONE);
            }

            viewHolder.goodsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入查看订单详情页
                    OrderDetailActivity.launch(mContext,item.getPo());
                }
            });
        }
    }

    private double getTotalAmount(List<OrderItem.OrderGoods> goods) {
        double totalAmount = 0.0;
        for(OrderItem.OrderGoods goodItem:goods){
            totalAmount = Utils.doubleAddDouble(totalAmount,Utils.doubleMultiplyDouble(goodItem.getPrice(), (double) goodItem.getQuantity()));
        }
        return totalAmount;
    }

    private int getTotalGoodsCount(List<OrderItem.OrderGoods> goods) {
        int goodsCount = 0;
        for(OrderItem.OrderGoods goodItem:goods){
            goodsCount += goodItem.getQuantity();
        }
        return goodsCount;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class OrderHolder extends RecyclerView.ViewHolder {
        private RelativeLayout goodsLayout;
        private TextView totalGoodsCountTv;
        private TextView orderStatusTv;
        private RemoteImageView goodsPicIv;
        private TextView goodsTitleTv;
        private TextView goodsPriceTv;
        private TextView actuallyPayTv;
        private Button businessBtn;
        public OrderHolder(View itemView) {
            super(itemView);
            totalGoodsCountTv = (TextView) itemView.findViewById(R.id.totalGoodsCountTv);
            orderStatusTv = (TextView) itemView.findViewById(R.id.orderStatusTv);
            goodsPicIv = (RemoteImageView) itemView.findViewById(R.id.goodsPicIv);
            goodsTitleTv = (TextView) itemView.findViewById(R.id.goodsTitleTv);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.goodsPriceTv);
            actuallyPayTv = (TextView) itemView.findViewById(R.id.actuallyPayTv);
            businessBtn = (Button) itemView.findViewById(R.id.businessBtn);
            goodsLayout = (RelativeLayout) itemView.findViewById(R.id.goodsLayout);
        }
    }

    @Override
    public void notifyDataChanged(List<OrderItem> items, boolean isRefresh) {
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
    public List<OrderItem> getData() {
        return mList;
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }
}
