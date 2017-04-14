package com.thinkeract.tka.widget;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.widget.timepicker.adapter.NumericWheelAdapter;
import com.thinkeract.tka.widget.timepicker.lib.WheelView;
import com.thinkeract.tka.widget.timepicker.view.BasePickerView;
import com.zitech.framework.widget.ActionSheetDialog;
import com.zitech.framework.widget.ActionSheetHelper;

/**
 * Created by minHeng on 2016/11/15 10:35.
 * mail:minhengyan@gmail.com
 */

public class AgePicker extends BasePickerView implements View.OnClickListener {

    ActionSheetDialog sheetDialog;
    View contentView;
    Activity mContext;

    private AgePickerListener listener;
    private TextView close;
    private TextView sure;
    public static final int MAX_AGE = 130;
    public static final int MIN_AGE = 1;
    private WheelView ageWheelView;
    private int currentAge;

    public void setLastSelect(int currentAge) {
        this.currentAge = currentAge-1;
    }

    public interface AgePickerListener {
        void onPicked(int selectedAge);
    }

    public AgePicker(Activity context) {
        super(context);
        mContext = context;
        initViews();
        close.setOnClickListener(this);
        sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == close) {
            if (sheetDialog != null && sheetDialog.isShowing()) {
                sheetDialog.dismiss();
            }
        } else if (v == sure) {
            if (sheetDialog != null && sheetDialog.isShowing()) {
                sheetDialog.dismiss();
            }
            if (listener != null) {
                listener.onPicked(ageWheelView.getCurrentItem()+1);
            }
        }
    }

    private void initViews() {
        contentView = mContext.getLayoutInflater().inflate(R.layout.age_picker, null);
        close = (TextView) contentView.findViewById(R.id.close);
        sure = (TextView) contentView.findViewById(R.id.sure);
        ageWheelView = (WheelView) contentView.findViewById(R.id.ageWheelView);
        ageWheelView.setCyclic(false);

        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(MIN_AGE, MAX_AGE);
        ageWheelView.setAdapter(numericWheelAdapter);
    }

    @Override
    public void show() {
        if (sheetDialog == null) {
            sheetDialog = ActionSheetHelper.createActionDialog(mContext, contentView);
        }
        ageWheelView.setCurrentItem(currentAge);
        sheetDialog.show();
    }

    public void setListener(AgePickerListener listener) {
        this.listener = listener;
    }

}


