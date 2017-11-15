package com.thinkeract.tka.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.ThinkerActApplication;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.request.PoBody;
import com.thinkeract.tka.data.api.response.LogisticsData;
import com.thinkeract.tka.ui.AppBarActivity;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

/**
 * Author：YMH on 2017/11/15 0015 14:42
 * E-mail：minheng_yan@163.com
 */
public class LogisticsDetailActivity extends AppBarActivity{
    private TextView mExpressCompanyTv;
    private TextView mWaybillNumTv;
    private String po;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_logistics_detail;
    }

    @Override
    protected void initView() {
        po = getIntent().getStringExtra(Constants.ActivityExtra.PO);
        mExpressCompanyTv = (TextView) findViewById(R.id.expressCompanyTv);
        mWaybillNumTv = (TextView) findViewById(R.id.waybillNumTv);
    }

    @Override
    protected void initData() {
        requestLogisticsData();
    }

    private void requestLogisticsData() {
        PoBody body = new PoBody();
        body.setPo(po);
        ApiFactory.lookOrderLogistics(body).subscribe(new ProgressSubscriber<ApiResponse<LogisticsData>>(this){
            @Override
            public void onNext(ApiResponse<LogisticsData> value) {
                super.onNext(value);
                refreshUI(value.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ThinkerActApplication.getInstance().postDelay(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },1000);
            }
        });
    }

    private void refreshUI(LogisticsData data) {
        if(data != null) {
            mExpressCompanyTv.setText(data.getCorp());
            mWaybillNumTv.setText(data.getOdd());
        }
    }

    public static void launch(Activity activity,String po){
        Intent intent = new Intent(activity,LogisticsDetailActivity.class);
        intent.putExtra(Constants.ActivityExtra.PO,po);
        activity.startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN,activity);
    }
}
