package com.thinkeract.tka.ui.mall.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.StringUtils;
import com.thinkeract.tka.data.api.entity.GoodsComment;
import com.thinkeract.tka.data.api.entity.GoodsDetailItem;
import com.thinkeract.tka.data.api.entity.GoodsItem;
import com.thinkeract.tka.data.api.response.GoodsDetailData;
import com.thinkeract.tka.ui.ListAdapter;
import com.thinkeract.tka.widget.SimpleWebView;
import com.zitech.framework.transform.CropCircleTransformation;
import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minHeng on 2017/4/14 16:58.
 * mail:minhengyan@gmail.com
 */

public class GoodsDetailAdapter extends ListAdapter<GoodsDetailItem> implements IDataAdapter<List<GoodsComment>> {

    private OnItemClickListener onItemClickListener;

    public GoodsDetailAdapter(Activity context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == GoodsDetailItem.TYPE_HEAD)
            return new HeadHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goods_detail_top,parent,false));
        else
            return new CommentHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        GoodsDetailItem item = mList.get(position);
        if(itemType == GoodsDetailItem.TYPE_HEAD){
            final GoodsDetailData goodsDetailData = item.getGoodsDetailData();
            HeadHolder headHolder = (HeadHolder) holder;
            GoodsItem goodsItem = goodsDetailData.getGoods();
            headHolder.goodsNameTv.setText(goodsItem.getName());
            headHolder.subTitleTv.setText(goodsItem.getSubtitle());
            headHolder.goodsPriceTv.setText(String.format(mContext.getResources().getString(R.string.rmb),goodsItem.getMinprice()));
            headHolder.introduceWebView.loadDataWithBaseURL(
                    null, StringUtils.convertHtmlTxt(goodsItem.getDescr()),
                    "text/html", "utf-8", null);

            headHolder.chooseSpecLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null)
                        onItemClickListener.chooseSpec(goodsDetailData);
                }
            });

            headHolder.serviceDescriptionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null)
                        onItemClickListener.lookServiceDescription(goodsDetailData);
                }
            });

            headHolder.noCommentTv.setVisibility(getItemCount() == 1?View.VISIBLE:View.GONE);
        }else {
            GoodsComment goodsComment = item.getGoodsComment();
            CommentHolder commentHolder = (CommentHolder) holder;
            commentHolder.userAvatarIv.setBitmapTransformation(new CropCircleTransformation(mContext));
            commentHolder.userAvatarIv.setImageUri(Constants.ImageDefResId.DEF_AVA_NORMAL,goodsComment.getPhotos());
            commentHolder.userNameTv.setText(goodsComment.getUname());
            commentHolder.commentContentTv.setText(goodsComment.getContent());
        }

    }

    public static class HeadHolder extends RecyclerView.ViewHolder{
        private TextView noCommentTv;
        private TextView goodsNameTv;
        private TextView subTitleTv;
        private TextView goodsPriceTv;
        private LinearLayout chooseSpecLayout;
        private SimpleWebView introduceWebView;
//        private TextView serviceDescriptionA;
//        private TextView serviceDescriptionB;
        private LinearLayout serviceDescriptionLayout;
        public HeadHolder(View itemView) {
            super(itemView);
            goodsNameTv = (TextView) itemView.findViewById(R.id.goodsNameTv);
            subTitleTv = (TextView) itemView.findViewById(R.id.subTitleTv);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.goodsPriceTv);
            chooseSpecLayout = (LinearLayout) itemView.findViewById(R.id.chooseSpecLayout);
            introduceWebView = (SimpleWebView) itemView.findViewById(R.id.introduceWebView);
//            serviceDescriptionA = (TextView) itemView.findViewById(R.id.serviceDescriptionA);
//            serviceDescriptionB = (TextView) itemView.findViewById(R.id.serviceDescriptionB);
            serviceDescriptionLayout = (LinearLayout) itemView.findViewById(R.id.serviceDescriptionLayout);
            noCommentTv = (TextView) itemView.findViewById(R.id.noCommentTv);
        }
    }

    public static class CommentHolder extends RecyclerView.ViewHolder{
        private RemoteImageView userAvatarIv;
//        private FrameLayout userAvatarLayout;
        private TextView userNameTv;
        private TextView commentContentTv;
//        private LinearLayout replyContentLayout;
        public CommentHolder(View itemView) {
            super(itemView);
            userAvatarIv = (RemoteImageView) itemView.findViewById(R.id.userAvatarIv);
//            userAvatarLayout = (FrameLayout) itemView.findViewById(R.id.userAvatarLayout);
            userNameTv = (TextView) itemView.findViewById(R.id.userNameTv);
            commentContentTv = (TextView) itemView.findViewById(R.id.commentContentTv);
//            replyContentLayout = (LinearLayout) itemView.findViewById(R.id.replyContentLayout);
        }
    }

    @Override
    public void notifyDataChanged(List<GoodsComment> items, boolean isRefresh) {
        boolean empty = this.mList.isEmpty();
        int sizeBeforeChange = this.mList.size();
        int size = this.mList.size();
        this.mList.addAll(convertToGoodsDetail(items));
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

    private List<GoodsDetailItem> convertToGoodsDetail(List<GoodsComment> items) {
        List<GoodsDetailItem> goodsDetailItemList = new ArrayList<>();
        for (int i=0,len = items.size();i<len;i++) {
            GoodsComment item = items.get(i);
            GoodsDetailItem goodsDetailItem = new GoodsDetailItem();
            goodsDetailItem.setGoodsComment(item);
            goodsDetailItemList.add(goodsDetailItem);
        }
        return goodsDetailItemList;
    }

    @Override
    public List<GoodsComment> getData() {
        List<GoodsComment> commentItemList = new ArrayList<>();
        for (GoodsDetailItem item : mList) {
            GoodsComment goodsComment = item.getGoodsComment();
            if (item.getItemType() == GoodsDetailItem.TYPE_COMMENT)
                commentItemList.add(goodsComment);
        }
        return commentItemList;
    }

    @Override
    public boolean isEmpty() {
        return mList == null || mList.size() == 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void chooseSpec(GoodsDetailData item);
        void lookServiceDescription(GoodsDetailData item);
    }

}
