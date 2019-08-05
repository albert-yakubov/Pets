package com.example.parallax;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;

public class ParallaxView extends SurfaceView implements Runnable {

    private volatile boolean running;
    private Thread gameThread = null;

    // For drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    // Holds a reference to the Activity
    Context context;

    // Control the fps
    long fps =60;

    // Screen resolution
    int screenWidth;
    int screenHeight;

    ParallaxView(Context context, int screenWidth, int screenHeight) {
        super(context);

        this.context = context;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // Initialize our drawing objects
        ourHolder = getHolder();
        paint = new Paint();

    }

    @Override
    public void run() {

        while (running) {
            long startFrameTime = System.currentTimeMillis();

            update();

            draw();

            // Calculate the fps this frame
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    private void update() {
        ParallaxView(Context context, int screenWidth, int screenHeight) {
            super(context);

            this.context = context;

            this.screenWidth = screenWidth;
            this.screenHeight = screenHeight;

            // Initialize our drawing objects
            ourHolder = getHolder();
            paint = new Paint();

            // Initialize our array list
            backgrounds = new ArrayList<>();

            //load the background data into the Background objects and
            // place them in our GameObject arraylist

            backgrounds.add(new Background(
                    this.context,
                    screenWidth,
                    screenHeight,
                    "skyline",  0, 80, 50));

            backgrounds.add(new Background(
                    this.context,
                    screenWidth,
                    screenHeight,
                    "grass",  70, 110, 200));

            // Add more backgrounds here

        }
        // Update all the background positions

    }

    private void draw() {

        if (ourHolder.getSurface().isValid()) {
            //First we lock the area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();

            //draw a background color
            canvas.drawColor(Color.argb(255, 0, 3, 70));

            // Draw the background parallax

            // Draw the rest of the game
            paint.setTextSize(60);
            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawText("I am a plane", 350, screenHeight / 100 * 5, paint);
            paint.setTextSize(220);
            canvas.drawText("I'm a train", 50, screenHeight / 100*80, paint);

            // Draw the foreground parallax
            ParallaxView(Context context, int screenWidth, int screenHeight) {
                super(context);

                this.context = context;

                this.screenWidth = screenWidth;
                this.screenHeight = screenHeight;

                // Initialize our drawing objects
                ourHolder = getHolder();
                paint = new Paint();

                // Initialize our array list
                backgrounds = new ArrayList<>();

                //load the background data into the Background objects and
                // place them in our GameObject arraylist

                backgrounds.add(new Background(
                        this.context,
                        screenWidth,
                        screenHeight,
                        "skyline",  0, 80, 50));

                backgrounds.add(new Background(
                        this.context,
                        screenWidth,
                        screenHeight,
                        "grass",  70, 110, 200));

                // Add more backgrounds here

            }

            // Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    // Clean up our thread if the game is stopped
    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    // Make a new thread and start it
    // Execution moves to our run method
    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}// End of ParallaxView
