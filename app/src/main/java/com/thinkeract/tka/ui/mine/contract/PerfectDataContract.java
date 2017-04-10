package com.thinkeract.tka.ui.mine.contract;

import com.thinkeract.tka.data.api.request.UpdateUserDataBody;
import com.thinkeract.tka.ui.BasePresenter;
import com.thinkeract.tka.ui.BaseView;
import com.thinkeract.tka.ui.login.contract.LoginContract;

/**
 * Created by ymh on 2016/11/19 23:19
 * e-mail:minhengyan@gmail.com
 */

public interface PerfectDataContract {

    public interface View extends BaseView<LoginContract.Presenter> {
        public void  showError(String message);
        public void showSuccess();

    }

    public interface Presenter extends BasePresenter {
        void completeProfile(UpdateUserDataBody body);
    }


}
