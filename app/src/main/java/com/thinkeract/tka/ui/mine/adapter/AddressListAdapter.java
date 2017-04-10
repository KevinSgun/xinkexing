package com.thinkeract.tka.ui.mine.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.AddressItem;

import java.util.List;

/**
 * Created by minHeng on 2017/4/7 15:01.
 * mail:minhengyan@gmail.com
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressHolder>{

    private Activity mContext;
    private List<AddressItem> mList;

    public AddressListAdapter(Activity context){
        mContext  = context;
    }

    public void setItemList(List<AddressItem> itemList){
        mList = itemList;
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddressHolder(LayoutInflater.from(mContext).inflate(R.layout.item_address,parent,false));
    }

    @Override
    public void onBindViewHolder(AddressHolder holder, int position) {
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
            }
        });

        holder.delTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 删除地址
            }
        });

        holder.defaultAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 设为默认地址
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
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

}
