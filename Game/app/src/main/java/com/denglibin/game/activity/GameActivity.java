package com.denglibin.game.activity;

import android.app.Activity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.denglibin.game.view.GameView;

public class GameActivity extends Activity {
    GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /*
        因为GameView继承SurfaceView,实现了SurfaceHolder.Callback，所以在主线程中调用了SurfaceView也
        调用了SurfaceHolder.Callback
       */
        gameView=new GameView(getApplicationContext());
        this.setContentView(gameView);//加载布局

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameView.handleTouch(event);
        return super.onTouchEvent(event);
    }
}
