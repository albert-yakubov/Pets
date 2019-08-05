package com.example.snake;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class SnakeActivity extends Activity {

    SnakeEngine snakeEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get pixel dimensions of the screen
        Display display = getWindowManager( ).getDefaultDisplay( );

        //initialize the result into a point
        Point size = new Point( );
        display.getSize(size);

        //create an instance of the snake engine class
        snakeEngine = new SnakeEngine(this, size);

        // now make it a view of the activity
        setContentView(snakeEngine);
    }



    @Override
    protected void onResume() {
        super.onResume( );
        snakeEngine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause( );
        snakeEngine.pause();
    }
}


