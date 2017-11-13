package com.example.kiboooo.userdefinedview.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kiboooo on 2017/11/13.
 */

public class ImageBarnnerViewGroup extends ViewGroup {

    private int ChildrenCount; //ViewGroup中所有的子视图个数
    private int Childrenheight;//子视图的高
    private int Childrenwidth;//子视图的宽

    public ImageBarnnerViewGroup(Context context) {
        super(context);
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://表示用户点击屏幕 按下的一瞬间

                break;
            case MotionEvent.ACTION_MOVE://表示  按下后在屏幕上移动的过程

                break;
            case MotionEvent.ACTION_UP://表示  用户从屏幕抬起的一瞬间
                break;

            default:
                break;
        }
        return true;//返回true 告诉该ViewGroup的父View  我们已经处理完了该事件；

    }
}
