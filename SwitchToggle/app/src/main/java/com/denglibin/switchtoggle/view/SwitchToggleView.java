package com.denglibin.switchtoggle.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/4/22.
 */
public class SwitchToggleView extends View {
    private Bitmap m_switchBackground;
    private Bitmap m_switchSlide;
    private Paint m_paint=new Paint();
    private boolean isOpened=false;//默认是关闭的
    public SwitchToggleView(Context context) {
        super(context);
    }

    public SwitchToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 背景，使用该控件的时候设置
     * @param resourceId
     */
    public void setSwitchBackground(int resourceId){
        m_switchBackground= BitmapFactory.decodeResource(getResources(),resourceId);
    }

    /**
     * 滑块,使用该控件的时候设置
     * @param resourceId
     */
    public void setSwitchSlide(int resourceId){
        m_switchSlide=BitmapFactory.decodeResource(getResources(),resourceId);
    }

    /**控制控件的大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if(m_switchBackground!=null){
            int width=m_switchBackground.getWidth();
            int height=m_switchBackground.getHeight();
            setMeasuredDimension(width,height);//设置控件的大小刚和背景图大小一样
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 绘制控件样子
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景
        if(m_switchBackground!=null){
            int left=0;
            int top=0;
            canvas.drawBitmap(m_switchBackground,left,top,m_paint);//相当于绘制了一个和m_switchBackground一样的bitmap
            if(m_switchSlide!=null){//绘制滑块
                if(!isOpened){
                    canvas.drawBitmap(m_switchSlide,0,0,m_paint);
                }else{
                    canvas.drawBitmap(m_switchSlide,m_switchBackground.getWidth()/2.6f,0,m_paint);
                }

            }
        }

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float currentX=event.getX();//点击的x坐标
                int slideWidth=m_switchSlide.getWidth();//滑块的宽度
                //如果按下的时候滑块是关闭及的
                if(!isOpened){
                    if(currentX<slideWidth/2f){//点击的是滑块的左侧
                        //把滑块绘制在左侧
                        //this.invalidate();//重新绘制
                    }else{//点击的是滑块的右侧
                        isOpened=true;//置为打开状态
                        this.invalidate();//重新绘制
                    }
                }else{//如果按下时，滑块是打开状态
                    if(currentX<slideWidth/2f){//点击的是滑块的左侧
                        isOpened=false;
                        invalidate();
                    }else{

                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
