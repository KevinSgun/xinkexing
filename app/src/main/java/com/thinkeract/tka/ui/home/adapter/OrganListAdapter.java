package com.thinkeract.tka.ui.home.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.SecondReportItem;
import com.thinkeract.tka.ui.home.CheckResultActivity;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

import java.util.List;

/**
 * Created by ymh on 2017/4/9 18:31
 * e-mail:minhengyan@gmail.com
 */

public class OrganListAdapter extends RecyclerView.Adapter{

    private List<SecondReportItem> mList;
    private Activity mContext;
    private static final int CONTENT = 0;
    private static final int EMPTY = 1;

    public OrganListAdapter(Activity activity){
        mContext = activity;
    }

    public void setItemList(List<SecondReportItem> itemList){
        mList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(mList.get(position).getId() == 0)
            return EMPTY;
        else
            return CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == CONTENT)
            return new OrganHolder(LayoutInflater.from(mContext).inflate(R.layout.item_organ,parent,false));
        else
            return new EmptyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_organ_empty,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        if(itemType == CONTENT){
            final SecondReportItem item = mList.get(position);
            OrganHolder organHolder = (OrganHolder) holder;
            organHolder.organIv.setImageUri(Constants.ImageDefResId.DEF_SQUARE_PIC_NORMAL,item.isChecked()?item.getCheckedIcon():item.getNoCheckedIcon());
            organHolder.itemsName.setTextColor(mContext.getResources().getColor(item.isChecked()?R.color.textColorPrimary:R.color.text_light_gray));
            reSizeTextView(organHolder.itemsName,item.getName());
            organHolder.itemsName.setText(item.getName());
            organHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入查看详细数据
                    CheckResultActivity.launch(mContext,item.getId(),item.getName());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    private void reSizeTextView(TextView textView, String text){
        if(text==null) return;
        int textSizeInPx = ViewUtils.getDimenPx(R.dimen.w22);
        if(text.length()>5) {
            Paint paint = textView.getPaint();
            float textWidth = paint.measureText(text);
            int maxWidth = ViewUtils.getDimenPx(R.dimen.w150);
            if (textWidth > maxWidth) {
                for (; textSizeInPx > 0; textSizeInPx--) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeInPx);
                    paint = textView.getPaint();
                    textWidth = paint.measureText(text);
                    if (textWidth <= maxWidth) {
                        break;
                    }
                }
            }
            textView.invalidate();
        }else{
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeInPx);
        }
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

    public static class EmptyHolder extends RecyclerView.ViewHolder{
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
