package com.thinkeract.tka.data.api.response;

/**
 * Author：YMH on 2017/11/16 0016 11:41
 * E-mail：minheng_yan@163.com
 */
public class DoctorAuthData {

    /**
     * createTime : 2017-04-05 14:41:01
     * descr : 通过审核
     * hospital : 深圳港大醫院
     * id : 1
     * jobTitle : 副主任医师
     * name : 張三
     * phoneNumber : 0755-123456789
     * qualifications : http://imgs.h2o-china.com/news/2015/08/1438916589145456.jpg
     * remark : 我是一个好医生
     * section : 男性泌尿科
     * status : 3
     * userId : 100189
     */

    private int id;
    private int status;
    private int userId;
    private String createTime;
    private String descr;
    private String hospital;
    private String jobTitle;
    private String name;
    private String phoneNumber;
    private String qualifications;
    private String remark;
    private String section;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
