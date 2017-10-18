package com.thinkeract.tka.ui.mine.contract;

import com.thinkeract.tka.data.api.entity.AddressItem;
import com.thinkeract.tka.data.api.request.UpdateAddressBody;
import com.thinkeract.tka.ui.BasePresenter;
import com.thinkeract.tka.ui.BaseView;

import java.util.List;

/**
 * Created by ymh on 2017/10/18 21:48
 * e-mail:minhengyan@gmail.com
 */

public class AddressStuffContract {

    public interface View extends BaseView<AddressStuffContract.Presenter> {
        public void showError(String message);
        //stuffType 1,获取收货地址列表，2设为默认地址 3删除地址 4新增地址 5修改地址
        public void showSuccess(List<AddressItem> addressItems,int stuffType,String message);

    }

    public interface Presenter extends BasePresenter {
        void getAddressList();
        void setDefAddress(UpdateAddressBody body);
        void deleteAddress(String id);
        void addAddress(UpdateAddressBody body);
        void updateAddress(UpdateAddressBody body);
    }

}
