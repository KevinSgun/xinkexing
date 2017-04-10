package com.thinkeract.tka.ui.mine.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.entity.OrderItem;
import com.zitech.framework.widget.RemoteImageView;

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

        viewHolder.totalGoodsCountTv.setText(String.format(mContext.getResources().getString(R.string.total_goods_count),item.getTotalGoodsCount()));
        viewHolder.orderStatusTv.setTextColor(mContext.getResources().getColor(ViewUtils.getOrderStatusColorRes(item.getOrderStatus())));
        viewHolder.orderStatusTv.setText(ViewUtils.getOrderStatusString(item.getOrderStatus()));

        viewHolder.goodsPicIv.setImageUri(Constants.ImageDefResId.DEF_SQUARE_PIC_NORMAL,item.getGoodsImg());
        viewHolder.goodsPriceTv.setText(String.format(mContext.getResources().getString(R.string.rmb),item.getAmount()));
        viewHolder.actuallyPayTv.setText(String.format(mContext.getResources().getString(R.string.rmb),item.getActuallyAmount()));
        viewHolder.goodsTitleTv.setText(item.getGoodsName());

        viewHolder.businessBtn.setBackgroundResource(ViewUtils.getOrderBusinessBgRes(item.getOrderStatus()));
        viewHolder.businessBtn.setText(ViewUtils.getOrderStatusString(item.getOrderStatus()));

        viewHolder.businessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getOrderStatus() == OrderItem.IS_SEND) {
                    //TODO 查看物流
                }else if(item.getOrderStatus() == OrderItem.WAIT_PAY){
                    //TODO 立即支付
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class OrderHolder extends RecyclerView.ViewHolder {
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
