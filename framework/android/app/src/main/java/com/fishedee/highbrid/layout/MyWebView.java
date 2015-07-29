package com.fishedee.highbrid.layout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.fishedee.highbrid.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by fish on 7/27/15.
 */
public class MyWebView extends RelativeLayout{
    FrameLayout m_contentView;
    public MyWebView(Context context){
        super(context);
        initialize(context);
    }
    public MyWebView(Context context,AttributeSet attrs ){
        super(context,attrs);
        initialize(context);
    }
    public MyWebView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        initialize(context);
    }
    private void initialize(Context context){
        m_contentView = (FrameLayout)((Activity)context).getLayoutInflater().inflate(R.layout.mywebview,null);
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(m_contentView, params);
    }
    public void setContent(String contentViewUrl,String loadingViewHtml){
        //初始化loadingView
        final MainWebView loadingWebView = (MainWebView)m_contentView.findViewById(R.id.loading_view);
        loadingWebView.loadData(loadingViewHtml, "text/html; charset=UTF-8", null);
        //初始化contentView
        final MainWebView contentWebView = (MainWebView)m_contentView.findViewById(R.id.content_view);
        contentWebView.importJavascript();
        //初始化FrameLayout
        final PtrClassicFrameLayout pullRefreshLayout = (PtrClassicFrameLayout)m_contentView.findViewById(R.id.content_view_pull_refresh);
        pullRefreshLayout.setResistance(1.7f);
        pullRefreshLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        pullRefreshLayout.setDurationToClose(200);
        pullRefreshLayout.setDurationToCloseHeader(1000);
        pullRefreshLayout.setPullToRefresh(false);
        pullRefreshLayout.setKeepHeaderWhenRefresh(true);
        pullRefreshLayout.setLastUpdateTimeRelateObject(this);
        pullRefreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                //return false;
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, contentWebView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                contentWebView.reload();
            }
        });
        //启动
        contentWebView.onPageFinish(new MainWebView.Listener() {
            @Override
            public void onEvent() {
                m_contentView.removeView(loadingWebView);
                pullRefreshLayout.refreshComplete();
            }
        });
        contentWebView.loadUrl(contentViewUrl);
    }
}
