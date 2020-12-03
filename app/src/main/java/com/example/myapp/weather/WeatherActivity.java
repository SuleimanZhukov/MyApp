package com.example.myapp.weather;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapp.BaseActivity;
import com.example.myapp.BuildConfig;
import com.example.myapp.Constants;
import com.example.myapp.Model;
import com.example.myapp.Parcel;
import com.example.myapp.R;
import com.example.myapp.model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

public class WeatherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            if (Constants.DEBUG) {
                Log.d("WeatherActivity", "savedInstanceState");
            }
            Bundle bundle = getIntent().getExtras();
            Parcel parcel = (Parcel) bundle.getSerializable(Constants.PARCEL);
            if (parcel != null) {
                Log.d("WeatherActivity", "PARCEL IS NOT NULL");
            }
            WeatherFragment weatherFragment = WeatherFragment.create(parcel);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_weather, weatherFragment).commit();
        }
    }
}
