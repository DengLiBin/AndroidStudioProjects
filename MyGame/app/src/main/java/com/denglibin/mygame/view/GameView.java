package com.denglibin.mygame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.denglibin.mygame.utils.DensityUtils;

/**
 * Created by DengLibin on 2016/5/6 0006.
 */
public class GameView extends View{
    private float text_X=20f;
    private float text_Y=20f;
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusableInTouchMode(true);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusableInTouchMode(true);
    }

    public GameView(Context context) {
        super(context);
        setFocusableInTouchMode(true);
    }

    /**
     * 重写绘图函数
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //黑色背景
        canvas.drawColor(Color.BLACK);
        System.out.println("坐标:"+text_X+","+text_Y);
        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
       paint.setColor(0xffff0000);
        paint.setTextSize(DensityUtils.sp2px(getContext(),25));
        //绘制文本
       canvas.drawText("Game",text_X,text_Y,paint);//坐标为(10,10)



    }

    /**
     * 重写触屏事件函数
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // text_X= event.getRawX();//相对于整个屏幕
       // text_Y= event.getRawY();
        text_X=event.getX();//当前这个控件中
        text_Y=event.getY();
        System.out.println("touch坐标:"+text_X+","+text_Y);
        //invalidate();
        //postInvalidate();
        //drawAlphaAnimation();//点击屏幕时开启动画效果
        drawScaleAnimation();
        return true;
    }
    /**
     * 渐变动画效果
     */
    public void drawAlphaAnimation(){

        AlphaAnimation alphaAnimation=new AlphaAnimation(0.1f,1.0f);
        alphaAnimation.setDuration(3000);//3秒钟
        this.startAnimation(alphaAnimation);
    }

    /**
     * 缩放动画效果
     */
    public void drawScaleAnimation(){
        ScaleAnimation scaleAnimation=new ScaleAnimation(0.0f,2.0f,1.5f,1.5f,
                Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.0f);
        /*
        参数1：动画起始时X坐标上的伸缩比例
        参数2：动画结束时X坐标上的伸缩比例
        参数3：动画起始时Y坐标上的伸缩比例
        参数4：动画结束时Y坐标上的伸缩比例
        参数5：动画在Y轴相对于物体的位置类型
        参数6：动画相对于物体X坐标的位置
        参数7：动画在Y轴相对于物体的位置类型
        参数8：动画相对于物体Y坐标的位置
        说明:Animation.ABSOLUTE:相对屏幕左上角，绝对位置，Animation.RELATIVE_TO_SELF:相对自身View,取0时相对于自身左上角,
           取1时，相对于自身右下角；Animation.RELATIVE_TO_PARENT:相对于父View
         */
        scaleAnimation.setDuration(2000);
        this.startAnimation(scaleAnimation);
    }
}
