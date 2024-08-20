package com.example.alienshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.HashMap;
import java.util.Map;

public abstract class GameEntity extends GameObject {

    private Context m_context;
    private int m_HP = 100;
    private int m_speedx = 0;
    private int m_speedy = 0;
    private int m_dx = 0;
    private int m_dy = 0;
    private int m_interval_time = 1;
    private int m_gravity = 0;
    private boolean m_collisional=false;

    private Rect m_collison_rect;

    private Map<String, Bitmap> m_map_bitmap = new HashMap<>();
    {
        m_map_bitmap.put("entity_appear",null);
        m_map_bitmap.put("entity_disappear",null);
        m_map_bitmap.put("entity_exist",null);
        m_map_bitmap.put("entity_hide",null);
    }

    private Map<String, Integer> m_map_frame = new HashMap<>();
    {
        m_map_frame.put("entity_appear", 1);
        m_map_frame.put("entity_exist", 1);
        m_map_frame.put("entity_disappear", 1);
        m_map_frame.put("entity_hide", 1);
    }
    private Map<String, Integer> m_map_width_gap = new HashMap<>();
    {
        m_map_width_gap.put("entity_appear", 1);
        m_map_width_gap.put("entity_exist", 1);
        m_map_width_gap.put("entity_disappear", 1);
        m_map_width_gap.put("entity_hide", 1);
    }
    private State.EntityState m_target_state;
    private State.EntityState m_state;


    public GameEntity(Context context) {
        super();//初始化角色素材
        m_context = context;
        m_state = State.EntityState.HIDE;
        m_target_state = State.EntityState.HIDE;
        setEntityState();
        m_collison_rect=new Rect(getM_x(), getM_y(), getM_x() + getM_width() / getM_frames() - 2 * getM_width_gap(), getM_y() + getM_height());
    }

    public Rect getM_collison_rect() {
        return m_collison_rect;
    }

    public int getInterval_time() {
        return m_interval_time;
    }

    public int getM_speedx() {
        return m_speedx;
    }

    public int getM_speedy() {
        return m_speedy;
    }

    public int getM_dx() {
        return m_dx;
    }

    public int getM_dy() {
        return m_dy;
    }

    public State.EntityState getM_state() {
        return m_state;
    }

    public int get_gravity() {
        return m_gravity;
    }

    public State.EntityState getM_target_state() {
        return m_target_state;
    }

    public Map<String, Bitmap> getM_map_bitmap() {
        return m_map_bitmap;
    }

    public Map<String, Integer> getM_map_frame() {
        return m_map_frame;
    }

    public Map<String, Integer> getM_map_width_gap() {
        return m_map_width_gap;
    }

    public int getM_HP() {
        return m_HP;
    }


    public void setM_collison_rect(Rect m_collison_rect) {
        this.m_collison_rect = m_collison_rect;
    }

    public void setInterval_time(int interval_time) {
        this.m_interval_time = interval_time;
    }

    public void setM_speedx(int m_speedx) {
        this.m_speedx = m_speedx;
    }

    public void setM_speedy(int m_speedy) {
        this.m_speedy = m_speedy;
    }

    public void setM_dx(int m_dx) {
        this.m_dx = m_dx;
    }

    public void setM_dy(int m_dy) {
        this.m_dy = m_dy;
    }

    public void setM_gravity(int m_gravity) {
        this.m_gravity = m_gravity;
    }

    public void setEntityTargetState(State.EntityState state) {
        m_target_state = state;
    }

    public void setM_collisional(boolean m_collisional) {
        this.m_collisional = m_collisional;
    }

    public boolean isM_collisional() {
        return m_collisional;
    }

    public void setM_HP(int m_HP) {
        this.m_HP = m_HP;
    }

    public void setM_state(State.EntityState m_state) {
        this.m_state = m_state;
    }

    public void setEntityState() {
        if (m_target_state != m_state) {
            State.EntityState temp_state=this.getM_state();
            int temp_height = getM_height();
            int temp_width = getM_width();
            int temp_frames = getM_frames();
            int temp_gap = getM_width_gap();
            switch (m_target_state) {
                case APPEAR:
                    m_state = State.EntityState.APPEAR;
                    reset_state_parameter("entity_appear");
                    break;
                case EXIST:
                    m_state = State.EntityState.EXIST;
                    reset_state_parameter("entity_exit");
                    break;
                case DISAPPEAR:
                    m_state = State.EntityState.DISAPPEAR;
                    reset_state_parameter("entity_disappear");
                    break;
                case HIDE:
                    m_state = State.EntityState.HIDE;
                    reset_state_parameter("entity_hide");
                    setM_speedx(0);
                    setM_speedy(0);
                    this.setM_x_in_world(0);
                    this.setM_y_in_world(0);
                    return;
            }
            if (getM_height_ratio()!=1.0f)
                setM_y_in_world(getM_y() - (getM_height() - temp_height));//参照图片下部
            else setM_y_in_world(getM_y() - (int) ((getM_height() - temp_height)*getM_height_ratio()));//参照图片下部
            if (getM_width_ratio()!=1.0f)
                setM_x_in_world(getM_x() -  (((getM_width() / getM_frames() - 2 * getM_width_gap()) - (temp_width / temp_frames - 2 * temp_gap)) / 2));//参照人物中部
            else     setM_x_in_world(getM_x() - (int) ((((getM_width() / getM_frames() - 2 * getM_width_gap()) - (temp_width / temp_frames - 2 * temp_gap)) / 2)*getM_width_ratio()));//参照人物中部

        }
        detectDrawWay();
    }

    public void putM_map_bitmap(String key,Bitmap entity_bitmap) {
        if (m_map_bitmap.containsKey(key))
        {
            m_map_bitmap.put(key,entity_bitmap);
        }
    }

    public void putM_map_width_gap(String key,int entity_gap) {
        if (m_map_width_gap.containsKey(key))
        {
            m_map_width_gap.put(key,entity_gap);
        }
    }

    public void putM_map_frame(String key,int entity_frame) {
        if (m_map_frame.containsKey(key))
        {
            m_map_frame.put(key,entity_frame);
        }
    }

    public boolean setM_y_in_world(int m_y) {//边界条件也要false
        if (m_y <= 0) {
            super.setM_y(m_y);
            return false;

        } else if (getM_world_height() != 0 && m_y >= getM_world_height() - (int) (getM_height()*getM_height_ratio())) {
            super.setM_y(m_y);
            return false;

        } else super.setM_y(m_y);
        return true;
    }

    public boolean setM_x_in_world(int m_x) {

        if (m_x+(int) ((getM_width()/getM_frames()-2*getM_width_gap())*(1.0F-getM_width_ratio())) <= 0) {
            super.setM_x(-(int) ((getM_width()/getM_frames()-2*getM_width_gap())*(1.0F-getM_width_ratio())));
            setM_speedx(0);
            return false;

        } else if (getM_world_width() != 0 && m_x >= getM_world_width() - (int)((getM_width() / getM_frames() - 2 * getM_width_gap())*getM_width_ratio())) {
            super.setM_x(getM_world_width() - (int)((getM_width() / getM_frames() - 2 * getM_width_gap())*getM_width_ratio()));
            setM_speedx(0);
            return false;
        } else super.setM_x(m_x);
        return true;

    }

    public boolean calculate_x() {
        m_dx = m_speedx * m_interval_time;
        if (setM_x_in_world(getM_x() + m_dx)) return true;
        else {
            setM_dx(0);
            return false;
        }
    }

    public boolean calculate_y() {
        m_dy = (int) (m_speedy * m_interval_time + 0.5 * m_gravity * Math.pow(m_interval_time, 2));
        m_speedy = m_speedy + m_gravity * m_interval_time;
        if (setM_y_in_world(getM_y() + m_dy)) return true;
        else {
            setM_dy(0);
            return false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        m_collison_rect=m_target_rect;
        m_sourse_rect = new Rect((getM_width() / getM_frames()) * (getM_index() - 1) + getM_width_gap(), 0, (getM_width() / getM_frames()) * getM_index() - getM_width_gap(), getM_height());
        m_target_rect = new Rect(getM_x(), getM_y(), getM_x() + getM_width() / getM_frames() - 2 * getM_width_gap(), getM_y() + getM_height());

        canvas.save();//保存画板状态
        if (getM_towards() < 0) {
            canvas.scale(-1, 1);
            canvas.translate((float) -(2 * getM_x() + getM_width() / getM_frames() - 2 * getM_width_gap()), 0);//将原点左移
        }
        canvas.drawBitmap(getM_image(), m_sourse_rect, m_target_rect, null);
        canvas.restore();//恢复画板状态

        setM_index((getM_index() % getM_frames()) + getM_frames_state());//设置角色下一图像
    }

    public void detectDrawWay() {
        switch (m_state) {
            case APPEAR:
            case EXIST:
                if (m_speedx > 0)//前移动动画
                {
                    setTowards(1);
                    setM_frames_state(1);
                } else if (m_speedx < 0)//后移动动画
                {
                    setTowards(-1);
                    setM_frames_state(1);
                } else setM_frames_state(0);//静止动画
                break;
            case DISAPPEAR:
                setM_frames_state(1);
                break;
            default:
                setM_frames_state(0);//静止动画
        }
    }

    public void reset_state_parameter(String key)
    {
        if (m_map_bitmap.containsKey(key)&&m_map_frame.containsKey(key))
            initM_image(m_map_bitmap.get(key), m_map_frame.get(key));
        if (m_map_width_gap.containsKey(key))
            setM_width_gap(m_map_width_gap.get(key));
        setM_index(1);
        if (getM_image()!=null) {
            setM_height(getM_image().getHeight());
            setM_width(getM_image().getWidth());
        }
        else
        {
            setM_height(0);
            setM_width(0);
        }
    }
}

