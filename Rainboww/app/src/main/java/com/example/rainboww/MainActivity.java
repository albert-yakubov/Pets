package com.example.rainboww;

import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

    Button buttonBlue;
    Button buttonYellow;
    Button buttonGreen;
    Button buttonPink;
    Button buttonRed;
    Button buttonBlack;
    LinearLayout myBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBlue = findViewById(R.id.blue_Button);
        buttonYellow = findViewById(R.id.yellow_Button);
        buttonGreen = findViewById(R.id.green_Button);
        buttonPink = findViewById(R.id.pink_Button);
        buttonRed = findViewById(R.id.red_Button);
        buttonBlack = findViewById(R.id.black_Button);
        myBackground = findViewById(R.id.my_background);

        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBackground.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
            }
        });
        buttonYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBackground.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
            }
        });
        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBackground.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            }
        });
        buttonPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBackground.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            }
        });
        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBackground.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        });
        buttonBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBackground.setBackgroundColor(getResources().getColor(android.R.color.black));
            }
        });
    }
}
