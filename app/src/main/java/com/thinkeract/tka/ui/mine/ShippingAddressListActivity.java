package com.thinkeract.tka.ui.mine;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.thinkeract.tka.Events;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.DBUtils;
import com.thinkeract.tka.data.api.entity.AddressItem;
import com.thinkeract.tka.data.api.request.UpdateAddressBody;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.mine.adapter.AddressListAdapter;
import com.thinkeract.tka.ui.mine.contract.AddressStuffContract;
import com.thinkeract.tka.ui.mine.presenter.AddressStuffPresenter;
import com.thinkeract.tka.widget.CommonDialog;
import com.zitech.framework.utils.ToastMaster;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by ymh on 2017/4/9 18:53
 * e-mail:minhengyan@gmail.com
 */

public class ShippingAddressListActivity extends AppBarActivity implements AddressStuffContract.View {
    private RecyclerView shippingAddressRv;
    private Button addNewAddressBtn;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AddressListAdapter mAdapter;
    private AddressStuffPresenter mPresenter;
    private int mCurrentPosition;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_shippiing_address_list;
    }

    @Subscribe
    public void onAddressChangeEvent(Events.AddressChangeEvent data){
        mPresenter.getAddressList();
    }

    @Override
    protected void initView() {
        setTitle(R.string.shipping_address);
        shippingAddressRv = (RecyclerView) findViewById(R.id.shippingAddressRv);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        addNewAddressBtn = (Button) findViewById(R.id.addNewAddressBtn);

        addNewAddressBtn.setOnClickListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.gplus_color_1, R.color.gplus_color_2, R.color.gplus_color_3, R.color.gplus_color_4);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getAddressList();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new AddressStuffPresenter(this);
        mAdapter = new AddressListAdapter(this);
        mAdapter.setOnItemStuffClickListener(new AddressListAdapter.OnItemStuffClickListener() {
            @Override
            public void onSetDefClick(int position) {
                if(mAdapter.getData().get(position).getStatus()!=1) {
                    mCurrentPosition = position;
                    setDefAddress();
                }
            }

            @Override
            public void onEditClick(int position) {
                UpdateAddressActivity.launch(ShippingAddressListActivity.this,mAdapter.getData().get(position));
            }

            @Override
            public void onDeleteClick(int position) {
                mCurrentPosition = position;
                showDeleteTips();
            }
        });
        shippingAddressRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        shippingAddressRv.setAdapter(mAdapter);

        mPresenter.getAddressList();
    }

    private void setDefAddress() {
        UpdateAddressBody body = new UpdateAddressBody(mAdapter.getData().get(mCurrentPosition));
        body.setStatus("1");
        mPresenter.setDefAddress(body);
    }

    private void showDeleteTips() {
        CommonDialog commonDialog = new CommonDialog(this,"删除后将无法恢复，确定删除吗");
        commonDialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
            @Override
            public void onClick(Dialog dialog) {
                deleteItem();
            }
        });
        commonDialog.show();
    }

    private void deleteItem() {
        AddressItem item = mAdapter.getData().get(mCurrentPosition);
         mPresenter.deleteAddress(String.valueOf(item.getId()));
    }

    @Override
    public void showError(String message) {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showSuccess(List<AddressItem> addressItems, int stuffType, String message) {
        swipeRefreshLayout.setRefreshing(false);
        if(stuffType == 1) {
            mAdapter.setItemList(addressItems);
        }else if(stuffType == 2){
            ToastMaster.shortToast(message);
            mPresenter.getAddressList();
        }else if(stuffType == 3){
            ToastMaster.shortToast(message);
            mAdapter.notifyItemRemoved(mCurrentPosition);
            mAdapter.getData().remove(mCurrentPosition);
        }
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
