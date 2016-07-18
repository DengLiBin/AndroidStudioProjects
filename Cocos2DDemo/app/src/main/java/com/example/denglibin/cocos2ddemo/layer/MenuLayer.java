package com.example.denglibin.cocos2ddemo.layer;

import com.example.denglibin.cocos2ddemo.utils.CommonUtils;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

/**
 * Created by DengLibin on 2016/5/13.
 */
public class MenuLayer extends CCLayer {
    CGSize winsize = CCDirector.sharedDirector().getWinSize();
    public MenuLayer(){
        init();
    }

    private void init() {
        CCSprite sprite=CCSprite.sprite("image/menu/main_menu_bg.jpg");
        sprite.setAnchorPoint(0,0);
        this.addChild(sprite);

        CCSprite normalSprite=CCSprite.sprite("image/menu/start_adventure_default.png");
        CCSprite selectedSprite=CCSprite.sprite("image/menu/start_adventure_press.png");

        //菜单 参数1：默认显示的精灵 参数2：选中的时候显示的精灵 参数3：对象 参数4：对象的方法
        CCMenuItem items= CCMenuItemSprite.item(normalSprite,selectedSprite,this,"click");
        CCMenu menu=CCMenu.menu(items);
        menu.setScale(0.5f);
        menu.setPosition(winsize.width/2-25,winsize.height/2-110);
        menu.setRotation(4.5f);//设置旋转的角度
        this.addChild(menu);
    }

    /**
     * 点击某个条目时调用
     * @param object 点击是哪个条目
     */
    public void click(Object object){
        System.out.println("我被点击了");
        CommonUtils.changeLayer(new FightLayer());
    }
}
