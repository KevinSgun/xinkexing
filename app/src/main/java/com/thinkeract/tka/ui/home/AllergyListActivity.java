package com.thinkeract.tka.ui.home;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.home.adapter.AllergyListAdapter;

/**
 * 过敏源列表
 * Created by ymh on 2017/4/9 18:14
 * e-mail:minhengyan@gmail.com
 */

public class AllergyListActivity extends AppBarActivity {
    private RecyclerView allergyRv;
    private AllergyListAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_allergy_list;
    }

    @Override
    protected void initView() {
        allergyRv = (RecyclerView) findViewById(R.id.allergyRv);

        mAdapter = new AllergyListAdapter(this);
        allergyRv.setLayoutManager(new GridLayoutManager(getContext(),4));
        allergyRv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

}
