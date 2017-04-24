package com.thinkeract.tka.ui.mine;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.DBUtils;
import com.thinkeract.tka.data.api.entity.AddressItem;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.home.datasource.UserAddressDataSource;
import com.thinkeract.tka.ui.mine.adapter.AddressListAdapter;
import com.thinkeract.tka.widget.MVCSwipeRefreshHelper;

import java.util.List;

/**
 * Created by ymh on 2017/4/9 18:53
 * e-mail:minhengyan@gmail.com
 */

public class ShippingAddressListActivity extends AppBarActivity {
    private RecyclerView shippingAddressRv;
    private Button addNewAddressBtn;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MVCSwipeRefreshHelper<List<AddressItem>> mvcHelper;
    private AddressListAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_shippiing_address_list;
    }

    @Override
    protected void initView() {
        setTitle(R.string.shipping_address);
        shippingAddressRv = (RecyclerView) findViewById(R.id.shippingAddressRv);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        addNewAddressBtn = (Button) findViewById(R.id.addNewAddressBtn);

        addNewAddressBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mAdapter = new AddressListAdapter(this);
        shippingAddressRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mvcHelper = new MVCSwipeRefreshHelper<List<AddressItem>>(swipeRefreshLayout);
        // 设置数据源
        mvcHelper.setDataSource(new UserAddressDataSource());
        // 设置适配器
        mvcHelper.setAdapter(mAdapter);
        // 加载数据
        mvcHelper.refresh();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.addNewAddressBtn){
            UpdateAddressActivity.launch(this,null);
        }
    }

    @Override
    protected void onDestroy() {
        DBUtils.insertAddressList(DBUtils.convertToGDAddressItemList(mAdapter.getData()));
        super.onDestroy();
    }
}
