package com.example.myapp;

import java.io.Serializable;

public class Parcel implements Serializable {
    private String cityName;

    public Parcel(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }


}
