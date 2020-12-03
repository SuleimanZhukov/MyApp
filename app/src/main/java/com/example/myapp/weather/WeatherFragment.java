package com.example.myapp.weather;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.DataSource;
import com.example.myapp.Constants;
import com.example.myapp.Model;
import com.example.myapp.Parcel;
import com.example.myapp.R;
import com.example.myapp.model.WeatherRequest;
import com.example.myapp.settings.SettingsActivity;
import com.example.myapp.settings.SettingsFragment;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

public class WeatherFragment extends Fragment {

    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String REST_OF_URL = "&units=metric&appid=";
    private static final String WEATHER_API_KEY = "65924ad2b3c04751bab085b9e4269cb9";
    private static final String WIKI = "https://en.wikipedia.org/wiki/";
    private static final String WEATHER_YANDEX = "https://yandex.ru/pogoda/";

    private TextView cityName;
    private TextView degree;
    private ImageView settings;
    private ImageView refresh;
    private Model model = Model.getInstance();

    public static WeatherFragment create(Parcel parcel) {
        WeatherFragment weatherFragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PARCEL, parcel);
        weatherFragment.setArguments(bundle);
        return weatherFragment;
    }

    private Parcel getParcel() {
        Parcel parcel = (Parcel) getArguments().getSerializable(Constants.PARCEL);
        return parcel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
        dailyListWeather(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        downloadData();
//        initParcel();
        cityName.setText(model.getCity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.THEME_CODE) {
            getActivity().recreate();
        }
    }

    private void initParcel() {
        Parcel parcel = getParcel();

        if (parcel != null) {
            if (Constants.DEBUG) {
                Log.d("initParcel", "IT WORKS");
            }
            cityName.setText(parcel.getCityName());
        }
    }

    private void initList(View layout) {

        cityName = layout.findViewById(R.id.cityName);
        degree = layout.findViewById(R.id.degrees);
        settings = layout.findViewById(R.id.settings);
        refresh = layout.findViewById(R.id.refresh);

        refresh.setOnClickListener(onClickListener);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    SettingsFragment settingsFragment = new SettingsFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.weather_container, settingsFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SettingsActivity.class);
                    startActivityForResult(intent, Constants.THEME_CODE);
                }
            }
        });

        cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(WIKI + Model.getInstance().getCity());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        degree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(WEATHER_YANDEX + Model.getInstance().getCity());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void dailyListWeather(View layout) {
        DataSource dataSource = new DataSource(getResources());
        initRecyclerView(dataSource.build(), layout);
    }

    private WeatherAdapter initRecyclerView(DataSource dataSource, View layout) {
        RecyclerView recyclerView = layout.findViewById(R.id.weather_daily_recycleview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), recyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        WeatherAdapter weatherAdapter = new WeatherAdapter(dataSource);
        recyclerView.setAdapter(weatherAdapter);

        return weatherAdapter;
    }

    public String getCurrentCity() {
        return getParcel().getCityName();
    }

    private void downloadData() {
        try {
            final URL uri = new URL(WEATHER_URL + Model.getInstance().getCity() + REST_OF_URL + WEATHER_API_KEY);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpURLConnection) uri.openConnection();

                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                        String result = getLines(in);
                        if (result == null) {
                            ErrorFragment errorFragment = new ErrorFragment();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.weather_container, errorFragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        }

                        Gson gson = new Gson();

                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                weatherData(weatherRequest);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            downloadData();
        }
    };

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

    private void weatherData(WeatherRequest weatherRequest) {
        int temp = (int) weatherRequest.getMain().getTemp();
        degree.setText(String.valueOf(temp));
    }
}
