package com.thinkeract.tka;

import android.text.TextUtils;

import com.thinkeract.tka.common.utils.StringUtils;
import com.thinkeract.tka.data.api.response.UserData;
import com.zitech.framework.SP;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by minHeng on 2016/7/1 0001 18:48.
 * mail:minhengyan@gmail.com
 */
public class User {

    private static final String ID = "id";
    private static final String CONCERNNUMBER = "concernNumber";//关注数
    private static final String FANSNUMBER = "fansNumber";//粉丝数
    private static final String ISUPDATE = "isUpdate";//是否已完善资料，0未完善，1完善
    private static final String BALANCE = "balance";//余额
    private static final String MOBILE = "mobile";
    private static final String NAME = "name";
    private static final String PORTRAIT = "portrait";//头像
    private static final String GENDER = "gender";//性别
    private static final String AGE = "age";//年龄
    private static final String TOKEN = "token";
    private static final String IS_AGREE = "is_agree";
    private static final String PUSH_ID = "push_id";
    private SP sp;

    private int id;
    private int concernNumber;//关注数
    private int fansNumber;//粉丝数
    private int isUpdate;//是否已完善资料，0未完善，1完善
    private float balance;//余额
    private String mobile;
    private String name;
    private String portrait;//头像
    private String token;
    private String gender;
    private int age;
    private boolean isAgree;
    private String pushId;//极光推送的ID

    public static User get() {
        return ThinkerActApplication.getInstance().getUser();
    }

    public User() {
        super();
        sp = new SP("USER_DATA");
        id = sp.getInt(ID, 0);
        concernNumber = sp.getInt(CONCERNNUMBER, 0);//关注数
        fansNumber = sp.getInt(FANSNUMBER, 0);//粉丝数
        isUpdate = sp.getInt(ISUPDATE, 0);//是否已完善资料，0未完善，1完善
        balance = sp.getFloat(BALANCE, 0f);//余额
        mobile = sp.getString(MOBILE, null);
        name = sp.getString(NAME, null);
        portrait = sp.getString(PORTRAIT, null);//头像
        token = sp.getString(TOKEN, "");
        isAgree = sp.getBoolean(IS_AGREE, false);
        pushId = sp.getString(PUSH_ID, "");
        gender = sp.getString(GENDER,"");
        age = sp.getInt(AGE,0);
    }

    private void storeId(int id) {
        this.id = id;
        sp.putInt(ID, id);
    }

    private void storeConcernNumber(int concernNumber) {
        this.concernNumber = concernNumber;
        sp.putInt(CONCERNNUMBER, concernNumber);
    }

    public void storeConcernNumberNotify(int concernNumber) {
        if (concernNumber >= 0)
            this.concernNumber = concernNumber;
        sp.putInt(CONCERNNUMBER, concernNumber);
        notifyChange();
    }

    private void storeFansNumber(int fansNumber) {
        this.fansNumber = fansNumber;
        sp.putInt(FANSNUMBER, fansNumber);
    }

    private void storeIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
        sp.putInt(ISUPDATE, isUpdate);
    }

    public void storeBalance(float balance) {
        if(balance >=0){
            this.balance = balance;
            sp.putFloat(BALANCE, balance);
        }
    }

    public void storeBalanceNotify(float balance) {
        this.balance = balance;
        sp.putFloat(BALANCE, balance);
        notifyChange();
    }

    private void storeMobile(String mobile) {
        this.mobile = mobile;
        sp.putString(MOBILE, mobile);
    }

    public void storeName(String name) {
        this.name = name;
        sp.putString(NAME, name);
    }

    public void storeNameNotify(String name) {
        this.name = name;
        sp.putString(NAME, name);
        notifyChange();
    }

    public void storePortrait(String portrait) {
        this.portrait = portrait;
        sp.putString(PORTRAIT, portrait);
    }

    public void storePortraitNotify(String portrait) {
        this.portrait = portrait;
        sp.putString(PORTRAIT, portrait);
        notifyChange();
    }

    private void storeToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            this.token = token;
            sp.putString(TOKEN, token);
        }
    }

    public void storeTokenNotify(String token) {
        if (!TextUtils.isEmpty(token)) {
            this.token = token;
            sp.putString(TOKEN, token);
            notifyChange();
        }
    }

    public void loginOut() {
        clear();
        notifyChange();
    }

    public void updateFrom(UserData data) {
        updateFrom(data, true);
    }

    public void updateFrom(UserData data, boolean isNeedNotify) {
        storeId(data.getId());
        storeConcernNumber(data.getConcernNumber());
        storeFansNumber(data.getFansNumber());
        storeIsUpdate(data.getIsUpdate());
        storeBalance(data.getBalance());
        storeMobile(data.getMobile());
        storeName(data.getNickName());
        storePortrait(data.getPortrait());
        storeToken(data.getToken());
        storeGender(data.getGender());
        storeAge(data.getAge());
        if (isNeedNotify)
            notifyChange();
    }

    private void storeAge(int age) {
        this.age =age;
        sp.putInt(AGE,age);
    }

    private void storeGender(String gender) {
        this.gender = gender;
        sp.putString(GENDER,gender);
    }

    public void clear() {
        //
        sp.remove(ID);
        id = 0;
        //
        sp.remove(CONCERNNUMBER);
        concernNumber = 0;
        //
        sp.remove(FANSNUMBER);
        fansNumber = 0;
        //
        sp.remove(ISUPDATE);
        isUpdate = 0;
        //
        sp.remove(BALANCE);
        balance = 0;

        //
        sp.remove(MOBILE);
        mobile = null;
        //
        sp.remove(NAME);
        name = null;
        //
        sp.remove(PORTRAIT);
        portrait = null;

        //
        sp.remove(TOKEN);
        token = "";

        //
        sp.remove(GENDER);
        gender = "";

        //
        sp.remove(AGE);
        age =0;

        notifyChange();
    }

    public void notifyChange(String... keys) {
        EventBus.getDefault().post(new Events.UserDataEvent(keys));
    }

    public boolean hasToken() {
        return !TextUtils.isEmpty(token);
    }

    public int getId() {
        return id;
    }

    public int getConcernNumber() {
        return concernNumber;
    }

    public int getFansNumber() {
        return fansNumber;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public float getBalance() {
        return balance;
    }

    public String getMobile() {
        return mobile;
    }

    public String getName() {
        if (TextUtils.isEmpty(name))
            return StringUtils.hideMiddle(mobile);
        return name;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getToken() {
        return token;
    }

    public String getPushId() {
        return pushId;
    }

    public boolean isCompleted() {
        return isUpdate == 1;
    }

    public void setIsComplete() {
        this.isUpdate = 1;
        sp.putInt(ISUPDATE, 1);
    }

    public boolean isVisitor() {
        return id == 0;
    }

    public void storeAgreedeal(boolean isAgree) {
        this.isAgree = isAgree;
        sp.putBoolean(IS_AGREE, isAgree);
    }

    public boolean isAgree() {
        return this.isAgree;
    }

    public String getGender(){
        return gender;
    }

    public String getGenderValue(){
        if("1".equals(gender)){
            return "男";
        }else if("0".equals(gender)){
            return "女";
        }else{
            return "";
        }
    }

    public int getAge(){
        return age;
    }
}
