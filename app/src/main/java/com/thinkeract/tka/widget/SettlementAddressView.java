package com.thinkeract.tka.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.DBUtils;
import com.thinkeract.tka.data.db.greendao.GDAddress;
import com.thinkeract.tka.ui.mall.adapter.AddressSimpleAdapter;

import java.util.List;

/**
 * Created by minHeng on 2017/4/6 20:50.
 * mail:minhengyan@gmail.com
 */

public class SettlementAddressView extends LinearLayout{
    private TextView contactNameTv;
    private TextView phoneNumTv;
    private TextView addressTv;
    private RelativeLayout addAddressLayout;
    private RecyclerView addressRv;
    private OnAddressClickListener onAddressClickListener;
    private RelativeLayout backAddressLayout;
    private AddressSimpleAdapter mAdapter;

    public SettlementAddressView(Context context) {
        super(context);
        init(context);
    }

    public SettlementAddressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SettlementAddressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        View.inflate(context, R.layout.layout_settlement_address,this);
        initializeView();
    }

    private void initializeView() {
        contactNameTv = (TextView) findViewById(R.id.contactNameTv);
        phoneNumTv = (TextView) findViewById(R.id.phoneNumTv);
        addressTv = (TextView) findViewById(R.id.addressTv);
        addAddressLayout = (RelativeLayout) findViewById(R.id.addAddressLayout);
        addressRv = (RecyclerView) findViewById(R.id.addressRv);
        backAddressLayout = (RelativeLayout) findViewById(R.id.backAddressLayout);

        backAddressLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAddressClickListener != null)
                    onAddressClickListener.onBackAddressClick();
            }
        });

        addAddressLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAddressClickListener != null)
                    onAddressClickListener.onAddMoreClick();
            }
        });
    }

    public void setData(GDAddress defAddress, List<GDAddress> addressList, Activity context){
        if(mAdapter == null) {
            mAdapter = new AddressSimpleAdapter(context);
            addressRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            addressRv.setAdapter(mAdapter);
        }
        mAdapter.setItemList(DBUtils.convertToAddressItemList(addressList));
    }

    public void setOnAddressClickListener(OnAddressClickListener onAddressClickListener){
        this.onAddressClickListener = onAddressClickListener;
    }

    public interface OnAddressClickListener{
        void onBackAddressClick();
        void onAddMoreClick();
    }

}
