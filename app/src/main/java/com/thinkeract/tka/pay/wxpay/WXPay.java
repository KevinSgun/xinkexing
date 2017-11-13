package com.thinkeract.tka.pay.wxpay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.thinkeract.tka.Constants;
import com.thinkeract.tka.pay.PayCallBack;
import com.zitech.framework.utils.ToastMaster;

import java.util.LinkedList;
import java.util.List;

@SuppressLint({"DefaultLocale", "HandlerLeak"})
@SuppressWarnings("deprecation")
public class WXPay {


//	public static String APP_ID;


    private static WXPay instance;

    private String genAppSign(List<BasicNameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.WX_APP_KEY);
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }

    private void sign(PayReq req) {
        // req.nonceStr = UUIDUtil.uuid();
        List<BasicNameValuePair> signParams = new LinkedList<>();
        signParams.add(new BasicNameValuePair("appid", Constants.WX_APP_ID));
        // signParams.add(new BasicNameValuePair("mch_id", mch_id));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        // signParams.add(new BasicNameValuePair("notify_url", notify_url));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        // signParams.add(new BasicNameValuePair("signType", signType));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);

    }

    public static WXPay getInstance(Activity context) {
        synchronized (context) {
            if (instance == null) {
                instance = new WXPay(context);
            }
        }
        return instance;
    }

    private WXPay(Activity activity) {
        this.context = activity;
    }

    private Context context;

    public void startPay(PayReq reg, PayCallBack callBack) {
//        sign(reg);
        this.callBack = callBack;
        context.registerReceiver(new WXPayResultReceiver(), new IntentFilter(WX_PAY_RESULT_RECEIVER_ACTION));
        api = WXAPIFactory.createWXAPI(context, Constants.WX_APP_ID, true);
        api.registerApp(Constants.WX_APP_ID);
        if (api.isWXAppInstalled() && api.isWXAppSupportAPI()) {
            api.sendReq(reg);
        } else {
            String msg = "未检测到微信客户端，请先安装微信";
            ToastMaster.popToast(context, msg);
            //api.sendReq(null);
            callBack.onFailure(msg);
        }
    }

    IWXAPI api;


    private PayCallBack callBack;

    private Handler callbBackHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                api.unregisterApp();
                int result = (int) msg.obj;
                if (result == BaseResp.ErrCode.ERR_OK) {
                    callBack.onCallBack("支付成功!");
                } else if (result == BaseResp.ErrCode.ERR_USER_CANCEL) {
                    callBack.onFailure("支付已取消");
                } else {
                    callBack.onFailure("支付失败");
                }

            } else {
                callBack.onFailure("支付失败");
            }
        }

    };

    /**
     * 微信支付结果回调
     */
    public static final String WX_PAY_RESULT_RECEIVER_ACTION = "WXPayResultReceiverAction";

    public class WXPayResultReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(WX_PAY_RESULT_RECEIVER_ACTION)) {
                int result = intent.getIntExtra("result", -1);
                callbBackHandler.sendMessage(callbBackHandler.obtainMessage(1, result));
            }
            context.unregisterReceiver(this);
        }

    }
}
