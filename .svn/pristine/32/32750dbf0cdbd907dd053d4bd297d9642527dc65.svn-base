/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thinkeract.tka.ui.preview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.thinkeract.tka.R;
import com.thinkeract.tka.ui.preview.widget.SmoothPhotoView;
import com.zitech.framework.utils.ViewUtils;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImagesDetailActivity extends Activity {

    public static final String INTENT_IMAGE_URL_TAG = "INTENT_IMAGE_URL_TAG";

    private static final String PHOTO_INSERT_RECT = "photoInsertRect";
    private String mImageUrl;
    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;

    //    @InjectView(R.id.images_detail_smooth_image)
    SmoothPhotoView mSmoothImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            setSystemBarTintDrawable(new ColorDrawable(Color.parseColor("#000000")));
        }
        initView();
        initData();
    }

    //    @Override
    protected int getContentViewId() {
        return R.layout.activity_images_detail;
    }

//    @Override
    protected void initView() {
        mImageUrl = getIntent().getStringExtra(INTENT_IMAGE_URL_TAG);
        Rect insertRect = getIntent().getParcelableExtra(PHOTO_INSERT_RECT);
        mLocationX = insertRect.left;
        mLocationY = insertRect.top;
        mWidth = insertRect.width();
        mHeight = insertRect.height();
        mSmoothImageView = (SmoothPhotoView) findViewById(R.id.images_detail_smooth_image);
//        setTranslucentStatus(true);
        setTranslucentStatus(true);
    }

//    @Override
    protected void initData() {
        mSmoothImageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        mSmoothImageView.transformIn();
        mSmoothImageView.setImageUri(mImageUrl);
        mSmoothImageView.setOnTransformListener(new SmoothPhotoView.TransformListener() {
            @Override
            public void onTranforming(float scale, int mode) {

            }

            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });

        mSmoothImageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v2) {
                mSmoothImageView.transformOut();
            }
        });

    }


    @Override
    public void onBackPressed() {
        mSmoothImageView.transformOut();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }

    //    @Override
//    protected int getComtent() {
//        return R.layout.activity_images_detail;
//    }
    public static void launch(Activity context, String mImageUrl, View view) {

        Rect frameRect = ViewUtils.getViewFrameRect(context, view);
        Intent intent = new Intent(context, ImagesDetailActivity.class);
        intent.putExtra(INTENT_IMAGE_URL_TAG, mImageUrl);
        intent.putExtra(PHOTO_INSERT_RECT, frameRect);
        context.startActivity(intent);
        context.overridePendingTransition(0, 0);
    }
    /**
     * set status bar translucency
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }
    /**
     * use SytemBarTintManager
     *
     * @param tintDrawable
     */
    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }
    }
}
