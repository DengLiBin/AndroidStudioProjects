package com.example.denglibin.cocos2ddemo.bean;

import com.example.denglibin.cocos2ddemo.utils.PlantsDb;

import org.cocos2d.nodes.CCSprite;

/**
 * Created by DengLibin on 2016/5/13.
 */
public class Plant extends CCSprite {
    public Plant(int i){
        super(PlantsDb.getPlantPaht(i));
    }
}
