package com.example.denglibin.cocos2ddemo.bean;

import com.example.denglibin.cocos2ddemo.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;

/**
 * Created by DengLibin on 2016/5/13.
 */
public class Zimbie extends CCSprite {
    public Zimbie(){
        super("image/zombies/zombies_1/shake/z_1_01.png");
        setScale(0.6f);
        setAnchorPoint(0.5f,0);
        CCAction animate= CommonUtils.getAnimate("image/zombies/zombies_1/shake/z_1_%02d.png",2,true);
        this.runAction(animate);
    }
}
