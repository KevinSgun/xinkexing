package com.thinkeract.tka.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.User;
import com.thinkeract.tka.common.utils.DBUtils;
import com.thinkeract.tka.common.utils.StockUtil;
import com.thinkeract.tka.common.utils.Utils;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.GoodsItem;
import com.thinkeract.tka.data.api.entity.Sku;
import com.thinkeract.tka.data.api.request.SubmitOrderBody;
import com.thinkeract.tka.data.api.response.GoodsDetailData;
import com.thinkeract.tka.data.api.response.PoData;
import com.thinkeract.tka.data.db.greendao.GDGoodsItem;
import com.thinkeract.tka.ui.mall.adapter.GoodsSpecAdapter;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.transform.RoundedCornersTransformation;
import com.zitech.framework.utils.ToastMaster;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;
import com.zitech.framework.widget.ValidDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by minHeng on 2017/4/7 10:49.
 * mail:minhengyan@gmail.com
 */

public class ChooseGoodsSpecDialog extends ValidDialog implements View.OnClickListener {

    private Activity mContext;
    private RemoteImageView goodsPicIv;
    private TextView goodsNameTv;
    private TextView goodsPriceTv;
    private RecyclerView goodsSpecRv;
    private ImageView minusIv;
    private TextView countTv;
    private ImageView plusIv;
    private Button submitBtn;
    private GoodsSpecAdapter mAdapter;
    private ImageView cancelIv;
    private HashSet<List<String>> pathList;
    private HashMap<Integer, String> selTempPath;
    private List<String> selLineValueList;
    private List<Integer> selLinePosList;
    private HashMap<String, GoodsDetailData.StockBean> codeMap;
    private GoodsDetailData.StockBean mStockBean;
    private GoodsItem mGoodsItem;
    private int MAX_COUNT;
    private int goodsCount = 1;
    private int mBuyType;//0,加入购物车；1,直接购买

    /**
     * @param context
     * @param buyType 0,加入购物车；1,直接购买
     */
    public ChooseGoodsSpecDialog(Activity context, int buyType) {
        super(context, R.style.BottomPushDialog);
        mContext = context;
        mBuyType = buyType;
        initView();
    }

    public void setType(int buyType) {
        mBuyType = buyType;
        submitBtn.setText(mBuyType == 0 ? "加入购物车" : "立即购买");
    }

    @SuppressLint("UseSparseArrays")
    private void initView() {
        setContentView(R.layout.dialog_choose_spec);

        goodsPriceTv = (TextView) findViewById(R.id.goodsPriceTv);
        goodsPicIv = (RemoteImageView) findViewById(R.id.goodsPicIv);
        goodsNameTv = (TextView) findViewById(R.id.goodsNameTv);
        goodsSpecRv = (RecyclerView) findViewById(R.id.goodsSpecRv);
        minusIv = (ImageView) findViewById(R.id.minusIv);
        countTv = (TextView) findViewById(R.id.countTv);
        plusIv = (ImageView) findViewById(R.id.plusIv);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        cancelIv = (ImageView) findViewById(R.id.cancelIv);

        minusIv.setOnClickListener(this);
        plusIv.setOnClickListener(this);
        cancelIv.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        submitBtn.setText(mBuyType == 0 ? "加入购物车" : "立即购买");

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams dialogParams;
        if (dialogWindow != null) {
            dialogParams = dialogWindow.getAttributes();
            dialogParams.gravity = Gravity.BOTTOM;
            dialogParams.height = ViewUtils.getDimenPx(R.dimen.h886);
            dialogParams.width = ViewUtils.getDimenPx(R.dimen.w720);
            dialogWindow.setAttributes(dialogParams);
        }
        selTempPath = new HashMap<Integer, String>();
        selLineValueList = new ArrayList<>();
        selLinePosList = new ArrayList<>();
        mAdapter = new GoodsSpecAdapter(mContext);
        goodsSpecRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter.setOnTagClickListener(new GoodsSpecAdapter.OnTagClickListener() {

            @Override
            public void onTagClick(Sku.Spec spec, int selPos, boolean isChoose) {
                List<Sku> skuList = mAdapter.getItemList();

                if (isChoose) {
                    selTempPath.put(selPos, spec.getPid() + ":" + spec.getId());
                } else {
                    selTempPath.remove(selPos);
                }

                selLineValueList.clear();
                selLinePosList.clear();
                selLineValueList.addAll(selTempPath.values());
                Collections.sort(selLineValueList, new CompareStringList<String>());
                selLinePosList.addAll(selTempPath.keySet());
                if (!pathList.contains(selLineValueList)) {
                    for (int i = 0, size = skuList.size(); i < size; i++) {
                        Sku sku = skuList.get(i);
                        for (Sku.Spec specB : sku.getItems()) {
                            if (i != selPos && isChoose) {
                                specB.setHasStock(false);
                                if (selTempPath.containsKey(i))
                                    selTempPath.remove(i);
                            } else {
                                specB.setHasStock(true);
                            }
                        }
                    }
                } else {
                    for (int i = 0, size = skuList.size(); i < size; i++) {
                        Sku sku = skuList.get(i);
                        for (Sku.Spec specB : sku.getItems()) {
                            if (!selLinePosList.contains(i)) {
                                List<String> selLineValueListTemp = copyFor(selLineValueList);
                                selLineValueListTemp.add(specB.getPid() + ":" + specB.getId());
                                Collections.sort(selLineValueListTemp, new CompareStringList<String>());
                                specB.setHasStock(pathList.contains(selLineValueListTemp));
                            } else if (selLinePosList.size() == 1) {
                                specB.setHasStock(true);
                            }
                        }
                    }
                }
                updateSubmitStatus();
            }
        });
        goodsSpecRv.setAdapter(mAdapter);
    }

    private void updateSubmitStatus() {
        boolean isSelDone = selTempPath.size() == mAdapter.getItemList().size();
        submitBtn.setEnabled(isSelDone);
        if (isSelDone) {
            selLineValueList.clear();
            selLineValueList.addAll(selTempPath.values());
            StringBuilder sb = new StringBuilder();
            Collections.sort(selLineValueList, new CompareStringList<String>());
            for (String codeValue : selLineValueList) {
                sb.append(codeValue).append(";");
            }
            sb.deleteCharAt(sb.length() - 1);
            if (codeMap.containsKey(sb.toString())) {
                mStockBean = codeMap.get(sb.toString());
                MAX_COUNT = mStockBean.getInventory();
            }
        }
    }

    private List<String> copyFor(List<String> selLineValueList) {
        List<String> temp = new ArrayList<>();
        for (String item : selLineValueList) {
            temp.add(item);
        }
        return temp;
    }

    private static class CompareStringList<T> implements Comparator<T> {
        @Override
        public int compare(T lhs, T rhs) {
            String[] itemArrA = ((String) lhs).split(":");
            String[] itemArrB = ((String) rhs).split(":");
            int itemIntA = Integer.parseInt(itemArrA[0]);
            int itemIntB = Integer.parseInt(itemArrB[0]);
            return itemIntA - itemIntB;
        }
    }

    public void setData(GoodsDetailData goodsDetailData) {
        mGoodsItem = goodsDetailData.getGoods();
        goodsPicIv.setBitmapTransformation(new RoundedCornersTransformation(mContext, ViewUtils.getDimenPx(R.dimen.w10), 0));
        goodsPicIv.setImageUri(Constants.ImageDefResId.DEF_SQUARE_PIC_NORMAL, mGoodsItem.getCover());
        goodsPriceTv.setText(String.format(mContext.getResources().getString(R.string.rmb), mGoodsItem.getMinprice()));
        goodsNameTv.setText(mGoodsItem.getName());
        List<GoodsDetailData.StockBean> stockList = goodsDetailData.getStock();

//        Sku sku = new Sku();
//        sku.setId(28);
//        sku.setName("颜色");
//        Sku.Spec spec = new Sku.Spec();
//        spec.setId(10);
//        spec.setPid(28);
//        spec.setName("紫色");
//        Sku.Spec spec2 = new Sku.Spec();
//        spec2.setId(11);
//        spec2.setPid(28);
//        spec2.setName("粉红色");
//        Sku.Spec spec3 = new Sku.Spec();
//        spec3.setId(13);
//        spec3.setPid(28);
//        spec3.setName("皓月白");
//        Sku.Spec spec4 = new Sku.Spec();
//        spec4.setId(14);
//        spec4.setPid(28);
//        spec4.setName("深空灰");
//        List<Sku.Spec> specS = new ArrayList<>();
//        specS.add(spec);
//        specS.add(spec2);
//        specS.add(spec3);
//        specS.add(spec4);
//        sku.setItems(specS);
//
//        Sku sku2 = new Sku();
//        sku2.setId(11);
//        sku2.setName("内存");
//        Sku.Spec spec21 = new Sku.Spec();
//        spec21.setId(10);
//        spec21.setPid(11);
//        spec21.setName("1G");
//        Sku.Spec spec22 = new Sku.Spec();
//        spec22.setId(11);
//        spec22.setPid(11);
//        spec22.setName("2G");
//        Sku.Spec spec33 = new Sku.Spec();
//        spec33.setId(13);
//        spec33.setPid(11);
//        spec33.setName("3G");
//        Sku.Spec spec44 = new Sku.Spec();
//        spec44.setId(14);
//        spec44.setPid(11);
//        spec44.setName("4G");
//        List<Sku.Spec> specS2 = new ArrayList<>();
//        specS2.add(spec21);
//        specS2.add(spec22);
//        specS2.add(spec33);
//        specS2.add(spec44);
//        sku2.setItems(specS2);
//
//        Sku sku3 = new Sku();
//        sku3.setId(30);
//        sku3.setName("产地");
//        Sku.Spec spec51 = new Sku.Spec();
//        spec51.setId(10);
//        spec51.setPid(30);
//        spec51.setName("美国");
//        Sku.Spec spec52 = new Sku.Spec();
//        spec52.setId(11);
//        spec52.setPid(30);
//        spec52.setName("澳大利亚");
//        Sku.Spec spec53 = new Sku.Spec();
//        spec53.setId(13);
//        spec53.setPid(30);
//        spec53.setName("印度尼西亚");
//        Sku.Spec spec54 = new Sku.Spec();
//        spec54.setId(44);
//        spec54.setPid(30);
//        spec54.setName("布宜诺斯艾利斯");
//        Sku.Spec spec55 = new Sku.Spec();
//        spec55.setId(48);
//        spec55.setPid(30);
//        spec55.setName("加拿大");
//        List<Sku.Spec> specS3 = new ArrayList<>();
//        specS3.add(spec51);
//        specS3.add(spec52);
//        specS3.add(spec53);
//        specS3.add(spec54);
//        specS3.add(spec55);
//        sku3.setItems(specS3);
//
//        GoodsDetailData gD = new GoodsDetailData();
//        gD.setGoods(goodsDetailData.getGoods());
//        List<Sku> skus = new ArrayList<>();
//        skus.add(sku);
//        skus.add(sku2);
//        skus.add(sku3);
//        gD.setSku(skus);
//
//        List<GoodsDetailData.StockBean> stockBeanList = new ArrayList<>();
//        GoodsDetailData.StockBean stockBeanBean = new GoodsDetailData.StockBean();
//        stockBeanBean.setName("第一组合");
//        stockBeanBean.setId(99);
//        stockBeanBean.setCode("28:10;11:10;30:13");
//        GoodsDetailData.StockBean stockBeanBean2 = new GoodsDetailData.StockBean();
//        stockBeanBean2.setName("第二组合");
//        stockBeanBean2.setId(99);
//        stockBeanBean2.setCode("28:14;11:11;30:44");
//        GoodsDetailData.StockBean stockBeanBean3 = new GoodsDetailData.StockBean();
//        stockBeanBean3.setName("第三组合");
//        stockBeanBean3.setId(99);
//        stockBeanBean3.setCode("28:14;11:11;30:48");
//        GoodsDetailData.StockBean stockBeanBean4 = new GoodsDetailData.StockBean();
//        stockBeanBean4.setName("第四组合");
//        stockBeanBean4.setId(99);
//        stockBeanBean4.setCode("28:10;11:10;30:48");
//        GoodsDetailData.StockBean stockBeanBean5 = new GoodsDetailData.StockBean();
//        stockBeanBean5.setName("第五组合");
//        stockBeanBean5.setId(99);
//        stockBeanBean5.setCode("28:10;11:11;30:13");
//        stockBeanList.add(stockBeanBean);
//        stockBeanList.add(stockBeanBean2);
//        stockBeanList.add(stockBeanBean3);
//        stockBeanList.add(stockBeanBean4);
//        stockBeanList.add(stockBeanBean5);
//
//        gD.setStock(stockBeanList);

//        pathList = new HashSet<>();
//        for (GoodsDetailData.StockBean stockBean : stockBeanList) {
//            pathList.addAll(StockUtil.getPowSet(stockBean.getCode()));
//        }
//
//        mAdapter.setItemList(skus);

        pathList = new HashSet<>();
        codeMap = new HashMap<String, GoodsDetailData.StockBean>();
        for (GoodsDetailData.StockBean stockBean : stockList) {
            codeMap.put(stockBean.getCode(), stockBean);
            pathList.addAll(StockUtil.getPowSet(stockBean.getCode()));
        }

        mAdapter.setItemList(goodsDetailData.getSku());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn:
                final GDGoodsItem goodsItem = new GDGoodsItem();
                goodsItem.setUserId(User.get().getId());
                goodsItem.setName(mGoodsItem.getName());
                goodsItem.setUserGoodsId(User.get().getId() + "" + mGoodsItem.getId());
                goodsItem.setGoodsImg(mGoodsItem.getCover());
                goodsItem.setGoodsId(mGoodsItem.getId());
                goodsItem.setInventory(mStockBean.getInventory());
                goodsItem.setPrice(mStockBean.getPrice());
                goodsItem.setSpec(mGoodsItem.getSpec());
                goodsItem.setSid(mStockBean.getId());
                goodsItem.setGoodsCount(goodsCount);
                if (mBuyType == 0) {
                    //加入购物车
                    DBUtils.insertOrReplaceGoods(goodsItem);
                    ToastMaster.shortToast("成功加入到购物车");
                    dismiss();
                } else if (mBuyType == 1) {
                    SettlementDialog settlementDialog = new SettlementDialog(mContext);
                    settlementDialog.setOnSettlementClickListener(new SettlementDialog.OnSettlementClickListener() {
                        @Override
                        public void onSettlementClick(double totalAmount,String addressId,String stockString) {
                            //提交订单
                                submitOrder(totalAmount,addressId,stockString);
                        }
                    });
                    double totalGoodsPrice = Utils.doubleMultiplyDouble(mGoodsItem.getMinprice(), (double) goodsCount);
                    settlementDialog.setData(goodsItem, totalGoodsPrice, totalGoodsPrice, 0);
                    settlementDialog.show();

                }

                break;
            case R.id.minusIv:
                if (goodsCount > 1) {
                    goodsCount--;
                    countTv.setText(String.valueOf(goodsCount));
                }
                break;
            case R.id.plusIv:
                if (goodsCount < MAX_COUNT) {
                    goodsCount++;
                    countTv.setText(String.valueOf(goodsCount));
                } else {
                    ToastMaster.shortToast("已经是最大库存啦");
                }
                break;
            case R.id.cancelIv:
                dismiss();
                break;
        }
    }

    private void submitOrder(double totalAmount,String addressId,String stockString) {
        SubmitOrderBody body = new SubmitOrderBody();
        body.setAddressId(addressId);
        body.setStock(stockString);
        body.setTotleAmount(String.valueOf(totalAmount));
        ApiFactory.submit(body).subscribe(new ProgressSubscriber<ApiResponse<PoData>>(mContext) {
            @Override
            public void onNext(ApiResponse<PoData> value) {
                super.onNext(value);
                ToastMaster.shortToast(value.getMsg());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
