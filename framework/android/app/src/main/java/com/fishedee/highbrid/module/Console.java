package com.fishedee.highbrid.module;


import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.util.Log;

/**
 * Created by fish on 7/23/15.
 */
public class Console{

    public Console(){

    }

    @JavascriptInterface
    public void log(String message){
        Log.d("Browser", message);
    }
}
