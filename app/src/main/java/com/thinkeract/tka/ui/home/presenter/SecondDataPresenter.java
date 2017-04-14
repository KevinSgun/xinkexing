package com.thinkeract.tka.ui.home.presenter;

import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.SecondReportItem;
import com.thinkeract.tka.data.api.request.IdRequest;
import com.thinkeract.tka.ui.home.contract.SecondDataContract;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.List;

/**
 * Created by ymh on 2016/10/30 10:53
 * e-mail:minhengyan@gmail.com
 */

public class SecondDataPresenter implements SecondDataContract.Presenter {
    private SecondDataContract.View mView;

    public SecondDataPresenter(SecondDataContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getSecondData(String id) {
        IdRequest idRequest = new IdRequest();
        idRequest.setId(id);
        ApiFactory.secondReport(idRequest).subscribe(new ProgressSubscriber<ApiResponse<List<SecondReportItem>>>(mView.getContext()){
            @Override
            public void onNext(ApiResponse<List<SecondReportItem>> value) {
                super.onNext(value);
                List<SecondReportItem> secondReportItemList = value.getData();
                if(secondReportItemList != null){
                    int left = secondReportItemList.size()%4;
                    int count  = 4-left;
                    for(int i=0;i<count;i++){
                        SecondReportItem emptyItem = new SecondReportItem();
                        secondReportItemList.add(emptyItem);
                    }
                }
                mView.showSuccess(secondReportItemList);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError(e.getMessage());
            }
        });
    }
}
