package com.thinkeract.tka.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.thinkeract.tka.R;
import com.zitech.framework.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;


public class ContentViewHolder extends ViewAnimator {

    public static final int LOADING = 0;
    public static final int RETRY = 1;
    public static final int NO_DATA = 2;
    public static final int CONTENT = 3;
    protected Button retry;
    private boolean retryInNoData = false;
    protected TextView noData;
    protected TextView errorPromptView;
    protected ProgressBar progressBar;
    private static final int CHILD_SIZE = 4;
    private String noDataHint;
    private CharSequence defaultErrorPrompt;
    private ImageView noDataIv;
    private String mCommonError;
    private List<OnClickListener> onClickListenerList;

    public ContentViewHolder(Context context) {
        super(context);
        initView();
    }

    //    private ViewCo
    public ContentViewHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }
    public void showNoData() {
        if (retryInNoData) {
            if (errorPromptView != null)
                errorPromptView.setText(noDataHint);
            setDisplayedChild(RETRY);
        } else {
            setDisplayedChild(NO_DATA);
        }
    }

    protected void initView() {
        View v = inflateLayout();
        retry = (Button) v.findViewById(R.id.retry_btn);
        noData = (TextView) v.findViewById(R.id.no_data);
        errorPromptView = (TextView) findViewById(R.id.error_prompt_view);
        progressBar = (ProgressBar) v.findViewById(R.id.loading_progress);
        noDataIv = (ImageView) v.findViewById(R.id.no_data_iv);
        defaultErrorPrompt=errorPromptView.getText();

        mCommonError = getResources().getString(R.string.common_error_msg);

        retry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListenerList != null) {
                    for (int i = onClickListenerList.size() - 1; i >= 0; i--) {
                        onClickListenerList.get(i).onClick(view);
                    }
                }
            }
        });

    }

    public void setContent(View content) {
        if (getChildCount() == CHILD_SIZE) {
            removeViewAt(CONTENT);
        }
        ViewGroup parent = (ViewGroup) content.getParent();
        ViewGroup.LayoutParams contentLayoutParams = content.getLayoutParams();
        int index = parent.indexOfChild(content);
        parent.removeView(content);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(content, CONTENT, params);
        parent.addView(this, index, contentLayoutParams);
    }

    protected View inflateLayout() {
        return inflate(getContext(), R.layout.content_view_holder, this);
    }

    public void showContent() {
        setDisplayedChild(CONTENT);
    }

    public void showLoading() {
        setDisplayedChild(LOADING);
    }

    public void showRetry() {
        setDisplayedChild(RETRY);
        if(NetworkUtil.isNetworkAvailable()){
            setErrorPromptLocal(mCommonError);
        }else {
            setErrorPromptLocal(R.string.common_no_network_msg);
        }
    }
    public void showRetry(String retryPrompt) {
        setDisplayedChild(RETRY);
        setErrorPrompt(retryPrompt);
    }

    public void showEmpty() {
        setDisplayedChild(NO_DATA);
    }
    public void showEmpty(String noDataPrompt) {
        setDisplayedChild(NO_DATA);
        setNoDataPrompt(noDataPrompt);
    }

    @Deprecated
    public void setRetryListener(OnClickListener listener) {
        addRetryListener(listener);
    }

    public void addRetryListener(OnClickListener listener) {
        if(onClickListenerList == null)
            onClickListenerList = new ArrayList<>();
        onClickListenerList.add(listener);
    }

    public void removeRetryListener(OnClickListener listener){
        if (onClickListenerList != null&&onClickListenerList.contains(listener)) {
            onClickListenerList.remove(listener);
        }
    }

    public void setDefaultEmptyImage(int resId) {
        noDataIv.setImageResource(resId);
    }

    public void setNoDataPrompt(String noDataPrompt) {
        noData.setText(noDataPrompt);
    }

    public void setNoDataTextColor(int colorResId) {
        noData.setTextColor(colorResId);
    }

    public void setErrorPrompt(int resId) {
        mCommonError = getResources().getString(resId);
    }

    public void setErrorPrompt(String errorPrompt) {
        mCommonError = errorPrompt;
    }

    private void setErrorPromptLocal(CharSequence errorPrompt) {
        errorPromptView.setText(errorPrompt);
    }

    private void setErrorPromptLocal(int errorPromptResId) {
        errorPromptView.setText(errorPromptResId);
    }

    public int getCurrentViewIndex() {
        return indexOfChild(getCurrentView());
    }
}
