package com.example.kiboooo.userdefinedview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Kiboooo on 2018/1/2.
 */

public class MyScrollView extends ScrollView{
    private float mDownPosX = 0;
    private float mDownPosY = 0;

    int lastX = -1;
    int lastY = -1;


    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//        int dealtX = 0;
//        int dealtY = 0;
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                dealtX = 0;
//                dealtY = 0;
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                dealtX += Math.abs(x - lastX);
//                dealtY += Math.abs(y - lastY);
//
//                if (dealtX >= dealtY) {
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                }else
//                    getParent().requestDisallowInterceptTouchEvent(false);
//
//                lastX = x;
//                lastY = y;
//                break;
//            case  MotionEvent.ACTION_CANCEL:
//                break;
//            case  MotionEvent.ACTION_UP:
//                break;
//        }
//
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownPosX = x;
                mDownPosY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltalX = Math.abs(x - mDownPosX);
                final float deltalY = Math.abs(y - mDownPosY);
                if (deltalX > deltalY) {
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
