package com.denglibin.game.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.denglibin.game.Boy;
import com.denglibin.game.Face;
import com.denglibin.game.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by DengLibin on 2016/3/9 0009.
 */
//想让SurfaceHolder.Callback生效，必须SurfaceHolder.addCallback(SurfaceHolder.Callback)
public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private SurfaceHolder surfaceHolder;
    private RenderThread renderThread;
    private boolean flag;//线程开启和停止的标记
    private Boy boy;
//    private Face face;
    private List<Face> faceList;//将创建的笑脸放到该集合中
    private  MyButton button;
    //构造方法
    public GameView(Context context) {
        super(context);
        //拿到SurfaceHolder
        surfaceHolder=getHolder();
        //给SurfacHolder添加一个SurfaceHolder.Callback
        surfaceHolder.addCallback(this);//相当于添加了一个监听器
    }
    //构造方法
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //构造方法
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //当SurfaceView创建的时候调用
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //小人的图片
        Bitmap boyBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.avatar_boy);
        //笑脸的图片
       // Bitmap faceBitmap=BitmapFactory.decodeResource(getResources(),R.drawable.rating_small);
        //按钮的图片
        Bitmap defaultBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.bottom_default);
        //按下时的图片
        Bitmap pressBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.bottom_press);
        //创建小人对象
        boy=new Boy(boyBitmap,new Point(0,0));
        //创建按钮
        button=new MyButton(defaultBitmap,new Point(360,getHeight()-800),pressBitmap);
        button.addClickListener(new MyButton.OnClickListener() {
            @Override
            public void click() {//复写click（）方法，该方法在button.click()方法中被调用
                boy.move(boy.DOWN);
            }
        });
        faceList=new CopyOnWriteArrayList<Face>();//创建集合,用这个API可以在遍历的过程中增删，对内存开销比较大
        //创建笑脸对象
        //face=new Face(faceBitmap,new Point(0,0));笑脸对象的创建已经交给了Boy,这里不需创建了
        System.out.println("surfaceCreated");
        //创建线程对象，然后开启
        renderThread=new RenderThread();
        flag=true;
        renderThread.start();
    }
    //当SurfaceView大小改变的时候调用
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        System.out.println("surfaceChanged");
    }
    //当SurfaceView销毁的时候调用
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        System.out.println("surfaceDestroyed");
        flag=false;//停止线程
    }

    //一个线程
    private class RenderThread extends Thread{
        @Override
        public void run() {
           while(flag){//会不停的调用drawUI()方法，重复绘制，不停的覆盖，相当于帧动画
                try{
                    //Thread.sleep(500);
                    long startTime=System.currentTimeMillis();
                    drawUI();//内部类方法访问外部类的方法：直接访问
                    long endTime=System.currentTimeMillis();
                    long dTtime=endTime-startTime;//绘制一次（执行一次drawUI方法）的时间:ms
                    int fps=(int)(1000/dTtime);//1秒钟可以绘制的次数，帧数>30（要求）由人眼决定的，小于30帧会很卡，太高就浪费cpu
                }catch (Exception e){
                    e.printStackTrace();
                }

           }
        }
    }

    //在SurfaceView上绘制图像,做游戏就是在surfaceView上画，画成什么样就是什么样
    private void drawUI() {
        //锁定画布
        Canvas lockCanvas=surfaceHolder.lockCanvas();

        //创建画笔
        Paint  paint=new Paint();
        paint.setColor(Color.GRAY);//画笔颜色
        //绘制界面,对上一次绘制的图像盖住（因为是循环绘制的）
        lockCanvas.drawRect(0,0,getWidth(),getHeight(),paint);//矩形，左上角坐标为（0,0）,宽高为屏幕的宽高

        boy.drawSelf(lockCanvas);//把小人绘制到屏幕上，实际上是调用的Canvas的drawBitmap方法
        button.drawSelf(lockCanvas);//把按钮绘制到屏幕上
    /* 单个笑脸的移动
        if(face!=null) {
            face.move();//循环调用该方法，每调用一次，笑脸的坐标都会改变，下次绘制时，就会在新的位置上绘制
            face.drawSelf(lockCanvas);//把笑脸（位置坐标已经发生改变）绘制到屏幕上

        }
        */
        for(Face face:faceList){//遍历集合
            face.drawSelf(lockCanvas);
            face.move();//集合中的笑脸都移动起来

            //笑脸移到屏幕外就删除这个对象，CopyOnWriteArrayList<Face>();用这个API可以在遍历的过程中增删，对内存开销比较大
            if(face.position.x<0||face.position.x>getWidth()||face.position.y<0||face.position.y>getHeight()){
                faceList.remove(face);
            }
        }
        //解锁画布并提交(一定不能少，如果不提交，上面的步骤都没效果)
        surfaceHolder.unlockCanvasAndPost(lockCanvas);//若果退出的时候，子线程刚好走到这里，就会报空指针异常，可以捕获处理
        /*
        清淡文件配置主题：
        android:theme="@android:style/Theme.Black.NoTitleBar.Fullscreen"
        */
    }

    //点击屏幕创建笑脸对象（在GameActivity的onTouchEvent方法中被调用）
    public void handleTouch(MotionEvent event){
        switch (event.getAction()){
        //  case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                int x=(int)event.getRawX();
                int y=(int)event.getRawY();
                System.out.println("当前点击坐标：" + x + "," + y);
                if(button.isClick(new Point(x,y))){//判断点击的点是否在按钮图片中
                //boy.move(boy.DOWN);//改变小人的坐标
                    button.click();//会调用监听器的click（）方法
                }else{
                    Face face=boy.createFace(getContext(),new Point(x,y));
                    faceList.add(face);//点一次屏幕就创建一个笑脸，并放入到该集合中
                    face=null;
                }
                break;
            case MotionEvent.ACTION_UP:
                button.setIsClick(false);
                break;

            default:
                break;
        }
    }
}
