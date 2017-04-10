package com.thinkeract.tka.ui.mall.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.AddressItem;

import java.util.List;

/**
 * Created by minHeng on 2017/4/7 15:01.
 * mail:minhengyan@gmail.com
 */

public class AddressSimpleAdapter extends RecyclerView.Adapter<AddressSimpleAdapter.SimpleAddressHolder>{

    private Activity mContext;
    private List<AddressItem> mList;

    public AddressSimpleAdapter(Activity context){
        mContext  = context;
    }

    public void setItemList(List<AddressItem> itemList){
        mList = itemList;
    }

    @Override
    public SimpleAddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleAddressHolder(LayoutInflater.from(mContext).inflate(R.layout.item_address_simple,parent,false));
    }

    @Override
    public void onBindViewHolder(SimpleAddressHolder holder, int position) {
        AddressItem item = mList.get(position);

        holder.contactNameTv.setText(item.getContact());
        holder.phoneNumTv.setText(item.getPhone());
        holder.addressTv.setText(item.getCityname()+item.getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 编辑地址
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    public static class SimpleAddressHolder extends RecyclerView.ViewHolder{
        private TextView contactNameTv;
        private TextView phoneNumTv;
        private TextView addressTv;
        public SimpleAddressHolder(View itemView) {
            super(itemView);
            contactNameTv = (TextView) itemView.findViewById(R.id.contactNameTv);
            phoneNumTv = (TextView) itemView.findViewById(R.id.phoneNumTv);
            addressTv = (TextView) itemView.findViewById(R.id.addressTv);
        }
    }

}
