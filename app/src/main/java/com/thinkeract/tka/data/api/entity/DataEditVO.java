package com.thinkeract.tka.data.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by minHeng on 2017/3/22 16:27.
 * mail:minhengyan@gmail.com
 */

public class DataEditVO implements Parcelable{
    private String title;
    private String content;
    private String hintTxt;
    private int limitLength;

    protected DataEditVO(Parcel in) {
        title = in.readString();
        content = in.readString();
        hintTxt = in.readString();
        limitLength = in.readInt();
    }

    public DataEditVO(){}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(hintTxt);
        dest.writeInt(limitLength);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataEditVO> CREATOR = new Creator<DataEditVO>() {
        @Override
        public DataEditVO createFromParcel(Parcel in) {
            return new DataEditVO(in);
        }

        @Override
        public DataEditVO[] newArray(int size) {
            return new DataEditVO[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHintTxt() {
        return hintTxt;
    }

    public void setHintTxt(String hintTxt) {
        this.hintTxt = hintTxt;
    }

    public int getLimitLength() {
        return limitLength;
    }

    public void setLimitLength(int limitLength) {
        this.limitLength = limitLength;
    }
}
