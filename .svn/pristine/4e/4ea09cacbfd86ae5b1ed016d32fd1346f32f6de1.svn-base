package com.thinkeract.tka.ui.preview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.Logger;
import com.thinkeract.tka.common.utils.Utils;
import com.thinkeract.tka.ui.preview.adapter.PhotoItem;
import com.thinkeract.tka.ui.preview.widget.HackyViewPager;
import com.thinkeract.tka.ui.preview.widget.SmoothPhotoView;
import com.zitech.framework.utils.ToastMaster;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoPagerActivity extends Activity {

    private HackyViewPager mViewPager;

    private static final String PHOTO_URLS = "photoUrls";
    //    private static final String THUMBNAIL_URLS = "thumbnailUrls";
    private static final String POSIOTION = "position";
    private static final String PHOTO_INSERT_RECT = "photoInsertRect";
    protected int position;

    private SamplePagerAdapter adapter;

    private ArrayList<String> photoUrls;

    private TextView photoCountTv;
    private Rect insertRect;

    private int currentPostion;
    private int startPosition;
    private boolean firstInit = true;
    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            setSystemBarTintDrawable(new ColorDrawable(Color.parseColor("#000000")));
        }
        setContentView(getContentViewId());
        initView();
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

    protected int getContentViewId() {
        return R.layout.preview_activity_photos_pager;
    }

    protected void initView() {
        contentView = findViewById(R.id.contentView);
//        contentView.setAlpha();
        // savePic = (Button) findViewById(R.id.save_pic);
        mViewPager = (HackyViewPager) findViewById(R.id.hackpager);
        photoCountTv = (TextView) findViewById(R.id.photo_counts);
        handleIntent(getIntent());
    }

    //    @Override
    protected void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        firstInit = true;
    }

    @Override
    public void finish() {
        super.finish();
        if (insertRect == null) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else {
            overridePendingTransition(0, 0);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @SuppressWarnings("deprecation")
    private void handleIntent(Intent intent) {
        photoUrls = intent.getStringArrayListExtra(PHOTO_URLS);
        startPosition = intent.getIntExtra(POSIOTION, 0);
        currentPostion = startPosition;
        insertRect = intent.getParcelableExtra(PHOTO_INSERT_RECT);
        adapter = new SamplePagerAdapter(photoUrls);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPostion);
        if (insertRect == null) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            contentView.setBackgroundColor(Color.BLACK);
        } else {
            overridePendingTransition(0, 0);
            Animation animation=AnimationUtils.loadAnimation(this,R.anim.fade_in);
            animation.setDuration(1000);
            animation.setStartOffset(1000);
            photoCountTv.startAnimation(animation);
        }
        final int allCouts = photoUrls.size();
        photoCountTv.setText(currentPostion + 1 + "/" + allCouts);


        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                PhotoPagerActivity.this.position = position;
                photoCountTv.setText(position + 1 + "/" + allCouts);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    class SamplePagerAdapter extends PagerAdapter {
        ArrayList<String> photoUrls;
        private PhotoView photoView;

        public SamplePagerAdapter(ArrayList<String> photosPath) {
            super();
            this.photoUrls = photosPath;
        }

        @Override
        public int getCount() {
            return photoUrls.size();
        }

        public View getCurrentView() {
            return photoView;
        }

        @SuppressLint("NewApi")
        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            final SmoothPhotoView photoView = new SmoothPhotoView(container.getContext());
            photoView.setImageUri(photoUrls.get(position));
            photoView.setOnErrorListener(new RemoteImageView.OnErrorListener() {
                @Override
                public void onLoadError(String url) {
                    Logger.i(PhotoPagerActivity.class,"error:"+url);
                    photoView.setImageUri(Utils.getUriFromResource(PhotoPagerActivity.this,R.drawable.ic_pic_empty).toString());
//                    photoView.setImageResource(R.drawable.ic_pic_empty);
                }
            });
            photoView.setOnTransformListener(new SmoothPhotoView.TransformListener() {
                @Override
                public void onTranforming(float scale, int mode) {
                    if (mode == SmoothPhotoView.STATE_TRANSFORM_OUT) {
                        String value = Integer.toHexString((int) (255 * scale));
                        if (value.length() == 0) {
                            value = "0" + value;
                        }
                        contentView.setBackgroundColor(Color.parseColor("#" + value + "000000"));
                    }
                }

                @Override
                public void onTransformComplete(int mode) {
                    if (mode == SmoothPhotoView.STATE_TRANSFORM_OUT) {
                        finish();
                    } else if (mode == SmoothPhotoView.STATE_TRANSFORM_IN) {
                        contentView.setBackgroundColor(Color.BLACK);
                    }
                }
            });
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {

                @Override
                public void onViewTap(View view, float x, float y) {
                    if (insertRect != null) {
                        photoView.transformOut();
                    } else {
                        finish();
                    }

                }
            });
            if (insertRect != null) {
                photoView.setOriginalInfo(insertRect.width(), insertRect.height(), insertRect.left, insertRect.top);
                if (position != PhotoPagerActivity.this.startPosition || !firstInit) {
                    photoView.setTranformInEnable(false);
                }
            }
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            this.photoView = (PhotoView) object;
            if (position == PhotoPagerActivity.this.startPosition && firstInit) {
                firstInit = false;
                ((SmoothPhotoView) photoView).transformIn();
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    public static void launch(Activity context, List<? extends PhotoItem> gdPhotoItems, View view, int position) {
        ArrayList<String> hdPhotos = new ArrayList<String>();
        for (PhotoItem photoItem : gdPhotoItems) {
            hdPhotos.add(photoItem.getPhotoUrlB());
        }
        Rect frameRect = ViewUtils.getViewFrameRect(context, view);
        PhotoPagerActivity.launch(context, hdPhotos, frameRect, position);
    }

    /**
     * @param context
     * @param photoUrls
     */

    public static void launch(Context context, ArrayList<String> photoUrls, Rect fromRect, int position) {
        try {
            if (photoUrls == null || photoUrls.size() <= 0) {
                ToastMaster.popToast(context, context.getResources().getString(R.string.image_noloading));
            } else {
                Intent intent = new Intent(context, PhotoPagerActivity.class);
                intent.putStringArrayListExtra(PHOTO_URLS, photoUrls);
                intent.putExtra(PHOTO_INSERT_RECT, fromRect);
                intent.putExtra(POSIOTION, position);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            Logger.e("eeeee" + e.getMessage());
        }

    }

    public static void launch(Context mContext, String url, String thumb, Rect fromRect) {
        ArrayList<String> urls = new ArrayList<>();
        urls.add(url);
        launch(mContext, urls, fromRect, 0);
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
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
