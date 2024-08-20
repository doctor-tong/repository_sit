package com.example.alienshooter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;


public class GameView extends View {
    ;
    private Paint m_paint = new Paint();
    GameBackgroud m_background;
    GamePlayer m_player;
    GameNpc m_goblin;
    private int m_world_width;
    private int m_world_height;
    private int m_dx = 0;
    private int m_dy = 0;
    private Canvas m_canvas;
    private GameButtonRight m_buttonRight;
    private GameButtonLeft m_buttonLeft;
    private GameButtonJump m_buttonJump;
    private GameButtonShoot m_buttonShoot;
    private int m_interval_time = 1;
    private int Gravity = 10;
    private Ground m_ground;
    private InputManager inputManager;
    private int m_canvas_x=0;
    private int m_canvas_y=0;



    public GameView(Context context) {
        super(context);
        m_interval_time = getResources().getInteger(R.integer.time_interval);
        inputManager = new InputManager();

        setM_background(new GameBackgroud());
        setM_ground(new Ground());
        setM_goblin(new GameNpc(context));

    }

    public void setM_player(GamePlayer m_player) {
        this.m_player = m_player;
        m_player.setM_world_width(m_world_width);
        m_player.setM_world_height(m_world_height);
        m_player.setInterval_time(m_interval_time);
        m_player.setM_y(m_world_height - m_player.getM_height());

    }

    public void setM_goblin(GameNpc m_goblin) {
        this.m_goblin = m_goblin;

        Bitmap goblin_bitmap_walk = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.goblin_walk);
//        Bitmap goblin_bitmap_jump = BitmapFactory.decodeResource();
        Bitmap goblin_bitmap_dead = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.goblin_dead);
        m_goblin.putM_map_bitmap("npc_walk",goblin_bitmap_walk);
//        put_m_map_bitmap("player_jump",player_bitmap_jump);
        m_goblin.putM_map_bitmap("npc_dead", goblin_bitmap_dead);

        m_goblin.putM_map_frame("npc_walk",getContext().getResources().getInteger(R.integer.goblin_walk_frame));
//        put_m_map_frame("player_jump",context.getResources().getInteger(R.integer.player_jump_frame));
        m_goblin.putM_map_frame("npc_dead",getContext().getResources().getInteger(R.integer.goblin_dead_frame));

        m_goblin.putM_map_width_gap("npc_walk",getContext().getResources().getInteger(R.integer.goblin_walk_gap));
//        put_m_map_width_gap("player_jump",context.getResources().getInteger(R.integer.player_jump_gap));
        m_goblin.putM_map_width_gap("npc_dead",getContext().getResources().getInteger(R.integer.goblin_dead_gap));

        m_goblin.setM_npc_target_state(State.NPCState.STAND);
        m_goblin.setNPCState();
        m_goblin.setTowards(-1);


        m_goblin.setM_world_width(m_world_width);
        m_goblin.setM_world_height(m_world_height);
        m_goblin.setInterval_time(m_interval_time);
        m_goblin.setM_y(m_world_height - m_goblin.getM_height());
        m_goblin.setM_x(m_world_width/2);

    }


    public void setM_background(GameBackgroud background) {
        this.m_background = background;
        Bitmap background_bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.background);
        m_background.initM_image(background_bitmap,1);
        m_background.setM_index(1);
        m_world_width = m_background.getM_width();
        m_world_height = m_background.getM_height();
    }


    public void setM_ground(Ground m_ground) {
        this.m_ground = m_ground;
        Bitmap ground_bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        this.m_ground.initM_image(ground_bitmap,1);
        this.m_ground.setM_index(1);
        this.m_ground.setM_world_width(m_world_width);
        this.m_ground.setM_world_height(m_world_height);
    }


    public void setM_buttonRight(GameButtonRight m_buttonRight) {
        this.m_buttonRight = m_buttonRight;
    }

    public void setM_buttonLeft(GameButtonLeft m_buttonLeft) {
        this.m_buttonLeft = m_buttonLeft;
    }

    public void setM_buttonJump(GameButtonJump m_buttonJump) {
        this.m_buttonJump = m_buttonJump;
    }

    public void setM_buttonShoot(GameButtonShoot m_buttonShoot) {
        this.m_buttonShoot = m_buttonShoot;
    }

    public void setM_world_width(int m_world_width) {
        this.m_world_width = m_world_width;
    }

    public void setM_world_height(int m_world_height) {
        this.m_world_height = m_world_height;
    }

    public void setM_canvas_x(int m_canvas_x) {
        this.m_canvas_x = m_canvas_x;
    }

    public void setM_canvas_y(int m_canvas_y) {
        this.m_canvas_y = m_canvas_y;
    }

    public int getM_canvas_x() {
        return m_canvas_x;
    }

    public int getM_canvas_y() {
        return m_canvas_y;
    }

    public void trans_canvas(Canvas canvas) {

        if (m_canvas_x==0) m_canvas_x = m_player.getM_x() - (canvas.getWidth() / 2 - (m_player.getM_width() / m_player.getM_frames()) / 2 + m_player.getM_width_gap());
        if (m_canvas_y==0) m_canvas_y = m_player.getM_y() + m_player.getM_height() - canvas.getHeight() / 2;

        m_canvas_x = m_player.getM_x() - (canvas.getWidth() / 2 - (m_player.getM_width() / m_player.getM_frames()) / 2 + m_player.getM_width_gap());
        m_canvas_y = m_player.getM_y() + m_player.getM_height() - canvas.getHeight() / 2;
        //确保实际绘制在设定范围内
        if (m_canvas_x < 0) m_canvas_x = 0;
        else if (m_canvas_x > m_world_width - canvas.getWidth()) m_canvas_x = m_world_width - canvas.getWidth();
        if (m_canvas_y < 0) m_canvas_y = 0;
        else if (m_canvas_y > m_world_height - canvas.getHeight()+(canvas.getHeight() / 4)) m_canvas_y = m_world_height - canvas.getHeight()+(canvas.getHeight() / 4);
        else if (m_canvas_y + canvas.getHeight() < m_player.getM_y() + m_player.getM_height()) {
            m_canvas_y = m_player.getM_y() + m_player.getM_height() - canvas.getHeight();
        }

        canvas.translate(-(float) m_canvas_x, -(float) m_canvas_y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制背景
        m_paint.setAntiAlias(true);//抗锯齿
        m_paint.setColor(Color.RED);//画笔颜色
        m_paint.setStyle(Paint.Style.STROKE);//描边模式
        m_paint.setStrokeWidth(4f);//设置画笔粗细度
        m_paint.setTextSize(52f);



        canvas.save();
        trans_canvas(canvas);
        m_background.draw(canvas);              //绘制背景
        m_goblin.draw(canvas);
        m_player.draw(canvas);                  //绘制玩家
        m_ground.draw(canvas);
        canvas.restore();


        m_buttonRight.draw(canvas);           //绘制控件
        m_buttonLeft.draw(canvas);
        m_buttonJump.draw(canvas);
        m_buttonShoot.draw(canvas);

        postDelayed(this::updateObject, 50);
    }

    private void updateObject() {//更新实体状态

        interactEntities();//实体交互
        upDatePlayer();//角色状态更新

        invalidate();//重绘
    }


    boolean on_edge(float touchX, float touchY, Rect rect) {
        int x = (int) touchX;
        int y = (int) touchY;
        return (x <= (rect.left + rect.width() / 5) || x >= (rect.right - rect.width() / 5) || y <= (rect.top + rect.height() / 5) || y >= (rect.bottom - rect.height() / 5));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 使用字符串键访问 InputManager 的方法
        int pointerCount = event.getPointerCount();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN://单点按下
                handleTouchDown(event, event.getActionIndex());//获取触发事件的点索引
                return true;
            case MotionEvent.ACTION_POINTER_DOWN://多点中某个点按下
                handleTouchDown(event, event.getActionIndex());
                return true;
            case MotionEvent.ACTION_MOVE:
                handleTouchMove(event, event.getActionIndex());
                // 处理触摸移动事件
                return true;
            case MotionEvent.ACTION_UP:
                handleTouchAllUp();
                return true;
            case MotionEvent.ACTION_POINTER_UP:
                handleTouchUp(event, event.getActionIndex());
                return true;
        }

        if (pointerCount == 0) {
            inputManager.setButtonUp("BUTTON_LEFT");
            inputManager.setButtonUp("BUTTON_RIGHT");
            inputManager.setButtonUp("BUTTON_JUMP");
            inputManager.setButtonUp("BUTTON_SHOOT");
        }
        return true;
    }


    private void handleTouchDown(MotionEvent event, int pointerIndex) {
        if (pointerIndex < 0 || pointerIndex >= event.getPointerCount()) {
            // 触摸点索引无效,直接返回
            return;
        }
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);
        if (m_buttonLeft.m_target_rect.contains((int) x, (int) y)) {
            inputManager.setButtonPressed("BUTTON_LEFT");
        } else if (m_buttonRight.m_target_rect.contains((int) x, (int) y)) {
            inputManager.setButtonPressed("BUTTON_RIGHT");
        } else if (m_buttonJump.m_target_rect.contains((int) x, (int) y)) {
            inputManager.setButtonPressed("BUTTON_JUMP");
        } else if (m_buttonShoot.m_target_rect.contains((int) x, (int) y)) {
            inputManager.setButtonPressed("BUTTON_SHOOT");
        }
    }

    private void handleTouchMove(MotionEvent event, int pointerIndex) {
        if (pointerIndex < 0 || pointerIndex >= event.getPointerCount()) {
            // 触摸点索引无效,直接返回
            return;
        }
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);
        if (m_buttonLeft.m_target_rect.contains((int) x, (int) y)) {
            if (on_edge(x, y, m_buttonLeft.m_target_rect))
                inputManager.setButtonUp("BUTTON_LEFT");
            else inputManager.setButtonTouched("BUTTON_LEFT");
        } else if (m_buttonRight.m_target_rect.contains((int) x, (int) y)) {
            if (on_edge(x, y, m_buttonRight.m_target_rect))
                inputManager.setButtonUp("BUTTON_RIGHT");
            else inputManager.setButtonTouched("BUTTON_RIGHT");
        } else if (m_buttonJump.m_target_rect.contains((int) x, (int) y)) {
            if (on_edge(x, y, m_buttonJump.m_target_rect))
                inputManager.setButtonUp("BUTTON_JUMP");
            else inputManager.setButtonTouched("BUTTON_JUMP");
        } else if (m_buttonShoot.m_target_rect.contains((int) x, (int) y)) {
            if (on_edge(x, y, m_buttonShoot.m_target_rect))
                inputManager.setButtonUp("BUTTON_SHOOT");
            else inputManager.setButtonTouched("BUTTON_SHOOT");
        }
    }

    private void handleTouchUp(MotionEvent event, int pointerIndex) {
        if (pointerIndex < 0 || pointerIndex >= event.getPointerCount()) {
            // 触摸点索引无效,直接返回
            return;
        }
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);
        if (m_buttonLeft.m_target_rect.contains((int) x, (int) y)) {
            inputManager.setButtonUp("BUTTON_LEFT");
        } else if (m_buttonRight.m_target_rect.contains((int) x, (int) y)) {
            inputManager.setButtonUp("BUTTON_RIGHT");
        } else if (m_buttonJump.m_target_rect.contains((int) x, (int) y)) {
            inputManager.setButtonUp("BUTTON_JUMP");
        } else if (m_buttonShoot.m_target_rect.contains((int) x, (int) y)) {
            inputManager.setButtonUp("BUTTON_SHOOT");
        }
    }

    private void handleTouchAllUp() {
        inputManager.setButtonUp("BUTTON_LEFT");
        inputManager.setButtonUp("BUTTON_RIGHT");
        inputManager.setButtonUp("BUTTON_JUMP");
        inputManager.setButtonUp("BUTTON_SHOOT");
    }



    public void upDatePlayer() {
        if (m_ground.onGround(m_player.getM_x(), m_player.getM_y() + m_player.getM_height()))//判断角色是否触地
        {
            if (m_player.getM_speedy() > 0) {//角色下落
                m_player.setM_speedy(0);
                m_player.setM_gravity(0);
                m_player.setM_speedx(0);
                m_player.setTargetCharacterState(GamePlayer.CharacterState.STAND);
                inputManager.setButtonWait("BUTTON_JUMP");
            } else if (inputManager.getButtonState("BUTTON_JUMP") != InputManager.ButtonState.Wait && inputManager.getButtonState("BUTTON_JUMP") != InputManager.ButtonState.FORBID)//角色起跳
            {
                m_player.setM_speedy(-this.getResources().getInteger(R.integer.player_speedy));
                m_player.setM_gravity(10);
                m_player.setTargetCharacterState(GamePlayer.CharacterState.JUMP);
                inputManager.setButtonFORBID("BUTTON_JUMP");
            }
        }
        if ((inputManager.getButtonState("BUTTON_SHOOT") == InputManager.ButtonState.Pressed || inputManager.getButtonState("BUTTON_SHOOT") == InputManager.ButtonState.Touched)) {
            if (m_player.getM_character_state() != GamePlayer.CharacterState.SHOOT) {
                if (m_player.getM_character_state() != GamePlayer.CharacterState.JUMP)
                    m_player.setM_speedx(0);
                m_player.setTargetCharacterState(GamePlayer.CharacterState.SHOOT);
            }
        } else if (inputManager.getButtonState("BUTTON_SHOOT") == InputManager.ButtonState.Up) {
            if (m_player.getM_speedy() == 0)
                m_player.setTargetCharacterState(GamePlayer.CharacterState.STAND);
            else m_player.setTargetCharacterState(GamePlayer.CharacterState.JUMP);
            inputManager.setButtonWait("BUTTON_SHOOT");
        }
        if (m_player.getM_character_target_state() == GamePlayer.CharacterState.JUMP || m_player.getM_character_target_state() == GamePlayer.CharacterState.SHOOT) {
            if (inputManager.getButtonState("BUTTON_RIGHT") == InputManager.ButtonState.Pressed || inputManager.getButtonState("BUTTON_RIGHT") == InputManager.ButtonState.Touched) {
                m_player.setTowards(1);
                m_player.setM_bias_x(0);
//                inputManager.setButtonWait("BUTTON_RIGHT");
            } else if (inputManager.getButtonState("BUTTON_LEFT") == InputManager.ButtonState.Pressed || inputManager.getButtonState("BUTTON_LEFT") == InputManager.ButtonState.Touched) {
                    m_player.setTowards(-1);
                    if (m_player.getM_character_state() == GamePlayer.CharacterState.SHOOT)
                        m_player.setM_bias_x( (m_player.getM_width() / m_player.getM_frames() - 2 * m_player.getM_width_gap()));
                    else  m_player.setM_bias_x(0);
//                inputManager.setButtonWait("BUTTON_LEFT");
            }
        } else {
            if (inputManager.getButtonState("BUTTON_LEFT") == InputManager.ButtonState.Pressed || inputManager.getButtonState("BUTTON_LEFT") == InputManager.ButtonState.Touched) {
                m_player.setM_speedx(-20);
                m_player.setTargetCharacterState(GamePlayer.CharacterState.WALK);
            } else if (inputManager.getButtonState("BUTTON_LEFT") == InputManager.ButtonState.Up) {
                m_player.setM_speedx(0);
                m_player.setTargetCharacterState(GamePlayer.CharacterState.STAND);
                inputManager.setButtonWait("BUTTON_LEFT");
            }
            if (inputManager.getButtonState("BUTTON_RIGHT") == InputManager.ButtonState.Pressed || inputManager.getButtonState("BUTTON_RIGHT") == InputManager.ButtonState.Touched) {
                m_player.setM_speedx(20);
                m_player.setTargetCharacterState(GamePlayer.CharacterState.WALK);
            } else if (inputManager.getButtonState("BUTTON_RIGHT") == InputManager.ButtonState.Up) {
                m_player.setM_speedx(0);
                m_player.setTargetCharacterState(GamePlayer.CharacterState.STAND);
                inputManager.setButtonWait("BUTTON_RIGHT");
            }
        }
        m_player.setCharacterState();//设置角色状态
        //计算并更新角色位置，此时获得实际位移
        m_player.calculate_x();
        m_player.calculate_y();
    }

    private void interactEntities() {
        playAttack();
    }

    private void playAttack() {
        // 创建 Random 对象
        Random random = new Random();
        for (int i=0;i<m_player.getM_bullet().length;i++)
        {
            if(m_player.getM_bullet()[i].getM_state()!= State.EntityState.HIDE) {
                if (GameEntityCollision.detect_collision(m_player.getM_bullet()[i].getM_collison_rect(), m_goblin.getM_collison_rect())) {
                    m_player.getM_bullet()[i].setM_collisional(true);
                    m_player.getM_bullet()[i].setM_x_in_world((int)m_goblin.getM_collison_rect().exactCenterX()+ (-1)*m_player.getM_bullet()[i].getM_towards()*random.nextInt(40)-15);
                    m_goblin.setM_HP(m_goblin.getM_HP()-m_player.getM_bullet()[i].getDamage());
                }

            }

        }
    }

    private void updateEnemy()
    {

    }


}



