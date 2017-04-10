package com.thinkeract.tka.ui.home;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.home.adapter.OrganListAdapter;

/**
 * 器脏列表
 * Created by ymh on 2017/4/9 18:14
 * e-mail:minhengyan@gmail.com
 */

public class OrganListActivity extends AppBarActivity {
    private RecyclerView organRv;
    private OrganListAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_organ_list;
    }

    @Override
    protected void initView() {
        organRv = (RecyclerView) findViewById(R.id.organRv);

        mAdapter = new OrganListAdapter(this);
        organRv.setLayoutManager(new GridLayoutManager(getContext(),4));
        organRv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

}
