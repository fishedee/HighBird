package com.fishedee.highbrid.layout.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fishedee.highbrid.module.HighBrid;

/**
 * Created by fish on 7/23/15.
 */
public class MainWebView extends WebView{
    public interface Listener{
        void onEvent();
    };

    WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public boolean	onJsAlert(WebView view, String url, String message, JsResult result){
            return false;
        }
    };

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
        @Override
        public void onPageFinished (WebView view, String url){
            if( m_pageFinishListener != null )
                m_pageFinishListener.onEvent();
        }
    };
    Activity m_activity;

    public MainWebView(Context context){
        super(context);
        initialize(context);
    }

    public MainWebView(Context context,AttributeSet attrs ){
        super(context,attrs);
        initialize(context);
    }

    public MainWebView(Context context,AttributeSet attrs,int defStyle ){
        super(context,attrs,defStyle);
        initialize(context);
    }

    private void initialize(Context context){
        m_activity = (Activity)context;
        //配置参数
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.getSettings().setDefaultTextEncodingName("UTF-8");
        //配置webviewclient
        this.setWebViewClient(webviewclient);
        this.setWebChromeClient(webChromeClient);
    }

    public void importJavascript(){
        //配置js库
        this.addJavascriptInterface(new HighBrid(m_activity), "HighBrid");
    }

    Listener m_pageFinishListener;

    public void onPageFinish(Listener listener){
        m_pageFinishListener = listener;
    }
}
