package com.denglibin.redboy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;


public class SplashActivity extends AppCompatActivity {
    protected static final int LOAD_MAINUI = 1;
    private LinearLayout root;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case LOAD_MAINUI:
                    loadMainUI();
                    break;
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        root= (LinearLayout) findViewById(R.id.ll_root);//跟布局
        AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);//渐变动画
        aa.setDuration(2000);
        root.startAnimation(aa);
        Message msg=new Message();
        msg.what=LOAD_MAINUI;
        handler.sendMessageDelayed(msg,2000);//延迟发送一个消息
    }

    /**
     * 进入主界面
     */
    private void loadMainUI(){
        Intent intent=new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
