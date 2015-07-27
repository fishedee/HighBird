package com.fishedee.highbrid.module;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * Created by fish on 7/27/15.
 */
public class HighBrid {
    Console m_console;
    Navigation m_navigation;
    public HighBrid(Activity activity){
        m_console = new Console();
        m_navigation = new Navigation(activity);
    }

    @JavascriptInterface
    public Object getConsole(){
        return m_console;
    }

    @JavascriptInterface
    public Object getNavigation(){
        return m_navigation;
    }
}
