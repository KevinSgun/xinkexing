package com.thinkeract.tka.ui.mine.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.response.LogisticsData;
import com.thinkeract.tka.ui.ListAdapter;

/**
 * Created by minHeng on 2017/4/12 14:07.
 * mail:minhengyan@gmail.com
 */

public class LogisticsAdapter extends ListAdapter<LogisticsData> {
    public static final int LOGISTICS_HEAD = 0;
    public static final int LOGISTICS_ITEM = 1;

    public LogisticsAdapter(Activity context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOGISTICS_ITEM)
            return new LogisticsItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_logistics, parent, false));
        else
            return new LogisticsHeadHolder(LayoutInflater.from(mContext).inflate(R.layout.item_logistics_head, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public static class LogisticsHeadHolder extends RecyclerView.ViewHolder {
        private TextView expressCompanyTv;
        private TextView waybillNumTv;

        public LogisticsHeadHolder(View itemView) {
            super(itemView);
            expressCompanyTv = (TextView) itemView.findViewById(R.id.expressCompanyTv);
            waybillNumTv = (TextView) itemView.findViewById(R.id.waybillNumTv);
        }
    }

    public static class LogisticsItemHolder extends RecyclerView.ViewHolder {
        private View logisticsTopView;
        private ImageView circlePointIv;
        private View logisticsBottomView;
        private TextView logisticsContentTv;
        private TextView logisticsTimeTv;
        public LogisticsItemHolder(View itemView) {
            super(itemView);
            logisticsTopView = itemView.findViewById(R.id.logisticsTopView);
            circlePointIv = (ImageView) itemView.findViewById(R.id.circlePointIv);
            logisticsBottomView = itemView.findViewById(R.id.logisticsBottomView);
            logisticsContentTv = (TextView) itemView.findViewById(R.id.logisticsContentTv);
            logisticsTimeTv = (TextView) itemView.findViewById(R.id.logisticsTimeTv);
        }
    }

}
