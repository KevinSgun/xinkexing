package com.thinkeract.tka.ui.mine.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.entity.AddressItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minHeng on 2017/4/7 15:01.
 * mail:minhengyan@gmail.com
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressHolder> implements IDataAdapter<List<AddressItem>> {

    private Activity mContext;
    private List<AddressItem> mList;
    private OnItemStuffClickListener onItemListener;

    public AddressListAdapter(Activity context){
        mContext  = context;
        mList = new ArrayList<>();
    }

    public void setItemList(List<AddressItem> itemList){
        mList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddressHolder(LayoutInflater.from(mContext).inflate(R.layout.item_address,parent,false));
    }

    @Override
    public void onBindViewHolder(final AddressHolder holder, int position) {
       AddressItem item = mList.get(position);

        if(item.getStatus()==1) {
            holder.defaultAddressTv.setText("默认地址");
            holder.defaultAddressTv.setTextColor(mContext.getResources().getColor(R.color.text_red));
            holder.defaultAddressIv.setImageResource(R.mipmap.ic_check_sel);
        } else {
            holder.defaultAddressTv.setText("设为默认");
            holder.defaultAddressTv.setTextColor(mContext.getResources().getColor(R.color.textColorPrimaryGray));
            holder.defaultAddressIv.setImageResource(R.mipmap.ic_check_nor);
        }

        holder.contactNameTv.setText(item.getContact());
        holder.phoneNumTv.setText(item.getPhone());
        holder.addressTv.setText(item.getCityname()+item.getAddress());

        holder.editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 编辑地址
                if(!ViewUtils.isFastDoubleClick()){
                    if(onItemListener != null)
                        onItemListener.onEditClick(holder.getLayoutPosition());
                }
            }
        });

        holder.delTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除地址
                if(!ViewUtils.isFastDoubleClick()){
                    if(onItemListener != null)
                        onItemListener.onDeleteClick(holder.getLayoutPosition());
                }
//                showDeleteTips(item,position);
            }
        });

        holder.defaultAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 设为默认地址
                if(!ViewUtils.isFastDoubleClick()){
                    if(onItemListener != null)
                        onItemListener.onSetDefClick(holder.getLayoutPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }


    @Override
    public void notifyDataChanged(List<AddressItem> items, boolean isRefresh) {
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
    public List<AddressItem> getData() {
        return mList;
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }

    public static class AddressHolder extends RecyclerView.ViewHolder{
        private TextView contactNameTv;
        private TextView phoneNumTv;
        private TextView addressTv;
        private TextView delTv;
        private TextView editTv;
        private LinearLayout defaultAddressLayout;
        private TextView defaultAddressTv;
        private ImageView defaultAddressIv;
        public AddressHolder(View itemView) {
            super(itemView);
            contactNameTv = (TextView) itemView.findViewById(R.id.contactNameTv);
            phoneNumTv = (TextView) itemView.findViewById(R.id.phoneNumTv);
            addressTv = (TextView) itemView.findViewById(R.id.addressTv);
            delTv = (TextView) itemView.findViewById(R.id.delTv);
            editTv = (TextView) itemView.findViewById(R.id.editTv);
            defaultAddressLayout = (LinearLayout) itemView.findViewById(R.id.defaultAddressLayout);
            defaultAddressTv = (TextView) itemView.findViewById(R.id.defaultAddressTv);
            defaultAddressIv = (ImageView) itemView.findViewById(R.id.defaultAddressIv);
        }
    }

    public void setOnItemStuffClickListener(OnItemStuffClickListener listener){
        this.onItemListener = listener;
    }

    public interface OnItemStuffClickListener{
        void onSetDefClick(int position);
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

}
