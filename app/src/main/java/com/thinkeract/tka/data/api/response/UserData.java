package com.thinkeract.tka.data.api.response;


/**
 * Created by lu on 2016/10/30.
 */

public class UserData {

    /**
     * id : 3
     * mobile : 15913101558
     * isProfessional : 0
     * isUpdate : 0
     * lastLoginDate : 2016-12-02 07:15:12
     * token : rBFVxul+nqRLMmV7yvL3DM79a2aOW8MBHQ7fSn2izjY=
     * balance : 0
     * name : 159****1558
     * company : null
     * concernNumber : 0
     * fansNumber : 0
     * industries : 0
     * phase : 0
     * stores : null
     * position : null
     * portrait : null
     */

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
    private String token;
    private String name;
    private String company;
    private String stores;//所在店铺
    private String position;//所在职位
    private String portrait;//头像

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIsProfessional() {
        return isProfessional;
    }

    public void setIsProfessional(int isProfessional) {
        this.isProfessional = isProfessional;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getConcernNumber() {
        return concernNumber;
    }

    public void setConcernNumber(int concernNumber) {
        this.concernNumber = concernNumber;
    }

    public int getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(int fansNumber) {
        this.fansNumber = fansNumber;
    }

    public int getIndustries() {
        return industries;
    }

    public void setIndustries(int industries) {
        this.industries = industries;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public String getStores() {
        return stores;
    }

    public void setStores(String stores) {
        this.stores = stores;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public float getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(float diamonds) {
        this.diamonds = diamonds;
    }
}
