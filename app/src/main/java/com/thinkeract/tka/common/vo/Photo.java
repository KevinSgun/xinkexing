package com.thinkeract.tka.common.vo;

import android.os.Parcel;

/**
 */
public class Photo extends PhotoBase {
	public static final Creator<Photo> CREATOR = new Creator<Photo>() {
		public Photo createFromParcel(Parcel in) {
			return readFromParcel(in, new Photo());
		}

		public Photo[] newArray(int size) {
			return new Photo[size];
		}
	};

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || ((Object) this).getClass() != o.getClass()) {
			return false;
		}

		Photo photo = (Photo) o;
		if(isNetUrl==1||photo.isNet()) return false;
		return imgPath != null && imgPath.equals(photo.imgPath)||imgPath == null&&photo.imgPath == null;
	}

	@Override
	public int hashCode() {
		if(isNetUrl==1) return thumbPath.hashCode();
		return imgPath.hashCode();
	}

	public String getName() {
		if (imgPath == null)
			return null;
		int index = imgPath.lastIndexOf("/");
		if (index >= 0) {
			return imgPath.substring(index);
		}
		return null;
	}

	// public String
}
