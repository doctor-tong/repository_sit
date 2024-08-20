package com.example.alienshooter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

public class GameBackgroud extends GameObject {



//    private int m_x_speed=0;
//    private int m_y_speed=0;
//
//    private boolean m_x_stop;
//    private boolean m_y_stop;

    public GameBackgroud( ) {
        super();
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
//      this.setM_height(this.getM_height()/3);
//      this.setM_width(this.getM_width()/3);
        this.setM_x(0);
        this.setM_y(0);
//        m_x_stop=false;
//        m_y_stop=false;
    }

//    public void setM_x_speed(int m_x_speed) {
//        this.m_x_speed = m_x_speed;
//    }
//
//    public void setM_y_speed(int m_y_speed) {
//        this.m_y_speed = m_y_speed;
//    }
//
//    public void setM_x_stop(boolean m_x_stop) {
//        this.m_x_stop = m_x_stop;
//    }
//
//    public void setM_y_stop(boolean m_y_stop) {
//        this.m_y_stop = m_y_stop;
//    }
//
//    public int getM_y_speed() {
//        return m_y_speed;
//    }
//
//    public int getM_x_speed() {
//        return m_x_speed;
//    }
//
//    public boolean isM_x_stop() {
//        return m_x_stop;
//    }
//
//    public boolean isM_y_stop() {
//        return m_y_stop;
//    }


    @Override
    public void draw(Canvas canvas) {


        m_sourse_rect = new Rect(0, 0,getM_width()  , getM_height());
        m_target_rect = new Rect(0, 0,getM_width()  , getM_height());
//        canvas.scale(-1,1);
//        canvas.translate((float) -(m_width /m_frames+m_x), m_y-m_height);

        canvas.drawBitmap(this.getM_image(), m_sourse_rect, m_target_rect, null);
    }
}
