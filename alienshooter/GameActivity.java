package com.example.alienshooter;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {


    private GameView game_veiw;//游戏画面
    private GameButtonLeft buttonLeft;//向左移动
    private GameButtonRight buttonRight;//向右移动
    private GameButtonShoot buttonShoot;
    private GameFrameLayout rootView;//根视图
    private GameButtonJump  buttonJump;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //创建根视图
        rootView = new GameFrameLayout(this);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

         game_veiw =new GameView(this);
        //设置tag
        game_veiw.setTag("game_view");

        //将子视图加入父视图中
        rootView.addView(game_veiw);//0

        // 设置视图层级
        game_veiw.bringToFront();

        setContentView(rootView);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //绘制控件
        buttonLeft = new GameButtonLeft();
        buttonRight = new GameButtonRight();
        buttonJump =new GameButtonJump();
        buttonShoot=new GameButtonShoot();

         //添加重要实体
        GamePlayer player =new GamePlayer(this);


        Bitmap player_bitmap_walk = BitmapFactory.decodeResource(getResources(), R.drawable.player_walk);

        Bitmap gamebuttonleft_bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.left);
        Bitmap gamebuttonright_bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.right);
        Bitmap gamebuttonjump_bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.jump);
        Bitmap gamebuttonshoot_bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.shoot);//


        player.initM_image(player_bitmap_walk,10);//添加图片资源
        player.setM_index(1);//设置图片数量



        buttonLeft.initM_image(gamebuttonleft_bitmap,1);
        buttonLeft.setM_index(1);

        buttonRight.initM_image(gamebuttonright_bitmap,1);
        buttonRight.setM_index(1);

        buttonJump.initM_image(gamebuttonjump_bitmap,1);
        buttonJump.setM_index(1);

        buttonShoot.initM_image(gamebuttonshoot_bitmap,1);
        buttonShoot.setM_index(1);


        game_veiw.setM_player(player);
        game_veiw.setM_buttonLeft(buttonLeft);
        game_veiw.setM_buttonRight(buttonRight);
        game_veiw.setM_buttonJump(buttonJump);
        game_veiw.setM_buttonShoot(buttonShoot);


    }


}