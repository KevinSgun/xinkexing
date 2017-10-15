package com.thinkeract.tka.ui.home.contract;

import com.thinkeract.tka.data.api.response.CheckResultData;
import com.thinkeract.tka.ui.BasePresenter;
import com.thinkeract.tka.ui.BaseView;

/**
 * Created by minHeng on 2017/03/10 17:58.
 * mail:minhengyan@gmail.com
 */

public interface CheckResultStuffContract {

    interface View extends BaseView<Presenter> {
        public void showError(String message);
        public void showSuccess(CheckResultData response);
    }

    interface Presenter extends BasePresenter {
        void getCheckResultData(String id);
    }
}
