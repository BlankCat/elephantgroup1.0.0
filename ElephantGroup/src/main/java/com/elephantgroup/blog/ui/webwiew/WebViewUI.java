package com.elephantgroup.blog.ui.webwiew;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.elephantgroup.blog.R;
import com.elephantgroup.blog.ui.base.BaseFragmentActivity;
import com.elephantgroup.blog.util.Utils;

import butterknife.Bind;


/**
 * @author KNothing
 * @date 2014-6-23
 * @description 关于我们页面中所有页面加载
 */
public class WebViewUI extends BaseFragmentActivity {

    @Bind(R.id.loading)
    ProgressBar loading;
    @Bind(R.id.loadWebView)
    AdvancedWebView loadWebView;
    @Bind(R.id.detail_set)
    ImageView rightBtn;//分享按钮

    private String loadUrl = null;
    private String title = "";
    private String webImageUrl;//网页图标
    private String webTitle = "";//网页标题,只是在WebView页面标题栏显示
    private String shareTitle;//分享标题，分享出去的时候作为标题，如果为空就用webTitle
    private Dialog mProgressDialog;
    private boolean finishLoading = false;

    /**
     * 是否显示右上角分享按钮
     */
    private boolean isHiddenRightBtn = false;

    @Override
    protected void setContentView() {
        setContentView(R.layout.load_webview);
        getPassData();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void getPassData() {
        if (getIntent() != null) {
            loadUrl = getIntent().getExtras().getString("loadUrl");
            title = getIntent().getExtras().getString("title");
            isHiddenRightBtn = getIntent().getExtras().getBoolean("isHiddenRightBtn");

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && loadWebView.canGoBack()) {
            loadWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initData() {
        setTitle(title);

        if (isHiddenRightBtn) { // 有些地方需要显示这个按钮，有些地方不需要显示
            rightBtn.setVisibility(View.GONE);
        }
        rightBtn.setImageResource(R.drawable.share_slected_icon);
        if (TextUtils.isEmpty(loadUrl)) {
            showToast("载入页面错误，请重试...T_T...");
        } else {
            if (!loadUrl.startsWith("http://") && !loadUrl.startsWith("https://")) {
                loadUrl = "http://" + loadUrl;
            }
            loadWebView.loadUrl(loadUrl);
        }

        loadWebView.getSettings().setJavaScriptEnabled(true);
        /*自适应屏幕大小*/
        loadWebView.getSettings().setUseWideViewPort(true);
        /*特殊内容显示必须打开*/
        loadWebView.getSettings().setDomStorageEnabled(true);
        loadWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        //启动缓存
        loadWebView.getSettings().setAppCacheEnabled(true);
        //提高渲染的优先级
        loadWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        loadWebView.setWebChromeClient(new WebChromeClient());
        loadWebView.requestFocus(View.FOCUS_DOWN);

        if (loadUrl.contains("17lfyq.com")) {
            loadWebView.addJavascriptInterface(new MyJavaScriptInterface(), "jyueni");
        } else {
            loadWebView.addJavascriptInterface(new MyJavaScriptInterface2(), "jyueni");
        }
        String defaultAgent = loadWebView.getSettings().getUserAgentString();
        String ourAgent = defaultAgent.concat(" jlyq").concat(" version=".concat(String.valueOf(Utils.getVersionName(WebViewUI.this))).concat("&"));
        loadWebView.getSettings().setUserAgentString(ourAgent);
        loadWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String Title) {
                super.onReceivedTitle(view, Title);
                webTitle = Title;
                if (TextUtils.isEmpty(title)) {
                    setTitle(Title);
                }
            }
        });
        loadWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                finishLoading = false;
                webImageUrl = null;
                loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadUrl = url;
                finishLoading = true;
                loading.setVisibility(View.GONE);
                view.loadUrl("javascript:window.elephant.processHTML(document.getElementsByTagName('img')[0].src);");
            }
        });

        loadWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype, long contentLength) {
                if (!TextUtils.isEmpty(url)) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * 需要清空与WebView相关的一切数据，防止死循环耗尽内存
     */
    @Override
    protected void onDestroy() {
        if (loadWebView != null) {
            loadWebView.removeAllViews();
            loadWebView.clearHistory();
            loadWebView.stopLoading();
            loadWebView.destroy();
            loadWebView = null;
        }
        super.onDestroy();
    }

    class MyJavaScriptInterface2 {
        @JavascriptInterface
        public void processHTML(String imageUrl) {
            webImageUrl = imageUrl;

        }
    }

    class MyJavaScriptInterface {
        @JavascriptInterface
        public void processHTML(String imageUrl) {
            webImageUrl = imageUrl;

        }

        @JavascriptInterface
        public void setTitle(String title) {
            shareTitle = title;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (loadWebView != null) {
            loadWebView.onActivityResult(requestCode, resultCode, intent);
        }
    }
}
