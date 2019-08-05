package com.example.guessinggame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;



public class MainActivity extends AppCompatActivity {

    static final  int MAX_NUMBER = 101;
    static final int CORRECT = 0;
    static final int LOW = 1;
    static final int HIGH = 2;
    static final int RESET = 3;

    EditText editEntry;
    Button buttonSubmit;
    TextView textMessage, textPrevious;

    int target;
    Random generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEntry = findViewById(R.id.edit_entry);
        buttonSubmit = findViewById(R.id.button_submit);
        textMessage = findViewById(R.id.text_message);
        textPrevious = findViewById(R.id.text_previous);
        generator = new Random();

        buttonSubmit.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                if(buttonSubmit.getText() == getString(R.string.submit_button_text)) {
                    int guess = Integer.parseInt(editEntry.getText().toString());
                    editEntry.setText(Integer.toString(guess));

                    UpdateUI(checkGuess(guess));
                }else{
                    target = generator.nextInt(MAX_NUMBER);
                    UpdateUI(RESET);
                }
            }
        });
    }
            @Override
            protected void onStart(){
            super.onStart();
            target = generator.nextInt(MAX_NUMBER);
            }
            private int checkGuess(int guess){
            if(guess == target) {
                return CORRECT;
            }else if(guess <= target){
                return LOW;
            }else{
                return HIGH;
            }
            }
            private void UpdateUI(int result){
                switch (result) {
                    case LOW:
                        textMessage.setText("Too Low!");
                        break;
                    case HIGH:
                        textMessage.setText("Too high!");
                        break;

                    case CORRECT:
                        textMessage.setText("Correct!");
                        case RESET:
                        buttonSubmit.setText(R.string.submit_button_text);
                        textMessage.setText(R.string.prompt);
                        break;

                }
            }
}
