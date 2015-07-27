package com.fishedee.highbrid;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = DashBoard.getInstance().createView(this);
        setContentView(view);
    }
}
