package com.example.myapp.menu;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
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

import com.example.myapp.Constants;
import com.example.myapp.Model;
import com.example.myapp.Parcel;
import com.example.myapp.R;
import com.example.myapp.weather.WeatherActivity;
import com.example.myapp.weather.WeatherFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class MenuFragment extends Fragment {
    private boolean isLandscape;
    private Model model = Model.getInstance();
    private Parcel parcel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            parcel = (Parcel) savedInstanceState.getSerializable(Constants.KEY_EXTRA);
        } else {
            parcel = new Parcel("alexandria");
        }

        if (isLandscape) {
            showWeather(parcel);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(Constants.KEY_EXTRA, parcel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.THEME_CODE) {
            getActivity().recreate();
        }
    }

    private void showWeather(Parcel parcel) {
        if (isLandscape) {
            if (Constants.DEBUG) {
                Log.d("MenuFragment", "ShowWeatherIsLandscape");
            }
            WeatherFragment weatherFragment = (WeatherFragment) getFragmentManager().findFragmentById(R.id.fragment_weather);
            if (weatherFragment == null || !weatherFragment.getCurrentCity().equals(parcel.getCityName())) {
                weatherFragment = WeatherFragment.create(parcel);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.weather_container, weatherFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            if (Constants.DEBUG) {
                Log.d("MenuFragment", "ShowWeatherELSE");
            }
            Intent intent = new Intent(getActivity(), WeatherActivity.class);
            intent.putExtra(Constants.PARCEL, parcel);
            startActivityForResult(intent, Constants.THEME_CODE);
        }
    }

    private void init(View layout) {
        TextInputEditText searchEditText = layout.findViewById(R.id.search_edit_text);
        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    return;
                }
                TextView textView = (TextView) v;
                validate(textView, getResources(), getString(R.string.error_msg));
            }
        });

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

                String snackText = String.format(getString(R.string.snackbar_format_string), selectedCity);
                Snackbar.make(view, snackText, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                parcel = new Parcel(selectedCity);

                model.setCity(selectedCity);
                showWeather(parcel);
            }
        });

        return menuAdapter;
    }

    private void validate(TextView textView, Resources resources, String msg) {
        String value = textView.getText().toString();

        for (int i = 0; i < resources.getStringArray(R.array.cities).length; i++) {
            if (value.equals(resources.getStringArray(R.array.cities)[i])) {
                hideError(textView);
            } else {
                showError(textView, msg);
            }
        }
    }

    private void hideError(TextView textView) {
        textView.setError(null);
    }

    private void showError(TextView textView, String msg) {
        textView.setError(msg);
    }
}
