package com.denglibin.left_menu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/4/28.
 */
public class SlidingMenu extends ViewGroup{
    private View m_left_view;
    private View m_content_view;
    private  int m_left_width;
    private Scroller m_scroller;
    public SlidingMenu(Context context) {
        this(context,null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        m_scroller=new Scroller(context);
    }
    /**
     * 布局加载完后回调
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        m_left_view=getChildAt(0);
        m_content_view=getChildAt(1);
        LayoutParams params=m_left_view.getLayoutParams();
        m_left_width=params.width;//左边view的宽度，布局文件中已经指定了
    }


    /**
     * onMeasure方法被measure方法调用，measure方法又会被父控件调用
     * @param widthMeasureSpec 这个控件的父控件的宽度
     * @param heightMeasureSpec 这个控件的父控件的高度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量孩子
        //
        // 父和子的测量关系
        // child.measure():期望孩子的大小该怎么设置
        // widthMeasureSpec:期望值--
        // 2. 头2位：代表的是模式
        // @ 1. UNSPECIFIED： 不确定，随意，自己去定-->0
        // @ 2. EXACTLY：精确的 ---> 200 希望宽度确定为200px
        // @ 3. AT_MOST：最大的---> <200
        // 3. 后30位：数值

        // int mode = MeasureSpec.getMode(widthMeasureSpec);获得头2位
        // int size = MeasureSpec.getSize(widthMeasureSpec);获得后30位的值
        // MeasureSpec.makeMeasureSpec(200, MeasureSpec.EXACTLY);组装32位的01010

        // widthMeasureSpec:父容器希望 自己的宽度是多大
        //测量左侧
        int leftWidtMeasureSpec=MeasureSpec.makeMeasureSpec(m_left_width,MeasureSpec.EXACTLY);
        m_left_view.measure(leftWidtMeasureSpec,heightMeasureSpec);//拿到左边view的宽高尺寸
        //测量右测
        m_content_view.measure(widthMeasureSpec,heightMeasureSpec);//右测的view的宽高就是父控件的宽高
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    /**
     * 继承ViewGroup必须实现该方法,该方法会被View的Layout方法调用，Layout方法永远是相对于父容器（包含该控件的控件）
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //当前这个SlidingMenu是父容器，里面有俩孩子 m_left_view 和 m_content_view
        //相对性：父容器确定自己孩子的位置
        //getWidth 得到的事某个View的实际尺寸。
        //getMeasuredWidth 得到的是某个View想要在parent view里面占的大小

        /*getWidth(): View在设定好布局后整个View的宽度。
            getMeasuredWidth(): 对View上的内容进行测量后得到的View内容占据的宽度，
            前提是你必须在父布局的onLayout()方法或者此View的onDraw()方法里调用measure(0,0);(measure中的参数的值你自己可以定义)，
            否则你得到的结果和getWidth()得到的结果是一样的。
         */
        int left_view_width=m_left_view.getMeasuredWidth();//如果不调用onMeasure方法（在该方法中测量孩子的尺寸），是拿不到孩子的宽高尺寸的
        int left_view_height=m_left_view.getMeasuredHeight();
        System.out.println("m_left_view.getMeasuredWidth():"+left_view_width);
        //给左侧布局
        int lv_left=-left_view_width;//横坐标(负的)
        int lv_top=0;//纵坐标，左上角的坐标(lv_left,0)

        int lv_width=0;//右上角横坐标（0）
        int lv_height=left_view_height;//高
        m_left_view.layout(lv_left,lv_top,lv_width,lv_height);//设置左侧布局

        m_content_view.layout(0, 0, m_content_view.getMeasuredWidth(),
                m_content_view.getMeasuredHeight());//设置右测布局
    }

    /**
     * 这个方法会被dispatchTouchEvent方法调用
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float moveY = ev.getY();

                if (Math.abs(moveX - downX) > Math.abs(moveY - downY)) {
                    // 水平方向移动
                    return true;//直接拦截了，进入到自己的onTouchEvent的方法
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 当ViewGroup中所有子View都不捕获Down事件时，将触发ViewGroup自身的onTouch事件。
     * 触发的方式是调用super.dispatchTouchEvent函数，即父类View的dispatchTouchEvent方法
     * @param event
     * @return
     */
    /*
    * scrollTo():移动屏幕到指定的坐标
    * scrollBy():按一定的距离移动屏幕
    * getX():相对与控件的坐标
    * getRawX():相对于整个屏幕的坐标
    * */
    float downX;
    float downY;
    float moveX;
    float moveY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX=event.getX();
                downY=event.getY();
                System.out.println("按下了："+downX);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX=event.getX();
                moveY=event.getY();
                System.out.println("移动："+moveX);
                int diffX=(int)(downX-moveX+0.5f);//四舍五入(往右滑,屏幕向左移动)
                //int scrollX=getScrollX();
                int scollX=getScrollX()+diffX;//getScrollX():获取屏幕左侧的横坐标
                if(scollX<0&&scollX<-m_left_view.getMeasuredWidth()){
                    scrollTo(-m_left_view.getMeasuredWidth(),0);//屏幕移动，显示出左侧内容
                }else if(scollX>0){
                    scrollTo(0,0);
                }else{
                    scrollBy(diffX,0);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 松开时的逻辑
                // 判断是要去打开，要去关闭
                int width=m_left_view.getMeasuredWidth();
                int currentX=getScrollX();
                float middle=-width/2f;
                switchMenu(currentX<middle);//控制打开或关闭，为true表示要打开
                break;
            default:
                break;
        }
        return true;//返回true,消费事件
    }

    boolean isLeftShow=false;
    public void switchMenu(boolean showLeft){
        isLeftShow=showLeft;
        int width=m_left_view.getMeasuredWidth();
        int currentX=getScrollX();
        if(!showLeft){
            //关闭
            int startX=currentX;
            int startY=0;

            int endX=0;
            int endY=0;

            int dx = endX - startX;// 增量的值
            int dy = endY - startY;

            int duration = Math.abs(dx) * 10;// 时长
            if (duration >= 600) {
                duration = 600;
            }

            // 模拟数据变化
            m_scroller.startScroll(startX, startY, dx, dy, duration);
        }else{
            //打开
            int startX = currentX;
            int startY = 0;

            int endX = -width;
            int endY = 0;

            int dx = endX - startX;// 增量的值
            int dy = endY - startY;

            int duration = Math.abs(dx) * 10;// 时长
            if (duration >= 600) {
                duration = 600;
            }

            // 模拟数据变化
            m_scroller.startScroll(startX, startY, dx, dy, duration);
        }
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (m_scroller.computeScrollOffset()) {
            // 更新位置
            scrollTo(m_scroller.getCurrX(), 0);
            invalidate();
        }
    }
}
