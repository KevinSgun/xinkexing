package com.thinkeract.tka.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.nio.charset.Charset;
import java.security.MessageDigest;

public class CenterClipRoundImageProcessor extends BitmapTransformation {

	public static final float POST_IMG_RATE = 173 / (float) 135;
	private static final String ID = "com.fans.framework.image.CenterClipRoundImageProcessor";
	private static String STRING_CHARSET_NAME = "UTF-8";
	private static Charset CHARSET = Charset.forName(STRING_CHARSET_NAME);
	private static final byte[] ID_BYTES = ID.getBytes(CHARSET);
	private float roundPx = 22;
	private float rate = 1f;// rate=w/h;
	private boolean justTop;

	public CenterClipRoundImageProcessor(Context context, float roundPx, float rate) {
		super(context);
		this.roundPx = roundPx;
		this.rate = rate;
	}

	public CenterClipRoundImageProcessor(Context context, float roundPx, float rate, boolean justTop) {
		super(context);
		this.justTop = justTop;
		this.roundPx = roundPx;
		this.rate = rate;
	}

	public void setRadiusJustTop(){
		justTop = true;
	}

	public Bitmap process(Bitmap bitmap) {

		float expectWidth = (bitmap.getHeight() * rate);
		float expectHeight = (bitmap.getWidth() / rate);
		float wRate = bitmap.getWidth() / expectWidth;
		float hRate = bitmap.getHeight() / expectHeight;
		int width = 0;
		int height = 0;
		if (wRate < hRate) {
			width = bitmap.getWidth();
			height = (int) (bitmap.getWidth() / rate);
		} else {
			height = bitmap.getHeight();
			width = (int) (height * rate);
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
		Path path = new Path();
		final int color = 0xff424242;
		final Paint paint = new Paint();

		int hPadding = 0;
		int vPadding = 0;
		if (height == bitmap.getHeight()) {
			hPadding = (bitmap.getWidth() - width) / 2;
		} else {
			vPadding = (bitmap.getHeight() - height) / 2;
		}

		final Rect src = new Rect(hPadding, vPadding, hPadding + width, vPadding + height);

		final RectF dest = new RectF(0, 0, width, height);
		float[] radius = {roundPx,roundPx,roundPx,roundPx,0,0,0,0,0};
		if (roundPx > 0) {
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			if(justTop) {
				path.addRoundRect(dest, radius, Path.Direction.CW);
				canvas.drawPath(path, paint);
			}else{
				canvas.drawRoundRect(dest,roundPx,roundPx,paint);
			}
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		}
		canvas.drawBitmap(bitmap, src, dest, paint);

		return output;
	}

	public void setRoundPx(float roundPx) {
		this.roundPx = roundPx;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof CenterClipRoundImageProcessor;
	}

	@Override
	public int hashCode() {
		return ID.hashCode();
	}

	@Override
	protected Bitmap transform(BitmapPool arg0, Bitmap arg1, int arg2, int arg3) {
		return process(arg1);
	}

	public String getId() {
		return "CenterClipRoundImageProcessor(radius=" + rate + ", roundPx=" + roundPx + ")";
	}

	@Override
	public void updateDiskCacheKey(MessageDigest messageDigest) {
		messageDigest.update(getId().getBytes(CHARSET));
	}
}
