package com.thinkeract.tka.widget;

import android.content.Context;
import android.support.annotation.ColorInt;

import com.thinkeract.tka.R;

/**
 * Created by minHeng on 2017/4/17 10:05.
 * mail:minhengyan@gmail.com
 */

public class GridItemDecoration extends DividerItemDecoration {

    private int mColumn = 2;

    public GridItemDecoration(Context context) {
        this(context,2);
    }

    public GridItemDecoration(Context context, int column) {
        this(context, 1f, context.getResources().getColor(R.color.divider_color));
        mColumn = column;
    }

    public GridItemDecoration(Context context, float lineWidthDp, @ColorInt int colorRGB) {
        super(context, lineWidthDp, colorRGB);
    }

    @Override
    public boolean[] getItemSidesIsHaveOffsets(int itemPosition) {
        //顺序:left, top, right, bottom
        boolean[] booleans = {false, false, false, false};
        if(mColumn == 2)
            switch (itemPosition % 2) {
                case 0:
                    //每一行前两个显示right和bottom
                    booleans[2] = true;
                    booleans[3] = true;
                    break;
                case 1:
                    //最后一个只显示bottom
                    booleans[3] = true;
                    break;
                default:
                    break;
            }
        else if (mColumn == 3)
            switch (itemPosition % 3) {
                case 0:
                case 1:
                    //每一行前两个显示right和bottom
                    booleans[2] = true;
                    booleans[3] = true;
                    break;
                case 2:
                    //最后一个只显示bottom
                    booleans[3] = true;
                    break;
                default:
                    break;
            }
        else if (mColumn == 4)
            switch (itemPosition % 4) {
                case 0:
                case 1:
                case 2:
                    //每一行前两个显示right和bottom
                    booleans[2] = true;
                    booleans[3] = true;
                    break;
                case 3:
                    //最后一个只显示bottom
                    booleans[3] = true;
                    break;
                default:
                    break;
            }
        else if (mColumn == 5)
            switch (itemPosition % 5) {
                case 0:
                case 1:
                case 2:
                case 3:
                    //每一行前两个显示right和bottom
                    booleans[2] = true;
                    booleans[3] = true;
                    break;
                case 4:
                    //最后一个只显示bottom
                    booleans[3] = true;
                    break;
                default:
                    break;
            }

        return booleans;
    }
}
