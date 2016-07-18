package com.example.denglibin.cocos2ddemo.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DengLibin on 2016/5/13.
 */
public class PlantsDb {

    //模拟数据库
    static Map<Integer,HashMap<String,String>> db;
    static{
        db=new HashMap<Integer, HashMap<String, String>>();
        String format="image/fight/chose/choose_default%02d.png";
        for(int i=1;i<=9;i++){
            HashMap<String ,String> value=new HashMap<String,String>();
            value.put("path",String.format(format,i));
            value.put("sun",50+"");
            db.put(i,value);
        }
    }

    public  static String getPlantPaht(int i){
        return db.get(i).get("path");
    }
    public static String getSun(int i){
        return db.get(i).get("sun");
    }
}
