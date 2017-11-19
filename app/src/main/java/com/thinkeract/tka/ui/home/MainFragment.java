package com.thinkeract.tka.ui.home;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.StatusBarUtil;
import com.thinkeract.tka.data.api.response.HomePageData;
import com.thinkeract.tka.ui.BaseFragment;
import com.thinkeract.tka.ui.home.adapter.HomeAdapter;
import com.thinkeract.tka.ui.home.contract.HomeDataContract;
import com.thinkeract.tka.ui.home.presenter.HomeDataPresenter;
import com.thinkeract.tka.widget.ContentViewHolder;

/**
 * Created by minHeng on 2017/3/14 16:53.
 * mail:minhengyan@gmail.com
 */

public class MainFragment extends BaseFragment implements HomeDataContract.View {
    private ContentViewHolder mContentViewHolder;
    private RecyclerView healthKnowledgeRv;
    private Toolbar mToolbar;
    private ImageView mParallax;
    private SmartRefreshLayout mRefreshLayout;
    private HomeAdapter mAdapter;
    private HomeDataPresenter mPresenter;
    private int mOffset;
    private int mScrollY;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        initView(contentView);
        StatusBarUtil.darkMode(getActivity());
        StatusBarUtil.setPaddingSmart(getActivity(), healthKnowledgeRv);
        StatusBarUtil.setPaddingSmart(getActivity(), mToolbar);
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
                mParallax.setTranslationY(mOffset - mScrollY);
                mToolbar.setAlpha(1 - Math.min(percent, 1));
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
                mParallax.setTranslationY(mOffset - mScrollY);
                mToolbar.setAlpha(1 - Math.min(percent, 1));
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getHomeData(false);
            }
        });

        healthKnowledgeRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int height = DensityUtil.dp2px(400);// 滑动开始变色的高,真实项目中此高度是由广告轮播或其他首页view高度决定
            private int overallXScroll = 0;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Logger.i("qwert","h = "+height+","+"dx = "+dx+",scrollY = "+dy);
                overallXScroll = overallXScroll + dy;// 累加y值 解决滑动一半y值为0
                mScrollY = overallXScroll;
                if (overallXScroll <= 0) {   //设置标题的背景颜色
                    mToolbar.setBackgroundColor(Color.argb((int) 0, 211, 133, 231));
                } else if (overallXScroll > 0 && overallXScroll <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    float scale = (float) overallXScroll / height;
                    float alpha = (255 * scale);
                    mToolbar.setBackgroundColor(Color.argb((int) alpha, 211, 133, 231));
                } else {
                    mToolbar.setBackgroundColor(Color.argb((int) 255, 211, 133, 231));
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        mToolbar.setBackgroundColor(0);
        mContentViewHolder.addRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getHomeData(false);
            }
        });
    }

    private void initView(View contentView) {
        mToolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        healthKnowledgeRv = (RecyclerView) contentView.findViewById(R.id.healthKnowledgeRv);
        mParallax = (ImageView) contentView.findViewById(R.id.parallax);
        mRefreshLayout = (SmartRefreshLayout) contentView.findViewById(R.id.refreshLayout);
        mContentViewHolder = (ContentViewHolder) contentView.findViewById(R.id.contentViewHolder);
    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        mPresenter = new HomeDataPresenter(this);
        mAdapter = new HomeAdapter(getActivity());
        healthKnowledgeRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        healthKnowledgeRv.setAdapter(mAdapter);
        mPresenter.getHomeData(true);
    }

    @Override
    public void onRefreshData() {
        super.onRefreshData();
        mPresenter.getHomeData(false);
    }

    @Override
    public void showError(String message) {
        mContentViewHolder.showRetry();
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void showSuccess(HomePageData response) {
        if(response !=null&&response.getScore()!=null&&response.getScore().size()>0) {
            mContentViewHolder.showContent();
            mRefreshLayout.finishRefresh();
            mAdapter.setData(response);
        }else{
            mContentViewHolder.showRetry();
        }
    }
}
