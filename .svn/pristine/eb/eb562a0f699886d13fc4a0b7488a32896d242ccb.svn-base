package com.thinkeract.tka.ui.preview;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.preview.adapter.PhotoItem;
import com.thinkeract.tka.ui.preview.adapter.PhotoPreviewAdapter;
import com.thinkeract.tka.ui.preview.adapter.SpaceItemDecoration;
import com.zitech.framework.utils.ViewUtils;

import java.util.ArrayList;

/**
 * Created by lu on 2016/11/20.
 */

public class AlbumsActivity extends AppBarActivity {
    private RecyclerView mPhotoView;

    private ArrayList<PhotoItem> gdPhotoItems;

    @Override
    protected int getContentViewId() {
        return R.layout.preview_activity_photo_preview;
    }

    @Override
    protected void initView() {
        setTitle("相册");
        gdPhotoItems = getIntent().getParcelableArrayListExtra(Constants.ActivityExtra.PHOTOS);
        mPhotoView = (RecyclerView) findViewById(android.R.id.list);
    }

    @Override
    protected void initData() {
//        mPhotoView
        PhotoPreviewAdapter adapter = new PhotoPreviewAdapter(this);
        adapter.setItems(gdPhotoItems);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mPhotoView.setLayoutManager(gridLayoutManager);
        mPhotoView.setItemAnimator(new DefaultItemAnimator());
        mPhotoView.addItemDecoration(new SpaceItemDecoration(ViewUtils.getDimenPx(R.dimen.w30)));
        mPhotoView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PhotoPreviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, PhotoItem item, int position) {
                PhotoPagerActivity.launch(AlbumsActivity.this, gdPhotoItems, view, position);
            }
        });

    }
}
