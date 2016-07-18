package com.denglibin.weixin;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.TabSpec;



public class MainActivity extends FragmentActivity {
    private final  static String TAG_CHAT="chat";
    private FragmentTabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1初始化Tabhost,id是系统的id,不能自己定义
        tabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),R.id.home_activity_frame);
        //2、新建TabSpec,底部按钮的那一块区域
        TabSpec spec=tabHost.newTabSpec(TAG_CHAT);//TAG_CHAT:标记
        spec.setIndicator("消息");

        //3、添加TabSpec
        tabHost.addTab(spec,MyFragment.class,null);
    }

}
