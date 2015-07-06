package com.example.wuxiaoxiao.drawingfun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class DrawingView extends View {

    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private int paintColor = 0xff6600;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpDrawing();
    }

    public void setUpDrawing(){
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);

        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);//paint.dither_flag is a android.graphics
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        //view given size
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);

    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://touch the view
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE://drag the finger
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP://lift the finger
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }

        invalidate();//causes onDraw to execute
        return true;
    }

    public void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);//parseColor turns a string code to color
        drawPaint.setColor(paintColor);
    }

}
