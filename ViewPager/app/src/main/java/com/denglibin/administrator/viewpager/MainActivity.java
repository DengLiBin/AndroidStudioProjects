package com.denglibin.administrator.viewpager;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.denglibin.administrator.viewpager.Adapter.MainPagerAdapter;
import com.denglibin.administrator.viewpager.config.Constant;
import com.denglibin.administrator.viewpager.domain.Ad;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager m_vp_head;
    private TextView m_tv_title;
    private LinearLayout m_ll_dot;
    private List<Ad> list;
    public static  String[] titels;
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg){
            m_vp_head.setCurrentItem(m_vp_head.getCurrentItem()+1);
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initDot();
        initListener();
        handler.sendEmptyMessage(0);

    }

    private void initData() {
        titels=getResources().getStringArray(R.array.title);
        list=new ArrayList<Ad>();
        for(int i=0;i<Constant.images.length;i++){
            list.add(new Ad(Constant.images[i],titels[i]));
        }
        m_vp_head.setAdapter(new MainPagerAdapter(this,list));
        m_tv_title.setText(titels[0]);//默认标题
    }

    private void initView() {
        m_vp_head= (ViewPager) findViewById(R.id.vp_head);
        m_tv_title= (TextView) findViewById(R.id.tv_title);
        m_ll_dot= (LinearLayout) findViewById(R.id.dot_layout);
    }

    /**
     * 初始化几个小点（指示器）,xml文件画的
     */
    private void initDot(){
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(12,12);//指定宽高
        for(int i=0;i<list.size();i++){
            View view=new View(this);
            if(i!=0){
                params.leftMargin=20;
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.selector_dot);
            if(i==0){
                view.setEnabled(true);
            }else{
                view.setEnabled(false);
            }

            m_ll_dot.addView(view);
        }
    }

    private void initListener(){
        m_vp_head.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //更新标题和指示器
            @Override
            public void onPageSelected(int position) {
                m_tv_title.setText(list.get(position%(list.size())).getTitle());
                for(int i=0;i<m_ll_dot.getChildCount();i++){
                    m_ll_dot.getChildAt(i).setEnabled(i==position%(list.size()));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
