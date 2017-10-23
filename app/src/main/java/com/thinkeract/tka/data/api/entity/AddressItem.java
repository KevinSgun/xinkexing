package com.thinkeract.tka.data.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkeract.tka.User;
import com.thinkeract.tka.data.db.greendao.GDAddress;

/**
 * Created by minHeng on 2017/4/7 15:05.
 * mail:minhengyan@gmail.com
 */

public class AddressItem implements Parcelable{

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

    public AddressItem(){}

    public AddressItem(GDAddress gdAddress){
        this.id = gdAddress.getAddressId();
        this.address = gdAddress.getAddress();
        this.cityname = gdAddress.getCity();
        this.contact = gdAddress.getContact();
        this.phone = gdAddress.getPhone();
        this.status = gdAddress.getStatus();
        this.uid = gdAddress.getUserId();
    }

    protected AddressItem(Parcel in) {
        id = in.readInt();
        uid = in.readInt();
        status = in.readInt();
        contact = in.readString();
        cityname = in.readString();
        address = in.readString();
        phone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(uid);
        dest.writeInt(status);
        dest.writeString(contact);
        dest.writeString(cityname);
        dest.writeString(address);
        dest.writeString(phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddressItem> CREATOR = new Creator<AddressItem>() {
        @Override
        public AddressItem createFromParcel(Parcel in) {
            return new AddressItem(in);
        }

        @Override
        public AddressItem[] newArray(int size) {
            return new AddressItem[size];
        }
    };

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
