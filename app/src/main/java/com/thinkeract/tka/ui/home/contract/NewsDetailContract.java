package com.thinkeract.tka.ui.home.contract;

import com.thinkeract.tka.data.api.response.NewsDetailData;
import com.thinkeract.tka.ui.BasePresenter;
import com.thinkeract.tka.ui.BaseView;

import java.util.List;

/**
 * Created by minHeng on 2017/03/10 17:58.
 * mail:minhengyan@gmail.com
 */

public interface NewsDetailContract {

    interface View extends BaseView<Presenter> {
        public void showError(String message);
        public void showSuccess(List<NewsDetailData> response);
    }

    interface Presenter extends BasePresenter {
        void getNewsDetail(String newsId);
    }
}
