package com.thinkeract.tka.ui.mall.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.Events;
import com.thinkeract.tka.R;
import com.thinkeract.tka.ThinkerActApplication;
import com.thinkeract.tka.common.utils.DBUtils;
import com.thinkeract.tka.common.utils.Utils;
import com.thinkeract.tka.data.db.greendao.GDGoodsItem;
import com.thinkeract.tka.widget.CommonDialog;
import com.zitech.framework.utils.ToastMaster;
import com.zitech.framework.widget.RemoteImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by minHeng on 2017/4/6 11:26.
 * mail:minhengyan@gmail.com
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int GOODS_TYPE = 0;
    public static final int TOTAL_FREIGHT_TYPE = 1;

    private Activity mContext;
    private List<GDGoodsItem> mList;

    public ShoppingCartAdapter(Activity context) {
        mContext = context;
    }

    public void setItemList(List<GDGoodsItem> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public List<GDGoodsItem> getItemList() {
        return mList;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getGoodsId() != 0 ? GOODS_TYPE : TOTAL_FREIGHT_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == GOODS_TYPE)
            return new GoodsItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cart_goods, parent, false));
        else
            return new TotalFreightViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_total_freight, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        GDGoodsItem item = mList.get(position);
        if (itemType == GOODS_TYPE) {
            final GoodsItemViewHolder goodsViewHolder = (GoodsItemViewHolder) holder;
            goodsViewHolder.checkItem.setChecked(item.getIsCheck());
            goodsViewHolder.goodsPicIv.setImageUri(Constants.ImageDefResId.DEF_SQUARE_PIC_NORMAL, item.getGoodsImg());
            goodsViewHolder.goodsTitleTv.setText(item.getName());
            goodsViewHolder.goodsPriceTv.setText(String.format(mContext.getResources().getString(R.string.rmb), item.getPrice()));
            goodsViewHolder.countTv.setText(String.valueOf(item.getGoodsCount()));
            goodsViewHolder.minusIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = goodsViewHolder.getLayoutPosition();
                    GDGoodsItem item = mList.get(pos);
                    minusGoodsCount(item, pos);
                }
            });

            goodsViewHolder.plusIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = goodsViewHolder.getLayoutPosition();
                    GDGoodsItem item = mList.get(pos);
                    plusGoodsCount(item, pos);
                }
            });

            goodsViewHolder.deleteGoodsIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = goodsViewHolder.getLayoutPosition();
                    showDeleteTips(pos);
                }
            });

            goodsViewHolder.checkItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int pos = goodsViewHolder.getLayoutPosition();
                    GDGoodsItem item = mList.get(pos);
                    item.setIsCheck(isChecked);
                    checkAndSendSelectedStatus();
                }
            });

        } else {
            TotalFreightViewHolder freightViewHolder = (TotalFreightViewHolder) holder;
            freightViewHolder.freightTv.setText(String.format(mContext.getResources().getString(R.string.rmb), calculationTotalFreight(mList)));
        }
    }

    private void checkAndSendSelectedStatus() {
        boolean allIsSelected = true;
        boolean mIsAllNotSelected = true;
        for (GDGoodsItem goodsItem : mList) {
            if (goodsItem.getGoodsId() != 0) {
                if (!(goodsItem.getIsCheck())) {
                    allIsSelected = false;
                } else {
                    mIsAllNotSelected = false;
                }
            }
        }
        if (allIsSelected) {
            EventBus.getDefault().post(new Events.GoodsSelectedStatusChange(1));
        } else if (mIsAllNotSelected) {
            EventBus.getDefault().post(new Events.GoodsSelectedStatusChange(2));
        } else {
            EventBus.getDefault().post(new Events.GoodsSelectedStatusChange(0));
        }
        ThinkerActApplication.getInstance().postDelay(new Runnable() {
            @Override
            public void run() {
                notifyItemChanged(mList.size() - 1);
            }
        }, 300);
    }

    private void showDeleteTips(final int position) {
        CommonDialog deleteDialog = new CommonDialog(mContext, "删除后无法恢复，确定要删除吗");
        deleteDialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
            @Override
            public void onClick(Dialog dialog) {
                DBUtils.deleteGoods(mList.get(position).getUserGoodsId());
                mList.remove(position);
                notifyItemRemoved(position);
                checkAndSendSelectedStatus();
            }
        });
        deleteDialog.show();
    }

    private void plusGoodsCount(GDGoodsItem item, int currentPosition) {
        if (item.getGoodsCount() < item.getInventory()) {
            item.setGoodsCount(item.getGoodsCount() + 1);
            notifyItemChanged(currentPosition);
            EventBus.getDefault().post(new Events.GoodsSelectedStatusChange(3));
        } else if (item.getGoodsCount() == item.getInventory()) {
            ToastMaster.shortToast("已经超出商品最大库存啦");
        }
    }

    private void minusGoodsCount(GDGoodsItem item, int currentPosition) {
        if (item.getGoodsCount() > 1) {
            item.setGoodsCount(item.getGoodsCount() - 1);
            notifyItemChanged(currentPosition);
            EventBus.getDefault().post(new Events.GoodsSelectedStatusChange(3));
        } else if (item.getGoodsCount() == 1) {
            ToastMaster.shortToast("不能再少啦");
        }
    }

    private float calculationTotalFreight(List<GDGoodsItem> goodsItemList) {
        float totalFreight = 0;
        for (GDGoodsItem goodsItem : goodsItemList) {
            if (goodsItem.getIsCheck() && goodsItem.getGoodsId() != 0) {
                totalFreight = Utils.floatAddFloat(totalFreight, goodsItem.getFreight());
            }
        }
        return totalFreight;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void setAllSelected(boolean allSelected) {
        for (GDGoodsItem goodsItem : mList) {
            goodsItem.setIsCheck(allSelected);
        }
        notifyDataSetChanged();
    }

    public static class GoodsItemViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkItem;
        private RemoteImageView goodsPicIv;
        private TextView goodsTitleTv;
        private TextView goodsPriceTv;
        private ImageView minusIv;
        private TextView countTv;
        private ImageView plusIv;
        private ImageView deleteGoodsIv;

        public GoodsItemViewHolder(View itemView) {
            super(itemView);

            checkItem = (CheckBox) itemView.findViewById(R.id.checkItem);
            goodsPicIv = (RemoteImageView) itemView.findViewById(R.id.goodsPicIv);
            goodsTitleTv = (TextView) itemView.findViewById(R.id.goodsTitleTv);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.goodsPriceTv);
            minusIv = (ImageView) itemView.findViewById(R.id.minusIv);
            countTv = (TextView) itemView.findViewById(R.id.countTv);
            plusIv = (ImageView) itemView.findViewById(R.id.plusIv);
            deleteGoodsIv = (ImageView) itemView.findViewById(R.id.deleteGoodsIv);

        }
    }

    public static class TotalFreightViewHolder extends RecyclerView.ViewHolder {
        private TextView freightTv;

        public TotalFreightViewHolder(View itemView) {
            super(itemView);

            freightTv = (TextView) itemView.findViewById(R.id.freightTv);
        }
    }
}
