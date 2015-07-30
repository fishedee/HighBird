package com.fishedee.highbrid.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fishedee.highbrid.layout.widget.Title;
import com.fishedee.highbrid.util.Util;

import org.json.JSONObject;

/**
 * Created by fish on 7/29/15.
 */
public class TitleView extends LinearLayout{
    Title m_titleView;
    public TitleView(Context context){
        super(context);
    }
    public TitleView(Context context,AttributeSet attrs ){
        super(context, attrs);
    }
    public TitleView(Context context,AttributeSet attrs,int defStyle){
        super(context, attrs, defStyle);
    }
    public void setContent(JSONObject option2)throws Exception{
        //配置本部
        ViewGroup.LayoutParams topLayoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        this.setLayoutParams(topLayoutParams);
        this.setOrientation(LinearLayout.VERTICAL);
        Util.setViewParams(this, option2, null);

        //配置title的样式
        m_titleView = new Title(getContext());
        JSONObject titleOption = option2.getJSONObject("title");
        m_titleView.setContent(titleOption);
        Util.addLinarLayoutView(
                this,
                m_titleView,
                titleOption,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0
                );
    }
}
