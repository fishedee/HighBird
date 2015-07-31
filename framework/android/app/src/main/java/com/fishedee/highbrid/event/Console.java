package com.fishedee.highbrid.event;


import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by fish on 7/23/15.
 */
public class Console{

    public Console(Context context){

    }

    @JavascriptInterface
    public void log(String message){
        Log.d("Browser", message);
    }
}
