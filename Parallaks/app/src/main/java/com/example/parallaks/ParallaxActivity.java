package com.example.parallaks;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class ParallaxActivity extends Activity {


    //our object to handle the view
    private ParallaxView parallaxView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        //Get a Display objects to access the screen details
        Display display = getWindowManager().getDefaultDisplay();

        //load the resolution into the Point object
        Point resolution = new Point();
        display.getSize(resolution);

        //set the view to the game
        parallaxView = new ParallaxView(this, resolution.x, resolution.y);

        //make our view to the view of the activity
        setContentView(parallaxView);

    }
    @Override
    protected void onPause(){
        super.onPause();
        parallaxView.pause();
    }
    @Override
    protected void onResume(){
        super.onResume();
        parallaxView.resume();

    }
}
