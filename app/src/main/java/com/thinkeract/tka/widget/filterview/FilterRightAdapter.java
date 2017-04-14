package com.thinkeract.tka.widget.filterview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkeract.tka.R;

import java.util.List;

/**
 * Created by minHeng on 17/4/12 13:57.
 * mail:minhengyan@gmail.com
 */
public class FilterRightAdapter extends BaseListAdapter<FilterEntity> {

    public FilterRightAdapter(Context context, List<FilterEntity> list) {
        super(context, list);
    }

    public void setSelectedEntity(FilterEntity filterEntity) {
        for (FilterEntity entity : getData()) {
            entity.setSelected(filterEntity != null && entity.getKey().equals(filterEntity.getKey()));
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.filterview_item_one, null);
        }

        FilterEntity entity = getItem(position);
        TextView tvTitle = ViewHolder.get(convertView,R.id.titleTv);
        tvTitle.setText(entity.getKey());
        if (entity.isSelected()) {
            tvTitle.setTextColor(mContext.getResources().getColor(R.color.text_theme_color));
        } else {
            tvTitle.setTextColor(mContext.getResources().getColor(R.color.textColorPrimaryDark));
        }

        return convertView;
    }

}
