package com.thinkeract.tka.widget.filterview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkeract.tka.R;

import java.util.List;

/**
 * Created by minHeng on 17/4/12 13:57.
 * mail:minhengyan@gmail.com
 */
public class FilterLeftAdapter extends BaseListAdapter<FilterTwoEntity> {

    public FilterLeftAdapter(Context context) {
        super(context);
    }

    public FilterLeftAdapter(Context context, List<FilterTwoEntity> list) {
        super(context, list);
    }

    public void setSelectedEntity(FilterTwoEntity filterEntity) {
        for (FilterTwoEntity entity : getData()) {
            entity.setSelected(filterEntity != null && entity.getType().equals(filterEntity.getType()));
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.filterview_item_left, null);
        }
        TextView tvTitle = ViewHolder.get(convertView,R.id.titleTv);
        LinearLayout llRootView = ViewHolder.get(convertView,R.id.rootViewLayout);

        FilterTwoEntity entity = getItem(position);

        tvTitle.setText(entity.getType());
        if (entity.isSelected()) {
            tvTitle.setTextColor(mContext.getResources().getColor(R.color.text_theme_color));
            llRootView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            tvTitle.setTextColor(mContext.getResources().getColor(R.color.textColorPrimaryDark));
            llRootView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }

        return convertView;
    }
}
