package com.denglibin.mygame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by DengLibin on 2016/5/11.
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    //用于控制SurfaceView
    private SurfaceHolder sfh;
    private Paint paint;
    //坐标
    private int textX=10;
    private int textY=10;

    public GameSurfaceView(Context context) {
        this(context,null);
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //实例化SurfaceHolder
        sfh=this.getHolder();
        //添加监听
        sfh.addCallback(this);
        //实例化一个画笔
        paint=new Paint();
        //设置画笔颜色为白色
        paint.setColor(Color.GREEN);
    }

    /**
     * 创建完成后响应的函数
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        myDraw();//绘制
    }

    /**
     * 状态发生改变时响应的函数
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * 销毁时回调的函数
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /**
     * 触摸监听
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        textX= (int) event.getX();
        textY= (int) event.getY();
        myDraw();//再次绘制
       return true;
    }

    private void myDraw() {
        Canvas canvas=sfh.lockCanvas();//锁定画布，画布大小默认是屏幕的大小，下次绘制时拿到的还是同一张画布，所以要进行刷屏操作
        //刷屏操作，四种方式
        //canvas.drawRect(0,0,this.getWidth(),this.getHeight(),paint);//每次绘制时都先绘制一个屏幕大小的矩形，覆盖在画布上
       // canvas.drawColor(Color.WHITE);
        canvas.drawRGB(0,0,0);//自定义RGB颜色
        //canvas.drawBitmap();用图片做背景
        //sfh.lockCanvas(Rect rect);可以拿到一个自定义大小的画布
        canvas.drawText("SurfaceGame",textX,textY,paint);
        sfh.unlockCanvasAndPost(canvas);//解锁画布和提交
    }

}
/*
*    在上面的程序中定义了一个SurfaceHolder类的实例，此类提供控制SurfaceView的大小、
* 格式等，并且主要用于监听SurfaceView的状态，其实SurfaceView只是保存当前视图的
* 像素数据，在使用SurfaceView时，并不会与SurfaceView直接打交道，而是通过SurfaceHolder
* 来控制，使用SurfaceHolder的lockCanvas()函数来获取到SurfaceView的Canvas对象，
* 再通过在Canvas上绘制内容来修改SurfaceView中的数据。
*
*     lockCanvas()函数不仅是获取Canvas,同时还对获取的Canvas画布加锁，主要是防止
* SurfaceView在绘制过程中被修改，摧毁等发生的状态改变。与该函数对应的还有一个
* unlockCanvasAndPost(Canvas canvas),用来解锁画布和提交。
*
*
* */

