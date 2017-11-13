package com.thinkeract.tka.pay.wxpay;

import android.text.TextUtils;

import java.io.Serializable;

public class BasicNameValuePair implements Cloneable, Serializable {


    private static final long serialVersionUID = -6437800749411518984L;

    private final String name;
    private final String value;

    /**
     * Default Constructor taking a name and a value. The value may be null.
     *
     * @param name  The name.
     * @param value The value.
     */
    public BasicNameValuePair(final String name, final String value) {

        super();
        if (name == null) {

            throw new IllegalArgumentException("Name may not be null");

        }
        this.name = name;
        this.value = value;

    }

    public String getName() {

        return this.name;

    }

    public String getValue() {

        return this.value;

    }

    public String toString() {

        // don't call complex default formatting for a simple toString

        if (this.value == null) {

            return name;

        } else {
            return this.name + "=" + this.value;

        }

    }

    public boolean equals(final Object object) {

        if (this == object) return true;
        if (object instanceof BasicNameValuePair) {

            BasicNameValuePair that = (BasicNameValuePair) object;
            return this.name.equals(that.name)
                    && TextUtils.equals(this.value, that.value);

        } else {

            return false;

        }

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    public Object clone() throws CloneNotSupportedException {

        return super.clone();

    }


}