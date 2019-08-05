package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

class SnakeEngine extends SurfaceView implements Runnable {
    //game thread for the main game loop
    private Thread thread = null;

    //holds the reference to the activity
    Context context;

    //for playing sound effects
    private SoundPool soundPool;
    private int eatBob = -2;
    private int snakeCrash = -1;

    public SnakeEngine(Context context) {
        super(context);
    }

    //for tracking the movement heading
    public enum Heading {
        UP, DOWN, RIGHT, LEFT
    }

    //start by heading to the right
    private Heading heading = Heading.RIGHT;

    //hold the screen size in pixels
    private int screenX;
    private int screenY;

    //how long is the snake
    private int snakeLength;

    //where is bob
    private int bobX;
    private int bobY;

    //size of the snake in pixels
    private int blockSize;

    //the size in pixels of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int numBlocksHigh;

    //control pausing
    private long nextFrameTime;
    //update the game 10 times per secong
    private final long fps = 10;
    //1000 milliseconds in a second
    private final long MILLIS_PER_SECOND = 1000;

    //draw the frame more often

    //how many points does the player have
    private int score;


    //the location of the grid of all segments
    private int[] snakeXs;
    private int[] snakeYs;

    //everything we need for drawing

    //is the game currently open
    private volatile boolean isPlaying;

    //canvas for the paint
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    public SnakeEngine(Context context, Point size) {
        super(context);

        context = context;
        screenX = size.x;
        screenY = size.y;

        //how many pixels is each block
        blockSize = screenX / NUM_BLOCKS_WIDE;
        //how many will fit into the height
        numBlocksHigh = screenY / blockSize;


        //set the sound up
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        eatBob = soundPool.load(context, R.raw.eat_bob, 1);
        snakeCrash = soundPool.load(context, R.raw.snake_crash, 1);


        //initialize the drawing objects
        surfaceHolder = getHolder( );
        paint = new Paint( );

        //at score 200 rewarded a crash achievement
        snakeXs = new int[200];
        snakeYs = new int[200];

        //start the game
        newGame( );
    }




    @Override
    public void run() {
        while (isPlaying) {
            if (updateRequired( )) {
                update( );
                draw( );

            }
        }
    }


    public void pause() {
        isPlaying = false;
        try {
            thread.join( );
        } catch (InterruptedException e) {

        }
    }


public void resume(){
    isPlaying = true;
    thread = new Thread(this);
    thread.start();
}
public void newGame(){
    snakeLength = 1;
    snakeXs[0]=NUM_BLOCKS_WIDE/2;
    snakeYs[0]=numBlocksHigh/2;

    //spawn the bob
    spawnBob();

    //score reset
    score = 0;

    //setup the next frame to trigger the update
    nextFrameTime = System.currentTimeMillis();
}
public void spawnBob(){
    Random random = new Random();
    bobX = random.nextInt(NUM_BLOCKS_WIDE - 1) +1;
    bobY = random.nextInt(numBlocksHigh - 1)+1;
}
//once snake eats bob it in creases in size
private void eatBob(){
    snakeLength++;
    spawnBob();

    score = score + 1;
    soundPool.play(eatBob,1,1,0,0,0);
}
private void moveSnake() {
    for (int i = snakeLength; i > 0; i--) {
        //start at back and move the snake and place the segment in front of it
        snakeXs[i] = snakeXs[i - 1];
        snakeYs[i] = snakeYs[i - 1];
        //exclude the head because the head is not in front of it

    }
    //move the head in appropriate heading
    switch (heading) {
        case UP:
            snakeYs[0]--;
            break;
        case RIGHT:
            snakeXs[0]++;
            break;
        case DOWN:
            snakeYs[0]++;
        case LEFT:
            snakeXs[0]--;
            break;
    }
}
private boolean detectDeath(){
    boolean dead = false;

    //hit the edge of the screen
    if(snakeXs[0] == -1)dead = true;
    if(snakeXs[0] >=NUM_BLOCKS_WIDE)dead = true;
    if(snakeYs[0] == -1)dead = true;
    if(snakeYs[0] >=numBlocksHigh)dead = true;

    //if i run into myself
    for(int i = snakeLength -1; i>0; i--){
        if((i>4)&&(snakeXs[0] == snakeYs[i])&&(snakeYs[0] == snakeYs[i])){
            dead = true;
        }
    }
    return dead;
}
public void update(){
    //has snake eaten the bob
    if(snakeXs[0] == bobX && snakeYs[0]==bobY){
        eatBob();
    }
    moveSnake();
    if(detectDeath()){
        //start again
        soundPool.play(-1,1,1,0,0,1);
        newGame();
    }
}
//drawing the game
    public void draw(){
    if(surfaceHolder.getSurface().isValid()){
        canvas = surfaceHolder.lockCanvas();

        canvas.drawColor(Color.argb(25,26,128,182));

        paint.setColor(Color.argb(255,255,255,255));

        paint.setTextSize(90);
        canvas.drawText("Score"+score,10,70,paint);

        //draw the snake one block at the time
        for(int i = 0; i <snakeLength; i++){
            canvas.drawRect(snakeXs[i] * blockSize,
                    (snakeYs[i] * blockSize),
                    (snakeXs[i] * blockSize) + blockSize,
                    (snakeYs[i] * blockSize) + blockSize,
                    paint);
        }
        //paint the bob red
        paint.setColor(Color.argb(255,255,0,0));

        //draw bob
        canvas.drawRect(bobX*blockSize,
                (bobY*blockSize),
                (bobX*blockSize)+blockSize,
                (bobY*blockSize)+blockSize, paint);

    //unlock the canvas for the frame
        surfaceHolder.unlockCanvasAndPost(canvas);

    }
    }
    public boolean updateRequired(){
        if (nextFrameTime<=System.currentTimeMillis()){
            nextFrameTime=System.currentTimeMillis()+MILLIS_PER_SECOND/fps;

            //return true so game is executed
            return true;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction( ) & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX( ) >= screenX / 2) {
                    switch (heading) {
                        case UP:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.UP;
                            break;
                    }
                } else {
                    switch (heading) {
                        case UP:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.UP;
                            break;
                    }
                }
        }
        return true;
    }
}


