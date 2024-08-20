package com.example.alienshooter;

import android.graphics.Rect;

public class GameEntityCollision {
    public static boolean detect_collision(Rect rect1,Rect rect2)
    {
        if (rect1.intersect(rect2)) {
            // 两个 Rect 发生碰撞
            return true;
        } else {
            // 两个 Rect 没有发生碰撞
            return false;
        }
    }
}
