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
import com.thinkeract.tka.widget.GridItemDecoration;

import java.util.List;

/**
 * 过敏源列表
 * Created by ymh on 2017/4/9 18:14
 * e-mail:minhengyan@gmail.com
 */

public class SurroundingsListActivity extends AppBarActivity implements SecondDataContract.View {
    private OrganListAdapter mAdapter;
    private RecyclerView surroundingsRv;
    private String mSurroundingsId;
    private SecondDataPresenter secondDataPresenter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_surroundings_list;
    }

    @Override
    protected void initView() {
        setTitle(R.string.surroundings_burden);
        mSurroundingsId = getIntent().getStringExtra(Constants.ActivityExtra.BUSI_ID);
        surroundingsRv = (RecyclerView) findViewById(R.id.surroundingsRv);

        mAdapter = new OrganListAdapter(this);
        surroundingsRv.setLayoutManager(new GridLayoutManager(getContext(),4));
        surroundingsRv.addItemDecoration(new GridItemDecoration(getContext(),4));
        surroundingsRv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        secondDataPresenter = new SecondDataPresenter(this);
        secondDataPresenter.getSecondData(mSurroundingsId);
    }

    public static void launch(Activity activity, String surroundingsId) {
        Intent intent = new Intent(activity,SurroundingsListActivity.class);
        intent.putExtra(Constants.ActivityExtra.BUSI_ID,surroundingsId);
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
