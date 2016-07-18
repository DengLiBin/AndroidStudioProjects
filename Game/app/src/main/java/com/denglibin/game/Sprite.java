package com.denglibin.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * 精灵，最小单位的基类（图片，图片的位置）
 * Created by DengLibin on 2016/3/13 0013.
 */
public abstract class Sprite {
    private Bitmap defaultBitmap;//默认显示的图片
    public Point position;//位置

    public Sprite(Bitmap defaultBitmap, Point position) {
        this.defaultBitmap = defaultBitmap;
        this.position = position;
    }

    //把图片绘制到位置上
    public void drawSelf(Canvas canvas){
        canvas.drawBitmap(defaultBitmap,position.x,position.y,null);
    }
}
