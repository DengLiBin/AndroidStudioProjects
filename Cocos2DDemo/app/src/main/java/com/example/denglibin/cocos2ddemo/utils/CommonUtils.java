package com.example.denglibin.cocos2ddemo.utils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCZoomFlipXTransition;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DengLibin on 2016/5/13.
 */
public class CommonUtils {
    /**
     * 切换场景
     * @param layer
     */
    public static void changeLayer(CCLayer layer){
        CCScene scene=CCScene.node();
        scene.addChild(layer);
        //跳跃动画
        //  CCJumpZoomTransition transition=CCJumpZoomTransition.transition(2,scene);//把场景包裹一层切换的动画效果
        //翻转动画
        CCZoomFlipXTransition transition=CCZoomFlipXTransition.transition(2,scene,1);//时间,场景,方向0或1
        CCDirector.sharedDirector().replaceScene(transition);//切换场景,参数：新的场景
    }

    public static ArrayList<CGPoint> parseMap(CCTMXTiledMap map,String name) {
        ArrayList<CGPoint> points = new ArrayList<CGPoint>();
        CCTMXObjectGroup objectGroup = map.objectGroupNamed(name);
        ArrayList<HashMap<String, String>> objects = objectGroup.objects;
        for (HashMap<String, String> hashMap : objects) {
            int x = Integer.parseInt(hashMap.get("x"));
            int y = Integer.parseInt(hashMap.get("y"));
            CGPoint cgPoint = map.ccp(x, y);
            points.add(cgPoint);
        }
        return points;
    }

    /**
     * 帧动画动作
     * @param format 图片的路径
     * @param num 图片的数量
     * @param isForever 是否循环
     * @return
     */
    public static CCAction getAnimate(String format, int num, boolean isForever){
        ArrayList<CCSpriteFrame> frames=new ArrayList<CCSpriteFrame>();
        for(int i=1;i<=num;i++){
            CCSpriteFrame spriteFrame=CCSprite.sprite(String.format(format,i)).displayedFrame();
            frames.add(spriteFrame);
        }
        CCAnimation anim=CCAnimation.animation("加载",0.2f,frames);
        //序列帧一般情况下需要循环播放，如果要不循环的话，需要指定第二个参数为false，默认为true

        if(isForever){
            org.cocos2d.actions.interval.CCAnimate animate=org.cocos2d.actions.interval.CCAnimate.action(anim);
            CCRepeatForever forever=CCRepeatForever.action(animate);
            return forever;
        }else{
            org.cocos2d.actions.interval.CCAnimate  animate= org.cocos2d.actions.interval.CCAnimate.action(anim,false);
            return animate;
        }

    }
}
