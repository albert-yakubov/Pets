package com.example.friends;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout display;
    EditText name;
    EditText phoneNumber;
    Button saveButton;
    ContactsManager contactsManager;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        contactsManager = new ContactsManager();

        display = findViewById(R.id.display);
        phoneNumber = findViewById(R.id.phone_number);
        name = findViewById(R.id.name);
        saveButton =findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                contactsManager.createContact(phoneNumber.getText().toString(),name.getText().toString());
                display.addView(getDefaultTextView(contactsManager.getLast().getDisplay()));

            }
        });
    }
    public TextView getDefaultTextView(String text){
        TextView view = new TextView(context);
        view.setText(text);
        view.setTextSize(28);
        return view;
    }
}
