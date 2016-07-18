package com.denglibin.myapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.denglibin.myapp.fragment.MusicFragment;
import com.denglibin.myapp.fragment.WatchFragment;
import com.denglibin.myapp.fragment.WeatherFragment;
import com.denglibin.myapp.utils.UiUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout frameLayout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;//实现了DrawerListener这个监听器
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//显示左边按钮
        initView();
        initListener();
        //就是一个DrawerLayou的监听器
                drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open_drawer,R.string.close_drawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //Toast.makeText(getApplicationContext(), "抽屉关闭了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //Toast.makeText(getApplicationContext(), "抽屉打开了", Toast.LENGTH_SHORT).show();
            }
        };
        //让开关和actionBar建立关系
        drawerLayout.setDrawerListener(drawerToggle);//设置监听器
        drawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return drawerToggle.onOptionsItemSelected(item)|super.onOptionsItemSelected(item);
    }

    private void initView(){
        frameLayout= (FrameLayout) findViewById(R.id.frm_lay_main);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer);
    }

    private void initListener(){
        findViewById(R.id.tv_left_home).setOnClickListener(this);
        findViewById(R.id.tv_left_music).setOnClickListener(this);
        findViewById(R.id.tv_left_weatther).setOnClickListener(this);
        findViewById(R.id.tv_left_watch).setOnClickListener(this);
        findViewById(R.id.tv_left_news).setOnClickListener(this);
        findViewById(R.id.tv_left_movie).setOnClickListener(this);

    }
    private void initDataMusic(){

        FragmentManager fm=getSupportFragmentManager();
        //打开事务
        FragmentTransaction ft = fm.beginTransaction();
        //把内容显示至帧布局
        ft.replace(R.id.frm_lay_main, new MusicFragment());
        //提交
        ft.commit();
        actionBar.setTitle("本地音乐");
        actionBar.setBackgroundDrawable(UiUtils.getResource().getDrawable(R.drawable.actionbar_music_bg));

    }

    private void initDataWeather(){
        FragmentManager fm=getSupportFragmentManager();
        //打开事务
        FragmentTransaction ft = fm.beginTransaction();
        //把内容显示至帧布局
        ft.replace(R.id.frm_lay_main, new WeatherFragment());
        //提交
        ft.commit();
        actionBar.setTitle("天气");
        actionBar.setBackgroundDrawable(UiUtils.getResource().getDrawable(R.drawable.actionbar_weaher_bg));
    }
    private void initDataWatch(){
        FragmentManager fm=getSupportFragmentManager();
        //打开事务
        FragmentTransaction ft = fm.beginTransaction();
        //把内容显示至帧布局
        ft.replace(R.id.frm_lay_main, new WatchFragment());
        //提交
        ft.commit();
        actionBar.setTitle("时钟");
        actionBar.setBackgroundDrawable(UiUtils.getResource().getDrawable(R.drawable.actionbar_watch_bg));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left_home:
                drawerLayout.closeDrawers();
                break;
              case R.id.tv_left_music:
                  initDataMusic();
                  drawerLayout.closeDrawers();
                break;
              case R.id.tv_left_weatther:
                  initDataWeather();
                  drawerLayout.closeDrawers();
                break;
              case R.id.tv_left_watch:
                  initDataWatch();
                  drawerLayout.closeDrawers();
                break;
              case R.id.tv_left_news:
                  drawerLayout.closeDrawers();
                break;
              case R.id.tv_left_movie:
                  drawerLayout.closeDrawers();
                break;
              default:
                  break;

        }
    }
}
