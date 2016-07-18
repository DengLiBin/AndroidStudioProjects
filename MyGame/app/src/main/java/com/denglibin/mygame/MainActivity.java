package com.denglibin.mygame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.denglibin.mygame.view.GameView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏部分（电池等图标和一切修饰符）
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //以上操作要在setContentView之前
         setContentView(new GameView(this));
        //setContentView(new GameSurfaceView(this));
        //setContentView(new ThreadSurfaceView(this));
    }
}
