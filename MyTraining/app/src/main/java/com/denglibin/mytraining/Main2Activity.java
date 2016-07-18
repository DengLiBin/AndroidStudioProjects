package com.denglibin.mytraining;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.zip.Inflater;

public class Main2Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView m_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//顯示返回箭頭，需要在清單文件中配置parent_activity

        initView();
        loadUrl();
    }

    private void initView(){
        //toolbar.setNavigationIcon(R.mipmap.ic_launcher);//設置返回圖標，放在 setSupportActionBar之后才会生效
        m_webview= (WebView) findViewById(R.id.wv_news);
        toolbar.setLogo(R.mipmap.ic_launcher);

        //toolbar.setTitle("主標題");主標題可以在清單文件中的activity的label屬性中配置
        toolbar.setSubtitle("副標題");

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.share:
                        Toast.makeText(Main2Activity.this,"分享",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.typeface:
                        Toast.makeText(Main2Activity.this,"字體大小",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
    private void loadUrl(){
        WebSettings setting=m_webview.getSettings();
        setting.setJavaScriptEnabled(true);//表示支持js，默认是false
        setting.setBuiltInZoomControls(true);//显示放大所次奥按钮
        setting.setUseWideViewPort(true);//双击缩放

        m_webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out.println("开始加载网页");

            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("网页加载结束");

            }

            //所有跳转的连接都会在此方法中回调
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("跳转URL:"+url);
                view.loadUrl(url);//强制让自己的WebView加载网页，不调用其他浏览器
                return true;
                //return super.shouldOverrideUrlLoading(view, url);
            }
        });

        m_webview.setWebChromeClient(new WebChromeClient(){

            //进度变化
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                System.out.println("加载进度："+newProgress);
                super.onProgressChanged(view, newProgress);
            }


            //获取网页标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("标题："+title);
                super.onReceivedTitle(view, title);
            }
        });
        m_webview.loadUrl("http://www.sina.com.cn/");
        //mWebView.loadUrl("http://www.itheima.com/");//加载网页
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main2,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
