package com.denglibin.notification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by DengLibin on 2016/3/31 0031.
 */
public class OtherActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView=new TextView(this);
        textView.setText("otherActivity");
        setContentView(textView);
    }
}
