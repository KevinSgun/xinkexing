package com.thinkeract.tka.ui;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ymh on 2017/4/9 22:48
 * e-mail:minhengyan@gmail.com
 */

public abstract class ListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Activity mContext;
    protected List<T> mList;

    public ListAdapter(Activity context){
        mContext = context;
    }

    public void setItemList(List<T> itemList){
        mList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList != null?mList.size():0;
    }

}
