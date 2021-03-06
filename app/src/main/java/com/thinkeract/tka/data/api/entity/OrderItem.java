package com.thinkeract.tka.data.api.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;


/**
 * Created by minHeng on 2016/12/14 11:32.
 * mail:minhengyan@gmail.com
 */

public class OrderItem implements Parcelable{

    private int status;
    private String name;
    private String po;
    private String date;
    private List<OrderGoods> goods;

    public static final int WAIT_PAY = 1;//待支付
    public static final int WAIT_SEND = 2;//待发货
    public static final int IS_SEND = 3;//已发货
    public static final int IS_RECEIVED = 4;//已收货
    public static final int IS_FINISH = 5;//已完成
    public static final int IS_CANCEL = 6;//已取消

    public OrderItem(){

    }

    protected OrderItem(Parcel in) {
        status = in.readInt();
        name = in.readString();
        po = in.readString();
        date = in.readString();
        goods = in.createTypedArrayList(OrderGoods.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(name);
        dest.writeString(po);
        dest.writeString(date);
        dest.writeTypedList(goods);
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<OrderGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<OrderGoods> goods) {
        this.goods = goods;
    }

    public static class OrderGoods implements Parcelable{

        /**
         * quantity : 1
         * name : 三尸脑神丹
         * price : 10
         * cover : http://120.24.95.8:11111/upload/20170412/1491999942136.png
         * spec : 包装规格:单号装;药品成分:中成药
         */

        private int quantity;
        private double price;
        private String name;
        private String cover;
        private String spec;

        protected OrderGoods(Parcel in) {
            quantity = in.readInt();
            price = in.readDouble();
            name = in.readString();
            cover = in.readString();
            spec = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(quantity);
            dest.writeDouble(price);
            dest.writeString(name);
            dest.writeString(cover);
            dest.writeString(spec);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<OrderGoods> CREATOR = new Creator<OrderGoods>() {
            @Override
            public OrderGoods createFromParcel(Parcel in) {
                return new OrderGoods(in);
            }

            @Override
            public OrderGoods[] newArray(int size) {
                return new OrderGoods[size];
            }
        };

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }
    }

}
