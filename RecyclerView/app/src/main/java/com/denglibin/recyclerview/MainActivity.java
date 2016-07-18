package com.denglibin.recyclerview;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = new RecyclerView(this);
        setContentView(recyclerView);
        /**
         * 设置RecyclerView的布局样式
         * LinearLayoutManager.HORIZONTAL：item会水平显示
         * false:表示不反转（倒序排列）
         */
        recyclerView.setLayoutManager(new GridLayoutManager(this,3,LinearLayoutManager.VERTICAL,false));//网格布局3列,竖向不反转
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));//水平，不反转
        recyclerView.setAdapter(new MyAdapter(this));
    }

}
