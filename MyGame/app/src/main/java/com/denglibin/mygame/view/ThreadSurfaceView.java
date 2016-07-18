package com.denglibin.mygame.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.denglibin.mygame.R;

/**
 * Created by DengLibin on 2016/5/11.
 */
public class ThreadSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder sfh;
    private Paint paint;
    private int textX=10,textY=10;
    private Thread th;//声明一个线程
    private boolean flag;//线程消亡的标识
    private Canvas canvas;//声明画布
    private int screenW,screenH;
    public ThreadSurfaceView(Context context) {
        this(context,null);
    }

    public ThreadSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ThreadSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sfh=this.getHolder();
        sfh.addCallback(this);
        paint=new Paint();
        paint.setColor(Color.WHITE);
        setFocusable(true);//设置焦点
        Thread.currentThread().setName("main thread");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenW=this.getWidth();
        screenH=this.getHeight();
        flag=true;
        //实例化线程,一定要放在当前方法中执行
        System.out.println("surfaceCreate方法运行的线程:"+Thread.currentThread().getName());//主线程

        th=new Thread(this);
        th.setName("th Thread");
        th.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        System.out.println("坐标:"+textX+","+textY);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag=false;
    }

    /**
     * 线程开启时执行此方法
     */
    @Override
    public void run() {
        while(flag){
            long start=System.currentTimeMillis();
            //drawBitmap(R.mipmap.ic_launcher);
            //myDraw();//SurfaceView，可以在主线程之外的线程中向屏幕绘图,而View是不行的
            //logic();//游戏逻辑
           drawClip();//设置可视区域
            long end=System.currentTimeMillis();
            try{
                if(end-start<50){
                    Thread.sleep(50-(end-start));
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        textX= (int) this.getX();
        textY= (int) this.getY();
        return  true;
    }

    public void myDraw(){
        try{
            canvas=sfh.lockCanvas();
            if(canvas!=null){
                //绘制矩形
               // canvas.drawRect(0,0,screenW,screenH,paint);
                //利用填充画布刷屏
               // canvas.drawColor(Color.BLACK);
                //利用填充画布制定的颜色分量刷屏
                canvas.drawRGB(255,255,255);
                canvas.drawText("ThreadSurfaceView",textX,textX,paint);

                Paint paint1=new Paint();
                canvas.drawCircle(40,30,20,paint1);
                paint1.setAntiAlias(true);//无锯齿
                canvas.drawCircle(100,30,20,paint1);
                //设置画笔的透明度
                canvas.drawText("无透明度",100,70,new Paint());
                Paint paint2=new Paint();
                //设置画笔为半透明
                paint2.setAlpha(0x77);
                canvas.drawText("半透明",20,70,paint2);
                //设置绘制文本的锚点
                canvas.drawText("锚点",20,90,new Paint());
                //设置以文本的中心点绘制
                Paint paint3=new Paint();
                paint3.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("锚点",20,105,paint3);
                //获取文本的长度
                Paint paint4=new Paint();
                float len=paint4.measureText("文本的宽度:");
                canvas.drawText("文本长度:"+len,20,130,new Paint());
                //设置画笔的样式
                canvas.drawRect(new Rect(20,140,40,160),new Paint());
                Paint paint5=new Paint();
                paint5.setStyle(Paint.Style.STROKE);
                canvas.drawRect(new Rect(60,140,80,160),paint5);
                //设置画笔的颜色
                Paint paint6=new Paint();
                paint6.setColor(Color.GRAY);
                canvas.drawText("灰色",30,180,paint6);
                //设置画笔的粗细程度
                canvas.drawLine(20,200,70,220,new Paint());
                Paint paint7=new Paint();
                paint7.setStrokeWidth(7);
                canvas.drawLine(20,220,70,220,paint7);
                //设置画笔绘制文本的文字粗细
                Paint paint8=new Paint();
                paint8.setTextSize(20);
                canvas.drawText("文字大小",20,260,paint8);
                //设置画笔的ARGB分量
                Paint paint9=new Paint();
                paint9.setARGB(0x77,0xff,0x00,0x00);
                canvas.drawText("红色半透明",20,290,paint9);
                System.out.println("myDraw方法运行的线程:"+Thread.currentThread().getName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(canvas!=null){
                sfh.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void drawBitmap(int sourcesId){
        canvas=sfh.lockCanvas();
        canvas.save();//保存一下画布当前的状态
        canvas.rotate(30);//默认中心点为屏幕中心点，是对画布进行旋转
        //canvas.translate(10,10);//画布平移，x,y正方向10个像素
       // canvas.scale(2f,2f,0,0);//画布缩放，x轴2倍，y轴2倍，基准点为0,0
        Bitmap bmp= BitmapFactory.decodeResource(getResources(),sourcesId);
        //canvas.rotate(30,bmp.getWidth/2,bmp.getHeight/2);//设置旋转中心为图片的中心点
        canvas.drawBitmap(bmp,0,0,paint);

        canvas.restore();//将画布恢复至上一次保存的状态，save()和restore()要成对出现
        canvas.drawBitmap(bmp,100,0,paint);//再画一张bitmap，就不会出现旋转的效果了

        Matrix matrix=new Matrix();
        matrix.postRotate(90,0,100);//通过矩阵让位图旋转90度（旋转中心为0,100）（画布并不旋转）
        matrix.postTranslate(100,100);//让位图平移
        matrix.postScale(-1,1,100,100);//x轴镜像，就是设置他的x轴缩放比例为-1，y轴不变，基准点坐标可以是位图的左上角或左下角的坐标,例如100,100
        canvas.drawBitmap(bmp,matrix,paint);
        sfh.unlockCanvasAndPost(canvas);
    }

    /**
     * 剪切可视区域
     */
    public void drawClip(){
        Bitmap bmp=BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_launcher);
        canvas=sfh.lockCanvas();
        canvas.save();
        //canvas.clipRect(0,0,50,50);//设置矩形可视区域
       // Path path=new Path();
        //path.addCircle(30,30,30, Path.Direction.CCW);
        //canvas.clipPath(path);//设置一个圆形可视区域

        Region region=new Region();//区域的集合
        region.op(new Rect(20,20,100,100),Region.Op.UNION);//区域全部显示
        region.op(new Rect(40,20,100,100),Region.Op.XOR);//不显示交集区域
        canvas.clipRegion(region);
        System.out.println("设置可视区域后view的宽高:"+getWidth()+","+getHeight());//720*1280
        canvas.drawBitmap(bmp,0,0,paint);
        canvas.restore();
        sfh.unlockCanvasAndPost(canvas);
    }


    /**
     * 游戏逻辑
     */
    public void logic(){
        if(textX==screenW){
            textX=10;
            textY=10;
            return;
        }
        textX++;
        textY++;
        System.out.println("logic方法运行的线程:"+Thread.currentThread().getName());//子线程中

    }
}
