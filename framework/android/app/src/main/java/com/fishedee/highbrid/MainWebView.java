package com.fishedee.highbrid;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.Activity;
import android.webkit.WebSettings;

import com.fishedee.highbrid.module.HighBrid;

/**
 * Created by fish on 7/23/15.
 */
public class MainWebView extends WebView{
    WebViewClient webviewclient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) {
            handler.proceed();
        }

    };

    public MainWebView(Activity activity){
        super(activity);
        //配置参数
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //配置webviewclient
        this.setWebViewClient(webviewclient);
        //配置js库
        this.addJavascriptInterface(new HighBrid(activity), "HighBrid");
    }
}
