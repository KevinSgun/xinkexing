package com.thinkeract.tka.ui.home.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.AllergyItem;
import com.zitech.framework.widget.RemoteImageView;

import java.util.List;

/**
 * Created by ymh on 2017/4/9 18:31
 * e-mail:minhengyan@gmail.com
 */

public class AllergyListAdapter extends RecyclerView.Adapter  implements IDataAdapter<List<AllergyItem>> {

    private List<AllergyItem> mList;
    private Activity mContext;

    public AllergyListAdapter(Activity activity){
        mContext = activity;
    }

    public void setItemList(List<AllergyItem> itemList){
        mList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AllergyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_allergy,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AllergyItem item = mList.get(position);
        AllergyHolder allergyHolder = (AllergyHolder) holder;

    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    public static class AllergyHolder extends RecyclerView.ViewHolder{
        private RemoteImageView allergyIv;
        private TextView itemsName;
        public AllergyHolder(View itemView) {
            super(itemView);

            allergyIv = (RemoteImageView) itemView.findViewById(R.id.allergyIv);
            itemsName = (TextView) itemView.findViewById(R.id.itemsName);
        }
    }

    @Override
    public void notifyDataChanged(List<AllergyItem> items, boolean isRefresh) {
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
    public List<AllergyItem> getData() {
        return mList;
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }

}
