package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {

    private TextView degree;
    private TextView weather;
    private ImageView settings;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = MainPresenter.getInstance();

        weather = findViewById(R.id.weather);
        degree = findViewById(R.id.degrees);

        mainPresenter.setWeather(weather.getText().toString());
        mainPresenter.setDegree(degree.getText().toString());

        settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        weather.setText(mainPresenter.getWeather());
        degree.setText(mainPresenter.getDegree());
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

//        outState.putString(Key.KEY_DEGREE, degree.getText().toString());
//        outState.putString(Key.KEY_WEATHER, weather.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, "onRestoreInstanceState - WeatherActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onRestoreInstanceState - WeatherActivity");
        }

//        degree.setText(savedInstanceState.getString(Key.KEY_DEGREE));
//        weather.setText(savedInstanceState.getString(Key.KEY_WEATHER));
    }
}
