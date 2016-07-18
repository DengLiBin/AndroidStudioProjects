package com.denglibin.weichatarticle;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by DengLibin on 2016/5/4 0004.
 */
public class ArticleActivity extends AppCompatActivity {
    private WebView webView;
    Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        toolbar= (Toolbar) findViewById(R.id.ari_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("文章");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.share:
                        Toast.makeText(ArticleActivity.this,"分享",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.typeface:
                        Toast.makeText(ArticleActivity.this,"字體大小",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        initView();
        initData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_article,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initView(){
        webView= (WebView) findViewById(R.id.wv_article);
    }

    private void initData(){
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        loadUrl(url);
    }
    
    private void loadUrl(String url){
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);//设置能够执行JavaScript脚本
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setPluginState(WebSettings.PluginState.ON);
        webSetting.setUseWideViewPort(true);
        //webSetting.setBuiltInZoomControls(true);//支持缩放

        webSetting.setDomStorageEnabled(true);
        webSetting.setDatabasePath(Environment.getExternalStorageDirectory().toString()+"/binViewDatabase");
        webSetting.setAppCacheEnabled(true);//是否使用缓存
        webSetting.setAppCachePath(Environment.getExternalStorageDirectory().toString()+"/binVieCache");
        //启用支持视窗meta标记（可实现双击缩放）
        webSetting.setUseWideViewPort(true);
        //以缩略图模式加载页面
        webSetting.setLoadWithOverviewMode(true);

        webSetting.setLoadWithOverviewMode(true);
        webSetting.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webSetting.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webSetting.setAppCacheEnabled(true);//是否使用缓存
        webSetting.setDomStorageEnabled(true);//DOM Storage
        webView.loadUrl(url);//加载
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }
        });//设置web视图

        webView.setWebChromeClient(new WebChromeClient(){
            // 一个回调接口使用的主机应用程序通知当前页面的自定义视图已被撤职
            CustomViewCallback customViewCallback;
            private FrameLayout video;
            // 进入全屏的时候
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                // 赋值给callback
                customViewCallback = callback;
                // 设置webView隐藏
                // webView.setVisibility(View.INVISIBLE);
                video = (FrameLayout) findViewById(R.id.frame_lay);
                // 将video放到当前视图中

                video.addView(view);
                // 横屏显示
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                // 设置全屏
                setFullScreen();
            }
            // 退出全屏的时候
            @Override
            public void onHideCustomView() {
                if (customViewCallback != null) {
                    // 隐藏掉

                    customViewCallback.onCustomViewHidden();
                }
                // 用户当前的首选方向
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
                // 退出全屏
                video.removeAllViews();
                quitFullScreen();
                // 设置WebView可见
                //webView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

        });
    }

    @Override
    protected void onStop() {
        webView.goBack();
        webView.clearFocus();
        webView.clearCache(true);
        finish();
        super.onStop();
    }
    /**
     * 设置全屏
     */
    private void setFullScreen() {
        // 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 全屏下的状态码：1098974464
        // 窗口下的状态吗：1098973440
    }

    /**
     * 退出全屏
     */
    private void quitFullScreen() {
        // 声明当前屏幕状态的参数并获取
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
