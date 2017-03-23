package com.thinkeract.tka.ui.home;

import android.os.Build;
import android.view.View;

import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.ui.BaseFragment;

/**
 * Created by minHeng on 2017/3/14 16:54.
 * mail:minhengyan@gmail.com
 */

public class MyFragment extends BaseFragment {
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            contentView.findViewById(R.id.actionBar).setPadding(0, ViewUtils.getStatuBarHeight(getContext()), 0, 0);
        }
    }
}
