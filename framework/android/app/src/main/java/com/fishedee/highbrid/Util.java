package com.fishedee.highbrid;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by fish on 7/30/15.
 */
public class Util {
    //网络操作
    public static RequestQueue createNetworkQueue(Context context){
        return Volley.newRequestQueue(context);
    }
    public static void getNetworkImageAsync(RequestQueue queue,String url,Response.Listener<Bitmap> successListener,Response.ErrorListener failListener){
        ImageRequest request = new ImageRequest(url,successListener,0,0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888,failListener);
        queue.add(request);
    }
    public static Bitmap getNetworkImage(RequestQueue queue,String url)throws Exception{
        RequestFuture<Bitmap> future = RequestFuture.newFuture();
        ImageRequest request = new ImageRequest(url,future,0,0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888,future);
        queue.add(request);
        return future.get();
    }
    public static void getNetworkTextAsync(RequestQueue queue,String url,Response.Listener<String> successListener,Response.ErrorListener failListener){
        StringRequest request = new StringRequest(url,successListener,failListener);
        queue.add(request);
    }
    public static String getNetworkText(RequestQueue queue,String url)throws Exception{
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(url,future,future);
        queue.add(request);
        String result = future.get();
        return new String(result.getBytes("ISO-8859-1"),"UTF-8");
    }
}
