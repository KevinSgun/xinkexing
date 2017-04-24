package com.thinkeract.tka.ui.home.contract;

import com.thinkeract.tka.data.api.response.HomePageData;
import com.thinkeract.tka.ui.BasePresenter;
import com.thinkeract.tka.ui.BaseView;

/**
 * Created by minHeng on 2017/03/10 17:58.
 * mail:minhengyan@gmail.com
 */

public interface HomeDataContract {

    interface View extends BaseView<Presenter> {
        public void showError(String message);
        public void showSuccess(HomePageData response);
    }

    interface Presenter extends BasePresenter {
        void getHomeData(boolean showProgress);
    }
}
