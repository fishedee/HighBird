package com.fishedee.highbrid.view;

import android.content.Context;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.fishedee.highbrid.MainActivity;
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

    StringParameter m_text;
    StringParameter m_activeText;
    DimemsionParameter m_fontSize;
    DimemsionParameter m_activeFontSize;
    ColorParameter m_color;
    ColorParameter m_activeColor;
    ColorParameter m_backgroundColor;
    ColorParameter m_activeBackgroundColor;
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
        m_imageView.setClickable(false);
        m_textView.setClickable(false);
        m_imageView.setFocusable(false);
        m_textView.setFocusable(false);
    }

    private void refreshData( StringParameter text, DimemsionParameter fontSize,ColorParameter color,ColorParameter backgroundColor ){
        if( text != null )
            m_textView.setText(text.value);
        if( fontSize != null )
            m_textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize.value);
        if( color != null )
            m_textView.setTextColor(color.value);
        if( backgroundColor != null )
            this.setBackgroundColor(backgroundColor.value);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if( event.getAction() == MotionEvent.ACTION_DOWN ){
            refreshData(
                    m_activeText,
                    m_activeFontSize,
                    m_activeColor,
                    m_activeBackgroundColor
            );
        }else if( event.getAction() == MotionEvent.ACTION_UP){
            refreshData(
                    m_text,
                    m_fontSize,
                    m_color,
                    m_backgroundColor
            );
        }
        return true;
    }

    public void setMyText(StringParameter parameter){
        m_textView.setText(parameter.value);
        m_text = parameter;
    }

    public void setMyActiveText(StringParameter parameter){
        m_activeText = parameter;
    }

    public void setMyFontSize(DimemsionParameter dimemsionParameter){
        m_textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimemsionParameter.value);
        m_fontSize = dimemsionParameter;
    }

    public void setMyActiveFontSize(DimemsionParameter dimemsionParameter){
        m_activeFontSize = dimemsionParameter;
    }

    public void setMyColor(ColorParameter color){
        m_textView.setTextColor(color.value);
        m_color = color;
    }

    public void setMyActiveColor(ColorParameter color){
        m_activeColor = color;
    }

    public void setMyBackgroundColor(ColorParameter color){
        this.setBackgroundColor(color.value);
        m_backgroundColor = color;
    }

    public void setMyActiveBackgroundColor(ColorParameter color){
        m_activeBackgroundColor = color;
    }

    public void setMyClick(final StringParameter event){
        m_textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) (NavigationBarItem.this.getContext())).getEvent().action(event.value);
            }
        });
    }


    public void setMyPaddingLeft( DimemsionParameter parameter){
        m_textView.setPadding(
                parameter.value,
                this.getPaddingTop(),
                this.getPaddingRight(),
                this.getPaddingBottom()
        );
    }

    public void setMyPaddingTop( DimemsionParameter parameter){
        m_textView.setPadding(
                this.getPaddingLeft(),
                parameter.value,
                this.getPaddingRight(),
                this.getPaddingBottom()
        );
    }

    public void setMyPaddingRight( DimemsionParameter parameter){
        m_textView.setPadding(
                this.getPaddingLeft(),
                this.getPaddingTop(),
                parameter.value,
                this.getPaddingBottom()
        );
    }

    public void setMyPaddingBottom( DimemsionParameter parameter){
        m_textView.setPadding(
                this.getPaddingLeft(),
                this.getPaddingTop(),
                this.getPaddingRight(),
                parameter.value
        );
    }


}
