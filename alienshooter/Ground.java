package com.example.alienshooter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Ground extends GameObject{

    private int m_ground_height;
    private int m_ground_width;


    public Ground() {
        super();
        setM_height(0);
        setM_width(0);
    }

    @Override
    public void setM_world_width(int m_world_width) {
        super.setM_world_width(m_world_width);
        m_ground_width=getM_world_width();
        setM_x(0);
    }

    @Override
    public void setM_world_height(int m_world_height) {
        super.setM_world_height(m_world_height);
        m_ground_height=getM_world_height();
        setM_y(m_world_height);
    }

    public boolean onGround(int x,int y)
    {
       if ((y==getM_y())&&(x>=getM_x())&&(x<=getM_x()+m_ground_width))
           return true;
       return false;
    }

    @Override
    public void draw(Canvas canvas) {

        for (int i = 0; i * getM_width() < getM_world_width(); i++) {
            m_sourse_rect = new Rect((getM_width() / getM_frames()) * (getM_index() - 1) + getM_width_gap(), 0, (getM_width() / getM_frames()) * getM_index() - getM_width_gap(), getM_height());
            m_target_rect = new Rect(getM_x()+(i*getM_width()), getM_y(), getM_x() + getM_width() / getM_frames() - 2 * getM_width_gap()+(i*getM_width()), getM_y() + getM_height());

            canvas.save();//保存画板状态
            if (getM_towards() < 0) {
                canvas.scale(-1, 1);
                canvas.translate((float) -(2 * getM_x() + getM_width() / getM_frames() - 2 * getM_width_gap()), 0);//将原点左移
            }
            canvas.drawBitmap(getM_image(), m_sourse_rect, m_target_rect, null);
            canvas.restore();//恢复画板状态

            setM_index((getM_index() % getM_frames()) + getM_frames_state());//设置角色下一图像
        }
    }
}
