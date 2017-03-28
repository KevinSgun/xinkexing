package com.zitech.framework.data.network.subscribe;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.zitech.framework.R;
import com.zitech.framework.data.network.exception.ApiException;
import com.zitech.framework.utils.NetworkUtil;
import com.zitech.framework.utils.ToastMaster;
import com.zitech.framework.widget.LoadingDialog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

//import com.zitech.framework.data.network.IContext;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressSubscriber<T> implements Observer<T> {

    private Dialog mDialog;

    private Context context;
    private Disposable mDisposable;

    public ProgressSubscriber(Context context) {
        this(context,new LoadingDialog(context));
    }

    public ProgressSubscriber(Context context,boolean shouldShowDialog) {
        this(context, shouldShowDialog?new LoadingDialog(context):null);
    }

    public ProgressSubscriber(Context context, Dialog dialog) {
        this.context = context;
        if(dialog!=null) {
            mDialog = dialog;
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    if (!mDisposable.isDisposed()) {
                        mDisposable.dispose();
                    }
                }
            });
        }
    }

    private void showProgressDialog() {
        if(mDialog!=null)mDialog.show();
    }

    private void dismissProgressDialog() {
        if(mDialog!=null)mDialog.dismiss();
    }

    /**
     * 完成，隐藏ProgressDialog
     */

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onSubscribe(Disposable d) {
        showProgressDialog();
        mDisposable = d;
    }

    @Override
    public void onNext(T value) {

    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof ApiException) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            if (NetworkUtil.isNetworkAvailable()) {
                ToastMaster.shortToast(R.string.request_failed);
            } else {
                ToastMaster.shortToast(R.string.network_unavailable);
            }
        }
        dismissProgressDialog();

    }


}