package com.thinkeract.tka.widget.timepicker.view;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.zitech.framework.widget.ActionSheetDialog;
import com.zitech.framework.widget.ActionSheetHelper;

/**
 * Created by Sai on 15/11/22.
 * 精仿iOSPickerViewController控件
 */
public class BasePickerView {
    protected Context mContext;
    protected View mContentView;
    private ActionSheetDialog sheetDialog;
    public BasePickerView(Context context){
        this.mContext = context;
    }

    public void show() {
        if (sheetDialog == null) {
            sheetDialog = ActionSheetHelper.createActionDialog(mContext, mContentView);
        }
        sheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                onPickerDismiss();
            }
        });
        sheetDialog.show();
    }

    public void dismiss() {
        if(sheetDialog!=null&&sheetDialog.isShowing())
            sheetDialog.dismiss();
    }

    protected void onPickerDismiss(){

    }

}