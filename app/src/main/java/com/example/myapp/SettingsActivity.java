package com.example.myapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch notificaitons;
    private Switch theme;
    private Switch temperature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notificaitons = findViewById(R.id.notification);
        theme = findViewById(R.id.theme);
        temperature = findViewById(R.id.temperature_unit);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart - SettingsActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onStart - SettingsActivity");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause - SettingsActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onPause - SettingsActivity");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume - SettingsActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onResume - SettingsActivity");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop - SettingsActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onStop - SettingsActivity");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy - SettingsActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onDestroy - SettingsActivity");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(this, "onSaveInstanceState - SettingsActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onSaveInstanceState - SettingsActivity");
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, "onRestoreInstanceState - SettingsActivity", Toast.LENGTH_SHORT).show();
        if (Debug.DEBUG) {
            Log.d(Key.KEY, "onRestoreInstanceState - SettingsActivity");
        }
    }
}
