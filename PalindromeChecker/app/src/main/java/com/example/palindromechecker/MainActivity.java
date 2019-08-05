package com.example.palindromechecker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editEntry;
    Button buttonSubmit;
    TextView resultView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    editEntry = findViewById(R.id.edit_entry);
    buttonSubmit = findViewById(R.id.button_submit);
    resultView = findViewById(R.id.result_view);
    buttonSubmit.setOnClickListener(new View.OnClickListener( ) {
        @Override
        public void onClick(View v) {
            String target = editEntry.getText().toString().toLowerCase();
            boolean valid = true;
            for(int i = 0; i < target.length(); ++i) {
                if (!(target.charAt(i) == target.charAt(target.length() - 1 - i ))) {
                    resultView.setText(R.string.not_a_palindrome);

                } else {
                    resultView.setText(R.string.palindrome);
                }

            }


        }
    });




    }
}
