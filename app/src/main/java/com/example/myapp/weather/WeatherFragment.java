package com.example.myapp.weather;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.myapp.Key;
import com.example.myapp.Parcel;
import com.example.myapp.R;
import com.example.myapp.URLs;
import com.example.myapp.settings.SettingsActivity;
import com.example.myapp.settings.SettingsFragment;

public class WeatherFragment extends Fragment {

    public static WeatherFragment create(Parcel parcel) {
        WeatherFragment weatherFragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Key.PARCEL, parcel);
        weatherFragment.setArguments(bundle);
        return weatherFragment;
    }

    public Parcel getParcel() {
        Parcel parcel = (Parcel) getArguments().getSerializable(Key.PARCEL);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Key.THEME_CODE) {
            getActivity().recreate();
        }
    }

    private void initList(View layout) {
//        Parcel parcel = getParcel();
        TextView cityName = layout.findViewById(R.id.cityName);
        TextView degree = layout.findViewById(R.id.degrees);
        ImageView settings = layout.findViewById(R.id.settings);

//        if (parcel != null) {
//            cityName.setText(parcel.getCityName());
//        }

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
                    startActivityForResult(intent, Key.THEME_CODE);
                }
            }
        });

        cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(URLs.WIKI_AMMAN);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        degree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(URLs.WEATHER_YANDEX);
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
}
