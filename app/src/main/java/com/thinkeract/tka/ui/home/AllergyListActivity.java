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

public class AllergyListActivity extends AppBarActivity implements SecondDataContract.View {
    private RecyclerView allergyRv;
    private OrganListAdapter mAdapter;
    private String mAllergyId;
    private SecondDataPresenter secondDataPresenter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_allergy_list;
    }

    @Override
    protected void initView() {
        setTitle(R.string.allergy_view);
        mAllergyId = getIntent().getStringExtra(Constants.ActivityExtra.BUSI_ID);
        allergyRv = (RecyclerView) findViewById(R.id.allergyRv);

        mAdapter = new OrganListAdapter(this);
        allergyRv.setLayoutManager(new GridLayoutManager(getContext(),4));
        allergyRv.addItemDecoration(new GridItemDecoration(getContext(),4));
        allergyRv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        secondDataPresenter = new SecondDataPresenter(this);
        secondDataPresenter.getSecondData(mAllergyId);
    }

    public static void launch(Activity activity, String allergyId) {
        Intent intent = new Intent(activity,AllergyListActivity.class);
        intent.putExtra(Constants.ActivityExtra.BUSI_ID,allergyId);
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
