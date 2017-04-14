package com.thinkeract.tka.data.api.entity;

/**
 * Created by minHeng on 2017/4/12 16:38.
 * mail:minhengyan@gmail.com
 */

public class SecondReportItem {

    /**
     * noCheckedIcon : http://120.24.95.8:11111/upload/20170409//head/1491740027618.png
     * isCheck : 1
     * checkedIcon : http://120.24.95.8:11111/upload/20170409//head/1491740016882.png
     * name : 总体器官检查
     * id : 48
     */

    private int id;
    private int isCheck;//0代表还未检测过，1代表已检测过
    private String noCheckedIcon;
    private String checkedIcon;
    private String name;


    public String getNoCheckedIcon() {
        return noCheckedIcon;
    }

    public void setNoCheckedIcon(String noCheckedIcon) {
        this.noCheckedIcon = noCheckedIcon;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public boolean isChecked(){
        return isCheck == 1;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public String getCheckedIcon() {
        return checkedIcon;
    }

    public void setCheckedIcon(String checkedIcon) {
        this.checkedIcon = checkedIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
