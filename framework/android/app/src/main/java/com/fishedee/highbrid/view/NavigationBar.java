package com.fishedee.highbrid.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.fishedee.highbrid.view.widget.StringParameter;

/**
 * Created by fish on 7/31/15.
 */
public class NavigationBar extends LinearLayout{
    LinearLayout m_leftLayout;
    LinearLayout m_centerLayout;
    LinearLayout m_rightLayout;

    public static class MyLayout{
        int gravity;
        public MyLayout(){
            gravity = -1;
        }

        public void setMyGravity(StringParameter parameter)throws Exception{
            String result = parameter.value;
            if( result == "left")
                gravity = -1;
            else if( result == "center")
                gravity = 0;
            else if( result == "right")
                gravity = 1;
            else
                throw new Exception("不合法的gravity:"+result);
        }
    }

    public NavigationBar(Context context){
        super(context);
        this.setOrientation(LinearLayout.HORIZONTAL);

        m_leftLayout = new LinearLayout(getContext());
        m_leftLayout.setOrientation(LinearLayout.HORIZONTAL);
        m_leftLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        LinearLayout.LayoutParams leftLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );
        this.addView(m_leftLayout, leftLayoutParams);

        m_centerLayout = new LinearLayout(getContext());
        m_centerLayout.setOrientation(LinearLayout.HORIZONTAL);
        m_centerLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams centerLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0
        );
        this.addView(m_centerLayout,centerLayoutParams);

        m_rightLayout = new LinearLayout(getContext());
        m_rightLayout.setOrientation(LinearLayout.HORIZONTAL);
        m_rightLayout.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
        LinearLayout.LayoutParams rightLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );
        this.addView(m_rightLayout,rightLayoutParams);
    }

    public void setMyBackgroundColor( int color ){
        this.setBackgroundColor(color);
    }

    public void addMyView( View view , MyLayout layout ){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                0
        );
        if( layout.gravity == -1 )
            m_leftLayout.addView(view,params);
        else if( layout.gravity == 0 )
            m_centerLayout.addView(view,params);
        else
            m_rightLayout.addView(view,params);
    }
}
