package com.fishedee.highbrid.layout.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.fishedee.highbrid.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by fish on 7/30/15.
 */
public class Title extends LinearLayout {
    LinearLayout m_leftLayout;
    LinearLayout m_centerLayout;
    LinearLayout m_rightLayout;
    public Title(Context context){
        super(context);
    }

    public Title(Context context,AttributeSet attrs ){
        super(context, attrs);
    }

    public Title(Context context,AttributeSet attrs,int defStyle ){
        super(context, attrs, defStyle);
    }

    private void addTextButtonToView( LinearLayout linearLayout , JSONArray option )throws Exception{
        for( int i = 0 ; i != option.length(); ++i ){
            JSONObject singleButtonOption = option.getJSONObject(i);
            TextButton singleButton = new TextButton(getContext());
            singleButton.setContent(singleButtonOption);
            Util.addLinarLayoutView(
                    linearLayout,
                    singleButton,
                    singleButtonOption,
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT,
                    0);
        }
    }

    public void setContent(JSONObject option)throws Exception{
        //配置本部
        this.setOrientation(LinearLayout.HORIZONTAL);
        Util.setViewParams(this,option,null);

        //加入左边
        m_leftLayout = new LinearLayout(getContext());
        m_leftLayout.setOrientation(LinearLayout.HORIZONTAL);
        m_leftLayout.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
        LinearLayout.LayoutParams leftLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );
        this.addView(m_leftLayout, leftLayoutParams);
        if( option.has("left"))
            this.addTextButtonToView(m_leftLayout,option.getJSONArray("left"));

        //加入中间
        m_centerLayout = new LinearLayout(getContext());
        m_centerLayout.setOrientation(LinearLayout.HORIZONTAL);
        m_centerLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams centerLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0
        );
        this.addView(m_centerLayout,centerLayoutParams);
        if( option.has("center"))
            this.addTextButtonToView(m_centerLayout,option.getJSONArray("center"));

        //加入右边
        m_rightLayout = new LinearLayout(getContext());
        m_rightLayout.setOrientation(LinearLayout.HORIZONTAL);
        m_rightLayout.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
        LinearLayout.LayoutParams rightLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );
        this.addView(m_rightLayout,rightLayoutParams);
        if( option.has("right"))
            this.addTextButtonToView(m_rightLayout,option.getJSONArray("right"));
    }
}
