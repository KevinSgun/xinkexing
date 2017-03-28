package com.thinkeract.tka.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 当前会话的数据
 * 
 * @author bsn
 * 
 */
public class SessionInfo implements Parcelable {
	public SessionInfo() {
	}

	public SessionInfo(Parcel in) {
		curType = in.readInt();
		curFriendUin = in.readString();
		troopUin = in.readString();
		curFriendNick = in.readString();
		phoneNum = in.readString();
	}

	/**
	 * 会话类型
	 */
	// TODO curType sessionType
	public int curType;
	/**
	 * 会话的uin(好友:好友uin/群: 群uin)
	 * 
	 */
	// TODO rename to currentUin
	public String curFriendUin;

	/**
	 * 号码(好友: 好友的QQ号/群: 群号)
	 */
	// TODO rename to code
	public String troopUin;

	/**
	 * 群临时会话用..貌似是来自群的uin
	 */
	public String realTroopUin;
	
	/**
	 * 会话的昵称(群/临时会话:群名称, 好友: 好友昵称)
	 */
	// rename to currentName
	public String curFriendNick;

	public String phoneNum;

	/**
	 * 通讯录 uin
	 */
	public String contactUin;

	/**
	 * 已读上报的最后一个msgid
	 */
	public long mLastReadMsgId = -1;

//	/**
//	 * 昵称颜色
//	 */
//	public int nicknameTextColor = ChatActivityConstants.NICKNAME_TEXT_COLOR_WHITE;
//	public ChatBackground chatBg;

	/**
	 * 字体大小
	 */
	public int textSizeForTextItem;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(curType);
		dest.writeString(curFriendUin);
		dest.writeString(troopUin);
		dest.writeString(curFriendNick);
		dest.writeString(phoneNum);
	}

	public final static Creator<SessionInfo> CREATOR = new Creator<SessionInfo>() {

		@Override
		public SessionInfo createFromParcel(Parcel source) {
			return new SessionInfo(source);
		}

		@Override
		public SessionInfo[] newArray(int size) {
			return new SessionInfo[size];
		}

	};
}
