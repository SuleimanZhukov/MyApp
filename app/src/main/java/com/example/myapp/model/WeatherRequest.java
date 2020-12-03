package com.example.myapp.model;

import com.example.myapp.model.entity.Coord;
import com.example.myapp.model.entity.Main;
import com.example.myapp.model.entity.Weather;

public class WeatherRequest {
    private Main main;
    private Weather[] weather;
    private Coord coord;
    private String name;

    public Coord getCoord() {
        return coord;
    }

    public Main getMain() {
        return main;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public String getName() {
        return name;
    }
}
