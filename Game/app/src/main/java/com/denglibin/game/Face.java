package com.denglibin.game;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Created by DengLibin on 2016/3/13 0013.
 */
public class Face extends Sprite{
    int speed=6;
    int tx,ty;
    int touchx,touchy;

    public Face(Bitmap defaultBitmap, Point position,Point touchPoint) {
        super(defaultBitmap, position);
        touchx=touchPoint.x;
        touchy=touchPoint.y;
        int X=touchx-position.x;
        int Y=touchy-position.y;

        int D=(int)Math.sqrt(X*X+Y*Y);

        //每次移动（因为绘制图形是放在线程的一个循环中，因此会不停的循环绘制，每次绘制时改变绘制点的坐标，实现动画效果）x,y坐标的改变值
        tx=speed*X/D;
        ty=speed*Y/D;
        System.out.println("X和Y:"+X+","+Y);

    }
    //每次绘制都让笑脸的坐标位置改变
    public void move(){
            this.position.x+=tx;
            this.position.y+=ty;
            System.out.println("tx和ty："+tx+","+ty);
            System.out.println("move一次的坐标："+this.position.x+","+this.position.y);

    }
}
