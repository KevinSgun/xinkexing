package com.thinkeract.tka.ui.mall.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.db.greendao.GDAddress;

import java.util.List;

/**
 * Created by minHeng on 2017/4/7 15:01.
 * mail:minhengyan@gmail.com
 */

public class AddressSimpleAdapter extends RecyclerView.Adapter<AddressSimpleAdapter.SimpleAddressHolder>{

    private List<GDAddress> mList;
    private OnItemClickListener onItemClickListener;

    public void setItemList(List<GDAddress> itemList){
        mList = itemList;
    }

    @Override
    public SimpleAddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleAddressHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_simple,parent,false));
    }

    @Override
    public void onBindViewHolder(SimpleAddressHolder holder, int position) {
        final GDAddress item = mList.get(position);

        holder.contactNameTv.setText(item.getContact());
        holder.phoneNumTv.setText(item.getPhone());
        holder.addressTv.setText(item.getCity()+item.getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(onItemClickListener!=null)
                   onItemClickListener.onItemClick(item);
            }
        });
        holder.arrowAddressIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑地址
                if(onItemClickListener!=null)
                    onItemClickListener.onEditAddress(item);
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
        private TextView arrowAddressIv;
        public SimpleAddressHolder(View itemView) {
            super(itemView);
            contactNameTv = (TextView) itemView.findViewById(R.id.contactNameTv);
            phoneNumTv = (TextView) itemView.findViewById(R.id.phoneNumTv);
            addressTv = (TextView) itemView.findViewById(R.id.addressTv);
            arrowAddressIv = (TextView) itemView.findViewById(R.id.arrowAddressIv);
        }
    }

    public interface OnItemClickListener{
        void onEditAddress(GDAddress item);
        void onItemClick(GDAddress item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
