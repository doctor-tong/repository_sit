package com.example.alienshooter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public abstract class GameObject  {

    private int m_x=0;
    private int m_y=0;
    private int m_frames=1;
    private int m_index;

    public int getM_height_gap() {
        return m_height_gap;
    }
    private Bitmap m_image;
    private int m_width=0;
    private int m_height=0;
    private int m_towards = 1;
    protected Rect m_target_rect;
    protected Rect m_sourse_rect;
    private int m_width_gap=0;
    private int m_height_gap=0;
    private int m_frames_state=0;
    private int m_world_width=0;
    private int m_world_height=0;
    private float m_width_ratio=1;
    private float m_height_ratio=1;



    public GameObject() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        this.m_towards =1;
        this.m_x =  0;
        this.m_y =(1/2)*displayMetrics.widthPixels;
    }

    public boolean setM_y(int m_y) {
//        if (m_y<0)
//        {
//            this.m_y = 0;
//            return false;
//        }
//        else if (m_world_height!=0&&m_y>m_world_height-2*m_height_gap) {
//            this.m_y = m_world_height - 2 * m_height_gap;
//            return false;
//        }
        this.m_y = m_y;
        return true;
//        return true;
    }

    public boolean setM_x(int m_x) {
//        if (m_x<0) {
//            this.m_x = 0;
//            return false;
//        }
//        else if (m_world_width!=0&&(m_x>m_world_width-2*m_width_gap-m_width/m_frames)) {
//            this.m_x = m_world_width - 2 * m_width_gap-m_width/m_frames;
//            return false;
//        }
        this.m_x=m_x;
        return true;
//        return true;
    }

    public void setTowards(int towards) {
        m_towards = towards;
    }

    public void initM_image(Bitmap image, int frames) {
        m_image = image;
        m_frames = frames;
        if (m_width==0&&m_height==0)
        {
             m_height = m_image.getHeight();
        }
    }

    public void setM_image(Bitmap image)
    {
        m_image = image;
    }

    public void setM_width(int m_width) {
        this.m_width = m_width;
    }

    public void setM_height(int m_height) {
        this.m_height = m_height;
    }

    public void setM_target_rect(Rect m_target_rect) {
        this.m_target_rect = m_target_rect;
    }

    public void setM_sourse_rect(Rect m_sourse_rect) {
        this.m_sourse_rect = m_sourse_rect;
    }

    public void setM_index(int index) {
        if(index<1) index=1;//确保至少有一幅图像

        this.m_index = index;
    }

    public void setM_width_gap(int m_width_gap) {
        this.m_width_gap = m_width_gap;
    }

//    public void setM_canvas(Canvas m_canvas) {
//        this.m_canvas = m_canvas;
//    }

    public void setM_frames_state(int m_frames_state) {
        this.m_frames_state = m_frames_state;
    }

    public void setM_world_width(int m_world_width) {
        this.m_world_width = m_world_width;
        m_x=0;
    }

    public void setM_world_height(int m_world_height) {
        this.m_world_height = m_world_height;
        m_y=m_world_height-m_height;
    }

    public void setM_frames(int m_frames) {
        this.m_frames = m_frames;
    }

    public void setM_height_ratio(float m_height_ratio) {
        this.m_height_ratio = m_height_ratio;
    }

    public void setM_width_ratio(float m_width_ratio) {
        this.m_width_ratio = m_width_ratio;
    }



    public int getM_towards() {
        return m_towards;
    }

    public int getM_x() {
        return m_x;
    }

    public int getM_y() {
        return m_y;
    }

    public int getM_width() {
        return m_width;
    }

    public int getM_height() {
        return m_height;
    }

    public Rect getM_target_rect() {
        return m_target_rect;
    }

    public Rect getM_sourse_rect() {
        return m_sourse_rect;
    }

    public int getM_frames() {
        return m_frames;
    }

    public int getM_index() {
        return m_index;
    }

    public Bitmap getM_image() {
        return m_image;
    }

    public int getM_width_gap() {
        return m_width_gap;
    }

//    public Canvas getM_canvas() {
//        return m_canvas;
//    }

    public int getM_frames_state() {
        return m_frames_state;
    }

    public int getM_world_width() {
        return m_world_width;
    }

    public int getM_world_height() {
        return m_world_height;
    }

    public float getM_width_ratio() {
        return m_width_ratio;
    }

    public float getM_height_ratio() {
        return m_height_ratio;
    }



    public void draw(Canvas canvas) {

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

    public Bitmap createMirrorBitmap(Bitmap originalBitmap) {
        // 创建一个新的 Matrix 对象
        Matrix matrix = new Matrix();

        // 设置水平镜像（水平翻转）
        matrix.preScale(-1.0f, 1.0f);

        // 创建一个新的 Bitmap，应用 Matrix 变换
        Bitmap mirroredBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, false);

        return mirroredBitmap;
    }
}


