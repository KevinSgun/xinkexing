package com.thinkeract.tka.pay.contract;

import com.thinkeract.tka.ui.BasePresenter;
import com.thinkeract.tka.ui.BaseView;

/**
 * Author：YMH on 2016/11/5 17:31
 * E-mail：minheng_yan@163.com
 */

public interface OrderPayContract {

    interface View extends BaseView<Presenter> {
        public void showPaySuccess(String msg);
        public void showPayFailed(String msg, int failedCode);
    }

    interface Presenter extends BasePresenter {
        void payByWxPay(String orderId);
        void payByAliPay(String orderId);
    }

}
