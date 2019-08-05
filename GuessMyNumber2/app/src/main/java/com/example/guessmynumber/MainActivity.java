

package com.example.guessmynumber;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;




public class MainActivity extends AppCompatActivity {

    static final int CORRECT = 0;
    static final int LOW = 1;
    static final int HIGH = 2;
    static final int RESET = 3;
    static final int MAX_NUMBER = 101;

    TextView userNumber, textPrevious;
    EditText userInput;
    Button submitButton;
    int target;
    Random generator;




        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNumber = findViewById(R.id.user_number);
        userInput = findViewById(R.id.user_input);
        submitButton = findViewById(R.id.submit_button);
        textPrevious = findViewById(R.id.text_previous);
        generator = new Random();

        submitButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {


                if(submitButton.getText() == getString(R.string.app_name)) {
                    int guess = Integer.parseInt(userInput.getText( ).toString( ));
                    userInput.setText(Integer.toString(guess));




                    }else{
                        target = generator.nextInt(MAX_NUMBER);
                        Updatetette
                    }

                    }

                }
        });


        }






    @Override
        protected void onStart(){
        super.onStart();
            int target = generator.nextInt(MAX_NUMBER);

        }
        private int checkGuess(int guess){
        if (guess == target) {
            return CORRECT;
        }else if(guess <= target){
            return LOW;
        }else {
            return HIGH;
        }
        }
        private void updateUI(int result){
        switch (result) {
            case CORRECT:
                userNumber.setText("Congrats!");
                break;
            case LOW:
                userNumber.setText("Too low!");
                break;
            case HIGH:
                userNumber.setText("Too HIGH");
                break;
            case RESET:
                submitButton.setText(R.string.app_name);
                userNumber.setText(R.string.app_name);
                break;
        }

        }
    }

