package com.fishedee.highbrid.layout;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by fish on 7/27/15.
 */
public class MyWebView extends FrameLayout{
    public MyWebView(Activity activity,String url,String loadingPage){
        super(activity);
        //配置双层View架构
        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);//定义框架布局器参数
        final MainWebView loadingWebView = new MainWebView(activity);
        final MainWebView realWebView = new MainWebView(activity);

        realWebView.importJavascript();
        this.addView(realWebView);
        this.addView(loadingWebView);

        realWebView.onPageFinish(new MainWebView.Listener() {
            @Override
            public void onEvent() {
                MyWebView.this.removeView(loadingWebView);
            }
        });

        //启动View
        loadingWebView.loadData(loadingPage, "text/html; charset=UTF-8",null);
        realWebView.loadUrl(url);
    }
}
