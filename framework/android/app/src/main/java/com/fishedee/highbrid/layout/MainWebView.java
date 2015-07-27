package com.fishedee.highbrid.layout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.Activity;
import android.webkit.WebSettings;

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

    public MainWebView(Activity activity){
        super(activity);
        m_activity = activity;
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
