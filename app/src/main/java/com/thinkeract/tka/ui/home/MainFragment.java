package com.thinkeract.tka.ui.home;

import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.response.HomePageData;
import com.thinkeract.tka.ui.BaseFragment;
import com.thinkeract.tka.ui.home.adapter.NewsListAdapter;
import com.thinkeract.tka.ui.home.contract.HomeDataContract;
import com.thinkeract.tka.ui.home.presenter.HomeDataPresenter;

import java.util.List;

/**
 * Created by minHeng on 2017/3/14 16:53.
 * mail:minhengyan@gmail.com
 */

public class MainFragment extends BaseFragment implements HomeDataContract.View {
//    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView healthKnowledgeRv;
    private NewsListAdapter mAdapter;
    private HomeDataPresenter mPresenter;
    private TextView leftCurrentScore;
    private TextView leftTotalScore;
    private TextView middleCurrentScore;
    private TextView middleTotalScore;
    private TextView rightCurrentScore;
    private TextView rightTotalScore;
    private TextView leftItemName;
    private TextView middleItemName;
    private TextView rightItemName;
    private String mOrganId;
    private String mAllergyId;
    private String mSourroundingsId;
//    private MVCSwipeRefreshHelper<List<NewsItem>> mvcHelper;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        Toolbar toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        LinearLayout coverLayout = (LinearLayout) contentView.findViewById(R.id.coverLayout);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            toolbar.setPadding(0, ViewUtils.getStatuBarHeight(getContext()), 0, 0);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams params = toolbar.getLayoutParams();
            params.height = com.zitech.framework.utils.ViewUtils.dip2px(getContext(),45);
            coverLayout.setFitsSystemWindows(true);
        }
        healthKnowledgeRv = (RecyclerView) contentView.findViewById(R.id.healthKnowledgeRv);
        leftCurrentScore = (TextView) contentView.findViewById(R.id.leftCurrentScore);
        leftTotalScore = (TextView) contentView.findViewById(R.id.leftTotalScore);
        RelativeLayout leftItemLayout = (RelativeLayout) contentView.findViewById(R.id.leftItemLayout);
        middleCurrentScore = (TextView) contentView.findViewById(R.id.middleCurrentScore);
        middleTotalScore = (TextView) contentView.findViewById(R.id.middleTotalScore);
        RelativeLayout middleItemLayout = (RelativeLayout) contentView.findViewById(R.id.middleItemLayout);
        rightCurrentScore = (TextView) contentView.findViewById(R.id.rightCurrentScore);
        rightTotalScore = (TextView) contentView.findViewById(R.id.rightTotalScore);
        RelativeLayout rightItemLayout = (RelativeLayout) contentView.findViewById(R.id.rightItemLayout);

        leftItemName = (TextView) contentView.findViewById(R.id.leftItemName);
        middleItemName = (TextView) contentView.findViewById(R.id.middleItemName);
        rightItemName = (TextView) contentView.findViewById(R.id.rightItemName);

        leftItemLayout.setOnClickListener(this);
        middleItemLayout.setOnClickListener(this);
        rightItemLayout.setOnClickListener(this);
    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        mPresenter = new HomeDataPresenter(this);
        mAdapter = new NewsListAdapter(getActivity());
        healthKnowledgeRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        healthKnowledgeRv.setAdapter(mAdapter);
        mPresenter.getHomeData(true);
    }

    @Override
    public void onRefreshData() {
        super.onRefreshData();
        mPresenter.getHomeData(false);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.leftItemLayout:
                OrganListActivity.launch(getActivity(),mOrganId);
                break;
            case R.id.middleItemLayout:
                AllergyListActivity.launch(getActivity(),mAllergyId);
                break;
            case R.id.rightItemLayout:
                SurroundingsListActivity.launch(getActivity(),mSourroundingsId);
                break;
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSuccess(HomePageData response) {
       refreshUI(response);
    }

    private void refreshUI(HomePageData response) {
        List<HomePageData.ScoreBean> scoreBeanList = response.getScore();
        mAdapter.setItemList(response.getNews());
        for(int i=0,size = scoreBeanList.size();i<size;i++){
            HomePageData.ScoreBean scoreBean = scoreBeanList.get(i);
            if(i == 0){
                leftTotalScore.setText("/"+String.valueOf(scoreBean.getMaxScore()));
                leftCurrentScore.setText(String.valueOf(scoreBean.getScore()));
                leftItemName.setText(scoreBean.getName());
                mOrganId = String.valueOf(scoreBean.getId());
            }else if(i == 1){
                middleTotalScore.setText("/"+String.valueOf(scoreBean.getMaxScore()));
                middleCurrentScore.setText(String.valueOf(scoreBean.getScore()));
                middleItemName.setText(scoreBean.getName());
                mAllergyId = String.valueOf(scoreBean.getId());
            }else {
                rightTotalScore.setText("/"+String.valueOf(scoreBean.getMaxScore()));
                rightCurrentScore.setText(String.valueOf(scoreBean.getScore()));
                rightItemName.setText(scoreBean.getName());
                mSourroundingsId = String.valueOf(scoreBean.getId());
            }
        }

    }

}
