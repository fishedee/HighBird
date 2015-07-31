package com.fishedee.highbrid.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fishedee.highbrid.view.widget.ColorParameter;
import com.fishedee.highbrid.view.widget.DimemsionParameter;

/**
 * Created by fish on 7/31/15.
 */
public class RootView extends LinearLayout{
    public static class MyLayout{
        int height;
        public MyLayout(){
            height = LayoutParams.MATCH_PARENT;
        }

        public void setMyHeight( DimemsionParameter value ){
            height = value.value;
        }
    };

    public RootView(Context context){
        super(context);
        this.setOrientation(VERTICAL);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        this.setLayoutParams(layoutParams);
    }

    public void addMyView( View view , MyLayout layout ){
        int width = 0;
        int height = 0;
        int weight = 0;
        if( layout.height == LayoutParams.MATCH_PARENT){
            width = LayoutParams.MATCH_PARENT;
            height = LayoutParams.MATCH_PARENT;
            weight = 1;
        }else{
            width = LayoutParams.MATCH_PARENT;
            height = layout.height;
            weight = 0;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                width,
                height,
                weight
        );
        this.addView(view, params);
    }

    public void setMyBackgroundColor(ColorParameter color){
        this.setBackgroundColor(color.value);
    }
}
