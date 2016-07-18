package com.denglibin.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by DengLibin on 2016/3/13 0013.
 */
public class Boy extends Sprite {
    public static  final  int DOWN=0;
    int speed=6;
    public Boy(Bitmap defaultBitmap, Point position) {
        super(defaultBitmap, position);
    }

    //创建笑脸
    public Face createFace(Context context,Point touchPoint){
        Bitmap faceBitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.rating_small);
        Face face=new Face(faceBitmap,new Point(position.x+80,position.y+70),touchPoint);//根据小人的位置确定笑脸的位置
        return  face;
    }

    public void move(int d){
        if(d==DOWN){
            this.position.y+=speed;//改变y坐标
        }
    }
}
