package com.example.alienshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GamePlayer extends GameObject {

    private Bullet m_bullet[];
    private Context m_context;
    private int m_HP = 100;
    private int m_speedx = 0;
    private int m_speedy = 0;
    private int m_dx = 0;
    private int m_dy = 0;
    private int m_interval_time = 1;
    private int m_gravity = 0;
    private int m_bias_x=0;
    private Map<String, Bitmap> m_map_bitmap = new HashMap<>();
    {
        m_map_bitmap.put("player_walk", null);
        m_map_bitmap.put("player_jump", null);
        m_map_bitmap.put("player_shoot", null);
    }
    private Map<String, Integer> m_map_frame = new HashMap<>();
    {
        m_map_frame.put("player_walk", 1);
        m_map_frame.put("player_jump", 1);
        m_map_frame.put("player_shoot", 1);
    }
    private Map<String,Integer> m_map_width_gap =new HashMap<>();
    {
        m_map_width_gap.put("player_walk", 1);
        m_map_width_gap.put("player_jump", 1);
        m_map_width_gap.put("player_shoot", 1);
    }
    private CharacterState m_character_target_state;
    private CharacterState m_character_state;

    public enum CharacterState {
        STAND,
        WALK,
        JUMP,
        SHOOT
    }

    public GamePlayer(Context context) {
        super();//初始化角色素材
        m_context = context;
        m_character_state = CharacterState.STAND;
        m_character_target_state=CharacterState.STAND;
        setM_width_gap(m_context.getResources().getInteger(R.integer.player_walk_gap));
        m_bullet=new Bullet[10];
        for (int i = 0; i < m_bullet.length; i++)
        {
            m_bullet[i] = new Bullet(context); // 为每个元素分配新的Bullet对象
        }

        Bitmap player_bitmap_walk = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_walk);
        Bitmap player_bitmap_jump = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_jump);
        Bitmap player_bitmap_shoot= BitmapFactory.decodeResource(context.getResources(), R.drawable.player_shoot);
        put_m_map_bitmap("player_walk",player_bitmap_walk);
        put_m_map_bitmap("player_jump",player_bitmap_jump);
        put_m_map_bitmap("player_shoot",player_bitmap_shoot);

        put_m_map_frame("player_walk",context.getResources().getInteger(R.integer.player_walk_frame));
        put_m_map_frame("player_jump",context.getResources().getInteger(R.integer.player_jump_frame));
        put_m_map_frame("player_shoot",context.getResources().getInteger(R.integer.player_shoot_frame));

        put_m_map_width_gap("player_walk",context.getResources().getInteger(R.integer.player_walk_gap));
        put_m_map_width_gap("player_jump",context.getResources().getInteger(R.integer.player_jump_gap));
        put_m_map_width_gap("player_shoot",context.getResources().getInteger(R.integer.player_shoot_gap));

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

    public CharacterState getM_character_state() {
        return m_character_state;
    }

    public int get_gravity() {
        return m_gravity;
    }

    public CharacterState getM_character_target_state() {
        return m_character_target_state;
    }

    public Bullet[] getM_bullet() {
        return m_bullet;
    }

    public void put_m_map_bitmap(String key,Bitmap player_bitmap)
    {
        m_map_bitmap.put(key,player_bitmap);
    }
    public void put_m_map_frame(String key,int player_frame)
    {
        m_map_frame.put(key,player_frame);
    }
    public void put_m_map_width_gap(String key,int player_width_gap)
    {
        m_map_width_gap.put(key,player_width_gap);
    }

    public void setM_bias_x(int m_bias_x) {
        this.m_bias_x = m_bias_x;
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

    public void setM_map_bitmap(Map<String, Bitmap> m_map_bitmap) {
        this.m_map_bitmap = m_map_bitmap;
    }

    public void setM_gravity(int m_gravity) {
        this.m_gravity = m_gravity;
    }

    public void setTargetCharacterState(CharacterState state) {
            m_character_target_state=state;
    }
    public void setCharacterState() {
        if (m_character_target_state != m_character_state) {
            CharacterState temp_state=getM_character_state();
            int temp_height = getM_height();
            int temp_width = getM_width();
            int temp_frames = getM_frames();
            int temp_gap = getM_width_gap();
            int temp_toward=getM_towards();
            switch (m_character_target_state) {
                case STAND:
                    m_character_state=CharacterState.STAND;
                    reset_parameter("player_walk");
                    break;
                case WALK:
                    m_character_state = CharacterState.WALK;
                    reset_parameter("player_walk");
                    break;
                case JUMP:
                    m_character_state = CharacterState.JUMP;
                    reset_parameter("player_jump");
                    break;
                case SHOOT:
                    m_character_state = CharacterState.SHOOT;
                    reset_parameter("player_shoot");
                    break;
            }
            detectDrawWay();
            setM_y(getM_y() - (getM_height() - temp_height));//参照图片下部
            if (m_character_state!=CharacterState.SHOOT) {
                m_bias_x = 0;
            }
            else if (getM_towards()>0) {
                m_bias_x = 0;

            }
            else if (getM_towards()<0) {
                m_bias_x = ((getM_width() / getM_frames() - 2 * getM_width_gap())) ;
            }
        }

    }

    @Override
    public void setM_world_height(int m_world_height) {
        super.setM_world_height(m_world_height);
        for (int i = 0; i < m_bullet.length; i++)
        {
            m_bullet[i].setM_world_height(m_world_height); // 为每个的Bullet对象分配
        }
    }

    @Override
    public void setM_world_width(int m_world_width) {
        super.setM_world_width(m_world_width);
        for (int i = 0; i < m_bullet.length; i++)
        {
            m_bullet[i].setM_world_width(m_world_width); // 为每个的Bullet对象分配
        }
    }

    @Override
    public boolean setM_y(int m_y) {
        if (m_y < 0) {
            super.setM_y(0);
            return false;

        } else if (getM_world_height() != 0 && m_y > getM_world_height() - getM_height()) {
            super.setM_y(getM_world_height() - getM_height());
            return false;

        } else super.setM_y(m_y);
        return true;
    }

    @Override
    public boolean setM_x(int m_x) {

        if (m_x < 0) {
            super.setM_x(0);
            setM_speedx(0);
            return false;

        } else if (getM_world_width() != 0 && m_x > getM_world_width() - getM_width() / getM_frames() + 2 * getM_width_gap()) {
            super.setM_x(getM_world_width() - getM_width() / getM_frames() + 2 * getM_width_gap());
            setM_speedx(0);
            return false;
        } else super.setM_x(m_x);
        return true;

    }



    public boolean calculate_x() {
        m_dx = m_speedx * m_interval_time;
        if (setM_x(getM_x() + m_dx)) return true;
        else {
            setM_dx(0);
            return false;
        }
    }

    public boolean calculate_y() {
        m_dy = (int) (m_speedy * m_interval_time + 0.5 * m_gravity * Math.pow(m_interval_time, 2));
        m_speedy = m_speedy + m_gravity * m_interval_time;
        if (setM_y(getM_y() + m_dy)) return true;
        else {
            setM_dy(0);
            return false;
        }
    }

    @Override
    public void draw(Canvas canvas) {

        m_sourse_rect = new Rect((getM_width() / getM_frames()) * (getM_index() - 1) + getM_width_gap(), 0, (getM_width() / getM_frames()) * getM_index() - getM_width_gap(), getM_height());
        m_target_rect = new Rect(getM_x()+m_bias_x, getM_y(), getM_x()+m_bias_x + getM_width() / getM_frames() - 2 * getM_width_gap(), getM_y() + getM_height());

        canvas.save();//保存画板状态
        if (getM_towards() < 0) {
            canvas.scale(-1, 1);
            if (m_character_state!=CharacterState.SHOOT)
                canvas.translate((float) -(2 * (getM_x()+m_bias_x) + getM_width() / getM_frames() - 2 * getM_width_gap()), 0);//将原点左移
            else  canvas.translate((float) -( 2 * (getM_x()+m_bias_x) + getM_width() / getM_frames()/2 - 2*  getM_width_gap()), 0);//将原点左移
        }
        canvas.drawBitmap(getM_image(), m_sourse_rect, m_target_rect, null);
        canvas.restore();//恢复画板状态

        setM_index((getM_index() % getM_frames()) + getM_frames_state());//设置角色下一图像
        DrawBullet(canvas);
    }

    public void detectDrawWay() {
        switch (m_character_state) {
            case STAND:
                setM_frames_state(0);
                break;
            case WALK:
                if (m_speedx > 0)//前移动动画
                {
                    setTowards(1);
                    setM_frames_state(1);
                } else if (m_speedx < 0)//后移动动画
                {
                    setTowards(-1);
                    setM_frames_state(1);
                }
                else  setM_frames_state(0);//静止动画
                break;
            case JUMP:
                if (m_speedx > 0)//前移动动画
                    setTowards(1);
                else if (m_speedx < 0)//后移动动画
                    setTowards(-1);
                setM_frames_state(0);
                break;
            case SHOOT:
                setM_frames_state(1);
                break;
            default:
                setM_frames_state(0);//静止动画
        }
    }

    public void DrawBullet(Canvas canvas)
    {
        // 创建 Random 对象
        Random random = new Random();

            if (this.m_character_state == CharacterState.SHOOT) {//射击状态时搜寻一发空闲子弹射出
                for (int i = 0; i < m_bullet.length; i++) {
                    if (m_bullet[i].getM_state()== State.EntityState.HIDE) {//搜寻到空闲子弹
                        m_bullet[i].setM_speedx((100+random.nextInt(20) + 1) * getM_towards());
                        m_bullet[i].setM_dead_line(800);
                        m_bullet[i].setM_collisional(false);
                        if (getM_towards() > 0)
                            m_bullet[i].setM_x(getM_x()+m_bias_x + getM_width() / getM_frames() - 2 * getM_width_gap()+30);//将参考坐标设为角色坐标
                        else
                            m_bullet[i].setM_x(getM_x()+m_bias_x-((getM_width() / getM_frames() - 2 * getM_width_gap())));//将参考坐标设为角色坐标

                        m_bullet[i].setM_y(getM_y() + 5 * getM_height() / 6);

                        m_bullet[i].setM_width(getM_width());//将参考矩形设为角色的矩形参数矩形
                        m_bullet[i].setM_height(getM_height());
                        m_bullet[i].setM_width_gap(getM_width_gap());
                        m_bullet[i].setM_frames(getM_frames());

                        m_bullet[i].setEntityTargetState(State.EntityState.APPEAR);
                        m_bullet[i].setEntityState();
                        break;//退出循环
                    }
                }
            }
        for (int i=0;i<m_bullet.length;i++) {//处理所有子弹绘制
            if (m_bullet[i].getM_state() != State.EntityState.HIDE) {
                m_bullet[i].draw(canvas);
                if (!m_bullet[i].calculate_x())//计算位置
                    m_bullet[i].setM_collisional(true);//撞到墙壁
                m_bullet[i].setM_dead_line(m_bullet[i].getM_dead_line() - Math.abs(m_bullet[i].getM_dx()));//计算剩余射程
            }
        }
    }

    public void reset_parameter(String key)
    {
        if (m_map_bitmap.containsKey(key)&&m_map_frame.containsKey(key))
            initM_image(m_map_bitmap.get(key), m_map_frame.get(key));
        if (m_map_width_gap.containsKey(key))
            setM_width_gap(m_map_width_gap.get(key));
        setM_index(1);
        setM_height(getM_image().getHeight());
        setM_width(getM_image().getWidth());
    }

}


