package com.thinkeract.tka.ui.mine.contract;

import com.thinkeract.tka.data.api.request.DoctorDataReviewBody;
import com.thinkeract.tka.ui.BasePresenter;
import com.thinkeract.tka.ui.BaseView;

/**
 * Created by ymh on 2016/11/19 23:19
 * e-mail:minhengyan@gmail.com
 */

public interface DoctorAuthContract {

    public interface View extends BaseView<DoctorAuthContract.Presenter> {
        public void showError(String message);
        public void showSuccess(String message);

    }

    public interface Presenter extends BasePresenter {
        void doDoctorAuth(DoctorDataReviewBody body);
    }

}
