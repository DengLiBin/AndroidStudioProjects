package com.example.denglibin.cocos2ddemo;

import android.view.MotionEvent;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCJumpBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.util.CGPointUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DengLibin on 2016/5/13.
 */
public class DemoLayer extends CCLayer {

    private CCTMXTiledMap map;
    private ArrayList<CGPoint> roadPoints;
    private int position=0;//起点
    private CCSprite sprite1;

    public DemoLayer(){
        setIsTouchEnabled(true);//打开触摸事件开关
        init();
    }
    private void init(){
        //加载地图
        loadMap();
        parseMap();//解析地图文件

        loadZombies();//加载僵尸
        moveToNext();//移动到下一个点
    }

    /**
     * 加载地图
     */
    private void loadMap(){
        map = CCTMXTiledMap.tiledMap("map.tmx");
        map.setAnchorPoint(0.5f,0.5f);
        //因为修改了锚点，所以要修改它的的位置，让锚点（地图的中心点）对应屏幕的中心点
        map.setPosition(map.getContentSize().width/2,map.getContentSize().height/2);
        this.addChild(map);//把地图添加当前图层
    }

    /**
     * 解析地图上的坐标点，并添加到集合中
     */
    private void parseMap(){
        /*  map.tmx文件内容：
        <?xml version="1.0" encoding="UTF-8"?>
        <map version="1.0" orientation="orthogonal" width="14" height="6" tilewidth="46" tileheight="53">
        <tileset firstgid="1" name="bk1" tilewidth="46" tileheight="53">
        <image source="bk1.jpg" width="678" height="320"/>
        </tileset>
        <layer name="block" width="14" height="6">
        <data encoding="base64" compression="zlib">
                eJwTZmBgECYDayBhYuSw8bHpQ5dDV6eBxXxi9OGzB5uZpPoZhgGLwwiJ
                </data>
        </layer>
        <objectgroup name="road" width="14" height="6">
        <object x="25" y="73"/>
        <object x="74" y="75"/>
        <object x="107" y="75"/>
        <object x="113" y="182"/>
        <object x="256" y="188"/>
        <object x="246" y="282"/>
        <object x="334" y="285"/>
        <object x="349" y="81"/>
        <object x="439" y="84"/>
        <object x="434" y="243"/>
        <object x="579" y="239"/>
        <object x="579" y="76"/>
        <object x="626" y="73"/>
        </objectgroup>
        </map>*/
        //解析地图文件文件
        roadPoints = new ArrayList<CGPoint>();//地图上的点的集合
        CCTMXObjectGroup group=map.objectGroupNamed("road");
        ArrayList<HashMap<String,String>> objects=group.objects;
        for(HashMap<String,String> hashMap:objects){
            int x=Integer.parseInt(hashMap.get("x"));//x坐标
            int y=Integer.parseInt(hashMap.get("y"));//y坐标

            CGPoint cgPoint=ccp(x,y);//点对象
            roadPoints.add(cgPoint);
        }
    }

    /**
     * 加载僵尸精灵
     */
    private void loadZombies(){
        sprite1 = CCSprite.sprite("z_1_01.png");
        sprite1.setPosition(roadPoints.get(position));//起点
        sprite1.setAnchorPoint(0.5f,0.3f);//设置锚点
        sprite1.setScale(0.50f);
        sprite1.setFlipX(true);//水平翻转
        map.addChild(sprite1);//把僵尸添加到地图上，而不是当前图层上
        //序列帧的集合
        ArrayList<CCSpriteFrame> frames=new ArrayList<CCSpriteFrame>();
        String format="z_1_%02d.png";//%02d:表示一个两位整数的占位，不足两位的前面一位用0补齐
        for(int i=1;i<8;i++){
            CCSpriteFrame frame=CCSprite.sprite(String.format(format,i)).displayedFrame();
            frames.add(frame);
        }
        //配置序列帧的信息，参数1：动作的名字（给程序员看的）参数2：每一帧播放的时间；参数3：所有用到的帧
        CCAnimation anim=CCAnimation.animation("走路",0.2f,frames);
        CCAnimate animate=CCAnimate.action(anim);
        //序列帧的动作默认是永不停止的循环的
        CCRepeatForever forever=CCRepeatForever.action(animate);
        sprite1.runAction(forever);
    }

    /**
     * 移动
     */
    public void moveToNext(){
        int speed=30;
        position++;
        if(position<roadPoints.size()){
            CGPoint point=roadPoints.get(position);
            float time=CGPointUtil.distance(roadPoints.get(position-1),point)/speed;//根据两个点之间的距离计算要花的时间
            CCMoveTo moveTo=CCMoveTo.action(time,point);//用一定时间移动到这个点
            CCSequence ccSequence=CCSequence.actions(moveTo, CCCallFunc.action(this,"moveToNext"));//移动完成后再次调用该方法，递归;用反射来拿到该方法，所以方法要public
            sprite1.runAction(ccSequence);
        }else{
            //移动完成
            sprite1.stopAllActions();//停止动作
            dance();
            SoundEngine soundEngine=SoundEngine.sharedEngine();
            //上下文
            soundEngine.playSound(CCDirector.theApp,R.raw.psy,true);//true表示循环播放
        }
    }

    /**
     * 跳舞，移动完成后调用
     */
    private void dance(){
        CCJumpBy ccJumpBy=CCJumpBy.action(2,ccp(-20,20),10,2);
        CCRotateBy by=CCRotateBy.action(1,360);
        CCSpawn ccSpawn=CCSpawn.actions(ccJumpBy,by);
        CCSequence sequence=CCSequence.actions(ccSpawn,ccSpawn.reverse());
        CCRepeatForever forever=CCRepeatForever.action(sequence);
        sprite1.runAction(forever);
    }
    /**
     *
     * @param event
     * @return
     */
    @Override
    public boolean ccTouchesMoved(MotionEvent event) {
        map.touchMove(event,map);//地图随着手指的移动而移动，要让该方法生效，要让地图的锚点在中心位置
        return super.ccTouchesMoved(event);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        this.onExit();//暂停
        this.getParent().addChild(new PauseLayer());//拿到场景，添加新的图层,此时的游戏图层被覆盖了是不可操作的
        return super.ccTouchesBegan(event);
    }

    /**
     * 游戏暂停时展示的图层
     */
    private class PauseLayer extends  CCLayer{

        private final CCSprite heart;

        public PauseLayer(){
            this.setIsTouchEnabled(true);//一定要开起触摸开关
            heart = CCSprite.sprite("heart.png");
            //获取屏幕尺寸
            CGSize winSize=CCDirector.sharedDirector().getWinSize();
            heart.setPosition(winSize.width/2,winSize.height/2);
            this.addChild(heart);
        }

        @Override
        public boolean ccTouchesBegan(MotionEvent event) {
            CGRect boundingBox=heart.getBoundingBox();
            //把安卓中坐标点转化成cocos2d的坐标的点
            CGPoint convertTouchToNodeSpace=this.convertTouchToNodeSpace(event);
            if(CGRect.containsPoint(boundingBox,convertTouchToNodeSpace)){//判断点击的点是否在heart上
                this.removeSelf();//移除当前图层
                DemoLayer.this.onEnter();//游戏继续
            }

            return super.ccTouchesBegan(event);
        }
    }
}
