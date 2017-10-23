package com.thinkeract.tka.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkeract.tka.R;

/**
 * Created by minHeng on 2017/4/6 20:58.
 * mail:minhengyan@gmail.com
 */

public class SettlementDetailView extends LinearLayout{
    private TextView amountTv;
    private TextView goodsPriceTv;
    private TextView freightTv;
    private RelativeLayout amountLayout;
    private OnDetailClickListener onDetailClickListener;

    public SettlementDetailView(Context context) {
        super(context);
        init(context);
    }

    public SettlementDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SettlementDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        View.inflate(context, R.layout.layout_settlement_detail,this);
        initializeView();
    }

    private void initializeView() {
        amountLayout = (RelativeLayout) findViewById(R.id.amountLayout);
        amountTv = (TextView) findViewById(R.id.amountDetailTv);
        goodsPriceTv = (TextView) findViewById(R.id.goodsPriceTv);
        freightTv = (TextView) findViewById(R.id.freightTv);

        amountLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDetailClickListener != null)
                    onDetailClickListener.onDetailClick();
            }
        });
    }

    public void setData(double amount,double goodsPrice,double freight){
        amountTv.setText(String.format(getResources().getString(R.string.rmb),amount));
        goodsPriceTv.setText(String.format(getResources().getString(R.string.rmb),goodsPrice));
        freightTv.setText(String.format(getResources().getString(R.string.rmb),freight));
    }

    public void setOnDetailClickListener(OnDetailClickListener onDetailClickListener){
        this.onDetailClickListener = onDetailClickListener;
    }

    public interface OnDetailClickListener{
        void onDetailClick();
    }
}
