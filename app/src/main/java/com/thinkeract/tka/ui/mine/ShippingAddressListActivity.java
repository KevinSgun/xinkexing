package com.thinkeract.tka.ui.mine;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.thinkeract.tka.R;
import com.thinkeract.tka.ui.AppBarActivity;

/**
 * Created by ymh on 2017/4/9 18:53
 * e-mail:minhengyan@gmail.com
 */

public class ShippingAddressListActivity extends AppBarActivity {
    private RecyclerView shippingAddressRv;
    private Button addNewAddressBtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_shippiing_address_list;
    }

    @Override
    protected void initView() {
        setTitle(R.string.shipping_address);
        shippingAddressRv = (RecyclerView) findViewById(R.id.shippingAddressRv);
        addNewAddressBtn = (Button) findViewById(R.id.addNewAddressBtn);
    }

    @Override
    protected void initData() {

    }

}
