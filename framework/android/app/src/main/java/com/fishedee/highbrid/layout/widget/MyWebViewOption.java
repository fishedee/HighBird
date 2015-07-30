package com.fishedee.highbrid.layout.widget;

/**
 * Created by fish on 7/29/15.
 */
public class MyWebViewOption {
    public MyWebViewOption(){

    }

    public String getContentViewUrl() {
        return contentViewUrl;
    }

    public void setContentViewUrl(String contentViewUrl) {
        this.contentViewUrl = contentViewUrl;
    }

    String contentViewUrl;

    public String getLoadingViewHtml() {
        return loadingViewHtml;
    }

    public void setLoadingViewHtml(String loadingViewHtml) {
        this.loadingViewHtml = loadingViewHtml;
    }

    String loadingViewHtml;
}
