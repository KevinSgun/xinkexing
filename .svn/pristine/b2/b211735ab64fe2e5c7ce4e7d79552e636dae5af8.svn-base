package com.thinkeract.tka.common.subscribe;

import android.content.Context;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public abstract class HandlingErrorsProgressSubscriber<T> extends com.zitech.framework.data.network.subscribe.ProgressSubscriber<T> {

    public HandlingErrorsProgressSubscriber(Context context) {
        super(context);
    }

    public HandlingErrorsProgressSubscriber(Context context, boolean shouldShowDialog) {
        super(context,shouldShowDialog);
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        super.onError(e);
//        Utils.doWithErrorStuff(e);
    }
}