package com.thinkeract.tka.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * handle date and datetime
 * <p>
 * Notice: All of the time operating here, please bsn
 */
public class DateUtil {
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATETIME_B = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_CHINESE_DATE = "yyyy年MM月dd日 HH:mm";
    public static final String FORMAT_HOUR_MINUTE_DATE = "HH:mm";
    private static final int MINUTE = 1000 * 60;


    /**
     * formate date
     *
     * @param d
     * @param format
     * @return
     */
    public static String format(Date d, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        return sdf.format(d);
    }

    /**
     * formate time
     *
     * @param time
     * @param format
     * @return
     */
    public static String format(long time, String format) {
        return format(new Date(time), format);
    }

    public static Date parse(String s, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(s);
        } catch (Exception e) {
            return null;
        }
    }


    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    public static int getDayOfMOnth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    public static int getMonth(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MONTH);
    }

    public static int getDayOfMOnth(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isSameDay(long dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTimeInMillis(dateTime);
        int saveDay = calendar.get(Calendar.DAY_OF_YEAR);
        if (today == saveDay) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 最近联系人 界面所显示时间
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    @SuppressWarnings("deprecation")
    public static String parseTimeForRecentList(Date date) {
        SimpleDateFormat formatter = null;
        Date currentTime = new Date();
        if (currentTime.getYear() != date.getYear()) {
            formatter = new SimpleDateFormat("yy年M月", Locale.CHINESE);
        } else if (currentTime.getMonth() == date.getMonth() && currentTime.getDate() == date.getDate()) {
            formatter = new SimpleDateFormat("HH:mm", Locale.CHINESE);
        } else if (currentTime.getMonth() == date.getMonth() && currentTime.getDate() == date.getDate() + 1) {
            formatter = new SimpleDateFormat("昨天  HH:mm", Locale.CHINESE);
        } else if (currentTime.getMonth() == date.getMonth() && currentTime.getDate() == date.getDate() + 2) {
            formatter = new SimpleDateFormat("前天  HH:mm", Locale.CHINESE);
        } else {
            formatter = new SimpleDateFormat("M月dd日", Locale.CHINESE);
        }
        return formatter.format(date);
    }
    /**
     * 当前对话所显示的时间
     *
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("SimpleDateFormat")
    public static String parseTimeForCurrentChat(Date date) {
        SimpleDateFormat formatter = null;
        Date currentTime = new Date();
        if (currentTime.getYear() != date.getYear()) {
            formatter = new SimpleDateFormat("yy年M月d日", Locale.CHINESE);

        } else {
            formatter = new SimpleDateFormat("M月dd日 HH:mm", Locale.CHINESE);
            if (currentTime.getMonth() == date.getMonth()) {
                if (currentTime.getDate() == date.getDate()) {
                    formatter = new SimpleDateFormat("HH:mm");
                    long minutes = (currentTime.getTime() - date.getTime()) / MINUTE;
                    if (minutes < 60)
                        if (minutes == 0)
                            return "1分钟内";
                        else
                            return minutes + "分钟前";
                    // return

                } else if (currentTime.getDate() == date.getDate() + 1) {
                    formatter = new SimpleDateFormat("昨天  HH:mm", Locale.CHINESE);
                } else if (currentTime.getDate() == date.getDate() + 2) {
                    formatter = new SimpleDateFormat("前天  HH:mm", Locale.CHINESE);
                }
            }
        }
        return formatter.format(date);

    }

    /**
     * 2015-05-02 12:12:12.0转成2015-05-02
     * */
    public static String getShortTimeFormat(String startTime) {
        if (!TextUtils.isEmpty(startTime)) {
            DateFormat dfBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
            try {
                return df.format(dfBefore.parse(startTime));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                return startTime;
            }
        } else {
            return "";
        }

    }

    /**
     * 2015-05-02 12:12:12.0转成2015-05-02
     * */
    public static String getNormalTimeFormat(String startTime) {
        if (!TextUtils.isEmpty(startTime)) {
            DateFormat dfBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault());
            try {
                return df.format(dfBefore.parse(startTime));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                return startTime;
            }
        } else {
            return "";
        }

    }

    /**
     * 2015-05-02 12:12:12.0转成2015年05月02日
     * */
    public static String getShortTimeZhFormat(String startTime) {
        if (!TextUtils.isEmpty(startTime)) {
            DateFormat dfBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
            DateFormat df = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINESE);
            try {
                return df.format(dfBefore.parse(startTime));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                return startTime;
            }
        } else {
            return "";
        }

    }

    /**
     * 2015-05-02 12:12:12.0转成2015年05月02日 HH时mm分
     * */
    public static String getShortTimeZhMinuteFormat(String startTime) {
        if (!TextUtils.isEmpty(startTime)) {
            DateFormat dfBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
            DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINESE);
            try {
                return df.format(dfBefore.parse(startTime));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                return startTime;
            }
        } else {
            return "";
        }

    }

    /**
     * 2015-05-02 12:12:12.0转成2015-05-02 HH:mm
     * */
    public static String getShortTimeNumMinuteFormat(String startTime) {
        if (!TextUtils.isEmpty(startTime)) {
            DateFormat dfBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINESE);
            try {
                return df.format(dfBefore.parse(startTime));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                return startTime;
            }
        } else {
            return "";
        }

    }

    /**
     * 将以秒为单位的时间转换为时分秒即 00:00:00格式
     *
     * @param time
     *            秒数
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }
    private static String unitFormat(int i) {
        String retStr;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


    /**
     * startDatetime format: HH:mm
     */
    public static String getCurrentTime(long sStartTime, Context ctx) {
        Date date=new Date(sStartTime);
        Format format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

}
