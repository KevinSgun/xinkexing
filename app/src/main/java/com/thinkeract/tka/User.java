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
    private static final String INDUSTRIES = "industries";//行业信息
    private static final String PHASE = "phase";//运营阶段
    private static final String ISPROFESSIONAL = "isProfessional";//是否为专家，0不是，1是
    private static final String ISUPDATE = "isUpdate";//是否已完善资料，0未完善，1完善
    private static final String BALANCE = "balance";//余额
    private static final String MOBILE = "mobile";
    private static final String LASTLOGINDATE = "lastLoginDate";
    private static final String NAME = "name";
    private static final String COMPANY = "company";
    private static final String STORES = "stores";//所在店铺
    private static final String POSITION = "position";//所在职位
    private static final String PORTRAIT = "portrait";//头像
    private static final String TOKEN = "token";
    private static final String IS_AGREE = "is_agree";
    private static final String PUSH_ID = "push_id";
    private static final String DIAMONDS = "diamonds";
    private static final String RATE = "rate";
    private SP sp;

    private int id;
    private int concernNumber;//关注数
    private int fansNumber;//粉丝数
    private int industries;//行业信息
    private int phase;//运营阶段
    private int isProfessional;//是否为专家，0不是，1是
    private int isUpdate;//是否已完善资料，0未完善，1完善
    private float balance;//余额
    private float diamonds;//钻石
    private String mobile;
    private String lastLoginDate;
    private String name;
    private String company;
    private String stores;//所在店铺
    private String position;//所在职位
    private String portrait;//头像
    private String token;
    private boolean isAgree;
    private String pushId;//极光推送的ID
    private int rate;//1：x,其中x就是rate，1为人民币

    public static User get() {
        return ThinkerActApplication.getInstance().getUser();
    }

    public User() {
        super();
        sp = new SP("USER_DATA");
        id = sp.getInt(ID, 0);
        concernNumber = sp.getInt(CONCERNNUMBER, 0);//关注数
        fansNumber = sp.getInt(FANSNUMBER, 0);//粉丝数
        industries = sp.getInt(INDUSTRIES, 0);//行业信息
        phase = sp.getInt(PHASE, 0);//运营阶段
        isProfessional = sp.getInt(ISPROFESSIONAL, 0);//是否为专家，0不是，1是
        isUpdate = sp.getInt(ISUPDATE, 0);//是否已完善资料，0未完善，1完善
        balance = sp.getFloat(BALANCE, 0f);//余额
        mobile = sp.getString(MOBILE, null);
        lastLoginDate = sp.getString(LASTLOGINDATE, null);
        name = sp.getString(NAME, null);
        company = sp.getString(COMPANY, null);
        stores = sp.getString(STORES, null);//所在店铺
        position = sp.getString(POSITION, null);//所在职位
        portrait = sp.getString(PORTRAIT, null);//头像
        token = sp.getString(TOKEN, "");
        isAgree = sp.getBoolean(IS_AGREE, false);
        pushId = sp.getString(PUSH_ID, "");
        diamonds = sp.getFloat(DIAMONDS,0f);
        rate  = sp.getInt(RATE,100);
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

    public void storeIndustries(int industries) {
        this.industries = industries;
        sp.putInt(INDUSTRIES, industries);
    }

    public void storeIndustriesNotify(int industries) {
        this.industries = industries;
        sp.putInt(INDUSTRIES, industries);
        notifyChange();
    }

    public void storePhase(int phase) {
        this.phase = phase;
        sp.putInt(PHASE, phase);
    }

    public void storePhaseNofity(int phase) {
        this.phase = phase;
        sp.putInt(PHASE, phase);
        notifyChange();
    }

    private void storeIsProfessional(int isProfessional) {
        this.isProfessional = isProfessional;
        sp.putInt(ISPROFESSIONAL, isProfessional);
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

    private void storeLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
        sp.putString(LASTLOGINDATE, lastLoginDate);
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

    public void storeCompany(String company) {
        this.company = company;
        sp.putString(COMPANY, company);
    }

    public void storeCompanyNotify(String company) {
        this.company = company;
        sp.putString(COMPANY, company);
        notifyChange();
    }

    public void storeStores(String stores) {
        this.stores = stores;
        sp.putString(STORES, stores);
    }

    public void storeStoresNotify(String stores) {
        this.stores = stores;
        sp.putString(STORES, stores);
        notifyChange();
    }

    public void storePosition(String position) {
        this.position = position;
        sp.putString(POSITION, position);
    }

    public void storePositionNotify(String position) {
        this.position = position;
        sp.putString(POSITION, position);
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

    public void storeDiamondsNotify(float diamonds){
        this.diamonds = diamonds;
        sp.putFloat(DIAMONDS,diamonds);
        notifyChange();
    }

    public void storeDiamonds(float diamonds){
        this.diamonds = diamonds;
        sp.putFloat(DIAMONDS,diamonds);
    }

    public void storeRateNotify(int rate){
        if(rate>0) {
            this.rate = rate;
            sp.putInt(RATE, rate);
            notifyChange();
        }
    }

    public void storeRate(int rate){
        if(rate>0) {
            this.rate = rate;
            sp.putInt(RATE, rate);
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
        storeIndustries(data.getIndustries());
        storePhase(data.getPhase());
        storeIsProfessional(data.getIsProfessional());
        storeIsUpdate(data.getIsUpdate());
        storeBalance(data.getBalance());
        storeMobile(data.getMobile());
        storeLastLoginDate(data.getLastLoginDate());
        storeName(data.getName());
        storeCompany(data.getCompany());
        storeStores(data.getStores());
        storePosition(data.getPosition());
        storePortrait(data.getPortrait());
        storeToken(data.getToken());
//        storeDiamonds(data.getDiamonds());
        if (isNeedNotify)
            notifyChange();
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
        sp.remove(INDUSTRIES);
        industries = 0;
        //
        sp.remove(PHASE);
        phase = 0;
        //
        sp.remove(ISPROFESSIONAL);
        isProfessional = 0;
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
        sp.remove(LASTLOGINDATE);
        lastLoginDate = null;
        //
        sp.remove(NAME);
        name = null;
        //
        sp.remove(COMPANY);
        company = null;
        //
        sp.remove(STORES);
        stores = null;
        //
        sp.remove(POSITION);
        position = null;
        //
        sp.remove(PORTRAIT);
        portrait = null;

        //
        sp.remove(TOKEN);
        token = "";

        //
        sp.remove(DIAMONDS);
        diamonds = 0f;

        //
        sp.remove(RATE);
        rate = 100;

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

    public int getIndustries() {
        return industries;
    }

    public int getPhase() {
        return phase;
    }

    public int getIsProfessional() {
        return isProfessional;
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

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public String getName() {
        if (TextUtils.isEmpty(name))
            return StringUtils.hideMiddle(mobile);
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getStores() {
        return stores;
    }

    public String getPosition() {
        return position;
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

    public float getDiamonds(){
        return diamonds;
    }

    public int getRate(){
        return rate;
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
}
