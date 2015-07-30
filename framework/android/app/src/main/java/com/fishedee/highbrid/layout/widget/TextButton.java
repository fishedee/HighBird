package com.fishedee.highbrid.layout.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.fishedee.highbrid.util.Util;

import org.json.JSONObject;

/**
 * Created by fish on 7/29/15.
 */
public class TextButton extends RelativeLayout {
    JSONObject m_options;
    RequestQueue m_requestQueue;
    ImageView m_imageView;
    TextView m_textView;
    public TextButton(Context context){
        super(context);
    }

    public TextButton(Context context,AttributeSet attrs ){
        super(context, attrs);
    }

    public TextButton(Context context,AttributeSet attrs,int defStyle ){
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if( event.getAction() == MotionEvent.ACTION_DOWN ){
            setActiveState();
        }else if( event.getAction() == MotionEvent.ACTION_UP ){
            setNormalState();
        }
        return true;
    }

    public void setContent(JSONObject options){
        m_imageView = new ImageView(getContext());
        m_textView = new TextView(getContext());
        m_options = options;
        m_requestQueue = Util.createNetworkQueue(getContext());
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        this.addView(m_imageView,imageParams);
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        this.addView(m_textView,textParams);
        this.setDefaultState();
        this.setNormalState();
    }

    private void setDefaultState(){
        m_textView.setBackgroundColor(Color.WHITE);
        m_textView.setTextColor(Color.BLACK);
        m_textView.setTextSize(16);
        m_textView.setBackgroundColor(Color.TRANSPARENT);
    }

    private void setNormalState(){
        try {
            this.setState(m_options);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setActiveState(){
        try {
            if (m_options.has(".active") == false)
                return;
            JSONObject activeOption = m_options.getJSONObject(".active");
            this.setState(activeOption);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setState(JSONObject option)throws Exception{
        //样式
        Util.setViewParams(m_imageView,option,m_requestQueue);
        Util.setViewParams(m_textView,option,m_requestQueue);
        Util.setViewParams(this,option,m_requestQueue);
    }
}
