package com.example.sess3;

import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button            buttonColor;
    ConstraintLayout  layoutBackground;
    int               colorIndex, numberColors; //there are multiple integers here 


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorIndex = 0;
        numberColors = 6; //number of colors that we have

        layoutBackground = findViewById(R.id.background_layout);
        layoutBackground.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        buttonColor = findViewById(R.id.button_color);
        buttonColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* int targetColor;
                if{((ColorDrawable)layoutBackground.getBackground()).getColor() == getResources().getColor(R.color.colorPrimaryDark)) }
            }else

            {
                targetColor = R.color.colorAccent;
            }
            layoutBackground.setBackgroundColor(getResources().getColor(targetColor));
*/

            int targetColor;
            switch(colorIndex) {
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
                        targetColor = android.R.color.holo_orange_dark;
                    break;
                case 4:
                    targetColor = android.R.color.holo_green_light;
                    break;
                case 5:
                    targetColor = android.R.color.holo_green_dark;
                    break;
                default:
                    targetColor = android.R.color.white;


            }

            layoutBackground.setBackgroundColor(getResources().getColor(targetColor));
            colorIndex = ++colorIndex % numberColors;
            //this is how we loop. so we pre increment ci then modulo the difference



            }
        });


    }
    }
