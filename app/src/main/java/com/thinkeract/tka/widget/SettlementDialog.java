package com.thinkeract.tka.widget;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.thinkeract.tka.R;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.ValidDialog;

/**
 * Created by minHeng on 2017/4/6 18:35.
 * mail:minhengyan@gmail.com
 */

public class SettlementDialog extends ValidDialog implements View.OnClickListener {
    private Activity mContext;
    private Button commitOrderBtn;
    private TextView contactNameTv;
    private TextView phoneNumTv;
    private TextView addressTv;
    private TextView amountTv;
    private RelativeLayout toAddressLayout;
    private RelativeLayout toDetailLayout;
    private SettlementAddressView addressLayout;
    private SettlementDetailView detailLayout;
    private ViewAnimator contentAnimator;
    private OnSettlementClickListener onSettlementClickListener;

    public SettlementDialog(Activity context) {
        super(context, R.style.BottomPushDialog);
        mContext = context;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_settlement);

        toAddressLayout = (RelativeLayout) findViewById(R.id.toAddressLayout);
        contactNameTv = (TextView) findViewById(R.id.contactNameTv);
        phoneNumTv = (TextView) findViewById(R.id.phoneNumTv);
        addressTv = (TextView) findViewById(R.id.addressTv);

        toDetailLayout = (RelativeLayout) findViewById(R.id.toDetailLayout);
        amountTv = (TextView) findViewById(R.id.amountTv);
        commitOrderBtn = (Button) findViewById(R.id.commitOrderBtn);

        addressLayout = (SettlementAddressView) findViewById(R.id.addressLayout);
        detailLayout = (SettlementDetailView) findViewById(R.id.detailLayout);

        contentAnimator = (ViewAnimator) findViewById(R.id.contentAnimator);

        toAddressLayout.setOnClickListener(this);
        toDetailLayout.setOnClickListener(this);
        commitOrderBtn.setOnClickListener(this);
        addressLayout.setOnAddressClickListener(new SettlementAddressView.OnAddressClickListener() {
            @Override
            public void onBackAddressClick() {
                contentAnimator.setDisplayedChild(0);
            }

            @Override
            public void onAddMoreClick() {

            }
        });
        detailLayout.setOnDetailClickListener(new SettlementDetailView.OnDetailClickListener() {
            @Override
            public void onDetailClick() {
                contentAnimator.setDisplayedChild(0);
            }
        });


        Window dialogWindow = getWindow();
        WindowManager.LayoutParams dialogParams;
        if (dialogWindow != null) {
            dialogParams = dialogWindow.getAttributes();
            dialogParams.gravity = Gravity.BOTTOM;
            dialogParams.height = ViewUtils.getDimenPx(R.dimen.h808);
            dialogParams.width = ViewUtils.getDimenPx(R.dimen.w720);
            dialogWindow.setAttributes(dialogParams);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toAddressLayout:
                contentAnimator.setDisplayedChild(1);
                break;
            case R.id.toDetailLayout:
                contentAnimator.setDisplayedChild(2);
                break;
            case R.id.commitOrderBtn:
                break;
        }
    }

    public void setOnSettlementClickListener(OnSettlementClickListener onSettlementClickListener){
        this.onSettlementClickListener = onSettlementClickListener;
    }

    public interface OnSettlementClickListener{
        void onSettlementClick();
    }
}
