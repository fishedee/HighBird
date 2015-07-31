package com.fishedee.highbrid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.os.Handler;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView image = (ImageView)findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim);
        animation.setFillAfter(true);
        image.startAnimation(animation);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.start();
            }
        }).start();
    }

    Handler m_handler =  new Handler(){
        @Override
        public void handleMessage(Message msg){
            if( msg.what != 0x123 )
                return;
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this,MainActivity.class);
            SplashActivity.this.startActivity(intent);
        }
    };

    public void start(){
        try{
            //初始化DashBoard
            MainActivity.initialize(
                    getApplicationContext(),
                    "http://192.168.2.1:8082/hybirdapp/config.xml"
                    );
            //启动页面
            m_handler.sendEmptyMessage(0x123);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
