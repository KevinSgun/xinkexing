package com.thinkeract.tka.ui.mine;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.OrderItem;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.mine.adapter.OrderListAdapter;
import com.thinkeract.tka.ui.mine.datasource.OrderListDataSource;
import com.thinkeract.tka.widget.MVCSwipeRefreshHelper;

import java.util.List;

/**
 * Created by ymh on 2017/4/8 22:18
 * e-mail:minhengyan@gmail.com
 */

public class MyOrderListActivity extends AppBarActivity{

    private RecyclerView orderListRv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MVCSwipeRefreshHelper<List<OrderItem>> mvcHelper;
    private boolean isNotFirstIn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_order_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isNotFirstIn&&mvcHelper!=null){
            mvcHelper.refresh();
        }else{
            isNotFirstIn = true;
        }
    }

    @Override
    protected void initView() {
        setTitle(R.string.my_order);
        orderListRv = (RecyclerView) findViewById(R.id.orderListRv);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeResources(R.color.gplus_color_1, R.color.gplus_color_2, R.color.gplus_color_3, R.color.gplus_color_4);
    }

    @Override
    protected void initData() {
        OrderListAdapter mAdapter = new OrderListAdapter(this);
        orderListRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mvcHelper = new MVCSwipeRefreshHelper<List<OrderItem>>(swipeRefreshLayout);
//        ContentViewHolder contentViewHolder = (ContentViewHolder) mvcHelper.getLoadViewFactory().getContentViewHolder();
//        contentViewHolder.setDefaultEmptyImage(R.mipmap.ic_no_data_def);
//        contentViewHolder.setNoDataPrompt("暂无订单信息");
        // 设置数据源
        mvcHelper.setDataSource(new OrderListDataSource());
        // 设置适配器
        mvcHelper.setAdapter(mAdapter);
        // 加载数据
        mvcHelper.refresh();

    }

}
