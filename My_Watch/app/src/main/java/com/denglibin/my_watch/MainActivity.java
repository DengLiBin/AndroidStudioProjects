package com.denglibin.my_watch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.denglibin.my_watch.view.WatchView;

public class MainActivity extends AppCompatActivity {
    private WatchView m_WatchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_WatchView= (WatchView) findViewById(R.id.wv);
    }
    public void clickStart(View v){
        m_WatchView.start();
    }
    public void clickStop(View v){
        m_WatchView.stop();
    }
}
