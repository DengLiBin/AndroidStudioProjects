package com.example.denglibin.cocos2ddemo.layer;

import com.example.denglibin.cocos2ddemo.bean.Plant;
import com.example.denglibin.cocos2ddemo.bean.Zimbie;
import com.example.denglibin.cocos2ddemo.utils.CommonUtils;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;

/**
 * 对战界面图层
 * Created by DengLibin on 2016/5/13.
 */
public class FightLayer extends CCLayer {

    private CCTMXTiledMap map;
    private CGSize contentSize;
    CGSize winsize = CCDirector.sharedDirector().getWinSize();
    private ArrayList<CGPoint> zombilesPoints;//僵尸的位置
    private CCSprite chose;//已选植物的容器
    private CCSprite choose;//可选植物的容器

    public FightLayer(){
        init();
    }

    private void init() {
        loadMap();
        moveMap();//移动地图
        parseMap();//解析地图上的点
        showZombies();//把僵尸放到指定的点上
    }

    /**
     * 加载地图
     */
    private void loadMap(){
        map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
        map.setAnchorPoint(0.5f,0.5f);
        contentSize = map.getContentSize();
        map.setPosition(contentSize.width/2, contentSize.height/2);
        this.addChild(map);

    }

    /**
     * 延迟一段时间后移动地图
     */
    private void moveMap(){
       int x= (int) (winsize.width-contentSize.width);//负数

        CCMoveBy moveBy=CCMoveBy.action(3,ccp(x,0));//地图向做移动
        CCSequence sequence=CCSequence.actions(CCDelayTime.action(4),moveBy,CCDelayTime.action(4),CCCallFunc.action(this,"loadContainer"));//延迟4秒再移动，最后执行“loadContainer”方法
        map.runAction(sequence);
    }

    /**
     * 解析地图上的点
     */
    private void parseMap() {
        zombilesPoints = CommonUtils.parseMap(map, "zombies");
    }

    /**
     * 在指定的点上显示僵尸
     */
    private void showZombies(){
       for(int i=0;i<zombilesPoints.size();i++){
           CGPoint cgPoint=zombilesPoints.get(i);
           Zimbie zimbie=new Zimbie();
           zimbie.setPosition(cgPoint);
           map.addChild(zimbie);//把僵尸添加到地图上
       }
    }

    /**
     * 加载两个容器(框)
     */
    public void loadContainer(){
        chose = CCSprite.sprite("image/fight/chose/fight_chose.png");
        chose.setAnchorPoint(0,1);//设置锚点为左上角
        chose.setPosition(0,winsize.height);//放置的位置为屏幕的左上角
        this.addChild(chose);

        choose = CCSprite.sprite("image/fight/chose/fight_choose.png");
        choose.setAnchorPoint(0,0);
        this.addChild(choose);
        showPlant();
    }

    /**
     * 把植物展示到容器中
     */
    private void showPlant(){
        for(int i=1;i<=9;i++){
            Plant plant=new Plant(i);//创建植物
            //plant.setPosition(100+i*30,100);//设置位置
            plant.setAnchorPoint(0,0);
            plant.setPosition(16+((i-1)%4)*54,175-((i-1)/4)*59);
            choose.addChild(plant);
        }
    }
}
