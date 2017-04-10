package com.thinkeract.tka.data.api.request;

/**
 * Created by ymh on 2017/4/8 17:11
 * e-mail:minhengyan@gmail.com
 */

public class UpdateAddressBody {

    /**
     * id : 0
     * phone : 15914087331
     * contact : 联系人
     * cityname : 广东深圳
     * address : 西乡真永丰社区
     * status : 0
     */

    private String id;
    private String phone;
    private String contact;
    private String cityname;
    private String address;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
