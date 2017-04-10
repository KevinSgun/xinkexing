package com.thinkeract.tka.data.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by minHeng on 2016/12/14 11:32.
 * mail:minhengyan@gmail.com
 */

public class OrderItem implements Parcelable{

    private int id;
    private int payType;
    private int userId;
    private int orderStatus;
    private int businessType;
    private int totalGoodsCount;
    private float actuallyAmount;
    private float amount;
    private String city;
    private String beginTime;
    private String orderName;
    private String orderId;
    private String createDate;
    private String goodsImg;
    private String goodsName;

    public static final int IS_FINISH = 0;//已完成
    public static final int WAIT_PAY = 1;//待支付
    public static final int IS_CANCEL = 2;//已取消
    public static final int IS_SEND = 3;//已发货

    public OrderItem(){

    }


    protected OrderItem(Parcel in) {
        id = in.readInt();
        payType = in.readInt();
        userId = in.readInt();
        orderStatus = in.readInt();
        businessType = in.readInt();
        totalGoodsCount = in.readInt();
        actuallyAmount = in.readFloat();
        amount = in.readFloat();
        city = in.readString();
        beginTime = in.readString();
        orderName = in.readString();
        orderId = in.readString();
        createDate = in.readString();
        goodsImg = in.readString();
        goodsName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(payType);
        dest.writeInt(userId);
        dest.writeInt(orderStatus);
        dest.writeInt(businessType);
        dest.writeInt(totalGoodsCount);
        dest.writeFloat(actuallyAmount);
        dest.writeFloat(amount);
        dest.writeString(city);
        dest.writeString(beginTime);
        dest.writeString(orderName);
        dest.writeString(orderId);
        dest.writeString(createDate);
        dest.writeString(goodsImg);
        dest.writeString(goodsName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public float getActuallyAmount() {
        return actuallyAmount;
    }

    public void setActuallyAmount(float actuallyAmount) {
        this.actuallyAmount = actuallyAmount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getTotalGoodsCount() {
        return totalGoodsCount;
    }

    public void setTotalGoodsCount(int totalGoodsCount) {
        this.totalGoodsCount = totalGoodsCount;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
