package com.thinkeract.tka.pay.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.thinkeract.tka.pay.PayCallBack;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

@SuppressLint("HandlerLeak")
public class AliPay {


    PayInfo info;

    Context context;

    PayCallBack callBack;

    private static class sub {

        public static AliPay instance;

        static {
            instance = new AliPay();
        }
    }

    public static AliPay getInstance() {
        return sub.instance;
    }

    private AliPay() {
        // TODO Auto-generated constructor stub
    }

//    public void startPay(Context context, PayInfo payInfo, PayCallBack callBack) {
//        // payInfo.setOut_trad_no("test__01");
//        // TODO Auto-generated method stub
//        if (!(context instanceof Activity)) {
//            return;
//        }
//        this.context = context;
//        this.info = payInfo;
//        this.callBack = callBack;
//
//        if (TextUtils.isEmpty(RSA_PRIVATE)) {
//            RSA_PRIVATE = Utils.readFromAssets(context, "alipay_ras");
//        }
//        pay();
//    }

	/*
     * // 商户PID public static final String PARTNER = "2990001293210931";
	 * 
	 * // 商户收款账号 public static final String SELLER = "456789";
	 */

    // 商户私钥，pkcs8格式

    public static String RSA_PRIVATE = "";

    // 支付宝公钥 客户端不需要
    // public static final String RSA_PUBLIC = "";

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /*
                      同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                      detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                      docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                        callBack.onCallBack("支付成功!");
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            //Toast.makeText(context, "支付结果确认中", Toast.LENGTH_SHORT).show();
                            callBack.onFailure("支付结果确认中...");
                        }  else if(TextUtils.equals(resultStatus, "6001")){
                             callBack.onFailure("支付已取消");
                        } else if(TextUtils.equals(resultStatus, "6002")){
                            callBack.onFailure("网络连接异常");
                        }else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            callBack.onFailure("支付失败");
                        }
                    }


//                        object.put("resultStatus", resultStatus);
//                        object.put("feeChannel", "1");


                    break;
                }
                case SDK_CHECK_FLAG: {
                    //Toast.makeText(context, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * call alipay sdk pay. 调用SDK支付
     */
//    private void pay() {
//        if (TextUtils.isEmpty(info.getPartner()) || TextUtils.isEmpty(RSA_PRIVATE)
//                || TextUtils.isEmpty(info.getSeller_id())) {
//            new AlertDialog.Builder(context).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                            //
//                            dialoginterface.dismiss();
//                        }
//                    }).show();
//            return;
//        }
//        // 订单
//        String orderInfo = getOrderInfo();
//        Log.i("888", "orderInfo---->" + orderInfo);
//
//        // 对订单做RSA 签名
//        String sign = sign(orderInfo);
//        if (!sign.equals(info.getSign())) {
//            Logger.i("777", "info.getSign()---->" + info.getSign());
//            Logger.i("777", "sign---->" + sign);
//            Logger.i("777", "签名不一致！！！！！！");
//        }
//        //sign=info.getSign();
//        try {
//            // 仅需对sign 做URL编码
//            sign = URLEncoder.encode(sign, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        // 完整的符合支付宝参数规范的订单信息
//        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
//        Logger.i("888", "payInfo------->" + payInfo);
//
//        payByZFB(payInfo);
//
//
//    }

    public void startPay(Context context, final String payInfo, PayCallBack payCallBack) {
        if (payInfo == null) {
            payCallBack.onFailure("payInfo不能为空");
            return;
        }
        this.context = context;
        this.callBack = payCallBack;
        payByZFB(payInfo);
    }

    private void payByZFB(final String payInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) context);

                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask((Activity) context);
        String version = payTask.getVersion();
        Toast.makeText(context, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     */
//    public String getOrderInfo() {
//
//        // 签约合作者身份ID
//        String orderInfo = "partner=" + "\"" + info.getPartner() + "\"";
//
//        // 签约卖家支付宝账号
//        orderInfo += "&seller_id=" + "\"" + info.getSeller_id() + "\"";
//
//        // 商户网站唯一订单号
//        orderInfo += "&out_trade_no=" + "\"" + info.getOut_trade_no() + "\"";
//
//        // 商品名称
//        orderInfo += "&subject=" + "\"" + info.getSubject() + "\"";
//
//        // 商品详情
//        orderInfo += "&body=" + "\"" + info.getBody() + "\"";
//
//        String sFee = "0.00"; // 将金额由分 转换为元，保留两位小数，支付宝 下单是需要 元为单位
//        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
//        sFee = df.format(info.getTotal_fee());
//
//        // 商品金额
//        orderInfo += "&total_fee=" + "\"" + sFee + "\"";
//
//        // 服务器异步通知页面路径
//        orderInfo += "&notify_url=" + "\"" + info.getNotify_url() + "\"";
//
//        // 服务接口名称， 固定值
//        orderInfo += "&service=\"" + info.getService() + "\"";
//
//        // 支付类型， 固定值
//        orderInfo += "&payment_type=\"" + info.getPayment_type() + "\"";
//
//        // 参数编码， 固定值
//        orderInfo += "&_input_charset=\"" + info.get_Input_charset() + "\"";
//
//        // 设置未付款交易的超时时间
//        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
//        // 取值范围：1m～15d。
//        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
//        // 该参数数值不接受小数点，如1.5h，可转换为90m。
//        orderInfo += "&it_b_pay=\"" + info.getIt_b_pay() + "\"";
//
//        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
//        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
//
//        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//        // orderInfo += "&return_url=\"" + info.getReturn_url() + "\"";
//
//        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
//        // orderInfo += "&paymethod=\"expressGateway\"";
//
//        orderInfo += "&show_url=\"" + info.getShow_url() + "\"";
//
//        return orderInfo;
//    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE,false);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
