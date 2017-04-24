package com.thinkeract.tka.data.api.response;

/**
 * Created by minHeng on 2017/4/17 10:59.
 * mail:minhengyan@gmail.com
 */

public class PayResultData {

    /**
     * payInfo : {"wx_notify_url":"http://121.201.3.158:8080/resultWx","wx_app_id":"wxd35c4398e0feb8cc","wx_seller":"1418938602","noncestr":"03255088ed63354a54e0e5ed957e9008","timestamp":1491998530,"prepay_id":"wx201704122002142183ec366c0159970194","sign":"DC32FDE23A5E7540DB03F6DA15B34F85","packageValue":"Sign=WXPay"}
     */

    private PayInfoBean payInfo;

    public PayInfoBean getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PayInfoBean payInfo) {
        this.payInfo = payInfo;
    }

    public static class PayInfoBean {
        /**
         * wx_notify_url : http://121.201.3.158:8080/resultWx
         * wx_app_id : wxd35c4398e0feb8cc
         * wx_seller : 1418938602
         * noncestr : 03255088ed63354a54e0e5ed957e9008
         * timestamp : 1491998530
         * prepay_id : wx201704122002142183ec366c0159970194
         * sign : DC32FDE23A5E7540DB03F6DA15B34F85
         * packageValue : Sign=WXPay
         */

        private int timestamp;
        private String wx_notify_url;
        private String wx_app_id;
        private String wx_seller;
        private String noncestr;
        private String prepay_id;
        private String sign;
        private String packageValue;

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

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
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
}
