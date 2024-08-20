package com.example.alienshooter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GameNpc extends GameEntity {
    private State.NPCState m_npc_target_state;
    private State.NPCState m_npc_state;


    public GameNpc(Context context) {
        super(context);
        m_npc_state=State.NPCState.HIDE;
        m_npc_target_state=State.NPCState.HIDE;
        getM_map_bitmap().clear();
        getM_map_bitmap().put("npc_walk", null);
        getM_map_bitmap().put("npc_jump", null);
        getM_map_bitmap().put("npc_attack", null);
        getM_map_bitmap().put("npc_dead", null);
        getM_map_bitmap().put("npc_hide", null);

        getM_map_frame().clear();
        getM_map_frame().put("npc_walk", null);
        getM_map_frame().put("npc_jump", null);
        getM_map_frame().put("npc_attack", null);
        getM_map_frame().put("npc_dead", null);
        getM_map_frame().put("npc_hide", 1);

        getM_map_width_gap().clear();
        getM_map_width_gap().put("npc_walk", null);
        getM_map_width_gap().put("npc_jump", null);
        getM_map_width_gap().put("npc_attack", null);
        getM_map_width_gap().put("npc_dead", null);
        getM_map_width_gap().put("npc_hide", 1);
    }

    public State.NPCState getM_npc_target_state() {
        return m_npc_target_state;
    }

    public State.NPCState getM_npc_state() {
        return m_npc_state;
    }

    public void setNPCState() {
        if (getM_npc_target_state() != getM_npc_state()) {
            State.NPCState temp_state = this.getM_npc_state();
            int temp_height = getM_height();
            int temp_width = getM_width();
            int temp_frames = getM_frames();
            int temp_gap = getM_width_gap();
            switch (getM_npc_target_state()) {
                case STAND:
                    setM_npc_state(State.NPCState.STAND);
                    reset_npc_state_parameter("npc_walk");
                    break;
                case WALK:
                    setM_npc_state(State.NPCState.WALK);
                    reset_npc_state_parameter("npc_walk");
                    break;
                case JUMP:
                    setM_npc_state(State.NPCState.JUMP);
                    reset_npc_state_parameter("npc_jump");
                    break;
                case ATTACK:
                    setM_npc_state(State.NPCState.ATTACK);
                    reset_npc_state_parameter("npc_attack");
                    break;
                case DEAD:
                    setM_npc_state(State.NPCState.DEAD);
                    reset_npc_state_parameter("npc_dead");
                    break;
                case HIDE:
                    setM_npc_state(State.NPCState.HIDE);
                    reset_npc_state_parameter("npc_hide");
                    setM_speedx(0);
                    setM_speedy(0);
                    setM_collison_rect(new Rect(0,0,0,0));
                    this.setM_x(0);
                    this.setM_y(0);
                    return;
            }
            if (getM_height_ratio() != 1.0f)
                setM_y_in_world(getM_y() - (getM_height() - temp_height));//参照图片下部
            else
                setM_y_in_world(getM_y() - (int) ((getM_height() - temp_height) * getM_height_ratio()));//参照图片下部
            if (getM_width_ratio() != 1.0f)
                setM_x_in_world(getM_x() - (((getM_width() / getM_frames() - 2 * getM_width_gap()) - (temp_width / temp_frames - 2 * temp_gap)) / 2));//参照人物中部
            else
                setM_x_in_world(getM_x() - (int) ((((getM_width() / getM_frames() - 2 * getM_width_gap()) - (temp_width / temp_frames - 2 * temp_gap)) / 2) * getM_width_ratio()));//参照人物中部

        }
        detectNPCDrawWay();
    }

    public void detectNPCDrawWay() {//通过状态调整绘制方式

        switch (this.getM_npc_state()) {
            case STAND:
            case WALK:
                if (getM_speedx() > 0)//前移动动画
                {
                    setTowards(1);
                    setM_frames_state(1);
                } else if (getM_speedx() < 0)//后移动动画
                {
                    setTowards(-1);
                    setM_frames_state(1);
                } else setM_frames_state(0);//静止动画
                break;
            case JUMP:
                if (getM_speedx() > 0)//前移动动画
                    setTowards(1);
                else if (getM_speedx() < 0)//后移动动画
                    setTowards(-1);
                setM_frames_state(0);
                break;
            case DEAD:
            case ATTACK:
                setM_frames_state(1);
                break;
            case HIDE:
                setM_frames_state(0);
                break;
            default:
                setM_frames_state(0);//静止动画
        }
    }

    public void setM_npc_target_state(State.NPCState m_npc_target_state) {
        this.m_npc_target_state = m_npc_target_state;
    }

    public void setM_npc_state(State.NPCState m_npc_state) {
        this.m_npc_state = m_npc_state;
    }

    public void reset_npc_state_parameter(String key) {
        if (getM_map_bitmap().containsKey(key) && getM_map_frame().containsKey(key))
            initM_image(getM_map_bitmap().get(key), getM_map_frame().get(key));
        if (getM_map_width_gap().containsKey(key))
            setM_width_gap(getM_map_width_gap().get(key));
        setM_index(1);
        if (getM_image() != null) {
            setM_height(getM_image().getHeight());
            setM_width(getM_image().getWidth());
        } else {
            setM_height(0);
            setM_width(0);
        }
    }

    public void resetDrawParameter()
    {
        if (getM_HP()<=0)
        {//当角色生命值归零
            if (getM_speedx()!=0) setM_speedx(0);//速度归零
            if (this.m_npc_state != State.NPCState.DEAD)
            {
                this.setM_npc_target_state(State.NPCState.DEAD);//转换成死亡状态
                setNPCState();
            }
            else if (this.getM_index()==getM_frames())
            {
                this.setM_npc_target_state(State.NPCState.HIDE);//转换成隐藏状态
                setNPCState();
            }
            else setM_index((getM_index() % getM_frames()) + 1);//设置角色死亡动画下一图像
        }
        setM_index((getM_index() % getM_frames()) + getM_frames_state());//设置角色下一图像
    }



    @Override
    public void draw(Canvas canvas) {
        if (m_npc_state != State.NPCState.HIDE) {
            m_sourse_rect = new Rect((getM_width() / getM_frames()) * (getM_index() - 1) + getM_width_gap(), 0, (getM_width() / getM_frames()) * getM_index() - getM_width_gap(), getM_height());
            m_target_rect = new Rect(getM_x(), getM_y(), getM_x() + getM_width() / getM_frames() - 2 * getM_width_gap(), getM_y() + getM_height());
            setM_collison_rect(m_target_rect);
            canvas.save();//保存画板状态
            if (getM_towards() < 0) {
                canvas.scale(-1, 1);
                canvas.translate((float) -(2 * getM_x() + getM_width() / getM_frames() - 2 * getM_width_gap()), 0);//将原点左移
            }
            canvas.drawBitmap(getM_image(), m_sourse_rect, m_target_rect, null);
            canvas.restore();//恢复画板状态

            resetDrawParameter();
        }

    }


}
