package com.thinkeract.tka.ui.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.ThinkerActApplication;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.entity.AddressItem;
import com.thinkeract.tka.data.api.entity.AreaVO;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.widget.timepicker.OptionsPickerView;
import com.zitech.framework.utils.ToastMaster;

import java.util.ArrayList;

/**
 * Created by minHeng on 2017/4/18 15:58.
 * mail:minhengyan@gmail.com
 */

public class UpdateAddressActivity extends AppBarActivity{
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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_update_address;
    }

    @Override
    protected void initView() {
        mAddress = getIntent().getParcelableExtra(Constants.ActivityExtra.ADDRESS_ITEM);
        setTitle(mAddress!=null?"编辑地址":"添加地址");
        initialize();
        provinceLayout.setOnClickListener(this);
        cityLayout.setOnClickListener(this);
        areaLayout.setOnClickListener(this);
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
        if(mAddress != null){
            contactNameEt.setText(mAddress.getContact());
            contactNameEt.setSelection(mAddress.getContact().length());
            phoneNumEt.setText(mAddress.getPhone());
//            provinceTv.setText();
            detailAddressEt.setText(mAddress.getAddress());
        }
        provinceList = ThinkerActApplication.getInstance().getEnhancedPlace().getRegion().provinceList;
        cityMap = ThinkerActApplication.getInstance().getEnhancedPlace().getRegion().cityMap;
        areaMap = ThinkerActApplication.getInstance().getEnhancedPlace().getRegion().areaMap;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.provinceLayout:
                if(provincePicker == null) {
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
                if(mCurrentProvince == null){
                    ToastMaster.shortToast("请先选择省份哦");
                    return;
                }
                if(cityPicker == null) {
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
                if(mCurrentCity == null){
                    ToastMaster.shortToast("请先选择一个城市吧");
                    return;
                }
                if(areaPicker == null) {
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
        }
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

    public static void launch(Activity activity, AddressItem addressItem){
        Intent intent = new Intent(activity,UpdateAddressActivity.class);
        intent.putExtra(Constants.ActivityExtra.ADDRESS_ITEM,addressItem);
        activity.startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN,activity);
    }
}
