package com.thinkeract.tka.widget.filterview;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minHeng on 17/4/12 13:57.
 * mail:minhengyan@gmail.com
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected Context mContext;
    private List<T> mList = new ArrayList<T>();
    protected LayoutInflater mInflater;

    public BaseListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public BaseListAdapter(Context context, List<T> list) {
        this(context);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList!=null?mList.size():0;
    }

    public void clearAll() {
        mList.clear();
    }

    public List<T> getData() {
        return mList;
    }

    public void addAll(List<T> list){
        if(list==null||list.size()==0) return;
        mList.addAll(list);
    }
    public void add(T item){
        mList.add(item);
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeEntity(T t){
        mList.remove(t);
    }
    public static class ViewHolder {
        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View convertView, int resId) {
            SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                convertView.setTag(viewHolder);
            }
            View childView = viewHolder.get(resId);
            if (childView == null) {
                childView = convertView.findViewById(resId);
                viewHolder.put(resId, childView);
            }
            return (T) childView;
        }
    }

}
