package com.thinkeract.tka.data.api.entity;

import com.thinkeract.tka.User;
import com.thinkeract.tka.data.db.greendao.GDAddress;

/**
 * Created by minHeng on 2017/4/7 15:05.
 * mail:minhengyan@gmail.com
 */

public class AddressItem {

    /**
     * contact : 刘先生
     * cityname : 广东深圳市
     * address : 西乡镇永丰社区
     * status : 0
     * phone : 15914087331
     * id : 2
     * uid : 100189
     */

    private int id;
    private int uid;
    private int status;
    private String contact;
    private String cityname;
    private String address;
    private String phone;


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public GDAddress toGDAddress(){
        GDAddress gdAddress = new GDAddress();
        gdAddress.setAddress(address);
        gdAddress.setAddressId(id);
        gdAddress.setCity(cityname);
        gdAddress.setContact(contact);
        gdAddress.setPhone(phone);
        gdAddress.setStatus(status);
        gdAddress.setUserId(uid);
        gdAddress.setUserAddressId(String.valueOf(User.get().getId())+String.valueOf(uid));
        return gdAddress;
    }
}
