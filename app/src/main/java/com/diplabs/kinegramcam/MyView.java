package com.diplabs.kinegramcam;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class MyView extends View {

    Paint paintTouchPointer;

    private final int initialAnimationSpeed = 1000;
    private int animationSpeed = 1000;
    private int animationSpeedRatio = 1;
    private int phase = 2;
    private int segment = 4;
    private int color_switch = 1;

    public int getAnimationSpeedRatio() {
        return animationSpeedRatio;
    }

    public void toggleColorSwitch() {

        if (color_switch == 1) {
            paintTouchPointer.setColor(Color.WHITE);
        } else {
            paintTouchPointer.setColor(Color.BLACK);
        }

        color_switch = color_switch * -1;
    }

    public void setAnimationSpeed(int animationSpeedRatio) {
        if (animationSpeedRatio == 0) {
            this.animationSpeedRatio = animationSpeedRatio;
            anim.pause();


        } else {
            this.animationSpeedRatio = animationSpeedRatio;
            animationSpeed = initialAnimationSpeed / animationSpeedRatio;
            anim.setDuration(animationSpeed);

            if (anim.isPaused()){
                anim.resume();
            }
        }

    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getSegment() {
        return segment;
    }

    public void setSegment(int segment) {
        this.segment = segment;
    }



    public MyView(Context context) {
        super(context);

        init();
    }


    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    int windowWidth;
    int windowHeight;

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        windowWidth = xNew;
        windowHeight = yNew;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }


    private void init() {


        paintTouchPointer = new Paint();
        paintTouchPointer.setStyle(Paint.Style.FILL);
        startAnimation();

    }

    ValueAnimator anim;
    public void startAnimation(){
        anim= ValueAnimator.ofInt(0,phase*segment);
        anim.setDuration(initialAnimationSpeed);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int animProgress = (Integer) animation.getAnimatedValue();
                startPosition = animProgress;
                invalidate();
            }
        });
        anim.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                // done
            }
        });
        anim.start();
    }






    int startPosition = 0;

    @Override
    protected void onDraw(Canvas canvas) {


//         for (int i = 0; i < 1000; i=i+phase) {
//
////            canvas.drawRect(200, 50, 200 + segment, 300, paintTouchPointer);
//
//                canvas.drawRect(startPosition+ segment*i-500, 0, startPosition + (phase-1)*segment+ segment*i-500, windowHeight, paintTouchPointer);
////            Log.i(TAG, "onDraw: "+(startPosition+ segment*i));
//            }


        for (int i = 0; i < (windowWidth*2.2); i=i+phase) {
//            canvas.drawRect(200, 50, 200 + segment, 300, paintTouchPointer);
            canvas.drawRect(startPosition+ segment*i-(windowWidth/2), 0, startPosition + (phase-1)*segment+ segment*i-(windowWidth/2), windowHeight, paintTouchPointer);
//            Log.i(TAG, "onDraw: "+(startPosition+ segment*i));
        }


}}