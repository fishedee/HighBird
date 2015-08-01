package com.fishedee.highbrid.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.fishedee.highbrid.MainActivity;
import com.fishedee.highbrid.R;
import com.fishedee.highbrid.view.widget.MainWebView;
import com.fishedee.highbrid.view.widget.StringParameter;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by fish on 7/27/15.
 */
public class WebView extends RelativeLayout{
    FrameLayout m_contentView;
    MainWebView m_loadingWebView;
    MainWebView m_contentWebView;
    PtrClassicFrameLayout m_pullRefreshLayout;

    public WebView(Context context){
        super(context);
        m_contentView = (FrameLayout)((Activity)context).getLayoutInflater().inflate(R.layout.my_webview,null);
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(m_contentView, params);

        m_loadingWebView = (MainWebView)m_contentView.findViewById(R.id.loading_view);
        m_loadingWebView.loadData(((MainActivity)context).getLoadingHtml(), "text/html; charset=UTF-8", null);

        m_contentWebView = (MainWebView)m_contentView.findViewById(R.id.content_view);
        m_contentWebView.importJavascript();

        m_pullRefreshLayout = (PtrClassicFrameLayout)m_contentView.findViewById(R.id.content_view_pull_refresh);
        m_pullRefreshLayout.setResistance(1.7f);
        m_pullRefreshLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        m_pullRefreshLayout.setDurationToClose(200);
        m_pullRefreshLayout.setDurationToCloseHeader(1000);
        m_pullRefreshLayout.setPullToRefresh(false);
        m_pullRefreshLayout.setKeepHeaderWhenRefresh(true);
        m_pullRefreshLayout.setLastUpdateTimeRelateObject(this);
        m_pullRefreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                //return false;
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, m_contentWebView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                m_contentWebView.reload();
            }
        });

        m_contentWebView.onPageFinish(new MainWebView.Listener() {
            @Override
            public void onEvent() {
                m_contentView.removeView(m_loadingWebView);
                m_pullRefreshLayout.refreshComplete();
            }
        });
    }

    public void setMyUrl(StringParameter url){
        m_contentWebView.loadUrl(url.value);
    }
}
