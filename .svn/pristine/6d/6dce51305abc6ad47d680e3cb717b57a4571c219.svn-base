package com.thinkeract.tka.ui.login.contract;

import com.thinkeract.tka.ui.BasePresenter;
import com.thinkeract.tka.ui.BaseView;

/**
 * Created by minHeng on 2017/03/10 17:58.
 * mail:minhengyan@gmail.com
 */

public interface FindPasswordContract {

    interface View extends BaseView<Presenter> {
        public void  showError(String message);
        public void showSuccess();
    }

    interface Presenter extends BasePresenter {
        void findPassword(String mobile, String password, String validateNum);
    }
}
