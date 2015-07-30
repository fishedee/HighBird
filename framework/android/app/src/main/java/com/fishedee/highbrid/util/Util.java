package com.fishedee.highbrid.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.math.BigInteger;

/**
 * Created by fish on 7/30/15.
 */
public class Util {
    //类型数值转换
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale + 0.5f);
    }
    public static int dip2px(Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale + 0.5f);
    }
    //JSONObject常用操作
    private static int parseInt(String inString){
        String string;
        int radix = 10;

        if( inString.indexOf('#') == 0 ){
            string = inString.substring(1);
            radix = 16;
        }else if( inString.indexOf('0') == 0 ){
            string = inString.substring(1);
            radix = 8;
        }else{
            string = inString;
            radix = 10;
        }
        string = string.replaceAll("[^0-9A-Fa-f]","");
        return new BigInteger(string,radix).intValue();
    }
    private static int getJSONInt(JSONObject option,String key)throws Exception{
        String text = option.getString(key);
        return Util.parseInt(text);
    }
    private static int getJSONColor(JSONObject option,String key)throws Exception{
        String text = option.getString(key);
        return Util.parseInt(text);
    }
    private static int getJSONPx(JSONObject option,String key,View view)throws Exception{
        String text = option.getString(key);
        if( text.lastIndexOf("px") == text.length() - 2 )
            text = text.substring(0,text.length()-2);
        return Util.dip2px(view.getContext(), Util.parseInt(text));
    }
    private static String getJSONString(JSONObject option,String key)throws Exception{
        return option.getString(key);
    }

    //UI实例化
    public static void setViewParams(View inView,JSONObject option,RequestQueue networkQueue) throws Exception{
        final View view = inView;
        if( option.has("text") && view instanceof TextView){
            String text = getJSONString(option,"text");
            ((TextView)view).setText(text);
        }
        if( option.has("color") && view instanceof TextView){
            int color = getJSONColor(option, "color");
            ((TextView)view).setTextColor(color);
        }
        if( option.has("fontSize") && view instanceof TextView){
            int fontSize = getJSONPx(option, "fontSize", inView);
            ((TextView)view).setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
        }
        if( option.has("background")){
            String backgroundString = getJSONString(option, "background");
            if( backgroundString.indexOf("http://") != 0 ){
                int background = getJSONColor(option, "background");
                view.setBackgroundColor(background);
            }else{
                if( view instanceof ImageView ){
                    Util.getNetworkImageAsync(networkQueue, backgroundString, new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            Drawable drawable = new BitmapDrawable(view.getContext().getResources(), response);
                            ((ImageView)view).setImageDrawable(drawable);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("TextButton", error.toString());
                        }
                    });
                }
            }
        }
    }
    public static void addLinarLayoutView(LinearLayout parentView,View childView, JSONObject option,int width,int height,int weight)throws Exception{
        int leftMargin = 0;
        int rightMargin = 0;
        int topMargin = 0;
        int bottomMargin = 0;
        if( option.has("marginTop")){
            topMargin = getJSONPx(option, "marginTop", parentView);
        }
        if( option.has("marginBottom")){
            bottomMargin = getJSONPx(option, "marginBottom", parentView);
        }
        if( option.has("marginLeft")){
            leftMargin = getJSONPx(option, "marginLeft", parentView);
        }
        if( option.has("marginRight")){
            rightMargin = getJSONPx(option, "marginRight", parentView);
        }
        if( option.has("width") ){
            width = getJSONPx(option, "width", parentView);
        }
        if( option.has("height") ){
            height = getJSONPx(option, "height", parentView);
        }
        if( option.has("weight")){
            weight = getJSONInt(option, "weight");
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                width,
                height,
                weight
        );
        layoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
        parentView.addView(childView,layoutParams);
    }
    //网络操作
    public static RequestQueue createNetworkQueue(Context context){
        return Volley.newRequestQueue(context);
    }
    public static void getNetworkImageAsync(RequestQueue queue,String url,Response.Listener<Bitmap> successListener,Response.ErrorListener failListener){
        ImageRequest request = new ImageRequest(url,successListener,0,0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888,failListener);
        queue.add(request);
    }
}
