package com.example.kiboooo.userdefinedview.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kiboooo on 2017/11/13.
 *   自定义实现轮播图；
 */

public class ImageBarnnerViewGroup extends ViewGroup  {

    private static int ChildrenCount; //ViewGroup中所有的子视图个数
    private static int Childrenheight;//子视图的高
    private static int Childrenwidth;//子视图的宽

    private static Scroller scroller  ;//获取Scroller类

    private int first_x;//代表第一次按下时的X坐标；以及每一次移动过程中，移动之前的位置横坐标
    private static int index = 0;//代表图片的索引值；

    //Timer是一种定时器工具，用来在一个后台线程计划执行指定任务。它可以计划执行一个任务一次或反复多次。
    private Timer timer = new Timer();

    private TimerTask task; //TimerTask一个抽象类，它的子类代表一个可以被Timer计划的任务。
    private boolean isAuto = true; //默认情况下开启轮播
    private boolean isClick ;


    /**
     * 实现点击事件的获取：
     * 利用判断用户手指抬起的一瞬间，判断当前用户是点击操作还是移动操作；
     * 然后改变相应的状态信号量，实现点击事件获取；
     */

    private ImageBarnnerListener listener;

    public ImageBarnnerListener getListener() {
        return listener;
    }

    public void setListener(ImageBarnnerListener listener) {
        this.listener = listener;
    }

    public interface ImageBarnnerListener{
        void ClickListener(int index);//index 代表了当前点击的这张照片
    }

    private ImageBarnnerViewGroupLinsnner imageBarnnerViewGroupLinsnner;

    public ImageBarnnerViewGroupLinsnner getImageBarnnerViewGroupLinsnner() {
        return imageBarnnerViewGroupLinsnner;
    }

    public void setImageBarnnerViewGroupLinsnner(ImageBarnnerViewGroupLinsnner imageBarnnerViewGroupLinsnner) {
        this.imageBarnnerViewGroupLinsnner = imageBarnnerViewGroupLinsnner;
    }

    /**
     * 利用FrameLayout布局，加入覆盖在Image上面的
     */

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://进行自动轮播
                    if (++index >= ChildrenCount) {
                        index = 0;
                    }
                    scrollTo(index * Childrenwidth, 0);
                    imageBarnnerViewGroupLinsnner.selectImage(index);

                    break;
                case 1:

                    break;
            }
        }
    };



    //改变信号量的操作

    private void startAuto(){
        isAuto = true;
    }

    private void stopAuto() {
        isAuto = false;
    }


    public ImageBarnnerViewGroup(Context context) {

        super(context);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs) {

        super(context, attrs);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    private void  initObj(){
        scroller = new Scroller(getContext());

        task = new TimerTask() {
            @Override
            public void run() {
                if (isAuto) {//开启轮播图
                    handler.sendEmptyMessage(0);
                }else
                    handler.sendEmptyMessage(1);
            }
        };

    //定时任务，在任务执行前延时100毫秒才开始执行,每隔3S执行一次任务Task
        timer.schedule(task, 100, 5000);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();//导致ViewGroup重绘
        }
    }

    /*自定义ViewGroup必须要实现的方法有： 测量 - 》布局 - 》绘制；
    * 由于绘制可以直接调用系统的绘制方法即可，不需要特地重写；
    * */


    /**
     * 测量
     *
     * 必须知道该容器中所有子视图；
     * 如果要测量 ViewGroup 的宽度和高度 ， 就必须测量子视图的宽度之和，和高度之和
     * 才能正常的计算出 ViewGroup 的高度和宽度是多少。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //1.求出子视图的个数：
        ChildrenCount = getChildCount();

        if (0 == ChildrenCount) {
            setMeasuredDimension(0, 0);

        }else {
            //2.测量子视图的宽度和高度：
            measureChildren(widthMeasureSpec, heightMeasureSpec);//绘制子视图
            //此时，我们以第一个子视图为基准，也就是说ViewGroup的高已经确定，
            // 就是已知子视图的高，而ViewGroup的宽就是 子视图的宽度 *  子视图的个数；
            View view  = getChildAt(0);
            Childrenwidth = view.getMeasuredWidth();
            Childrenheight = view.getMeasuredHeight();

            int width = view.getMeasuredWidth() * ChildrenCount;//该宽度是所有子视图的总和，也就是ViewGroup的宽度

            setMeasuredDimension(width, Childrenheight);

        }

    }

    /**
     * 布局：
     *
     * 继承ViewGroup 必须要实现布局的onLayout方法
     * @param changed 判断ViewGroup的位置有没有改变，位置改变就为true 否则为 false
     * @param l left
     * @param t top
     * @param r right
     * @param b bottom
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int leftMargin = 0;
            for (int i = 0; i < ChildrenCount; i++) {
                View view = getChildAt(i);

                view.layout(leftMargin, 0, leftMargin + Childrenwidth, Childrenheight);
                //top:在根ViewGroup布局中的Top距离自然是0，因为不改变viewChildren的上下位置；
                //right : 自然是left+right
                //bottom： Top+Childrenheight = Childrenheight；

                leftMargin += Childrenwidth;
            }
        }
    }

    /**
     * 事件的传递过程中的调用方法：我们需要调用容器的拦截方法： onInterceptTouchEvent
     *
     * 返回true：  ViewGroup就会处理此次拦截事件；--》处理该事件的方法是: onTouchEvent
     * 返回false： ViewGroup将不会处理此次事件，并且该事件将继续向下传递该事件；
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }


    /**
     * 常用2种方式实现 轮播图的手动轮播
     * 1.利用scrollTo，scrollBy 配合实现轮播图的手动轮播
     * 2.利用 scroller 对象 实现；
     *
     *  （1）：滑动屏幕图片的过程中，就是自定义的ViewGroup的子视图的移动过程，那么需要知道滑动之前的
     *  横坐标和滑动之后的横坐标，求出滑动的距离，利用 scrollBy 方法实现图片的滑动；
     *
     *  （2）： 求出按下那一瞬间的那个点的横坐标值作为我们的滑动前的横坐标初始值；
     *
     *  （3）：在不断的滑动过程中，系统不断的调用ACTION_MOVE 方法，我们需要将滑动之前的点 和 滑动之后的点
     *  进行保存；
     *
     *  （4）： 在用户手指抬起的一瞬间，需要计算出已经滑动到哪张图片的位置，
     *  需要求出滑动到的图片的索引值：
     * （ 我们当前ViewGroup的滑动位置 + 我们每一张图片宽度 / 2 ）/ 每张图片的宽度值 。
     * 再调用 scrollTo 方法 滑动到该图片上；
     *
     * @param event
     * @return
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://表示用户点击屏幕 按下的一瞬间
                stopAuto();

                //判断当前图片的滑动是否完成
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();//未完成图片的滑动就立即结束滑动

                }
                isClick = true;
                first_x = (int) event.getX();
                Log.e("ACTION_DOWN", "" + first_x);
                break;

            case MotionEvent.ACTION_MOVE://表示  按下后在屏幕上移动的过程
                int MoveX = (int) event.getX();
                int Distance = MoveX - first_x;//移动前后横坐标的变化范围

                scrollBy(-Distance, 0);
                //scrollBy：在当前视图内容继续偏移(x , y)个单位，
                // 显示(可视)区域也跟着偏移(x,y)个单位，
                // 由于 当手指向右（→）滑动的时候，MoveX > first_x
                // 需要显示该图片前面的内容才能造成图片往后退的效果；
                //所以需要进行偏移量取反的操作；左划（←）同理

                first_x = MoveX;//把当前的移动结束的坐标作为下一次滑动前的横坐标；
                if (Distance!=0) isClick = false;
                Log.e("ACTION_MOVE", "" + Distance);
                break;
            case MotionEvent.ACTION_UP://表示  用户从屏幕抬起的一瞬间

                int ScrollX = getScrollX();//获取：该视图内容相当于视图起始坐标的偏移量

                index = (ScrollX + Childrenwidth / 2) / Childrenwidth;

                if (index < 0) {//表明已经滑动到最左端
                    index = 0;
                } else if (index > ChildrenCount - 1) {//表明已经滑动到最右端
                    index = ChildrenCount - 1;
                }

                if (isClick) {
                    listener.ClickListener(index);
                }else
                {
                    int dx = index * Childrenwidth - ScrollX;

                    scroller.startScroll(ScrollX,0,dx,0);
                    postInvalidate();
                    imageBarnnerViewGroupLinsnner.selectImage(index);

//                scrollTo(index * Childrenwidth, 0);

                    Log.e("ACTION_UP", "" + index);
                }
                startAuto();
                break;

            default:
                break;
        }
        return true;//返回true 告诉该ViewGroup的父View  我们已经处理完了该事件；

    }

    public interface ImageBarnnerViewGroupLinsnner{
        void selectImage(int pos);
    }


}
