package com.example.methods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editDemo;
    Button buttonDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editDemo = findViewById(R.id.edit_demo);
        buttonDemo = findViewById(R.id.button_demo);
        buttonDemo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
               String content = String.valueOf(editDemo.getText());
                Log.i("Demo", content );

                //provide a value to convert a string to an int
                int value = Integer.parseInt(content);
                int num = value * 6;


            }
        });
    }
}
