package com.denglibin.game.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.denglibin.game.Sprite;

/**
 * Created by DengLibin on 2016/3/14 0014.
 */
public class MyButton extends Sprite {
    private boolean isClick;
    private Bitmap pressBitmap;
    private  OnClickListener listener;
    public MyButton(Bitmap defaultBitmap, Point position,Bitmap pressBitmap) {
        super(defaultBitmap, position);
        this.pressBitmap=pressBitmap;
    }
    public interface  OnClickListener{
        void click();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if(isClick){
            //绘制一个按下的图片
            canvas.drawBitmap(pressBitmap,position.x,position.y,null);
        }else{
            super.drawSelf(canvas);
        }
    }
    //判断点击的点是否点中了当前的按钮
    public boolean isClick(Point touchPoint){
        //创建一个矩形（位置和宽高就是按钮图片的位置和宽高）
        Rect rect=new Rect(position.x,position.y,position.x+pressBitmap.getWidth(),position.y+pressBitmap.getHeight());
        isClick=rect.contains(touchPoint.x,touchPoint.y);//点击的点的坐标是否在矩形内
        return isClick;
    }

    public void setIsClick(boolean isClick) {
        this.isClick = isClick;
    }
    public void addClickListener(OnClickListener listener){
        this.listener=listener;
    }
    //按钮点击时调用该方法
    public void click(){
        if(listener!=null){//判断是否已经添加监听器
            listener.click();
        }
    }
}
