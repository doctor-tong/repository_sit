package com.example.alienshooter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class MyView extends View {
    private static final String TAG = "MyView";
    private Paint paint = new Paint();
    private Path path1 = new Path();
    private Path path2 = new Path();
    private Path path3 = new Path();

    public MyView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(Color.RED);//画笔颜色
        paint.setStyle(Paint.Style.STROKE);//描边模式
        paint.setStrokeWidth(4f);//设置画笔粗细度
        paint.setTextSize(52f);
        drawsth(canvas, paint, path1, 0f);
        paint.setStyle(Paint.Style.FILL);//填充模式
        paint.setColor(Color.BLUE);//画笔颜色
        drawsth(canvas, paint, path2, 220f);
        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.STROKE);//描边模式
        drawTextOnCurve(canvas, paint, path3);
    }

    //绘制曲线和曲线上的文字
    private void drawTextOnCurve(Canvas canvas, Paint paint, Path path) {
        path.moveTo(440f, 10f);
        path.lineTo(460f, 80f);
        path.lineTo(490f, 150f);
        path.lineTo(550f, 250f);
        path.lineTo(700f, 400f);
        canvas.drawPath(path, paint);
        canvas.drawTextOnPath("曲线上的文字", path, 10f, 10f, paint);
    }

    //绘制基本图形、Path不规则图形和文字
    private void drawsth(Canvas canvas, Paint paint, Path path, float offset) {

        //paint绘制圆形，参数：圆心坐标（x,y）半径r，画笔paint
        canvas.drawCircle(100f + offset, 100f, 90f, paint);
        //paint绘制矩形,参数：左上角坐标（x,y），右下角坐标（x,y），画笔paint
        canvas.drawRect(10f + offset, 200f, 210f + offset, 400f, paint);
        //paint绘制圆角矩形，参数：左上角坐标（x,y），右下角坐标（x,y），x方向上的圆角半径，y方向上的圆角半径，画笔paint
        canvas.drawRoundRect(10f + offset, 410f, 210f + offset, 610f, 40f, 40f, paint);
        //paint绘制椭圆，参数：左上角坐标（x,y），右下角坐标（x,y）[参数表示矩形内切的椭圆]，画笔paint
        canvas.drawOval(10f + offset, 620f, 210f + offset, 720f, paint);

        //Path绘制三角形,moveto表示第1个坐标，lineto分别表示第2、3个坐标。以此类推。
        path.moveTo(110f + offset, 730f);
        path.lineTo(10f + offset, 930f);
        path.lineTo(210f + offset, 930f);
        path.close();
        canvas.drawPath(path, paint);
        //Path绘制不规则多点多边形
        path.moveTo(10f + offset, 950f);
        path.lineTo(50f + offset, 1000f);
        path.lineTo(200f + offset, 970f);
        path.lineTo(150f + offset, 1070f);
        path.lineTo(10f + offset, 1110f);
        path.lineTo(210f + offset, 1130f);
        path.close();
        canvas.drawPath(path, paint);

        //注意：这个坐标（x,y）并不是文字的左上角，而是一个与左下角比较接近的位置。
        canvas.drawText("测试文字", 10f + offset, 1190f, paint);
    }
}