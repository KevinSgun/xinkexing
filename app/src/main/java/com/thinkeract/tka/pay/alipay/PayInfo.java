package com.thinkeract.tka.pay.alipay;

import com.tencent.mm.opensdk.modelpay.PayReq;


public class PayInfo{


	/**
	 * wx_notify_url : http://121.201.3.158:8080/resultWx
	 * wx_app_id : wxc4f97b7279e94c9b
	 * wx_seller : 1266157301
	 * noncestr : 2ef35a8b78b572a47f56846acbeef5d3
	 * timestamp : 1503665240
	 * prepay_id : 12312312312312
	 * sign : C191DE97ECA31355924BC594679F2ADF
	 * packageValue : Sign=WXPay
	 */

	private String wx_notify_url;
	private String wx_app_id;
	private String wx_seller;
	private String noncestr;
	private String prepay_id;
	private String sign;
	private String packageValue;
	private String timestamp;

	public PayReq toPayReq(){
		// 微信
		PayReq req = new PayReq();

//        Gson gson = new Gson();
//        gson.fromJson(this.payInfo,new TypeToken<PayInfo>(){}.getType())
		req.appId = wx_app_id;//object.optString("appid");
		req.partnerId = wx_seller;// object.optString("partner");// 商户号
		req.prepayId = prepay_id;//object.optString("prepayId");
		req.nonceStr = noncestr;
		req.timeStamp = timestamp;//object.optString("timeStamp");
		req.packageValue = packageValue;// "Sign=WXPay";
		req.sign = sign;
		req.extData	= "app data";

		return req;
	}

	public String getWx_notify_url() {
		return wx_notify_url;
	}

	public void setWx_notify_url(String wx_notify_url) {
		this.wx_notify_url = wx_notify_url;
	}

	public String getWx_app_id() {
		return wx_app_id;
	}

	public void setWx_app_id(String wx_app_id) {
		this.wx_app_id = wx_app_id;
	}

	public String getWx_seller() {
		return wx_seller;
	}

	public void setWx_seller(String wx_seller) {
		this.wx_seller = wx_seller;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPackageValue() {
		return packageValue;
	}

	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}
}
