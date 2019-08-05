package com.example.parallaks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class ParallaxView extends SurfaceView implements Runnable{

    //this is to initialize the array list for our backgrounds
    ArrayList<Background> backgrounds;



    private volatile boolean running;
    private Thread gameThread = null;

    //for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    //holds the reference the activity
    Context context;

    //controls the fps
    long fps = 60;

    //screen resolution
    int screenWidth;
    int screenHeight;

    ParallaxView(Context context, int screenWidth, int screenHeight){
        super(context);

        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        //initialize the paint
        ourHolder = getHolder();
        paint = new Paint();

        //initialize the array list
        backgrounds = new ArrayList<>();

        //load the background data into the Background objects and place them in Game objects array list
        backgrounds.add(new Background(
                this.context,
                screenWidth,
                screenHeight,
                "skyline",0,80,50));
        backgrounds.add(new Background(
                this.context,
                screenWidth,
                screenHeight,
                "grass",70,110,200));

        //you can keep going with background images
    }

    @Override
    public void run(){
        while (running){
            long startFrameTime = System.currentTimeMillis();

            update();

            draw();

            //calculating the fps in this frame
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if(timeThisFrame >= 1){
                fps = 1000 / timeThisFrame;
            }
        }
    }
    private void update(){
        //update all background positions
        for(Background bg : backgrounds){
            bg.update(fps);
        }
    }
    private void draw(){
        if(ourHolder.getSurface().isValid()){
            //first lock the area to be drawn on
            canvas = ourHolder.lockCanvas();

            //draw background color
            canvas.drawColor(Color.argb(255,255,255,255));
            canvas.drawColor(Color.argb(255,0,3,70));

            //draw the background Parallax
            drawBackground(0);

            //drawing the rest of the game
            paint.setTextSize(60);
            paint.setColor(Color.argb(255,255,255,255));
            canvas.drawText("I am a plane", 350, screenHeight/100*5,paint);
            paint.setTextSize(220);
            canvas.drawText("I am a train",50,screenHeight/100*80,paint);

            drawBackground(1);
            //draw the foreground for parallax

            //unlock and draw the scene

            //unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
    private void drawBackground(int position){
        //make a copy of relevant background
        Background bg = backgrounds.get(position);
        //define what portion of the image to capture and draw to the screen
        //for regular bitmap:
        Rect fromRect1 = new Rect(0,0,bg.width - bg.xClip, bg.height);
        Rect toRect1 = new Rect(bg.xClip,bg.startY, bg.width,bg.endY);

        //for reversed backgrounds:
        Rect fromRect2 = new Rect(bg.width - bg.xClip,0,bg.width,bg.height);
        Rect toRect2 = new Rect(0,bg.startY,bg.xClip,bg.endY);

        //drawing the two backgrounds
        if(!bg.reversedFirst){
            canvas.drawBitmap(bg.bitmap,fromRect1,toRect1,paint);
            canvas.drawBitmap(bg.bitmapReversed,fromRect1,toRect1,paint);
        }else{
            canvas.drawBitmap(bg.bitmap,fromRect2,toRect2,paint);
            canvas.drawBitmap(bg.bitmapReversed,fromRect2,toRect2,paint);
        }
    }
    //reset the threat if the game is paused
    public void pause(){
        running = false;
        try {
            gameThread.join();
        }catch (InterruptedException e){
            //error
        }
    }
    //starts a new threat and the code is executing at run method
    public void resume(){
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}
