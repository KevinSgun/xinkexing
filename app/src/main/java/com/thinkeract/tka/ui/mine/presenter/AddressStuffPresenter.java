package com.thinkeract.tka.ui.mine.presenter;

import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.AddressItem;
import com.thinkeract.tka.data.api.request.IdRequest;
import com.thinkeract.tka.data.api.request.UpdateAddressBody;
import com.thinkeract.tka.ui.mine.contract.AddressStuffContract;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.List;

/**
 * Created by ymh on 2017/10/18 21:57
 * e-mail:minhengyan@gmail.com
 */

public class AddressStuffPresenter implements AddressStuffContract.Presenter {

    private AddressStuffContract.View mView;

    public AddressStuffPresenter(AddressStuffContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getAddressList() {
        ApiFactory.getUserAddressList().subscribe(new ProgressSubscriber<ApiResponse<List<AddressItem>>>(mView.getContext()){
            @Override
            public void onNext(ApiResponse<List<AddressItem>> value) {
                super.onNext(value);
                mView.showSuccess(value.getData(),1,value.getMsg());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError(e.getMessage());
            }
        });
    }

    @Override
    public void setDefAddress(UpdateAddressBody body) {
        ApiFactory.modifyAddress(body).subscribe(new ProgressSubscriber<ApiResponse>(mView.getContext()){
            @Override
            public void onNext(ApiResponse value) {
                super.onNext(value);
                mView.showSuccess(null,2,value.getMsg());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError(e.getMessage());
            }
        });
    }

    @Override
    public void deleteAddress(String id) {
        IdRequest body = new IdRequest();
        body.setId(id);
        ApiFactory.deleteAddress(body).subscribe(new ProgressSubscriber<ApiResponse>(mView.getContext()){
            @Override
            public void onNext(ApiResponse value) {
                super.onNext(value);
                mView.showSuccess(null,3,value.getMsg());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError(e.getMessage());
            }
        });
    }

    @Override
    public void addAddress(UpdateAddressBody body) {
        ApiFactory.addAddress(body).subscribe(new ProgressSubscriber<ApiResponse>(mView.getContext()){
            @Override
            public void onNext(ApiResponse value) {
                super.onNext(value);
                mView.showSuccess(null,4,value.getMsg());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError(e.getMessage());
            }
        });
    }

    @Override
    public void updateAddress(UpdateAddressBody body) {
        ApiFactory.modifyAddress(body).subscribe(new ProgressSubscriber<ApiResponse>(mView.getContext()){
            @Override
            public void onNext(ApiResponse value) {
                super.onNext(value);
                mView.showSuccess(null,5,value.getMsg());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError(e.getMessage());
            }
        });
    }
}
