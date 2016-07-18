package com.denglibin.topnews.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/4/26.
 */
public class GetJsonStringUtils {
    public static String getJson(Context context){
        StringBuilder builder=new StringBuilder();
        try {
            InputStream in=context.getResources().getAssets().open("json/newsTab.json");
            byte[] bytes=new byte[1024];
            int ch=0;
            while ((ch=in.read(bytes))!=-1){
                builder.append(new String(bytes,0,ch));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("assets目录文件获取异常");
        }
        return builder.toString();
    }
}
