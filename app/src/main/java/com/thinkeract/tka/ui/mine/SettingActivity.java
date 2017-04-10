package com.thinkeract.tka.ui.mine;

import android.app.Dialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.User;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.widget.CommonDialog;
import com.zitech.framework.Session;
import com.zitech.framework.utils.IoUtil;
import com.zitech.framework.utils.ToastMaster;
import com.zitech.framework.utils.Utils;
import com.zitech.framework.widget.LoadingDialog;

import java.util.Locale;

/**
 * Created by minHeng on 2016/12/17 12:04.
 * mail:minhengyan@gmail.com
 */

public class SettingActivity extends AppBarActivity {
    private TextView cacheSizeTv;
    private RelativeLayout clearCacheLayout;
    private RelativeLayout helpAndFeedBackLayout;
    private TextView versionTv;
    private LoadingDialog loadingDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        setTitle(R.string.sys_setting);
        initializeView();
        clearCacheLayout.setOnClickListener(this);
        helpAndFeedBackLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        refreshUI();
    }

    private void refreshUI() {
        cacheSizeTv.setText(String.format(Locale.CHINESE, "%.2fM", Utils.sizeOfDiskCache(getApplicationContext())));
        versionTv.setText(String.format(Locale.CHINESE, "Version %s", Session.getInstance().getVersionName()));
    }

    private void initializeView() {

        cacheSizeTv = (TextView) findViewById(R.id.cacheSizeTv);
        clearCacheLayout = (RelativeLayout) findViewById(R.id.clearCacheLayout);
        helpAndFeedBackLayout = (RelativeLayout) findViewById(R.id.helpAndFeedBackLayout);
        versionTv = (TextView) findViewById(R.id.versionTv);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.clearCacheLayout:
                if (Utils.sizeOfDiskCache(getApplicationContext()) > 0)
                    showClearCacheDialog();
                break;
            case R.id.helpAndFeedBackLayout:
//                BrowseActivity.launch(this, Constants.HELP_AND_FEEDBACK, "帮助与反馈");
                break;
        }
    }

    private void showClearCacheDialog() {
        if (loadingDialog == null)
            loadingDialog = new LoadingDialog(this);
        CommonDialog commonDialog = new CommonDialog(this, "确定要清除所有缓存吗");
        commonDialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
            @Override
            public void onClick(Dialog dialog) {
                double size = com.zitech.framework.utils.Utils.sizeOfDiskCache(getApplicationContext());
                if (size > 0) {
                    loadingDialog.show();
                    new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected Void doInBackground(Void... params) {
                            IoUtil.clearAllCache(getApplicationContext());
                            return null;
                        }

                        protected void onPostExecute(Void result) {
                            loadingDialog.dismiss();
                            refreshUI();
                            ToastMaster.shortToast("清理缓存成功");
                        }
                    }.execute((Void) null);
                } else {
                    refreshUI();
                    ToastMaster.shortToast("清理缓存成功");
                }
            }
        });
        commonDialog.show();
    }
}
