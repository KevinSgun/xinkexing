package com.zitech.framework.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * @author LuDaiqian
 */
public class RemoteImageView extends android.support.v7.widget.AppCompatImageView {

    private static final int NONE = -1;
    /**
     * 网络图片url
     */
    private String mUrl;

    private int mDefaultImageResource = NONE;
    private Drawable mDefaultDrawable;
    private Transformation<Bitmap>[] bitmapTransformation;
    private int mErrorImageResource = NONE;
    private Drawable mErrorDrawable;
    private OnErrorListener mOnErrorListener;

    public String getImageUri() {
        return mUrl;
    }

    public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RemoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RemoteImageView(Context context) {
        super(context);
        init();
    }

    private void init() {
        // buildingOptions();
    }

    /**
     * 加载网络图片
     *
     * @param url eg. http://yourwebsite.com/abz.jpg
     */
    public void setImageUri(final String url) {
        if (isActivityDestroyed())
            return;
        if (TextUtils.isEmpty(url)) {
            if (mDefaultDrawable != null) {
                setImageDrawable(mDefaultDrawable);
            } else if (mDefaultImageResource != NONE) {
                setImageResource(mDefaultImageResource);
            }
            return;
        }
        mUrl = url;
        RequestBuilder<Drawable> request = Glide.with(getContext()).asDrawable().load(url);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(mDefaultImageResource)
                .error(mErrorImageResource)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        if(bitmapTransformation!=null)
            options.transforms(bitmapTransformation);
        request.apply(options)
                .transition(new DrawableTransitionOptions().crossFade());
        request.listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                if (mOnErrorListener != null) {
                    mOnErrorListener.onLoadError(url);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        });
        request.into(this);
    }
    
    public interface OnErrorListener{
        public void onLoadError(String url);
    }
    public void setOnErrorListener(OnErrorListener onErrorListener){
        this.mOnErrorListener =onErrorListener;
    }
    @SuppressLint("NewApi")
    private boolean isActivityDestroyed() {
        Context context = getContext();
        if (context instanceof Activity) {
            return ((Activity) context).isFinishing();
        }
        return false;
    }

    public void setDefaultImageDrawable(Drawable defaultDrawable) {
        this.mDefaultDrawable = defaultDrawable;
    }

    @SuppressWarnings("deprecation")
    public void setDefaultImageBitmap(Bitmap defaultImageBitmap) {
        this.mDefaultDrawable = new BitmapDrawable(defaultImageBitmap);
    }

    /**
     * 设置默认图片资源id
     *
     * @param resId
     */
    public void setDefaultImageResource(int resId) {
        mDefaultImageResource = resId;
    }

    public void setImageUri(int defResId, String url) {
        if (mUrl == null || !this.mUrl.equals(url)) {
            setDefaultImageResource(defResId);
            setImageUri(url);
        }
    }

    public void setImageUri(Drawable defaultDrawable, String url) {
        if (mUrl == null || !this.mUrl.equals(url)) {
            setDefaultImageDrawable(defaultDrawable);
            setImageUri(url);
        }
    }

    public int getErrorImageResource() {
        return mErrorImageResource;
    }


    public void setErrorImageResource(int errorImageResource) {
        this.mErrorImageResource = errorImageResource;
    }

    public Drawable getErrorDrawable() {
        return mErrorDrawable;
    }

    public void setErrorDrawable(Drawable errorDrawable) {
        this.mErrorDrawable = errorDrawable;
    }

    public void setBitmapTransformation(Transformation<Bitmap>... tf) {
        this.bitmapTransformation = tf;
    }

    public void removeUrl(){
        mUrl = null;
    }
}
