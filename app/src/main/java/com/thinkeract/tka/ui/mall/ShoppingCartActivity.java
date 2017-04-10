package com.thinkeract.tka.ui.mall;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.thinkeract.tka.Events;
import com.thinkeract.tka.R;
import com.thinkeract.tka.ThinkerActApplication;
import com.thinkeract.tka.common.utils.DBUtils;
import com.thinkeract.tka.common.utils.Utils;
import com.thinkeract.tka.data.db.greendao.GDGoodsItem;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.mall.adapter.ShoppingCartAdapter;
import com.thinkeract.tka.widget.SettlementDialog;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by minHeng on 2017/4/5 20:42.
 * mail:minhengyan@gmail.com
 */

public class ShoppingCartActivity extends AppBarActivity {

    private CheckBox checkAll;
    private RelativeLayout selAllLayout;
    private RecyclerView cartListRv;
    private TextView totalAmountTv;
    private Button settlementBtn;
    private ViewAnimator viewanimator;
    private ShoppingCartAdapter mAdapter;
    private float totalGoodsPrice;
    private float totalFreight;
    private int totalCount;
    private float totalAmount;
    private static final int GOODS_CART = 1;
    private static final int EMPTY = 0;
    private boolean noNeedChangeAll;

    @Subscribe
    public void onGoodsSeltectedStatusChange(Events.GoodsSelectedStatusChange data) {
        if (data != null) {
            if(data.selectedStatus!=3) {
                noNeedChangeAll = (data.selectedStatus == 0);
                if (!noNeedChangeAll) {
                    checkAll.setChecked(data.selectedStatus == 1);
                }else{
                    checkAll.setChecked(false);
                }
            }
            calculationTotalAmount(mAdapter.getItemList());
            refreshUI();
        }
    }

    /**
     * 重置商品选择状态
     *
     * @param isSelector 全选或全不选
     */
    private void resetGoodsSelectorStatus(boolean isSelector, List<GDGoodsItem> goodsItemList) {
        for (GDGoodsItem goodsItem : goodsItemList) {
            if (goodsItem.getGoodsId() != 0) {
                goodsItem.setIsCheck(isSelector);
            }
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    protected void initView() {
        setTitle(R.string.my_shopping_cart);
        initializeView();
        mAdapter = new ShoppingCartAdapter(this);
        settlementBtn.setOnClickListener(this);
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!noNeedChangeAll) {
                    resetGoodsSelectorStatus(isChecked, mAdapter.getItemList());
                    ThinkerActApplication.getInstance().postDelay(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    },400);
                } else {
                    noNeedChangeAll = false;
                }
                calculationTotalAmount(mAdapter.getItemList());
                refreshUI();
            }
        });

        cartListRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cartListRv.setItemAnimator(new DefaultItemAnimator());
        cartListRv.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.settlementBtn) {
            SettlementDialog settlementDialog = new SettlementDialog(this);
            settlementDialog.setOnSettlementClickListener(new SettlementDialog.OnSettlementClickListener() {
                @Override
                public void onSettlementClick() {
                    //提交订单
                }
            });
            settlementDialog.show();
        }
    }

    @Override
    protected void initData() {
        List<GDGoodsItem> goodsItemList = DBUtils.queryAllGoodsList();
        if (goodsItemList != null && goodsItemList.size() > 0) {
            viewanimator.setDisplayedChild(GOODS_CART);
            goodsItemList.add(new GDGoodsItem());
            mAdapter.setItemList(goodsItemList);
            checkAllSelectedStatus(goodsItemList);
            calculationTotalAmount(goodsItemList);
            refreshUI();
        } else {
            viewanimator.setDisplayedChild(EMPTY);
        }
    }

    private void refreshUI() {
        if (mAdapter.getItemList() != null && mAdapter.getItemList().size() > 1) {
            totalAmountTv.setText(String.format(getResources().getString(R.string.rmb), totalAmount));
        } else {
            viewanimator.setDisplayedChild(EMPTY);
        }
    }

    /**
     * 第一次进入购物车时检查所有商品的选中状态
     *
     * @param goodsItemList
     */
    private void checkAllSelectedStatus(List<GDGoodsItem> goodsItemList) {
        boolean allIsSelected = true;
        for (GDGoodsItem goodsItem : goodsItemList) {
            if (!(allIsSelected = goodsItem.getIsCheck())) {
                break;
            }
        }
        checkAll.setChecked(allIsSelected);
    }

    /**
     * 计算购物车内所有商品的合计金额
     */
    private void calculationTotalAmount(List<GDGoodsItem> goodsItemList) {
        totalGoodsPrice = 0;
        totalFreight = 0;
        totalCount = 0;
        totalAmount = 0;
        for (GDGoodsItem goodsItem : goodsItemList) {
            if (goodsItem.getIsCheck() && goodsItem.getGoodsId() != 0) {
                totalGoodsPrice = Utils.floatAddFloat(totalGoodsPrice, Utils.floatMultiplyFloat(goodsItem.getPrice(), goodsItem.getGoodsCount()));
                totalFreight = Utils.floatAddFloat(totalFreight, goodsItem.getFreight());
                totalCount += goodsItem.getGoodsCount();
            }
        }
        totalAmount = Utils.floatAddFloat(totalGoodsPrice, totalFreight);
    }

    private void initializeView() {
        checkAll = (CheckBox) findViewById(R.id.checkAll);
        selAllLayout = (RelativeLayout) findViewById(R.id.selAllLayout);
        cartListRv = (RecyclerView) findViewById(R.id.cartListRv);
        totalAmountTv = (TextView) findViewById(R.id.totalAmountTv);
        settlementBtn = (Button) findViewById(R.id.settlementBtn);
        viewanimator = (ViewAnimator) findViewById(R.id.view_animator);
    }

    @Override
    protected void onDestroy() {
        DBUtils.insertOrReplaceAll(mAdapter.getItemList());
        super.onDestroy();
    }
}