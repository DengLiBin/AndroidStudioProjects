package com.denglibin.my_watch.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;


import com.denglibin.my_watch.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/4/22.
 */
public class WatchView extends View{
    private String TAG="WatchView";
    private float m_PaddingLeft = 5;
    private float m_PaddingRight = 5;
    private float m_PaddingTop = 5;
    private float m_PaddingBottom = 5;

    float cx;//中心点坐标
    float cy;

    private float m_Width = 100;
    private float m_Height = 100;
    private float m_WatchCycleWidth=3;//表盘的外环宽度
    private float m_WatchRadius=150;//表盘的半径
    private float m_SecondCursorLength=120;//秒针的长度
    private float m_SecondCursorDegrees=0;//秒针旋转的角度
    private Path m_SecondCursorPath=new Path();//秒针的形状,通过Path(路径)来绘制

    private float m_MinuteCursorLength=90;//分针长度
    private float m_MinuteCursorDegrees=0;//分针旋转的角度
    private Path m_MinuteCursorPath=new Path();//分针的形状

    // 时钟的长度
    private float m_HourCursorLength = 90;
    // 时钟的形状
    private Path m_HourCursorPath = new Path();
    // 时钟旋转的角度
    private float m_HourCursorDegrees = 0;

    private Paint m_paint=new Paint();//画笔
    private Typeface m_face;//字体样式
    private Rect m_LogoRect;
    private Drawable m_Logo;

    private Rect m_BackgroundRect;
    private GradientDrawable m_Background;

    // 标记watch是否在走
    private boolean isRunning;
    private Handler handler=new Handler();

    public WatchView(Context context){
        this(context,null);
    }
    public WatchView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.My_Watch,defStyleAttr,0);
        m_WatchCycleWidth=typedArray.getDimensionPixelSize(R.styleable.My_Watch_cycleWidth,(int)m_WatchCycleWidth);
        m_WatchRadius=typedArray.getDimensionPixelSize(R.styleable.My_Watch_watchRadius,(int)m_WatchRadius);

        m_SecondCursorLength = typedArray.getDimensionPixelSize(R.styleable.My_Watch_secondLength, (int) m_SecondCursorLength);
        m_MinuteCursorLength = typedArray.getDimensionPixelSize(R.styleable.My_Watch_minuteLength, (int) m_MinuteCursorLength);
        m_HourCursorLength = typedArray.getDimensionPixelSize(R.styleable.My_Watch_hourLength, (int) m_HourCursorLength);

        m_Logo=typedArray.getDrawable(R.styleable.My_Watch_watchlogo);
        typedArray.recycle();

        m_face=Typeface.createFromAsset(getContext().getAssets(),"fonts/samplefont.ttf");

        initSecondCursorPath();
        initMinuteCursorPath();
        initHourCursorPath();

        initBackground();
        initLogo();
    }

    /**
     * 绘制秒针
     */
    private void initSecondCursorPath() {
        //针尖坐标
        float x1=m_PaddingLeft+m_WatchRadius;
        float y1=m_PaddingTop+m_WatchRadius- m_SecondCursorLength;
        //针左脚坐标
        float x2=x1-3;
        float y2=m_PaddingTop+m_WatchRadius+8;
        //针尾坐标
        float x3=x1;
        float y3=m_PaddingTop+m_WatchRadius+4;
        //针右脚坐标
        float x4=x1+3;
        float y4=y2;

        m_SecondCursorPath.moveTo(x1,y1);//起点
        m_SecondCursorPath.lineTo(x2,y2);//直线
        m_SecondCursorPath.lineTo(x3,y3);//直线
        m_SecondCursorPath.lineTo(x4,y4);//直线
        m_SecondCursorPath.lineTo(x1,y1);//直线

        m_SecondCursorPath.close();//关闭
    }


    /**
     * 绘制分针
     */
    private void initMinuteCursorPath() {
        // 针尖
        float x1 = m_PaddingLeft + m_WatchRadius;
        float y1 = m_PaddingTop + m_WatchRadius - m_MinuteCursorLength;
        // 针左角
        float x2 = x1 - 3;
        float y2 = m_PaddingTop + m_WatchRadius + 8;
        // 针尾
        float x3 = x1;
        float y3 = m_PaddingTop + m_WatchRadius + 4;
        // 针右角
        float x4 = x1 + 3;
        float y4 =y2;

        m_MinuteCursorPath.moveTo(x1, y1);
        m_MinuteCursorPath.lineTo(x2, y2);
        m_MinuteCursorPath.lineTo(x3, y3);
        m_MinuteCursorPath.lineTo(x4, y4);
        m_MinuteCursorPath.lineTo(x1, y1);

        m_MinuteCursorPath.close();
    }

    /**
     * 绘制时针
     */
    private void initHourCursorPath() {
        // 针尖
        float x1 = m_PaddingLeft + m_WatchRadius;
        float y1 = m_PaddingTop + m_WatchRadius - m_HourCursorLength;
        // 针左角
        float x2 = x1 - 3;
        float y2 = m_PaddingTop + m_WatchRadius + 8;
        // 针尾
        float x3 = x1;
        float y3 = m_PaddingTop + m_WatchRadius + 4;
        // 针右角
        float x4 = x1 + 3;
        float y4 = m_PaddingTop + m_WatchRadius + 8;

        m_HourCursorPath.moveTo(x1, y1);
        m_HourCursorPath.lineTo(x2, y2);
        m_HourCursorPath.lineTo(x3, y3);
        m_HourCursorPath.lineTo(x4, y4);
        m_HourCursorPath.lineTo(x1, y1);

        m_HourCursorPath.close();
    }

    private  void  initBackground(){
        //矩形
        m_BackgroundRect=new Rect((int)m_PaddingLeft,(int)m_PaddingTop,//左上角点坐标
                (int)(m_PaddingLeft+2*m_WatchRadius),//右上角点的横坐标
                (int)(m_PaddingTop+2*m_WatchRadius));//左下角的纵坐标

        m_Background = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[] { Color.LTGRAY, Color.WHITE, Color.LTGRAY });
        m_Background.setShape(GradientDrawable.RECTANGLE);
        m_Background.setGradientRadius((float) (Math.sqrt(2) * m_WatchRadius));
    }

    /**
     * logo的位置
     */
    private void initLogo(){
        int left = (int) (m_PaddingLeft + m_WatchRadius * 5 / 6);
        int top = (int) (m_PaddingTop + m_WatchRadius * 2 / 4);
        int right = (int) (m_PaddingLeft + m_WatchRadius * 7 / 6);
        int bottom = (int) (m_PaddingTop + m_WatchRadius * 10 / 12);

        m_LogoRect = new Rect(left, top, right, bottom);
    }

    /**
     * 设置控件的大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) (m_PaddingLeft + m_PaddingRight + m_WatchRadius * 2f),
                (int) (m_PaddingTop + m_PaddingBottom + m_WatchRadius * 2f));
    }

    /**
     * 绘制控件样子
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawWatchCycle(canvas);
        drawTimeScale(canvas);
        drawLogo(canvas);

        drawTimeNum(canvas);
        drawHourCursor(canvas);
        drawMinuteCursor(canvas);
        drawSecondCursor(canvas);
    }

    /**
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas){
        m_Background.setBounds(m_BackgroundRect);//设置边界
        canvas.save();
        canvas.translate(0,0);
        m_Background.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        m_Background.setCornerRadii(new float[] { m_WatchRadius, m_WatchRadius,
                m_WatchRadius, m_WatchRadius, m_WatchRadius, m_WatchRadius,
                m_WatchRadius, m_WatchRadius });
        m_Background.draw(canvas);
        canvas.restore();
    }

    /**
     * 绘制表的外环（圆环）
     * @param canvas
     */
    private void drawWatchCycle(Canvas canvas){
        cx=m_PaddingLeft+m_WatchRadius;//中心x坐标
        cy=m_PaddingTop+m_WatchRadius;//中心y坐标
        float radius=m_WatchRadius;
        m_paint.setColor(Color.BLACK);
        m_paint.setStyle(Paint.Style.STROKE);//设置是否为空心
        m_paint.setStrokeWidth(m_WatchCycleWidth);//线宽度
        m_paint.setAntiAlias(true);//抗锯齿

        canvas.drawCircle(cx,cy,radius,m_paint);
    }

    /**
     * 画时间刻度
     * @param canvas
     */
    private void drawTimeScale(Canvas canvas){
        canvas.save();
        for(int i=1;i<=60;i++){
            canvas.rotate(6,cx,cy);//旋转6度
            float startX=m_PaddingLeft+m_WatchRadius;
            float startY=m_PaddingTop;
            float stopX=startX;
            float stopY=m_PaddingTop+4;
            m_paint.setStrokeWidth(1);
            m_paint.setColor(Color.GRAY);
            if(i%15==0){
                stopY=m_PaddingTop+10;
                m_paint.setStrokeWidth(2);
                m_paint.setColor(Color.DKGRAY);
            }else if(i%5==0){
                stopY = m_PaddingTop + 7;
                m_paint.setStrokeWidth(2);
                m_paint.setColor(Color.DKGRAY);
            }
            //画线
            canvas.drawLine(startX,startY,stopX,stopY,m_paint);
        }
        canvas.restore();
    }

    /**
     * 画logo
     * @param canvas
     */
    private void drawLogo(Canvas canvas){
        m_Logo.setBounds(m_LogoRect);
        canvas.save();
        m_Logo.draw(canvas);
        canvas.restore();
    }

    /**
     * 画时间数字
     * @param canvas
     */
    private void drawTimeNum(Canvas canvas){
        canvas.save();
        for(int i=1;i<=12;i++){
            canvas.rotate(30, cx, cy);//旋转30度
            float x = m_PaddingLeft + m_WatchRadius;
            float y = m_PaddingTop + m_WatchRadius * 5 / 16;

            String text = i + "";
            m_paint.setColor(Color.BLACK);
//			mPaint.setTypeface(mFace);
            m_paint.setTextAlign(Paint.Align.CENTER);

            float textHeight = 16;
            m_paint.setTextSize(textHeight);
            float textWidth = m_paint.measureText(text);

            float textCenterX = x;
            float textCenterY = y - textHeight / 2;

            canvas.rotate(-30 * i, textCenterX, textCenterY);
            canvas.drawText(text, x, y, m_paint);
            canvas.rotate(30 * i, textCenterX, textCenterY);
        }
        canvas.restore();
    }

    /**
     * 画时针
     * @param canvas
     */
    private void drawHourCursor(Canvas canvas){
        m_paint.setColor(Color.BLUE);
        m_paint.setStyle(Paint.Style.FILL);
        m_paint.setAntiAlias(true);// 设置 抗锯齿

        canvas.save();
        // 将画布旋转
        canvas.rotate(m_HourCursorDegrees, cx, cy);
        // 旋转完再画
        canvas.drawPath(m_HourCursorPath, m_paint);
        canvas.restore();
    }

    /**
     * 画分针
     * @param canvas
     */
    private void drawMinuteCursor(Canvas canvas){
        m_paint.setColor(Color.GREEN);
        m_paint.setStyle(Paint.Style.FILL);
        m_paint.setAntiAlias(true);// 设置 抗锯齿

        canvas.save();
        // 将画布旋转
        canvas.rotate(m_MinuteCursorDegrees, cx,cy);
        // 旋转完再画
        canvas.drawPath(m_MinuteCursorPath, m_paint);
        canvas.restore();
    }

    /**
     * 画秒针
     * @param canvas
     */
    private void drawSecondCursor(Canvas canvas){
        m_paint.setColor(Color.RED);
        m_paint.setStyle(Paint.Style.FILL);
        m_paint.setAntiAlias(true);//抗锯齿
        canvas.save();
        //将画布旋转
        canvas.rotate(m_SecondCursorDegrees,cx,cy);//旋转的角度：当前时间的秒数*6
        //旋转完再画
        canvas.drawPath(m_SecondCursorPath,m_paint);
        canvas.restore();
    }

    public void start(){
        if(isRunning){
            return;
        }
        isRunning=true;
        doRunWatch();
    }

    public void stop(){
        isRunning=false;
    }

    private void doRunWatch(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long currentTimeMillis=System.currentTimeMillis();
                Calendar calendar=Calendar.getInstance();
                calendar.setTimeInMillis(currentTimeMillis);

                int hour=calendar.get(Calendar.HOUR);
                int minute=calendar.get(Calendar.MINUTE);
                int second=calendar.get(Calendar.SECOND);

                m_SecondCursorDegrees = second * 360f / 60;//绘制秒针时，每画一次画布旋转的角度
                m_MinuteCursorDegrees = minute * 360f / 60;
                m_HourCursorDegrees = hour * 360f / 12;

                invalidate();//重新执行onDraw方法

                doRunWatch();
            }
        },1000);
    }

}
/*
* 理解：调用doRunWatch()方法，每隔一秒获取一次当前系统的时间，
* 根据当前的时间确定时针，分针，秒针应该从12开始转多少角度。
* 每秒钟画一次，每次画都是重新获取时间计算角度，确定画时针，分针，秒针时，画布要旋转的角度
* */