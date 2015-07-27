package com.fishedee.highbrid.module;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.content.Intent;

import com.fishedee.highbrid.R;

/**
 * Created by fish on 7/23/15.
 */
public class Navigation{

    Activity m_activity;

    public Navigation(Activity activity){
        m_activity = activity;
    }

    @JavascriptInterface
    public void go(String url){
        Intent intent = new Intent();
        intent.putExtra("url", url);
        intent.setClass(m_activity, m_activity.getClass());
        m_activity.startActivity(intent);
    }

    @JavascriptInterface
    public void back(){
        m_activity.finish();
    }

}
