package com.example.canvas_sample.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.canvas_sample.R;
import com.example.canvas_sample.model.KeyPiano;
import com.example.canvas_sample.utils.SoundManager;

import java.util.ArrayList;

public class PianoView extends View {

    public static final int NUMBER_KEYS = 14;
    private ArrayList<KeyPiano> whites;
    private ArrayList<KeyPiano> blacks;
    private ArrayList<KeyPiano> fakeWhites;
    Paint white, black,grey;
    private SoundManager soundManager;

    private int keyWidth, keyHeight;

    public PianoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        whites = new ArrayList<KeyPiano>();
        blacks = new ArrayList<KeyPiano>();
        fakeWhites = new ArrayList<KeyPiano>();

        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);

        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        grey = new Paint();
        grey.setColor(Color.GRAY);
        grey.setStyle(Paint.Style.FILL);

        soundManager = SoundManager.getInstance();
        soundManager.init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        keyWidth = w / NUMBER_KEYS;
        keyHeight = h;

        int blackCount = 15;
        //Tao mang phim trang
        for(int i=0; i<NUMBER_KEYS; i++){
            int left = i * keyWidth;
            int right = left + keyWidth;
            RectF rectF = new RectF(left,0.6f*h,right,h);
            whites.add(new KeyPiano(i+1,rectF,false));

            //Tao mang phim den
            if(i!=0 && i!=3 && i!=7 && i!=10){
                rectF = new RectF((float)(i)*keyWidth+0.25f*keyWidth,0,(i+1)*keyWidth,0.6f*h);
                fakeWhites.add(new KeyPiano(i+1,rectF,false));
                rectF = new RectF((float)(i-1)*keyWidth + 0.75f*keyWidth,0,(float) i*keyWidth+0.25f*keyWidth,0.6f*keyHeight);
                blacks.add(new KeyPiano(blackCount,rectF,false));
                blackCount++;
            }
            else {
                rectF = new RectF((float)(i)*keyWidth,0,(i+1)*keyWidth-0.25f*keyWidth,0.6f*h);
                fakeWhites.add(new KeyPiano(i+1,rectF,false));
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(KeyPiano k:fakeWhites){
            canvas.drawRect(k.rect,k.down ? grey : white);
        }

        for(KeyPiano k:whites){
            canvas.drawRect(k.rect,k.down ? grey : white);
        }
        //Tao duong vien cho phim trang
        for(int i = 1; i<NUMBER_KEYS; i++){
            canvas.drawLine(i*keyWidth,0,i*keyWidth,keyHeight,black);
        }

        for(KeyPiano k:blacks){
            canvas.drawRect(k.rect,k.down? grey : black);
        }
    }
    private static boolean callClick = false;
    private static float x = 0;
    private static float z = 0;
    private static float y = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean isDownAction = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN;
        for (int touchIndex = 0; touchIndex<event.getPointerCount();touchIndex++){
            x = event.getX(touchIndex);
            y = event.getY(touchIndex);
                for (KeyPiano k : whites) {
                    changeXtoZ();
                    for(KeyPiano h:fakeWhites) if(h.rect.contains(z,y-0.6f*keyHeight)) h.down = isDownAction;
                    if (k.rect.contains(z, y)) {
                        k.down = isDownAction;
                            if (!callClick) {
                                if (0 <= x && x <= 100) soundManager.playSound(R.raw.c3);
                                else if (x <= 205) soundManager.playSound(R.raw.d3);
                                else if (x <= 310) soundManager.playSound(R.raw.e3);
                                else if (x <= 415) soundManager.playSound(R.raw.f3);
                                else if (x <= 520) soundManager.playSound(R.raw.g3);
                                else if (x <= 625) soundManager.playSound(R.raw.a3);
                                else if (x <= 730) soundManager.playSound(R.raw.b3);
                                else if (x <= 835) soundManager.playSound(R.raw.c4);
                                else if (x <= 940) soundManager.playSound(R.raw.d4);
                                else if (x <= 1045) soundManager.playSound(R.raw.e4);
                                else if (x <= 1150) soundManager.playSound(R.raw.f4);
                                else if (x <= 1255) soundManager.playSound(R.raw.g4);
                                else if (x <= 1360) soundManager.playSound(R.raw.a4);
                                else if (x <= 1465) soundManager.playSound(R.raw.b4);
                            }

                        callClick = !callClick;
                    }
                }
                for (KeyPiano k : blacks) {
                    if (k.rect.contains(x, y)) {
                        k.down = isDownAction;
                        if (!callClick) {
                            if (75 <= x && x <= 125) soundManager.playSound(R.raw.db3);
                            else if (x <= 230) soundManager.playSound(R.raw.eb3);
                            else if (x <= 440) soundManager.playSound(R.raw.gb3);
                            else if (x <= 545) soundManager.playSound(R.raw.ab3);
                            else if (x <= 650) soundManager.playSound(R.raw.bb3);
                            else if (x <= 860) soundManager.playSound(R.raw.db4);
                            else if (x <= 965) soundManager.playSound(R.raw.eb4);
                            else if (x <= 1175) soundManager.playSound(R.raw.gb4);
                            else if (x <= 1280) soundManager.playSound(R.raw.ab4);
                            else if (x <= 1385) soundManager.playSound(R.raw.bb4);
                        }

                        callClick = !callClick;
                    }
                }
        }
        invalidate();
        return true;
    }
    private void changeXtoZ(){
        if (0 <= x && x <= 100) z=50;
        else if (x <= 205) z=155;
        else if (x <= 310) z=260;
        else if (x <= 415) z=365;
        else if (x <= 520) z=470;
        else if (x <= 625) z=575;
        else if (x <= 730) z=680;
        else if (x <= 835) z=785;
        else if (x <= 940) z=890;
        else if (x <= 1045) z=995;
        else if (x <= 1150) z=1100;
        else if (x <= 1255) z=1205;
        else if (x <= 1360) z=1310;
        else if (x <= 1465) z=1415;
    }
}
