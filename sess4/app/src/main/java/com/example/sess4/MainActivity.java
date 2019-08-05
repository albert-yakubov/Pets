package com.example.sess4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import static com.example.sess4.R.id.buttonDemo;
import static com.example.sess4.R.id.editDemo;

public class MainActivity extends AppCompatActivity {

    EditText edit_Demo;
    Button button_Demo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edit_Demo = findViewById(editDemo);
        button_Demo = findViewById(buttonDemo);
        button_Demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String content = String.valueOf(edit_Demo.getText());
            Log.i("Demo", content);

            int value = Integer.parseInt(content);


            }
        });

    }


}
