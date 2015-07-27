package com.fishedee.highbrid.data;

import java.util.List;
import java.util.Map;

/**
 * Created by fish on 7/27/15.
 */
public class Config {

    public static class URLInfo{
        public URLInfo(){

        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        String style;

        public Map<String, String> getOptions() {
            return options;
        }

        public void setOptions(Map<String, String> options) {
            this.options = options;
        }

        Map<String,String> options;
    };

    String title;

    String domain;

    String index;

    String loading;

    Map<String,URLInfo> urls;

    public Config(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getLoading() {
        return loading;
    }

    public void setLoading(String loading) {
        this.loading = loading;
    }

    public Map<String,URLInfo> getUrls() {
        return urls;
    }

    public void setUrls(Map<String,URLInfo> urls) {
        this.urls = urls;
    }

}
