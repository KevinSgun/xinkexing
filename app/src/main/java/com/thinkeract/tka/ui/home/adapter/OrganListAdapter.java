package com.thinkeract.tka.ui.home.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.OrganItem;
import com.zitech.framework.widget.RemoteImageView;

import java.util.List;

/**
 * Created by ymh on 2017/4/9 18:31
 * e-mail:minhengyan@gmail.com
 */

public class OrganListAdapter extends RecyclerView.Adapter  implements IDataAdapter<List<OrganItem>> {

    private List<OrganItem> mList;
    private Activity mContext;

    public OrganListAdapter(Activity activity){
        mContext = activity;
    }

    public void setItemList(List<OrganItem> itemList){
        mList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrganHolder(LayoutInflater.from(mContext).inflate(R.layout.item_organ,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final OrganItem item = mList.get(position);
        OrganHolder organHolder = (OrganHolder) holder;

    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    public static class OrganHolder extends RecyclerView.ViewHolder{
        private RemoteImageView organIv;
        private TextView itemsName;
        public OrganHolder(View itemView) {
            super(itemView);

            organIv = (RemoteImageView) itemView.findViewById(R.id.organIv);
            itemsName = (TextView) itemView.findViewById(R.id.itemsName);
        }
    }

    @Override
    public void notifyDataChanged(List<OrganItem> items, boolean isRefresh) {
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
    public List<OrganItem> getData() {
        return mList;
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }

}
