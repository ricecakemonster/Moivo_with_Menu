package com.example.hyunji.moivowithmenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Weather extends AppCompatActivity {
    float longitude;
    float latitude;
    TextView cityField, detailsField, currentTemperatureField, tempAtNoonField, tempComeHomeField,
            weatherNowIcon, weatherNoonIcon, weatherComingHomeIcon, noonTextView, homeTextView, weatherTextView;
    ImageView kidView;
    ImageView umbrellaView;
    Typeface weatherFont;
    Button button;
    String textLine;
    String currentTemp;
    int currentTempInt;
    Boolean rain;
    long hotMin, mildMin, mildMax, coolMin, coolMax, coldMin, coldMax;
    Typeface typefaceLorem;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.alarm:
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                    Boolean isAlarmSet = prefs.getBoolean("IsAlarmSet",false);
                    Intent alarmIntent;
//                    Toast.makeText(Weather.this, "Is Alarm Set?" + isAlarmSet, Toast.LENGTH_SHORT).show();
                    if (isAlarmSet == false){
                        Toast.makeText(Weather.this, "Alarm is not set", Toast.LENGTH_SHORT).show();
                        alarmIntent = new Intent(getBaseContext(), MainActivity.class);


                    } else {
                        Toast.makeText(Weather.this, "Alarm is already set", Toast.LENGTH_SHORT).show();
                        alarmIntent = new Intent(Weather.this, AlarmSet.class);


                    }


                    startActivity(alarmIntent);
                    overridePendingTransition(0, 0);
                    return true;



                case R.id.weather:
                    Intent weatherIntent = new Intent(getBaseContext(), Weather.class);
                    startActivity(weatherIntent);
                    overridePendingTransition(0, 0);
                    return true;
//
            }
            return false;
        }

    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        getSupportActionBar().hide();
        typefaceLorem = Typeface.createFromAsset(getAssets(), "fonts/Lorem.ttf");


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        kidView = (ImageView) findViewById(R.id.kidView);
        umbrellaView = (ImageView) findViewById((R.id.umbrellaView));


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Float latitude = prefs.getFloat("latitude", 0);
        Float longitude = prefs.getFloat("longitude", 0);

        textLine = "Latitude: " + String.valueOf(latitude) + "\n Longitude: " + String.valueOf(longitude);
//        Log.e("location in Weather: ", textLine);


        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weathericons-regular-webfont.ttf");
        cityField = (TextView)findViewById(R.id.city_field);
        currentTemperatureField = (TextView)findViewById(R.id.current_temperature_field);
        tempAtNoonField = (TextView)findViewById(R.id.weatherAtNoon);
        tempComeHomeField = (TextView) findViewById(R.id.weatherComingHome);
        weatherNowIcon = (TextView)findViewById(R.id.weatherNow_icon);
        weatherNoonIcon = (TextView)findViewById(R.id.weatherNoon_icon);
        weatherComingHomeIcon = (TextView)findViewById(R.id.weatherCH_icon);
        weatherNowIcon.setTypeface(weatherFont);
        weatherNoonIcon.setTypeface(weatherFont);
        weatherComingHomeIcon.setTypeface(weatherFont);
        noonTextView = (TextView)findViewById(R.id.textView5);
        homeTextView = (TextView)findViewById(R.id.textView6);
        weatherTextView = (TextView)findViewById(R.id.weatherText);
        rain = false;

        hotMin = 22;
        mildMin = 15;
        mildMax = 21;
        coolMin = 8;
        coolMax = 14;
        coldMax = 7;


//        hotMin = 43;
//        mildMin = 36;
//        mildMax = 42;
//        coolMin = 30;
//        coolMax = 35;
//        coldMax = 29;

//        hotMin = 28;
//        mildMin = 21;
//        mildMax = 27;
//        coolMin = 15;
//        coolMax = 20;
//        coldMax = 15;


        Function.placeIdTask asyncTask =new Function.placeIdTask(new Function.AsyncResponse()
        {
            public void processFinish(String weather_city, String current_weather_description,
                                      String current_weather_temperature, String current_weather_iconText,
                                      String noon_Time, String noon_weather_description,
                                      String noon_weather_temperature, String noon_weather_iconText,
                                      String home_Time, String home_weather_description,
                                      String home_weather_temperature, String home_weather_iconText,
                                      double tempNow, double tempNoon, double tempHome)
            {
//                current_weather_iconText = "&#xf019"; //raining now

                List<Double> list = Arrays.asList(tempNow, tempNoon, tempHome);
                    double max = Collections.max(list);
                    double min = Collections.min(list);
                Log.e("maximum temp", String.valueOf(max));
                Log.e("minimu temp", String.valueOf(min));
                Log.e("current weather icon", current_weather_iconText);

                if (current_weather_iconText == "&#xf019" ||
                    current_weather_iconText == "&#xf01e" ||
                    current_weather_iconText == "&#xf01b")
                {
                    rain = true;
                }


                if (max <= coldMax) {
                    kidView.setImageResource(R.drawable.ic_winter);
                } else if (max < coldMax && rain) {
                    kidView.setImageResource(R.drawable.ic_winter_rain_snow);
                } else if (min > coolMin && rain){
                    kidView.setImageResource(R.drawable.ic_cool_rain);
                } else if (min <= coolMin && max <= mildMin){
                    kidView.setImageResource(R.drawable.ic_cool_jacket);
                } else if (min > coolMin && max <= mildMin){
                    kidView.setImageResource(R.drawable.ic_cool);
                } else if (min > mildMin && rain){
                    kidView.setImageResource(R.drawable.ic_mild_rain);
                } else if (min <= mildMin && max <=hotMin){
                    kidView.setImageResource(R.drawable.ic_mild_jacket);
                } else if (min > mildMin && max <= hotMin){
                    kidView.setImageResource(R.drawable.ic_mild);
                } else if (min > hotMin && rain ){
                    kidView.setImageResource(R.drawable.ic_summer_rain);
                } else if (min <= hotMin && max > hotMin) {
                    kidView.setImageResource(R.drawable.ic_summer_jacket);
                } else if (min > hotMin) {
                    kidView.setImageResource(R.drawable.ic_summer);
                }



                if (rain ||
                    home_weather_iconText == "&#xf019" ||
                    home_weather_iconText == "&#xf01e")
                {
                    umbrellaView.setImageResource(R.drawable.ic_umbrella);
                } // for rainy day

                if (!rain ||
                        home_weather_iconText == "&#xf019" ||
                        home_weather_iconText == "&#xf01e")
                {
                    umbrellaView.setImageResource(R.drawable.ic_dog);
                } // for rainy day



                cityField.setText(weather_city);
                currentTemperatureField.setText(current_weather_temperature);
                weatherNowIcon.setText(Html.fromHtml(current_weather_iconText));
                tempAtNoonField.setText(noon_weather_temperature);
                weatherNoonIcon.setText(Html.fromHtml(noon_weather_iconText));
                tempComeHomeField.setText(home_weather_temperature);
                weatherComingHomeIcon.setText(Html.fromHtml(home_weather_iconText));
                noonTextView.setText(noon_Time);
                homeTextView.setText(home_Time);
                weatherTextView.setTypeface(typefaceLorem);
                weatherTextView.setText(current_weather_description);
            }
        });



        String stringLatitude = String.valueOf(latitude);
        String stringLongitude = String.valueOf(longitude);
        asyncTask.execute(stringLatitude, stringLongitude);


    }
}




