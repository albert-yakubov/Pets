package com.example.stringdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.StringBuilder;

public class MainActivity extends AppCompatActivity {

    EditText userString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        public class Aufg1 {
            public static void main(String[] args) {
                String wort = "reliefpfpfeiller";
                char[] warray = wort.toCharArray();
                System.out.println(isPalindrom(warray));
            }

            public static boolean isPalindrom(char[] wort){
                boolean palindrom = false;
                if(wort.length%2 == 0){
                    for(int i = 0; i < wort.length/2-1; i++){
                        if(wort[i] != wort[wort.length-i-1]){
                            return false;
                        }else{
                            palindrom = true;
                        }
                    }
                }else{
                    for(int i = 0; i < (wort.length-1)/2-1; i++){
                        if(wort[i] != wort[wort.length-i-1]){
                            return false;
                        }else{
                            palindrom = true;
                        }
                    }
                }
                return palindrom;
            }
        }



    }
}
