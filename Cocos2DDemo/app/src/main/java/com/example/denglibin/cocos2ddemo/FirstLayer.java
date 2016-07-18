package com.example.denglibin.cocos2ddemo;

import android.view.MotionEvent;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

/**
 * 图层
 * Created by DengLibin on 2016/5/12.
 */
public class FirstLayer extends CCLayer {

    private CCSprite bg;
    private CCSprite ccSprite;

    public FirstLayer(){
        setIsTouchEnabled(true);//打开触摸事件开关，不打开的话不会响应触摸事件
        init();
    }

    /**
     * 按下屏幕事件
     * @param event
     * @return
     */
    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        System.out.println("按下");
        //先把android坐标系中的点转换成cocos2d坐标系中的点
        CGPoint convertTouchToNodeSpace=this.convertTouchToNodeSpace(event);//点击的点
        CGRect boundingBox=ccSprite.getBoundingBox();//精灵所占的矩形区域
        boolean containsPoint=CGRect.containsPoint(boundingBox,convertTouchToNodeSpace);//判断点击的点是否在精灵的矩形区域内
        if(containsPoint){
            ccSprite.setScale(ccSprite.getScale()+0.2f);//放大精灵
        }else{
            ccSprite.setScale(ccSprite.getScale()-0.2f);//缩小
        }
        return super.ccTouchesBegan(event);
    }

    private void init(){
        //背景
        bg = CCSprite.sprite("bbg_arena.jpg");
        bg.setAnchorPoint(0,0);
        this.addChild(bg);//先添加的在下面
        //this.addChild(bg,1);//第二个参数表示优先级，值越大越靠上，一样大，先添加的在下面
        //this.addChild(bg,1,1);//第三个参数是tag标签，方便查找
       // this.getChildByTag(1);//根据标签查找
        //参数就是精灵的图片bitmap,图片放在assets文件夹下
        ccSprite = CCSprite.sprite("z_1_attack_01.png");
        //设置锚点(cocos2d锚点的位置默认是在精灵的中心(相对于自身))，cocos2d游戏中屏幕的坐标原点是左下角，
        //精灵在屏幕中默认的位置就是原点（左下角），就是精灵的锚点和坐标原点对齐
        ccSprite.setAnchorPoint(0,0);//将精灵的锚点设置为该精灵的左下角的原点（默认是中心点，范围是0-1）

        ccSprite.setPosition(100,100);//设置精灵的位置，就是让精灵的锚点移到（100,100），注意:坐标是director.setScreenSize(480,320)之后的坐标
        ccSprite.setScale(1.0f);//缩放精灵
        ccSprite.setFlipX(true);//水平翻转
       // ccSprite.setFlipY(true);//垂直翻转
        ccSprite.setOpacity(255);//设置透明度，值为0-255,255表示完全不透明
        ccSprite.setVisible(true);//可不可见
        //把精灵添加到图层上
        this.addChild(ccSprite);
    }
}
