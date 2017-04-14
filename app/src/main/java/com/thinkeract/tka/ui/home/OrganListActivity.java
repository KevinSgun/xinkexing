package com.thinkeract.tka.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.entity.SecondReportItem;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.home.adapter.OrganListAdapter;
import com.thinkeract.tka.ui.home.contract.SecondDataContract;
import com.thinkeract.tka.ui.home.presenter.SecondDataPresenter;

import java.util.List;

/**
 * 器脏列表
 * Created by ymh on 2017/4/9 18:14
 * e-mail:minhengyan@gmail.com
 */

public class OrganListActivity extends AppBarActivity implements SecondDataContract.View {
    private RecyclerView organRv;
    private OrganListAdapter mAdapter;
    private String mOrganId;
    private SecondDataPresenter secondDataPresenter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_organ_list;
    }

    @Override
    protected void initView() {
        setTitle(R.string.organ_burden);
        mOrganId = getIntent().getStringExtra(Constants.ActivityExtra.BUSI_ID);
        organRv = (RecyclerView) findViewById(R.id.organRv);

        mAdapter = new OrganListAdapter(this);
        organRv.setLayoutManager(new GridLayoutManager(getContext(),4));
        organRv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        secondDataPresenter = new SecondDataPresenter(this);
        secondDataPresenter.getSecondData(mOrganId);
    }

    public static void launch(Activity activity, String organId) {
        Intent intent = new Intent(activity,OrganListActivity.class);
        intent.putExtra(Constants.ActivityExtra.BUSI_ID,organId);
        activity.startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN,activity);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSuccess(List<SecondReportItem> response) {
        mAdapter.setItemList(response);
    }
}
