package com.example.myapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MenuFragment extends Fragment {

    private boolean isLandscape;
    private Parcel parcel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            parcel = (Parcel) savedInstanceState.getSerializable(Key.KEY_EXTRA);
        } else {
            parcel = new Parcel("alexandria");
        }

        if (isLandscape) {
            showWeather(parcel);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(Key.KEY_EXTRA, parcel);
        super.onSaveInstanceState(outState);
    }

    private void showWeather(Parcel parcel) {
        if (isLandscape) {
            WeatherFragment weatherFragment = new WeatherFragment();
            //Не получается
//            if (weatherFragment == null || weatherFragment.getParcel().getCityName() != parcel.getCityName()) {
//                weatherFragment = WeatherFragment.create(parcel);
//            }
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.weather_container, weatherFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), WeatherActivity.class);
            intent.putExtra(Key.PARCEL, parcel);
            startActivity(intent);
        }
    }

    private void initList(View layout) {
        final Button button;
        button = layout.findViewById(R.id.amman);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parcel = new Parcel(button.getText().toString());
                showWeather(parcel);
            }
        });
    }
}
