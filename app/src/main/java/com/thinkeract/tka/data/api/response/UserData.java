package com.thinkeract.tka.data.api.response;


/**
 * Created by ymh on 2016/10/30 17:40
 * e-mail:minhengyan@gmail.com
 */

public class UserData {

    private int id;
    private int concernNumber;//关注数
    private int fansNumber;//粉丝数
    private int isProfessional;//是否为专家，0不是，1是
    private int isUpdate;//是否已完善资料，0未完善，1完善
    private int age;
    private float balance;//余额
    private String mobile;
    private String nickName;
    private String position;//所在职位
    private String portrait;//头像
    private String createDate;//注册时间
    private String status;//1.正常，2已被禁用
    private String token;
    private String gender;

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

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
