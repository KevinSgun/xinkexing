package com.thinkeract.tka.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mozillaonline.providers.DownloadManager;
import com.mozillaonline.providers.downloads.DownloadInfo;
import com.thinkeract.tka.Constants;
import com.zitech.framework.BaseApplication;
import com.zitech.framework.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by minHeng on 16/7/7 18:31.
 * mail:minhengyan@gmail.com
 */
public class Utils extends com.zitech.framework.utils.Utils {
    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isPackageInstalled(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }


    public static String readFromAssets(Context context, String name) {
        try {
            InputStream e = context.getResources().getAssets().open(name);
            byte[] buffer = new byte[1];
            ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
            int len = -1;
            while (-1 != (len = e.read(buffer))) {
//                bIn.append(buffer, 0, buffer.length);
                bout.write(buffer, 0, len);
            }
            e.close();
            String res = new String(bout.toByteArray());
            return res.trim();
        } catch (IOException var6) {
            var6.printStackTrace();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return null;
    }

    public static boolean needUpdate(String minVersion) {
        String currentVersion = Session.getInstance().getVersionName();
        Logger.i("VERSION", "version:" + currentVersion + ",min version:" + minVersion);
        if (!TextUtils.isEmpty(minVersion) && !TextUtils.isEmpty(currentVersion)) {
            String currentVersions[] = currentVersion.split(".");
            String minVersions[] = minVersion.split(".");
            if (currentVersions.length == minVersions.length) {
                for (int i = 0; i < currentVersions.length; i++) {
                    int current = Integer.parseInt(currentVersions[i]);
                    int min = Integer.parseInt(minVersions[i]);
                    if (current > min) {
                        return false;
                    } else if (current < min) {
                        return true;
                    }
                }

            }
        }
        return false;
    }


    public static String complateUserJid(String userJid, boolean addResouce) {
        if (userJid == null)
            return null;
        if (!userJid.contains("@")) {
            userJid = userJid + "@" + Constants.XMPP_SERVER_NAME;
        }
        if (addResouce && !userJid.endsWith("Smack")) {
            userJid += "/Smack";
        }
        return userJid;
    }

    @SuppressLint("SimpleDateFormat")
    public static Age caculateAge(String birthday) {
        if (birthday == null) {
            return new Age();
        }
        java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Calendar from = Calendar.getInstance();
        try {
            from.setTime(df.parse(birthday));
        } catch (ParseException e) {
            birthday = "";
        }
        if ("".equals(birthday)) {
            Age brs = new Age();
            brs.setDays(0);
            brs.setMonth(0);
            return brs;
        } else {

            int yearbirthday = from.get(Calendar.YEAR);
            int monthbirthday = from.get(Calendar.MONTH);
            int daysbirthday = from.get(Calendar.DAY_OF_MONTH);

            GregorianCalendar to = (GregorianCalendar) Calendar.getInstance();

            int yearTo = to.get(Calendar.YEAR);
            int monthTo = to.get(Calendar.MONTH);
            int daysTo = to.get(Calendar.DAY_OF_MONTH);
            Age age = new Age();
            if (from.getTimeInMillis() > to.getTimeInMillis()) {
                age.setDays(0);
                age.setMonth(0);
            } else {
                int monthResult = 12 * (yearTo - yearbirthday) + (monthTo - monthbirthday);
                int daysResult = daysTo - daysbirthday;
                if (daysResult < 0) {
                    monthResult = monthResult - 1;
                    int daysCount = getDaysOfMonth(monthTo - 1, to.isLeapYear(yearTo));

                    daysResult = daysCount + daysResult;
                }
                age.setDays(daysResult);
                age.setYear(monthResult / 12);
                age.setMonth(monthResult % 12);
            }
            return age;
        }
    }

    public static int getAgeByBirthdayInt(String birthday) {
        if (TextUtils.isEmpty(birthday))
            return 0;
        try {
            Calendar cal = Calendar.getInstance();
            Date curDate = DateUtil.parse(birthday, DateUtil.FORMAT_DATE);
            if (cal.before(curDate)) {
                return 0;
            }

            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH) + 1;
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

            cal.setTime(curDate);
            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH) + 1;
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

            int age = yearNow - yearBirth;

            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    // monthNow==monthBirth
                    if (dayOfMonthNow < dayOfMonthBirth) {
                        age--;
                    }
                } else {
                    // monthNow>monthBirth
                    age--;
                }
            }
            return age;
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * 根据用户生日计算年龄
     */
    public static String getAgeByBirthdayString(String birthday) {
        int ageStr = getAgeByBirthdayInt(birthday);
        if (ageStr == -1) {
            return "年龄保密";
        }
        return String.valueOf(ageStr + "岁");
    }

    private static int getDaysOfMonth(int month, boolean isLeepYear) {
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                return isLeepYear ? 29 : 28;
            default:
                return 30;
        }
    }

    public static String downloadAcc(String url) {
        DownloadManager manager = Session.getInstance().getDownloadManager();
        DownloadInfo info = manager.query(url);
        if (info == null) {
            String path = "/";
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setTitle("acc文件");
            request.setShowRunningNotification(false);
            request.setMimeType(com.mozillaonline.providers.downloads.Constants.MIMETYPE_ACC);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, path);
            request.setUid(url);
            manager.enqueue(request);
            return null;
        }

        return info.mFileName;
    }

    private static String mappingAccId(String path) {
        return Utils.md5(path) + ".acc";
    }

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy( Context context,String content) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    public static class Age {
        int days;
        int month;
        int year;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

    }

    public static String hidePhoneNum(String phoneNum) {
        String hideNum = "";
        if (!TextUtils.isEmpty(phoneNum) && phoneNum.length() == 11) {
            String first = phoneNum.substring(0, 3);
            String sec = phoneNum.substring(7, 11);
            hideNum = first + "****" + sec;
        }
        return hideNum;
    }

    // public static void reLogin() {
    // loginOut();
    // }

    /**
     * Fragment跳转， 将一个layout替换为新的fragment。
     *
     * @param fm
     * @param fragmentClass
     * @param replaceLayoutId
     * @param args
     */
    public static void replace(FragmentManager fm, Class<? extends Fragment> fragmentClass, int replaceLayoutId, Bundle args) {
        replace(fm, fragmentClass, replaceLayoutId, fragmentClass.getSimpleName(), args);
    }

    /**
     * Fragment跳转， 将一个layout替换为新的fragment。
     *
     * @param fm
     * @param replaceLayoutId
     * @param fragmentClass
     * @return
     */
    public static Fragment replace(FragmentManager fm, int replaceLayoutId, Class<? extends Fragment> fragmentClass) {
        return replace(fm, fragmentClass, replaceLayoutId, fragmentClass.getSimpleName(), null);
    }

    /**
     * Fragment跳转， 将一个layout替换为新的fragment。
     *
     * @param fm
     * @param fragmentClass
     * @param tag
     * @param args
     * @return
     */
    public static Fragment replace(FragmentManager fm, Class<? extends Fragment> fragmentClass, int replaceLayoutId, String tag,
                                   Bundle args) {
        // mIsCanEixt = false;
        Fragment fragment = fm.findFragmentByTag(tag);
        boolean isFragmentExist = true;
        if (fragment == null) {
            try {
                isFragmentExist = false;
                fragment = fragmentClass.newInstance();
                if (args != null)
                    fragment.setArguments(args);
                else {
                    fragment.setArguments(new Bundle());
                }

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            if (args != null) {
                if (fragment.getArguments() != null)
                    fragment.getArguments().putAll(args);
                else
                    fragment.setArguments(args);
            }
        }
        if (fragment == null)
            return null;
        if (fragment.isAdded()) {
            // fragment.onResume();
            return fragment;
        }
        FragmentTransaction ft = fm.beginTransaction();
        if (isFragmentExist) {
            ft.replace(replaceLayoutId, fragment);
        } else {
            ft.replace(replaceLayoutId, fragment, tag);
        }

        ft.addToBackStack(tag);
        ft.commitAllowingStateLoss();
        return fragment;
    }

    public static String formatMoney(Double amounts) {
        double doubleAmounts = Double.valueOf(String.valueOf(amounts));
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(0);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(doubleAmounts);
    }

    public static Double valueOfMoney(Double value) {
        return Double.valueOf(formatMoney(value));
    }

    /**
     * 浮点数加法，计算金额时使用 a+b
     *
     * @param a
     * @param b
     * @return 返回String类型
     */
    public static String DoubleAddString(Double a, Double b) {
        BigDecimal result = new BigDecimal(Double.toString(a)).add(new BigDecimal(Double.toString(b)));
        return result.toPlainString();
    }

    /**
     * 浮点数加法，计算金额时使用 a+b
     *
     * @param a
     * @param b
     * @return 返回Double型
     */
    public static Double DoubleAddDouble(Double a, Double b) {
        BigDecimal result = new BigDecimal(Double.toString(a)).add(new BigDecimal(Double.toString(b)));
        return result.doubleValue();
    }

    /**
     * 浮点数减法，计算金额时使用 a-b
     *
     * @param a
     * @param b
     * @return 返回String类型
     */
    public static String DoubleSubString(Double a, Double b) {
        BigDecimal result = new BigDecimal(Double.toString(a)).subtract(new BigDecimal(Double.toString(b)));
        return result.toPlainString();
    }

    /**
     * 浮点数减法，计算金额时使用 a-b
     *
     * @param a
     * @param b
     * @return 返回Double型
     */
    public static Double DoubleSubDouble(Double a, Double b) {
        BigDecimal result = new BigDecimal(Double.toString(a)).subtract(new BigDecimal(Double.toString(b)));
        return result.doubleValue();
    }

    /**
     * 浮点数除法，计算金额使用 a/b
     *
     * @param a
     * @param b
     * @return 返回String类型
     */
    public static String DoubleDivideString(double a, double b) {
        BigDecimal result = new BigDecimal(Double.toString(a)).divide(new BigDecimal(Double.toString(b)),2,RoundingMode.HALF_EVEN);
        return result.toPlainString();
    }

    /**
     * 浮点数除法，计算金额使用 a/b
     *
     * @param a
     * @param b
     * @return 返回Double类型
     */
    public static Double DoubleDivideDouble(double a, double b) {
        if(b == 0) return 0d;
        BigDecimal result = new BigDecimal(Double.toString(a)).divide(new BigDecimal(Double.toString(b)),2,RoundingMode.HALF_EVEN);
        return result.doubleValue();
    }

    /**
     * 浮点数乘法，计算金额时使用 a*b
     *
     * @param a
     * @param b
     * @return 返回String类型
     */
    public static String DoubleMultiplyString(Double a, Double b) {
        BigDecimal result = new BigDecimal(Double.toString(a)).multiply(new BigDecimal(Double.toString(b)));
        return result.toPlainString();
    }

    /**
     * 浮点数乘法，计算金额时使用 a*b
     *
     * @param a
     * @param b
     * @return 返回Double类型
     */
    public static Double DoubleMultiplyDouble(Double a, Double b) {
        BigDecimal result = new BigDecimal(Double.toString(a)).multiply(new BigDecimal(Double.toString(b)));
        return result.doubleValue();
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    public static ActivityManager.RunningTaskInfo getRunningTaskInfo() {
        ActivityManager am = (ActivityManager) BaseApplication.getInstance().getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> appTask = am.getRunningTasks(1);
        if (appTask != null)
            if (appTask.size() > 0)
                return appTask.get(0);
        return null;
    }

    public static boolean isInMyApp() {
        String packageName = BaseApplication.getInstance().getPackageName();
        ActivityManager.RunningTaskInfo info = getRunningTaskInfo();
        if (info != null && info.topActivity != null)
            if (info.topActivity.toString().contains(packageName))
                return true;
        return false;
    }

    public static String[] splitPictureUrls(String urls) {
        return urls != null ? urls.split("@X@") : null;
    }

//    public static String formartRmb(Double items_price) {
//        return String.format(BaseApplication.getInstance().getResources().getString(R.string.rmb), items_price);
//    }
//
//    public static String formartYuan(Double items_price) {
//        return String.format(BaseApplication.getInstance().getResources().getString(R.string.yuan), items_price);
//    }

    public interface OnVisitorAccountCreatedListener {
        public void onVistorAccountCreated(String id, String password);

        public void onCreateFailed(String reason);
    }

    public static String getMetaDataValue(String name, String def) {
        String value = getMetaDataValue(name);
        return (value == null) ? def : value;

    }

    private static String getMetaDataValue(String name) {

        Object value = null;

        PackageManager packageManager = BaseApplication.getInstance().getPackageManager();

        ApplicationInfo applicationInfo;

        try {

            applicationInfo = packageManager.getApplicationInfo(BaseApplication.getInstance()

                    .getPackageName(), PackageManager.GET_META_DATA);

            if (applicationInfo != null && applicationInfo.metaData != null) {

                value = applicationInfo.metaData.get(name);

            }

        } catch (PackageManager.NameNotFoundException e) {

        }

        return value == null ? null : value.toString();

    }

    public static String formatDueTime(String endTime) {
        String display;
        try {
            Date date = DateUtil.parse(endTime, DateUtil.FORMAT_DATETIME);
            display = DateUtil.format(date, DateUtil.FORMAT_DATE);

        } catch (Exception e) {
            display = endTime;
        }
        return display + " 到期";
    }

    public static String hidePartCode(String code) {
        if (TextUtils.isEmpty(code)) return "******";
        if (code.length() == 1 || code.length() == 2) return code + "******";
        return code.substring(0, 3) + "******";
    }

    /**
     * json对象转为Map对象
     * @param obj
     * @return
     */
    public static HashMap<String, Object> convertToMapParams(Object obj) {
        if (obj == null) return null;
        String jString;
        if (obj instanceof String)
            jString = (String) obj;
        else {
            Gson gson = new Gson();
            jString = gson.toJson(obj);
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> arrKey = null;
        HashMap<String, Object> map = new HashMap<>();
        if (jsonObject != null) arrKey = jsonObject.keys();
        if (arrKey != null)
            while (arrKey.hasNext()) {
                String key = arrKey.next();
                Object value = null;
                try {
                    value = jsonObject.get(key);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                map.put(key, value);
            }
        return map;
    }

    /**
     * map对象按键字母排序
     * @param oriMap
     * @return 返回排好序的json字符串
     */
    public static String sortTransform(HashMap<String, Object> oriMap) {
        if (oriMap == null) return null;
        Set keySet = oriMap.keySet();
        String[] keys = new String[keySet.size()];
        keySet.toArray(keys);
        Arrays.sort(keys);
        if(keys.length==0)
            return "";
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < keys.length; i++) {
            sb.append("\"").append(keys[i]).append("\"").append(":");
            if (oriMap.get(keys[i]) instanceof String) {
                sb.append("\"").append(oriMap.get(keys[i])).append("\"");
            }else{
                sb.append(oriMap.get(keys[i]));
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }

    /**
     * json对象按键字母排序
     * @param obj
     * @return
     */
    public static String sort(Object obj){
        return sortTransform(convertToMapParams(obj));
    }

//    public static void doWithErrorStuff(Throwable e) {
//        if (e instanceof ApiException) {
//            if(((ApiException) e).getErrorCode() == Constants.LOGIN_ERROR_CODE) {
//                Intent in = new Intent(BaseApplication.getInstance(), LoginActivity.class);
//                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                BaseApplication.getInstance().startActivity(in);
//            }
//        }
//    }

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";
    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

//    public static Uri urlToUri(String url) {
//        if(TextUtils.isDataEmpty(url))
//            return resourceIdToUri(BaseApplication.getInstance(),R.mipmap.ic_avatar_def);
//        else
//            return Uri.parse(url);
//    }

    /**
     * 判断主Activity是否正在运行
     * @param pkg
     * @param cls
     * @param context
     * @return
     */
    public static boolean isClsRunning(String pkg, String cls, Context context) {
        ActivityManager am =(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        ActivityManager.RunningTaskInfo task = tasks.get(0);
        if (task != null) {
            return TextUtils.equals(task.baseActivity.getPackageName(), pkg) && TextUtils.equals(task.baseActivity.getClassName(), cls);
        }
        return false;
    }

}

