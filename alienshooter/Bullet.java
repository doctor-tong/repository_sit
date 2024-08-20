package com.example.alienshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

public class Bullet extends GameEntity{

    private int m_dead_line = 500;
    private int damage=10;
    private Bitmap m_glowing;
    public Bullet(Context context) {
        super(context);

        m_glowing=BitmapFactory.decodeResource(context.getResources(), R.drawable.glowing);
        //初始化不同状态图片素材
        Bitmap bullet_bitmap_appear = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_appear);
        Bitmap bullet_bitmap_disappear = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_disapear);
        Bitmap bullet_bitmap_exist= BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_appear);
        this.putM_map_bitmap("entity_appear",bullet_bitmap_appear);
        this.putM_map_bitmap("entity_disappear",bullet_bitmap_disappear);
        this.putM_map_bitmap("entity_exist",bullet_bitmap_exist);

        //初始化不同gap
        this.putM_map_width_gap("entity_appear",context.getResources().getInteger(R.integer.bullut_appear_gap));
        this.putM_map_width_gap("entity_disappear",context.getResources().getInteger(R.integer.bullet_disapear_gap));
        this.putM_map_width_gap("entity_exist",context.getResources().getInteger(R.integer.bullut_appear_gap));

        //初始化不同fram
        this.putM_map_frame("entity_appear",context.getResources().getInteger(R.integer.bullet_appear_frame));
        this.putM_map_frame("entity_disappear",context.getResources().getInteger(R.integer.bullet_disappear_frame));
        this.putM_map_frame("entity_exist",context.getResources().getInteger(R.integer.bullet_appear_frame));

        setM_height_ratio(0.1F);
        setM_width_ratio(0.2F);
    }

    public int getM_dead_line() {
        return m_dead_line;
    }


    public int getDamage() {
        return damage;
    }


    public void setDamage(int damage) {
        this.damage = damage;
    }


    public void setM_dead_line(int m_dead_line) {
        this.m_dead_line = m_dead_line;
    }

    public void resetDrawParameter()
    {
        if (m_dead_line<=0)
        {//当子弹到达极限距离
            if (getM_speedx()!=0) setM_speedx(0);//速度归零
            if (this.getM_state() == State.EntityState.APPEAR)
            {//直接隐藏
                this.setEntityTargetState(State.EntityState.HIDE);//转换成隐藏状态
                setEntityState();
            }
        }
        else if (isM_collisional())
        {
            if (getM_speedx()!=0) setM_speedx(0);//速度归零
            if (this.getM_state() == State.EntityState.APPEAR) {//转换成消失动画
                setEntityTargetState(State.EntityState.DISAPPEAR);
                setEntityState();
            }
            else if (this.getM_state() == State.EntityState.DISAPPEAR) {//如果是消失动画
                if (getM_index() == getM_frames())//对应动画播放完毕
                {
                    this.setEntityTargetState(State.EntityState.HIDE);//转换成隐藏状态
                    setEntityState();
                }
                else    setM_index((getM_index() % getM_frames()) + getM_frames_state());//设置角色下一图像
            }

            else    setM_index((getM_index() % getM_frames()) + getM_frames_state());//设置角色下一图像

        } else if (getM_index()<getM_frames()) setM_index((getM_index()) + getM_frames_state());//设置角色下一图像
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.getM_state()!=State.EntityState.HIDE)
        {

            m_sourse_rect = new Rect((getM_width() / getM_frames()) * (getM_index() - 1) + getM_width_gap(), 0, (getM_width() / getM_frames()) * getM_index() - getM_width_gap(), getM_height());
            m_target_rect = new Rect(getM_x(), getM_y(), (int) (getM_x() +  ((float) getM_width() / getM_frames() - 2 * getM_width_gap())*getM_width_ratio()), (int) (getM_y() + getM_height()*getM_height_ratio()));
            setM_collison_rect(m_target_rect);
//           m_target_rect = new Rect(getM_x(), getM_y(),  getM_x() +  ( getM_width() / getM_frames() - 2 * getM_width_gap()),  getM_y() + getM_height());
            canvas.save();//保存画板状态
            if (getM_towards() < 0)
            {
                canvas.scale(-1, 1);
                canvas.translate((float) -(2 * getM_x() + ((float)getM_width() / getM_frames() - 2 * getM_width_gap())*getM_width_ratio()), 0);//将原点左移
            }
            if (this.getM_state() == State.EntityState.APPEAR&&getM_index()==1)
            {
                Paint glowingPaint = new Paint();
                glowingPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
                Rect m_sourse_rect_2 = new Rect(0, 0, m_glowing.getWidth() ,m_glowing.getHeight());
                Rect m_target_rect_2 = new Rect(getM_x()-3*m_glowing.getWidth()/5+m_glowing.getWidth()/8, getM_y()-3*m_glowing.getHeight()/5,  getM_x()+3*m_glowing.getWidth()/5+m_glowing.getWidth()/8 ,  getM_y()+3*m_glowing.getHeight()/5 );
                canvas.drawBitmap(m_glowing, m_sourse_rect_2, m_target_rect_2, glowingPaint);
            }
            canvas.drawBitmap(getM_image(), m_sourse_rect, m_target_rect, null);
            canvas.restore();//恢复画板状态
            resetDrawParameter();//根据情况调整子弹状态
        }
    }

    @Override
    public void detectDrawWay() {//射出的子弹在APPEAR无静止动画所以重构动画播放方式
        switch (this.getM_state()) {
            case APPEAR:
            case EXIST:
                if (getM_speedx() > 0)//前移动动画
                {
                    setTowards(1);
                    setM_frames_state(1);
                } else if (getM_speedx() < 0)//后移动动画
                {
                    setTowards(-1);
                    setM_frames_state(1);
                } else setM_frames_state(1);//静止动画
                break;
            case DISAPPEAR:
                setM_frames_state(1);
                break;
            default:
                setM_frames_state(0);//静止动画
        }
    }
}
