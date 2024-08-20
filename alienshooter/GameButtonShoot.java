package com.example.alienshooter;

import android.graphics.Canvas;
import android.graphics.Rect;

public class GameButtonShoot extends GameObject {
    public GameButtonShoot( ) {
        super();
    }

    public void draw(Canvas canvas) {
        m_sourse_rect=new Rect((getM_width()/getM_frames())*(getM_index()-1)+getM_width_gap(),0,(getM_width()/getM_frames())*getM_index()-getM_width_gap(),getM_height());
        m_target_rect =new Rect(10*canvas.getWidth()/16+3*canvas.getWidth()/20,16*canvas.getHeight()/30,13*canvas.getWidth()/16+3*canvas.getWidth()/20,26*canvas.getHeight()/30);
        canvas.drawBitmap(getM_image(),m_sourse_rect ,m_target_rect , null);
    }
}
