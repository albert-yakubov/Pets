 package com.example.conditionalsandloops;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

 public class MainActivity extends AppCompatActivity {

    Button buttonColor;
    ConstraintLayout backgroundLayout;
    int colorIndex, numberColors;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorIndex = 0;
        numberColors = 6;

        backgroundLayout = findViewById(R.id.background_layout);
        backgroundLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        buttonColor = findViewById(R.id.button_color);
        buttonColor.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                int targetColor;
                switch(colorIndex){
                    case 0:
                        targetColor = android.R.color.holo_blue_bright;
                        break;
                    case 1:
                        targetColor = android.R.color.holo_blue_light;
                        break;
                    case 2:
                        targetColor = android.R.color.holo_blue_dark;
                        break;
                    case 3:
                        targetColor = android.R.color.holo_green_dark;
                        break;
                    case 4:
                        targetColor = android.R.color.holo_green_light;
                        break;
                    case 5:
                        targetColor = android.R.color.holo_red_light;
                        break;
                    default:
                        targetColor = android.R.color.white;
                }
            backgroundLayout.setBackgroundColor(getResources().getColor(targetColor));
                colorIndex = ++colorIndex % numberColors;




            }
        });
    }
}
