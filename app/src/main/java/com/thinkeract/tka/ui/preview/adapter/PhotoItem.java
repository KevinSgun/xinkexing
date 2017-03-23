package com.thinkeract.tka.ui.preview.adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by minHeng on 2016/12/14 14:33.
 * mail:minhengyan@gmail.com
 */

public class PhotoItem implements Parcelable {

    protected String id;
    protected String photoUrlB;
    protected String photoUrlS;
    protected String userId;

    public PhotoItem() {
    }

    public PhotoItem(String id) {
        this.id = id;
    }

    public PhotoItem(String id, String photoUrlB, String photoUrlS, String userId) {
        this.id = id;
        this.photoUrlB = photoUrlB;
        this.photoUrlS = photoUrlS;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoUrlB() {
        return photoUrlB;
    }

    public void setPhotoUrlB(String photoUrlB) {
        this.photoUrlB = photoUrlB;
    }

    public String getPhotoUrlS() {
        return photoUrlS;
    }

    public void setPhotoUrlS(String photoUrlS) {
        this.photoUrlS = photoUrlS;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.photoUrlB);
        dest.writeString(this.photoUrlS);
        dest.writeString(this.userId);
    }

    protected PhotoItem(Parcel in) {
        this.id = in.readString();
        this.photoUrlB = in.readString();
        this.photoUrlS = in.readString();
        this.userId = in.readString();
    }

    public static final Creator<PhotoItem> CREATOR = new Creator<PhotoItem>() {
        @Override
        public PhotoItem createFromParcel(Parcel source) {
            return new PhotoItem(source);
        }

        @Override
        public PhotoItem[] newArray(int size) {
            return new PhotoItem[size];
        }
    };
}