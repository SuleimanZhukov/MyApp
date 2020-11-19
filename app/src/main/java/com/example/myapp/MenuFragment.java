package com.example.myapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        String[] data = getResources().getStringArray(R.array.cities);
        initRecyclerView(data, layout);
    }

    private MenuAdapter initRecyclerView(String[] dataSource, View layout) {
        RecyclerView recyclerView = layout.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        recyclerView.addItemDecoration(dividerItemDecoration);

        MenuAdapter menuAdapter = new MenuAdapter(dataSource);
        recyclerView.setAdapter(menuAdapter);

        menuAdapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void itemClickListener(View view, int position) {
                String selectedCity = getResources().getStringArray(R.array.cities)[position];
                parcel = new Parcel(selectedCity);
                showWeather(parcel);
            }
        });

        return menuAdapter;
    }
}
