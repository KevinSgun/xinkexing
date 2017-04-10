package com.thinkeract.tka.data.api.request;

/**
 * Created by ymh on 2017/4/8 17:06
 * e-mail:minhengyan@gmail.com
 */

public class UpdateUserDataBody {

    /**
     * nickName : 正在
     * age : 20
     * gender : 1
     */

    private String nickName;
    private String age;
    private String gender;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
