package com.thinkeract.tka.widget.timepicker.view;

import android.content.Context;
import android.view.View;

import com.thinkeract.tka.R;
import com.thinkeract.tka.widget.timepicker.TimePickerView;
import com.thinkeract.tka.widget.timepicker.adapter.NumericWheelAdapter;
import com.thinkeract.tka.widget.timepicker.lib.WheelView;
import com.thinkeract.tka.widget.timepicker.listener.OnItemSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class WheelTime {
    public DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_minutes;
    private int textSize = 8; //字体大小设置


    private TimePickerView.Type type;
    private int START_YEAR = 1900, END_YEAR = 2020;
    private int MIN_MONTH = 1;
    private int MIN_DAY = 1;
    private int MIN_HOUR = 0;
    private int MIN_MINS = 0;
    private boolean isMinYear;
    private boolean isMinMonth;
    private boolean isMinDay;
    int startMonth = 1;
    int startDay = 1;
    int startHour = 0;
    int startMinute = 0;

    private int MAX_MONTH = 1;
    private int MAX_DAY = 1;
    private int MAX_HOUR = 0;
    private int MAX_MINS = 0;
    private boolean isMaxYear;
    private boolean isMaxMonth;
    private boolean isMaxDay;
    int endMonth = 12;
    int endDay = 30;
    int endHour = 23;
    int endMinute = 59;
    private OnCurrentSelectedListener onCurrentSelectedListener;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getStartYear() {
        return START_YEAR;
    }

    public void setStartYear(int sTART_YEAR) {
        START_YEAR = sTART_YEAR;
    }

    public int getEndYear() {
        return END_YEAR;
    }

    public void setEndYear(int eND_YEAR) {
        END_YEAR = eND_YEAR;
    }

    public WheelTime(View view) {
        super();
        this.view = view;
        type = TimePickerView.Type.ALL;
        setView(view);
    }

    public WheelTime(View view, TimePickerView.Type type) {
        super();
        this.view = view;
        this.type = type;
        setView(view);
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0);
    }

    /**
     * @Description: TODO 弹出日期时间选择器
     */
    public void setPicker(int year, final int month, int day, int h, int m) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        isMinYear = (year == START_YEAR);
        isMinMonth = (month == MIN_MONTH);
        isMinDay = (day == MIN_DAY);

        Context context = view.getContext();
        // 年
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
        wv_year.setLabel(context.getString(R.string.year));// 添加文字
        wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据

        // 月
        wv_month = (WheelView) view.findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(START_YEAR == year ? MIN_MONTH : 1, 12));
        wv_month.setLabel(context.getString(R.string.month));
        wv_month.setCurrentItem(month - (START_YEAR == year ? MIN_MONTH : 1) + 1);

        // 日
        wv_day = (WheelView) view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(MIN_MONTH == (month + 1) ? MIN_DAY : 1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(MIN_MONTH == (month + 1) ? MIN_DAY : 1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                wv_day.setAdapter(new NumericWheelAdapter(MIN_MONTH == (month + 1) ? MIN_DAY : 1, 29));
            else
                wv_day.setAdapter(new NumericWheelAdapter(MIN_MONTH == (month + 1) ? MIN_DAY : 1, 28));
        }
        wv_day.setLabel(context.getString(R.string.day));
        wv_day.setCurrentItem(day - (MIN_MONTH == (month + 1) ? MIN_DAY : 1));


        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(MIN_DAY == day ? MIN_HOUR : 0, 23));
        wv_hours.setLabel(context.getString(R.string.hours));// 添加文字
        wv_hours.setCurrentItem(h - (MIN_DAY == day ? MIN_HOUR : 0));

        wv_minutes = (WheelView) view.findViewById(R.id.min);
        wv_minutes.setAdapter(new NumericWheelAdapter(MIN_HOUR == h ? MIN_MINS : 0, 59));
        wv_minutes.setLabel(context.getString(R.string.minutes));// 添加文字
        wv_minutes.setCurrentItem(m - (MIN_HOUR == h ? MIN_MINS : 0));

        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + START_YEAR;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (year_num == START_YEAR) {
                    startMonth = MIN_MONTH;
                    startDay = MIN_DAY;
                    startHour = MIN_HOUR;
                    startMinute = MIN_MINS;
                    isMinYear = true;
                } else {
                    startMonth = 1;
                    startDay = 1;
                    startHour = 0;
                    startMinute = 0;
                    isMinYear = false;
                }
                if (year_num == END_YEAR) {
                    endMonth = MAX_MONTH;
                    endDay = MAX_DAY;
                    endHour = MAX_HOUR;
                    endMinute = MAX_MINS;
                    isMaxYear = true;
                } else {
                    endMonth = 12;
                    endDay = 30;
                    endHour = 23;
                    endMinute = 59;
                    isMaxYear = false;
                }

                int maxItem = 30;
                wv_month.setAdapter(new NumericWheelAdapter(startMonth, endMonth));
                if (list_big
                        .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(startDay, isMaxMonth?endDay:31));
                    maxItem = isMaxMonth?endDay:31;
                } else if (list_little.contains(String.valueOf(wv_month
                        .getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(startDay, isMaxMonth?endDay:30));
                    maxItem = isMaxMonth?endDay:30;
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0)
                            || year_num % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(startDay, isMaxMonth?endDay:29));
                        maxItem = isMaxMonth?endDay:29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(startDay, isMaxMonth?endDay:28));
                        maxItem = isMaxMonth?endDay:28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }
                wv_hours.setAdapter(new NumericWheelAdapter(startHour, 23));
                wv_minutes.setAdapter(new NumericWheelAdapter(startMinute, 59));

                if(onCurrentSelectedListener != null)
                    onCurrentSelectedListener.onCurrentSelected();
            }
        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index + MIN_MONTH;
                if (month_num == MIN_MONTH && isMinYear) {
                    startDay = MIN_DAY;
                    startHour = MIN_HOUR;
                    startMinute = MIN_MINS;
                    isMinMonth = true;
                } else {
                    startDay = 1;
                    startHour = 0;
                    startMinute = 0;
                    isMinMonth = false;
                }

                if (month_num == MAX_MONTH && isMaxYear) {
                    endMonth = MAX_MONTH;
                    endDay = MAX_DAY;
                    endHour = MAX_HOUR;
                    endMinute = MAX_MINS;
                    isMaxMonth = true;
                } else {
                    endMonth = 12;
                    endDay = 30;
                    endHour = 23;
                    endMinute = 59;
                    isMaxMonth = false;
                }
                int maxItem = 30;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(startDay, isMaxMonth?endDay:31));
                    maxItem = isMaxMonth?endDay:31;
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(startDay, isMaxMonth?endDay:30));
                    maxItem = isMaxMonth?endDay:30;
                } else {
                    if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
                            .getCurrentItem() + START_YEAR) % 100 != 0)
                            || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(startDay, isMaxMonth?endDay:29));
                        maxItem = isMaxMonth?endDay:29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(startDay, isMaxMonth?endDay:28));
                        maxItem = isMaxMonth?endDay:28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }
                wv_hours.setAdapter(new NumericWheelAdapter(startHour, 23));
                wv_minutes.setAdapter(new NumericWheelAdapter(startMinute, 59));

                if(onCurrentSelectedListener != null)
                    onCurrentSelectedListener.onCurrentSelected();
            }
        };

        //添加天的监听
        OnItemSelectedListener wheelListenr_days = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                int days = index + MIN_DAY;
                if (days == MIN_DAY && isMinYear && isMinMonth) {
                    startHour = MIN_HOUR;
                    startMinute = MIN_MINS;
                    isMinDay = true;
                } else {
                    startHour = 0;
                    startMinute = 0;
                    isMinDay = false;
                }
                wv_hours.setAdapter(new NumericWheelAdapter(startHour, 23));
                wv_minutes.setAdapter(new NumericWheelAdapter(startMinute, 59));

                if(onCurrentSelectedListener != null)
                    onCurrentSelectedListener.onCurrentSelected();
            }
        };

        //添加小时的监听
        OnItemSelectedListener wheelListenr_hours = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                int hours = index + MIN_HOUR;
                if (hours == MIN_HOUR && isMinYear && isMinMonth && isMinDay)
                    startMinute = MIN_MINS;
                else
                    startMinute = 0;
                wv_minutes.setAdapter(new NumericWheelAdapter(startMinute, 59));
            }
        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);
        wv_day.setOnItemSelectedListener(wheelListenr_days);
        wv_hours.setOnItemSelectedListener(wheelListenr_hours);

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        textSize = 4;
        switch (type) {
            case ALL:
                textSize = textSize * 4;
                break;
            case YEAR_MONTH_DAY:
                textSize = textSize * 4;
                wv_hours.setVisibility(View.GONE);
                wv_minutes.setVisibility(View.GONE);
                break;
            case HOURS_MINS:
                textSize = textSize * 4;
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                break;
            case MONTH_DAY_HOUR_MIN:
                textSize = textSize * 3;
                wv_year.setVisibility(View.GONE);
                break;
            case YEAR_MONTH:
                textSize = textSize * 4;
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_minutes.setVisibility(View.GONE);
            case DAY:
                textSize = textSize * 4;
                wv_month.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_minutes.setVisibility(View.GONE);
                wv_year.setVisibility(View.GONE);
        }
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_minutes.setTextSize(textSize);

    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_minutes.setCyclic(cyclic);
    }

    public String getTime() {
        StringBuffer sb = new StringBuffer();
        int selectYear = (wv_year.getCurrentItem() + START_YEAR);
        int selectMonth = (wv_month.getCurrentItem() + (START_YEAR == selectYear ? MIN_MONTH : 1));
        int selectDay = (wv_day.getCurrentItem() + (MIN_MONTH == (selectMonth) ? MIN_DAY : 1));
        int selectHour = (wv_hours.getCurrentItem() + (MIN_DAY == selectDay ? MIN_HOUR : 0));
        int selectMinute = (wv_minutes.getCurrentItem() + (MIN_HOUR == selectHour ? MIN_MINS : 0));
        sb.append(selectYear).append("-")
                .append(selectMonth).append("-")
                .append(selectDay).append(" ")
                .append(selectHour).append(":")
                .append(selectMinute);
        return sb.toString();
    }

    public void setMinTime(int year, int month, int day, int hours, int minute) {
        START_YEAR = year;
        MIN_MONTH = startMonth = month + 1;
        MIN_DAY = startDay = day;
        MIN_HOUR = startHour = hours;
        MIN_MINS = startMinute = minute;
        isMinYear = true;
        isMinMonth = true;
        isMinDay = true;
    }

    public void setMaxTime(int year, int month, int day, int hours, int minute) {
        END_YEAR = year;
        MAX_MONTH = endMonth = month + 1;
        MAX_DAY = endDay = day;
        MAX_HOUR = endHour = hours;
        MAX_MINS = endMinute = minute;
    }

    public interface OnCurrentSelectedListener{
        void onCurrentSelected();
    }

    public void setOnCurrentSelectedListener(OnCurrentSelectedListener onCurrentSelectedListener){
        this.onCurrentSelectedListener = onCurrentSelectedListener;
    }
}
