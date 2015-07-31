package com.fishedee.highbrid.view;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.fishedee.highbrid.Util;
import com.fishedee.highbrid.view.widget.ColorParameter;
import com.fishedee.highbrid.view.widget.DimemsionParameter;
import com.fishedee.highbrid.view.widget.StringParameter;

/**
 * Created by fish on 7/31/15.
 */
public class NavigationBarItem extends RelativeLayout {
    ImageView m_imageView;
    TextView m_textView;
    RequestQueue m_requestQueue;
    public NavigationBarItem(Context context){
        super(context);
        m_imageView = new ImageView(getContext());
        m_textView = new TextView(getContext());
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
    }

    public void setMyText(StringParameter parameter){
        m_textView.setText(parameter.value);
    }

    public void setMyFontSize(DimemsionParameter dimemsionParameter){
        m_textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimemsionParameter.value);
    }

    public void setMyColor(ColorParameter color){
        m_textView.setTextColor(color.value);
    }

    public void setMyBackgroundColor(ColorParameter color){
        this.setBackgroundColor(color.value);
    }


}
