package com.fishedee.highbrid.event;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.fishedee.highbrid.MainActivity;

/**
 * Created by fish on 7/23/15.
 */
public class Navigation{

    Context m_context;

    public Navigation(Context context){
        m_context = context;
    }

    @JavascriptInterface
    public void go(String url){
        ((MainActivity)m_context).pushActivity(url);

    }

    @JavascriptInterface
    public void back(){
        ((MainActivity)m_context).popActivity();
    }

}
