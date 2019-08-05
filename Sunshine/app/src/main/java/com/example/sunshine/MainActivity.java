package com.example.sunshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // TODO (1) Create a field to store the weather display TextView
    TextView weatherDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO (2) Use findViewById to get a reference to the weather display TextView
        weatherDisplay = findViewById(R.id.tv_weather_data);
        // TODO (3) Create an array of Strings that contain fake weather data
        String[] weatherData = {

                "Today, May 17 - Clear - 17°C / 15°C",

                "Tomorrow - Cloudy - 19°C / 15°C",

                "Thursday - Rainy- 30°C / 11°C",

                "Friday - Thunderstorms - 21°C / 9°C",

                "Saturday - Thunderstorms - 16°C / 7°C",

                "Sunday - Rainy - 16°C / 8°C",

                "Monday - Partly Cloudy - 15°C / 10°C",

                "Tue, May 24 - Meatballs - 16°C / 18°C",

                "Wed, May 25 - Cloudy - 19°C / 15°C",

                "Thu, May 26 - Stormy - 30°C / 11°C",

                "Fri, May 27 - Hurricane - 21°C / 9°C",

                "Sat, May 28 - Meteors - 16°C / 7°C",

                "Sun, May 29 - Apocalypse - 16°C / 8°C",

                "Mon, May 30 - Post Apocalypse - 15°C / 10°C",};

        // TODO (4) Append each String from the fake weather data array to the TextView
        for(String weatherDay : weatherData){
            weatherDisplay.append(weatherDay + "\n\n\n");
        }
    }
}
