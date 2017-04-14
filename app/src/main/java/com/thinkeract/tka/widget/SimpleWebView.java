package com.thinkeract.tka.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.thinkeract.tka.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ymh
 * @version V1.0
 * @Description webview
 * @date 2016/08/26
 */
public class SimpleWebView extends WebView {

    private final List<String> mTitleList = new ArrayList<String>();

    private IWebComplete mIWebComplete;

    private Handler mHandler;

    private boolean firstLoad = true;
    private boolean hasRefresh = false;
    private String mUrl = "";

    /**
     * webView 是否用缓存
     */
    private static final String mInterfaceName = "xy";
    private OnBackEventListener onBackEventListener;
//    private static final String BACK_TAG = "http://liveapp/back";
    private static final String BACK_TAG = "app/back";
    private static final String CLICK_TAG = "godomx:";

    private ProgressBar progressbar;
    private OnClickEventListener onClickEventListener;

    public SimpleWebView(Context context) {
        super(context);
        init();
    }

    public SimpleWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        progressbar = new ProgressBar(context, null,

                android.R.attr.progressBarStyleHorizontal);

        progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,

                5, 0, 0));


        Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_states);

        progressbar.setProgressDrawable(drawable);

        addView(progressbar);

    }

    public SimpleWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {

        this.setBackgroundColor(Color.argb(0, 0, 0, 0)); // 设置背景颜色 透明
        this.setHorizontalScrollBarEnabled(false);//水平不显示
        this.setVerticalScrollBarEnabled(false); //垂直不显示
        this.getSettings().setDefaultTextEncodingName("utf-8");  // 设置编码
        this.getSettings().setJavaScriptEnabled(true);// 支持js
        this.getSettings().setSupportZoom(true);
//        this.getSettings().setUseWideViewPort(true); //自适应屏幕
        this.getSettings().setLoadWithOverviewMode(true);
        this.setWebChromeClient(new MyWebChromeClient());
        this.setWebViewClient(new MyWebClient());
        this.getSettings().setLoadsImagesAutomatically(true);//自动加载图片
        this.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        if (Build.VERSION.SDK_INT >= 21) {
            this.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

    }

    /**
     * 网页加载进度监听
     */
    public class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {

                progressbar.setVisibility(GONE);

            } else {

                if (progressbar.getVisibility() == GONE)

                    progressbar.setVisibility(VISIBLE);

                progressbar.setProgress(newProgress);

            }
            //加载完成
            if (newProgress == 100) {
                if (mIWebComplete != null) {
                    mIWebComplete.complete();
                }
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            mTitleMap.put(view.getUrl(), m);
            mTitleList.add(title);
            titleChange(title);
        }

    }

    private void titleChange(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
    }

    /**
     * //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
     */
    public class MyWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // return super.shouldOverrideUrlLoading(view, url);
            if (url.endsWith(BACK_TAG)) {
                if(onBackEventListener != null) onBackEventListener.onBack();
                if(onClickEventListener != null) onClickEventListener.onClick(url);
            } else if (url.startsWith(CLICK_TAG)) {
                if(onClickEventListener != null) onClickEventListener.onClick(url);
            } else if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if (url.endsWith(BACK_TAG)) {
                if(onBackEventListener != null) onBackEventListener.onBack();
            }
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return super.shouldOverrideKeyEvent(view, event);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

//        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//
//        }
    }

    //判断是否进行刷新
    public void setHasRefresh(Boolean hasRefresh) {
        this.hasRefresh = hasRefresh;
    }


    public void setIWebComplete(IWebComplete i) {
        mIWebComplete = i;
    }

    @Override
    public void goBack() {
        super.goBack();
        if (mTitleList.size() > 1) {
            mTitleList.remove(mTitleList.size() - 1);
        }
        titleChange(mTitleList.get(mTitleList.size() - 1));
    }

    public interface IWebComplete {
        void complete();
    }

    public interface OnBackEventListener{
        void onBack();
    }

    public void setOnBackEventListener(OnBackEventListener onBackEventListener){
        this.onBackEventListener = onBackEventListener;
    }

    public void setOnClickEventListener(OnClickEventListener onClickEventListener){
        this.onClickEventListener = onClickEventListener;
    }

    public interface OnClickEventListener{
        void onClick(String path);
    }

}
