package com.example.denglibin.cocos2ddemo;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.ease.CCEaseOut;
import org.cocos2d.actions.interval.CCBezierBy;
import org.cocos2d.actions.interval.CCBlink;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCJumpBy;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.actions.interval.CCTintBy;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CCBezierConfig;
import org.cocos2d.types.ccColor3B;

/**
 * Created by DengLibin on 2016/5/12.
 */
public class ActionLayer extends CCLayer {
    private CCSprite ccSprite;
    private CCSprite ccHeart;
    public ActionLayer(){
        init();
    }
    private void init(){
        ccSprite = CCSprite.sprite("z_1_attack_01.png");
        ccHeart=CCSprite.sprite("heart.png");
        ccHeart.setAnchorPoint(0,0);
        ccHeart.setPosition(0,0);
        //设置锚点(cocos2d锚点的位置默认是在精灵的中心(相对于自身))，cocos2d游戏中屏幕的坐标原点是左下角，
        //精灵在屏幕中默认的位置就是原点（左下角），就是精灵的锚点和坐标原点对齐
        ccSprite.setAnchorPoint(0,0);//将精灵的锚点设置为该精灵的左下角的原点（默认是中心点，范围是0-1）
        ccSprite.setFlipX(true);
       // moveTo(ccSprite);
        //moveBy(ccSprite);
        //moveSequence(ccSprite);
        jumpBy(ccSprite);
        //jumpSequence(ccSprite);
        //scaleBy(ccHeart);
        //rotateBy(ccHeart);
       // bezierBy(ccHeart);
        //  fadeIn(ccHeart);
       // ease(ccSprite);
        //this.addChild(ccHeart);
       this.addChild(ccSprite);
        //tint();
    }

    /**
     * 移动到指定位置
     * @param sprite
     */
    private void moveTo(CCSprite sprite){
        //参数1：移动的时间,单位s，参数2 移动的目的地
        CCMoveTo ccMoveTo=CCMoveTo.action(5, CCNode.ccp(400,0));
        sprite.runAction(ccMoveTo);
    }

    /**
     * 移动一段距离
     * @param sprite
     */
    private void moveBy(CCSprite sprite){
        //参数1：移动的时间,单位s，参数2 坐标的改变值
        CCMoveBy ccMoveBy= CCMoveBy.action(5, CCNode.ccp(300,0));//横坐标加300，
        sprite.runAction(ccMoveBy);
    }

    /**
     * 动作集合，串行动作
     * @param sprite
     */
    private void moveSequence(CCSprite sprite){
        //参数1：移动的时间,单位s，参数2 坐标的改变值
        CCMoveBy ccMoveBy= CCMoveBy.action(5, CCNode.ccp(300,0));//横坐标加300，
        CCMoveBy reverse=ccMoveBy.reverse();//反向，回来，moveTo没有反向动作
        CCSequence sequence=CCSequence.actions(ccMoveBy,reverse);//串行动作
        sprite.runAction(sequence);
    }

    /**
     * 跳跃,旋转，并行动作
     * @param sprite
     */
    private void jumpBy(CCSprite sprite){
        //参数1:时间s，参数2：目的地坐标，参数3：高出目的地的高度，参数4：跳跃的次数
        CCJumpBy ccJumpBy=CCJumpBy.action(2,ccp(200,150),100,1);
        CCRotateBy rotateBy=CCRotateBy.action(2,360);//2秒，旋转360度
        CCSpawn ccSpawn=CCSpawn.actions(ccJumpBy,rotateBy);//并行动作，跳跃过程中同时旋转
        CCSequence sequence=CCSequence.actions(ccSpawn,ccSpawn.reverse());//串行动作
        CCRepeatForever forever=CCRepeatForever.action(sequence);
        sprite.setAnchorPoint(0.5f,0.5f);
        sprite.setPosition(50,50);
        sprite.runAction(forever);
    }

    /**
     * 循环跳跃
     * @param sprite
     */
    private void jumpSequence(CCSprite sprite){
        CCJumpBy ccJumpBy=CCJumpBy.action(2,ccp(200,150),100,2);
        CCJumpBy reverse=ccJumpBy.reverse();
        CCSequence sequence=CCSequence.actions(ccJumpBy,reverse);//跳上去，跳回来
        CCRepeatForever forever=CCRepeatForever.action(sequence);//一直循环动作
       // sprite.runAction(sequence);
        sprite.runAction(forever);
    }

    /**
     * 缩放
     * @param sprite
     */
    private void scaleBy(CCSprite sprite){
        //参数1：时间s;参数2：缩放的比例
        CCScaleBy ccScaleBy=CCScaleBy.action(1,0.5f);//基于锚点缩放
        CCScaleBy reverse=ccScaleBy.reverse();//反向
        CCSequence sequence=CCSequence.actions(ccScaleBy,reverse);
        CCRepeatForever forever=CCRepeatForever.action(sequence);
        //sprite.runAction(sequence);
        sprite.runAction(forever);
    }

    /**
     * 旋转
     */
    private void rotateBy(CCSprite sprite){
        //时间s;旋转的角度
        CCRotateBy rotateBy=CCRotateBy.action(3,180);
        sprite.runAction(rotateBy);
    }

    /**
     * 贝塞尔曲线
     * @param sprite
     */
    private void bezierBy(CCSprite sprite){
        CCBezierConfig config=new CCBezierConfig();
        //贝塞尔曲线的三个点
        config.controlPoint_1=ccp(0,0);
        config.controlPoint_2=ccp(100,100);
        config.endPosition=ccp(200,0);

        CCBezierBy bezierBy=CCBezierBy.action(2,config);
        sprite.runAction(bezierBy);
    }

    /**
     * 渐变
     */
    private void fadeIn(CCSprite sprite){//淡入
        //参数：时间
        CCFadeIn fadeIn=CCFadeIn.action(10);
        sprite.runAction(fadeIn);
    }

    /**
     * 加速度动作
     * @param sprite
     */
    private void ease(CCSprite sprite){
        CCMoveTo ccMoveTo=CCMoveTo.action(10,CCNode.ccp(400,0));
        CCEaseOut easeOut=CCEaseOut.action(ccMoveTo,5);//按照加速度移动,easeOut越来越慢，easeIn越来越快
        sprite.runAction(easeOut);
    }

    /**
     * 闪烁
     * @param sprite
     */
    private  void blink(CCSprite sprite){
        //3秒钟闪烁3次
        CCBlink blink=CCBlink.action(3,3);
        sprite.runAction(blink);
    }
    /**
     * 文字颜色渐变
     */
    private void tint(){
        //专门显示文字的精灵
        //参数1：显示的内容；参数2：字体的样式；参数3：字体大小
        CCLabel label=CCLabel.labelWithString("那些年的苦逼日子","hkbd.ttf",24);
        label.setColor(ccc3(50,0,255));
        label.setPosition(200,200);
        this.addChild(label);

        ccColor3B c=ccc3(100,255,-100);//颜色
        //参数1：时间s;参数2：变化后的颜色
        CCTintBy tintBy=CCTintBy.action(1,c);
        CCTintBy reverse=tintBy.reverse();
        CCSequence sequence=CCSequence.actions(tintBy,reverse);
        CCRepeatForever forever=CCRepeatForever.action(sequence);
        label.runAction(forever);
    }
}
