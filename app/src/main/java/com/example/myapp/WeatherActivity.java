package com.example.myapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {

    private TextView city;
    private TextView degree;
    private TextView weather;
    private ImageView settings;

    String urlCity = "https://en.wikipedia.org/wiki/Amman";
    String urlDegree = "https://yandex.ru/pogoda/";

    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = Model.getInstance();

        city = findViewById(R.id.cityName);
        weather = findViewById(R.id.weather);
        degree = findViewById(R.id.degrees);

        model.setWeather(weather.getText().toString());
        model.setDegree(degree.getText().toString());

        settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(urlCity);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        degree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(urlDegree);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        weather.setText(model.getWeather());
        degree.setText(model.getDegree());

        Parcel parcel = (Parcel) getIntent().getExtras().getSerializable(Key.KEY_EXTRA);
        city.setText(parcel.getCityName());
    }


    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart - WeatherActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onStart - WeatherActivity");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause - WeatherActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onPause - WeatherActivity");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume - WeatherActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onResume - WeatherActivity");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop - WeatherActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onStop - WeatherActivity");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy - WeatherActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onDestroy - WeatherActivity");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(this, "onSaveInstanceState - WeatherActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onSaveInstanceState - WeatherActivity");
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, "onRestoreInstanceState - WeatherActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onRestoreInstanceState - WeatherActivity");
        }
    }
}
