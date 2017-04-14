package com.thinkeract.tka.widget;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.Sku;
import com.thinkeract.tka.ui.mall.adapter.GoodsSpecAdapter;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;
import com.zitech.framework.widget.ValidDialog;

import java.util.List;

/**
 * Created by minHeng on 2017/4/7 10:49.
 * mail:minhengyan@gmail.com
 */

public class ChooseGoodsSpecDialog extends ValidDialog{

    private Activity mContext;
    private RemoteImageView goodsPicIv;
    private TextView goodsNameTv;
    private TextView goodsPriceTv;
    private RecyclerView goodsSpecRv;
    private ImageView minusIv;
    private TextView countTv;
    private ImageView plusIv;
    private Button commitBtn;
    private GoodsSpecAdapter mAdapter;

    public ChooseGoodsSpecDialog(Activity context) {
        super(context, R.style.BottomPushDialog);
        mContext = context;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_choose_spec);

        goodsPriceTv = (TextView) findViewById(R.id.goodsPriceTv);
        goodsPicIv = (RemoteImageView) findViewById(R.id.goodsPicIv);
        goodsNameTv = (TextView) findViewById(R.id.goodsNameTv);
        goodsSpecRv = (RecyclerView) findViewById(R.id.goodsSpecRv);
        minusIv = (ImageView) findViewById(R.id.minusIv);
        countTv = (TextView) findViewById(R.id.countTv);
        plusIv = (ImageView) findViewById(R.id.plusIv);
        commitBtn = (Button) findViewById(R.id.commitBtn);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams dialogParams;
        if (dialogWindow != null) {
            dialogParams = dialogWindow.getAttributes();
            dialogParams.gravity = Gravity.BOTTOM;
            dialogParams.height = ViewUtils.getDimenPx(R.dimen.h896);
            dialogParams.width = ViewUtils.getDimenPx(R.dimen.w720);
            dialogWindow.setAttributes(dialogParams);
        }

        mAdapter = new GoodsSpecAdapter(mContext);
        goodsSpecRv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        goodsSpecRv.setAdapter(mAdapter);
    }

    public void setData(List<Sku> specsList){
        mAdapter.setItemList(specsList);
    }

}
