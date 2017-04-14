package com.thinkeract.tka.ui.mall.contract;

import com.thinkeract.tka.ui.BasePresenter;
import com.thinkeract.tka.ui.BaseView;
import com.thinkeract.tka.ui.login.contract.LoginContract;
import com.thinkeract.tka.widget.filterview.FilterEntity;
import com.thinkeract.tka.widget.filterview.FilterTwoEntity;

import java.util.List;

/**
 * Created by ymh on 2016/11/19 23:19
 * e-mail:minhengyan@gmail.com
 */

public interface FilterDataContract {

    public interface View extends BaseView<LoginContract.Presenter> {
        public void showError(String errorMsg);
        public void showSuccess(List<FilterTwoEntity> filterTwoEntityList, List<FilterEntity> sortFilterList);

    }

    public interface Presenter extends BasePresenter {
        void getGoodsClassify();
    }


}
