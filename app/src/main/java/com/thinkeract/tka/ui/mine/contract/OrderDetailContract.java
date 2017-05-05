package com.thinkeract.tka.ui.mine.contract;

import com.thinkeract.tka.data.api.entity.OrderDetailData;
import com.thinkeract.tka.ui.BasePresenter;
import com.thinkeract.tka.ui.BaseView;

import java.util.List;

/**
 * Created by ymh on 2016/11/19 23:19
 * e-mail:minhengyan@gmail.com
 */

public interface OrderDetailContract {

    public interface View extends BaseView<OrderDetailContract.Presenter> {
        public void showError(String message);
        public void showSuccess(List<OrderDetailData> orderDetailDatas);

    }

    public interface Presenter extends BasePresenter {
        void getOrderDetail(String po);
    }


}
