package com.example.collectionsdemo;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int NUMBER_OF_ELEMENTS = 1024;


    LinearLayout layoutList;

    private Context context;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        layoutList = findViewById(R.id.list_layout);


        int[] array = new int[NUMBER_OF_ELEMENTS];
        for (int i = 0; i < NUMBER_OF_ELEMENTS; ++i)
            array[i] = i;



        for (int num: array) {
            layoutList.addView(defaultTextViewGenerator(Integer.toBinaryString(num)));

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private TextView defaultTextViewGenerator(String displayText){


    /*   <TextView
            android:text="PLACEHOLDER"
            android:textSize="32dp"
            android:padding="22dp"
            android:textAlignment="center"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />
        **/
        TextView view = new TextView(context);
        view.setText(displayText);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        view.setPadding(15, 15, 15,15);
        view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        view.setWidth(2000);
        return view;


        }


    }

