package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // TODO (1) Declare a TextView variable called mToysListTextView
    TextView mToysListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO (3) Use findViewById to get a reference to the TextView from the layout
        mToysListTextView = findViewById(R.id.tv_toy_names);
        // TODO (4) Use the static ToyBox.getToyNames method and store the names in a String array
        String[] toyNames = ToyBox.getToyNames();

        // TODO (5) Loop through each toy and append the name to the TextView (add \n for spacing)
        for(String toyName : toyNames){
            mToysListTextView.append(toyName +"\n\n\n");
        }
    }
}