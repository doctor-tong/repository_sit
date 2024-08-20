package com.example.alienshooter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

public class GameButtonLeft extends GameObject{

    public GameButtonLeft( ) {
        super();
    }

    @Override
    public void draw(Canvas canvas) {
        m_sourse_rect=new Rect((getM_width()/getM_frames())*(getM_index()-1)+getM_width_gap(),0,(getM_width()/getM_frames())*getM_index()-getM_width_gap(),getM_height());
        m_target_rect =new Rect(0,7*canvas.getHeight()/10,canvas.getWidth()/8,14*canvas.getHeight()/15);
        canvas.drawBitmap(getM_image(),m_sourse_rect ,m_target_rect , null);
    }


}
