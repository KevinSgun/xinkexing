package com.thinkeract.tka.widget;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.thinkeract.tka.Constants;
import com.youth.banner.loader.ImageLoader;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context.getApplicationContext())
                .asBitmap()
                .load(path)
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(Constants.ImageDefResId.DEF_SQUARE_PIC_NORMAL))
                .transition(new BitmapTransitionOptions().crossFade())
                .into(imageView);
    }


}
