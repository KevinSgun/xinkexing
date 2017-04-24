package com.thinkeract.tka.widget;

import android.content.Context;
import android.support.annotation.ColorInt;

import com.thinkeract.tka.R;

/**
 * Created by minHeng on 2017/4/17 10:37.
 * mail:minhengyan@gmail.com
 */

public class LinearItemDecoration extends DividerItemDecoration{

    public LinearItemDecoration(Context context) {
        this(context, 1f, context.getResources().getColor(R.color.divider_color));
    }

    public LinearItemDecoration(Context context, float lineWidthDp, @ColorInt int colorRGB) {
        super(context, lineWidthDp, colorRGB);
    }

    @Override
    public boolean[] getItemSidesIsHaveOffsets(int itemPosition) {
        //顺时针顺序:left, top, right, bottom
        return new boolean[]{false, false, false, true};
    }
}
