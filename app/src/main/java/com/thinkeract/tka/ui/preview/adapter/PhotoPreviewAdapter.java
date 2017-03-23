package com.thinkeract.tka.ui.preview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkeract.tka.R;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu on 2016/10/28.
 */

public class PhotoPreviewAdapter extends RecyclerView.Adapter<PhotoPreviewAdapter.ViewHolder> {
    private Context mContext;
    private List<PhotoItem> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;


    public PhotoPreviewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<PhotoItem> getItems() {
        return items;
    }

    public void setItems(List<PhotoItem> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.preview_item_photo_preview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        PhotoItem item = items.get(position);
        holder.render(item);
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ViewUtils.isFastDoubleClick())
                    return;
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view,items.get(position),position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RemoteImageView picture;

        public ViewHolder(View itemView) {
            super(itemView);
            picture = (RemoteImageView) itemView.findViewById(R.id.picture);
        }

        public void render(PhotoItem item) {
            picture.setImageUri(R.drawable.ic_pic_empty, item.getPhotoUrlS());

        }

        public void setOnClickListener(final View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }

    public interface OnItemClickListener {
         void onItemClick(View view, PhotoItem item, int position);
    }

    public void setOnItemClickListener(PhotoPreviewAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
