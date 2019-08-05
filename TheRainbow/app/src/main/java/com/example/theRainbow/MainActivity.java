package com.example.theRainbow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    public Button button1;
    public LinearLayout linear1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;
    public Button button6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        initializeLogic();
    }

    public void initialize() {

        linear1 = findViewById(R.id.linear1);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setBackgroundColor(0xFFF44336);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setBackgroundColor(0xFF2196f3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setBackgroundColor(0xFF000000);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setBackgroundColor(0xFF4CAF50);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setBackgroundColor(0xFFFFEB3B);

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setBackgroundColor(0xFFFFFFFF);

            }
        });


            }
        });
            }
        });
            }
        });

            }
        });
            }
        });
    }

    public void initializeLogic() {
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode,_resultCode,_data);

        switch (_requestCode) {

        default:
        break;
    }
}}