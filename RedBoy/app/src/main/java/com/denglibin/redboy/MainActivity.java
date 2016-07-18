package com.denglibin.redboy;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout middleContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        middleContainer= (RelativeLayout) findViewById(R.id.main_middle);
        init();
    }
    private void init(){

    }

}
