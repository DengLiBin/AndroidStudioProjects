package com.denglibin.prallaxdemo;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;

import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.denglibin.prallaxdemo.ui.MyListView;
import com.denglibin.prallaxdemo.util.Cheeses;

public class MainActivity extends Activity{
    MyListView myListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //添加header
        myListView= (MyListView) findViewById(R.id.mlv_main);
        final View mHeaderView=View.inflate(MainActivity.this,R.layout.view_header,null);
        final ImageView mImage= (ImageView) mHeaderView.findViewById(R.id.iv_main);
        myListView.addHeaderView(mHeaderView);//不能直接添加ImageView

        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 当布局填充结束之后, 此方法会被调用
                myListView.setParallaxImage(mImage);
                mHeaderView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }

        });
        // 填充数据
        myListView.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,Cheeses.NAMES));
    }

}
