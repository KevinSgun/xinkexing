package com.thinkeract.tka.widget;

import android.app.Activity;
import android.support.annotation.NonNull;
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
import com.thinkeract.tka.common.utils.DBUtils;
import com.thinkeract.tka.data.api.entity.StockSimple;
import com.thinkeract.tka.data.db.greendao.GDAddress;
import com.thinkeract.tka.data.db.greendao.GDGoodsItem;
import com.thinkeract.tka.ui.mine.UpdateAddressActivity;
import com.zitech.framework.utils.ToastMaster;
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
    private double totalAmount;
    private double goodsAmount;
    private double freight;
    private String stockString;
    private TextView goodsPriceTv;
    private TextView freightTv;
    private TextView amountDetailTv;
    private GDAddress mDefAddress;

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
                UpdateAddressActivity.launch(mContext,null);
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
        refreshDefAddress(DBUtils.queryDefAddress());
        addressLayout.setData(DBUtils.queryDefAddress(),DBUtils.queryAllAddressList(),mContext);
    }

    private void refreshDefAddress(GDAddress defAddress) {
        if(defAddress!=null){
            contactNameTv.setText(defAddress.getContact());
            phoneNumTv.setText(defAddress.getPhone());
            addressTv.setText(defAddress.getProvince()+defAddress.getCity()+defAddress.getAddress());
            mDefAddress = defAddress;
        }
    }

    public void setData(List<GDGoodsItem> goodsItemList, double totalAmount, double goodsAmount, double freight) {
        List<StockSimple> stockSimples = new ArrayList<>();
        for (GDGoodsItem goodsItem : goodsItemList) {
            if (goodsItem.getGoodsId() != 0) {
                StockSimple simple = getStockSimple(goodsItem);
                stockSimples.add(simple);
            }
        }
        refreshPriceUI(totalAmount, goodsAmount, freight, stockSimples);
        amountDetailTv.setText(String.format(mContext.getResources().getString(R.string.rmb), totalAmount));
    }

    private void refreshPriceUI(double totalAmount, double goodsAmount, double freight, List<StockSimple> stockSimples) {
        Gson gson = new Gson();
        stockString = gson.toJson(stockSimples, new TypeToken<List<StockSimple>>() {
        }.getType());
        this.totalAmount = totalAmount;
        this.goodsAmount = goodsAmount;
        this.freight = freight;

        amountTv.setText(String.format(mContext.getResources().getString(R.string.rmb), totalAmount));
        goodsPriceTv.setText(String.format(mContext.getResources().getString(R.string.rmb), goodsAmount));
        freightTv.setText(String.format(mContext.getResources().getString(R.string.rmb), freight));

        detailLayout.setData(totalAmount,goodsAmount,freight);
    }

    public void setData(GDGoodsItem goodsItem, double totalAmount, double goodsAmount, double freight) {
        List<StockSimple> stockSimples = new ArrayList<>();
        StockSimple simple = getStockSimple(goodsItem);
        stockSimples.add(simple);
        refreshPriceUI(totalAmount, goodsAmount, freight, stockSimples);
    }

    @NonNull
    private StockSimple getStockSimple(GDGoodsItem goodsItem) {
        StockSimple simple = new StockSimple();
        simple.setId(String.valueOf(goodsItem.getGoodsId()));
        simple.setPrice(String.valueOf(goodsItem.getPrice()));
        simple.setQuantity(String.valueOf(goodsItem.getGoodsCount()));
        simple.setSid(String.valueOf(goodsItem.getSid()));
        return simple;
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
                if(mDefAddress == null|| mDefAddress.getAddressId() == 0){
                    ToastMaster.shortToast("请先添加收货地址");
                    return;
                }
                if(onSettlementClickListener != null) {
                    onSettlementClickListener.onSettlementClick(totalAmount, String.valueOf(mDefAddress.getAddressId()),stockString);
                    dismiss();
                }

                break;
        }
    }

    public void setOnSettlementClickListener(OnSettlementClickListener onSettlementClickListener) {
        this.onSettlementClickListener = onSettlementClickListener;
    }

    public interface OnSettlementClickListener {
        void onSettlementClick(double totalAmount,String addressId,String stockString);
    }
}
