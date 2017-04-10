package com.thinkeract.tka.data.api.request;

/**
 * Created by ymh on 2017/4/8 17:16
 * e-mail:minhengyan@gmail.com
 */

public class DoctorDataReviewBody {

    /**
     * qualifications : http://imgs.h2o-china.com/news/2015/08/1438916589145456.jpg
     * phoneNumber : 0755-123456789
     * remark : 我是一个好医生
     * name : 張三
     * section : 男性泌尿科
     * hospital : 深圳港大醫院
     * jobTitle : 副主任医师
     */

    private String qualifications;
    private String phoneNumber;
    private String remark;
    private String name;
    private String section;
    private String hospital;
    private String jobTitle;

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
