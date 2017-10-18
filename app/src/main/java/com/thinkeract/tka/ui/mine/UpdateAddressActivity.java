package com.thinkeract.tka.ui.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.Events;
import com.thinkeract.tka.R;
import com.thinkeract.tka.ThinkerActApplication;
import com.thinkeract.tka.common.utils.DBUtils;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.entity.AddressItem;
import com.thinkeract.tka.data.api.entity.AreaVO;
import com.thinkeract.tka.data.api.request.UpdateAddressBody;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.mine.contract.AddressStuffContract;
import com.thinkeract.tka.ui.mine.presenter.AddressStuffPresenter;
import com.thinkeract.tka.widget.timepicker.OptionsPickerView;
import com.zitech.framework.utils.ToastMaster;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minHeng on 2017/4/18 15:58.
 * mail:minhengyan@gmail.com
 */

public class UpdateAddressActivity extends AppBarActivity implements AddressStuffContract.View {
    private AddressItem mAddress;
    private EditText contactNameEt;
    private EditText phoneNumEt;
    private TextView provinceTv;
    private RelativeLayout provinceLayout;
    private TextView cityTv;
    private RelativeLayout cityLayout;
    private TextView areaTv;
    private RelativeLayout areaLayout;
    private EditText detailAddressEt;
    private Button saveBtn;
    private ArrayList<AreaVO> provinceList;
    private SparseArray<ArrayList<AreaVO>> cityMap;
    private SparseArray<ArrayList<AreaVO>> areaMap;
    private OptionsPickerView<AreaVO> provincePicker;
    private OptionsPickerView<AreaVO> cityPicker;
    private OptionsPickerView<AreaVO> areaPicker;
    private AreaVO mCurrentProvince;
    private AreaVO mCurrentCity;
    private AreaVO mCurrentArea;
    private AddressStuffPresenter mPresenter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_update_address;
    }

    @Override
    protected void initView() {
        mAddress = getIntent().getParcelableExtra(Constants.ActivityExtra.ADDRESS_ITEM);
        setTitle(mAddress != null ? "编辑地址" : "添加地址");
        initialize();
        provinceLayout.setOnClickListener(this);
        cityLayout.setOnClickListener(this);
        areaLayout.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }

    private void initialize() {
        contactNameEt = (EditText) findViewById(R.id.contactNameEt);
        phoneNumEt = (EditText) findViewById(R.id.phoneNumEt);
        provinceTv = (TextView) findViewById(R.id.provinceTv);
        provinceLayout = (RelativeLayout) findViewById(R.id.provinceLayout);
        cityTv = (TextView) findViewById(R.id.cityTv);
        cityLayout = (RelativeLayout) findViewById(R.id.cityLayout);
        areaTv = (TextView) findViewById(R.id.areaTv);
        areaLayout = (RelativeLayout) findViewById(R.id.areaLayout);
        detailAddressEt = (EditText) findViewById(R.id.detailAddressEt);
        saveBtn = (Button) findViewById(R.id.saveBtn);
    }

    @Override
    protected void initData() {
        mPresenter = new AddressStuffPresenter(this);
        if (mAddress != null) {
            contactNameEt.setText(mAddress.getContact());
            contactNameEt.setSelection(mAddress.getContact().length());
            phoneNumEt.setText(mAddress.getPhone());
            String cityAddress = mAddress.getCityname();
            String[] cityArray = cityAddress.split(",");
            if(cityArray.length == 3){
                provinceTv.setText(cityArray[0]);
                cityTv.setText(cityArray[1]);
                areaTv.setText(cityArray[2]);
            }
            detailAddressEt.setText(mAddress.getAddress());
        }
        provinceList = ThinkerActApplication.getInstance().getEnhancedPlace().getRegion().provinceList;
        cityMap = ThinkerActApplication.getInstance().getEnhancedPlace().getRegion().cityMap;
        areaMap = ThinkerActApplication.getInstance().getEnhancedPlace().getRegion().areaMap;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.provinceLayout:
                if (provincePicker == null) {
                    provincePicker = new OptionsPickerView<>(this);
                    provincePicker.setPicker(provinceList);
                    provincePicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3) {
                            mCurrentProvince = provinceList.get(options1);
                            provinceTv.setText(mCurrentProvince.getVal());
                            cityTv.setText("");
                            cityTv.setHint("请选择");
                            areaTv.setText("");
                            areaTv.setHint("请选择");
                        }
                    });
                }
                provincePicker.show();
                break;
            case R.id.cityLayout:
                if (mCurrentProvince == null) {
                    ToastMaster.shortToast("请先选择省份哦");
                    return;
                }
                if (cityPicker == null) {
                    cityPicker = new OptionsPickerView<>(this);
                    cityPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3) {
                            mCurrentCity = cityMap.get(mCurrentProvince.getKey()).get(options1);
                            cityTv.setText(mCurrentCity.getVal());
                            areaTv.setText("");
                            areaTv.setHint("请选择");
                        }
                    });
                }
                cityPicker.setPicker(cityMap.get(mCurrentProvince.getKey()));
                cityPicker.show();
                break;
            case R.id.areaLayout:
                if (mCurrentCity == null) {
                    ToastMaster.shortToast("请先选择一个城市吧");
                    return;
                }
                if (areaPicker == null) {
                    areaPicker = new OptionsPickerView<>(this);
                    areaPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3) {
                            mCurrentArea = areaMap.get(mCurrentCity.getKey()).get(options1);
                            areaTv.setText(mCurrentArea.getVal());
                        }
                    });
                }
                areaPicker.setPicker(areaMap.get(mCurrentCity.getKey()));
                areaPicker.show();
                break;
            case R.id.saveBtn:
                String contactName = contactNameEt.getText().toString();
                if (TextUtils.isEmpty(contactName)) {
                    ToastMaster.shortToast("收件人姓名不能为空");
                    return;
                }

                String phoneNumber = phoneNumEt.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)) {
                    ToastMaster.shortToast("手机号码不能为空");
                    return;
                }

                String province = provinceTv.getText().toString();
                if (TextUtils.isEmpty(province)) {
                    ToastMaster.shortToast("请选择省份");
                    return;
                }

                String city = cityTv.getText().toString();
                if (TextUtils.isEmpty(city)) {
                    ToastMaster.shortToast("请选择城市");
                    return;
                }
                String area = areaTv.getText().toString();
                if (TextUtils.isEmpty(area)) {
                    ToastMaster.shortToast("请选择区县");
                    return;
                }

                String detail = detailAddressEt.getText().toString();
                if (TextUtils.isEmpty(detail)) {
                    ToastMaster.shortToast("请填写详细地址");
                    return;
                }

                UpdateAddressBody body = new UpdateAddressBody();
                body.setContact(contactName);
                body.setPhone(phoneNumber);
                body.setCityname(province + "," + city + "," + area);
                body.setAddress(detail);
                if (DBUtils.queryDefAddress() == null) {
                    body.setStatus("1");
                }
                if(mAddress != null) {
                    body.setId(String.valueOf(mAddress.getId()));
                    mPresenter.updateAddress(body);
                }else {
                    mPresenter.addAddress(body);
                }
                break;
        }
    }


    @Override
    public void showError(String message) {

    }

    @Override
    public void showSuccess(List<AddressItem> addressItems, int stuffType, String message) {
        ToastMaster.shortToast(message);
        ThinkerActApplication.getInstance().postDelay(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new Events.AddressChangeEvent());
                finish();
            }
        }, 400);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (ViewUtils.isTouchedOutsideView(v, ev)) {
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public static void launch(Activity activity, AddressItem addressItem) {
        Intent intent = new Intent(activity, UpdateAddressActivity.class);
        intent.putExtra(Constants.ActivityExtra.ADDRESS_ITEM, addressItem);
        activity.startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN, activity);
    }

}
