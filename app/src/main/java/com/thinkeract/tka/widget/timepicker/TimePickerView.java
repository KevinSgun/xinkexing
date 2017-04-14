package com.thinkeract.tka.widget.timepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.widget.timepicker.view.BasePickerView;
import com.thinkeract.tka.widget.timepicker.view.WheelTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by minHeng on 15/11/22 10:32.
 * mail:minhengyan@gmail.com
 */
public class TimePickerView extends BasePickerView implements View.OnClickListener {
    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN , YEAR_MONTH, DAY
    }// 四种选择模式，年月日时分，年月日，时分，月日时分,   日

    WheelTime wheelTime;
    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private OnTimeSelectListener timeSelectListener;

    public TimePickerView(Context context, Type type) {
        super(context);

        mContentView = LayoutInflater.from(context).inflate(R.layout.pickerview_time, null);
        // -----确定和取消按钮
        btnSubmit = mContentView.findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = mContentView.findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) mContentView.findViewById(R.id.tvTitle);
        // ----时间转轮
        final View timepickerview = mContentView.findViewById(R.id.timepicker);
        wheelTime = new WheelTime(timepickerview, type);

        //默认选中当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);

    }

    /**
     * 设置可以选择的时间范围
     *
     * @param START_YEAR
     * @param END_YEAR
     */
    public void setRange(int START_YEAR, int END_YEAR) {
        wheelTime.setStartYear(START_YEAR);
        wheelTime.setEndYear(END_YEAR);
    }

    /**
     * 设置选中时间
     * @param date
     */
    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        minute = (minute<59?(minute+1):minute);
        wheelTime.setPicker(year, month, day, hours, minute);
    }

    /**
     * 设置选中时间，且该时间为最小可选时间
     * @param date
     */
    public void setTimeWithMinTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        minute = (minute<59?(minute+1):minute);
        wheelTime.setMinTime(year,month,day,hours,minute);
        wheelTime.setPicker(year, month, day, hours, minute);
    }

    /**
     * 设置最小可选时间
     * @param date
     */
    public void setMinTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        minute = (minute<59?(minute+1):minute);
        wheelTime.setMinTime(year,month,day,hours,minute);
    }


    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (timeSelectListener != null) {
                try {
                    Date date = wheelTime.dateFormat.parse(wheelTime.getTime());
                    timeSelectListener.onTimeSelect(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dismiss();
            return;
        }
    }

    public interface OnTimeSelectListener {
        void onTimeSelect(Date date);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }
}
