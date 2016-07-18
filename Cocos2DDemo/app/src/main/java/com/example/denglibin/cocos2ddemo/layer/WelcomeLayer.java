package com.example.denglibin.cocos2ddemo.layer;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.MotionEvent;

import com.example.denglibin.cocos2ddemo.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

/**
 * Created by DengLibin on 2016/5/13.
 */
public class WelcomeLayer extends CCLayer {

    private CGSize winsize;
    private CCSprite start;//开始按钮

    public WelcomeLayer(){
        new AsyncTask<Void,Void,Void>(){
            //子线程中执行
            @Override
            protected Void doInBackground(Void... params) {
                //加载数据的操作
                SystemClock.sleep(5000);//睡3秒
                return null;
            }
            //子线程执行完后执行
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                start.setVisible(true);//数据加载完后，设置start精灵为可见
                setIsTouchEnabled(true);//触摸事件能晚开就晚开，能早关就早关
            }
        }.execute();//不要忘记执行
        init();
    }
    private void init(){
       logo();

    }

    /**
     * 加载Logo
     */
    private void logo() {
        CCSprite logo=CCSprite.sprite("image/popcap_logo.png");
        winsize = CCDirector.sharedDirector().getWinSize();
        logo.setAnchorPoint(0.5f,0.5f);
        logo.setPosition(winsize.width/2,winsize.height/2);
        this.addChild(logo);
        CCHide hide=CCHide.action();//隐藏
        CCDelayTime delayTime=CCDelayTime.action(2);//停留2秒
        CCSequence sequence=CCSequence.actions(delayTime,hide,delayTime, CCCallFunc.action(this,"loadWelcome"));//先停留再隐藏再停留,再执行loadWelcome方法
        logo.runAction(sequence);
    }

    /**
     * ,展示欢迎界面。展示logo完成后调用
     */
    public void loadWelcome(){
        CCSprite bg=CCSprite.sprite("image/welcome.jpg");
        bg.setAnchorPoint(0,0);
        this.addChild(bg);

        loading();
    }

    /**
     * 加载动画,展示欢迎界面时调用
     */
    private void loading() {
        CCSprite loading=CCSprite.sprite("image/loading/loading_01.png");
        loading.setAnchorPoint(0.5f,0.5f);
        loading.setPosition(winsize.width/2,30);//位置
        this.addChild(loading);
        String format="image/loading/loading_%02d.png";
        //帧动画动作
        CCAction animate=CommonUtils.getAnimate(format,9,false);
        loading.runAction(animate);

        start = CCSprite.sprite("image/loading/loading_start.png");
        start.setAnchorPoint(0.5f,0.5f);
        start.setPosition(winsize.width/2,30);//位置
        this.addChild(start);
        start.setVisible(false);//不可见
    }

    /**
     * 给start精灵设置点击事件
     * @param event
     * @return
     */
    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        //把安卓中坐标点转化为cocos2d坐标点
        CGPoint point=this.convertTouchToNodeSpace(event);
        CGRect startBox=start.getBoundingBox();
        if(CGRect.containsPoint(startBox,point)){
           // System.out.println("start按钮被点击了");
            CommonUtils.changeLayer(new MenuLayer());//切换场景
        }
        return super.ccTouchesBegan(event);
    }
}
