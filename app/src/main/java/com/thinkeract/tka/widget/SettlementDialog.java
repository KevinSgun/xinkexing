package com.thinkeract.tka.widget;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.StockSimple;
import com.thinkeract.tka.data.db.greendao.GDGoodsItem;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.ValidDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minHeng on 2017/4/6 18:35.
 * mail:minhengyan@gmail.com
 */

public class SettlementDialog extends ValidDialog implements View.OnClickListener {
    private Activity mContext;
    private Button commitOrderBtn;
    private TextView contactNameTv;
    private TextView phoneNumTv;
    private TextView addressTv;
    private TextView amountTv;
    private RelativeLayout toAddressLayout;
    private RelativeLayout toDetailLayout;
    private SettlementAddressView addressLayout;
    private SettlementDetailView detailLayout;
    private ViewAnimator contentAnimator;
    private OnSettlementClickListener onSettlementClickListener;
    private float totalAmount;
    private float goodsAmount;
    private float freight;
    private String stockString;
    private TextView goodsPriceTv;
    private TextView freightTv;
    private TextView amountDetailTv;

    public SettlementDialog(Activity context) {
        super(context, R.style.BottomPushDialog);
        mContext = context;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_settlement);

        toAddressLayout = (RelativeLayout) findViewById(R.id.toAddressLayout);
        contactNameTv = (TextView) findViewById(R.id.contactNameTv);
        phoneNumTv = (TextView) findViewById(R.id.phoneNumTv);
        addressTv = (TextView) findViewById(R.id.addressTv);

        toDetailLayout = (RelativeLayout) findViewById(R.id.toDetailLayout);
        amountTv = (TextView) findViewById(R.id.amountTv);
        commitOrderBtn = (Button) findViewById(R.id.commitOrderBtn);

        goodsPriceTv = (TextView) findViewById(R.id.goodsPriceTv);
        freightTv = (TextView) findViewById(R.id.freightTv);
        amountDetailTv = (TextView) findViewById(R.id.amountDetailTv);

        addressLayout = (SettlementAddressView) findViewById(R.id.addressLayout);
        detailLayout = (SettlementDetailView) findViewById(R.id.detailLayout);

        contentAnimator = (ViewAnimator) findViewById(R.id.contentAnimator);

        toAddressLayout.setOnClickListener(this);
        toDetailLayout.setOnClickListener(this);
        commitOrderBtn.setOnClickListener(this);
        addressLayout.setOnAddressClickListener(new SettlementAddressView.OnAddressClickListener() {
            @Override
            public void onBackAddressClick() {
                contentAnimator.setDisplayedChild(0);
            }

            @Override
            public void onAddMoreClick() {

            }
        });
        detailLayout.setOnDetailClickListener(new SettlementDetailView.OnDetailClickListener() {
            @Override
            public void onDetailClick() {
                contentAnimator.setDisplayedChild(0);
            }
        });


        Window dialogWindow = getWindow();
        WindowManager.LayoutParams dialogParams;
        if (dialogWindow != null) {
            dialogParams = dialogWindow.getAttributes();
            dialogParams.gravity = Gravity.BOTTOM;
            dialogParams.height = ViewUtils.getDimenPx(R.dimen.h808);
            dialogParams.width = ViewUtils.getDimenPx(R.dimen.w720);
            dialogWindow.setAttributes(dialogParams);
        }
    }

    public void setData(List<GDGoodsItem> goodsItemList, float totalAmount, float goodsAmount, float freight) {
        List<StockSimple> stockSimples = new ArrayList<>();
        for (GDGoodsItem goodsItem : goodsItemList) {
            if (goodsItem.getGoodsId() != 0) {
                StockSimple simple = new StockSimple();
                simple.setId(String.valueOf(goodsItem.getGoodsId()));
                simple.setPrice(String.valueOf(goodsItem.getPrice()));
                simple.setQuantity(String.valueOf(goodsItem.getGoodsCount()));
                simple.setSid(String.valueOf(goodsItem.getSid()));
                stockSimples.add(simple);
            }
        }
        Gson gson = new Gson();
        stockString = gson.toJson(stockSimples, new TypeToken<List<StockSimple>>() {
        }.getType());
        this.totalAmount = totalAmount;
        this.goodsAmount = goodsAmount;
        this.freight = freight;

        amountTv.setText(String.format(mContext.getResources().getString(R.string.rmb), totalAmount));
        goodsPriceTv.setText(String.format(mContext.getResources().getString(R.string.rmb), goodsAmount));
        freightTv.setText(String.format(mContext.getResources().getString(R.string.rmb), freight));
        amountDetailTv.setText(String.format(mContext.getResources().getString(R.string.rmb), totalAmount));
    }

    public void setData(GDGoodsItem goodsItem, float totalAmount, float goodsAmount, float freight) {
        List<StockSimple> stockSimples = new ArrayList<>();
        StockSimple simple = new StockSimple();
        simple.setId(String.valueOf(goodsItem.getGoodsId()));
        simple.setPrice(String.valueOf(goodsItem.getPrice()));
        simple.setQuantity(String.valueOf(goodsItem.getGoodsCount()));
        simple.setSid(String.valueOf(goodsItem.getSid()));
        stockSimples.add(simple);
        Gson gson = new Gson();
        stockString = gson.toJson(stockSimples, new TypeToken<List<StockSimple>>() {
        }.getType());
        this.totalAmount = totalAmount;
        this.goodsAmount = goodsAmount;
        this.freight = freight;

        amountTv.setText(String.format(mContext.getResources().getString(R.string.rmb), totalAmount));
        goodsPriceTv.setText(String.format(mContext.getResources().getString(R.string.rmb), goodsAmount));
        freightTv.setText(String.format(mContext.getResources().getString(R.string.rmb), freight));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toAddressLayout:
                contentAnimator.setDisplayedChild(1);
                break;
            case R.id.toDetailLayout:
                contentAnimator.setDisplayedChild(2);
                break;
            case R.id.commitOrderBtn:
                //TODO 提交订单
                break;
        }
    }

    public void setOnSettlementClickListener(OnSettlementClickListener onSettlementClickListener) {
        this.onSettlementClickListener = onSettlementClickListener;
    }

    public interface OnSettlementClickListener {
        void onSettlementClick();
    }
}
