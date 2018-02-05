package com.ucsc.zachary.asg2_connect4;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by zachary on 11/6/17.
 */

public class draw4me extends SurfaceView{

    protected Paint paint = new Paint();
    protected Bitmap bMap;
    protected SurfaceHolder surfHolder;
    protected Bitmap.Config cfg = Bitmap.Config.ARGB_8888;
    protected Canvas board;


    public draw4me(Context context) {
        super(context);
        //DisplayMetrics metrics = new DisplayMetrics();
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bMap = Bitmap.createBitmap(width, height, cfg);
        board = new Canvas(bMap);
        surfHolder = getHolder();
        surfHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas board = holder.lockCanvas();
                onDrawBoard(board);
                surfHolder.unlockCanvasAndPost(board);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    public void onDrawBoard(Canvas canvas){
        paint.setColor(Color.BLUE);

        canvas.drawCircle(20, 20, 10, paint);
    }

    @Override
    public void onDraw(Canvas canvas){

    }

}
