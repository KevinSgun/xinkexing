package com.thinkeract.tka.ui.mall.contract;

import com.thinkeract.tka.data.api.response.GoodsDetailData;
import com.thinkeract.tka.ui.BasePresenter;
import com.thinkeract.tka.ui.BaseView;
import com.thinkeract.tka.ui.login.contract.LoginContract;

/**
 * Created by ymh on 2016/11/19 23:19
 * e-mail:minhengyan@gmail.com
 */

public interface GoodsDetailDataContract {

    public interface View extends BaseView<LoginContract.Presenter> {
        public void showError(String errorMsg);
        public void showSuccess(GoodsDetailData goodsDetailData);

    }

    public interface Presenter extends BasePresenter {
        void getGoodsDetail(String goodsId);
    }


}
