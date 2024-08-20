package com.example.alienshooter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

public class GameFrameLayout extends FrameLayout {


    public GameFrameLayout(@NonNull Context context) {
        super(context);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {//设置控件互斥
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                // 获取当前触摸的 View
//                View touchedView = getTouchedView(event.getX(), event.getY());
//                assert touchedView != null;
//                if (touchedView.getTag().equals("buttonRight")  || touchedView.getTag().equals("buttonLeft")) {
//                    // 左右移动  互斥,拦截事件
//                    return true;
//                }
//                break;
//            // 其他事件类型的处理逻辑
//        }
//        // 默认不拦截事件
//        return false;
//    }
//
//    private View getTouchedView(float x, float y) {
//        int childCount = getChildCount();
//        for (int i = childCount - 1; i >= 0; i--) {
//            View child = getChildAt(i);
//            if (isPointInView(child, x, y)) {
//                return child;
//            }
//        }
//        return null;
//    }
//
//    private boolean isPointInView(View view, float x, float y) {
//        int[] location = new int[2];
//        view.getLocationInWindow(location);
//        int left = location[0];
//        int top = location[1];
//        int right = left + view.getWidth();
//        int bottom = top + view.getHeight();
//        return x >= left && x <= right && y >= top && y <= bottom;
//    }
}
