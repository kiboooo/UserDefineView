package com.example.kiboooo.userdefinedview.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kiboooo on 2017/11/13.
 */

public class ImageBarnnerViewGroup extends ViewGroup {


    public ImageBarnnerViewGroup(Context context) {
        super(context);
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     *  自定义ViewGroup必须要实现的方法有： 测量 - 》布局 - 》绘制；
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 必须知道该容器中所有子视图；
         * 如果要测量 ViewGroup 的宽度和高度 ， 就必须测量子视图的宽度之和，和高度之和
         * 才能正常的计算出 ViewGroup 的高度和宽度是多少。
         */
        //1.求出子视图的个数：
        int ChildrenCount = getChildCount();

        if (0 == ChildrenCount) {
            setMeasuredDimension(0, 0);

        }else {
            //2.测量子视图的宽度和高度：
            measureChildren(widthMeasureSpec, heightMeasureSpec);//绘制子视图
            //此时，我们以第一个子视图为基准，也就是说ViewGroup的高已经确定，
            // 就是已知子视图的高，而ViewGroup的宽就是 子视图的宽度 *  子视图的个数；
            View view  = getChildAt(0);
            int height = view.getMeasuredHeight();
            int width = view.getMeasuredWidth() * ChildrenCount;
            setMeasuredDimension(width, height);

        }

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
