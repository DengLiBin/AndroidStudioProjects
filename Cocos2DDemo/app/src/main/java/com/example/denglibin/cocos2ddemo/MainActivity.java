package com.example.denglibin.cocos2ddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.denglibin.cocos2ddemo.layer.WelcomeLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends AppCompatActivity {

    private CCDirector director;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        CCGLSurfaceView ccglSurfaceView = new CCGLSurfaceView(this);//this必须是activity
        setContentView(ccglSurfaceView);

        //导演只有一个，单例
        director = CCDirector.sharedDirector();
        director.attachInView(ccglSurfaceView);//開起線程
        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);//设置游戏方向，水平
        director.setDisplayFPS(true);//显示帧率
        director.setAnimationInterval(1.0f/30);//锁定帧率，只能往下调，30帧就够了，太多了浪费cpu和内存
        director.setScreenSize(480,320);//设置屏幕的大小，可以自动屏幕适配（相当于把屏幕宽分成480份，高320份）,不同屏幕大小，精灵的大小也就不同
        CCScene ccScene = CCScene.node();//创建场景
        //ccScene.addChild(new FirstLayer());//给场景添加图层
       // ccScene.addChild(new ActionLayer());
       // ccScene.addChild(new DemoLayer());
        ccScene.addChild(new WelcomeLayer());
        director.runWithScene(ccScene);//导演来运行场景
    }

    @Override
    protected void onResume() {
        super.onResume();
        director.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        director.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        director.end();
    }
}
/*
* 游戏核心：导演，场景，图层，精灵
* */