package com.ucsc.zachary.asg2_connect4;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zachary on 11/8/17.
 */

public class CanvasDraw extends View {

    private Paint paint = new Paint();
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Bitmap.Config cfg = Bitmap.Config.ARGB_8888;
    private Bitmap bMap;
    private Canvas board;
    int numColumns;
    int numRows;
    float slotWidth;
    float slotHeight;
    float colBotY;
    float colTopY;
    float rowTopY;
    float rowBotY;

    public CanvasDraw(Context context){
        super(context);
        bMap = makeBitmap();
        paint.setColor(Color.BLACK);
        numColumns = 7;
        numRows = 6;
        //height = ;
        //width = getWidth();
    }

    private void calcDim(){
        //slotWidth = (float) ((width/numColumns)*(0.8));
        //slotHeight = (float) ((height/numRows)*(0.8));

        slotWidth = (float) (width/numColumns);
        slotHeight = slotWidth;
        float remainingSpace = (height-(slotHeight*numColumns));

        colBotY = (height-remainingSpace+slotHeight);
        colTopY = (2*slotHeight);
        rowBotY = (2*slotWidth);
        rowTopY = (2*slotWidth);
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas){
        //canvas.drawColor(Color.BLACK);
        //board = canvas;
        canvas.drawBitmap(bMap, 0, 0, paint);
        calcDim();
        for (int i = 0; i <= numColumns; i++) {
            canvas.drawLine((i * slotWidth), colTopY, i * slotWidth, colBotY, paint);
        }

        for (int i = 0; i <= numRows; i++) {
            canvas.drawLine(0, rowTopY+(i * slotHeight), width, rowBotY+(i * slotHeight), paint);
        }

    }

    public Canvas clearCanvas(){
        Canvas canvas = new Canvas(bMap);
        canvas.drawColor(Color.WHITE);
        return canvas;
    }

    public Bitmap makeBitmap(){
        Bitmap bitmap = Bitmap.createBitmap(width, height, cfg);
        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();


        return true;
    }
}
